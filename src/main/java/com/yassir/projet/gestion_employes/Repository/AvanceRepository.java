package com.yassir.projet.gestion_employes.Repository;

import com.yassir.projet.gestion_employes.Entity.Avance;
import com.yassir.projet.gestion_employes.Entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
public interface AvanceRepository  extends JpaRepository<Avance, Long> {
    List<Avance> findByEmployeAndDateAvanceBetween(Employe e, Date start, Date end);

}

