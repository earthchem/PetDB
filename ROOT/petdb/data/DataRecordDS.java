package petdb.data;

import java.util.*;
import java.sql.*;
import petdb.config.*;

public class DataRecordDS  extends RecordDS {

    public static String  Major_Oxides = "MAJ";
    public static String  Isotopic_Ratio = "IR";
    public static String  Noble_Gas = "NGAS";
    public static String  REE = "REE";
    public static String  U_Series = "US";
    public static String  Volatile = "VO";
    public static String  TE = "TE";
    public static String  IS = "IS";
    public static String  EM = "EM";
    public static String  AGE = "AGE";


	protected Vector index_key;
    protected String[] data_type = DisplayConfigurator.Type_Order;

    protected String[] data_Label = DisplayConfigurator.Label_Order;

    public String[] getOrderedLabels()
    {
                return data_Label;
    }
    
    public String[] getOrderedKeys()
    {
                return data_type;
    }

	public String getIndexKey(String st)
	{
		int i = 0;
		for (i=0; i<index.size(); i++)
			if (((String)index.elementAt(i)).equals(st))
				break; 
		 return (String)index_key.elementAt(i);
	}
	
	public DataRecordDS(ResultSet rs)
	{
		super(rs);
	}

	public DataRecordDS(ResultSet rs, String qry)
	{
		super(rs, qry);
	}
 

    protected int buildDS(ResultSet rs)
    {
		index_key = new Vector();
		//System.out.println("Build DS");
	    synchronized(index) {
            String key = "";
		    String prv_key = "";
		    String prv_data = "";
		    String data_key = "";
		    Vector current_v = null; 
		    String st ="";
            try {
			    int counter = -1;
			    int i_counter = -1;
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rsmd == null) return -1;
                int count = rsmd.getColumnCount() - 3;

                while (rs.next())
                {
				    //System.out.println("Key = " + key + " prev = " + prv_key);
			        key = rs.getString(1);
                                if (!prv_key.equals(key)) {
                                        counter ++;
                                        index.add(key);
					st = "just before index_key";
					st +=index_key.toString();
                                        index_key.add(rs.getString(2));
					//System.out.println("index = " + key + " key " + rs.getString(2)); 
                                        data.add(new Vector());
					current_v = (Vector)data.elementAt(counter);
					st = "just before new record";
					current_v.add(newRecord(rs, count));
					i_counter = 0;
					prv_data = rs.getString(3);
					data_key = "";
                    prv_key = key;
                } 
				else
				{ 
                    current_v = (Vector)data.elementAt(counter);
					data_key = rs.getString(3);
					if (prv_data.equals(data_key))
					{
						DataRecord pr = (DataRecord)current_v.elementAt(i_counter);
                                		pr.update(2, rs.getString(5));
					}
					 else 
					{
						i_counter++;
						current_v.add(newRecord(rs, count));
						prv_data = data_key;
					}
				}
            }
            return 1;
            }
            catch (Exception e)
            {
			    //System.out.println("Error : "+ st + e.getMessage());
                return -1;
            }
	    }
    }

	
	protected Record newRecord(ResultSet rs, int count)
	{
		return new DataRecord(rs, count);
		
	}

}

