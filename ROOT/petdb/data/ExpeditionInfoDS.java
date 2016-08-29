package petdb.data;

import java.util.*; 
import java.sql.*;

public class ExpeditionInfoDS extends RowBasedDS 
{

	public static int Exp_Name   	=1;
	public static int Exp_Num   	=2;
        public static int Ship_Name    	=3;
        public static int Year    	=4;
        public static int Chief_ID     	=5;
        public static int Chief  	=6;
        public static int Inst_ID 	=7;
        public static int Ints  	=8;
        public static int Appcode  	=9;
        public static int EA_expcode  	=10;

	public ExpeditionInfoDS(ResultSet rs)
	{
		super(rs);
	}
}
