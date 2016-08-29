package petdb.data;

import java.util.*; 
import java.sql.*;
import petdb.config.*;

public class TblInRefInfo1DS extends UniformValueDS 
{

	Hashtable hsh = new Hashtable(); 

	public static int Type_Code     =1;
	public static int Item_Code     =2;

	public TblInRefInfo1DS(ResultSet rs)
	{
		super(rs);
	}


	public void buildOrderedIndex()
	{
		int j = 0;
		for (int i= 0; i<DisplayConfigurator.Type_Order.length; i++)
		{
			String c_type = DisplayConfigurator.Type_Order[i];
			if ( getKeys().indexOf(c_type) >= 0 )
			{
				Vector v = (Vector)getValue(c_type);
				for (int k=0; k < v.size();k++)
					hsh.put(c_type+v.elementAt(k),new Integer(j++));
			}

		}
	}

	
	public int getIndexOf(String type, String elem)
	{
		Integer i_j = (Integer)hsh.get(type+elem);
		if (i_j != null) return i_j.intValue();
		else return -1;
	}

}
