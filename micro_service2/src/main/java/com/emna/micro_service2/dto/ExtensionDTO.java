package com.emna.micro_service2.dto;

public class ExtensionDTO {
    private Long id;            // facultatif si c'est généré automatiquement
    private String titre;
    private String numPolice;   // lien vers le contrat
    private String texte;       // contenu de l'extension

    public ExtensionDTO() {}

    public ExtensionDTO(Long id, String titre, String numPolice, String texte) {
        this.id = id;
        this.titre = titre;
        this.numPolice = numPolice;
        this.texte = texte;
    }

    public ExtensionDTO(String titre, String numPolice, String texte) {
        this.titre = titre;
        this.numPolice = numPolice;
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
