package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	
	public static File src;
	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static Workbook wb;
	public static Sheet ws;
	public static Row row;
	public static Cell cell;
	
	
	public int getRowcount(String Filepath,String Sheetname) throws IOException
	{
		src=new File(Filepath);
		fis=new FileInputStream(src);
		wb=new XSSFWorkbook(fis);
		ws=wb.getSheet(Sheetname);		
		int rowcount=ws.getLastRowNum();
		wb.close();
		fis.close();
		return rowcount;
	}
	
	public int getcolcount(String Filepath,String Sheetname) throws IOException 
	{
		src=new File(Filepath);
		fis=new FileInputStream(src);
		wb=new XSSFWorkbook(fis);
		ws=wb.getSheet(Sheetname);
		int colcount=ws.getRow(0).getLastCellNum();
		fis.close();
		wb.close();
		return colcount;
	}
	
	public String getCellData(String Filepath,String Sheetname,int rownum,int colnum) throws IOException
	{	
		src=new File(Filepath);
		fis=new FileInputStream(src);
		wb=new XSSFWorkbook(fis);
		ws=wb.getSheet(Sheetname);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter=new DataFormatter();
		String Data=formatter.formatCellValue(cell);
		
		return Data;		
	}
	
	public void insertCellData(String Filepath,String Sheetname,int rownum,int colnum,String result) throws IOException
	{
		
		src=new File(Filepath);
		fis=new FileInputStream(src);		
		wb=new XSSFWorkbook(fis);
		ws=wb.getSheet(Sheetname);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		cell.setBlank();
		cell.setCellValue(result);
		fos=new FileOutputStream(src);
		wb.write(fos);		
		fos.close();
		wb.close();	
		
		
	}
	
	public int numberofRowsForaColumn(String Filepath,String Sheetname,int colnum) throws IOException
	{
		src=new File(Filepath);
		fis=new FileInputStream(src);
		wb=new XSSFWorkbook(fis);
		ws=wb.getSheet(Sheetname);		
		int lastrowcount=ws.getLastRowNum();
		
		while(lastrowcount >=0 && ws.getRow(lastrowcount).getCell(colnum)==null)
		{
			lastrowcount--;
		}
		
		int rowcount=lastrowcount ;	
		
		wb.close();
		fis.close();
		return rowcount;
	}

}
