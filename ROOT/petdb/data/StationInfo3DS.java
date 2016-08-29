package petdb.data;

import java.util.*; 
import java.sql.*;

public class StationInfo3DS extends RowBasedDS 
{
        public static int Location_Order  	=1;
        public static int Latitude 	 	    =2;
        public static int Longitude  		=3;
        public static int Elevation_Min  	=4;
        public static int Elevation_Max  	=5;
        public static int Land_Sea  		=6;
        public static int Tectonic_Name  	=7;
        public static int Location_comment  =8;
        public static int LocationOrder_num =9;
        public static int Loc_precision     =10;

	public StationInfo3DS(ResultSet rs)
	{
		super(rs);
	}
	
}
