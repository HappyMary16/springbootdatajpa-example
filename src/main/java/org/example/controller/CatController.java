package org.example.controller;

import org.example.model.Cat;
import org.example.model.CatBehaviour;
import org.example.service.CatService;
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

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    public Collection<Cat> getAllCats(@RequestParam(required = false) CatBehaviour behaviour) {
        if (behaviour != null) {
            return catService.getCatsByBehaviour(behaviour);
        }

        return catService.getAllCats();
    }

    @GetMapping("/{catName}")
    public Cat getCat(@PathVariable String catName) {
        if (catService.getCat(catName) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return catService.getCat(catName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cat createCat(@RequestBody Cat cat) {
        if (catService.addCat(cat)) {
            return catService.getCat(cat.getName());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{catName}")
    public Cat updateCat(@PathVariable String catName, @RequestBody Cat cat) {
        if (catService.getCat(catName) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return catService.updateCat(cat);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catName}")
    public Cat deleteCat(@PathVariable String catName) {
        if (catService.getCat(catName) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return catService.deleteCat(catName);
    }
}
