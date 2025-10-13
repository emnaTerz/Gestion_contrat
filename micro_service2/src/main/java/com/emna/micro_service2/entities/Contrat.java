package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contrat {

    @Id
    @Column(name = "numPolice", unique = true, nullable = false)
    private String numPolice;

    @ManyToOne
    @JoinColumn(name = "adherent_id")
    private Adherent adherent;

    private String Nom_assure;

    @Enumerated(EnumType.STRING)
    private Fractionnement fractionnement;

    @Enumerated(EnumType.STRING)
    private CodeRenouvellement codeRenouvellement;

    @Enumerated(EnumType.STRING)
    private Branche branche;

    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    private Integer service;
    private String codeAgence;

    private double primeTTC;
    private double primeNET;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String editingUser;
    private LocalDateTime editingStart;

    @Lob
    private String preambule;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contrat_numPolice", referencedColumnName = "numPolice")
    private List<RC_Exploitation> rcExploitations = new ArrayList<>();


    public Contrat() {}

    public Contrat(String numPolice, Adherent adherent, String nom_assure, Fractionnement fractionnement, CodeRenouvellement codeRenouvellement, Branche branche, TypeContrat typeContrat, Integer service, String codeAgence, double primeTTC, double primeNET, LocalDate dateDebut, LocalDate dateFin, String editingUser, LocalDateTime editingStart, String preambule, List<RC_Exploitation> rcExploitations) {
        this.numPolice = numPolice;
        this.adherent = adherent;
        Nom_assure = nom_assure;
        this.fractionnement = fractionnement;
        this.codeRenouvellement = codeRenouvellement;
        this.branche = branche;
        this.typeContrat = typeContrat;
        this.service = service;
        this.codeAgence = codeAgence;
        this.primeTTC = primeTTC;
        this.primeNET = primeNET;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.editingUser = editingUser;
        this.editingStart = editingStart;
        this.preambule = preambule;
        this.rcExploitations = rcExploitations;
    }

// Getter et Setter pour codeAgence


    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getNom_assure() {
        return Nom_assure;
    }

    public void setNom_assure(String nom_assure) {
        Nom_assure = nom_assure;
    }

    // Getters & Setters

    public String getPreambule() {
        return preambule;
    }

    public void setPreambule(String preambule) {
        this.preambule = preambule;
    }

    public String getNumPolice() { return numPolice; }
    public void setNumPolice(String numPolice) { this.numPolice = numPolice; }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public Fractionnement getFractionnement() { return fractionnement; }
    public void setFractionnement(Fractionnement fractionnement) { this.fractionnement = fractionnement; }

    public CodeRenouvellement getCodeRenouvellement() { return codeRenouvellement; }
    public void setCodeRenouvellement(CodeRenouvellement codeRenouvellement) { this.codeRenouvellement = codeRenouvellement; }

    public Branche getBranche() { return branche; }
    public void setBranche(Branche branche) { this.branche = branche; }

    public TypeContrat getTypeContrat() { return typeContrat; }
    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }
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
