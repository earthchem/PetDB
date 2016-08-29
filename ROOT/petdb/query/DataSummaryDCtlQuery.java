
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;
import java.io.*;

public class DataSummaryDCtlQuery extends DynamicCtlQuery 
{

	public DataSummaryDCtlQuery(String filter)
	{
		super(filter);
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new DataSummaryDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}
	public int updateData(String filter)
        {
                        v_filter = filter;
                        changed = true;
                        return setFilter();
        }


	protected int setFilter()
	{
	qry = "select count(1), sum(decode(m.material_code,'GL',1,0)) glass, " 
		+ " sum(decode(m.material_code,'WR',1,decode(m.material_code,'ROCK',1,0))) Wholerock, "
		//+ " sum(decode(m.material_code,'INC',1,0)) inclusion, " 
		+ " sum(decode(inc.inclusion_type,'GL',1,'GLASS',1,'FLUID',1,'M',1,'MIN',1)) inclusion," //FIXME inclusion has fluid, melt, mineral, vesicle et. We only care about first two for now.
		+ " sum(decode(inc.inclusion_type,'GL',1,'GLASS',1)) inclusion_glass," //FIXME there's 'GLASS' in Inclusion table
		+ " sum(decode(inc.inclusion_type,'MIN',1,'MINERAL',1)) inclusion_mineral," //FIXME there is 'MINERAL' in Inclusion table
		+ " sum(decode(m.material_code,'MIN',1,0)) mineral, "
		+ " count(distinct rock_mode_num) rockmode, "
		+ " sum(decode(inc.inclusion_type,'FLUID',1,0)) inclusion_fluid"
		+ " from material m, analysis a, rock_mode_analysis rma, "
		+ " (select distinct i.batch_num, i.inclusion_type"
		+ " from inclusion I where i.inclusion_type is not null) inc,"
		+ " batch b " 
		+ " where m.material_num = b.material_num "
		+ " and inc.batch_num (+)= b.batch_num"
		+ " and rma.sample_num(+)= b.sample_num"
		+ " and a.batch_num = b.batch_num"
		+ " and (" + DisplayConfigurator.toReplace(v_filter,'b')+ ")";
               
//        try{
        	  // Create file 
//        	  FileWriter fstream = new FileWriter("C:\\Users\\Lulin Song\\Downloads\\qry.txt");
//        	  BufferedWriter out = new BufferedWriter(fstream);
//        	  out.write(qry);
        	  //Close the output stream
//        	  out.close();
//        	  }catch (Exception e){//Catch exception if any
//        	  System.err.println("Error: " + e.getMessage());
//        	  }
//			DataDCtlQuery.writeQueryToFile(qry, "C:\\Documents and Settings\\Lulin Song\\My Documents\\Downloads\\datasummarydctlqry.txt");
		return 1;
	} 	  

}
