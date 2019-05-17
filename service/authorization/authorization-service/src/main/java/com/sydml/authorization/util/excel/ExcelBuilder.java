package com.sydml.authorization.util.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/5/8 0008
 */
public class ExcelBuilder extends AbstractExcelTool {
    /**
     * 根据输出内容和提供的模版，构建出excel输出流；
     *
     * @param result       数据信息
     * @param templateFile 模版信息
     * @return 输出流
     */
    public ByteArrayOutputStream build(List<Map<String, Object>> result, File templateFile) throws IOException, InvalidFormatException {
        // 默认导出为Sheet 2，从第3行开始写入
        return build(result, templateFile, 1, 2);
    }

    /**
     * 根据输出内容和提供的模版，构建出excel输出流；
     *
     * @param result       数据信息
     * @param templateFile 模版信息
     * @param sheetIndex   写入页(从0开始)
     * @param beginRow     写入行(从0开始)
     * @return 输出流
     */
    public ByteArrayOutputStream build(List<Map<String, Object>> result, File templateFile,
                                       Integer sheetIndex, Integer beginRow) throws IOException, InvalidFormatException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(templateFile);
            Sheet sheet1 = wb.getSheetAt(sheetIndex);

            // 过滤掉第一行，第二行为key信息；
            Map<Integer, String> keyMap = parseKeyTitle(sheet1.getRow(1).cellIterator());

            int index = beginRow;
            for (Map map : result) {
                Row row = sheet1.createRow(index++);
                keyMap.keySet().stream().forEach(columnIndex -> {
                    String value = keyMap.get(columnIndex);
                    if (map.containsKey(value) && map.get(value) != null) {
                        row.createCell(columnIndex).setCellValue(String.valueOf(map.get(value)));
                    }
                });
            }
            wb.write(outputStream);
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) { //NOSONAR
                // nothing to do;
            }
        }
        return outputStream;
    }

    /**
     * 根据输出内容和提供的模版，构建出excel输出流；
     *
     * @param result       数据信息，excel根据map的数量设置sheet页，
     *                     每个map对应一个sheet页，List中的第一条记录作为表头，第二条记录作为code用于填充数据
     * @param templateFile 模版信息
     * @param sheetIndex   写入页(从0开始)
     * @return 输出流
     */
    public ByteArrayOutputStream buildMultiSheet(Map<String, List<Map<String, Object>>> result, File templateFile,
                                                 Integer sheetIndex) throws IOException, InvalidFormatException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(templateFile);
            // excel第一页为模板页
            Sheet firstSheet = wb.getSheetAt(sheetIndex);
            // 保存模板页的前两行
            Map<Integer, String> titleRow = parseKeyTitle(firstSheet.getRow(0).cellIterator());
            Map<Integer, String> codeRow = parseKeyTitle(firstSheet.getRow(1).cellIterator());
            for (Map.Entry<String, List<Map<String, Object>>> entry : result.entrySet()) {
                if (sheetIndex >= wb.getNumberOfSheets()) {
                    wb.createSheet("Sheet_" + (sheetIndex + 1));
                }
                Sheet sheet = wb.getSheetAt(sheetIndex);
                int index = 0;
                // 设置前两行，保证国际化，和所有sheet页的公共头部一致
                for (; index < 2; index++) {
                    createTitle(sheet, index, index == 0 ? titleRow : codeRow, entry.getValue().get(index));
                }
                // 将list中的第二条数据作为以后数据填充的参考，补充到keyMap中去.
                Map<Integer, String> keyMap = parseKeyTitle(sheet.getRow(1).cellIterator());

                for (int i = 2; i < entry.getValue().size(); i++) {
                    Map<String, Object> map = entry.getValue().get(i);
                    Row row = sheet.createRow(index++);
                    keyMap.keySet().stream().forEach(columnIndex -> {
                        String value = keyMap.get(columnIndex);
                        if (map.containsKey(value) && map.get(value) != null) {
                            row.createCell(columnIndex).setCellValue(String.valueOf(map.get(value)));
                        }
                    });
                }
                sheetIndex++;
            }

            wb.write(outputStream);
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) { //NOSONAR
                // nothing to do;
            }
        }
        return outputStream;
    }

    private void createTitle(Sheet sheet, int index, Map<Integer, String> oldTitle, Map<String, Object> newTitle) {
        if (null != sheet.getRow(index)) {
            sheet.removeRow(sheet.getRow(index));
        }
        Row row = sheet.createRow(index);
        int column = 0;
        for (Integer key : oldTitle.keySet()) {
            row.createCell(column++).setCellValue(oldTitle.get(key));
        }
        for (String key : newTitle.keySet()) {
            row.createCell(column++).setCellValue(newTitle.get(key).toString());
        }
        if (index == 1) {
            row.setZeroHeight(true);
        }
    }
}
