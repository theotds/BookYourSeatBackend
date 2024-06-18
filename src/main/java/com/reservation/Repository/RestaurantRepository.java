package com.reservation.Repository;

import com.reservation.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByUserId(Long userId);
    Restaurant findByName(String name);
}
