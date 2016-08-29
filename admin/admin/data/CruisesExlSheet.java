package admin.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;

/* The CruisesExlSheet class represents 'CRUISES' sheet of data entry Excel file. 
 * 
 * Thadmine pass-in data will fill into this sheet.
 * 
 */
public class CruisesExlSheet
{
	WritableSheet sheet=null;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'CRUISES' sheet.
	 * Output: sht will be filled.
	*/
	public CruisesExlSheet(WritableSheet sht, Cruises2DS info) throws Exception
	{
        sheet=sht;
        
        /* Fill all data the sheet */
        fillSheet(info);
   //     info.close(); 
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Cruises2DS info) throws Exception
	{
		sheet.addCell(new Label(0,0,"CRUISE NAME"));
		sheet.addCell(new Label(1,0,"LEG"));
		sheet.addCell(new Label(2,0,"SHIP"));
		sheet.addCell(new Label(3,0,"FROM YEAR"));
		sheet.addCell(new Label(4,0,"TO YEAR"));
		sheet.addCell(new Label(5,0,"CHIEF SCIENTIST"));
		sheet.addCell(new Label(6,0,"INSTITUTION"));
	}
}