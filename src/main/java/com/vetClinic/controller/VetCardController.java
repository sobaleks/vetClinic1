package com.vetClinic.controller;

import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.VetCard;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.vetClinic.service.VetCardService;
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
            if (vetCard == null) {
                return new ResponseEntity<>(
                        new ApplicationError(
                                "VetCard with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vetCard, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<?> getAllVetCard() {
            logger.info("doing /vetCard method getAllVetCard!");
            ArrayList<VetCard> vetCards = vetCardService.getAllVetCard();
            if (vetCards == null) {
                return new ResponseEntity<>(
                        new ApplicationError(
                                "VetCards not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(vetCards, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<?> createVetCard(
                @RequestBody @Valid VetCardRequestDTO vetCardRequestDTO, BindingResult bindingResult) {
            logger.info("doing /vetCard method createVetCard!");
            if (bindingResult.hasErrors()) {
                for (ObjectError o : bindingResult.getAllErrors()) {
                    logger.warn("We have bindingResult error :" + o );
                }
            }
            VetCard createdVetCard = vetCardService.createVetCard(vetCardRequestDTO);
            if (createdVetCard == null) {
                return new ResponseEntity<>(
                        new ApplicationError(
                                "VetCard not created", HttpStatus.NO_CONTENT.value()),
                        HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(createdVetCard, HttpStatus.OK);
        }

        @PutMapping
        public ResponseEntity<?> updateVetCard(
                @RequestBody @Valid VetCardRequestDTO vetCardRequestDTO, BindingResult bindingResult) {
            logger.info("doing /vetCard method updateVetCard!");
            if (bindingResult.hasErrors()) {
                for (ObjectError o : bindingResult.getAllErrors()) {
                    logger.warn("We have bindingResult error :" + o );
                }
            }
//            VetCard vetCard1 = new VetCard();
//            vetCard1 = vetCardService.getVetCardById(vetCardRequestDTO.getId());
//            if (vetCard1 == vetCardService.updateVetCard(vetCardRequestDTO)) {
//                return new ResponseEntity<>(
//                        new ApplicationError("VetCard not updated", HttpStatus.NOT_FOUND.value()),
//                        HttpStatus.NOT_FOUND);
//            }
            return new ResponseEntity<>(vetCardService.updateVetCard(vetCardRequestDTO), HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        private ResponseEntity<?> deleteVetCard(@PathVariable int id) {
            logger.info("doing /vetCard method deleteVetCard!");
            //VetCard vetCard = vetCardService.getVetCardById(id);
            vetCardService.deleteVetCard(id);
            if (vetCardService.getVetCardById(id) != null) {
                return new ResponseEntity<>(
                        new ApplicationError(
                                "VetCard is not deleted", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }


}
