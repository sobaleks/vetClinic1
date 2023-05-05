package com.vetClinic.controller;

import com.vetClinic.domain.Owner;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.vetClinic.service.OwnerService;
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
@RequestMapping("/owner")
public class OwnerController {

    OwnerService ownerService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable int id){
        logger.info("doing /owner method getOwnerById!");
        Owner owner = ownerService.getOwnerById(id);
        if(owner == null){
            return new ResponseEntity<>(
                    new ApplicationError("Owner with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NO_CONTENT);
        }
    return new ResponseEntity<>(owner, HttpStatus.OK);
        }

    @GetMapping
    public ResponseEntity<?> getAllOwner() {
        logger.info("doing /owner method getAllOwner!");
        ArrayList<Owner> owners = ownerService.getAllOwners();
        if (owners == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOwner(@RequestBody @Valid Owner owner, BindingResult bindingResult) {
        logger.info("doing /owner method getAllOwner!");
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                 logger.warn("We have bindingResult error :" + o );
            }
        }
        Owner createdOwner = ownerService.createdOwner(owner);
        if (createdOwner == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not created", HttpStatus.NO_CONTENT.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdOwner, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateOwner(@RequestBody @Valid Owner owners, BindingResult bindingResult) {
        logger.info("doing /owner method updateOwner!");
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                 logger.warn("We have bindingResult error :" + o );
            }
        }
        Owner owner = ownerService.getOwnerById(owners.getId());
        if (owners == owner) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not updated", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerService.updateOwner(owners), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteOwner(@PathVariable int id) {
        logger.info("doing /owner method deleteOwner!");
        Owner owners = ownerService.getOwnerById(id);
        ownerService.deletedOwner(id);
        if (ownerService.getOwnerById(id) != null) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not deleted", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

