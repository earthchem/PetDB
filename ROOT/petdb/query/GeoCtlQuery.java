
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;

public class GeoCtlQuery extends ControlQuery 
{

	public GeoCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT LOCATION_TYPE, LOCATION_NAME"
		+ " FROM GEOGRAPH_LOC ORDER BY LOCATION_TYPE, LOCATION_NAME";
		return 1;

	}



	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new UniformValueDS(rs);
			Vector v1 =  ds.getKeys();
            for (int i =0; i< v1.size(); i++) {
                    String key  = (String)v1.elementAt(i);
                  //  System.out.println("GeoCtlQuery::prepareData myKey="+key);
            }
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

