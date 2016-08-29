package admin.data;


import admin.dbAccess.DatabaseAccess;
import admin.dbAccess.SimpleQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableCell;


public class ExcelUtil
{
	public static WritableCellFormat getFloatFormat(String number)
	{
		
		int position = number.indexOf(".");
		String format = null;
		if(position != -1)
		{
			int size =number.length()- position;
			format= "#.";
			for(int i = 2; i < size; i++) format += "#";
	        if(number.endsWith("0")) format += "0";
	        else format += "#";            
	        return new WritableCellFormat( new NumberFormat(format));
		} else return new WritableCellFormat( NumberFormats.INTEGER);
	}
	
	 public static boolean isNumber( String num )
     {
         try {
            new Float( num );
             return true;
         }
         catch( Exception e ) {
             return false;
         }
     }

	
	public static void fillDataToInclusionSheet(ResultSet rs, WritableSheet sheet, HashMap itemMap, HashMap mineralMap, int valueCol)
	{
		// inc.MINERAL_BATCH_NUM column 9
			int row = 7;
			try{	
			int previous = 999;
				while(rs.next())
				{	
					int current = rs.getInt(1);
					
					if(previous != current)
					{
						row++;
						previous = current;
						String tabInRef = rs.getString(2);
						sheet.addCell(new Label(1,row, tabInRef));
				
						//sample name
							String alias = (String)rs.getString("alias");
							sheet.addCell(new Label(2,row, alias));			
						for(int i= 3; i < valueCol; i++) {	
							if(i==4 && rs.getInt(i) != 0) sheet.addCell(new Number(i,row, rs.getInt(i)));
							else if (i==9 && rs.getInt(i) != 0)
							{
								String mKey = rs.getString(i);
								if(mineralMap.containsKey(mKey)) {
									String mValue = (String)mineralMap.get(mKey);
													sheet.addCell(new Number(i,row, new Integer(mValue).intValue()));
								} else {
													sheet.addCell(new Label(i,row, ""));
								}
							}
							else sheet.addCell(new Label(i,row, rs.getString(i)));		
						}
					}	 
					String value =  rs.getString(valueCol);
					String key = rs.getString("val_col");					
					if(itemMap.containsKey(key)) {
						Integer kval = (Integer)itemMap.get(key);
						if(kval == null) continue;
						int itemCol = kval.intValue();
                        sheet.addCell(new Label(itemCol,row, value));
					}
				}
				sheet.addCell(new Number(2, ++row, -1));
			}
			catch (SQLException e) {
				System.out.println("SQLException in fillDataTosheet of fillDataToInclusionSheet: "+ e.getMessage());
			}	catch (Exception e) {
				System.out.println("Exception in fillDataTosheet of fillDataToInclusionSheet: "+ e.getMessage());
			}
	}
    public static void fillDataToInclusionSheet(List list, WritableSheet sheet, HashMap itemMap, HashMap mineralMap, int valueCol)
	{
            Iterator it  = list.iterator();
			int row = 7;
			try{	
			int previous = 999;
				while(it.hasNext())
                {	
                    String []dt = (String[])it.next();
				//	int current = rs.getInt(1);
					int current = new Integer(dt[0]).intValue();
					if(previous != current)
					{
						row++;
						previous = current;
						//String tabInRef = rs.getString(2);
                        String tabInRef = dt[1];
						sheet.addCell(new Label(1,row, tabInRef));
				
						//sample name
						//	String alias = (String)rs.getString("alias");
                            String alias =  dt[16]; // alias
							sheet.addCell(new Label(2,row, alias));			
						for(int i= 3; i < valueCol; i++) {	
						//	if(i==4 && rs.getInt(i) != 0) sheet.addCell(new Number(i,row, rs.getInt(i)));
                            if(i==4 && !"0".equals(dt[i-1])) sheet.addCell(new Label(i,row, dt[i-1]));
							//else if (i==9 && rs.getInt(i) != 0)
                             if(i==9 && !"0".equals(dt[i-1]))
							{
							//	String mKey = rs.getString(i);
                                String mKey = dt[i-1];
								if(mineralMap.containsKey(mKey)) {
									String mValue = (String)mineralMap.get(mKey);
												//	sheet.addCell(new Number(i,row, new Integer(mValue).intValue()));
                                                sheet.addCell(new Label(i,row, mValue));
								} else {
													sheet.addCell(new Label(i,row, ""));
								}
							}
						//	else sheet.addCell(new Label(i,row, rs.getString(i)));		
                        else sheet.addCell(new Label(i,row, dt[i-1]));		
						}
					}	 
					//String value =  rs.getString(valueCol);
                    String value =  dt[valueCol-1];
			//		String key = rs.getString("val_col");	
                    String key = dt[valueCol];						
					if(itemMap.containsKey(key)) {
						Integer kval = (Integer)itemMap.get(key);
						if(kval == null) continue;
						int itemCol = kval.intValue();
                        sheet.addCell(new Label(itemCol,row, value));
					}
				}
				sheet.addCell(new Number(2, ++row, -1));
			}
            	catch (Exception e) {
				System.out.println("Exception in fillDataTosheet of fillDataToInclusionSheet: "+ e.getMessage());
			}
	}
    
