/* $Id:*/
package petdb.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;

/* The ExlDS class handle writing out data to either OutputStream object or PrintWriter object. So data can be attached to HttpResponse for downloading from Web.
 * It also write out data in two different format. One is in Microsoft Excel format and another one is in CVS format.
 * 
 */
public class ExlDS
{
	public static String DataSheet = "Data";
	protected String state ="";
	
	protected OutputStream file = null;
	protected PrintWriter pr_file = null; 
	protected Vector columns = null;
	protected Vector values = null;
	
	protected ExcelDS data = null;
	protected WritableWorkbook workbook = null;
	protected WritableSheet sheet = null;
	protected int element_count = 0;
	protected int offset = 0;
	protected boolean csv = false; //By default it uses Microsoft Excel format.

	/* Constructor. It contains a WritableWorkbook object, WritableSheet object, OutputStream object 
	 * and PrintWriter object. It handles writing out data to either OutputStream or PrintWriter.
	 * 
	 * It will NOT create a WritableWookbook object and WritableSheet object when the constructor is called.
	 * 
	 * Input Paramters:
	 *   ExcelDS object: ds     contains all the data to be written out.
	 *   Vector object : c      contains column header strings for 'ds'.
	 *   boolean: flag          flag for either written out as CVS text file or charactor stream in OutputStream
	 * Output Parameters: 
	 *   OutputStream object: outStr          will be filled with data
	 *   
	*/
	public ExlDS(ExcelDS ds, Vector c, OutputStream outStr, boolean flag) throws Exception
	{
		data =ds;
		file = outStr;
		pr_file = new PrintWriter(file);
		csv = flag;
		element_count = c.size();
		columns = c;
		data.getTitleRow(columns);
		values = new Vector(columns.size());
		for (int i=0; i<columns.size();i++)
			values.add("");
	}

	/* Constructor. It contains a WritableWorkbook object, WritableSheet object, OutputStream object 
	 * and PrintWriter object. It will write out data to OutputStream.
	 * 
	 * It will create a WritableWookbook object and WritableSheet object when the constructor is called.
	 * 
	 * Input Paramters:
	 *   ExcelDS object: ds     contains all the data to be written out.
	 *   Vector object : c      contains column header strings for 'ds'.
	 * Output Parameters: 
	 *   OutputStream object: outStr          will be filled with data
	 *   
	*/
	public ExlDS(ExcelDS ds, Vector c, OutputStream outStr) throws Exception
	{
		data = ds;
		
		file = outStr;
		element_count = c.size();
		
		columns = c;
		data.getTitleRow(columns);
		
		values = new Vector(columns.size());
		for (int i=0; i<columns.size();i++)
			values.add("");
		
		createBook();
	}


	/* Constructor. It contains a WritableWorkbook object, WritableSheet object, OutputStream object 
	 * and PrintWriter object. It will write out data to OutputStream.
	 * 
	 * It will get the WritableWookbook object from the pass-in ExlDS object, then append data to its sheet.
	 * 
	 * Input Paramters:
	 *   ExcelDS object: ds     contains all the data to be written out.
	 *   Vector object : c      contains column header strings for 'ds'.
	 *   ExlDS object: append_to     data from 'ds' will be append to its Workbook.
	 * Output Parameters: 
	 *   OutputStream object: outStr          will be filled with data
	 *   
	*/
	public ExlDS(ExcelDS ds, Vector c, ExlDS append_to) throws Exception
	{
		data = ds;
		
		element_count = c.size();
		
		columns = c;
		data.getTitleRow(columns);
		
		values = new Vector(columns.size());
		for (int i=0; i<columns.size();i++)
			values.add("");
		file = append_to.getFile();
		workbook = append_to.getWorkbook();
		//sheet = append_to.getSheet(DataSheet); 
	}

	public String toString() {return state;}
	
	public void setOffset(int os) { offset = os;}
	
	public int getOffset() {return offset;}

	public WritableSheet getSheet(String name){ return workbook.getSheet(name);}
	public void addSheet(String name){  WritableSheet wr = workbook.createSheet(name,1);}
	public OutputStream  getFile(){ return file;}
	public WritableWorkbook getWorkbook(){ return workbook;}

	public int write() throws Exception
	{
		writeRow(offset, columns);
		if (!csv)
			offset += data.writeAllToSheet(sheet,++offset,values,element_count);
		else 	offset += data.writeAllToSheet(pr_file, ++offset,values,element_count);
		 //workbook.write();
		return 1;
	}
	
	public void setDefault(String name) { sheet = workbook.getSheet(name);}

	private void createBook() throws Exception
	{
		workbook = Workbook.createWorkbook(file);
		sheet = workbook.createSheet("Data", 0);
		sheet = workbook.getSheet("Data");
		offset = 0;
	}


	private int writeRow(int counter, Vector  v) throws Exception
	{
		String csv_line = "";
		for (int i=0; i< columns.size(); i++)
		{	
			if (!csv)
			{
				Label label = new Label(i,counter,(String)v.elementAt(i));
				sheet.addCell(label);
			} else
				csv_line += v.elementAt(i) + ",";	
		}

		if (csv) pr_file.println(csv_line);
		return 1;
	}

	/* Close Workbook object. All content in OutputStream or PrintStream are written out */
	public int closeBook() throws Exception
	{
		if (!csv)
			workbook.close();
		else 
		{
			pr_file.flush();
			pr_file.close();
		}
		return 1;
	}

	private void displayValues( Vector v )
	{
		//for (int i = 0; i<v.size(); i++)
		//	System.out.println("at : " + i + " is " + v.elementAt(i));

	}

}
