package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;


/* The Inclusion2DS class stores the search results from Inclusion2DCtlQuery
 * It extends RowBasedDS class
 *  
 */
public class Inclusion2DS extends ReferenceDS
{						

    public static int TAB_IN_REF=1;
    public static int SAMPLE_NAME=2;
    public static int SPOT_ID=3;
    public static int NUMBER_OF_REPLICATES=4;
    public static int CALC_AVGE=5;
    public static int INCLUSION_TYPE=6;
    public static int INCLUSION_MINERAL=7;
    public static int HOST_MINERAL=8;
    public static int HOST_MINERAL2=9;
    public static int HOST_ROCK=10;
    public static int INCLUSION_SIZE=11;
    public static int RIM_CORE=12;
    public static int HEATED=13;
    public static int TEMPERATURE=14;
    public static final int COL_NUM =17;
    
  
	public Inclusion2DS(String v_filter) {
       super(getInclusion2DS(v_filter),COL_NUM);
       refNum = v_filter;
	}
}