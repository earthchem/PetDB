package admin.dbAccess;

import admin.data.ReferenceExcelDownload;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class StationByLocationQueries {
	protected DatabaseAccess da;
	protected ResultSet r_set;
	
	public StationByLocationQueries()
	{
        String schema = DatabaseAccess.getDataSourceSchema();
        try {
            da = new DatabaseAccess(schema);        
        }  catch (NamingException e) {
			    e.printStackTrace();
        } catch (SQLException e) {
			    e.printStackTrace();			    
        }        
	}
    
    public boolean update(String query){
        boolean updated = false;
        updated = da.runQuery(query);
        da.close();
        return updated;
    }
    
    private void setSearchResult(String station){
       String query = "select sl.STATION_NUM||'-'||sl.LOCATION_NUM, s.STATION_NUM||'-'||s.STATION_ID||' ('||sl.LOCATION_NUM||', order '||sl.LOCATION_ORDER||')' "+
                    "from station s, station_by_location sl where sl.STATION_NUM = s.STATION_NUM and UPPER(s.STATION_ID) like '"+station+"%' order by s.STATION_ID, sl.location_order";
      da.runQuery(query);
    }
     
    public String getSearchResultHtml(String station)
    {
        setSearchResult(station);   
        StringBuffer sb = new StringBuffer();
        sb.append("<td>Search Results:</td><td><select id=\"station-location\" onChange='selectResult()'>"); 
        boolean isEmpty = true;
        r_set = da.getResultSet();    
        try{
            while(r_set.next())
            {   
                if(isEmpty) {
                    sb.append("<option value=\"\">Please select</option>");  
                    isEmpty = false;
                }
                 sb.append("<option value=\""+r_set.getString(1)+"\">"+r_set.getString(2)+"</option>");  
            }
        } catch (SQLException ex) {
              System.err.println("getSearchResultHtml: " +ex);
        }
        finally{da.close();}
        if(isEmpty) sb.append("<option value=\"\">No data found!</option>"); 
        sb.append("</select></td>");     
        return sb.toString();
    }
    
    public String getStationLocationDS(String station_location)
    {
        String arr[] = station_location.split("-");     
        return getStationByLocation(arr[0], arr[1])+
        "<br><div><form name='locationForm' id='locationForm' action='stationByLocationServlet'>"+getLocation(arr[1])+"</form></div>";
    }
    
    private String getStationByLocation(String station, String location)
    {
        String query = "select * from Station_By_Location where station_num = "+station+" and location_num = "+location;
        da.runQuery(query) ;
        r_set = da.getResultSet(); 
       
        StringBuffer sb = new StringBuffer();
        sb.append("<div><form name='stationByLocationForm' id='stationByLocationForm' action='stationByLocationServlet'><table><caption><b>Station_By_Location</b></caption>");
        try{   
            ResultSetMetaData rsmd = r_set.getMetaData();
            while(r_set.next()) {
                for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                  String l = rsmd.getColumnName(i);
                  String v = r_set.getString(i);
                  sb.append("<tr><td>"+l+"</td><td><input type='text' name='"+l+"' id='sl_"+l+"' value='"+v+"'");
                  if(i==1) sb.append(" style='color:grey;' readonly");
                  sb.append("></td></tr>");                  
                }   
            }
        } catch (SQLException e) {
            System.err.println("getStationByLocation :"+e.getMessage());
            da.close();
        }
        sb.append("<tr><td/><td align='right'><input type='button' name='sl_update' value='Update' onClick='updateStationLocation();'/><input type='button' name='sl_delete' value='Delete' onClick='deleteStationLocation();'/></td></tr></table></form></div>");
        sb.append("<p style='font-size:10px'>LOCATION_ORDER 1 is the primary location for single point locations (e.g Holes, Cores, outcrops), and 'on' for dredges<br>LOCATION_ORDER 2 is the 'off' location for dredges</p>");
        return sb.toString();
    }
     
    public String getLocation(String location)
    {
        String query = "select * from Location where location_num = "+location;
        da.runQuery(query) ;
        r_set = da.getResultSet(); 
       
        StringBuffer sb = new StringBuffer();
        sb.append("<table><caption><b>Location</b></caption>");
        try{   
            ResultSetMetaData rsmd = r_set.getMetaData();
            while(r_set.next()) {
                for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                  String v = r_set.getString(i);
                  if(v == null) v="";
                  String n = rsmd.getColumnName(i);
                  if(i==1) sb.append("<tr><td>"+n+"</td><td><input type='text' name='"+n+"' style='color:grey;' value="+v+" readonly");
                  else if (i==9) sb.append("<tr><td>"+n+"</td><td>"+getTectonicList(v));
                  else sb.append("<tr><td>"+n+"</td><td><input type='text' name='"+n+"=' value="+v);                
                  sb.append("></td></tr>");                  
                }   
            }
        } catch (SQLException e) {
            System.err.println("getLocation :"+e.getMessage());
        } finally {
            da.close();
        }
        sb.append("<tr><td/><td align='right'><input type='button' name='l_update' value='Update' onClick='updateLocation();'/></td></tr></table>");
        return sb.toString();
    }
    
    public String getLocation(String lat, String lon)
    {
        String query = "select * from Location where latitude ="+lat+" and longitude= "+lon;
        da.runQuery(query) ;
        r_set = da.getResultSet(); 
       
        StringBuffer sb = new StringBuffer();
        boolean found = true;
        try{   
            ResultSetMetaData rsmd = r_set.getMetaData();
            if(r_set.next()) {
                for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                  String v = r_set.getString(i);
                  if(v == null) v="";
                  String n = rsmd.getColumnName(i);
                  if(i==1) sb.append("<input type='hidden' id='lookupId' name='lookupId' value='"+v+"'><table>");
                  sb.append("<tr><td>"+n+":</td><td>"+v+"</td></tr>");                     
                }   
            } else found = false;
        } catch (SQLException e) {
            System.err.println("getLocation :"+e.getMessage());
        } finally {
            da.close();
        }
        if(!found) sb.append("No data found!");
        else sb.append("<tr><td colspan='2' align='right'><input type='button' name='lookupBnt' value='Use this location for update' onClick='editLookup();'/></td></tr></table>");
        return sb.toString();
    }
    
    private String getTectonicList(String tectonicId)
    {
        String query = "select * from tectonic_setting_list order by TECTONIC_SETTING_NUM";
        DatabaseAccess da2 = null;
        StringBuffer sb = new StringBuffer("<select name ='TECTONIC_SETTING_NUM=' id ='TECTONIC_SETTING_NUM'>");
		try
		{
            da2 = new DatabaseAccess(DatabaseAccess.getDataSourceSchema(), query);
            ResultSet rs = da2.getResultSet();
			while(rs.next()) {
                String id = rs.getString(1);
                String selected ="";
                if(id.equals(tectonicId)) selected = " selected ";
				sb.append("<option value='"+id+"'"+selected+">"+id+" ("+rs.getString(2)+")</option>");
            }
		}	catch (SQLException e) {
			System.out.println("SQLException in fillSection: "+ e.getMessage());
		}	catch (Exception e) {
			System.out.println("Exception in fillSection: "+ e.getMessage());
		}
		finally {
			da2.close();
		}
        sb.append("</selected>");
        return sb.toString();
    }
 
}