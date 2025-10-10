package com.yassir.projet.gestion_employes.Controller;

import com.yassir.projet.gestion_employes.Entity.*;
import com.yassir.projet.gestion_employes.Repository.EmployeRepository;
import com.yassir.projet.gestion_employes.Service.GestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/avance-absence")
public class AvanceAbsenceController {

    private final GestionService service;
    private final EmployeRepository employeRepo;

    public AvanceAbsenceController(GestionService service, EmployeRepository employeRepo) {
        this.service = service;
        this.employeRepo = employeRepo;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("employes", service.getAllEmployes());
        return "ajout-avance-absence";
    }

    @PostMapping("/add")
    public String add(@RequestParam String employeCin,
                      @RequestParam String type,
                      @RequestParam Float montant,
                      @RequestParam String date) throws Exception {

        Employe e = employeRepo.findById(employeCin).orElse(null);
        if (e == null) return "redirect:/avance-absence";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(date);

        if ("avance".equals(type)) {
            Avance a = new Avance();
            a.setEmploye(e);
            a.setMontant(montant);
            a.setDateAvance(d);
            service.addAvance(a);
        } else if ("absence".equals(type)) {
            Absence a = new Absence();
            a.setEmploye(e);
            a.setPenalite(montant);
            a.setDateAbsence(d);
            service.addAbsence(a);
        }

        return "redirect:/avance-absence";
    }
}