package com.vetClinic.controller;

import com.vetClinic.domain.Doctor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.vetClinic.service.DoctorService;
import com.vetClinic.utils.ApplicationError;

import java.util.ArrayList;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable int id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Doctor with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllDoctor() {
        ArrayList<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Doctors not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody @Valid Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                //  logger.warn("We have bindingResult error :" + o );
            }
        }
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        if (createdDoctor == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Doctor not created", HttpStatus.NO_CONTENT.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdDoctor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateDoctor(@RequestBody @Valid Doctor doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                //  logger.warn("We have bindingResult error :" + o );
            }
        }
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
        Doctor doctor = doctorService.getDoctorById(id);
        doctorService.deleteDoctor(id);
        if (doctorService.getDoctorById(id) != null) {
            return new ResponseEntity<>(
                    new ApplicationError("Doctor not deleted", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping("/change/{id}/{changeStatus}")
    public ResponseEntity<?> change(@PathVariable int id, @PathVariable String changeStatus ) {
        doctorService.change(id, changeStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

