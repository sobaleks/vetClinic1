package com.vetClinic.controller;

import com.vetClinic.domain.Owners;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.vetClinic.service.OwnersService;
import com.vetClinic.utils.ApplicationError;

import java.util.ArrayList;

@RestController
@RequestMapping("/owner")
public class QwnersController {

    OwnersService ownersService;

    @Autowired
    public QwnersController(OwnersService ownersService) {
        this.ownersService = ownersService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable int id){
        Owners owners = ownersService.getOwnersById(id);
        if(owners == null){
            return new ResponseEntity<>(
                    new ApplicationError("Owner with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NO_CONTENT);
        }
    return new ResponseEntity<>(owners, HttpStatus.OK);
        }

    @GetMapping
    public ResponseEntity<?> getAllOwner() {
        ArrayList<Owners> owners = ownersService.getAllOwners();
        if (owners == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOwner(@RequestBody @Valid Owners owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                //  logger.warn("We have bindingResult error :" + o );
            }
        }
        Owners createdOwner = ownersService.createdOwner(owner);
        if (createdOwner == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not created", HttpStatus.NO_CONTENT.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(createdOwner, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateOwner(@RequestBody @Valid Owners owners, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                //  logger.warn("We have bindingResult error :" + o );
            }
        }
        Owners owner = ownersService.getOwnersById(owners.getId());
        if (owners == owner) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not updated", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownersService.updateOwners(owners), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteOwner(@PathVariable int id) {
        Owners owners = ownersService.getOwnersById(id);
        ownersService.deletedOwners(id);
        if (ownersService.getOwnersById(id) != null) {
            return new ResponseEntity<>(
                    new ApplicationError("Owner not deleted", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


