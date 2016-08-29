package petdb.data;

import java.util.*; 
import java.sql.*;

public class MethodInfo6DS extends RowBasedDS 
{
	public static int Code    	=1;
	public static int Ratio    	=2;
    public static int Standard 	=3;
    public static int Value	   	=4;

	public MethodInfo6DS(ResultSet rs)
	{
		super(rs);
	}
	
}
