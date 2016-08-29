
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Exp_ExpCtlQuery extends ControlQuery 
{

	public Exp_ExpCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
	qry = "SELECT DECODE(substr(e.expedition_name,1,3),'ODP','ODP'"
	+",'DSD',DECODE(substr(e.expedition_name,1,4),'DSDP','DSDP',substr(e.expedition_name,1,1))"
	+",'ALV',DECODE(substr(e.expedition_name,1,5),'ALVIN','ALVIN',substr(e.expedition_name,1,1))"
	+", substr(e.expedition_name,1,1)) key"
	+", REPLACE(e.expedition_name||DECODE(e.leg,'','',concat('::Leg_',e.leg)),' ','')"
	+" expedition_leg, e.expedition_num"
	+" FROM EXPEDITION e"
	+" ORDER BY key, expedition_leg";
	return 1;
	}



	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new ExpRRecordDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}
	

}

