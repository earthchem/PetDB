package petdb.data;

import java.util.*;
import java.io.*;
import java.sql.*;
import jxl.*;
import jxl.write.*;

public abstract class RecordDS  extends IndexedDS implements ExcelDS
{
	String qry="";
    
	/* Filled index and data Vectors with the contents from ResultSet */
	public RecordDS(ResultSet rs) {
		super();
		int t = buildDS(rs);
	}

	public RecordDS(ResultSet rs, String qry) {
		super();
		qry = qry;
		int t = 0;
		if (rs != null) 
			t  =  buildDS(rs);
	}

	protected int buildDS(ResultSet rs)
	{
	 synchronized (index) {
		String key = "";
		try {
			int counter = 0;
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rsmd == null) return -1;
            int count = rsmd.getColumnCount() - 1;
            boolean b = rsmd.isSearchable(1);
			while (rs.next()) 
			{
				key = rs.getString(1);
				/* index and data are two Vector from IndexedDS parent class */
				index.add(key);
				Record r = newRecord(rs, count);
				data.add(newRecord(rs, count));
		
			}
			return 1;
		}
		catch (Exception e) 
		{
			return -1;
		}
	 }
	}
	
	public String display(Record rec)
	{
		return rec.display();
	}

	public String getStrValue(String key)
	{
		return((Record)getValue(key)).display();	
	}

	public String getQuery()
	{
		return qry;
	}
        
	public int writeAllToSheet(WritableSheet sheet,int offset, Vector values, int d_c) throws Exception
    {
          return 0;
    }
	
	public int writeAllToSheet(PrintWriter sheet,int offset, Vector values, int d_c) throws Exception
    {
          return 0;
    }

	public boolean getExlRow(int offset,Vector v, int d_c, boolean flag) throws Exception { return true;}

    public int getTitleRow(Vector v){ return 1;}

    public int getColumnCount() { return 0;}
  
	protected abstract Record newRecord(ResultSet rs, int count);

	public static void printColumnNamesInResultSet(ResultSet rs)
	{
	    try{
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rsmd == null) return;
            int count = rsmd.getColumnCount();
         //   System.out.println("Total Columns number in ResultSet ==>"+rsmd.getColumnCount());
          //  for(int i=1;i<=count;i++)
        	//    System.out.println("Column "+i+" ==>"+rsmd.getColumnName(i));
        }
        catch (SQLException e)
        {
        	System.out.println(e.getMessage());
        }
	}
}

