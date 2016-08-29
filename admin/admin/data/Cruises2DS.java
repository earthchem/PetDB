package admin.data;

import admin.dbAccess.ReferenceDS;
import java.sql.*;


/* The Cruises2DS class stores the search results from Cruises2DCtlQuery
 * It extends RowBasedDS class
 * 
 * It contains multiple rows information with 7 columns 'CRUISE_NAME', 'LEG', 'SHIP', 'FROM_YEAR', 'TO_YEAR', 'CHIEF_SCIENTIST', and 'INSTITUTION'.
 * 
 */
public class Cruises2DS extends ReferenceDS
{
    public static int CRUISE_NAME 	=1;
    public static int LEG =2;
    public static int SHIP =3;
    public static int FROM_YEAR=4;
    public static int TO_YEAR=5;
    public static int CHIEF_SCIENTIST=6;
    public static int INSTITUTION=7;
    
    public Cruises2DS(String v_filter) {
      super(v_filter);
	}
}