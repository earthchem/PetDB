package petdb.query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreparedQuery extends SimpleQuery
{
    public static int STRING = 0;
    public static int INTEGER = 1;
    public static int DOUBLE = 2;
    private String[] parameters = null;
    private int[] types = null;

	
	public PreparedQuery(String qry, String[] items, int[] types) {
		this.qry = qry;
        this.parameters = items;
        this.types = types;
        getResultSet();
	}


	public ResultSet getResultSet()
	{
        makeConnection();
        try
		{
			pstmt = dbConn.prepareStatement(qry);
            int j = 0;
            for(int i = 0; i < types.length; i++) {
                j = i+1;
                if(types[i] == STRING) {pstmt.setString(j,parameters[i]);}
            	if(types[i] == INTEGER) pstmt.setInt(j, Integer.parseInt(parameters[i]));	
                if(types[i] == DOUBLE) pstmt.setDouble(j, Double.parseDouble(parameters[i]));              		
            }	
            
            rs =pstmt.executeQuery();
		}
		catch(SQLException e) {
            System.err.println("prepared_err "+e.getMessage());
		}   
        
		return rs;
	}
	
	
    
    private int makeConnection() {
        try 
		{
			if ((dbConn == null) || (dbConn.isClosed()) )
				if (initDBConn() != 0)
				return -1;
		} 
		catch (Exception e )
		{
			errorMsg = qry + ": \n" + e.getMessage();
			return closeQuery();
		}
        return 1;
    }
    
    public void close() {
        
    }
    
}
