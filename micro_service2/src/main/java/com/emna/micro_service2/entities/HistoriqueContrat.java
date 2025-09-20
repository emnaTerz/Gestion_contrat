package com.emna.micro_service2.entities;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoriqueContrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;       // Ex : "Création contrat POL123"
    private String username;     // User qui a effectué l'action
    private LocalDateTime date;  // Date de l'action
    private Long tempsRealisation; // Durée en ms ou s

    public HistoriqueContrat() {}

    public HistoriqueContrat(String action, String username, LocalDateTime date, Long tempsRealisation) {
        this.action = action;
        this.username = username;
        this.date = date;
        this.tempsRealisation = tempsRealisation;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Long getTempsRealisation() { return tempsRealisation; }
    public void setTempsRealisation(Long tempsRealisation) { this.tempsRealisation = tempsRealisation; }
}
