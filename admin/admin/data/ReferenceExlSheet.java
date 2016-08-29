/* $Id:*/
package admin.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;


/* The ReferenceExlSheet class represents 'REFERENCE' sheet for data entry excel file. It is the first sheet in the file. 
 * 
 * It only contains one usefull information, 'reference number'. The rest are hard-coded empty columns with column names.
 * 
 */
public class ReferenceExlSheet
{
	WritableSheet sheet=null;
    String reference_num;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	*/
	
	public ReferenceExlSheet(WritableSheet sht, String ref_num) throws Exception
	{
        sheet=sht;
        reference_num = ref_num;
        
        /* Fill all data the sheet */
        fillSheet(ref_num);
	}

	/* Add all cell information for REFERENCE sheet */
	public void fillSheet(String reference_num) throws Exception
	{
		sheet.addCell(new Label(0,0,"REFERENCE"));
		sheet.addCell(new Number(1,0,new Integer(reference_num).intValue()));
	//	sheet.addCell(new Label(2,0,"Cell B1 is reserved for DBA to put in the REF_NUM in the database, please leave blank"));
		sheet.addCell(new Label(2,0,""));
		sheet.addCell(new Label(0,1,"TITLE:"));
		sheet.addCell(new Label(0,2,"YEAR:"));
		sheet.addCell(new Label(0,3,"JOURNAL:"));
		sheet.addCell(new Label(0,4,"VOLUME:"));
		sheet.addCell(new Label(0,5,"ISSUE:"));
		sheet.addCell(new Label(0,6,"FIRST PAGE:"));
		sheet.addCell(new Label(0,7,"LAST PAGE:"));
		sheet.addCell(new Label(0,8,"BOOK TITLE:"));
		sheet.addCell(new Label(0,9,"BOOK EDITORS:"));
		sheet.addCell(new Label(0,10,"PUBLISHER:"));
		sheet.addCell(new Label(0,13,"AUTHORS:"));
		sheet.addCell(new Label(0,14,"LAST NAME"));
		sheet.addCell(new Label(1,14,"FIRST AND MIDDLE NAME INITIALS"));
		sheet.addCell(new Label(2,14,"INSTITUTION"));
		sheet.addCell(new Label(3,14,"DEPARTMENT"));
		sheet.addCell(new Label(4,14,"ADDRESS"));
		sheet.addCell(new Label(5,14,"CITY STATE ZIP CODE"));
		sheet.addCell(new Label(6,14,"COUNTRY"));
		sheet.addCell(new Label(7,14,"PHONE"));                                                                  
		sheet.addCell(new Label(8,14,"E-MAIL ADDRESS"));
	
	}
}