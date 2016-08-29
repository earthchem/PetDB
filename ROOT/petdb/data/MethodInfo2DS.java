package petdb.data;

import java.util.*; 
import java.sql.*;

public class MethodInfo2DS extends RowBasedDS 
{
	public static int Item    	=1;
        public static int Type    	=2;
        public static int Min   	=3;
        public static int Max      	=4;

	public MethodInfo2DS(ResultSet rs)
	{
		super(rs);
	}
	
}
