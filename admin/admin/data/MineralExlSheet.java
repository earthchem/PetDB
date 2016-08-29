package admin.data;

import admin.dbAccess.ReferenceDS;
import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;


/* The MineralExlSheet class represents 'Minerals' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class MineralExlSheet
{
	WritableSheet sheet=null;
	HashMap methodMap;
	HashMap itemMap = new HashMap();
	//int valueCol = 10;
	int valueCol = 11;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'Minerals' sheet.
	 * Output: sht will be filled.
	*/
	public MineralExlSheet(WritableSheet sht, Mineral2DS info, HashMap map) throws Exception
	{
        sheet=sht;
        methodMap = map;
        /* Fill all data the sheet */
        fillSheet(info);
     //   info.close(); 
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Mineral2DS info) throws Exception
	{
		sheet.addCell(new Label(0,0,"MINERAL DATA"));			        	    
	    sheet.addCell(new Label(11,1,"ENTER OXIDES/ELEMENTS/ISOTOPE RATIOS ANALYZED HERE"));		
	    sheet.addCell(new Number(11,2,-1));
	    sheet.addCell(new Label(8,3,"METHOD_CODE"));
	    sheet.addCell(new Label(9,3,"AS USED IN \"METHODS\""));   
	    sheet.addCell(new Label(8,4,"UNIT"));   
	    sheet.addCell(new Label(2,5,"SAMPLE DATA"));
	    sheet.addCell(new Label(11,5,"CHEMISTRY DATA"));
	    
	    sheet.addCell(new Label(0,6,"ANALYSES NO."));
	    sheet.addCell(new Label(1,6,"TAB_IN_REF"));
	    sheet.addCell(new Label(2,6,"SAMPLE NAME"));
	    sheet.addCell(new Label(3,6,"ANALYSIS COMMENT"));
	    sheet.addCell(new Label(4,6,"SPOT_ID"));
	    sheet.addCell(new Label(5,6,"NUMBER_OF_REPLICATES"));
	    sheet.addCell(new Label(6,6,"CALC_AVGE"));
	    sheet.addCell(new Label(7,6,"MINERAL"));
	    sheet.addCell(new Label(8,6,"GRAIN"));
	    sheet.addCell(new Label(9,6,"RIM/CORE"));
	    sheet.addCell(new Label(10,6,"MINERAL SIZE"));
	    
	    sheet.addCell(new Label(2,7,"AS USED IN \"SAMPLES\"")); 
	    sheet.addCell(new Label(5,7,"IF AVERAGE"));
	    sheet.addCell(new Label(6,7,"Y-----Can be averaged   N-----Can't be A-----It is an average"));
	    sheet.addCell(new Label(7,7,"PLAG, OL, CPX, OPX, SP, AMPH"));	    
	    sheet.addCell(new Label(8,7,"PC=PHENOCRYST, MC=MICRO-PC, XC=XENOCRYST, GM=GROUNDMASS"));
	    sheet.addCell(new Label(9,7,"R=RIM,        C=CORE    I=INTERMEDIATE"));
	    sheet.addCell(new Label(10,7,"DESCRIPTION ONLY, NOT GOING TO BE USED FOR QUERY"));
        if(info.getList().size() == 0) {
            sheet.addCell(new Number(2,8,-1));	        
        } else { 
	    int size = 1101;
	    int offset = 8;
	    for(int i=0; i < size; ++i)
	    {
	    	sheet.addCell(new Number(0,offset+i,i+1));
	    }             
            fillItemmeasured(info.refNum);
	//    ExcelUtil.fillDataTosheet(info.getResultSet(),sheet,itemMap,null,valueCol);
    
   //     ExcelUtil.fillDataTosheet(info.getList(ReferenceDS.getMineral2DS(info.refNum),12),sheet,itemMap,null,valueCol);
            ExcelUtil.fillDataTosheet(info.getList(),sheet,itemMap,null,valueCol);
        }
    }
	
	private void fillItemmeasured(String refNum) {
		String query = "select c.itemtypelist_num, d.DATA_QUALITY_NUM, lower(c.unit), i.ITEM_MEASURED_NUM from data_quality d, ANALYSIS a, ITEM_MEASURED I, CHEMISTRY C "+
				" where a.DATA_QUALITY_NUM = d.DATA_QUALITY_NUM and d.ref_num = "+ refNum + 
				" AND I.ITEM_MEASURED_NUM = C.ITEM_MEASURED_NUM AND A.ANALYSIS_NUM = C.ANALYSIS_NUM "+
				" and a.BATCH_NUM in "+
				" (select b.BATCH_NUM from batch b, table_in_ref t, mineral m "+
				" where b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and m.BATCH_NUM = b.BATCH_NUM and t.REF_NUM ="+ refNum + ") "+
				" group by c.itemtypelist_num, i.ITEM_MEASURED_NUM, d.DATA_QUALITY_NUM, lower(c.unit) order by d.DATA_QUALITY_NUM";
		ExcelUtil.fillItemmeasured(query,sheet,itemMap,methodMap,valueCol);
	}

}