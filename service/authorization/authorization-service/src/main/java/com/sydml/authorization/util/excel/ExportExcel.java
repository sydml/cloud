package com.sydml.authorization.util.excel;

import com.sydml.common.utils.CastUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Liuym
 * @date 2019/5/8 0008
 */
public class ExportExcel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcel.class);

    public void export(HttpServletResponse response, String fileName, String filePath, List<Map<String, Object>> resultList) {
        response.reset();
        response.setContentType("application/octet-stream");
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        ByteArrayOutputStream outputStream = buildWorkbook(resultList, filePath);
        byte[] data = outputStream.toByteArray();
        try {
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * build the 输出流
     *
     * @param result
     * @param path
     * @return
     */
    public ByteArrayOutputStream buildWorkbook(List<Map<String, Object>> result, String path) {
        try {
            File file = new File(path);
            return new ExcelBuilder().build(result, file);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("can.not.export.excel.info", e);
        }
    }

    /**
     * 读取前台国际化属性文件
     */
    @Deprecated
    public Map<String, Object> international(String path) {
        Map<String, Object> languageMap = new HashMap<>();
        Properties pro = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            //设置编码格式
            pro.load(new InputStreamReader(in, "utf-8"));
            Set<Object> keys = pro.keySet();
            for (Object key : keys) {
                languageMap.put(key.toString(), pro.get(key));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("can.not.export.excel.info", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                    throw new RuntimeException("can.not.export.excel.info", e);//NOSONAR
                }
            }
        }
        return languageMap;
    }

    /**
     * @param instant
     * @param timeZone
     * @param format
     * @return
     */
    public String formatDate(Instant instant, Integer timeZone, String format) {
        if (null == instant) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(timeZone).normalized());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * formatDate 方法会在原来的时间上再加相应的时差，返回数据不准确
     * 此方法修正formate的错误，
     *
     * @param instantStr “2019-03-15T05:09:37.377Z”字符串
     * @param format     格式化字符串
     * @return
     */
    public String formatCompanyDate(String instantStr, String format, ZoneId zoneId) {
        if (null == instantStr) {
            return null;
        }
        LocalDateTime date = LocalDateTime.parse(instantStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        Instant systemInstant = date.atZone(zoneId).toInstant();
        return LocalDateTime.ofInstant(systemInstant, zoneId).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Map生成excel表
     *
     * @param head     (头部的key必须对应map的key)
     * @param contents
     * @return 返回保存的地址
     */
    public HSSFWorkbook mapToExcel(Map<String, String> head, List<? extends Map> contents, String sheetName) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.autoSizeColumn(0);
        //生成内容
        for (int i = 0; i < contents.size() + 1; i++) {
            //excel的行数
            HSSFRow row = sheet.createRow(i);
            int j = 0;
            //列
            for (Map.Entry<String, String> property : head.entrySet()) {
                HSSFCell cell = row.createCell(j++);
                if (i == 0) {
                    //表头信息
                    cell.setCellValue(property.getValue());
                } else {
                    cell.setCellValue(contents.get(i - 1).get(CastUtil.castInt(property.getKey())) == null ? null :
                            contents.get(i - 1).get(CastUtil.castInt(property.getKey())).toString());
                }
            }
        }
        return workbook;
    }
}
