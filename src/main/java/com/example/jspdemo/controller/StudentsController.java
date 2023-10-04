package com.example.jspdemo.controller;
import com.example.jspdemo.model.Students;
import com.example.jspdemo.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayOutputStream;





@Controller
public class StudentsController {
    @Autowired
    StudentsService studentsService;

    @GetMapping({"/", "viewStudentsList"})
    public String viewStudentsList(@ModelAttribute("message") String message, Model model) {
        model.addAttribute("studentsList", studentsService.getAllStudents());
        model.addAttribute("message", message);

        return "ViewStudentsList";
    }

    @GetMapping("/addStudents")
    public String addStudents(@ModelAttribute("message") String message, Model model) {
        model.addAttribute("students", new Students());
        model.addAttribute("message", message);

        List<String> colleges = Arrays.asList("DU", "GGSIPU", "AKTU", "VIT", "SRM");
        model.addAttribute("colleges", colleges);

        return "AddStudents";
    }


    @PostMapping("/saveStudents")
    public String saveStudents(Students students, RedirectAttributes redirectAttributes) {
        if (studentsService.saveOrUpdateStudents(students)) {
            redirectAttributes.addFlashAttribute("message", "Save Success");
            return "redirect:/viewStudentsList";
        }

        redirectAttributes.addFlashAttribute("message", "Save Failure");
        return "redirect:/addStudents";

    }

    @GetMapping("/editStudents/{id}")
    public String editStudents(@PathVariable Long id, Model model) {
        model.addAttribute("students", studentsService.getStudentsById(id));
        List<String> colleges = Arrays.asList("DU", "GGSIPU", "AKTU", "VIT", "SRM");
        model.addAttribute("colleges", colleges);

        return "EditStudents";
    }


    @PostMapping("/editSaveStudents")
    public String editSaveStudents(Students students, RedirectAttributes redirectAttributes) {
        if (studentsService.saveOrUpdateStudents(students)) {
            redirectAttributes.addFlashAttribute("message", "Edit Success");
            return "redirect:/viewStudentsList";
        }

        redirectAttributes.addFlashAttribute("message", "Edit Failure");
        return "redirect:/editStudents/" + students.getId();

    }

    @GetMapping("/deleteStudents/{id}")
    public String deleteStudents(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (studentsService.deleteStudents(id)) {
            redirectAttributes.addFlashAttribute("message", "Delete Success");
        } else {

            redirectAttributes.addFlashAttribute("message", "Delete Failure");

        }

        return "redirect:/viewStudentsList";
    }

    @GetMapping("/exportToPDF")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<Students> studentsList = studentsService.getAllStudents();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=students.pdf");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        int currentPage = 0;
        int rowsDrawn = 0;

        PDType1Font font = PDType1Font.HELVETICA;
        int fontSize = 12;

        float cellHeight = 20; // Adjust cell height as needed
        float tableXStart = margin;

        int rowsPerPage = (int) ((yStart - margin) / cellHeight); // Calculate rows per page based on available height

        for (int i = 0; i < studentsList.size(); i++) {
            if (rowsDrawn == rowsPerPage) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                currentPage++;
                yPosition = yStart;
                rowsDrawn = 0;
            }
            if (currentPage == 0 && rowsDrawn == 0) {
                // Draw table headers on the first page
                drawTableHeaders(contentStream, yPosition, tableWidth, tableXStart, font, fontSize, cellHeight);
                yPosition -= cellHeight;
            }
            drawTableRow(contentStream, yPosition, tableXStart, tableWidth, font, fontSize, cellHeight, studentsList.get(i));
            yPosition -= cellHeight;
            rowsDrawn++;
        }

        contentStream.close();
        document.save(response.getOutputStream());
        document.close();
    }

    private void drawTableHeaders(PDPageContentStream contentStream, float y, float tableWidth, float xStart, PDType1Font font, int fontSize, float cellHeight) throws IOException {
        float xPosition = xStart;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
        contentStream.setLineWidth(1f); // Set the line width for cell borders

        // Draw cell borders and fill with content
        for (int i = 0; i < 5; i++) { // Assuming 5 columns
            contentStream.addRect(xPosition, y, getColumnWidth(tableWidth, 5), cellHeight);
            contentStream.stroke();
            contentStream.beginText();

            // Adjust text position for better alignment
            float textYPosition = y + cellHeight - 15;

            contentStream.newLineAtOffset(xPosition + 5, textYPosition);
            contentStream.showText(getColumnHeader(i)); // Get column header based on column index
            contentStream.endText();
            xPosition += getColumnWidth(tableWidth, 5);
        }
    }

    private void drawTableRow(PDPageContentStream contentStream, float y, float xStart, float tableWidth, PDType1Font font, int fontSize, float cellHeight, Students student) throws IOException {
        float xPosition = xStart;
        contentStream.setFont(font, fontSize);
        contentStream.setLineWidth(1f); // Set the line width for cell borders

        // Draw cell borders and fill with content
        for (int i = 0; i < 5; i++) { // Assuming 5 columns
            contentStream.addRect(xPosition, y, getColumnWidth(tableWidth, 5), cellHeight);
            contentStream.stroke();
            contentStream.beginText();

            // Adjust text position for better alignment
            float textYPosition = y + cellHeight - 15;

            contentStream.newLineAtOffset(xPosition + 5, textYPosition);
            contentStream.showText(getCellValue(student, i)); // Get cell value based on column index
            contentStream.endText();
            xPosition += getColumnWidth(tableWidth, 5);
        }
    }

    private String getColumnHeader(int columnIndex) {
        // Return the appropriate column header based on the columnIndex
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Name";
            case 2:
                return "Year";
            case 3:
                return "College";
            case 4:
                return "Status";
            default:
                return "";
        }
    }

    private String getCellValue(Students student, int columnIndex) {
        // Return the appropriate cell value based on the columnIndex
        switch (columnIndex) {
            case 0:
                return String.valueOf(student.getId());
            case 1:
                return student.getName();
            case 2:
                return String.valueOf(student.getYear());
            case 3:
                return student.getCollege();
            case 4:
                return student.isCheckboxOption() ? "Active" : "Inactive";
            default:
                return "";
        }
    }

    private float getColumnWidth(float tableWidth, int numColumns) {
        return tableWidth / (float) numColumns;
    }



    @GetMapping("/exportToExcel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<Students> studentsList = studentsService.getAllStudents();

        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        // Create headers for the columns
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Id");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Year");
        headerRow.createCell(3).setCellValue("College");
        headerRow.createCell(4).setCellValue("Status");

        // Populate data
        int rowNum = 1;
        for (Students student : studentsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getName());
            row.createCell(2).setCellValue(student.getYear());
            row.createCell(3).setCellValue(student.getCollege());
            row.createCell(4).setCellValue(student.isCheckboxOption() ? "Active" : "Inactive");

        }

        // Prepare response
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.setContentDispositionFormData("attachment", "students.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/generateRandomStudents")
    public String generateRandomStudents() {
        studentsService.generateRandomStudents(10);
        return "redirect:/viewStudentsList";
    }

}




