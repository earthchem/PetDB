package petdb.data;

import java.util.*;
 
import java.sql.*;

public class SampleInfo1DS extends RowBasedDS 
{

	public static int SAMPLE_ID     =1;
        public static int LATITUDE      =2;
        public static int LONGITUDE     =3;
        public static int DEPTH 	=4;
        public static int TECT	 	=5;
        public static int LOC_Name 	=6;
        public static int LOC_Com 	=7;
        public static int ROCKTYPE 	=8;
        public static int ROCKCLASS 	=9;
        public static int DESC 		=10;
        public static int ALTERATION    =11;
        public static int GEOL_AGE	=12;
        public static int AGE		=13;
        public static int EXP_Name	=14;
        public static int EXP_Num	=15;
        public static int SHIP		=16;
        public static int CHIEF		=17;
        public static int SAMP_DATE     =18;
        public static int SAMP_TECH 	=19;
        public static int STATION       =20;
        public static int STATION_Num   =21;
        public static int INSTITUTION   =22;
        public static int DEPARTMENT   =23;
        public static int REF_NUM   =24;

	public SampleInfo1DS(ResultSet rs)
	{
		super(rs);
	}
	
}
