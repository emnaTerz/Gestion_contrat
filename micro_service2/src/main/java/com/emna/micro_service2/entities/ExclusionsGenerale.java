package com.emna.micro_service2.entities;

import com.emna.micro_service2.entities.enums.Branche;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exclusions_generale")
public class ExclusionsGenerale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Branche branche;

    @ElementCollection
    @CollectionTable(name = "exclusion_liste", joinColumns = @JoinColumn(name = "exclusions_generale_id"))
    @Column(name = "exclusion")
    private List<String> listeExclusion = new ArrayList<>();

    // Constructeurs
    public ExclusionsGenerale() {}

    public ExclusionsGenerale(Long id, Branche branche, List<String> listeExclusion) {
        this.id = id;
        this.branche = branche;
        this.listeExclusion = listeExclusion != null ? listeExclusion : new ArrayList<>();
    }

    public ExclusionsGenerale(Long id, Branche branche) {
        this(id, branche, new ArrayList<>());
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Branche getBranche() { return branche; }
    public void setBranche(Branche branche) { this.branche = branche; }

    public List<String> getListeExclusion() { return listeExclusion; }
    public void setListeExclusion(List<String> listeExclusion) {
        this.listeExclusion = listeExclusion != null ? listeExclusion : new ArrayList<>();
    }

    // Méthodes utilitaires
    public void ajouterExclusion(String exclusion) {
        if (exclusion != null && !exclusion.trim().isEmpty()) {
            this.listeExclusion.add(exclusion.trim());
        }
    }

    public void ajouterExclusions(List<String> exclusions) {
        if (exclusions != null) {
            exclusions.forEach(this::ajouterExclusion);
        }
    }

    public boolean supprimerExclusion(String exclusion) {
        return this.listeExclusion.remove(exclusion);
    }

    public void supprimerExclusionParIndex(int index) {
        if (index >= 0 && index < listeExclusion.size()) {
            this.listeExclusion.remove(index);
        }
    }

    public void viderExclusions() { this.listeExclusion.clear(); }

    public boolean contientExclusion(String exclusion) { return this.listeExclusion.contains(exclusion); }

    public int getNombreExclusions() { return this.listeExclusion.size(); }

    public boolean hasExclusions() { return !this.listeExclusion.isEmpty(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExclusionsGenerale that = (ExclusionsGenerale) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ExclusionsGenerale{" +
                "id=" + id +
                ", branche=" + branche +
                ", listeExclusion=" + listeExclusion +
                '}';
    }
}
