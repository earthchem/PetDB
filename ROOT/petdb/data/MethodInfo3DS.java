package petdb.data;

import java.util.*; 
import java.sql.*;

public class MethodInfo3DS extends RowBasedDS 
{
	public static int Code    	=1;
        public static int Name    	=2;
        public static int Val    	=3;
        public static int Stdev      	=4;
        public static int Type  	=5;
        public static int Unit  	=6;

	public MethodInfo3DS(ResultSet rs)
	{
		super(rs);
	}
	
}
