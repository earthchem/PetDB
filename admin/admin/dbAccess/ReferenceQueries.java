/*$Id:*/
package admin.dbAccess;


public class ReferenceQueries 
{
   public static String getTableInRefTable(String v_filter)  {
	  return  " select table_in_ref, table_title from table_in_ref where ref_num =" + v_filter;
   }
	
}