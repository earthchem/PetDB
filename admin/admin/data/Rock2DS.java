package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;


/* The Rock2DS class stores the search results from Rock2DCtlQuery
 * It extends RowBasedDS class
 *  
 */
public class Rock2DS extends ReferenceDS
{						

    public static int TAB_IN_REF=1;
    public static int SAMPLE_NAME=2;
    public static int ANALYSIS_COMMENT=3;
    public static int NUMBER_OF_REPLICATES=4;
    public static int CALC_AVGE=5;
    public static int MATERIAL=6;
    public static final int COL_NUM =8;
 
	public Rock2DS(String v_filter)
	{
        super(getRock2DS(v_filter),COL_NUM); 
        refNum = v_filter; 
	}
    
	
}