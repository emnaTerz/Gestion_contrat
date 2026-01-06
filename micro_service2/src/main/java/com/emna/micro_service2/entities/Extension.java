package com.emna.micro_service2.entities;

import jakarta.persistence.*;

@Entity
public class Extension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numPolice;
    private String titre;
    @Lob
    private String texte;

    // Constructeurs
    public Extension() {}

    public Extension(String numPolice, String titre, String texte) {
        this.numPolice = numPolice;
        this.titre = titre;
        this.texte = texte;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumPolice() { return numPolice; }
    public void setNumPolice(String numPolice) { this.numPolice = numPolice; }

    public String getTexte() { return texte; }
    public void setTexte(String texte) { this.texte = texte; }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
