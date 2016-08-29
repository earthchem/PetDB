package admin.dbAccess;

import java.sql.*; 
import java.util.*;
import javax.naming.NamingException; 


public class  DatabaseAccess {
	
	private static String schema; 
	private Connection theConnection;			
	private String theQuery; 	
	private ResultSet resultSet = null;
	
	public DatabaseAccess(String schema) throws NamingException,SQLException  {
		
		this.schema = schema;
		theConnection = SchemaCollection.getDBConnection(schema);
    }
		
	public DatabaseAccess(String schema, String sql) throws NamingException,SQLException  {
		
		this.schema = schema;
		theConnection = SchemaCollection.getDBConnection(schema);
		this.theQuery = sql; 	//.toUpperCase();

	}
    
    public ResultSet getResultSet() {   
        if(theQuery != null) runQuery();
        return resultSet;
    }

	public static String getDataSourceSchema()
	{return schema;}
	
	public void setQuery(String sql) {
		theQuery = sql; //.toUpperCase();
	}


	public int executeProcedure(String procedureName, String p1, String p2) throws Exception {
		
		if ( (theConnection != null) && (p1 != null) && (p2 != null) )
		{ 
			CallableStatement cs =  
            (CallableStatement)theConnection.prepareCall("{call "+procedureName+"(?,?)}");
			cs.setString(1,"'"+p1+"'");
			cs.setString(2,"'"+p2+"'");
			cs.executeUpdate();
			
			// close conn
			if (cs != null) cs.close();
			close();
		}
		else if  ( (theConnection != null) && (p1 != null) && (p2 == null) ) //only one parameter to pass in
		{
			CallableStatement cs =  
				(CallableStatement)theConnection.prepareCall("{call "+procedureName+"(?)}");										
			int refNum=Integer.parseInt(p1);
			cs.setInt(1,refNum);
			boolean yesno = cs.execute();
			int updateCnt = cs.getUpdateCount();
			// close conn
			if (cs != null) cs.close();
			close();
		}
		else if (theConnection == null )
			System.out.println("No connection  to the database");		
		else {
			System.out.println("Your Parameters are empty");
			close();
		}
		
		return -1;
	}

	public int executeUpdate(String updateSql) throws Exception {
		
		
		int ret;
		if ( (theConnection != null) && (updateSql != null)) { 
			ret = ((Statement)theConnection.createStatement()).executeUpdate(updateSql);
		}
		else if (theConnection == null ) {
			System.out.println("No connection  to the database");
			ret = -1; 
		} else {
			System.out.println("Your update query is empty: updateSql = " + updateSql);
			ret = -1;
		}
		//close conn
		close();
		
		return ret;
	}

	public synchronized QueryResultSet executeQuery(String query) {
		QueryResultSet tmpRS = null;
		if (runQuery(query))
				tmpRS = new QueryResultSet(resultSet);
	  	try {
			if (resultSet!=null) 
				resultSet.close();
		} catch (SQLException sqlE) {
			System.out.println("SQLException : Cannot close the resultSet : " + sqlE.getMessage()); 
		}
		close();

		return tmpRS;
	}
    
    public synchronized QueryResultSet executeQuery(String query, String user, String pw) {
        this.theQuery = query;
		QueryResultSet tmpRS = null;
		if ( populateRS(user, pw))
				tmpRS = new QueryResultSet(resultSet);
	  	try {
			if (resultSet!=null) 
				resultSet.close();
		} catch (SQLException sqlE) {
			System.out.println("SQLException : Cannot close the resultSet : " + sqlE.getMessage()); 
		}
		close();

		return tmpRS;
	}

    


	public synchronized QueryResultSet executeQuery() 
	{
		QueryResultSet tmpRS = null;
		if (runQuery()) 
			tmpRS = new QueryResultSet(resultSet);
	  	try {
			if (resultSet!=null) 
				resultSet.close();
		} catch (SQLException sqlE) {
			System.out.println("SQLException : Cannot close the resultSet : " + sqlE.getMessage()); 
		}
		close();
		
		return tmpRS;
	}


	public boolean runQuery() {
		boolean return_Val = false;
		
		if ( (theConnection != null) && (theQuery != null)) {
			return_Val = populateRS();
		} else {
			if (theConnection == null)
				System.out.println("There is no connection to the database");
			else System.out.println("Call runQuery(query)");
		} 
		return return_Val;
	}

	public boolean runQuery(String sql) {
		setQuery(sql);
		return runQuery();
	}

	private  boolean populateRS() {
	synchronized(this) {
               	try {
			resultSet = 
			((Statement)theConnection.createStatement()).executeQuery(theQuery);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Exception when accessing the database: ");
			System.out.println(theQuery);
			return false;
		}
	}
	}
	
    private  boolean populateRS(String user, String pw) {
	synchronized(this) {
        PreparedStatement prepStmt = null;
               	try {
             prepStmt = theConnection.prepareStatement(theQuery);   
             prepStmt.setString(1, user);
             prepStmt.setString(2, pw); 
			resultSet = prepStmt.executeQuery();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Exception when accessing the database: ");
			System.out.println(theQuery);
			return false;
		}
        }
	}
    	
		
	public String getQuery() {  
		return theQuery;
	}

	public void close() { 
	  	try {
            if(resultSet != null) resultSet.close();
			if (theConnection!=null) theConnection.close();
		} catch (SQLException sqlE) {
			System.out.println("SQLException : Cannot close the connection : " + sqlE.getMessage()); 
		}
	}

	public void finalize() {
        close();
	}
	

}
