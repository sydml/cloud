package com.sydml.authorization.controller;

import com.sydml.authorization.util.excel.ExportExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/5/8 0008
 */
@RestController
@RequestMapping("export")
public class ExportExcelController {

    @GetMapping("/template")
    public void exportWithTemplate(HttpServletResponse response, HttpServletRequest request) {
        ExportExcel exportExcel = new ExportExcel();
        List<Map<String, Object>> maps = new ArrayList<>();
        String fileName = "MistakeRecharge" + LocalDate.now().toString() + ".xls";
        String filePath = request.getServletContext().getRealPath("excel/MistakeRecharge.xls");
        exportExcel.export(response, fileName, filePath, maps);
    }

    @GetMapping("/with-out-template")
    public void exportWithOutTemplate(HttpServletResponse response, HttpServletRequest request) {
        ExportExcel exportExcel = new ExportExcel();
        LinkedHashMap<String, String> head = new LinkedHashMap<>();
        List<LinkedHashMap> content = new ArrayList<>();
        String sheetName = "AdminQuery" + LocalDate.now().toString() + ".xls";
        try {
            sheetName = URLEncoder.encode(sheetName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("transfer.encode.fail");
        }
        HSSFWorkbook workbook = exportExcel.mapToExcel(head, content, sheetName);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("export.excel.fail");
        }
    }
}
