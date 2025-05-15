package com.vetClinic.controller;

import com.vetClinic.domain.Appointment;
import com.vetClinic.domain.DTO.LoginRequestDTO;
import com.vetClinic.domain.DTO.LoginResponse;
import com.vetClinic.domain.DTO.OwnerResponseDTO;
import com.vetClinic.domain.Owner;
import com.vetClinic.domain.Pet;
import com.vetClinic.repository.OwnerRepository;
import com.vetClinic.service.OwnerService;
import com.vetClinic.service.PetService;
import com.vetClinic.utils.DtoMapper;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    OwnerService ownerService;
    OwnerRepository ownerRepository;
    private JwtUtil jwtUtil;
    PetService petService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OwnerController(OwnerService ownerService, PetService petService, JwtUtil jwtUtil,
                           OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.jwtUtil = jwtUtil;
        this.ownerRepository = ownerRepository;
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
        String token = jwtUtil.generateToken(createOwner.getLogin(), createOwner.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("owner", createOwner);
        response.put("token", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
        return new ResponseEntity<>(id, HttpStatus.OK);
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        logger.info("doing /owner/login method login!");
        LoginResponse response = ownerService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


