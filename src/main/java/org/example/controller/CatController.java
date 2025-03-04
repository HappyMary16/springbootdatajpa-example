package org.example.controller;

import org.example.model.Cat;
import org.example.model.CatBehaviour;
import org.example.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

/**
 * Опрацьовує HTTP запити й помилки, які виникають.
 * Повертає правильні коди відповіді
 */
@RequestMapping("/cats")
@RestController
public class CatController {

    private static final Logger log = LoggerFactory.getLogger(CatController.class);

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    public Collection<Cat> getAllCats(@RequestParam(required = false) CatBehaviour behaviour) {
        log.debug("Getting all cats. Behaviour: {}", behaviour);
        if (behaviour != null) {
            return catService.getCatsByBehaviour(behaviour);
        }

        return catService.getAllCats();
    }

    @GetMapping("/{catName}")
    public Cat getCat(@PathVariable String catName) {
        log.debug("Getting cat. Cat name: {}", catName);

        if (catService.getCat(catName) == null) {
            log.debug("Cat '{}' was not found.", catName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        log.debug("Cat '{}' was found.", catName);
        return catService.getCat(catName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cat createCat(@RequestBody Cat cat) {
        log.debug("Creating a cat. Cat: {}", cat);

        if (catService.addCat(cat)) {
            log.debug("Cat '{}' was created successfully.", cat.getName());
            return catService.getCat(cat.getName());
        } else {
            log.debug("Cat '{}' was not created.", cat.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{catName}")
    public Cat updateCat(@PathVariable String catName, @RequestBody Cat cat) {
        log.debug("Updating a cat. Cat: {}", cat);
        if (catService.getCat(catName) == null) {
            log.debug("Cat '{}' was not updated as it was not found.", catName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        log.debug("Cat '{}' was updated successfully.", catName);
        return catService.updateCat(cat);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catName}")
    public Cat deleteCat(@PathVariable String catName) {
        log.debug("Deleting a cat. Cat: {}", catName);
        if (catService.getCat(catName) == null) {
            log.debug("Cat '{}' was not deleted as it was not found.", catName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        log.debug("Cat '{}' was deleted successfully.", catName);
        return catService.deleteCat(catName);
    }
}