    public static void fillDataTosheet(List list, WritableSheet sheet, HashMap itemMap, HashMap alias, int valueCol)
	{
				int row = 7;
				try{	
				int previous = 999;
                Iterator it = list.iterator();
					while(it.hasNext())
					{	
						String []dt = (String[])it.next();
						int current = new Integer(dt[0]).intValue();
                      
						if(previous != current)
						{
							row++;
							previous = current;
							String tabInRef = dt[1];
							sheet.addCell(new Label(1,row, tabInRef));
							//sample name
							if(alias == null) {	  //Mineral
                              //  sheet.addCell(new Label(2,row, (String)rs.getString("alias")));	
                           //   sheet.addCell(new Label(2,row, dt[11]));			
                               sheet.addCell(new Label(2,row, dt[12]));		
							} else {		
                                  			
								if(alias.containsKey(""+current)) {
                                    sheet.addCell(new Label(2,row, (String)alias.get(""+current)));	
                                }
							}
							for(int i= 3; i < valueCol; i++) {	
                            sheet.addCell(new Label(i,row, dt[i-1]));
							}
						}	 
			
						String value =  dt[valueCol-1];
					//	String key = rs.getString("val_col");	
                  //  String key = dt[10];		
                    String key = dt[valueCol];				
						if(itemMap.containsKey(key)) {
							int itemCol = ((Integer)itemMap.get(key)).intValue();
                            sheet.addCell(new Label(itemCol,row, value));
						}
					}
					sheet.addCell(new Number(2, ++row, -1));
				}
			//	catch (SQLException e) {
			//		System.out.println("SQLException in fillDataTosheet of fillDataTosheet: "+ e.getMessage());
			//	}	
                catch (Exception e) {
					System.out.println("Exception in fillDataTosheet of fillDataTosheet: "+ e.getMessage());
				}	            	
           // }		
		}
			
