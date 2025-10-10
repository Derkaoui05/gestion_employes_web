package com.yassir.projet.gestion_employes.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Avance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float montant;

    @Temporal(TemporalType.DATE)
    private Date dateAvance;

    @ManyToOne
    @JoinColumn(name = "employe_cin")
    private Employe employe;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Float getMontant() { return montant; }
    public void setMontant(Float montant) { this.montant = montant; }

    public Date getDateAvance() { return dateAvance; }
    public void setDateAvance(Date dateAvance) { this.dateAvance = dateAvance; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
}