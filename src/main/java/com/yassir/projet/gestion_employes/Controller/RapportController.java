package com.yassir.projet.gestion_employes.Controller;

import com.yassir.projet.gestion_employes.Entity.Employe;
import com.yassir.projet.gestion_employes.Service.GestionService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/rapport")
public class RapportController {

    private final GestionService service;

    public RapportController(GestionService service) {
        this.service = service;
    }

    @GetMapping
    public String view(@RequestParam(required = false) String cin, Model model) {
        model.addAttribute("employes", service.getAllEmployes());

        if (cin != null && !cin.isBlank()) {
            Employe employe = service.getAllEmployes().stream()
                    .filter(e -> e.getCin().equals(cin))
                    .findFirst()
                    .orElse(null);

            if (employe != null) {
                Date debut = service.getLastThursday();
                Date fin = service.getNextThursday();

                Float totalSalaire = service.calculerSalaireSemaine(employe, debut, fin);
                int nbAvances = service.countAvancesBetween(employe, debut, fin);
                int nbAbsences = service.countAbsencesBetween(employe, debut, fin);
                Float totalAvances = service.totalAvancesBetween(employe, debut, fin);
                Float totalPenalites = service.totalAbsencePenalitesBetween(employe, debut, fin);

                model.addAttribute("employe", employe);
                model.addAttribute("total", totalSalaire);
                model.addAttribute("nbAvances", nbAvances);
                model.addAttribute("nbAbsences", nbAbsences);
                model.addAttribute("totalAvances", totalAvances);
                model.addAttribute("totalPenalites", totalPenalites);
                model.addAttribute("debut", debut);
                model.addAttribute("fin", fin);
            }
        }
        return "rapport";
    }

    @GetMapping("/export")
    public void exportAll(HttpServletResponse response) throws Exception {
        Date debut = service.getLastThursday();
        Date fin = service.getNextThursday();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Rapports");

            // === Styles ===
            // En-tête principal
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // En-têtes de colonnes
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Style normal pour les cellules
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setWrapText(true);

            // === Titre du document ===
            int rowIdx = 0;
            Row titleRow = sheet.createRow(rowIdx++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Rapport de semaine entre " + df.format(debut) + " et " + df.format(fin));
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

            // === En-têtes ===
            Row header = sheet.createRow(rowIdx++);
            String[] headers = {
                    "CIN", "Nom", "Prénom", "Période", "Salaire Hebdo (mensuel / 7)",
                    "Nb Avances", "Total Avances", "Dates Avances", "Nb Absences",
                    "Total Pénalités", "Dates Absences", "Salaire Ajusté"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // === Données ===
            for (Employe e : service.getAllEmployes()) {
                Row row = sheet.createRow(rowIdx++);

                int nbAvances = service.countAvancesBetween(e, debut, fin);
                int nbAbsences = service.countAbsencesBetween(e, debut, fin);
                Float totalAvances = service.totalAvancesBetween(e, debut, fin);
                Float totalPenalites = service.totalAbsencePenalitesBetween(e, debut, fin);
                Float salaireHebdo = (e.getSalaire() == null) ? 0f : e.getSalaire() / 7f;
                Float salaireAjuste = service.calculerSalaireSemaine(e, debut, fin);

                String avanceDates = service.getAvancesBetween(e, debut, fin).stream()
                        .map(a -> df.format(a.getDateAvance()))
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");

                String absenceDates = service.getAbsencesBetween(e, debut, fin).stream()
                        .map(a -> df.format(a.getDateAbsence()))
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");

                Object[] data = {
                        e.getCin(), e.getNom(), e.getPrenom(),
                        df.format(debut) + " - " + df.format(fin),
                        salaireHebdo, nbAvances, totalAvances, avanceDates,
                        nbAbsences, totalPenalites, absenceDates, salaireAjuste
                };

                for (int i = 0; i < data.length; i++) {
                    Cell cell = row.createCell(i);
                    if (data[i] instanceof Number n) cell.setCellValue(n.doubleValue());
                    else cell.setCellValue(String.valueOf(data[i]));
                    cell.setCellStyle(cellStyle);
                }
            }

            // Auto-sizing et esthétique
            for (int i = 0; i < 12; i++) {
                sheet.autoSizeColumn(i);
            }

            // === Export du fichier ===
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=Rapport-Semaine.xlsx");
            workbook.write(response.getOutputStream());
        }
    }
}
