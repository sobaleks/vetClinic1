package com.vetClinic.controller;

import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.Pet;
import com.vetClinic.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPet() {
        logger.info("doing /pet method getAllPet!");
        ArrayList<Pet> pets = petService.getAllPets();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody @Valid PetRequestDTO petRequestDTO) {
        logger.info("doing /pet method createPet!");
        Pet createPet = petService.createPet(petRequestDTO);
        return new ResponseEntity<>(createPet, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody @Valid PetRequestDTO petRequestDTO) {  //TODO @PathVariable
        logger.info("doing /pet method updatePet!");
        petService.updatePet(petRequestDTO);
        return new ResponseEntity<>(petService.updatePet(petRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePet(@PathVariable int id) {
        logger.info("doing /pet method deletePet!");
        petService.deletePet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
