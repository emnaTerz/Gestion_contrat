package com.emna.micro_service2.entities;


import com.emna.micro_service1.entities.Branche;
import com.emna.micro_service2.entities.enums.CodeRenouvellement;
import com.emna.micro_service2.entities.enums.Fractionnement;
import com.emna.micro_service2.entities.enums.TypeContrat;
import com.emna.micro_service2.entities.enums.TypeFranchise;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contrat_q")
public class ContratQ {

    @Id
    @Column(name = "numPolice", unique = true, nullable = false)
    private String numPolice;

    @Enumerated(EnumType.STRING)
    private Branche branche;

    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    private Integer service;

    private String codeAgence;

    private String status;

    private double primeTTC;

    private double primeNET;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private LocalDate dateCreation;

    private LocalDate dateModif;

    private String editingUser;

    private LocalDateTime editingStart;

    private String nature;

    @Enumerated(EnumType.STRING)
    private Fractionnement fractionnement;

    @Enumerated(EnumType.STRING)
    private CodeRenouvellement codeRenouvellement;

    @Column(name = "num_adherent")
    private String numAdherent;

    /* Franchise */
    @Enumerated(EnumType.STRING)
    private TypeFranchise franchise;
    // ABSOLUE, SUR_DOMMAGE, INEXISTANTE

    private Double taux;

    private Double min;

    private Double max;

    @Column(name = "nouveau")
    private boolean nouveau;

    private boolean leasing;

    private String compagnieLeasing;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String objetContrat;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String etendueGarantie;

    /* ===== Constructors ===== */

    public ContratQ() {
    }

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

    public double getPrimeTTC() {
        return primeTTC;
    }

    public void setPrimeTTC(double primeTTC) {
        this.primeTTC = primeTTC;
    }

    public double getPrimeNET() {
        return primeNET;
    }

    public void setPrimeNET(double primeNET) {
        this.primeNET = primeNET;
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

    public LocalDateTime getEditingStart() {
        return editingStart;
    }

    public void setEditingStart(LocalDateTime editingStart) {
        this.editingStart = editingStart;
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

    public String getNumAdherent() {
        return numAdherent;
    }

    public void setNumAdherent(String numAdherent) {
        this.numAdherent = numAdherent;
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

    public boolean isNouveau() {
        return nouveau;
    }

    public void setNouveau(boolean nouveau) {
        this.nouveau = nouveau;
    }

    public boolean isLeasing() {
        return leasing;
    }

    public void setLeasing(boolean leasing) {
        this.leasing = leasing;
    }

    public String getCompagnieLeasing() {
        return compagnieLeasing;
    }

    public void setCompagnieLeasing(String compagnieLeasing) {
        this.compagnieLeasing = compagnieLeasing;
    }

    public String getObjetContrat() {
        return objetContrat;
    }

    public void setObjetContrat(String objetContrat) {
        this.objetContrat = objetContrat;
    }

    public String getEtendueGarantie() {
        return etendueGarantie;
    }

    public void setEtendueGarantie(String etendueGarantie) {
        this.etendueGarantie = etendueGarantie;
    }
}

