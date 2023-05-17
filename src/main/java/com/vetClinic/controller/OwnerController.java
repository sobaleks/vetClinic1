package com.vetClinic.controller;

import com.vetClinic.domain.DTO.OwnerResponseDTO;
import com.vetClinic.domain.Owner;
import com.vetClinic.domain.Pet;
import com.vetClinic.service.OwnerService;
import com.vetClinic.service.PetService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    OwnerService ownerService;

    PetService petService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OwnerController(OwnerService ownerService, PetService petService) {
        this.ownerService = ownerService;
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable int id) {
        logger.info("doing /owner method getOwnerById!");
        OwnerResponseDTO owner = ownerService.getOwnerById(id);
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllOwner() {
        logger.info("doing /owner method getAllOwner!");
        ArrayList<OwnerResponseDTO> owners = ownerService.getAllOwners();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOwner(@RequestBody @Valid Owner owner) {
        logger.info("doing /owner method getAllOwner!");
        Owner createOwner = ownerService.createOwner(owner);
        return new ResponseEntity<>(createOwner, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateOwner(@RequestBody @Valid Owner owner) {
        logger.info("doing /owner method updateOwner!");
        Owner updateOwner = ownerService.updateOwner(owner);
        return new ResponseEntity<>(updateOwner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteOwner(@PathVariable int id) {
        logger.info("doing /owner method deleteOwner!");
        ownerService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/myPets/{idOwn}")
    public ResponseEntity<?> getPetsByIdOwn(@PathVariable int idOwn) {
        logger.info("doing /owner/myPets/ method getPetsByIdOwn!");
        ArrayList<Pet> pets = ownerService.getPetsByIdOwn(idOwn);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PutMapping("/recCons/{name}")
    public ResponseEntity<?> recodingConsultation(@PathVariable String name) {
        logger.info("doing /owner/recCons/ method recodingConsultation!");
        petService.recodingConsultation(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/consult")
    public ResponseEntity<?> getOwnersNeedConsultation() {
        logger.info("doing /owner method getPetsNeedConsultation!");
        ArrayList<OwnerResponseDTO> owners = ownerService.getOwnersNeedConsultation();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }
}


