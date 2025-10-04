package com.emna.micro_service2.dto;


import java.util.List;

public class ExclusionsRequestDTO {
    private List<String> listeExclusion;

    public List<String> getListeExclusion() { return listeExclusion; }
    public void setListeExclusion(List<String> listeExclusion) { this.listeExclusion = listeExclusion; }
}
