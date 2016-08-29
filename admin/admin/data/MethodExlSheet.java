package admin.data;

import admin.dbAccess.DatabaseAccess;
import admin.dbAccess.SimpleQuery;
import java.util.*; 
import java.sql.*;
import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;

/* The MethodExlSheet class represents 'Methods' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class MethodExlSheet
{
	WritableSheet sheet=null;
	HashMap itemMap = new HashMap();
	HashMap methodMap = new HashMap();
	Method2DS info;
	
	int valueCol = 3;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'Methods' sheet.
	 * Output: sht will be filled.
	*/
	public MethodExlSheet(WritableSheet sht, Method2DS info) throws Exception
	{
        sheet=sht;
        this.info = info;
        /* Fill all data the sheet */
        fillSheet(info);
     //   info.close(); 
	}
	
	public HashMap getMethodMap() { return methodMap; }
	
	//public void setMethodMap(HashMap map) { methodMap = map;}
	
	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Method2DS info) throws Exception
	{

		sheet.addCell(new Label(0,0,"METHOD_NUM"));		
	    sheet.addCell(new Label(1,0,"METHOD_CODE"));
	    for(int i = 1; i <= 20; i++)
	    {
	    	sheet.addCell(new Number(1,i,i));
	    }
	    
	    sheet.addCell(new Label(2,0,"TECHNIQUE"));
	   
	    sheet.addCell(new Label(3,0,"LAB"));
	    sheet.addCell(new Label(4,0,"METHOD_COMMENT"));
 
	    sheet.addCell(new Label(3,22,"ENTER OXIDES/ELEMENTS/ISOTOPE RATIOS ANALYZED HERE"));
	    
	    sheet.addCell(new Label(0,23,"METHOD RELATED INFORMATION"));
	    sheet.addCell(new Number(3,23,-1));
	        
	    sheet.addCell(new Label(0,24,"METHOD_CODE"));
	    sheet.addCell(new Label(0,25,"UNIT"));
	    sheet.addCell(new Label(0,26,"NORMALIZATION"));
	    sheet.addCell(new Label(0,27,"STANDARD_NAME"));
	   
	    
	    sheet.addCell(new Label(0,28,"STANDARD_VALUE"));
	    sheet.addCell(new Label(1,28,"STD VALUE USED FOR NORMALIZATION"));	
	    
	    sheet.addCell(new Label(0,29,"FRACTIONATION CORRECTION (ISOTOPES)"));
	    
	    
	    sheet.addCell(new Label(0,30,"FCORR RATIO"));
	    sheet.addCell(new Label(1,30,"ISOTOPE RATIO USED FOR FRACTIONATION CORRECTION"));
	    sheet.addCell(new Label(2,30,"E.G. SR88/SR86"));
	    
	    
	    sheet.addCell(new Label(0,31,"STANDARD"));
	    sheet.addCell(new Label(1,31,"NAME OF STD USED FOR FRACT CORRECT."));	
	    sheet.addCell(new Label(0,32,"STANDARD_VALUE"));
	    sheet.addCell(new Label(1,32,"VALUE USED FOR FRACT CORRECT."));	
	    
	    sheet.addCell(new Label(0,33,"METHOD PRECISION"));
	    sheet.addCell(new Label(0,34,"PRECISION MIN"));
	    sheet.addCell(new Label(0,35,"PRECISION MAX"));
	    sheet.addCell(new Label(0,36,"PRECISION TYPE"));
	    sheet.addCell(new Label(0,37,"STANDARD DATA"));
	    sheet.addCell(new Label(0,38,"STANDARD NAME"));
	//    fillInfo(info.getResultSet());
        fillInfo(info.getList());
	    fillAllSections(info.refNum);
	}
	
	private void fillAllSections(String refNum)
	{
		
		    fillItemmeasured(refNum);
		    
		    String query ="select n.NORM_STANDARD_NAME,n.NORM_VALUE, n.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col "+
					 " from DATA_QUALITY d, NORMALIZATION N, NORMALIZATION_LIST NL "+
					 " where NL.DATA_QUALITY_NUM = D.DATA_QUALITY_NUM AND N.NORMALIZATION_NUM=NL.NORMALIZATION_NUM and d.REF_NUM = "+refNum;
		    fillSection(query, 27, 2);  
		    
		    query = "select i2.ITEM_CODE, F.FCORR_STANDARD_NAME, F.FCORR_VALUE, i.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col "+
					 " from FRACT_CORRECT F, DATA_QUALITY d, ITEM_MEASURED i,ITEM_MEASURED i2, FRACT_CORRECT_LIST FL "+
					 " where FL.data_quality_num = d.DATA_QUALITY_NUM AND F.FRACT_CORRECT_NUM = FL.FRACT_CORRECT_NUM "+
					 " and f.ITEM_FCORR_NUM = i.ITEM_MEASURED_NUM and f.ITEM_MEASURED_NUM =i2.ITEM_MEASURED_NUM and d.REF_NUM = "+refNum; 
		    fillSection(query, 30, 3);  
		    
		    query = "select m.PRECISION_MIN, m.PRECISION_MAX, m.PRECISION_TYPE, i.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col "+
					 " from METHOD_PRECIS M, DATA_QUALITY d, ITEM_MEASURED i "+
					 " where M.data_quality_num = d.DATA_QUALITY_NUM and i.ITEM_MEASURED_NUM = M.ITEM_MEASURED_NUM AND D.REF_NUM = "+refNum; 
		    fillSection(query, 34, 3);  
		    
		    fillStandard(refNum);
	}
	
   
	private void fillInfo(List list) {	
        Iterator it =  list.iterator();
        int row = 1;
		try {   
			
			while(it.hasNext())			 
			{
                String [] dt = (String[]) it.next();
				sheet.addCell(new Number(0,row,1));	
				sheet.addCell(new Label(2,row,dt[0]));	
                sheet.addCell(new Label(3,row,dt[1]));
                sheet.addCell(new Label(4,row,dt[2]));
				methodMap.put(dt[3], ""+row);
				row++;
			}
			sheet.addCell(new Number(0,row,-1));	
		}	catch (Exception e) {
			System.out.println("Exception in MethodExlSheet"+ e.getMessage());
		}
	}
	
	private void fillItemmeasured(String refNum) {
	
		String query = "select * from (select c.itemtypelist_num, d.DATA_QUALITY_NUM, lower(c.unit), i.ITEM_MEASURED_NUM "+
				" from DATA_QUALITY d, ITEM_MEASURED i, chemistry c, ANALYSIS a, NORMALIZATION N, NORMALIZATION_LIST NL "+
				" where  i.ITEM_MEASURED_NUM = n.ITEM_MEASURED_NUM "+
				" and c.ITEM_MEASURED_NUM =n.ITEM_MEASURED_NUM and a.ANALYSIS_NUM = c.ANALYSIS_NUM "+
				" and a.DATA_QUALITY_NUM= d.DATA_QUALITY_NUM "+
				" and NL.DATA_QUALITY_NUM = D.DATA_QUALITY_NUM AND N.NORMALIZATION_NUM=NL.NORMALIZATION_NUM "+
				" and d.REF_NUM =  "+ refNum +" group by c.itemtypelist_num, d.DATA_QUALITY_NUM, i.ITEM_MEASURED_NUM, lower(c.unit) "+
				" union "+
				" select c.itemtypelist_num, d.DATA_QUALITY_NUM, c.UNIT, i.ITEM_MEASURED_NUM "+
				" from FRACT_CORRECT F, DATA_QUALITY d, ITEM_MEASURED i, FRACT_CORRECT_LIST FL, chemistry c, ANALYSIS a  "+
				" where FL.data_quality_num = d.DATA_QUALITY_NUM AND F.FRACT_CORRECT_NUM = FL.FRACT_CORRECT_NUM "+
				" and f.ITEM_FCORR_NUM = i.ITEM_MEASURED_NUM and c.ITEM_MEASURED_NUM = i.ITEM_MEASURED_NUM "+
				" and a.DATA_QUALITY_NUM =d.DATA_QUALITY_NUM and a.ANALYSIS_NUM = c.ANALYSIS_NUM "+
				" AND D.REF_NUM =  "+ refNum +" group by c.itemtypelist_num, d.DATA_QUALITY_NUM, i.ITEM_MEASURED_NUM, c.UNIT "+
				" union "+
				" select c.itemtypelist_num, d.DATA_QUALITY_NUM, c.UNIT, i.ITEM_MEASURED_NUM "+
				" from METHOD_PRECIS M, DATA_QUALITY d, ITEM_MEASURED i, chemistry c, ANALYSIS a "+
				" where M.data_quality_num = d.DATA_QUALITY_NUM and i.ITEM_MEASURED_NUM = M.ITEM_MEASURED_NUM "+
				" and c.ITEM_MEASURED_NUM = i.ITEM_MEASURED_NUM "+
				" and a.ANALYSIS_NUM = c.ANALYSIS_NUM and a.DATA_QUALITY_NUM= d.DATA_QUALITY_NUM "+
				" AND D.REF_NUM =  "+ refNum +" group by c.itemtypelist_num, d.DATA_QUALITY_NUM, i.ITEM_MEASURED_NUM, c.UNIT "+
				" union "+
				" select c.itemtypelist_num, d.DATA_QUALITY_NUM, c.UNIT, i.ITEM_MEASURED_NUM "+ 
				" from standard s, DATA_QUALITY d, ITEM_MEASURED i, chemistry c, ANALYSIS a "+
				" where s.data_quality_num = d.DATA_QUALITY_NUM and i.ITEM_MEASURED_NUM = s.ITEM_MEASURED_NUM "+
				" and c.ITEM_MEASURED_NUM =s.ITEM_MEASURED_NUM and a.ANALYSIS_NUM = c.ANALYSIS_NUM and a.DATA_QUALITY_NUM =d.DATA_QUALITY_NUM "+
				" and d.REF_NUM = "+ refNum +" group by c.itemtypelist_num, d.DATA_QUALITY_NUM, i.ITEM_MEASURED_NUM, c.UNIT) order by DATA_QUALITY_NUM";
		ExcelUtil.fillItemmeasured(query,sheet,itemMap,methodMap,valueCol);
	}
	
	
	public void fillSection(String query, int row, int rows)
	{
	//	DatabaseAccess da = null;
        SimpleQuery da = new SimpleQuery(query);
		try
		{
        //    da = new DatabaseAccess(ReferenceExcelDownload.getSchema(), query);
            ResultSet rs = da.getResultSet();
			while(rs.next()) {
				Integer colNum = (Integer) itemMap.get(rs.getString(rows+1));
				if(colNum == null) continue;
                int col = colNum.intValue();
                for(int i = 0; i < rows; ++i)
                	sheet.addCell(new Label(col,i+row,rs.getString(i+1)));
			}
		}	catch (SQLException e) {
			System.out.println("SQLException in fillSection: "+ e.getMessage());
		}	catch (Exception e) {
			System.out.println("Exception in fillSection: "+ e.getMessage());
		}
		finally {
			da.close();
		}
	}
		
	public void fillStandard(String refNum)
	{
		String query = "select s.STANDARD_NAME, s.STANDARD_VALUE||', '||s.STDEV, s.ITEM_MEASURED_NUM||d.DATA_QUALITY_NUM val_col "+ 
				" from standard s, DATA_QUALITY d "+
				" where s.data_quality_num = d.DATA_QUALITY_NUM and d.REF_NUM = "+refNum+" order by s.STANDARD_NAME"; 
		
	//	DatabaseAccess da = null;
        SimpleQuery da = new SimpleQuery(query);
		int row = 38;
		String name = "";
		try
		{
     //        da = new DatabaseAccess(ReferenceExcelDownload.getSchema(), query);
             ResultSet rs = da.getResultSet();
			while(rs.next()) {
				Integer colNum = (Integer) itemMap.get(rs.getString(3));
				if(colNum == null) continue;
				int col = colNum.intValue();
				String currentName = rs.getString(1);
				if(name.equals(currentName)) {									
                	sheet.addCell(new Label(col,row,rs.getString(2)));	                	
				} else {						
					row++;						
					name = currentName;
					sheet.addCell(new Label(0,row,name));
					sheet.addCell(new Label(col,row,rs.getString(2)));	 
				}						
			}
			sheet.addCell(new Number(0,++row,-1));			
		}	catch (SQLException e) {
			System.out.println("SQLException in fillStandard: "+ e.getMessage());
		}	catch (Exception e) {
			System.out.println("Exception in fillStandard: "+ e.getMessage());
		}
		finally {
			da.close();
		}
	}
	
	
}