package com.example.demo.service;

import com.example.demo.model.CityInfo;
import com.example.demo.model.CityTime;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CityService {

    private final List<CityInfo> cities = new ArrayList<>();

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("cities.csv"), StandardCharsets.UTF_8))) {
            String line;
            reader.readLine(); // пропустить заголовок
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    cities.add(new CityInfo(
                            parts[0],
                            parts[1],
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3]),
                            parts[4],
                            null,  // временно, заполним позже
                            null,
                            null
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CityInfo> getAllCities() {
        return enrichCitiesWithTime(cities);
    }

    public CityInfo getCityByName(String name) {
        return cities.stream()
                .filter(c -> c.getCity().equalsIgnoreCase(name))
                .map(this::enrichCityWithTime)
                .findFirst()
                .orElse(null);
    }

    public CityInfo getCityByCountry(String country) {
        return cities.stream()
                .filter(c -> c.getCountry().equalsIgnoreCase(country))
                .map(this::enrichCityWithTime)
                .findFirst()
                .orElse(null);
    }

    public CityInfo getCityByTimeZone(String timeZone) {
        return cities.stream()
                .filter(c -> c.getTimezone().split("/")[0].equalsIgnoreCase(timeZone))
                .map(this::enrichCityWithTime)
                .findFirst()
                .orElse(null);
    }

    public CityTime getCityTimeByName(String name) {
        CityInfo city = cities.stream()
                .filter(c -> c.getCity().equalsIgnoreCase(name))
                .map(this::enrichCityWithTime)
                .findFirst()
                .orElse(null);
        return new CityTime(city.getLocalTime(), city.getUtcTime());
    }

    private List<CityInfo> enrichCitiesWithTime(List<CityInfo> cityList) {
        List<CityInfo> updated = new ArrayList<>();
        for (CityInfo city : cityList) {
            updated.add(enrichCityWithTime(city));
        }
        return updated;
    }

    private CityInfo enrichCityWithTime(CityInfo city) {
        try {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(city.getTimezone()));
            int localHour = now.getHour();
            int utcHour = Integer.parseInt(Instant.now().toString().substring(11, 13));
            log.info("Local hour: {}", localHour);
            log.info("UTC hour: {}", utcHour);
            String utc = String.valueOf(localHour - utcHour);
            utc = (utc.startsWith("-"))? utc : "+"+utc;
            city.setLocalTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            city.setUtcTime(Instant.now().toString()); // UFC-формат (ISO 8601, UTC)
            city.setTimeDescription(city.getCity() + ": " + now.format(DateTimeFormatter.ofPattern("HH:mm")) + " (" + utc + " UTC)");
        } catch (Exception e) {
            city.setLocalTime("Unknown");
            city.setUtcTime("Unknown");
        }
        return city;
    }
}