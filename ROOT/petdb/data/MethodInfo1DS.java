package petdb.data;

import java.util.*; 
import java.sql.*;

public class MethodInfo1DS extends RowBasedDS 
{

	public static int Method_code   	=1;
        public static int Reference	    	=2;
        public static int Reference_Num	    	=3;
        public static int Inst		   	=4;
        public static int Method_Name      	=5;
        public static int Comment	  	=6;

	public MethodInfo1DS(ResultSet rs)
	{
		super(rs);
	}
	
}
