package petdb.data;

import java.util.*;
import java.sql.*;

public class ExpRecordDS  extends RecordDS {

	public ExpRecordDS(ResultSet rs)
	{
		super(rs);
	}

	public ExpRecordDS(ResultSet rs, String qry)
	{
		super(rs, qry);
	}
 

       protected int buildDS(ResultSet rs)
        {
	synchronized(index) {
                String key = "";
		String prv_key = "";
                try {
			int counter = -1;
                        ResultSetMetaData rsmd = rs.getMetaData();
                        if (rsmd == null) return -1;
                        int count = rsmd.getColumnCount() - 1;
                        while (rs.next())
                        {
			        key = rs.getString(1);
                                if (!prv_key.equals(key)) {
                                        counter ++;
                                        index.add(key);
                                        data.add(newRecord(rs,count));
                                        prv_key = key;
                                } 
				else
				{ 
                                	ExpRecord pr = (ExpRecord)data.elementAt(counter);
                                	pr.update(4, rs.getString(6));
                                	pr.update(5, rs.getString(7));
				}

                        }
                        return 1;
                }
                catch (Exception e)
                {
                        return -1;
                }
	 }
        }

	
	protected Record newRecord(ResultSet rs, int count)
	{
		return new ExpRecord(rs, count);
		
	}

}

