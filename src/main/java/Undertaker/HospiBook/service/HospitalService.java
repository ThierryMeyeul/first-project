package Undertaker.HospiBook.service;

import Undertaker.HospiBook.model.entities.Hospital;
import Undertaker.HospiBook.model.entities.Speciality;
import Undertaker.HospiBook.repository.HospitalRepository;
import Undertaker.HospiBook.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final SpecialityRepository specialityRepository;

    public HospitalService(HospitalRepository hospitalRepository, SpecialityRepository specialityRepository) {
        this.hospitalRepository = hospitalRepository;
        this.specialityRepository = specialityRepository;
    }

    public Hospital create(Hospital hospital) {
         List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
         if(hospitals != null){
             hospitals.forEach(hospital1 -> {
                 if(Objects.equals(hospital1.getName(), hospital.getName())){
                     throw new RuntimeException("hospital is already exists");
                 }
             });
             return this.hospitalRepository.save(hospital);
         }
        return null;
    }

    public List<Hospital> read() {
        return this.hospitalRepository.findByDeleted(false);
    }

    public Hospital readId(Long id) {
        Optional<Hospital> optionalHospital = this.hospitalRepository.findById(id);
        if(optionalHospital.isPresent()){
            if(!optionalHospital.get().isDeleted()){
                return optionalHospital.get();
            }else{
                return null;
            }
        }
        return null;
    }

    public String delete(Long id) {
        Optional<Hospital> optionalHospital = this.hospitalRepository.findById(id);
        if (optionalHospital.isPresent()){
            optionalHospital.get().setDeleted(true);
            this.hospitalRepository.save(optionalHospital.get());
            return "Hospital deleted successful";
        }
        return "Hospital doesn't exists";
    }

    public Hospital update(Long id, Hospital hospital) {
        Hospital hospitalisationBDD = this.readId(id);
        if (hospitalisationBDD != null) {
            List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
            hospitalisationBDD.setName("faux nom");
            if(hospitals != null) {
                hospitals.forEach(hospital1 -> {
                    if (Objects.equals(hospital1.getName(), hospital.getName())) {
                        throw new RuntimeException("hospital is already exists");
                    }
                });
                hospitalisationBDD.setName(hospital.getName());
                hospitalisationBDD.setPhone(hospital.getPhone());
                hospitalisationBDD.setAddress(hospital.getAddress());
                hospitalisationBDD.setLocalisation(hospital.getLocalisation());
                hospitalisationBDD.setCreationDate(hospital.getCreationDate());
                return this.hospitalRepository.save(hospitalisationBDD);
            }
        }
        return null;
    }

    public Hospital addSpeciality(String name, String speciality) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        if(hospitals != null){
            hospitals.forEach(hospital1 -> {
                if(Objects.equals(hospital1.getName(), name)){
                    hospital.set(hospital1);
                }
            });

            List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
            AtomicReference<Speciality> speciality1;
            speciality1 = new AtomicReference<x(new Speciality());
            if (specialities != null) {
                specialities.forEach(speciality2 -> {
                    if (Objects.equals(speciality2.getName(), speciality)){
                        speciality1.set(speciality2);
                    }
                });

                if (hospital.get().getName() == null || speciality1.get().getName() == null){
                    throw new RuntimeException("speciality or hospital not found");
                }
                Set<Speciality> specialitySet = new HashSet<>();
                specialitySet = hospital.get().getSpecialities();
                specialitySet.add(speciality1.get());
                hospital.get().setSpecialities(specialitySet);
                return this.hospitalRepository.save(hospital.get());
            }
            return null;
        }
        return null;
    }

    public Set<Speciality> getSpecialities(String name) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        if(hospitals != null) {
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), name)) {
                    hospital.set(hospital1);
                }
            });
            return hospital.get().getSpecialities();
        }
        return null;
    }

    public String deleteSpeciality(String name, String speciality) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        if(hospitals != null){
            hospitals.forEach(hospital1 -> {
                if(Objects.equals(hospital1.getName(), name)){
                    hospital.set(hospital1);
                }
            });

            List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
            AtomicReference<Speciality> speciality1;
            speciality1 = new AtomicReference<>(new Speciality());
            if (specialities != null) {
                specialities.forEach(speciality2 -> {
                    if (Objects.equals(speciality2.getName(), speciality)){
                        speciality1.set(speciality2);
                    }
                });

                if (hospital.get().getName() == null || speciality1.get().getName() == null){
                    throw new RuntimeException("speciality or hospital not found");
                }
                Set<Speciality> specialitySet = new HashSet<>();
                specialitySet = hospital.get().getSpecialities();
                specialitySet.remove(speciality1.get());
                hospital.get().setSpecialities(specialitySet);
                this.hospitalRepository.save(hospital.get());
                return "deleted successful";
            }
            return null;
        }
        return null;
    }

    public Hospital readByName(String name) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        hospitals.forEach(hospital1 -> {
            if (Objects.equals(hospital1.getName(), name)){
                hospital.set(hospital1);
            }

        });
        if (hospital.get().getName() == null){
            return null;
        }
        return hospital.get();
    }

    public List<Hospital> readBySpeciality(String speciality) {
        /*List<Speciality> specialities = this.specialityRepository.findByDeleted(false);
        AtomicReference<Speciality> speciality1;
        speciality1 = new AtomicReference<>(new Speciality());
        specialities.forEach(speciality2 -> {
            if (Objects.equals(speciality2.getName(), speciality)) {
                speciality1.set(speciality2);
            }
        });
        if (speciality1.get().getName() == null){
            throw new RuntimeException("Speciality not found");
        }
        Set<Speciality> specialitySet = new HashSet<>();
        specialitySet.add(speciality1.get());
        List<Hospital> hospitals = this.hospitalRepository.findBySpeciality(specialitySet);
        AtomicReference<List<Hospital>> hospitalList = new AtomicReference<>(new ArrayList<>());
        hospitals.forEach(hospital -> {
            if (!hospital.isDeleted()){
                hospitalList.get().add(hospital);
            }
        });
        return hospitalList.get();*/

        return this.hospitalRepository.findBySpecialityNameContaining(speciality);
    }

    @Value("${upload.directory}")
    private String uploadDirectory;

    /*public Hospital updateHospital(Long id, Hospital hospital, MultipartFile logo) throws IOException {
        if (logo != null && !logo.isEmpty()) {
            String logoPath = saveLogo(logo);
            hospital.setLogoPath(logoPath);
        }
        Hospital hospitalisationBDD = this.readId(id);
        if (hospitalisationBDD != null) {
            List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
            hospitalisationBDD.setName("faux nom");
            if(hospitals != null) {
                hospitals.forEach(hospital1 -> {
                    if (Objects.equals(hospital1.getName(), hospital.getName())) {
                        throw new RuntimeException("hospital is already exists");
                    }
                });
                hospitalisationBDD.setName(hospital.getName());
                hospitalisationBDD.setPhone(hospital.getPhone());
                hospitalisationBDD.setAddress(hospital.getAddress());
                hospitalisationBDD.setLocalisation(hospital.getLocalisation());
                hospitalisationBDD.setCreationDate(hospital.getCreationDate());
                hospitalisationBDD.setLogoPath(hospital.getLogoPath());
                return this.hospitalRepository.save(hospitalisationBDD);
            }
        }
        return null;
    }

    private String saveLogo(MultipartFile logo) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID().toString() + "_"  + logo.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(logo.getInputStream(), filePath);

        return fileName;
    }
     */

    public Hospital updateLogo(String name, MultipartFile logo) throws IOException {

        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        if(hospitals != null) {
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), name)) {
                    hospital.set(hospital1);
                }
            });
            if (logo != null && !logo.isEmpty()) {
                hospital.get().setLogo(logo.getBytes());
                hospital.get().setLogoPath(logo.getContentType());
            }
            return this.hospitalRepository.save(hospital.get());
        }
        return null;
    }


    public Hospital updateWebsite(String name, String website) {
        List<Hospital> hospitals = this.hospitalRepository.findByDeleted(false);
        AtomicReference<Hospital> hospital;
        hospital = new AtomicReference<>(new Hospital());
        if(hospitals != null) {
            hospitals.forEach(hospital1 -> {
                if (Objects.equals(hospital1.getName(), name)) {
                    hospital.set(hospital1);
                }
            });
            hospital.get().setWebsite(website);
            return this.hospitalRepository.save(hospital.get());
        }
        return null;
    }
}
