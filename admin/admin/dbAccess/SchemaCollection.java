package admin.dbAccess;

//import ciesin.config.*;
import java.sql.*;
import javax.naming.NamingException;

	
public class SchemaCollection {
		
	//public static final String PetDBAdminQuery	 = "PetDBAdminQuery";
	//public static final String PBUDBQuery		 = "PBUDBQuery";

	//public static String getDBConnectStr(String indicator) {

	//    String retString = null;

	//    if (indicator.equals(PetDBAdminQuery)) retString = PetDBAdminQuery;
	//    else if (indicator.equals(PBUDBQuery)) retString = PBUDBQuery;

    //        return retString;
    //    }
	
	public static Connection getDBConnection(String datasource)
			throws SQLException, NamingException {

		Connection conn=null;
		DatabaseAdapter adpt=null;

		adpt = new DatabaseAdapter(datasource);
		if(adpt !=null)
		{
		    adpt.initConnection();
		    conn= adpt.getConnection();
		}
		return conn;
        }

}
