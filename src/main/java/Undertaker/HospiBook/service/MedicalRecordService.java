package Undertaker.HospiBook.service;

import Undertaker.HospiBook.dto.MedicalRecordDTO;
import Undertaker.HospiBook.model.entities.MedicalRecord;
import Undertaker.HospiBook.model.entities.Visit;
import Undertaker.HospiBook.repository.MedicalRecordRepository;
import Undertaker.HospiBook.repository.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    /*private final MedicalRecordRepository medicalRecordRepository;
    private final VisitRepository visitRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, VisitRepository visitRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.visitRepository = visitRepository;
    }

    public MedicalRecord create(Long id, MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = new MedicalRecord(medicalRecordDTO.getWeight(), medicalRecordDTO.getHeight(), medicalRecordDTO.getBloodPressure(), medicalRecordDTO.getTemperature());
        Visit visit = this.visitRepository.findById(id).orElseThrow(() -> new RuntimeException("visit not found"));
        medicalRecord.setVisit(visit);
        return this.medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord> read() {
        return this.medicalRecordRepository.findAllByDeleted(false);
    }

    public MedicalRecord readById(Long id) {
        Visit visit = this.visitRepository.findById(id).orElseThrow(() -> new RuntimeException("visit not found"));

        return this.medicalRecordRepository.findByVisit(visit);
    }

    public MedicalRecord update(Long id, MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = this.readById(id);
        if(medicalRecord == null){
            return null;
        }
        medicalRecord.setWeight(medicalRecordDTO.getWeight());
        medicalRecord.setHeight(medicalRecordDTO.getHeight());
        medicalRecord.setBloodPresure(medicalRecordDTO.getBloodPressure());
        medicalRecord.setTemperature(medicalRecordDTO.getTemperature());
        return this.medicalRecordRepository.save(medicalRecord);
    }

    public String delete(Long id) {
        Optional<MedicalRecord> medicalRecord = this.medicalRecordRepository.findById(id);
        if(medicalRecord.isEmpty() || medicalRecord.get().isDeleted()){
            return "medical record doesn't exists";
        }
        medicalRecord.get().setDeleted(true);
        this.medicalRecordRepository.save(medicalRecord.get());
        return "medical record deleted successful";
    }

     */
}
