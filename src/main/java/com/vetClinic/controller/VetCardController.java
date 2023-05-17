package com.vetClinic.controller;

import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.VetCard;
import com.vetClinic.service.VetCardService;
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
@RequestMapping("/card")
public class VetCardController {

    VetCardService vetCardService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public VetCardController(VetCardService vetCardService) {
        this.vetCardService = vetCardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVetCardById(@PathVariable int id) {
        logger.info("doing /vetCard method getVetCardById!");
        VetCard vetCard = vetCardService.getVetCardById(id);
        return new ResponseEntity<>(vetCard, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllVetCard() {
        logger.info("doing /vetCard method getAllVetCard!");
        ArrayList<VetCard> vetCards = vetCardService.getAllVetCard();
        return new ResponseEntity<>(vetCards, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createVetCard(
            @RequestBody @Valid VetCardRequestDTO vetCardRequestDTO) {
        logger.info("doing /vetCard method createVetCard!");
        VetCard createVetCard = vetCardService.createVetCard(vetCardRequestDTO);
        return new ResponseEntity<>(createVetCard, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateVetCard(
            @RequestBody @Valid VetCardRequestDTO vetCardRequestDTO) {
        logger.info("doing /vetCard method updateVetCard!");
        VetCard vetCard = vetCardService.updateVetCard(vetCardRequestDTO);
        return new ResponseEntity<>(vetCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteVetCard(@PathVariable int id) {
        logger.info("doing /vetCard method deleteVetCard!");
        vetCardService.deleteVetCard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
