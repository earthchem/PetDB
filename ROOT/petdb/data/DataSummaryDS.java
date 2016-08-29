package petdb.data;

import java.util.*;
import java.sql.*;

public class DataSummaryDS  extends RecordDS {

	public DataSummaryDS(ResultSet rs)
	{
		super(rs);
	}

	public DataSummaryDS(ResultSet rs, String qry)
	{
		super(rs, qry);
	}
 

	protected Record newRecord(ResultSet rs, int count)
	{
		return new DataSummaryRecord(rs, count);
		
	}

}

