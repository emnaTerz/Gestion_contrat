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
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // Lock

    private String editingUser;
    private LocalDateTime editingStart;

    // -------------------- NOUVEAU --------------------
    private LocalDateTime startTime;
    private List<SectionDTO> sections;

    public ContratDTO() {}

    // Getters & Setters existants

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
}
