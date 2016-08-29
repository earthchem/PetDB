package admin.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;


/* The RockExlSheet class represents 'Rocks' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class RockExlSheet
{
	WritableSheet sheet=null;
	HashMap itemMap = new HashMap();
	HashMap methodMap;
	int valueCol = 7;
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'Rocks' sheet.
	 * Output: sht will be filled.
	*/
	public RockExlSheet(WritableSheet sht, Rock2DS info) throws Exception
	{
        sheet=sht;
        /* Fill all data the sheet */
        fillSheet(info);
    //    info.close(); 
	}
	
	public RockExlSheet(WritableSheet sht, Rock2DS info, HashMap map) throws Exception
	{
        sheet=sht;
        methodMap = map;
        /* Fill all data the sheet */
        fillSheet(info);
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Rock2DS info) throws Exception
	{
		sheet.addCell(new Label(0,0,"ROCK DATA"));		
		sheet.addCell(new Label(7,1,"ENTER OXIDES/ELEMENTS/ISOTOPE RATIOS ANALYZED HERE"));		
	    sheet.addCell(new Number(7,2,-1));
	    
	    sheet.addCell(new Label(4,3,"METHOD_CODE"));
	    sheet.addCell(new Label(5,3,"AS USED IN \"METHODS\""));   
	    sheet.addCell(new Label(4,4,"UNIT"));
	    
	    sheet.addCell(new Label(2,5,"SAMPLE DATA"));
	        
	    sheet.addCell(new Label(0,6,"ANALYSES NO."));
	    sheet.addCell(new Label(1,6,"TAB_IN_REF"));
	    sheet.addCell(new Label(2,6,"SAMPLE NAME"));
	    sheet.addCell(new Label(3,6,"ANALYSIS COMMENT"));
	    sheet.addCell(new Label(4,6,"NUMBER_OF_REPLICATES"));
	    sheet.addCell(new Label(5,6,"CALC_AVGE"));
	    sheet.addCell(new Label(6,6,"MATERIAL"));

	    sheet.addCell(new Label(2,7,"AS USED IN \"SAMPLES\""));
	    sheet.addCell(new Label(4,7,"IF AVERAGE"));
	    sheet.addCell(new Label(5,7,"Y-----Can be averaged   N-----Can't be A-----It is an average"));
	    sheet.addCell(new Label(6,7,"GL=GLASS          WR=WHOLE ROCK"));
	   
         if(info.getList().size() == 0) {
            sheet.addCell(new Number(2,8,-1));	
         
        } else { 
	    int size = 1178;
	    int offset = 8;
	    for(int i=0; i < size; ++i)
	    {
	    	sheet.addCell(new Number(0,offset+i,i+1));
	    }        
        fillItemmeasured(info.refNum);
	    HashMap alias = getAlias(info.refNum);
	//    ExcelUtil.fillDataTosheet(info.getResultSet(),sheet,itemMap,alias,valueCol);
        ExcelUtil.fillDataTosheet(info.getList(),sheet,itemMap,alias,valueCol);
        }
    }

	
	private void fillItemmeasured(String refNum) {
		String query = "select c.itemtypelist_num, d.DATA_QUALITY_NUM, lower(c.unit), i.ITEM_MEASURED_NUM from data_quality d, ANALYSIS a, ITEM_MEASURED I, CHEMISTRY C "+
				" where a.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM and d.ref_num = "+ refNum + 
				" AND I.ITEM_MEASURED_NUM = C.ITEM_MEASURED_NUM AND A.ANALYSIS_NUM = C.ANALYSIS_NUM "+
				" and a.BATCH_NUM not in "+
				" (select b.BATCH_NUM from batch b, table_in_ref t, mineral m, inclusion i "+
				" where b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and (m.BATCH_NUM = b.BATCH_NUM or i.BATCH_NUM = b.BATCH_NUM)and t.REF_NUM ="+ refNum + ") "+
				" group by c.itemtypelist_num, i.ITEM_MEASURED_NUM, d.DATA_QUALITY_NUM, lower(c.unit) order by d.DATA_QUALITY_NUM";
		ExcelUtil.fillItemmeasured(query,sheet,itemMap,methodMap,valueCol);
	}
	
	private HashMap getAlias(String refNum)
	{
		String query ="SELECT B.BATCH_NUM, SC.ALIAS FROM SAMPLE_COMMENT SC, BATCH B "+
				" WHERE SC.SAMPLE_NUM = B.SAMPLE_NUM AND SC.REF_NUM = "+ refNum + " AND B.BATCH_NUM IN " +
				" (SELECT B.BATCH_NUM FROM table_in_ref t, BATCH B "+
				" WHERE T.REF_NUM = "+ refNum + " AND T.TABLE_IN_REF_NUM = B.TABLE_IN_REF_NUM AND B.BATCH_NUM NOT IN "+
				" (select b.BATCH_NUM from batch b, table_in_ref t, mineral m, inclusion i "+
				" where b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and (m.BATCH_NUM = b.BATCH_NUM or i.BATCH_NUM = b.BATCH_NUM) and t.REF_NUM ="+refNum+"))";
		return ExcelUtil.getMap(query);
	}
	
}