package com.emna.micro_service2.dto.Responses;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.emna.micro_service2.entities.enums.*;

public class ContratResponseDTO {
    private String numPolice;
    private AdherentResponseDTO adherent;
    private Fractionnement fractionnement;
    private CodeRenouvellement codeRenouvellement;
    private Branche branche;
    private String Nom_assure;
    private TypeContrat typeContrat;
    private String preambule;
    private double primeTTC;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String editingUser;
    private LocalDateTime editingStart;
    private List<SectionResponseDTO> sections;
    private String codeAgence;
    private List<RcConfigurationResponseDTO> rcConfigurations;


    public ContratResponseDTO() {}

    // Getters & Setters

    public List<RcConfigurationResponseDTO> getRcConfigurations() {
        return rcConfigurations;
    }

    public void setRcConfigurations(List<RcConfigurationResponseDTO> rcConfigurations) {
        this.rcConfigurations = rcConfigurations;
    }

    public String getNom_assure() {
        return Nom_assure;
    }

    public void setNom_assure(String nom_assure) {
        Nom_assure = nom_assure;
    }

    public String getNumPolice() { return numPolice; }
    public void setNumPolice(String numPolice) { this.numPolice = numPolice; }

    public AdherentResponseDTO getAdherent() { return adherent; }
    public void setAdherent(AdherentResponseDTO adherent) { this.adherent = adherent; }

    public Fractionnement getFractionnement() { return fractionnement; }
    public void setFractionnement(Fractionnement fractionnement) { this.fractionnement = fractionnement; }

    public CodeRenouvellement getCodeRenouvellement() { return codeRenouvellement; }
    public void setCodeRenouvellement(CodeRenouvellement codeRenouvellement) { this.codeRenouvellement = codeRenouvellement; }

    public Branche getBranche() { return branche; }
    public void setBranche(Branche branche) { this.branche = branche; }

    public TypeContrat getTypeContrat() { return typeContrat; }
    public void setTypeContrat(TypeContrat typeContrat) { this.typeContrat = typeContrat; }

    public String getPreambule() {
        return preambule;
    }

    public void setPreambule(String preambule) {
        this.preambule = preambule;
    }

    public double getPrimeTTC() { return primeTTC; }
    public void setPrimeTTC(double primeTTC) { this.primeTTC = primeTTC; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getEditingUser() { return editingUser; }
    public void setEditingUser(String editingUser) { this.editingUser = editingUser; }

    public LocalDateTime getEditingStart() { return editingStart; }
    public void setEditingStart(LocalDateTime editingStart) { this.editingStart = editingStart; }

    public List<SectionResponseDTO> getSections() { return sections; }
    public void setSections(List<SectionResponseDTO> sections) { this.sections = sections; }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }
}
