package ai.service.ai_service.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import ai.service.ai_service.dto.Doctor_Details_Dto;
import jakarta.annotation.PostConstruct;

@Service
public class ExcelService {

    private List<Doctor_Details_Dto> doctors;

    @PostConstruct
    public void init() throws Exception {
        doctors = loadDoctors();
        System.out.println("✅ Doctors Loaded: " + doctors.size());
    }

    public List<Doctor_Details_Dto> getDoctors() {
        return doctors;
    }

    public List<Doctor_Details_Dto> loadDoctors() throws Exception {

        List<Doctor_Details_Dto> list = new ArrayList<>();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("RAG-Data.xls");

        if (is == null) {
            throw new RuntimeException("❌ File not found: RAG-Data.xls");
        }

        // ✅ Auto-detects .xls or .xlsx
        try (Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {

                // Skip header
                if (row.getRowNum() == 0)
                    continue;

                // ✅ Null-safe cell reading
                String name = formatter.formatCellValue(
                        row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));

                // Skip empty rows
                if (name == null || name.trim().isEmpty())
                    continue;

                Doctor_Details_Dto d = new Doctor_Details_Dto();
                d.setName(name);

                // ✅ Specialization (column 6)
                d.setSpecialization(formatter.formatCellValue(
                        row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));

                // ✅ Fees (column 1)
                String feesStr = formatter.formatCellValue(
                        row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
                d.setFees(parseInt(feesStr));

                // ✅ Availability (column 2)
                d.setAvailableDays(
                        formatter.formatCellValue(
                                row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                                .replace("\n", ","));

                // ✅ Location (column 3)
                d.setLocation(formatter.formatCellValue(
                        row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));

                // ✅ Reviews (column 4)
                d.setReviews(formatter.formatCellValue(
                        row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));

                // ✅ Rating (column 5)
                String ratingStr = formatter.formatCellValue(
                        row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
                d.setRating(parseDouble(ratingStr));

                // ✅ Search Text (column 7)
                d.setSearchText(formatter.formatCellValue(
                        row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));

                list.add(d);
            }
        }

        return list;
    }

    // 🔧 Utility methods

    private int parseInt(String value) {
        if (value == null) return 0;
        String cleaned = value.replaceAll("[^0-9]", "");
        return cleaned.isEmpty() ? 0 : Integer.parseInt(cleaned);
    }

    private double parseDouble(String value) {
        if (value == null) return 0.0;
        String cleaned = value.replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
    }
}