package Undertaker.HospiBook.model.entities;

import jakarta.persistence.Entity;

@Entity
public class Etudiant extends Personne{
    private int matricule;

    public Etudiant(Personne personne, int matricule){
        this.setId(personne.getId());
        this.setName(personne.getName());
        this.matricule = matricule;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public Etudiant(){}
}
