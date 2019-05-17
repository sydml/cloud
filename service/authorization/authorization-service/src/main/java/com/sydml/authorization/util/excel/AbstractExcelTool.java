package com.sydml.authorization.util.excel;

import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/5/8 0008
 */
public class AbstractExcelTool {
    /**
     * 获取标题头信息
     *
     * @param cellIterator 标题头数据
     * @return 列号+标题key的信息
     */
    protected Map<Integer, String> parseKeyTitle(Iterator<Cell> cellIterator) {
        int index = 0;
        Map<Integer, String> keyMap = new HashMap<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            keyMap.put(index++, getStringCellValue(cell));
        }
        return keyMap;
    }

    /**
     * 得到单元格内数据信息
     *
     * @param cell 单元格对象
     * @return 单元格数据
     */
    protected String getStringCellValue(Cell cell) {
        String strCell;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        return strCell;
    }
}
