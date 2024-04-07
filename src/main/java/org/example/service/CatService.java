package org.example.service;

import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import org.example.model.Cat;
import org.example.model.CatBehaviour;
import org.example.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatService {

    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
        catRepository.save(new Cat("Tomas", CatBehaviour.CALM));
        catRepository.save(new Cat("Oscar", CatBehaviour.NORMAL));
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
        if (catRepository.existsById(cat.getName())) {
            return false;
        }

        catRepository.save(cat);
        return true;
    }

    public Cat updateCat(Cat cat) {
        return catRepository.save(cat);
    }

    @Transactional
    public Cat deleteCat(String catName) {
        Cat cat = catRepository.findCat(catName);
        catRepository.deleteCat(catName);
        return cat;
    }

    /**
     * Видаляємо всіх котів з Бази Даних коли програма завершується
     */
    @PreDestroy
    public void destroy() {
        catRepository.deleteAll();
    }
}
