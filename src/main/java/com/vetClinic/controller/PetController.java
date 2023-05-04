package com.vetClinic.controller;

import com.vetClinic.domain.Pet;
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
        logger.info("doing /pet method getPetById!");
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return new ResponseEntity<>(
                    new ApplicationError(
                            "Pet with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPet() {
        logger.info("doing /pet method getAllPet!");
        ArrayList<Pet> pets = petService.getAllPets();
        if (pets == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pets not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody @Valid Pet pet, BindingResult bindingResult) {
        logger.info("doing /pet method createPet!");
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("We have bindingResult error :" + o );
            }
        }
        Pet createdPet = petService.createPet(pet);
        if (createdPet == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pet not created", HttpStatus.NO_CONTENT.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdPet, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody @Valid Pet pet, BindingResult bindingResult) {
        logger.info("doing /pet method updatePet!");
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("We have bindingResult error :" + o );
            }
        }
        Pet pet1 = petService.getPetById(pet.getId());
        if (pet == pet1) {
            return new ResponseEntity<>(
                    new ApplicationError("Pet not updated", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(petService.updatePet(pet), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePet(@PathVariable int id) {
        logger.info("doing /pet method deletePet!");
        Pet pet = petService.getPetById(id);
        petService.deletePet(id);
        if (petService.getPetById(id) != null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pet not deleted", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/myPets/{id}")
    public ResponseEntity<?> getPetsByIdOwn(@PathVariable int id) {
        logger.info("doing /pet method getPetsByIdOwn!");
        ArrayList<Pet> pets = petService.getPetsByIdOwn(id);
        if (pets == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pets not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PutMapping("/recCons/{id}")
    public ResponseEntity<?> recodingConsultation(@PathVariable int id) {
        logger.info("doing /pet method recodingConsultation!");
        petService.recodingConsultation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
