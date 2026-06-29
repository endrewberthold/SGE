package org.sge.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sge.parking.dtos.ParkingEntryDTO;
import org.sge.parking.dtos.ParkingExitDTO;
import org.sge.parking.dtos.ParkingSessionResponseDTO;
import org.sge.parking.service.ParkingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementação futura 3:
 * O cliente podera registrar o seu veiculo em uma
 * vaga disponivel, sistema similar ao de seleção de
 * assento em onibus fretado e aviões de voo domestico
 * */

@Tag(
        name = "Parking sessions",
        description = "Operations related to entry, exit and session retrieval"
)
@RestController
@RequestMapping("/parking")
public class ParkingSessionController {

    private final ParkingSessionService parkingSessionService;

    public ParkingSessionController(ParkingSessionService parkingSessionService){
        this.parkingSessionService = parkingSessionService;
    }

    @Operation(
            summary = "Start a parking session",
            description = "Create a new parking session for a registered vehicle upon entry"
    )
    @PostMapping("/entry")
    public ParkingSessionResponseDTO registerEntry(@RequestBody ParkingEntryDTO dto){
        return parkingSessionService.registerEntry(dto);
    }

    @Operation(
            summary = "End a parking session",
            description = "Ends an active parking session for a registered vehicle"
    )
    @PostMapping("/exit")
    public ParkingSessionResponseDTO registerExit(@RequestBody ParkingExitDTO dto){
        return parkingSessionService.registerExit(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSessionResponseDTO> findById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                parkingSessionService.findById(id)
        );
    }

    @GetMapping("/open")
    public ResponseEntity<List<ParkingSessionResponseDTO>> findOpen(){
        return ResponseEntity.ok(
                parkingSessionService.findOpenSessions()
        );
    }

    @GetMapping("/closed")
    public ResponseEntity<List<ParkingSessionResponseDTO>> findClosed(){
        return ResponseEntity.ok(
                parkingSessionService.findClosedSessions()
        );
    }

    @GetMapping("/vehicle/{plate}")
    public ResponseEntity<List<ParkingSessionResponseDTO>> findByVehicle(
            @PathVariable String plate
    ){
        return ResponseEntity.ok(parkingSessionService.findByPlate(plate));
    }
    @GetMapping("/client/{id}")
    public ResponseEntity<List<ParkingSessionResponseDTO>> findByClient(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(
                parkingSessionService.findByClient(id)
        );
    }
}
