package com.sydml.authorization.util.excel;

import com.opencsv.CSVReader;
import com.sydml.common.utils.JsonUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Liuym
 * @date 2019/5/8 0008
 */
public class ExcelReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

    public static final int ALL_SHEET = -1;
    public static final int ACTIVE_SHEET = -2;

    public static final String FILENAME_EXTENSION_EXCEL2003 = ".xls";
    public static final String FILENAME_EXTENSION_EXCEL2007 = ".xlsx";
    public static final String FILENAME_EXTENSION_CSV = ".csv";

    // 默认单元格内容为数字时格式
    private static DecimalFormat defaultDecimalFormat = new DecimalFormat("#.##");

    // 默认单元格格式化日期字符串
    private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取Excel文件页数
     *
     * @param file 上传文件信息
     * @return 页数
     */
    public Integer getNumberOfSheets(MultipartFile file) throws IOException {
        return getNumberOfSheets(Objects.requireNonNull(file.getOriginalFilename()), new BufferedInputStream(file.getInputStream()));
    }

    /**
     * 获取Excel文件页数
     *
     * @param filename  文件名
     * @param fileBytes 文件内容
     * @return 页数
     */
    public Integer getNumberOfSheets(String filename, byte[] fileBytes) throws IOException {
        return getNumberOfSheets(filename, new ByteArrayInputStream(fileBytes));
    }

    /**
     * 获取Excel文件页数
     */
    private Integer getNumberOfSheets(String filename, InputStream inputStream) throws IOException {
        Integer number;
        if (filename.toLowerCase().endsWith(FILENAME_EXTENSION_EXCEL2003)) {
            number = (new HSSFWorkbook(inputStream)).getNumberOfSheets();
        } else if (filename.toLowerCase().endsWith(FILENAME_EXTENSION_EXCEL2007)) {
            number = (new XSSFWorkbook(inputStream)).getNumberOfSheets();
        } else if (filename.toLowerCase().endsWith(FILENAME_EXTENSION_CSV)) {
            number = 1;
        } else {
            LOGGER.warn("not support file format [{}].", filename);
            throw new RuntimeException("not support file format");
        }
        LOGGER.info("file [{}] get {} sheets.", filename, number);
        return number;
    }

    /**
     * 读取Excel文件(读取指定SheetIndex)
     *
     * @param file       上传文件信息
     * @param sheetIndex 解析的sheet index(从0开始)
     * @return 解析结果
     */
    public List<Map<Integer, String>> parse(MultipartFile file,
                                            Integer sheetIndex) throws IOException {
        return parse(Objects.requireNonNull(file.getOriginalFilename()), new BufferedInputStream(file.getInputStream()), sheetIndex);
    }

    /**
     * 读取Excel文件(读取指定SheetIndex)
     *
     * @param filename   文件名
     * @param fileBytes  文件内容
     * @param sheetIndex 解析的sheet index(从0开始)
     * @return 解析结果
     */
    public List<Map<Integer, String>> parse(String filename, byte[] fileBytes,
                                            Integer sheetIndex) throws IOException {
        return parse(filename, new ByteArrayInputStream(fileBytes), sheetIndex);
    }

    /**
     * 读取Excel文件(读取指定SheetIndex)
     */
    private List<Map<Integer, String>> parse(String filename, InputStream inputStream,
                                             Integer sheetIndex) throws IOException {
        List<Map<Integer, String>> result;
        if (filename.toLowerCase().endsWith(FILENAME_EXTENSION_EXCEL2003)) {
            // 读取xls格式文件
            result = parseWorkbook(new HSSFWorkbook(inputStream), sheetIndex);
        } else if (filename.toLowerCase().endsWith(FILENAME_EXTENSION_EXCEL2007)) {
            // 读取xlsx格式文件
            result = parseWorkbook(new XSSFWorkbook(inputStream), sheetIndex);
        } else if (filename.toLowerCase().endsWith(FILENAME_EXTENSION_CSV)) {
            // 读取csv格式文件
            result = parseCSVFormat(inputStream);
        } else {
            LOGGER.warn("not support file format [{}].", filename);
            throw new RuntimeException("not support ");
        }
        LOGGER.info("parse file [{}] sheet [{}] get {} rows.", filename, sheetIndex, result.size());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("================================");
            result.forEach(iter -> LOGGER.debug(" -> " + JsonUtil.encodeJson(iter)));
            LOGGER.debug("--------------------------------");
        }
        return result;
    }

    /**
     * 读取Excel WorkBook
     */
    private List<Map<Integer, String>> parseWorkbook(Workbook workBook, Integer sheetIndex) {
        List<Map<Integer, String>> allData = new ArrayList<>();
        if (sheetIndex == ACTIVE_SHEET) {
            Integer activeSheetIndex = workBook.getActiveSheetIndex();
            parseSheet(allData, workBook.getSheetAt(activeSheetIndex));
        } else if (sheetIndex == ALL_SHEET) {
            for (int index = 0; index < workBook.getNumberOfSheets(); index++) {
                parseSheet(allData, workBook.getSheetAt(index));
            }
        } else if (sheetIndex >= 0 && sheetIndex <= workBook.getNumberOfSheets()) {
            parseSheet(allData, workBook.getSheetAt(sheetIndex));
        } else {
            LOGGER.warn("givin sheet index [{}] out of bound.", sheetIndex);
            throw new RuntimeException("givin sheet index out of bound");
        }
        return allData;
    }

    /**
     * 读取sheet中的数据信息
     */
    private void parseSheet(List<Map<Integer, String>> allData, Sheet sheet) {
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            // 解析每个行单元的数据
            Map<Integer, String> rowMap = new HashMap<>();
            Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowMap.put(cell.getColumnIndex(), getStringCellValue(cell));
            }
            allData.add(rowMap);
        }
    }

    /**
     * 读取单元格内数据信息
     */
    private static String getStringCellValue(Cell cell) {
        String strCell;
        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
            strCell = cell.getStringCellValue();
        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            if (DateUtil.isCellDateFormatted(cell)) {
                strCell = formatDate(cell.getDateCellValue());
            } else {
                strCell = defaultDecimalFormat.format(cell.getNumericCellValue());
            }
        } else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
            strCell = String.valueOf(cell.getBooleanCellValue());
        } else if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
            strCell = "";
        } else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
            strCell = "";
        } else {
            strCell = cell.toString();
        }
        return strCell;
    }

    private synchronized static String formatDate(Date date) {
        return defaultDateFormat.format(date);
    }

    /**
     * 读取CSV格式的数据
     */
    private List<Map<Integer, String>> parseCSVFormat(InputStream inputStream) throws IOException {
        List<Map<Integer, String>> allData = new ArrayList<>();
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
        for (String[] aReader : reader) {
            Map<Integer, String> rowMap = new HashMap<>();
            int index = 0;
            for (String cell : aReader) {
                rowMap.put(index++, cell);
            }
            allData.add(rowMap);
        }
        reader.close();
        return allData;
    }

    public static void main(String[] args) throws IOException {
        ExcelReader reader = new ExcelReader();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("excel/5月份工作.xlsx");
        List<Map<Integer, String>> result = reader.parse("5月份工作.xlsx", resourceAsStream, -1);
        ExportExcel exportExcel = new ExportExcel();
        Map<Integer, String> map = result.get(0);
        Map<String, String> head = new HashMap<>();
        for (Map.Entry<Integer, String> integerStringEntry : map.entrySet()) {
            head.put(integerStringEntry.getKey().toString(), integerStringEntry.getValue());
        }
        result.remove(0);

        HSSFWorkbook workbook = exportExcel.mapToExcel(head, result, "ceshi");
//        OutputStream outputStream = new FileOutputStream(new File());
//        workbook.write(outputStream);

        FileOutputStream fos = null;
        BufferedOutputStream bufos = null;
        File dir = new File("E:\\test");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(dir, "test.xls");
        try {
            fos = new FileOutputStream(destFile);
            bufos = new BufferedOutputStream(fos);
            workbook.write(bufos);
            bufos.flush();
        } finally {
            if (bufos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭失败");
                }
            }
        }
        System.out.println();
    }
}
