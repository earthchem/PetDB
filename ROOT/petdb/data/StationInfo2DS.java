package petdb.data;

import java.util.*; 
import java.sql.*;

/* Store search result for StationInfo2DCtlQuery */
public class StationInfo2DS extends RowBasedDS 
{
	
	public static int Sample_Num		=1;
	public static int Sample_ID    		=2;
    public static int Rock_Decs		    =3;
    public static int Alteration      	=4;
    public static int Data_Existence  	=5;
    public static int IGSN          	=6;

	public StationInfo2DS(ResultSet rs)
	{
		super(rs);
	}
	
}
