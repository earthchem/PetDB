package petdb.data;

import java.util.*; 
import java.sql.*;

public class RockModeMineralsDS extends RowBasedDS 
{

        public static int Mineral_Code		=1;

	public RockModeMineralsDS(ResultSet rs)
	{
		super(rs);
	}

	public double getDoubleValue(int index) throws Exception
	{
		  
        String s = r_set.getString(index);    
        if(s != null)  return new Double(s).doubleValue();
        else return 0;

	}

	public int getAllMinerals(Vector out) throws Exception
	{
		if (r_set != null)
		while ( r_set.next())
			out.add(r_set.getString(Mineral_Code));
		return 1; 
	}
	
	
}
