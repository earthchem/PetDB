package petdb.data;

import java.util.*; 
import java.sql.*;

public class MethodInfo4DS extends RowBasedDS 
{
	public static int Code    	=1;
        public static int Name 	   	=2;
        public static int Value	   	=3;

	public MethodInfo4DS(ResultSet rs)
	{
		super(rs);
	}
	
}
