package petdb.data;

import java.util.*;

import java.sql.*;

public class DataIIRecordDS  extends DataRecordDS {

	public DataIIRecordDS(ResultSet rs)
	{
		super(rs);
	}

	public DataIIRecordDS(ResultSet rs, String qry)
	{
		super(rs, qry);
	}
 

       	protected int buildDS(ResultSet rs)
        {
		  index_key = new Vector();
	      synchronized(index) {
              String key = "";
		      String prv_key = "";
		      String prv_data = "";
		      String data_key = "";
		      Vector current_v = null; 
		      int counter = -1;
              try {
			      //int counter = -1;
			      int i_counter = -1;
                  ResultSetMetaData rsmd = rs.getMetaData();
                  if (rsmd == null) return -1;
                  int count = rsmd.getColumnCount() - 3;
                 // for( int i=1;i<=count+3;i++)
                 // {
                //	  System.out.println("DataIIRecordDS Column ["+i+"]="+rsmd.getColumnLabel(i));
                	 
                 // }
                  while (rs.next())
                  {
			        key = rs.getString(1);
			      
                    if (!prv_key.equals(key)) 
                    {
                       counter++;
                       index.add(key);
                       index_key.add(rs.getString(2));
                       data.add(new Vector());
					   current_v = (Vector)data.elementAt(counter);
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
						pr.update(DataIIRecord.Type, rs.getString(5));
						String filter2Str=rs.getString(6); //FILTER2
		                if(filter2Str !=null)
                          pr.update(DataIIRecord.TypeII, filter2Str);
                        else
                          pr.update(DataIIRecord.TypeII, "N/A");
                        
					  }
					  else 
					  {
						i_counter++;
						current_v.add(newRecord(rs, count));
						prv_data = data_key;
					  }//end of if
				    }//end of if
                  }//end of while
                  return 1;
                }
                catch (Exception e)
                {
			         System.out.println("Error DataIIRecordDS: "+ e.getMessage() + " counter " + counter);
                     return -1;
                }
	 }
        }

	
	protected Record newRecord(ResultSet rs, int count)
	{
		return new DataIIRecord(rs, count);
		
	}

}

