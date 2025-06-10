package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Speciality")
public class Speciality extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @OneToOne
    @JoinColumn(name = "VisitFreeId")
    private VisitFree visitFree;

    public Speciality(String name, String description, VisitFree visitFree) {
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.visitFree = visitFree;
    }

    public Speciality(){

    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "speciality")
    private List<Personnel> personnels;

    @ManyToMany(mappedBy = "specialities", fetch = FetchType.LAZY)
    private Set<Hospital> hospitals;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public VisitFree getVisitFree() {
        return visitFree;
    }

    public void setVisitFree(VisitFree visitFree) {
        this.visitFree = visitFree;
    }
}
