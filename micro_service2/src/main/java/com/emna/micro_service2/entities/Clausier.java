package com.emna.micro_service2.entities;



import jakarta.persistence.*;

@Entity
public class Clausier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    @Lob
    private byte[] file;


    public Clausier() {}

    public Clausier(String nom, byte[] file) {
        this.nom = nom;
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

}

