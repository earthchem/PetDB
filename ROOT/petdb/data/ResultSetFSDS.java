package petdb.data;

import java.util.*; 
import java.io.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;

/* Implement two interface to support manipulate ResultSet and write out data to spreadsheet */
public class ResultSetFSDS implements FinalSampleDS, ExcelDS 
{
	ResultSet r_set; /* Search Results from pg3.jsp */
	int dynamic_count = 0; /* total number of chemicals user selected in pg3.jsp page */
	int r_count = 0;
	int remainder = 0;
	int total_count = 0; /* Total number of rows in ResultSet (r_set) */
	String analysis = ",";
	WritableCellFormat emptyFormat = new WritableCellFormat (NumberFormats.DEFAULT);
	String sampleNums = "";
	int A_K = 1;
	
	public ResultSetFSDS(ResultSet rs, int d_c)
	{
		r_set = rs;
		//RecordDS.printColumnNamesInResultSet(rs);
		dynamic_count = d_c;
		//System.out.println("total chemicals selected ="+d_c);
		if (r_set != null)
			total_count =setTotalCount();	
		
		//System.out.println("total rows number ="+total_count);
	}
   
    public String getSampleNumbers() {return sampleNums;}
       
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

	public int getRem() {return remainder;}
	
	public int getTotalCount() { return total_count;}
	
	public int getCurrentRow() throws Exception { return r_set.getRow();}
	
	public int getCurrentRecNum() throws Exception { return r_set.getRow();}
	
	public String getAnalysisStr() 
	{
		if (analysis.length()>1)
			return  "(" + analysis.substring(1,analysis.length()) + ")";
		else return "";
	}
	
	/* Set analysis String and return total number of rows in ResultSet */
	private int setTotalCount()
	{
        String prev ="";
		int t_c = 0;
		try{
			while(r_set.next())
			{  
                String sample_Num = r_set.getString("sample_num");
                if(!sample_Num.equals(prev)) sampleNums +=","+sample_Num;
                prev = sample_Num;
               
				t_c++;
				String next_analysis = "";
				if (r_set.getString(dynamic_count+ A_K)!= null)
				{
					next_analysis = r_set.getString(dynamic_count + A_K) + ",";
					if (analysis.indexOf("," + next_analysis) < 0)  
						analysis += next_analysis;
				}
			}
           
			analysis = analysis.substring(0,analysis.length()-1);
            sampleNums = sampleNums.substring(1);
            
			
			if (t_c != 0) r_set.beforeFirst();
			return t_c;
		} catch (Exception e) {System.out.println("Error in setTotalCount() "+e.getMessage()); return -1;}
	}

	public void setCount(int count)
	{ ; }
	
	
    public Vector getKeys() { return null;}

    public Object getValue(String key) {return null;}

	public Vector getValues()  {return null;}
	
	public String getKeyAt(String index)  {return null;}
	
	public String getStrValue(String key)  {return null;}

	public boolean next() throws Exception
	{
		boolean ret = false;
		if (r_set == null) return ret; 
		try{
			ret = r_set.next();
			r_count++;
		} catch (Exception e) 
			{ throw new Exception(e.getMessage() 
				+ ":Error when next() at row = " + r_count + " for " + r_set); 
			}
		return ret;
	}

	public boolean goPreviousPage(int rows_num) throws Exception
	{
		String s ="";
		try {
		if (r_count > total_count)
		{
			int rem =rows_num * ((int) Math.floor(total_count/rows_num) - 1);
			s += " total_count = " + total_count + " rem = " + rem;
			if (rem <= 0 ) r_set.beforeFirst();
			else r_set.absolute(rem);
			remainder = rem;
		}
		else if (r_count < 2*rows_num +1)
			r_set.beforeFirst();
		else { 
			int r_count =r_set.getRow() - 2*rows_num;
			if (r_count <=0) r_set.beforeFirst();
			else r_set.absolute(r_count);
		}
		r_count = r_set.getRow();
		} catch (Exception e) 
			{ throw new Exception(e.getMessage() + "state = " + s + " " + 
				 "REAL: Error when goPreviousPage at row =" +r_count + " for " + r_set); }
		return true;
	}

