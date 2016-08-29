package petdb.data;

import java.util.*;
import java.io.*;
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class PubRecordDS  extends RecordDS
{

	int browising = 0;
	int current_index= -1;
		
	int orderedBy = PubRecord.AUTH;
	Hashtable orderedM = new Hashtable();
	Vector orderedValues = new Vector();
	Comparator comparator_desc = new DescDateComparator();

	public PubRecordDS(ResultSet rs)
	{
		super(rs);
	}

	public PubRecordDS(ResultSet rs, String qry)
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
                                	PubRecord pr = (PubRecord)data.elementAt(counter);
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
	 	} // end of:  synchronized(index)

        }

	public int getValuesSize() { return orderedValues.size();}
	public boolean hasMoreKeys()
	{
		if (current_index < orderedValues.size() -1)
		{	
			current_index++;
			return true;
		} else return false;
	}

	public String getNextKey()
	{
		String v_key=""; 
		if ( (v_key =(String)orderedValues.elementAt(current_index)) != null)
			return (String)orderedM.get(v_key);
		else return "";
	}

	public int orderBy(int indicator)
	{
		orderedBy = indicator;
		orderedM.clear();
		orderedValues.clear();
		if (index != null)
		for (int i =0; i< index.size(); i++)
		{
			PubRecord pr = (PubRecord)data.elementAt(i);
			orderedM.put(pr.getValue(orderedBy) + (String)index.elementAt(i),index.elementAt(i));
		}
		for ( Enumeration e = orderedM.keys() ; e.hasMoreElements() ; )
			orderedValues.add(e.nextElement());
		if (orderedBy != PubRecord.AUTH)
			Collections.sort(orderedValues, comparator_desc); //, new StrComparator());	
		else  Collections.sort(orderedValues);
		return 1;
	}

	
	protected Record newRecord(ResultSet rs, int count)
	{
		return new PubRecord(rs, count);
		
	}

	public void goFirst() { browising = 0; current_index = -1;}
	
	public int getColumnCount() { return 9;}
        
	public int writeAllToSheet(PrintWriter file,int offset, Vector values, int d_c) throws Exception
        {
		int row_counter = 0;
                while (getExlRow(offset++,values,d_c,true))
		{
			String csv_str = "";
			row_counter++;
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
			row_counter++;
                        for (int i=0; i< values.size(); i++)
                                sheet.addCell((jxl.write.WritableCell)values.elementAt(i));
        	}
		return row_counter;
	}


        public boolean getExlRow(int offset, Vector v, int d_c, boolean flag) throws Exception
        {
		
                boolean r = (browising < data.size());
		if (r) 
		{
			PubRecord pr= (PubRecord)data.elementAt(browising);
			if (!flag)
			{
			v.setElementAt(new Label(0,offset,pr.getValue(PubRecord.AUTH)),0);		
			v.setElementAt(new Label(1,offset,pr.getValue(PubRecord.YEAR)),1);		
			v.setElementAt(new Label(2,offset,pr.getValue(PubRecord.TITLE)),2);
			v.setElementAt(new Label(3,offset,pr.getValue(PubRecord.Journal)),3);
			v.setElementAt(new Label(4,offset,pr.getValue(PubRecord.Volume)),4);
			v.setElementAt(new Label(5,offset,pr.getValue(PubRecord.Book_Title)),5);
			v.setElementAt(new Label(6,offset,pr.getValue(EPubRecord.PAGE)),6);
			v.setElementAt(new Label(7,offset,pr.getValue(PubRecord.Editors)),7);
			v.setElementAt(new Label(8,offset,pr.getValue(PubRecord.Publishers)),8);
			} else {
			v.setElementAt(pr.getValue(PubRecord.AUTH),0);		
			v.setElementAt(pr.getValue(PubRecord.YEAR),1);		
			v.setElementAt(pr.getValue(PubRecord.TITLE),2);
			v.setElementAt(pr.getValue(PubRecord.Journal),3);
			v.setElementAt(pr.getValue(PubRecord.Volume),4);
			v.setElementAt(pr.getValue(PubRecord.Book_Title),5);
			v.setElementAt(pr.getValue(EPubRecord.PAGE),6);
			v.setElementAt(pr.getValue(PubRecord.Editors),7);
			v.setElementAt(pr.getValue(PubRecord.Publishers),8);
			}

			browising++;
		}
                return  r;

        }

        public int getTitleRow(Vector v)
        {
                v.add("Author");
                v.add("Year");
                v.add("Title");
                v.add("Journal");
                v.add("Volume");
                v.add("Book_Title");
                v.add("Pages");
                v.add("Editors");
                v.add("Publishers");

                return 1;
        }



}

class DescDateComparator implements Comparator
{
	public int compare(Object obj1, Object obj2) 
	{
		if ((obj1 != null) && (obj2 != null))
		{
			int r = ((String)obj1).compareTo((String)obj2);
			return (r*(-1));
		}
		else 
		{
			if (obj1 == null) return 1;
			else return -1; 
		}
		
	}

	public boolean equals(Object obj) 
	{ return false;}
	
}

