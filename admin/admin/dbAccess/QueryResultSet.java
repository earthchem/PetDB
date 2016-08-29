package admin.dbAccess;

import java.sql.*; 
import java.util.*; 

public class  QueryResultSet {
	
	Vector resultSet = new Vector();
	private Vector columnsLabels = new Vector();


	public QueryResultSet(ResultSet rS)  {
		initializeInstance();		
		try {
			populateColumnsLabels(rS);
			populateResultSet(rS);
		} catch (Exception e) {
			System.out.println("Error populating resultSet: " + e.getMessage());
		}
	}

	static void displayRS(ResultSet rS) {
		int i = 1;
		try {
		while (rS.next()) 
			System.out.println("at the " + i++);
		} catch (Exception e) {
			System.out.println("Error at display " + e.getMessage());
		}
	}
	
	private void initializeInstance() {
		 resultSet.clear();
		 columnsLabels.clear(); 
	}

	private boolean populateColumnsLabels (ResultSet rS) throws Exception {
		synchronized(columnsLabels) {
		ResultSetMetaData metadataInfo = rS.getMetaData();
                if (metadataInfo != null) {
			try {
				for (int i=1; i<= metadataInfo.getColumnCount(); i++) {
					String labelorName = metadataInfo.getColumnName(i); 
					columnsLabels.add(labelorName);
					}
				return true;
			} catch (Exception e ) {
				System.out.println("Exception when working with metadata " + e.getMessage());
				return false;
			}
		} else 
			return false;
		}
	}

	public admin.dbAccess.Record getRecordAt(int index) {
		if ( (resultSet != null) && (index <resultSet.size()) ) 
		return (admin.dbAccess.Record)resultSet.elementAt(index);
		else return null;
	}

	private boolean populateResultSet(ResultSet rS) throws Exception {
                synchronized (resultSet) {
		if (rS != null) {
			while (rS.next()) {
				resultSet.add(new admin.dbAccess.Record());
				admin.dbAccess.Record rec = (admin.dbAccess.Record)
							resultSet.elementAt(resultSet.size() - 1);
				for (int i=0; i< columnsLabels.size(); i++) {
					String column = (String)columnsLabels.elementAt(i);
					String tmp = (rS.getString(column) != null) 
						? rS.getString(column) : "";
					rec.addValue(tmp);
				}
			}
			return true;
		} else return false;
		}

	}

	public void clear() {
		resultSet.clear();
		columnsLabels.clear();
	}

	public Vector getColumnsLabels() {
		return columnsLabels;
	}

	public int getColumnCount() {
		return columnsLabels.size();
	} 

	public int getRecordCount() {
		return resultSet.size();
	}

} 
