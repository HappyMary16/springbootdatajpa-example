package org.example.repository;

import org.example.model.Cat;
import org.example.model.CatBehaviour;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface CatRepository extends CrudRepository<Cat, String> {

    Cat findByName(String name);

    @Query("SELECT cat FROM Cat cat WHERE cat.name = :catName")
    Cat findCat(@Param("catName") String catName);

    Collection<Cat> findAllByBehaviour(CatBehaviour behaviour);


    void deleteAllByName(String name);

    @Modifying
    @Query("DELETE  FROM Cat cat WHERE cat.name = :catName")
    void deleteCat(@Param("catName") String catName);
}
