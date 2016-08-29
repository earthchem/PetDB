package petdb.data;

import java.util.*;
import java.sql.*;

public class AValuePerKeyDS extends IndexedDS {
	
		
	public AValuePerKeyDS(ResultSet rs) {
		super();
		int t = buildDS(rs);
	}

	
	protected int buildDS(ResultSet rs) {
		synchronized (index) {	
			String key = "";
			try {			
			ResultSetMetaData rsmd = rs.getMetaData();
            
            int count = rsmd.getColumnCount();
			while (rs.next()) 
				{
					key = rs.getString(1);
					index.add(key);
					data.add(rs.getString(2));
				}
				return 1;
			}
			catch (Exception e) 
			{
				System.out.println("Error:"+e.getMessage());
				return -1;
			}
		}
	}

	
	public String getStrValue(String key)
	{
                synchronized (index){
                        int d = (index != null ? index.indexOf(key) : -1);
                        if (d >= 0 )
                                return  (data != null ? (String)data.elementAt(d) : "");
                        else return key;
                }
	}

	public String getStrKey(String key)
	{
                synchronized (data){
                        int d = (data != null ? data.indexOf(key) : -1);
                        if (d >= 0 )
                                return  (index != null ? (String)index.elementAt(d) : "");
                        else return key;
                }
	
	}


}