	public boolean goNextPage()
	{
		return true;
	}

	public boolean goFirstPage() throws Exception
	{
		//System.out.println("GoFirst PAGE");
		try {
		r_set.beforeFirst();
		r_count = r_set.getRow();
		} catch (Exception e) 
			{ throw new Exception(e.getMessage()	
			 +  ": Error when beforeFirst at row = " + r_count + "for " + r_set);
			}
		 return true;
	}

	public boolean goLastPage(int rows_num) throws Exception
	{
		try {
		if (total_count <= rows_num) {
			r_set.beforeFirst();
		}else {
			int rem =rows_num * (int) Math.floor(total_count/rows_num);
			if (rem == total_count)
				rem = total_count - rows_num; 
			r_set.absolute(rem);
			remainder = rem;
		}
		r_count = r_set.getRow();
		} catch (Exception e) 
			{ throw new Exception(e.getMessage() 
				+ " : Error when goLastPage at row " + r_count + " for " + r_set);
			}
		return true;
	}

	public String getExlValue(int index) throws Exception
	{
		String val = "";
		try {
			val = r_set.getString(index);
		} catch (Exception e) 
			{ throw new Exception(e
			 + " : Error when getValue(" + index +") at row = "+ r_count + " for "
			 + r_set + " at " + r_set.getRow() + "\n " + dispRow()); }
		return (val == null ? " " : val);
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
		try {
			val = r_set.getString(index);
		} 
		catch (Exception e) 
		{ throw new Exception(e
			 + " : Error when getValue(" + index +") at row = "+ r_count + " for "
			 + r_set + " at " + r_set.getRow() + "\n " + dispRow()); 
		}
		return (val == null ? "&nbsp;" : val);
	}
	
	private String dispRow()
	{
		String d= "" + r_set;
		try {
		d += " Before Stat";
		d +="stat = " + r_set.getStatement();
		d += " Before MetaData";
		ResultSetMetaData rs_md = r_set.getMetaData();
		d += "MetaData = " + rs_md;
		d += " Before getColumnCount";
		d += "C_Count = " +  rs_md.getColumnCount();
		for (int i =00; i< rs_md.getColumnCount(); i++)
			d += i + " = " + r_set.getObject(i).toString();
		
		} catch (Exception e)
		{
			d += "Error = " + e.getMessage(); 

		}
		return d;
	}

	public int writeAllToSheet(PrintWriter file,int offset, Vector values, int d_c) throws Exception
	{	
		int row_counter = 0;
		r_set.beforeFirst();
		while (getExlRow(offset++,values,d_c, true))
		{
			String csv_str = ""; 
			row_counter++;
			for (int i=0; i< values.size(); i++)
                        	csv_str += (String)values.elementAt(i) +",";
			
			file.println(csv_str);
		}
		if (r_count ==0) r_set.beforeFirst();
                else r_set.absolute(r_count);
		return row_counter;
	}
	

	public int writeAllToSheet(WritableSheet sheet,int offset, Vector values, int d_c) throws Exception
	{	 
		int row_counter = 0;
        r_set.beforeFirst();
        
		while (getExlRow(offset++,values,d_c, false))
		{
			row_counter++;
			for (int i=0; i< values.size(); i++)
				if (values.elementAt(i) != null)
                        		sheet.addCell((jxl.write.WritableCell)values.elementAt(i));
		}
    
		if (r_count ==0) r_set.beforeFirst();
                else r_set.absolute(r_count);
                
		return row_counter;
	}

	public jxl.write.WritableCell getNumCell(int c, int r, String value)
	{	
			try {
				double v = Double.parseDouble(value);
				return  new jxl.write.Number(c,r,v);
 			} catch (Exception e)
			{

				return  null; //new jxl.write.Label(c,r,"",emptyFormat);
			}
	} 
	
	public int getColumnCount(){return 0;}

	public boolean getExlRow(int offset, Vector v, int d_c, boolean flag) throws Exception 
	{ return next();}

	public int getTitleRow(Vector v) { return 1;} 
}
