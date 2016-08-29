package admin.data;

import java.util.*; 
import java.io.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class RowBasedDS implements DataSet, ExcelDS 
{   
	String refNum;
	ResultSet r_set;
	WritableCellFormat emptyFormat = new WritableCellFormat (NumberFormats.DEFAULT);
	
	public RowBasedDS(ResultSet rs)
	{
		r_set = rs;
	}
	
	
    public Vector getKeys() { return null;}

    public Object getValue(String key) {return null;}

	public Vector getValues()  {return null;}
	
	public String getKeyAt(String index)  {return null;}
	
	public String getStrValue(String key)  {return null;}

	public boolean next() throws Exception
	{
		boolean ret = false;
		synchronized (r_set)
		{
			ret = r_set.next();
		}
		return ret;
	}

	public String getExlValue(int index) throws Exception
	{
		String val = "";
		synchronized(r_set)
		{
			val = r_set.getString(index);
		}
		if (val == null) val = " ";
		if (val.equals(" ")) val = " ";
		return val;

	}

	public boolean isFieldEmpty(int index) throws Exception
	{
		String val = "";
		synchronized(r_set)
		{
			val = r_set.getString(index);
		}
		if (val == null)  
			return true;
		else 
			return false;

	}

	public double getDoubleValue(int index) throws Exception
	{ 		  
        String s = r_set.getString(index);    
        if(s != null)  return new Double(s).doubleValue();
        else return 0;
	}


	public String getValue(int index) throws Exception
	{
		String val = "";
		synchronized(r_set)
		{
		  //	  ResultSetMetaData rsmd = r_set.getMetaData();
          //    if (rsmd != null)
          //    {
          //     int count = rsmd.getColumnCount();
          //     System.out.println("------------------------- Count="+count);
          //     for(int i=1;i<=count;i++)
          //     {
          //         System.out.println("-- column["+i+"]="+rsmd.getColumnName(i));
          //     }
          //    }
			val = r_set.getString(index);
		}
		if (val == null) val = "&nbsp;";
		if (val.equals(" ")) val = "&nbsp;";
		return val;
	
	}
        
	public int writeAllToSheet(PrintWriter file,int offset, Vector values, int d_c) throws Exception
        {
		int row_counter = 0;
 	   	while (getExlRow(offset++,values,d_c, true))
                {
			String csv_str = "";
			row_counter = 0;
		        for (int i=0; i< values.size(); i++)
                                csv_str += values.elementAt(i) + ",";
			file.println(csv_str);
		}
		return row_counter;
	}

	public int writeAllToSheet(WritableSheet sheet,int offset, Vector values, int d_c) throws Exception
        {
		int row_counter = 0;
 	   	while (getExlRow(offset++,values,d_c, false))
                {
			row_counter = 0;
		        for (int i=0; i< values.size(); i++)
				if (values.elementAt(i) != null)
                                sheet.addCell((jxl.write.WritableCell)values.elementAt(i));
		}
		return row_counter;
	}

	
	public jxl.write.WritableCell getNumCell(int c, int r, String value)
        {
                        try {
                                double v = Double.parseDouble(value);
                                return  new jxl.write.Number(c,r,v);
                        } catch (Exception e)
                        {

                                return null; //new jxl.write.Label(c,r,"",emptyFormat);
                        }
        }

        public boolean getExlRow(int offset, Vector v, int d_c, boolean flag) throws Exception { return next();}

        public int getTitleRow(Vector v) { return 1;}

	public int getColumnCount() {return 0;}
}
