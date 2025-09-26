package com.emna.micro_service2.dto.Responses;



public class ExclusionRCResponse {
    private Long id;
    private String nom;

    // Constructeurs
    public ExclusionRCResponse() {}

    public ExclusionRCResponse(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
