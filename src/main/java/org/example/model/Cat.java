package org.example.model;

import jakarta.persistence.*;

import java.time.Instant;

@Table(name = "cats")
@Entity
public class Cat {

    @Id
    @Column(name = "cat_name")
    private String name;
    @Enumerated(EnumType.STRING)
    private CatBehaviour behaviour;
    private Instant birthday;

    public Cat() {
    }

    public Cat(String name, CatBehaviour behaviour) {
        this.name = name;
        this.behaviour = behaviour;
        birthday = Instant.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBehaviour(CatBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public CatBehaviour getBehaviour() {
        return behaviour;
    }

    public Instant getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", behaviour=" + behaviour +
                ", birthday=" + birthday +
                '}';
    }
}
