package admin.data;


import admin.dbAccess.DatabaseAccess;
import admin.dbAccess.ReferenceDS;
import admin.dbAccess.SimpleQuery;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.write.*;
import jxl.write.Number;


/* The StationExlSheet class represents 'STATION' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class StationExlSheet
{
	WritableSheet sheet=null;
	HashMap heading = new HashMap();
    HashMap geographMap = new HashMap();
    ReferenceDS info;
	int basicCols = 20; 
	int colSize = basicCols; //current column size
	int rows = 1;  //current row number
    private static final int STATION_ID =0;     
    private static final int LATITUDE =9;
    private static final int LONGITUDE =10;
    private static final int ELEVATION_MIN =11;   
    private static final int ELEVATION_MAX =12;    
    private static final int LOCATION_NUM =17;    
    private static final int LOCATION_ORDER =18;
   
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'STATION' sheet.
	 * Output: sht will be filled.
	*/
	public StationExlSheet(WritableSheet sht, Station2DS info) throws Exception
	{
        sheet=sht;
        this.info = info;
        /* Fill all data the sheet */
        createGeographMap(info.refNum);
        fillSheet(info);
       // info.close();       
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Station2DS info) throws Exception
	{
		sheet.addCell(new Label(0,0,"STATION_ID"));
	    sheet.addCell(new Label(1,0,"EXPEDITION_NUM"));
	    sheet.addCell(new Label(2,0,"STATION_NAME"));
	    sheet.addCell(new Label(3,0,"CRUISE_NAME"));
	    sheet.addCell(new Label(4,0,"LEG"));
	    sheet.addCell(new Label(5,0,"STATION_COMMENT"));
	    sheet.addCell(new Label(6,0,"SAMPLING TECHNIQUE"));
	    sheet.addCell(new Label(7,0,"NAVIGATION METHOD"));
	    sheet.addCell(new Label(8,0,"SAMPLING_DATE"));
	    sheet.addCell(new Label(9,0,"LATITUDE ON"));
	    sheet.addCell(new Label(10,0,"LONGITUDE ON"));
	    sheet.addCell(new Label(11,0,"ELEVATION_MIN"));
	    sheet.addCell(new Label(12,0,"ELEVATION_MAX"));
	    sheet.addCell(new Label(13,0,"LATITUDE OFF"));
	    sheet.addCell(new Label(14,0,"LONGITUDE OFF"));
	    sheet.addCell(new Label(15,0,"LAT_LONG_PRECISION"));
	    sheet.addCell(new Label(16,0,"ELEVATION_MIN"));
	    sheet.addCell(new Label(17,0,"ELEVATION_MAX"));
	    sheet.addCell(new Label(18,0,"LOCATION_COMMENT"));
	    sheet.addCell(new Label(19,0,"LAND_OR_SEA"));
	    sheet.addCell(new Label(20,0,"TECTONIC_SETTING"));
	
	    sheet.addCell(new Label(0,1,"PLEASE, LEAVE EMPTY FOR USE BY DBA ONLY"));
	    sheet.addCell(new Label(1,1,"PLEASE, LEAVE EMPTY FOR USE BY DBA ONLY"));
	    sheet.addCell(new Label(2,1,"PREFERRED FORMAT:   CRUISE-DREDGE/ROCKCORE#, E.G. AII107-020"));
	    sheet.addCell(new Label(3,1,"ALSO GO TO CRUISE SHEET"));
	    sheet.addCell(new Label(6,1,"DR=DREDGE, RC=ROCK CORE, DC=DRILL CORE, SU=SUBMERSIBLE OC=OUTCROP"));
	    sheet.addCell(new Label(7,1,"GPS     SAT=SATELLITE  SB=SEABEAM  LORAN_A        LORAN_C"));
	    sheet.addCell(new Label(9,1,"PLEASE, USE DECIMALS,    NEGATIVE NUMBERS FOR SOUTH"));
	    sheet.addCell(new Label(10,1,"PLEASE, USE DECIMALS,  NEGATIVE NUMBERS FOR WEST"));
	    sheet.addCell(new Label(11,1,"METERS, NEGATIVE NUMBERS FOR DEPTH BELOW SEA LEVEL"));
	    sheet.addCell(new Label(12,1,"METERS, NEGATIVE NUMBERS FOR DEPTH BELOW SEA LEVEL"));
	    sheet.addCell(new Label(13,1,"PLEASE, USE DECIMALS,    NEGATIVE NUMBERS FOR SOUTH"));
	    sheet.addCell(new Label(14,1,"PLEASE, USE DECIMALS,  NEGATIVE NUMBERS FOR WEST"));
	    sheet.addCell(new Label(15,1,"NUMBER OF SIGNIFICANT DIGITS"));
	    sheet.addCell(new Label(16,1,"METERS, NEGATIVE NUMBERS FOR DEPTH BELOW SEA LEVEL"));
	    sheet.addCell(new Label(17,1,"METERS, NEGATIVE NUMBERS FOR DEPTH BELOW SEA LEVEL"));
	    sheet.addCell(new Label(18,1,"E.G. WEST WALL OF AXIAL VALLEY"));
	    sheet.addCell(new Label(19,1,"SAQ=SUBAQUATIC SAE=SUBAERIAL"));
	 //   fillDataTosheet(info.getResultSet());
       fillDataTosheet(info.getList());
	}
    
    	private void fillDataTosheet(List list)
		{
            Iterator it = list.iterator();
			try{		
				while(it.hasNext())
				{
					String dt[] = (String[] )it.next();
                    String stationId = dt[STATION_ID];
                    
					String order = dt[LOCATION_ORDER];	
					if("1".equals(order))
					{                 
						rows++;
						for(int i= 0, j= 1; i <= basicCols; i++)
						{
							
								if(i==13 || i==14 || i==16 || i==17) continue;
								else if (i == 15)
								{
                                    String number = null;
                                    if((number = dt[j-1])!= null) {
                                       sheet.addCell(new Label(i, rows, number));
                                    }
                                    j++;
								}
								else 
									sheet.addCell(new Label(i, rows, dt[j++-1]));
						}                      
						String locationNum =dt[LOCATION_NUM]; 
						if(locationNum != null) writeGeographData(locationNum);
					}
                    else if ("2".equals(order)) {
                        sheet.addCell(new Label(13, rows, dt[LATITUDE]));
						sheet.addCell(new Label(14, rows, dt[LONGITUDE]));
						sheet.addCell(new Label(16, rows, dt[ELEVATION_MIN]));
						sheet.addCell(new Label(17, rows, dt[ELEVATION_MAX]));
					} 
				}		
				sheet.addCell(new Number(0,++rows, -1));
				sheet.addCell(new Number(++colSize,0,-1));	
             
			}
		/*	catch (SQLException e) {
				System.out.println("SQLException in fillDataTosheet of StationExlSheet "+ e.getMessage());
			}	*/
            catch (Exception e) {
				System.out.println("Exception in fillDataTosheet of StationExlSheet "+ e.getMessage());
			} 
		}
