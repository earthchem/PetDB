package admin.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;


/* The RockModeExlSheet class represents 'RockModes' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class RockModeExlSheet
{
	WritableSheet sheet=null;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'RockModes' sheet.
	 * Output: sht will be filled.
	*/
	public RockModeExlSheet(WritableSheet sht, RockMode2DS info) throws Exception
	{
        sheet=sht;
        
        /* Fill all data the sheet */
        fillSheet(info);
 //       info.close(); 
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(RockMode2DS info) throws Exception
	{
		sheet.addCell(new Label(0,0,"ROCK MODE"));
		
		sheet.addCell(new Label(9,1,"GROUNDMASS"));
		sheet.addCell(new Label(10,1,"VESICLES"));
	    sheet.addCell(new Label(11,1,"ENTER NAMES OF OTHER MINERALS"));
	
	    sheet.addCell(new Label(0,2,"SAMPLE NAME"));
	    sheet.addCell(new Number(0,3,-1));
	    sheet.addCell(new Label(1,2,"NUMBER_OF_POINTS_COUNTED(MIN)"));
	    sheet.addCell(new Label(2,2,"NUMBER_OF_POINTS_COUNTED(MAX)"));
	    sheet.addCell(new Label(3,2,"PLAG"));
	    sheet.addCell(new Label(4,2,"CPX"));
	    sheet.addCell(new Label(5,2,"OPX"));
	    sheet.addCell(new Label(6,2,"OL"));
	    sheet.addCell(new Label(7,2,"OPAQ"));
	    sheet.addCell(new Label(8,2,"AMPH"));
	    sheet.addCell(new Label(9,2,"GM"));
	    sheet.addCell(new Label(10,2,"VES"));
	    sheet.addCell(new Number(11,2,-1));
	   	}
}