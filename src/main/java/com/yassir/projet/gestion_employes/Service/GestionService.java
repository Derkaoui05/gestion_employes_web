package com.yassir.projet.gestion_employes.Service;

import com.yassir.projet.gestion_employes.Entity.*;
import com.yassir.projet.gestion_employes.Repository.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GestionService {

    private final EmployeRepository employeRepo;
    private final AvanceRepository avanceRepo;
    private final AbsenceRepository absenceRepo;

    public GestionService(EmployeRepository employeRepo, AvanceRepository avanceRepo, AbsenceRepository absenceRepo) {
        this.employeRepo = employeRepo;
        this.avanceRepo = avanceRepo;
        this.absenceRepo = absenceRepo;
    }

    // ----- Employés -----
    public List<Employe> getAllEmployes() { return employeRepo.findAll(); }
    public void saveEmploye(Employe e) { employeRepo.save(e); }
    public void deleteEmploye(String cin) { employeRepo.deleteById(cin); }

    // ----- Avances -----
    public void addAvance(Avance a) { avanceRepo.save(a); }
    public List<Avance> getAvancesByEmploye(Employe e) { return avanceRepo.findByEmployeAndDateAvanceBetween(e, new Date(0), new Date()); }

    // ----- Absences -----
    public void addAbsence(Absence a) { absenceRepo.save(a); }
    public List<Absence> getAbsencesByEmploye(Employe e) { return absenceRepo.findByEmployeAndDateAbsenceBetween(e, new Date(0), new Date()); }

    // ----- Salaire hebdomadaire (Jeudi à Jeudi) -----
    public Float calculerSalaireSemaine(Employe e, Date debut, Date fin) {
        Float totalAvance = avanceRepo.findByEmployeAndDateAvanceBetween(e, debut, fin)
                .stream().map(Avance::getMontant).reduce(0f, Float::sum);

        Float totalAbsence = absenceRepo.findByEmployeAndDateAbsenceBetween(e, debut, fin)
                .stream().map(Absence::getPenalite).reduce(0f, Float::sum);

        // Weekly payroll rule: compute from monthly salaire
        Float weeklyBase = (e.getSalaire() == null ? 0f : e.getSalaire() / 7f);
        // Advances are prepayments (subtract) and absences incur penalties (subtract)
        return weeklyBase - totalAvance - totalAbsence;
    }

    public int countAvancesBetween(Employe e, Date debut, Date fin) {
        return avanceRepo.findByEmployeAndDateAvanceBetween(e, debut, fin).size();
    }

    public int countAbsencesBetween(Employe e, Date debut, Date fin) {
        return absenceRepo.findByEmployeAndDateAbsenceBetween(e, debut, fin).size();
    }

    public Float totalAvancesBetween(Employe e, Date debut, Date fin) {
        return avanceRepo.findByEmployeAndDateAvanceBetween(e, debut, fin)
                .stream().map(Avance::getMontant).reduce(0f, Float::sum);
    }

    public Float totalAbsencePenalitesBetween(Employe e, Date debut, Date fin) {
        return absenceRepo.findByEmployeAndDateAbsenceBetween(e, debut, fin)
                .stream().map(Absence::getPenalite).reduce(0f, Float::sum);
    }

    public List<Avance> getAvancesBetween(Employe e, Date debut, Date fin) {
        return avanceRepo.findByEmployeAndDateAvanceBetween(e, debut, fin);
    }

    public List<Absence> getAbsencesBetween(Employe e, Date debut, Date fin) {
        return absenceRepo.findByEmployeAndDateAbsenceBetween(e, debut, fin);
    }

    // ----- Dates Jeudi à Jeudi -----
    public Date getLastThursday() {
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) { cal.add(Calendar.DAY_OF_WEEK, -1); }
        return cal.getTime();
    }

    public Date getNextThursday() {
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) { cal.add(Calendar.DAY_OF_WEEK, 1); }
        return cal.getTime();
    }
}