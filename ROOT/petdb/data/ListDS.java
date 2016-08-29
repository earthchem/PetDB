package petdb.data;

import java.util.*; 
import java.sql.*;

public class ListDS implements DataSet
{
	
	protected Vector data;
	
        public ListDS(ResultSet rs) {
		//System.out.println("RS = " + rs);
                data = new Vector();
                int t =buildDS(rs);
        }


        protected int buildDS(ResultSet rs) {
	 synchronized(data) {
                try {
		//System.out.println("Building ControlList");
		ResultSetMetaData meta = rs.getMetaData();
		//System.out.println("meta =" + meta);
		//for (int f = 1; f< meta.getColumnCount(); f++)
			//System.out.println(meta.getColumnLabel(f));
                        while (rs.next())
			{
                                //System.out.println(rs.getString(1));
				data.add(rs.getString(1));
			}
                        return 1;
                }
                catch (Exception e)
                {
			System.out.println("Exception =" + e.getMessage());
                        return -1;
                }
	 }
        }

	public Vector getValues() 
	{
		synchronized (data) {
			return data;
		}
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
                synchronized(data)
                {
                return (String)data.elementAt(Integer.decode(index).intValue()-1);
                }
        }

	
		
}
