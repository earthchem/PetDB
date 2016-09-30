package petdb.data;

import java.sql.*;

public class DownloadPurposeStatisticsDS extends RowBasedDS 
{
	public static int PURPOSE_CNT         =1;
	public static int PURPOSE             =2;

	public DownloadPurposeStatisticsDS(ResultSet rs)
	{
		super(rs);
	}
      
}