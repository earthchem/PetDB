/* $Id:*/
package admin.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.format.Colour;

/* The TableTitlesExlSheet class represents 'TABLE_TITLES' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class TableTitlesExlSheet
{
	WritableSheet sheet=null;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'TABLE_TITLES' sheet.
	 * Output: sht will be filled.
	*/
	public TableTitlesExlSheet(WritableSheet sht, TableInRefTable2DS info) throws Exception
	{
        sheet=sht;
        fillSheet(info);
       // info.close(); 
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(TableInRefTable2DS info) throws Exception
	{
   		//sheet.addCell(new Label(0,0,"Number of TABLE_IN_REF",CellFormat.getFormat()));
		//sheet.addCell(new Label(1,0,"TABLE_TITLE",CellFormat.getFormat()));      
		sheet.addCell(new Label(0,0,"Number of TABLE_IN_REF"));
		sheet.addCell(new Label(1,0,"TABLE_TITLE"));      
        info.writeColumnToSheet(sheet,0,1);
		//FIXME: Fill in the rest of data from 'info'
	}

}