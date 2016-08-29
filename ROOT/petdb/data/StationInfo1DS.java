package petdb.data;

import java.util.*; 
import java.sql.*;

public class StationInfo1DS extends RowBasedDS 
{
	public static int Station_ID    	=1;
    public static int Expedition    	=2;
    public static int Expedition_id    	=3;
    public static int Samp_Tech      	=4;
    public static int Location_Name  	=5;
    public static int Station_IGSN  	=6;
    public static int station_comment   =7;
    public static int navigation_name   =8;
    public static int station_name      =9;
    
	public StationInfo1DS(ResultSet rs)
	{
		super(rs);
	}
	
}
