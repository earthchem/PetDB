package petdb.data;

import java.util.*; 
import java.sql.*;

public class StationExpedInfo1DS extends RowBasedDS 
{
	public static int Station_ID    	=1;
    public static int year              =2;
    public static int exped_name         =3;
    public static int scientist_name    =4;

    
	public StationExpedInfo1DS(ResultSet rs)
	{
		super(rs);
	}
	
}
