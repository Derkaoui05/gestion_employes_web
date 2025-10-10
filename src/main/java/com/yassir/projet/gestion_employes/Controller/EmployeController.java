package com.yassir.projet.gestion_employes.Controller;

import com.yassir.projet.gestion_employes.Entity.Employe;
import com.yassir.projet.gestion_employes.Service.GestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    private final GestionService service;

    public EmployeController(GestionService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employes", service.getAllEmployes());
        model.addAttribute("employeForm", new Employe());
        return "employes";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Employe e) {
        service.saveEmploye(e);
        return "redirect:/employes";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Employe e) {
        service.saveEmploye(e);
        return "redirect:/employes";
    }

    @PostMapping("/delete/{cin}")
    public String delete(@PathVariable String cin) {
        service.deleteEmploye(cin);
        return "redirect:/employes";
    }
}
