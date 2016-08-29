package petdb.data;

import java.util.*; 
import java.sql.*;
import petdb.config.*;

public class CriteriaDS implements DataSet
{
	
	protected int count=0; 
	protected String data="";
	
        public CriteriaDS(ResultSet rs) {
                int t =buildDS(rs);
        }


	private boolean follows(String i_a, String i_b)
	{
		try 
		{
			int int_a = (int)Integer.decode(i_a).intValue(); 
			int int_b = (int)Integer.decode(i_b).intValue();
			return (int_a == int_b + 1) ? true : false;
		} 
		catch (Exception e)
		{
		  return false;	
		} 
	}
        protected int buildDS(ResultSet rs) {
	 synchronized(data) {
		try {
                        while (rs.next())
			{
				count++;
				if (count ==1)
					data += DisplayConfigurator.toBeReplaced +".sample_num IN (" + rs.getString(1);
				else 
					data += ", "+ rs.getString(1);
			}
			data += ")";
		}
		catch (Exception e)
			{;}
		}
		return 1;
	}

        protected int buildDSS(ResultSet rs) {
	 synchronized(data) {
		try {
			int i_count = 0;
			String prev_val = "-1";
			String now_val = "";
			String singles = "";
                        while (rs.next())
			{
				count++;
				now_val = rs.getString(1);
				if ( follows(now_val, prev_val) )
				{
					i_count ++;
					if (i_count == 1) 
				 		if (data.length()!=0)
					 		data +=" OR " 
							+ DisplayConfigurator.toBeReplaced +
							".sample_num BETWEEN " + prev_val;
						else 
					 		data += DisplayConfigurator.toBeReplaced 
							+ ".sample_num  BETWEEN " + prev_val ;
				}
				else 
				{
					if (i_count > 0 ) 
					 	data += " AND " + prev_val;
					else 
						if (singles.length() == 0 )
							singles += "( " + prev_val;
						else  
							singles += ", " + prev_val;
					i_count = 0;
				}
				prev_val = now_val;
			}

			if (i_count > 0 )
				data +=" AND " + prev_val;
			else 
				singles += ", " + prev_val;
			
			if (singles.length() > 0)
				data += " OR " + DisplayConfigurator.toBeReplaced + ".sample_num IN " + singles +")";
                        return 1;
                }
                catch (Exception e)
                {
                        return -1;
                }
	 }
        }
	public int getCount(){ return count; }
	public String getSampleIDs(){ return data; }
	public Vector getValues() 
	{
		return new Vector();
	}

	public Object getValue(String key)
	{
		synchronized (data) {
			return data;
		}
	}
		
	public String getStrValue(String key)
	{
		return (String)getValue(key);

	}

	public Vector getKeys()
	{
		return null;
	}

	public String getKeyAt(String index)
        {
		return "";
        }

	
		
}
