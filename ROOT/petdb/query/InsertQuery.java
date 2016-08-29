package petdb.query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import petdb.config.*;
import petdb.data.*;
import petdb.config.*;
public class InsertQuery extends EditingQuery
{
    private boolean isTagAlong = false;
    
	public InsertQuery(String session_id, String query)
	{
		super();
		int r = setQuery(session_id, query);
	}

    public InsertQuery(String session_id, String query, boolean isTagAlong)
	{
		super();
        this.isTagAlong = isTagAlong;
		int r = setQuery(session_id, query);
	}
    
    
    
	public  int setQuery(String session_id, String query)
	{
        if(isTagAlong) {
            qry = "insert into sessionparam"
			+ " (session_id, param_name, param_value)"
			+ " (select '" + session_id + "', 'sample_num_ta',"
            + " ta.sample_num from (select distinct b.sample_num FROM  item_measured i, chemistry c, material m, analysis a, batch b "
            + " WHERE  i.item_measured_num = c.item_measured_num  AND c.analysis_num = a.analysis_num AND a.batch_num = b.batch_num "
            + " AND b.material_num = m.material_num AND m.material_code in ('WR','GL','ROCK') AND (b.sample_num IN "
            + " ( select param_value from sessionparam where param_name='sample_num' and session_id = '" + session_id + "' )) "
            + " AND i.item_code in ("+query+")) ta)";
        } else {
          
		qry = "insert into sessionparam"
			+ " (session_id, param_name, param_value)"
			+ " (select '" + session_id + "', 'sample_num',"
			+ " s.sample_num"
			+ " from  sample s where " + DisplayConfigurator.toReplace(query,'s')
			+ " ) ";
        }
//        try{
      	  // Create file 
//      	  FileWriter fstream = new FileWriter("C:\\Users\\Lulin Song\\Downloads\\insertqry.txt");
//      	  BufferedWriter out = new BufferedWriter(fstream);
//      	  out.write(qry);
      	  //Close the output stream
//      	  out.close();
//      	  }catch (Exception e){//Catch exception if any
//      	  System.err.println("Error: " + e.getMessage());
//      	  }
//		DataDCtlQuery.writeQueryToFile(qry, "C:\\Documents and Settings\\Lulin Song\\My Documents\\Downloads\\insertdctlqry.txt");		
		return 1;
	}

}

