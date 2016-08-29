package petdb.data;

import java.util.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class DownloadStatisticsDS extends RowBasedDS 
{
	public static int Year              =1;
	public static int Month             =2;
    public static int Unique_IP         =3;
    public static int Unique_Email      =4;
    public static int Monthly_Download  =5;

	public DownloadStatisticsDS(ResultSet rs)
	{
		super(rs);
	}
      
}