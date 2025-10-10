package com.yassir.projet.gestion_employes.Repository;

import com.yassir.projet.gestion_employes.Entity.Absence;
import com.yassir.projet.gestion_employes.Entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEmployeAndDateAbsenceBetween(Employe e, Date start, Date end);
}