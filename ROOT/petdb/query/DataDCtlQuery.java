
package petdb.query;

import java.sql.*;
import java.io.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class DataDCtlQuery extends DynamicCtlQuery 
{
	public static String Rock 	= "rock";
	public static String Inclusion 	= "inclusion";
	public static String Mineral 	= "mineral";
	public static String RockMode 	= "rockmode";
    private String tagAlongFilter="";

	
	String type = Rock;

	public DataDCtlQuery(String filter, String type)
	{
		super();
                v_filter 	= filter;
                this.type 	= type;
                int i 		= setFilter();

	}
 	
    public DataDCtlQuery(String filter, String type, String tagAlongFilter)
	{
		this.tagAlongFilter = " and it.ITEM_TYPE_CODE in ("+tagAlongFilter+")";
        v_filter 	= filter;
        this.type 	= type;
        int i 		= setFilter();
	}
    
	public int updateData(String filter)
        {
                        v_filter = filter;
                        changed = true;
                        return setFilter();
        }

	public void setType(String t){ type = t;}
	
	public String getType(){return type;}

	public synchronized void prepareData() 
	{
		try {

			if (type.equals(Inclusion))
			{
				
				ds = (DataSet) new DataIIRecordDS(rs);
			}
			else if (type.equals(RockMode)) 
			{
				ds = (DataSet) new RMDataRecordDS(rs);
			
			} else {
				ds = (DataSet) new DataRecordDS(rs);
			}
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{

		if (type.equals(Rock))
		{
			qry = "SELECT  distinct it.item_type_code, it.item_type_num, i.item_code,"
			+ " i.item_measured_num, m.material_code,itl.display_order"
			+ " FROM  item_type it, itemtype_list itl, item_measured i,"
			+ " chemistry c, material m, analysis a, batch b"
			+ " WHERE it.item_type_num = itl.item_type_num"
			+ " AND itl.itemtypelist_num = c.itemtypelist_num"
			+ " AND i.item_measured_num = c.item_measured_num"
			+ " AND i.item_measured_num = itl.item_measured_num"
			+ " AND c.analysis_num = a.analysis_num"
			+ " AND a.batch_num = b.batch_num"
			+ " AND b.material_num = m.material_num"
			+ " AND m.material_code in ('WR','GL','ROCK')"
			+ " AND (" + DisplayConfigurator.toReplace(v_filter,'b') + ")" + tagAlongFilter
			+ " order by it.item_type_code, itl.display_order";
			//writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\datadctlqry_roc.txt");
			return 1;
		}
		else if (type.equals(Mineral))
		{
			qry = " select distinct it.item_type_code, it.item_type_num, i.item_code,"
				+ " i.item_measured_num, m.mineral_num,itl.display_order"
				+ " from item_type it, itemtype_list itl, item_measured i,"
				+ " chemistry c, mineral m, analysis a, batch b"
				+ " where it.item_type_num = itl.item_type_num"
				+ " and itl.itemtypelist_num = c.itemtypelist_num"
				+ " and i.item_measured_num = c.item_measured_num"
				+ " and i.item_measured_num = itl.item_measured_num"
				+ " and c.analysis_num = a.analysis_num"
				+ " and a.batch_num = b.batch_num"
				+ " and m.batch_num = b.batch_num"
				+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"+ tagAlongFilter
				+ " order by it.item_type_code, itl.display_order"; 
			return 1;
		}
		else if (type.equals(Inclusion))
		{	
			qry = " select distinct it.item_type_code, it.item_type_num, i.item_code,"
				+ " i.item_measured_num, p.filter1, p.filter2,itl.display_order"
				+ " from item_type it, itemtype_list itl, item_measured i,"
				+ " chemistry c, "
				+ " (select distinct inc.batch_num batch_num, "
				+ " inc.inclusion_type filter1,"
				+ " inc.host_mineral_num||decode(nvl(inc.heating, ''),'','',upper(substr(inc.heating,1,1)))"
				+ " filter2"
				+ " from inclusion inc where inc.inclusion_type <> 'VESICLE') p," 
                + " analysis a, batch b"
				+ " where it.item_type_num = itl.item_type_num"
				+ " and itl.itemtypelist_num = c.itemtypelist_num"
				+ " and i.item_measured_num = c.item_measured_num"
				+ " and i.item_measured_num = itl.item_measured_num"
				+ " and c.analysis_num = a.analysis_num"
				+ " and a.batch_num = b.batch_num"
				+ " and p.batch_num = b.batch_num"
				+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"+tagAlongFilter
				+ " order by it.item_type_code, itl.display_order";
			return 1;
		} else if (type.equals(RockMode))
		{
			qry = " select distinct 'MIN', '1', ml.mineral_code, ml.mineral_num, 'MIN'"
				+ " from mineral_list ml, rock_mode rm, rock_mode_analysis b "
				+ " where b.rock_mode_num = rm.rock_mode_num"
				+ " and ml.mineral_num = rm.mineral_num"
				+ " and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"+tagAlongFilter
				+ " order by ml.mineral_code";
			
			return 1;
		}

	return 1;
	} 
	
	public static void writeQueryToFile(String query, String fileNameWithFullPath)
	{
		// Write the query to a file for debug	
		try{
		    // Create file 
			FileWriter fstream = new FileWriter(fileNameWithFullPath);
			BufferedWriter outFile = new BufferedWriter(fstream);
			outFile.write( query );
			//Close the output stream
			outFile.close();
			} catch (Exception e){
				//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
	}
}
