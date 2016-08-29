/*$Id:*/
package admin.data;

import admin.dbAccess.ReferenceDS;
import admin.dbAccess.ReferenceQueries;
import java.sql.*;
import java.util.Iterator;
import java.util.Vector;

import jxl.write.WritableSheet;
import jxl.write.Label;
import jxl.write.Number;

/* The TableInRefTable2DS class stores the search results from TableInRefTable2DCtlQuery
 * It extends RowBasedDS class
 * 
 * It contains multiple rows information with two column 'Table_in_ref' and 'Table_Title'.
 * 
 */

public class TableInRefTable2DS extends ReferenceDS
{
    public static int TABLE_IN_REF 	=1;
    public static int TABLE_TITLE 	=2;
    private static final int COL_NUM = 2;

	public TableInRefTable2DS(String v_filter) {
     //   super(getTableInRefTable(v_filter));  
        super(getTableInRefTable(v_filter),COL_NUM);  
	}
	
	public void writeColumnToSheet(WritableSheet sheet, int columnOff, int rowOff)
	{
        int row = 0;
        Iterator it = dsList.iterator();
        try {     
			while(it.hasNext())
			{
                String [] dt = (String[]) it.next();
				String tabInRef = dt[0];
				sheet.addCell(new Label(columnOff+0,rowOff+row, tabInRef));	
                sheet.addCell(new Label(columnOff+1,rowOff+row,dt[1]));          
                row++;
            }
			sheet.addCell(new Number(columnOff+0,rowOff+row,-1)); 
        } catch (Exception e){
            System.out.println(" TableTitlesExlSheet: " +e.getMessage());
        }
        
	/*	try {     
			while(r_set.next())
			{
				String tabInRef = r_set.getString(TABLE_IN_REF);
				sheet.addCell(new Label(columnOff+0,rowOff+row, tabInRef));	
                sheet.addCell(new Label(columnOff+1,rowOff+row,r_set.getString(TABLE_TITLE)));          
                row++;
            }
			sheet.addCell(new Number(columnOff+0,rowOff+row,-1)); 
        } catch (Exception e){
            System.out.println(" TableTitlesExlSheet: " +e.getMessage());
        }
        
        finally {
				da.close();
        }
        */
    }	
	
}

