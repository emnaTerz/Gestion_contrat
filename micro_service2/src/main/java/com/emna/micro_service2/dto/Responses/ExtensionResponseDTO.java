package com.emna.micro_service2.dto.Responses;


public class ExtensionResponseDTO {
    private Long id;
    private String numPolice;
    private String titre;
    private String texte;

    public ExtensionResponseDTO() {}

    public ExtensionResponseDTO(Long id, String numPolice, String titre, String texte) {
        this.id = id;
        this.numPolice = numPolice;
        this.titre = titre;
        this.texte = texte;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumPolice() {
        return numPolice;
    }

    public void setNumPolice(String numPolice) {
        this.numPolice = numPolice;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
