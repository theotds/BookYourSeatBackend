package com.reservation.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    private String reservationDate;
    private String reservationHour;
    private Long userId;
    private Long restaurantId;

    // Getters and Setters



    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getReservationHour() {
        return reservationHour;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}