	public static void fillDataTosheet(ResultSet rs, WritableSheet sheet, HashMap itemMap, HashMap alias, int valueCol)
	{
				int row = 7;
				try{	
				int previous = 999;
					while(rs.next())
					{	
						
						int current = rs.getInt(1);
					
						if(previous != current)
						{
							row++;
							previous = current;
							String tabInRef = rs.getString(2);
							sheet.addCell(new Label(1,row, tabInRef));
							//sample name
							if(alias == null) {	
                                sheet.addCell(new Label(2,row, (String)rs.getString("alias")));			
							} else {		
                                  			
								if(alias.containsKey(""+current)) {
                                    sheet.addCell(new Label(2,row, (String)alias.get(""+current)));	
                                }
							}
							for(int i= 3; i < valueCol; i++) {	
                            sheet.addCell(new Label(i,row, rs.getString(i)));
							}
						}	 
			
						String value =  rs.getString(valueCol);
						String key = rs.getString("val_col");					
						if(itemMap.containsKey(key)) {
							int itemCol = ((Integer)itemMap.get(key)).intValue();
                            sheet.addCell(new Label(itemCol,row, value));
						}
					}
					sheet.addCell(new Number(2, ++row, -1));
				}
				catch (SQLException e) {
					System.out.println("SQLException in fillDataTosheet of fillDataTosheet: "+ e.getMessage());
				}	catch (Exception e) {
					System.out.println("Exception in fillDataTosheet of fillDataTosheet: "+ e.getMessage());
				}	            	
                finally {
                    try {
                        if(rs != null) rs.close();
                    }
                    catch (Exception e) {
					System.out.println("SQLException in fillDataTosheet of fillDataTosheet close(): "+ e.getMessage());
                    }   	
            }		
		}
	 	
		public static void fillItemmeasured(String query, WritableSheet sheet, HashMap itemMap, HashMap methodMap, int valueCol) {
			//DatabaseAccess da = null;
            SimpleQuery da = new SimpleQuery(query);
			int col = valueCol;
			int row = 2;
			if(valueCol == 3) row = 23;
			
			try
			{
             //   da = new DatabaseAccess(ReferenceExcelDownload.getSchema(), query);
                ResultSet rs = da.getResultSet();
				while(rs.next()) {          
					String methodCode = (String) methodMap.get(rs.getString(2));
                    if(methodCode == null) continue;
                    itemMap.put(rs.getString(4)+rs.getString(2), new Integer(col));
                    String codeType = (String) ReferenceExcel.itemCodeTypeMap.get(rs.getString(1));
                    sheet.addCell(new Label(col,row,codeType));
					sheet.addCell(new Number(col,row+1,new Integer(methodCode).intValue()));	
					sheet.addCell(new Label(col,row+2,rs.getString(3)));	
					col++;
				}
				sheet.addCell(new Number(col,row,-1));	
			}	catch (SQLException e) {
				System.out.println("SQLException in fillItemmeasured"+ e.getMessage());
			}	catch (Exception e) {
				System.out.println("Exception in fillItemmeasured"+ e.getMessage());
			}
			finally {
				da.close();
			}
		}

        public static HashMap getMap(String query) {
            //DatabaseAccess da = null;
            SimpleQuery da = new SimpleQuery(query);
			HashMap map = new HashMap();
			try
			{
            //    da = new DatabaseAccess(ReferenceExcelDownload.getSchema(), query);
                ResultSet rs = da.getResultSet();
				while(rs.next()) {
					map.put(rs.getString(1), rs.getString(2));
				}	
			//}	catch (NamingException e) {
			 //   e.printStackTrace();
            } catch (SQLException e) {
			    e.printStackTrace();			    
            }        
            finally {
				da.close();			
			}
			return map;				
		}
        
		
		// Mineral tab order map
		public static HashMap getMineralMap(String query)
		{
			HashMap map = new HashMap();
	//		DatabaseAccess da = null;
            SimpleQuery da = new SimpleQuery(query);
			int order = 0;
			try
			{
       //         da = new DatabaseAccess(ReferenceExcelDownload.getSchema(), query);
                ResultSet rs = da.getResultSet();
				while(rs.next()) {
					map.put(rs.getString(1), ""+ ++order);
				}	
			}	
            catch (SQLException e) {
				System.out.println("SQLException in getMap"+ e.getMessage());
			//}	catch (NamingException e) {
			    //e.printStackTrace();
            } 
			
			finally {
				da.close();			
			}
			return map;
		}
		
		public static WritableCell getFormatCell(int col, int row, String val)
		{
			if(ExcelUtil.isNumber(val))  {
				
				return new Number(col,row, new Integer(val).intValue());
			}
			return new Label(col,row, val);
		}
}