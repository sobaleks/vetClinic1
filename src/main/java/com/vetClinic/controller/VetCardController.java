package com.vetClinic.controller;

import com.vetClinic.domain.VetCard;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.vetClinic.service.VetCardService;
import com.vetClinic.utils.ApplicationError;

import java.util.ArrayList;
@RestController
@RequestMapping("/card")
public class VetCardController {


        VetCardService vetCardService;

        @Autowired
        public VetCardController(VetCardService vetCardService) {
            this.vetCardService = vetCardService;
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getVetCardById(@PathVariable int id) {
            VetCard vetCard = vetCardService.getVetCardById(id);
            if (vetCard == null) {
                return new ResponseEntity<>(
                        new ApplicationError("VetCard with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vetCard, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<?> getAllVetCard() {
            ArrayList<VetCard> vetCards = vetCardService.getAllVetCard();
            if (vetCards == null) {
                return new ResponseEntity<>(
                        new ApplicationError("VetCards not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(vetCards, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<?> createVetCard(@RequestBody @Valid VetCard vetCard, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                for (ObjectError o : bindingResult.getAllErrors()) {
                    //  logger.warn("We have bindingResult error :" + o );
                }
            }
            VetCard createdVetCard = vetCardService.createVetCard(vetCard);
            if (createdVetCard == null) {
                return new ResponseEntity<>(
                        new ApplicationError("VetCard not created", HttpStatus.NO_CONTENT.value()),
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(createdVetCard, HttpStatus.OK);
        }

        @PutMapping
        public ResponseEntity<?> updateVetCard(@RequestBody @Valid VetCard vetCard, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                for (ObjectError o : bindingResult.getAllErrors()) {
                    //  logger.warn("We have bindingResult error :" + o );
                }
            }
            VetCard vetCard1 = vetCardService.getVetCardById(vetCard.getId());
            if (vetCard == vetCard1) {
                return new ResponseEntity<>(
                        new ApplicationError("VetCard not updated", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(vetCardService.updateVetCard(vetCard), HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        private ResponseEntity<?> deleteVetCard(@PathVariable int id) {
            VetCard vetCard = vetCardService.getVetCardById(id);
            vetCardService.deleteVetCard(id);
            if (vetCardService.getVetCardById(id) != null) {
                return new ResponseEntity<>(
                        new ApplicationError("VetCard is not deleted", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }


}
