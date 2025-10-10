package com.yassir.projet.gestion_employes.Repository;

import com.yassir.projet.gestion_employes.Entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeRepository extends JpaRepository<Employe, String> {

}
