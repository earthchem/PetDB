package petdb.data;

import java.util.*; 
import java.sql.*;

public class TblInRefInfo2DS extends RowBasedDS 
{
    public static int Batch_Num 	=1;
	public static int Alias         =2;
	public static int Sample_ID     =3;
    public static int Material 	 	=4;
    public static int Mineral 	 	=5;
    public static int Inclusion 	=6;
    public static int Type_Code 	=7;
    public static int Item_Code 	=8;
    public static int Value			=9;
    public static int IGSN			=11;
    public static int Method        =12;
    public static int Unit          =13;
    
	public TblInRefInfo2DS(ResultSet rs)
	{
		super(rs);
	}

	public double getDoubleValue(int index) throws Exception
	{   
        String s = r_set.getString(index);    
        if(s != null)  return new Double(s).doubleValue();
        else return 0;
	}
    
}
