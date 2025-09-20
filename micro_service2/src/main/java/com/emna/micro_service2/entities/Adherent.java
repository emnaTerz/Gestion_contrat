package com.emna.micro_service2.entities;
import jakarta.persistence.*;

// Classe Adhérent
@Entity
public class Adherent {

    @Id
    private String codeId;         // Code identifiant de l'adhérent (non auto-généré)

    private String nomRaison;      // Nom ou raison sociale
    private String adresse;        // Adresse


    private String activite;

    // Constructeur complet
    public Adherent(String codeId, String nomRaison, String adresse, String activite) {
        this.codeId = codeId;
        this.nomRaison = nomRaison;
        this.adresse = adresse;
        this.activite = activite;
    }

    public Adherent() {} // Constructeur par défaut requis par JPA

    // Getters et Setters
    public String getCodeId() { return codeId; }
    public void setCodeId(String codeId) { this.codeId = codeId; }

    public String getNomRaison() { return nomRaison; }
    public void setNomRaison(String nomRaison) { this.nomRaison = nomRaison; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
}
