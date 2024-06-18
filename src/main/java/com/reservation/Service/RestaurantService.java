package com.reservation.Service;

import com.reservation.DTO.RestaurantDto;
import com.reservation.Entity.Restaurant;
import com.reservation.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant saveOrUpdateRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.findByUserId(restaurantDto.getUserId());
        if (restaurant == null) {
            restaurant = new Restaurant();
        }
        restaurant.setName(restaurantDto.getName());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setUserId(restaurantDto.getUserId());
        return restaurantRepository.save(restaurant);
    }

    public Restaurant findByUserId(Long userId) {
        return restaurantRepository.findByUserId(userId);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }
}
