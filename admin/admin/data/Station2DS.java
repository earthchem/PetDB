package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/* The STATION2DS class stores the search results from STATION2DCtlQuery
 * It extends RowBasedDS class
 * 
 * It contains multiple rows information with 28 columns STATION_ID, EXPEDITION_NUM, STATION_NAME, CRUISE NAME, LEG, STATION_COMMENT,
 * SAMPLING TECHNIQUE,	NAVIGATION METHOD, SAMPLING_DATE, LATITUDE ON, LONGITUDE ON, ELEVATION_MIN,	ELEVATION_MAX,	LATITUDE OFF, LONGITUDE OFF,
 * LAT_LONG_PRECISION,	ELEVATION_MIN,	ELEVATION_MAX,	LOCATION_COMMENT,	LAND_OR_SEA,	TECTONIC_SETTING,	SPREADING_CENTER,	FRACTURE_ZONE	SEAMOUNT,	BACK-ARC_BASIN,	OCEAN,	SEA,	-1
 */
public class Station2DS extends ReferenceDS
{
    public static int STATION_ID =1;
    public static int EXPEDITION_NUM =2;
    public static int STATION_NAME =3;
    public static int CRUISE_NAME =4;
    public static int LEG =5;
    public static int STATION_COMMENT =6;   
    public static int SAMPLING_TECHNIQUE =7;
    public static int NAVIGATION_METHOD =8;
    public static int SAMPLING_DATE =9;
    public static int LATITUDE_ON =10;
    public static int LONGITUDE_ON =11;
    public static int ELEVATION_MIN =12;
    public static int ELEVATION_MAX =13;
    public static int LATITUDE_OFF =14;
    public static int LONGITUDE_OFF =15;
    public static int LAT_LONG_PRECISION =16;
    public static int ELEVATION_MIN2 =17;
 //   public static int ELEVATION_MAX2 =18;
    public static int LOCATION_NUM =18;   
//    public static int LOCATION_COMMENT =19;
    public static int LOCATION_ORDER =19;
    public static int LAND_OR_SEA =20;
    public static int TECTONIC_SETTING =21;
    public static int SPREADING_CENTER =22;
    public static int FRACTURE_ZONE =23;
    public static int SEAMOUNT =24;
    public static int BACK_ARC_BASIN =25;
    public static int OCEAN	=26;
    public static int SEA =27;
    public static int NEGATIVE_ONE =28;
    public static int COL_NUM = 19;
   
        public Station2DS(String v_filter) {
            super(getStation2DS(v_filter),COL_NUM);  
       //     super(getStation2DS(v_filter));  
            refNum = v_filter;
      //      setList(COL_NUM);
        }
        
    /*   public void setList(int size) {
            dsList = new ArrayList();
        try {
           while(r_set.next()) {
            String row[] = new String[size];
            for(int i = 0;  i < size;) row[i] = r_set.getString(++i);
            dsList.add(row);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
   */ 
   
}