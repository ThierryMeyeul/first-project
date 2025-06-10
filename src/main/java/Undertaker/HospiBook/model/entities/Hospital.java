package Undertaker.HospiBook.model.entities;

import Undertaker.HospiBook.model.audit.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Hospital")
public class Hospital extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String website;
    private String logoPath;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] logo;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String localisation;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private String phone;

    @Version
    private Long version = 0L;

    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "hospital")
    private List<Personnel> personnels;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SpecialitiesHospital", joinColumns = @JoinColumn(name = "hospitalId"), inverseJoinColumns = @JoinColumn(name = "specialityId"))
    private Set<Speciality> specialities;

    @OneToMany(mappedBy = "hospital")
    private List<Visit> visits;

    public Long getId() {
        return id;
    }

    public Hospital(String name, String address, String localisation, Date creationDate, String phone, boolean deleted) {
        this.name = name;
        this.address = address;
        this.localisation = localisation;
        this.creationDate = creationDate;
        this.phone = phone;
        this.deleted = deleted;
    }

    public Hospital() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
