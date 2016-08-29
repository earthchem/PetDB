package petdb.data;

import java.util.*;
import java.sql.*;

public class ExpRRecordDS  extends RecordDS {

	public ExpRRecordDS(ResultSet rs)
	{
		super(rs);
	}

	public ExpRRecordDS(ResultSet rs, String qry)
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
                                        data.add(newRecord(rs, count));
                                        prv_key = key;
                                } 
				else
				{ 
                                	ExpRRecord pr = (ExpRRecord)data.elementAt(counter);
                                	pr.update(0, rs.getString(2));
                                	pr.update(1, rs.getString(3));
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
		return new ExpRRecord(rs, count);
		
	}

}

