package com.emna.micro_service2.entities;

import jakarta.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identification;
    private String adresse;
    private String natureConstruction;
    private String contiguite;
    private String avoisinage;
    @ManyToOne
    @JoinColumn(name = "rc_exploitation_id")
    private RC_Exploitation rcExploitation;

    @ManyToOne
    @JoinColumn(name = "num_police", referencedColumnName = "numPolice")
    private Contrat contrat;

    public Section() {}

    public Section(String identification, String adresse, String natureConstruction,
                   String contiguite, String avoisinage, Contrat contrat) {
        this.identification = identification;
        this.adresse = adresse;
        this.natureConstruction = natureConstruction;
        this.contiguite = contiguite;
        this.avoisinage = avoisinage;
        this.contrat = contrat;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getNatureConstruction() { return natureConstruction; }
    public void setNatureConstruction(String natureConstruction) { this.natureConstruction = natureConstruction; }

    public String getContiguite() { return contiguite; }
    public void setContiguite(String contiguite) { this.contiguite = contiguite; }

    public String getAvoisinage() { return avoisinage; }
    public void setAvoisinage(String avoisinage) { this.avoisinage = avoisinage; }

    public Contrat getContrat() { return contrat; }
    public void setContrat(Contrat contrat) { this.contrat = contrat; }

    public RC_Exploitation getRcExploitation() {
        return rcExploitation;
    }

    public void setRcExploitation(RC_Exploitation rcExploitation) {
        this.rcExploitation = rcExploitation;
    }
}
