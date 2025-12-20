package com.emna.micro_service2.dto;

import java.time.LocalDateTime;

public class HistoriqueContratDTO {
    private String action;
    private LocalDateTime startDate;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
