package org.sge.parking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sge.enums.SessionStatus;
import org.sge.vehicle.entity.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "parking_sessions")
public class ParkingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Vehicle vehicle;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @ManyToOne
    @JoinColumn(name = "parking_rate_id")
    private ParkingRate parkingRate;
}
