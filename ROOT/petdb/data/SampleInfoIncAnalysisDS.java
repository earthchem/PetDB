package petdb.data;

import java.util.*; 
import java.sql.*;

public class SampleInfoIncAnalysisDS extends SampleInfoAnalysisDS
{

        public static int Heating 		=11;
        public static int Type 			=12;
        public static int Mineral       =13;

	public SampleInfoIncAnalysisDS(ResultSet rs)
	{
		super(rs);
	}

}
