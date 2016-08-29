package petdb.data;

import java.util.*; 
import java.sql.*;

public class ReferenceInfo1DS extends RowBasedDS 
{
    public static int Author       	  =1;
    public static int Pub_Year        =2;
    public static int Title  	      =3;
    public static int Pub_Desc	      =4;
    public static int Book_Title	  =5;
    public static int Editors	      =6;
    public static int Publisher	      =7;
    public static int Locations_Num   =8;
    public static int Publication_Doi =9;
    public static int Status   		  =10;
    public static int Comment 		  =11;

	public ReferenceInfo1DS(ResultSet rs)
	{
		super(rs);
	}
	
}
