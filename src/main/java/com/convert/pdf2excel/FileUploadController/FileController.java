package com.convert.pdf2excel.FileUploadController;

import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileController {

    @PostMapping("/convert")
    public List<Map<String, String>> convertPdfToExcel(@RequestParam("file") MultipartFile file) throws IOException {
        // Reading PDF file
        String pdfText = readPdf(file);

        //  Creating Excel file
        ByteArrayInputStream excelFile = createExcel(pdfText);

        // Parsing the Excel file and return bank transactions
        return parseExcel(excelFile);
    }


    private String readPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private ByteArrayInputStream createExcel(String pdfText) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bank Transactions");

        String[] lines = pdfText.split("\\r?\\n");
        int rowNum = 0;

        for (String line : lines) {
            Row row = sheet.createRow(rowNum++);
            String[] cells = line.split("\\s+");

            for (int i = 0; i < cells.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(cells[i]);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private List<Map<String, String>> parseExcel(ByteArrayInputStream excelFile) throws IOException {
        List<Map<String, String>> transactions = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Map<String, String> transaction = new HashMap<>();
            transaction.put("Date", row.getCell(0).getStringCellValue());
            transaction.put("Transaction Type", row.getCell(1).getStringCellValue());
            transaction.put("Amount", row.getCell(2).getStringCellValue());
            transactions.add(transaction);
        }

        workbook.close();
        return transactions;
    }

}
