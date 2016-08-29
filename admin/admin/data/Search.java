package admin.data;

import java.util.*;
import admin.config.*;
import admin.dbAccess.*;

public abstract class Search {
	
	protected String db_schema = DatabaseAccess.getDataSourceSchema();
	protected SearchID searchID = new SearchID(); 
	protected QueryResultSet resultSet = null;
	
	public Search() {
		super();
	}

	public void searchChanged( boolean f){
		searchID.changed(f);
	} 

	public Search(String nm) {
		super();
		searchID.setTlbName(nm);
		if (searchID.getTlbName().equals("RegisteredUser")) 
			db_schema = DatabaseAccess.getDataSourceSchema();
		resultSet = null;
	}

	public void setTlbName(String nm) {
		searchID.setTlbName(nm);
		if (searchID.getTlbName().equals("RegisteredUser")) /*Not sure what is this FIXME: Lulin */
			db_schema = DatabaseAccess.getDataSourceSchema();
		else 
			db_schema = DatabaseAccess.getDataSourceSchema();
		
		if (searchID.isChanged()) {
			resultSet = null;
		}
	}
	public String getTlbName() {
		return searchID.getTlbName();
	}
	
	public SearchID getSearchID() {
		return searchID;
	}
	
	public synchronized void setSearchStr(String s_str) throws Exception {
		synchronized(this) {
			searchID.setSearchStr(s_str);
			if (searchID.isChanged()) {
        			DatabaseAccess da = 
					new DatabaseAccess(db_schema);
				resultSet = da.executeQuery(getQuery());
				searchID.changed(false);
			}
		}
	
	}

	public String getSearchStr() {
		return searchID.getSearchStr();
	}

	public QueryResultSet getResultSet() {
		return resultSet;
	}

	public String toString() { return db_schema;}
		
	public abstract String getQuery();

}

