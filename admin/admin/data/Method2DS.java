package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;


/* The Method2DS class stores the search results from Method2DCtlQuery
 * It extends RowBasedDS class
 * 
 */
public class Method2DS extends ReferenceDS
{
    public static int STANDARD_NAME=1;  
    public static int COL_NUM = 4;	

	public Method2DS(String v_filter) {
       super(getMethod2DS(v_filter),COL_NUM);
       refNum = v_filter;
    }
}