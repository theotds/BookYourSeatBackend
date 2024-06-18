package com.reservation.DTO;

public class RestaurantDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private Long userId;

    // Constructors, getters, and setters

    public RestaurantDto() {
    }

    public RestaurantDto(Long id, String name, String description, String address, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
