package com.reservation.Controller;

import com.reservation.DTO.RestaurantDto;
import com.reservation.Entity.Restaurant;
import com.reservation.Service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if(restaurants == null || restaurants.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping("/update/{userID}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long userID, @RequestBody RestaurantDto restaurantDto) {
        restaurantDto.setUserId(userID);
        Restaurant restaurant = restaurantService.saveOrUpdateRestaurant(restaurantDto);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<Restaurant> getRestaurantByUserId(@PathVariable Long userID) {
        Restaurant restaurant = restaurantService.findByUserId(userID);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/restaurant/{restaurantId}/name")
    public ResponseEntity<String> getRestaurantNameById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant.getName());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String name) {
        Restaurant restaurant = restaurantService.findByName(name);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }
}