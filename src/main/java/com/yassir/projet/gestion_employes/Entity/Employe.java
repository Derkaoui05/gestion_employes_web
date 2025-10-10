package com.yassir.projet.gestion_employes.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Employe {
    @Id
    private String cin;
    private String nom;
    private String prenom;
    private Float salaire;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avance> avances;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Absence> absences;

    // Getters et Setters
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public Float getSalaire() { return salaire; }
    public void setSalaire(Float salaire) { this.salaire = salaire; }

    public List<Avance> getAvances() { return avances; }
    public void setAvances(List<Avance> avances) { this.avances = avances; }

    public List<Absence> getAbsences() { return absences; }
    public void setAbsences(List<Absence> absences) { this.absences = absences; }
}