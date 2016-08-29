package petdb.data;

import java.util.*; 
import java.sql.*;

public class MethodInfo5DS extends RowBasedDS 
{

	public static int Item_code   	=1;
	
	public MethodInfo5DS(ResultSet rs)
	{
		super(rs);
	}
	
}
