package petdb.data;

import java.util.*; 
import java.sql.*;

public class SampleIDDS extends RowBasedDS 
{
	public static int KEY           =1;
	public static int SAMPLE_ID     =2;
    public static int STATION_ID    =3;
    public static int ROCK_DESC     =4;
    public static int ALTERATION    =5;
    public static int SAMPLING      =6;
    public static int LATITUDE      =7;
    public static int LONGITUDE     =8;
    public static int ELEV_MIN      =9;
    public static int ELEV_MAX      =10;
    public static int DATA_E        =11;
    public static int STATION_NUM   =12;
    public static int IGSN          =13;

	public SampleIDDS(ResultSet rs)
	{
		super(rs);
	}
	
}
