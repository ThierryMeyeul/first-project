package Undertaker.HospiBook.dto;

import java.math.BigDecimal;

public class SpecialityDTO {
    private String name;
    private String description;
    private BigDecimal amount;
    private float reduction;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }

    public SpecialityDTO(String name, String description, BigDecimal amount, float reduction) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.reduction = reduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
