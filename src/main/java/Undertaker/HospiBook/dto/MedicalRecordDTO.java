package Undertaker.HospiBook.dto;

public class MedicalRecordDTO {
    private float weight;
    private float height;
    private String bloodPressure;
    private float temperature;

    public MedicalRecordDTO(float weight, float height, String bloodPressure, float temperature) {
        this.weight = weight;
        this.height = height;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
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

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
