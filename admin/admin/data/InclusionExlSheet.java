package admin.data;

import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;

/* The InclusionExlSheet class represents 'Inclusions' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class InclusionExlSheet
{
	WritableSheet sheet=null;
	HashMap methodMap;
	HashMap itemMap = new HashMap();
	int valueCol = 15;
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'Inclusions' sheet.
	 * Output: sht will be filled.
	*/
	public InclusionExlSheet(WritableSheet sht, Inclusion2DS info, HashMap map) throws Exception
	{
        sheet=sht;
        methodMap = map;
        /* Fill all data the sheet */
        fillSheet(info);
     //   info.close(); 
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Inclusion2DS info) throws Exception
	{
		sheet.addCell(new Label(0,0,"ROCK DATA"));		
		sheet.addCell(new Label(15,1,"ENTER OXIDES/ELEMENTS/ISOTOPE RATIOS ANALYZED HERE"));		
	    sheet.addCell(new Label(15,2,"-1"));
	    
	    sheet.addCell(new Label(12,3,"METHOD_CODE"));
	    sheet.addCell(new Label(13,3,"AS USED IN \"METHODS\""));   
	    sheet.addCell(new Label(12,4,"UNIT"));
	    
	    sheet.addCell(new Label(0,5,"SAMPLE DATA"));
	    sheet.addCell(new Label(15,5,"CHEMISTRY DATA"));
	        
	    sheet.addCell(new Label(0,6,"ANALYSES NO."));
	    sheet.addCell(new Label(1,6,"TAB_IN_REF"));
	    sheet.addCell(new Label(2,6,"SAMPLE NAME"));
	    sheet.addCell(new Label(3,6,"SPOT_ID"));
	    sheet.addCell(new Label(4,6,"NUMBER_OF_REPLICATES"));
	    sheet.addCell(new Label(5,6,"CALC_AVGE"));
	    sheet.addCell(new Label(6,6,"INCLUSION_TYPE"));
	    sheet.addCell(new Label(7,6,"INCLUSION_MINERAL"));
	    sheet.addCell(new Label(8,6,"HOST_MINERAL"));
	    sheet.addCell(new Label(9,6,"HOST_MINERAL2"));
	    sheet.addCell(new Label(10,6,"HOST_ROCK"));
	    sheet.addCell(new Label(11,6,"INCLUSION_SIZE"));
	    sheet.addCell(new Label(12,6,"RIM/CORE"));
	    sheet.addCell(new Label(13,6,"HEATED"));
	    sheet.addCell(new Label(14,6,"TEMPERATURE"));
	    
	    sheet.addCell(new Label(2,7,"AS USED IN \"SAMPLES\""));
	    sheet.addCell(new Label(4,7,"IF AVERAGE"));
	    sheet.addCell(new Label(5,7,"Y-----Can be averaged   N-----Can't be A-----It is an average"));
	    sheet.addCell(new Label(6,7,"GLASS     MINERAL     FLUID"));
	    sheet.addCell(new Label(7,7,"PLAG                          CPX                              OPX                                OL                                       SP"));
	    sheet.addCell(new Label(8,7,"PLAG                          CPX                              OPX                                OL                                       SP"));
	    sheet.addCell(new Label(9,7,"ANALYSES NO. OF THE HOSTMINERAL (IF AVAILABLE)"));
	    sheet.addCell(new Label(10,7,"ANALYSES NO. OF THE HOSTROCK (IF AVAILABLE)"));	    
	    sheet.addCell(new Label(12,7,"R=RIM,        C=CORE    I=INTERMEDIATE"));
	    sheet.addCell(new Label(13,7,"YES OR NO"));
	    sheet.addCell(new Label(14,7,"IN DEGREES CELCIUS"));
        if(info.getList().size() == 0) {
            sheet.addCell(new Number(2,8,-1));	
         
        } else { 
	    int size = 1189;
	    int offset = 8;
	    for(int i=0; i < size; ++i)
	    {
	    	sheet.addCell(new Number(0,offset+i,(i+1)));
	    }    
	    fillItemmeasured(info.refNum);
	    HashMap mineralMap = getMineralMap(info.refNum);
	//    ExcelUtil.fillDataToInclusionSheet(info.getResultSet(),sheet,itemMap,mineralMap,valueCol);
        ExcelUtil.fillDataToInclusionSheet(info.getList(),sheet,itemMap,mineralMap,valueCol);
        }
    }
	
	private void fillItemmeasured(String refNum) {	
		String query = "select c.itemtypelist_num, d.DATA_QUALITY_NUM, lower(c.unit), i.ITEM_MEASURED_NUM from data_quality d, ANALYSIS a, ITEM_MEASURED I, CHEMISTRY C "+
				" where a.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM and d.ref_num = "+ refNum + 
				" AND I.ITEM_MEASURED_NUM = C.ITEM_MEASURED_NUM AND A.ANALYSIS_NUM = C.ANALYSIS_NUM "+
				" and a.BATCH_NUM in "+
				" (select b.BATCH_NUM from batch b, table_in_ref t, inclusion m "+
				" where b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and m.BATCH_NUM = b.BATCH_NUM and t.REF_NUM ="+ refNum + ") "+
				" group by c.itemtypelist_num, i.ITEM_MEASURED_NUM, d.DATA_QUALITY_NUM, lower(c.unit) order by d.DATA_QUALITY_NUM";
		ExcelUtil.fillItemmeasured(query,sheet,itemMap,methodMap,valueCol);
	}
	
	private HashMap getMineralMap(String refNum)
	{
		String query = "select b.BATCH_NUM from batch b, TABLE_IN_REF t, mineral mi "+	
				" where t.TABLE_IN_REF_NUM =b.TABLE_IN_REF_NUM and mi.BATCH_NUM = b.BATCH_NUM and t.REF_NUM = "+ refNum+
				" group by t.TABLE_IN_REF_NUM, b.BATCH_NUM order by t.TABLE_IN_REF_NUM, b.BATCH_NUM ";  		
		return ExcelUtil.getMineralMap(query);
	}
    
}