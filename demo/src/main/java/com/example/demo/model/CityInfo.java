package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInfo {
    private long id;  // id
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    private String timezone;
    private String localTime;  // текущее местное время
    private String utcTime;    // текущее UTC время в формате UFC (ISO 8601)
    private String timeDescription;  // дополнительное поле описания времени
    private long population; // население города
    private String imageUrl; // URL изображения города
}