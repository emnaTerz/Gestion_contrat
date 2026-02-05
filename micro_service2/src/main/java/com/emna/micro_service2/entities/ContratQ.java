package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contrat_q")
public class ContratQ {

    /* ===== Identifiant ===== */
    @Id
    @Column(name = "numPolice", unique = true, nullable = false)
    private String numPolice;

    /* ===== Informations générales ===== */
    @Enumerated(EnumType.STRING)
    private Branche branche;

    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    private Integer service;

    private String codeAgence;

    private String status;

    private LocalDate dateCreation;

    private LocalDate dateModif;

    private String editingUser;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String nature;

    @Enumerated(EnumType.STRING)
    private Fractionnement fractionnement;

    @Enumerated(EnumType.STRING)
    private CodeRenouvellement codeRenouvellement;

    /* ===== Relations ===== */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "adherent_id")
    private Adherent adherent;

    @ManyToMany
    @JoinTable(
            name = "contrat_exclusion",
            joinColumns = @JoinColumn(name = "contrat_id"),
            inverseJoinColumns = @JoinColumn(name = "exclusion_id")
    )
    private List<ExclusionGlobale> exclusionsGlobales;




    /* ===== Dates supplémentaires ===== */
    private LocalDateTime creationDate;
    private LocalDate dateOffre;

    /* ===== Franchise ===== */
    @Enumerated(EnumType.STRING)
    private TypeFranchise franchise; // ABSOLUE, SUR_DOMMAGE, INEXISTANTE

    private Double taux;
    private Double min;
    private Double max;

    /* ===== Constructeurs ===== */
    public ContratQ() {}

    /* ===== Getters & Setters ===== */
    public String getNumPolice() {
        return numPolice;
    }

    public void setNumPolice(String numPolice) {
        this.numPolice = numPolice;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModif() {
        return dateModif;
    }

    public void setDateModif(LocalDate dateModif) {
        this.dateModif = dateModif;
    }

    public String getEditingUser() {
        return editingUser;
    }

    public void setEditingUser(String editingUser) {
        this.editingUser = editingUser;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Fractionnement getFractionnement() {
        return fractionnement;
    }

    public void setFractionnement(Fractionnement fractionnement) {
        this.fractionnement = fractionnement;
    }

    public CodeRenouvellement getCodeRenouvellement() {
        return codeRenouvellement;
    }

    public void setCodeRenouvellement(CodeRenouvellement codeRenouvellement) {
        this.codeRenouvellement = codeRenouvellement;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public List<ExclusionGlobale> getExclusionsGlobales() {
        return exclusionsGlobales;
    }

    public void setExclusionsGlobales(List<ExclusionGlobale> exclusionsGlobales) {
        this.exclusionsGlobales = exclusionsGlobales;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDateOffre() {
        return dateOffre;
    }

    public void setDateOffre(LocalDate dateOffre) {
        this.dateOffre = dateOffre;
    }

    public TypeFranchise getFranchise() {
        return franchise;
    }

    public void setFranchise(TypeFranchise franchise) {
        this.franchise = franchise;
    }

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
