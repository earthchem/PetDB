package petdb.data;

import java.util.*; 
import java.sql.*;

public class AnalysisInfo2DS extends RowBasedDS 
{
        public static int Item_Code 	=1;
        public static int Value 	=2;

	public AnalysisInfo2DS(ResultSet rs)
	{
		super(rs);
	}
}
