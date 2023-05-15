package com.vetClinic.controller;

import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.VetCard;
import com.vetClinic.exeptions.AppError;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.vetClinic.service.DoctorService;
import com.vetClinic.utils.ApplicationError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    DoctorService doctorService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable int id) {
        logger.info("doing /doctor method getDoctorById!");
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return new ResponseEntity<>(
                    new AppError("Doctor with id = " + id + " not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllDoctor() {
        logger.info("doing /doctor method getAllDoctor!");
        ArrayList<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors == null) {
            return new ResponseEntity<>(
                    new AppError(
                            "Doctors not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(
            @RequestBody @Valid Doctor doctor, BindingResult bindingResult) {
        logger.info("doing /doctor method createDoctor!");
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        if (createdDoctor == null) {
            return new ResponseEntity<>(
                    new AppError("Doctor not created", HttpStatus.NO_CONTENT.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdDoctor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateDoctor(
            @RequestBody @Valid Doctor doctor, BindingResult bindingResult){
        logger.info("doing /doctor method updateDoctor!");
        Doctor doc = doctorService.getDoctorById(doctor.getId());
        if (doctor == doc) {
            return new ResponseEntity<>(
                    new ApplicationError("Doctor not updated", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctorService.updateDoctor(doctor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteDoctor(@PathVariable int id) {
        logger.info("doing /doctor method deleteDoctor!");
        if (doctorService.getDoctorById(id) == null) {
            return new ResponseEntity<>(
                    new AppError("Doctor with id = " + id + " not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>( id,HttpStatus.OK);
    }

    @PutMapping("/change/{id}/{changeStatus}")
    public ResponseEntity<?> changeStatus(@PathVariable int id, @PathVariable String changeStatus ) {
        logger.info("doing /doctor method change!");
        doctorService.changeStatus(id, changeStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/createVK")
    public ResponseEntity<?> createVetCardDoctor(
            @RequestBody @Valid VetCardRequestDTO vetCardRequestDTO, BindingResult bindingResult) {
        // logger.info("doing /doctor method createVetCardDoctor!");
        VetCard createVetCardDoctor = doctorService.createVetCardDoctor(vetCardRequestDTO);
        if (createVetCardDoctor == null) {
            return new ResponseEntity<>(
                    new AppError("VetCard not created", HttpStatus.NO_CONTENT.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createVetCardDoctor, HttpStatus.OK);
    }

}

