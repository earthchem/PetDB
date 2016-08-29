package petdb.data;

import java.util.*; 
import java.sql.*; 

public class EPubRecord extends PubRecord
{
	public static int PAGE	=10; 

	public EPubRecord(ResultSet rs, int count)
        {
                super(rs, count);
        }

}
