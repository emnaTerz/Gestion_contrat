package com.emna.micro_service2.dto;

import com.emna.micro_service2.entities.enums.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ContratDTO {
    private String numPolice;
    private AdherentDTO adherent;
    private Fractionnement fractionnement;
    private String Nom_assure;
    private CodeRenouvellement codeRenouvellement;
    private Branche branche;
    private TypeContrat typeContrat;
    private double primeTTC;
    private Integer service;
    private double primeNET;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String preambule;
    private List<RcConfigurationDTO> rcConfigurations;

    // Lock

    private String editingUser;
    private LocalDateTime editingStart;

    // -------------------- NOUVEAU --------------------
    private LocalDateTime startTime;
    private List<SectionDTO> sections;
    private String codeAgence;
    // -------------------- RC EXPLOITATION --------------------


    public ContratDTO() {}

    public ContratDTO(String numPolice, AdherentDTO adherent, Fractionnement fractionnement, String nom_assure, CodeRenouvellement codeRenouvellement, Branche branche, TypeContrat typeContrat, double primeTTC, Integer service, double primeNET, LocalDate dateDebut, LocalDate dateFin, String preambule, List<RcConfigurationDTO> rcConfigurations, String editingUser, LocalDateTime editingStart, LocalDateTime startTime, List<SectionDTO> sections, String codeAgence) {
        this.numPolice = numPolice;
        this.adherent = adherent;
        this.fractionnement = fractionnement;
        Nom_assure = nom_assure;
        this.codeRenouvellement = codeRenouvellement;
        this.branche = branche;
        this.typeContrat = typeContrat;
        this.primeTTC = primeTTC;
        this.service = service;
        this.primeNET = primeNET;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.preambule = preambule;
        this.rcConfigurations = rcConfigurations;
        this.editingUser = editingUser;
        this.editingStart = editingStart;
        this.startTime = startTime;
        this.sections = sections;
        this.codeAgence = codeAgence;
    }

    public ContratDTO(String numPolice, AdherentDTO adherentDTO, Fractionnement fractionnement, String nomAssure, CodeRenouvellement codeRenouvellement, Branche branche,
                      TypeContrat typeContrat, double primeTTC, LocalDate dateDebut, LocalDate dateFin, String preambule, String editingUser, LocalDateTime editingStart) {

    }

    // Getters & Setters existants

    public String getPreambule() {
        return preambule;
    }

    public void setPreambule(String preambule) {
        this.preambule = preambule;
    }

    public String getNom_assure() {
        return Nom_assure;
    }

    public void setNom_assure(String nom_assure) {
        Nom_assure = nom_assure;
    }

    public String getNumPolice() { return numPolice; }
    public void setNumPolice(String numPolice) { this.numPolice = numPolice; }
    public AdherentDTO getAdherent() { return adherent; }
    public void setAdherent(AdherentDTO adherent) { this.adherent = adherent; }
    public Fractionnement getFractionnement() { return fractionnement; }
    public void setFractionnement(Fractionnement fractionnement) { this.fractionnement = fractionnement; }
    public CodeRenouvellement getCodeRenouvellement() { return codeRenouvellement; }
    public void setCodeRenouvellement(CodeRenouvellement codeRenouvellement) { this.codeRenouvellement = codeRenouvellement; }
    public Branche getBranche() { return branche; }
    public void setBranche(Branche branche) { this.branche = branche; }
    public TypeContrat getTypeContrat() { return typeContrat; }
    public void setTypeContrat(TypeContrat typeContrat) { this.typeContrat = typeContrat; }

    public double getPrimeTTC() { return primeTTC; }
    public void setPrimeTTC(double primeTTC) { this.primeTTC = primeTTC; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public String getEditingUser() { return editingUser; }
    public void setEditingUser(String editingUser) { this.editingUser = editingUser; }
    public LocalDateTime getEditingStart() { return editingStart; }
    public void setEditingStart(LocalDateTime editingStart) { this.editingStart = editingStart; }

    // -------------------- GETTERS/SETTERS SECTIONS --------------------
    public List<SectionDTO> getSections() { return sections; }
    public void setSections(List<SectionDTO> sections) { this.sections = sections; }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public List<RcConfigurationDTO> getRcConfigurations() {
        return rcConfigurations;
    }

    public void setRcConfigurations(List<RcConfigurationDTO> rcConfigurations) {
        this.rcConfigurations = rcConfigurations;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public double getPrimeNET() {
        return primeNET;
    }

    public void setPrimeNET(double primeNET) {
        this.primeNET = primeNET;
    }
}
