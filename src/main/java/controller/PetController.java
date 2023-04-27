package controller;

import domain.Pet;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import service.PetService;
import utils.ApplicationError;
import java.util.ArrayList;

@RestController
@RequestMapping("/pet")
public class PetController {

    PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable int id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pet with id " + id + "not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPet() {
        ArrayList<Pet> pets = petService.getAllPets();
        if (pets == null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pets not found", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody @Valid Pet pet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                //  logger.warn("We have bindingResult error :" + o );
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
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                //  logger.warn("We have bindingResult error :" + o );
            }
        }
        Pet pet1 = petService.getPetById(pet.getId());
        if (pet == pet1) {
            return new ResponseEntity<>(
                    new ApplicationError("Pet not updated", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pet1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePet(@PathVariable int id) {
        Pet pet = petService.getPetById(id);
        petService.deletePet(id);
        if (petService.getPetById(id) != null) {
            return new ResponseEntity<>(
                    new ApplicationError("Pet not deleted", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

}
