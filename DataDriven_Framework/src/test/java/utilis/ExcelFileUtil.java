package utilis;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil 
{XSSFWorkbook wb;
//constructor for reading excel path
public ExcelFileUtil(String Excelapth)throws Throwable
{
	FileInputStream fi = new FileInputStream(Excelapth);
	wb = new XSSFWorkbook(fi);
}
//method for counting no of rows in a sheet
public int rowCount(String sheetname)
{
	return wb.getSheet(sheetname).getLastRowNum();
}
//method for reading cell data
public String getCelldata(String sheetname,int row,int column)
{
	String data ="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
	{
		int celldata =(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		data = String.valueOf(celldata);
				
	}
	else
	{
		data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
//method for writing results into new file
public void setCelldata(String sheetname,int row,int column,String status,String WriteExcel)throws Throwable
{
	XSSFSheet ws = wb.getSheet(sheetname);
	XSSFRow rowNum = ws.getRow(row);
	XSSFCell cell =rowNum.createCell(column);
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		XSSFCellStyle style =wb.createCellStyle();
		XSSFFont font = wb.createFont();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		style.setFont(font);
		ws.getRow(row).getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Fail"))
	{
		XSSFCellStyle style =wb.createCellStyle();
		XSSFFont font = wb.createFont();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		style.setFont(font);
		ws.getRow(row).getCell(column).setCellStyle(style);
	}
	FileOutputStream fo = new FileOutputStream(WriteExcel);
	wb.write(fo);
	
}


}
