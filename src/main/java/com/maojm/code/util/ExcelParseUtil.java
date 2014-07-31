/**
 * 
 */
package com.maojm.code.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * @since 2014年4月26日 下午8:00:32
 */
public class ExcelParseUtil {
	private Workbook wookBook;
    private DecimalFormat df = new DecimalFormat("###");
    private static Logger logger = Logger.getLogger(ExcelParseUtil.class);
    
    public ExcelParseUtil(File excelFile){
        InputStream is = null;
        try {
            is = new FileInputStream(excelFile);
            wookBook = WorkbookFactory.create(excelFile);
        } catch (Exception e) {
            logger.error("WorkbookFactory.create error", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("InputStream close fail.", e);
            }
        }
    }
    
    public int getSheetNumberOfRows(int sheetIndex){
        Sheet sheet = wookBook.getSheetAt(sheetIndex);
        int rowNums = sheet.getPhysicalNumberOfRows();
        return rowNums;
    }
    
    public Iterator<Row> rowIterator(int sheetIndex){
        return wookBook.getSheetAt(sheetIndex).rowIterator();
    }
    
    public List<String> getCellList(Row row){
        List<String> cellValList = new ArrayList<String>();
        int maxCellNum = row.getLastCellNum();
        for (int i = 0; i < maxCellNum; i++) {
            if (row.getCell(i) != null) {
                cellValList.add(getCellValue(row.getCell(i)));
            } else {
                cellValList.add(null);
            }
        }
        return cellValList;
    }
    
    public String getCellValue(Cell cell){
        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
            return cell.getStringCellValue();
        } else if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            return df.format(cell.getNumericCellValue());
        } else if (HSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
            return "";
        } else if (HSSFCell.CELL_TYPE_ERROR == cell.getCellType()) {
            return "";
        } else if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
            return "";
        }
        return "";
    }
    
    /**
     * 
     * @param templateSheet
     * @param row2copy
     * @param index
     */
    public void copyRowByTemplate(Sheet templateSheet, Row row2copy,int index){
		try{
			Row row = templateSheet.getRow(index);
			Iterator<Cell> it = row.iterator();
			int cindex = 0;
			while(it.hasNext()){
				Cell cell = it.next();
				Cell cc = row2copy.createCell(cindex);
				cc.setCellValue(cell.getStringCellValue());
				cc.setCellStyle(cell.getCellStyle());
				cindex++;
			}
		}catch(Exception e){
			logger.error("copyRowByTemplate cause exception",e);
		}
	}
    
    /**
     * 
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    public void mergeRegion(Sheet sheet,int firstRow,int lastRow,int firstCol,int lastCol){
    	sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstCol,lastCol));
    }
}
