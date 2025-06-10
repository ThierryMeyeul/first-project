package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.SpecialityDTO;
import Undertaker.HospiBook.dto.SpecialityUpdateDTO;
import Undertaker.HospiBook.model.entities.Speciality;
import Undertaker.HospiBook.model.entities.VisitFree;
import Undertaker.HospiBook.repository.SpecialityRepository;
import Undertaker.HospiBook.repository.VisitFreeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;
    private final VisitFreeRepository visitFreeRepository;

    public SpecialityService(SpecialityRepository specialityRepository, VisitFreeRepository visitFreeRepository) {
        this.specialityRepository = specialityRepository;
        this.visitFreeRepository = visitFreeRepository;
    }

    public Speciality create(SpecialityDTO specialityDTO){
        List<Speciality> specialities = this.specialityRepository.findByDeleted(false);

        if(specialities != null){
            specialities.forEach(speciality -> {
                if(Objects.equals(speciality.getName(), specialityDTO.getName())){
                    throw new RuntimeException("Speciality already exists");
                }
            });
            VisitFree visitFree = new VisitFree(specialityDTO.getAmount(), specialityDTO.getReduction());
            Speciality speciality = new Speciality(specialityDTO.getName(), specialityDTO.getDescription(), visitFree);
            this.visitFreeRepository.save(visitFree);
            return this.specialityRepository.save(speciality);
        }
        return null;
    }

    public List<Speciality> read() {
        return this.specialityRepository.findByDeleted(false);
    }

    public Speciality readName(String name) {
        List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
        if (specialities != null) {
            AtomicReference<Speciality> speciality1;
            speciality1 = new AtomicReference<>(new Speciality());
            specialities.forEach(speciality -> {
                if(Objects.equals(speciality.getName(), name)){
                    System.out.println(speciality.getName() + " et " + name);
                    speciality1.set(speciality);
                }
            });
            return speciality1.get();
        }
        return null;
    }

    public Speciality update(String name, SpecialityUpdateDTO specialityUpdateDTO) {
        Speciality speciality = this.readName(name);
        if(speciality == null){
            return null;
        }else {
            List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
            speciality.setName("faux nom");
            if(specialities != null){
                specialities.forEach(speciality2 -> {
                    if(Objects.equals(speciality2.getName(), specialityUpdateDTO.getName())){
                        throw  new RuntimeException("speciality already exists");
                    }
                });
                speciality.setName(specialityUpdateDTO.getName());
                speciality.setDescription(specialityUpdateDTO.getDescription());
                VisitFree visitFree = speciality.getVisitFree();
                visitFree.setAmount(specialityUpdateDTO.getAmount());
                visitFree.setReduction(specialityUpdateDTO.getReduction());
                speciality.setVisitFree(visitFree);
                this.visitFreeRepository.save(visitFree);
                return this.specialityRepository.save(speciality);
            }
        }
        return null;
    }

    public String delete(String name) {
        Speciality speciality = this.readName(name);
        if(speciality != null){
            speciality.setDeleted(true);
            VisitFree visitFree = speciality.getVisitFree();
            visitFree.setDeleted(true);
            speciality.setVisitFree(visitFree);
            this.visitFreeRepository.save(visitFree);
            this.specialityRepository.save(speciality);
            return "speciality deleted successful";
        }else{
            return "speciality doesn't exists";
        }
    }
}