/*	
	private void fillDataTosheet(ResultSet rs)
		{
			try{		
				while(rs.next())
				{
					String stationId = rs.getString("STATION_ID");
					int order = rs.getInt("LOCATION_ORDER");	
					if(order == 1)
					{                 
						rows++;
						for(int i= 0, j= 1; i <= basicCols; i++)
						{
							
								if(i==13 || i==14 || i==16 || i==17) continue;
								else if (i == 15)
								{
                                    String number = null;
                                    if((number = rs.getString(j))!= null) {
                                       sheet.addCell(new Label(i, rows, number));
                                    }
                                    j++;
								}
								else 
									sheet.addCell(new Label(i, rows, rs.getString(j++)));
						}                      
						String locationNum =rs.getString("LOCATION_NUM"); 
						if(locationNum != null) writeGeographData(locationNum);
					} else if (order == 2) {
						sheet.addCell(new Label(13, rows, rs.getString("LATITUDE")));
						sheet.addCell(new Label(14, rows, rs.getString("LONGITUDE")));
						sheet.addCell(new Label(16, rows, rs.getString("ELEVATION_MIN")));
						sheet.addCell(new Label(17, rows, rs.getString("ELEVATION_MAX")));
					} 
				}		
				sheet.addCell(new Number(0,++rows, -1));
				sheet.addCell(new Number(++colSize,0,-1));	
             
			}
			catch (SQLException e) {
				System.out.println("SQLException in fillDataTosheet of StationExlSheet "+ e.getMessage());
			}	catch (Exception e) {
				System.out.println("Exception in fillDataTosheet of StationExlSheet "+ e.getMessage());
			} finally {
                info.close();
            }
		}
*/		
	private void writeGeographData(String locationNum) {
        if(geographMap.containsKey(locationNum)){
            HashMap map = (HashMap) geographMap.get(locationNum);
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                int cols = new Integer((String)pairs.getKey()).intValue();
                try {
                sheet.addCell(new Label(cols,rows,(String)pairs.getValue()));	
                } catch (Exception e) {
                    System.out.println("Exception in writeGeographData of StationElSheet"+ e.getMessage());
                }
               // it.remove(); // avoids a ConcurrentModificationException
            }
		}
    } 
   	
		private void createGeographMap(String ref)
		{
            String query = "select l.LOCATION_NUM,gl.LOCATION_TYPE, gl.LOCATION_NAME "+
                "from STATION s INNER JOIN STATION_BY_LOCATION sl ON s.STATION_NUM = sl.STATION_NUM "+				
                "INNER JOIN LOCATION l ON sl.LOCATION_NUM = l.LOCATION_NUM LEFT OUTER JOIN GEOGRAPH_LOC gl on l.LOCATION_NUM = gl.LOCATION_NUM "+
                "where sl.LOCATION_ORDER <> 5 and s.STATION_NUM IN (SELECT SA.STATION_NUM FROM SAMPLE sa, BATCH ba, TABLE_IN_REF ti "+
                "WHERE  ba.SAMPLE_NUM = sa.SAMPLE_NUM and ba.TABLE_IN_REF_NUM = ti.TABLE_IN_REF_NUM and ti.REF_NUM ="+ref+") order by l.LOCATION_NUM";
          //  DatabaseAccess da = null;
            SimpleQuery da = new SimpleQuery(query);
			try
			{
               // da = new DatabaseAccess(ReferenceExcelDownload.getSchema(), query);
                ResultSet rs = da.getResultSet();
                int previous = 0;
                int current = 0;  //location num
                HashMap locationMap = null;
				while(rs.next()) {
                    current = rs.getInt(1);                
                    if(current != previous)
                    {
                        locationMap = new HashMap();
                        geographMap.put(""+current, locationMap);
                    }
					String key = rs.getString("LOCATION_TYPE");
                    int cols = 0;
					if(heading.containsKey(key)){
						cols = new Integer((String)heading.get(key)).intValue();
					} else {
						sheet.addCell(new Label(++colSize,0,key));			
						heading.put(key, ""+colSize);
                        cols = colSize;
					} 
                    locationMap.put(""+cols,rs.getString("LOCATION_NAME"));
                    previous = current;
				}
			}	catch (SQLException e) {
				System.out.println("SQLException in createGeographMap: "+ e.getMessage());
			}	catch (Exception e) {
				System.out.println("Exception in createGeographMap: "+ e.getMessage());
			}
			finally
			{	
				da.close();		
			}   
    	}
		
}