package com.yassir.projet.gestion_employes.Service;
import org.springframework.stereotype.Service;
import java.util.*;
import com.yassir.projet.gestion_employes.Entity.*;
import com.yassir.projet.gestion_employes.Repository.*;


@Service
public class RapportService {
    private final EmployeRepository employeRepo;
    private final AvanceRepository avanceRepo;
    private final AbsenceRepository absenceRepo;

    public RapportService(EmployeRepository employeRepo, AvanceRepository avanceRepo, AbsenceRepository absenceRepo) {
        this.employeRepo = employeRepo;
        this.avanceRepo = avanceRepo;
        this.absenceRepo = absenceRepo;
    }

    public Float calculerSalaireSemaine(String cin, Date debut, Date fin) {
        Employe e = employeRepo.findById(cin).orElse(null);
        if (e == null) return 0f;

        Float totalAvance = avanceRepo.findByEmployeAndDateAvanceBetween(e, debut, fin)
                .stream().map(Avance::getMontant).reduce(0f, Float::sum);

        Float totalAbsence = absenceRepo.findByEmployeAndDateAbsenceBetween(e, debut, fin)
                .stream().map(Absence::getPenalite).reduce(0f, Float::sum);

        // Weekly payroll rule: compute from monthly salaire
        Float weeklyBase = (e.getSalaire() == null ? 0f : e.getSalaire() / 7f);
        // Align with GestionService: avances and absences both reduce the payable amount
        return weeklyBase - totalAvance - totalAbsence;
    }
}
