package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.entities.Speciality;
import Undertaker.HospiBook.model.entities.VisitFree;

import java.util.List;

public class SpecialityReadDTO {
    private List<Speciality> specialities;
    private List<VisitFree> visitFrees;

    public SpecialityReadDTO(List<Speciality> specialities, List<VisitFree> visitFrees) {
        this.specialities = specialities;
        this.visitFrees = visitFrees;
    }
}
