package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;
import java.util.List;


/* The Mineral2DS class stores the search results from Mineral2DCtlQuery
 * It extends RowBasedDS class
 *  
 */
public class Mineral2DS extends ReferenceDS
{						

    public static int TAB_IN_REF=1;
    public static int SAMPLE_NAME=2;
    public static int ANALYSIS_COMMENT= 3;
    		/*
    public static int SPOT_ID=3;
    public static int NUMBER_OF_REPLICATES=4;
    public static int CALC_AVGE=5;
    public static int MINERAL=6;
    public static int GRAIN=7;
    public static int RIM_CORE=8;
    public static int MINERAL_SIZE=9;
*/
    public static int SPOT_ID=4;
    public static int NUMBER_OF_REPLICATES=5;
    public static int CALC_AVGE=6;
    public static int MINERAL=7;
    public static int GRAIN=8;
    public static int RIM_CORE=9;
    public static int MINERAL_SIZE=10;
  //  public static int VAL_COL =11;
    private static final int COL_NUM =13;
    		
	public Mineral2DS(String v_filter) {
             super(getMineral2DS(v_filter),COL_NUM);  
    //   super(getMineral2DS(v_filter));
       refNum = v_filter;
	}
    
    
}