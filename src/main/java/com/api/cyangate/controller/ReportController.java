package com.api.cyangate.controller;

import com.api.cyangate.common.Convert;
import com.api.cyangate.common.Variable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class ReportController {

    @GetMapping("/export")
    public String export(Model model) {
        Convert.hashMap = new HashMap<>();
        Convert.count = 0;
        HashMap<String, List<String>> hashMap = Convert.toHashInDirectoryPath(Variable.PUBLIC_DIR.toString());

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet spreadsheet = workbook.createSheet(" Cat Records ");

        XSSFRow row;

        row = spreadsheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Total Count: ");
        cell = row.createCell(1);
        cell.setCellValue(Convert.count);
        row = spreadsheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("Date of report: ");
        cell = row.createCell(1);
        cell.setCellValue(new Date().toString());

        int rowid = 2;
        int cellid = 0;

        for (String key : hashMap.keySet()) {
            cellid = 0;
            row = spreadsheet.createRow(rowid++);
            cell = row.createCell(cellid++);
            cell.setCellValue(key.toString());

            List<String> objectArr = hashMap.get(key);

            for (Object obj : objectArr) {
                row = spreadsheet.createRow(rowid++);
                cell = row.createCell(cellid);
                cell.setCellValue((String) obj);
            }
        }

        try {
            FileOutputStream out = null;
            out = new FileOutputStream(new File("export.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/cat/list";
    }
}
