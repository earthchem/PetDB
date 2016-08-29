package petdb.data;

import java.util.*; 
import java.sql.*;

public class SampleInfo2DS extends RowBasedDS 
{
	public static int Author     		=1;
	public static int Ref_num	     	=2;
        public static int Alias 		=3;
        public static int Data_Existence 	=4;

	public SampleInfo2DS(ResultSet rs)
	{
		super(rs);
	}
	
}
