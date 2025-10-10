package com.yassir.projet.gestion_employes.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float penalite;

    @Temporal(TemporalType.DATE)
    private Date dateAbsence;

    @ManyToOne
    @JoinColumn(name = "employe_cin")
    private Employe employe;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Float getPenalite() { return penalite; }
    public void setPenalite(Float penalite) { this.penalite = penalite; }

    public Date getDateAbsence() { return dateAbsence; }
    public void setDateAbsence(Date dateAbsence) { this.dateAbsence = dateAbsence; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
}
