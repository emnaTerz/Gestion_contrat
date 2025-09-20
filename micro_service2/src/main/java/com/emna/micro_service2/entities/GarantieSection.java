package com.emna.micro_service2.entities;

import jakarta.persistence.*;

@Entity
public class GarantieSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
    private Long franchise;

    @ManyToOne
    @JoinColumn(name = "sous_garantie_id")
    private SousGarantie sousGarantie;  // référence unique

    // Nouveaux attributs personnalisés
    private Double limite;
    private Double maximum;
    private Double minimum;
    private Double capitale;
    private Double primeNET;
    private Double primeTTC;

    public Double getPrimeTTC() {
        return primeTTC;
    }

    public void setPrimeTTC(Double primeTTC) {
        this.primeTTC = primeTTC;
    }

    public GarantieSection() {}


    public GarantieSection(Section section, Long franchise, SousGarantie sousGarantie, Double limite, Double maximum, Double minimum, Double capitale, Double primeNET, Double primeTTC) {
        this.section = section;
        this.franchise = franchise;
        this.sousGarantie = sousGarantie;
        this.limite = limite;
        this.maximum = maximum;
        this.minimum = minimum;
        this.capitale = capitale;
        this.primeNET = primeNET;
        this.primeTTC = primeTTC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Long getFranchise() {
        return franchise;
    }

    public void setFranchise(Long franchise) {
        this.franchise = franchise;
    }

    public SousGarantie getSousGarantie() {
        return sousGarantie;
    }

    public void setSousGarantie(SousGarantie sousGarantie) {
        this.sousGarantie = sousGarantie;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getCapitale() {
        return capitale;
    }

    public void setCapitale(Double capitale) {
        this.capitale = capitale;
    }

    public Double getPrimeNET() {
        return primeNET;
    }

    public void setPrimeNET(Double primeNET) {
        this.primeNET = primeNET;
    }
}
