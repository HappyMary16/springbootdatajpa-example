package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Cat;
import org.example.model.CatBehaviour;
import org.example.repository.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatService {

    private static final Logger log = LoggerFactory.getLogger(CatService.class);

    @Value("${app.max-cat-number}")
    private int maxCatNumber;

    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Collection<Cat> getAllCats() {
        return (Collection<Cat>) catRepository.findAll();
    }

    public Cat getCat(String catName) {
        return catRepository.findByName(catName);
    }

    public Collection<Cat> getCatsByBehaviour(CatBehaviour catBehaviour) {
        return catRepository.findAllByBehaviour(catBehaviour);
    }

    public boolean addCat(Cat cat) {
        log.info("Creating a cat. Cat: {}", cat);

        if (catRepository.count() == maxCatNumber) {
            log.info("Cat '{}' can not be created. Max supported amount of cats: {}.", cat.getName(), maxCatNumber);
            return false;
        }

        if (catRepository.existsById(cat.getName())) {
            log.info("Cat '{}' can not be created. It already exists.", cat.getName());
            return false;
        }

        catRepository.save(cat);

        log.info("Cat '{}' is created.", cat.getName());
        return true;
    }

    public Cat updateCat(Cat cat) {
        log.info("Updating a cat. Cat: {}", cat);
        return catRepository.save(cat);
    }

    @Transactional
    public Cat deleteCat(String catName) {
        log.info("Deleting a cat. Cat: {}", catName);
        Cat cat = catRepository.findCat(catName);
        catRepository.deleteCat(catName);
        return cat;
    }
}
