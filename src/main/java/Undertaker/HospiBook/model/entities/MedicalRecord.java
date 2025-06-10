package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "MedicalRecord")
public class MedicalRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float weight;

    @Column(nullable = false)
    private float height;

    @Column(nullable = false)
    private String bloodPresure;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private boolean deleted;

    @Version
    private Long version;

    public MedicalRecord(float weight, float height, String bloodPresure, float temperature) {
        this.weight = weight;
        this.height = height;
        this.bloodPresure = bloodPresure;
        this.temperature = temperature;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getBloodPresure() {
        return bloodPresure;
    }

    public void setBloodPresure(String bloodPresure) {
        this.bloodPresure = bloodPresure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public MedicalRecord() {
    }
}
