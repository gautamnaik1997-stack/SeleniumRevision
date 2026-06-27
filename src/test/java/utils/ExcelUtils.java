package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	public XSSFCellStyle style;
	public String xlfile;
	
	public int getRowCount(String sname) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sname);
		int totalrows = ws.getLastRowNum();	
		return totalrows;		
	}
	
	public int getCellCount(String sname, int rownum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sname);
		int totalcells = ws.getRow(rownum).getLastCellNum();
		return totalcells;		
	}
	
	public String getSpecificCellData(String sname, int rownum, int cellnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sname);
		String cellvalue;
		try {
		cell = ws.getRow(rownum).getCell(cellnum);
		DataFormatter df = new DataFormatter();
		cellvalue = df.formatCellValue(cell);
		} catch(Exception e) {
			cellvalue = "";
		}
		return cellvalue;		
	}
	
	public void setSpecificCellDate(String sname, int rownum, int cellnum, String cellvalue) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sname);
		row = ws.getRow(rownum);
		row.getCell(cellnum).setCellValue(cellvalue);
		fo = new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	
	public void setGreenCellColor(String sname, int rownum, int cellnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sname);
		cell = ws.getRow(rownum).getCell(cellnum);
		style = wb.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	
	public void setRedCellColor(String sname, int rownum, int cellnum) throws IOException {
		fi = new FileInputStream(xlfile);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sname);
		cell = ws.getRow(rownum).getCell(cellnum);
		style = wb.createCellStyle();		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		fo=new FileOutputStream(xlfile);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
}
