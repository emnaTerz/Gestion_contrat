package com.emna.micro_service2.entities;
import jakarta.persistence.*;

// Classe Activité
@Entity
public class Activite {

    @Id
    private String id;

    private String nom;
    private double taux;
    // Constructeur complet
    public Activite(String id, String nom, double taux) {
        this.id = id;
        this.nom = nom;
        this.taux = taux;
    }

    public Activite() {} // Constructeur par défaut requis par JPA

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getTaux() { return taux; }
    public void setTaux(double taux) { this.taux = taux; }
}
