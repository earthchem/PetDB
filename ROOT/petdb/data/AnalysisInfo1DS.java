package petdb.data;

import java.util.*; 
import java.sql.*;

public class AnalysisInfo1DS extends RowBasedDS 
{
	public static int Analysis_Num  	=1;
        public static int Sample_ID      	=2;
        public static int Sample_Num      	=3;
        public static int Reference  		=4;
        public static int Reference_Num		=5;
        public static int Method_Code  		=6;
        public static int Data_Quality_Num  	=7;
        public static int Material  		=8;

	public AnalysisInfo1DS(ResultSet rs)
	{
		super(rs);
	}
}
