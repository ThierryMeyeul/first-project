package Undertaker.HospiBook.model.entities;

import jakarta.persistence.Entity;

@Entity
public class Professeur extends Personne{
    private String profession;

    public Professeur(String name, String profession) {
        super(name);
        this.profession = profession;
    }

    public Professeur(String profession) {
        this.profession = profession;
    }
}
