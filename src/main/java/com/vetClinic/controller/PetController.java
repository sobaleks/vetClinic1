package com.vetClinic.controller;

import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.Pet;
import com.vetClinic.exeptions.AppError;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.vetClinic.service.PetService;
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
@RequestMapping("/pet")
public class PetController {

    PetService petService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable int id) {
       // logger.info("doing /pet method getPetById!");
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return new ResponseEntity<>(
                    new AppError("Pet with id = " + id + " not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPet() {
        logger.info("doing /pet method getAllPet!");
        ArrayList<Pet> pets = petService.getAllPets();
        if (pets == null) {
            return new ResponseEntity<>(
                    new AppError("Pets not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody @Valid PetRequestDTO petRequestDTO) {
        logger.info("doing /pet method createPet!");
        if (petService.createPet(petRequestDTO)==null) {
            return new ResponseEntity<>(new AppError("Pet was not created",
                    HttpStatus.NO_CONTENT.value()), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(petRequestDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody @Valid PetRequestDTO petRequestDTO) {  //TODO @PathVariable
        logger.info("doing /pet method updatePet!");
        if (petService.getPetById(petRequestDTO.getId())==null) {
            return new ResponseEntity<>(
                    new AppError("Pet with id = " + petRequestDTO.getId() + " not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
            }
        petService.updatePet(petRequestDTO);
        return new ResponseEntity<>(petService.updatePet(petRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePet(@PathVariable int id) {
        logger.info("doing /pet method deletePet!");
        if (petService.getPetById(id) == null) {
            return new ResponseEntity<>(
                    new AppError("Pet with id = " + id + " not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        petService.deletePet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/recCons/{id}")
    public ResponseEntity<?> recodingConsultation(@PathVariable int id) {
        logger.info("doing /pet/recCons/ method recodingConsultation!");
        if(petService.getPetById(id)==null){
            return new ResponseEntity<>(
                    new AppError("Consultation not recoding", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        petService.recodingConsultation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
