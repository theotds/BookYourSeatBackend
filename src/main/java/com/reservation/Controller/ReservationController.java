package com.reservation.Controller;

import com.reservation.DTO.ReservationDto;
import com.reservation.Entity.Reservation;
import com.reservation.Service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        if (reservations == null || reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        boolean deleted = reservationService.deleteReservation(reservationId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/restaurant/{name}")
    public ResponseEntity<List<Reservation>> getReservationsByRestaurantName(@PathVariable String name) {
        List<Reservation> reservations = reservationService.findByRestaurantName(name);
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
}