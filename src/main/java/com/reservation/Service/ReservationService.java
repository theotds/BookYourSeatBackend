package com.reservation.Service;


import com.reservation.DTO.ReservationDto;
import com.reservation.Entity.Reservation;
import com.reservation.Entity.Restaurant;
import com.reservation.Entity.User;
import com.reservation.Repository.ReservationRepository;
import com.reservation.Repository.RestaurantRepository;
import com.reservation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation createReservation(ReservationDto reservationDto) {
        User user = userRepository.findById(reservationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(reservationDto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setReservationTime(reservationDto.getReservationHour());
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public boolean deleteReservation(Long reservationId) {
        if (reservationRepository.existsById(reservationId)) {
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }

    public List<Reservation> findByRestaurantName(String name) {
        return reservationRepository.findByRestaurant_Name(name);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}