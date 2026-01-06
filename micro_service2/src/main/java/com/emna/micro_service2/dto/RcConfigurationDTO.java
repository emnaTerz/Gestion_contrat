package com.emna.micro_service2.dto;

import java.util.List;

public class RcConfigurationDTO {
    private Long id;
    private Double limiteAnnuelleDomCorporels;
    private Double limiteAnnuelleDomMateriels;
    private Double limiteParSinistreCorporels;
    private Double limiteParSinistreMateriels;

    private Double franchise;
    private Double maximum;
    private Double minimum;
    private Double primeNET;
    private String objetDeLaGarantie;
    private List<Long> exclusionsRcIds;
    private List<Long> sectionIds; // ✅ IDs des sections qui utilisent cette configuration
    private List<String> sectionIdentifications; // Noms des sections pour l'affichage


    public RcConfigurationDTO(Long id, Double limiteAnnuelleDomCorporels, Double limiteAnnuelleDomMateriels, Double limiteParSinistreCorporels, Double limiteParSinistreMateriels, Double franchise, Double maximum, Double minimum, Double primeNET, String objetDeLaGarantie, List<Long> exclusionsRcIds, List<Long> sectionIds) {
        this.id = id;
        this.limiteAnnuelleDomCorporels = limiteAnnuelleDomCorporels;
        this.limiteAnnuelleDomMateriels = limiteAnnuelleDomMateriels;
        this.limiteParSinistreCorporels = limiteParSinistreCorporels;
        this.limiteParSinistreMateriels = limiteParSinistreMateriels;
        this.franchise = franchise;
        this.maximum = maximum;
        this.minimum = minimum;
        this.primeNET = primeNET;
        this.objetDeLaGarantie = objetDeLaGarantie;
        this.exclusionsRcIds = exclusionsRcIds;
        this.sectionIds = sectionIds;
    }



    public Long getId() {
        return id;
    }

    public List<Long> getSectionIds() {
        return sectionIds;
    }

    public List<String> getSectionIdentifications() {
        return sectionIdentifications;
    }

    public void setSectionIdentifications(List<String> sectionIdentifications) {
        this.sectionIdentifications = sectionIdentifications;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimiteAnnuelleDomCorporels() {
        return limiteAnnuelleDomCorporels;
    }

    public void setLimiteAnnuelleDomCorporels(Double limiteAnnuelleDomCorporels) {
        this.limiteAnnuelleDomCorporels = limiteAnnuelleDomCorporels;
    }

    public Double getLimiteAnnuelleDomMateriels() {
        return limiteAnnuelleDomMateriels;
    }

    public void setLimiteAnnuelleDomMateriels(Double limiteAnnuelleDomMateriels) {
        this.limiteAnnuelleDomMateriels = limiteAnnuelleDomMateriels;
    }

    public Double getLimiteParSinistreCorporels() {
        return limiteParSinistreCorporels;
    }

    public void setLimiteParSinistreCorporels(Double limiteParSinistreCorporels) {
        this.limiteParSinistreCorporels = limiteParSinistreCorporels;
    }

    public Double getLimiteParSinistreMateriels() {
        return limiteParSinistreMateriels;
    }

    public void setLimiteParSinistreMateriels(Double limiteParSinistreMateriels) {
        this.limiteParSinistreMateriels = limiteParSinistreMateriels;
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

    public Double getFranchise() {
        return franchise;
    }

    public void setFranchise(Double franchise) {
        this.franchise = franchise;
    }

    public Double getPrimeNET() {
        return primeNET;
    }

    public void setPrimeNET(Double primeNET) {
        this.primeNET = primeNET;
    }

    public String getObjetDeLaGarantie() {
        return objetDeLaGarantie;
    }

    public void setObjetDeLaGarantie(String objetDeLaGarantie) {
        this.objetDeLaGarantie = objetDeLaGarantie;
    }

    public List<Long> getExclusionsRcIds() {
        return exclusionsRcIds;
    }

    public void setExclusionsRcIds(List<Long> exclusionsRcIds) {
        this.exclusionsRcIds = exclusionsRcIds;
    }

    public List<Long> sectionIds() {
        return sectionIds;
    }

    public void setSectionIds(List<Long> sectionIds) {
        this.sectionIds = sectionIds;
    }
}
