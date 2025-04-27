package com.vetClinic.controller;

import com.vetClinic.domain.DTO.DoctorResponseDTO;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.VetCard;
import com.vetClinic.service.DoctorService;
import com.vetClinic.utils.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    DoctorService doctorService;
    private JwtUtil jwtUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DoctorController(DoctorService doctorService, JwtUtil jwtUtil) {

        this.doctorService = doctorService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable int id) {
        logger.info("doing /doctor method getDoctorById!");
        DoctorResponseDTO doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllDoctor() {
        logger.info("doing /doctor method getAllDoctor!");
        ArrayList<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }



    @PostMapping()
    public ResponseEntity<?> createDoctor(@RequestBody @Valid Doctor doctor) {
        logger.info("doing /doctor method createDoctor!");

        Doctor createdDoctor = doctorService.createDoctor(doctor);

        String token = jwtUtil.generateToken(createdDoctor.getLogin(), createdDoctor.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("doctor", createdDoctor);
        response.put("token", token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateDoctor(
            @RequestBody @Valid Doctor doctor) {
        logger.info("doing /doctor method updateDoctor!");
        Doctor updateDoctor = doctorService.updateDoctor(doctor);
        return new ResponseEntity<>(updateDoctor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteDoctor(@PathVariable int id) {
        logger.info("doing /doctor method deleteDoctor!");
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/change/{id}/{changeStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable int id, @PathVariable String changeStatus) {
        logger.info("doing /doctor method change status");
        doctorService.changeStatus(id, changeStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/createVK")
    public ResponseEntity<?> createVetCardDoctor(
            @RequestBody @Valid VetCardRequestDTO vetCardRequestDTO) {
        logger.info("doing /doctor method createVetCardDoctor!");
        VetCard createVetCardDoctor = doctorService.createVetCardDoctor(vetCardRequestDTO);
        return new ResponseEntity<>(createVetCardDoctor, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchBySpecialisation(@RequestParam String specialisation) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialisation(specialisation);
        return ResponseEntity.ok(doctors);
    }
    @GetMapping("/by-specialisation")
    public ResponseEntity<List<LinkedHashMap<String, Object>>> getDoctorsBySpecialisation() {
        List<LinkedHashMap<String, Object>> result = doctorService.getDoctorsGroupedBySpecialisation();
        return ResponseEntity.ok(result);
    }
}

