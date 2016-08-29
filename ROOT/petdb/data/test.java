package petdb.data;

import java.io.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import javax.servlet.*;
import javax.servlet.http.*;
import petdb.data.*;

public class test
{
	public static void main(String[] a)
	{
		String n = "12.7";
		float f = Float.parseFloat(n);
	//	System.out.println(f); 
	//	System.out.println((double)f); 
		double d = Double.parseDouble(n);
	//	System.out.println(d); 
		try {
		WritableWorkbook workbook = Workbook.createWorkbook(new FileOutputStream("myfile.xls"));
        	WritableSheet sheet =((WritableWorkbook)workbook).createSheet("Data",0);
		sheet.addCell(new Label(1,1,"Data"));
		//((SheetSettings)sheet.getSettings()).setSelected();
		workbook.createSheet("References",1);
		sheet = workbook.getSheet("References");
		sheet.addCell(new Label(1,1,"References"));
		//((SheetSettings)sheet.getSettings()).setSelected();
		workbook.write();
		workbook.close();
		/*	
		System.out.println(sheet.getSettings());
		petdb.data.ExcelDownload ed =  new petdb.data.ExcelDownload();
		ed.doGet(new HttpServletRequest(), new HttpServletResponse()); 
		*/
		} catch (Exception e) { System.out.println(e.getMessage());}

	}


}


