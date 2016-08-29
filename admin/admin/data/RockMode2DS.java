package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;


/* The RockMode2DS class stores the search results from RockMode2DCtlQuery
 * It extends RowBasedDS class
 * 
 */
public class RockMode2DS extends ReferenceDS
{
    public static int SAMPLE_NAME=1;
    public static int NUMBER_OF_POINTS_COUNTED_MIN=2;
    public static int NUMBER_OF_POINTS_COUNTED_MAX=3;
    public static int PLAG=4;
    public static int CPX=5;
    public static int OPX=6;
    public static int OL=7;   
    public static int OPAQ=8;
    public static int AMPH=9;
    public static int GM=10;
    public static int VES=11;
    										

	public RockMode2DS(String v_filter) 
	{
            super(v_filter);
	}
	
	
}