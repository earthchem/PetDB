package admin.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import admin.dbAccess.*;
import admin.config.*;

public class UpdateableRecordSearch extends Search 
		implements Executable {
        
	private Vector columns, values;
	public static String NEW  = "NEW";
	public static String DELETE = "DELETE";
	public static String CANCEL = "CANCEL";
	public static String EDIT = "EDIT";
	public static String CLEAN_REF = "CLEAN";
	
	String action = "";	
	boolean actionPerformed = false;
 	String message = "Error at ";
	

	public synchronized void setSearchStr(String s_str) throws Exception {
		super.setSearchStr(s_str);
		columns = resultSet.getColumnsLabels();
        	values = new Vector(columns.size());  
	}

	public boolean actionPerformed() {
		return actionPerformed;
	}
	
	public Vector getColumns(){
		return columns;
	}

	public void setValue(int ind, String value) {
		values.insertElementAt(value,ind);
	}

	public  String getMessage() {
		return message;
	}

	public String getQuery() {
		String searchString;
		String tlbName = searchID.getTlbName();
	 	searchString =searchID.getSearchStr();
		return Configurator.getEditSearchQuery(tlbName,"'"+searchString+"'");
	}
	
	public synchronized boolean runAction() {
		String execQ ="";
		String col="";
		String val=""; 
		boolean ret_value = false;
		if (action.equals(CANCEL)) {
			actionPerformed = true;
			return true;
		} 
		
		if  (action.equals(NEW)) {
		execQ = "INSERT INTO " + searchID.getTlbName() + " ";
            String cityStZip = "";
			int pos = 0;
			for (int i = 0; i<columns.size(); i++) { 
				String c_c = (String)columns.elementAt(i);
				String c_v = "'"+(String)values.elementAt(i)+"'";
				if(c_v.equals("'select a state'")) c_v = null;
				if (c_c.equals(Configurator.getTablesKey(searchID.getTlbName())) )
					continue;
				if (pos == 0) {
					col += c_c;
					val += c_v ;
					pos +=1; 
				} else { 
					col +=","+ c_c;   
					val +=","+ c_v;
					pos +=1;
				}
                if("CITY".equals(c_c) && (String)values.elementAt(i) != null) cityStZip = "'"+(String)values.elementAt(i);
                else if("STATE".equals(c_c) && c_v != null)  cityStZip += ", "+ (String)values.elementAt(i);
                else if("ZIP".equals(c_c) && (String)values.elementAt(i) != null) cityStZip += " "+(String)values.elementAt(i);
			}
			if(cityStZip.length() > 1) {
                cityStZip +="'";
                col +=",CITY_STATE_ZIP";
                val +=","+cityStZip;
            }
			execQ +="(" + col+ ") values (" + val+")";
		}
	
		if ( (action.equals(EDIT)) && (!searchID.getTlbName().equals("Sample")) )
		{	
			int pos = 0;
            execQ = "UPDATE " + searchID.getTlbName() + " SET ";
            String cityStZip = "";
			for (int i = 0; i<columns.size(); i++) {
				col = (String)columns.elementAt(i);
				if ( col.equals(Configurator.getTablesKey(searchID.getTlbName())) ) 
					continue;   
				val = "'" + (String)values.elementAt(i) + "'";
				if(val.equals("'select a state'")) val = null;
				if (pos == 0) {  
					execQ += col + "=" + val;
					pos +=1;
				}
				else { 
					execQ +=", " + col + "=" + val;	
					pos +=1;
				}
                if("CITY".equals(col) && (String)values.elementAt(i) != null) cityStZip = ", CITY_STATE_ZIP='"+(String)values.elementAt(i);
                else if("STATE".equals(col) && val != null) cityStZip += ", "+ (String)values.elementAt(i);
                else if("ZIP".equals(col) && (String)values.elementAt(i) != null) cityStZip += " "+(String)values.elementAt(i);
			}
            if(cityStZip.length() > 1) cityStZip +="'";
            execQ += cityStZip;
			execQ += " WHERE upper(" 
				+ Configurator.getETablesKey(searchID.getTlbName())
				+ ") = '" + searchID.getSearchStr() + "'";
		}

		if (action.equals(DELETE)) {
			  execQ = "DELETE FROM " + searchID.getTlbName() 
				+ " WHERE "
				+ "upper(" + Configurator.getETablesKey(searchID.getTlbName())
				+ ") = '" + searchID.getSearchStr() +"'";
//              writeQueryToFile(execQ, "C:\\Users\\Lulin Song\\Downloads\\deleteQry.txt");
		}
		
		if (action.equals(CLEAN_REF)) {
			String tableName = searchID.getTlbName();
			if(tableName.equalsIgnoreCase("REFERENCE")==true)
			{
				execQ="PRC_DEL_REF";
			}
            else
                execQ="";
		}
		int exec_ret = -1;
		try {
			DatabaseAccess da =
				 new DatabaseAccess(db_schema);
			try {
				if (
					 (action.equals(EDIT)) 
					 &&
					 (searchID.getTlbName().equals("Sample"))
				    )
				{					
						exec_ret = da.executeProcedure(
							"prc_renameduplsample",
							(String) values.elementAt(0),
							(String) values.elementAt(1)
							);
						ret_value = true;
				}
				else
				{
					if(execQ.equals("PRC_DEL_REF")==true)
					{
						exec_ret = da.executeProcedure(
								"PRC_DEL_REF",
								(String) searchID.getSearchStr(),
								null
								);
					}
					else 
					    exec_ret = da.executeUpdate(execQ);
					ret_value = true;
				}
			} catch (Exception e ) {
				message =action +": " + e.getMessage();
				System.out.println("Inner catch: " + message);
			}
			actionPerformed = true;
			if (exec_ret != -1) 
			{
				ret_value = true;
				da.runQuery("commit");
				//da.runQuery("rollback");
				searchID.changed(true);
				super.setSearchStr(super.getSearchStr());
			} //else  System.out.println("exec_ret == -1");
		} catch (Exception e) {
			actionPerformed = true;
			message =action + ": "+ e.getMessage();
			System.out.println("Outer catch: " + message);
		}

		return ret_value;
	}

	public String toString() {
		String tmp = " Action = " + action  + '\n';
		tmp += " actionPerformed = " + actionPerformed + '\n';
		tmp += " searchID = " + super.getSearchStr() + '\n';
		tmp += " message = " + message + '\n';
	return tmp;
	}
	
	public static void writeQueryToFile(String query, String fileNameWithFullPath)
	{
		// Write the query to a file for debug	
		try{
		    // Create file 
			FileWriter fstream = new FileWriter(fileNameWithFullPath);
			BufferedWriter outFile = new BufferedWriter(fstream);
			outFile.write( query );
			//Close the output stream
			outFile.close();
			} catch (Exception e){
				//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
	}		 
}
