package admin.data;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.sql.*;

import admin.config.*;
import admin.dbAccess.*;

public class StationByLocationServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException 
    {
        doPost(request,response);
    }
            
	public void doPost(HttpServletRequest request,HttpServletResponse response)
              throws ServletException, IOException 
    {
        String station_id =request.getParameter("station_id");    
        String station_location =request.getParameter("station_location");         
        String sl_u = request.getParameter("sl_update");
        String sl_d = request.getParameter("sl_delete");
        String l_u = request.getParameter("l_update");
        String lookupId = request.getParameter("lookupId");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
    
        PrintWriter out = response.getWriter();    
        StationByLocationQueries ds = new StationByLocationQueries(); 
        if(station_id != null)
        {                 
            out.println(ds.getSearchResultHtml(station_id));
        } else if (station_location != null) {
            out.println(ds.getStationLocationDS(station_location));
        } else if (request.getParameter("STATION_NUM") != null) {
            ds.update(updateStationByLocationQuery(request));
            out.println("Updated Station_By_Location table!");
        } else if (request.getParameter("del_station_location") != null) {
            ds.update(deleteStationByLocationQuery(request));
            out.println("Deleted from Station_By_Location!");
        } else if (request.getParameter("LOCATION_NUM") != null) {       
            ds.update(updateLocationQuery(request));
            out.println("Updated Location table!");
        } else if (request.getParameter("lat") != null) {    
            out.println(ds.getLocation(request.getParameter("lat"), request.getParameter("lon")));           
        } else if (lookupId != null) {    
           out.println(ds.getLocation(lookupId));           
        }
    }
    
    private String updateLocationQuery(HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer("update location set ");
        
        Enumeration enumeration = request.getParameterNames();
        int i = 0;
        while (enumeration.hasMoreElements()) 
        {          
            String n = (String)enumeration.nextElement();
            if( n.endsWith("=")) {
                String v = request.getParameter(n);
                if( i++ != 0) sb.append(",");
                sb.append(n+"'"+v+"'");
            }
        }
                                                                
        sb.append(" where location_num = "+request.getParameter("LOCATION_NUM"));
        return sb.toString();        
    }
    
    private String updateStationByLocationQuery(HttpServletRequest request)
    {
        String station_location = request.getParameter("stationLocation");
        String arr[] = station_location.split("-");
        String locationNum = request.getParameter("LOCATION_NUM");
        String locationOrder = request.getParameter("LOCATION_ORDER");      
        String q = "update Station_by_location set location_num="+locationNum+",location_order="+locationOrder+
            " where station_num = "+arr[0]+" and location_num="+arr[1];
        return q;        
    }
    
     private String deleteStationByLocationQuery(HttpServletRequest request)
    {
        String station_location = request.getParameter("del_station_location");
        String arr[] = station_location.split("-");     
        String q = "delete Station_by_location where station_num = "+arr[0]+" and location_num="+arr[1];
        return q;        
    }
}

