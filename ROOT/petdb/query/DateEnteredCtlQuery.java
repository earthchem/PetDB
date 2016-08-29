
package petdb.query;

import java.sql.*;
import petdb.data.*;
import petdb.config.*;

public class DateEnteredCtlQuery extends ControlQuery 
{

	public DateEnteredCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry ="select distinct to_char(r.data_entered_date,'" 
			+ DisplayConfigurator.DateEnteredFormat + "'),"
			+ " to_char(r.data_entered_date,'"
			+ DisplayConfigurator.DateEnteredFormat + "') "
                        + " from reference r"
                        + " where r.status = 'COMPLETED'"
			+ " and (r.journal is not null or r.book_title is not null) "
                        + " order by to_char(r.data_entered_date,'"
			+ DisplayConfigurator.DateEnteredFormat + "') DESC ";
		return 1;
	}



	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new AValuePerKeyDS(rs);
		} 
		catch (Exception e) 
		{
			;
		}
	}

}

