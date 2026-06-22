package org.sge.parking.service;

import org.sge.enums.RateType;
import org.sge.exception.ResourceNotFoundException;
import org.sge.parking.service.result.ParkingCalculationResult;
import org.sge.parking.entity.ParkingRate;
import org.sge.parking.entity.ParkingSession;
import org.sge.parking.repository.ParkingRateRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;

@Service
public class ParkingFeeCalculatorService {

    private final ParkingRateRepository parkingRateRepository;

    public ParkingFeeCalculatorService(ParkingRateRepository parkingRateRepository) {
        this.parkingRateRepository = parkingRateRepository;
    }

    public ParkingCalculationResult calculate(ParkingSession session){

        Duration duration = Duration.between(
                session.getEntryTime(),
                session.getExitTime()
        );

        ParkingRate rate = parkingRateRepository.findByTypeAndActiveTrue(
                RateType.HOUR).orElseThrow(() -> new ResourceNotFoundException("Tarifa não encontrada."));

        long hours = Math.max(1, duration.toHours());

        BigDecimal total = rate.getAmount().multiply(BigDecimal.valueOf(hours));

        return new ParkingCalculationResult(total, rate);

    }
}