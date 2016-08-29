package petdb.data;

import java.util.*; 
import java.io.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class ExpeditionDS extends RowBasedDS 
{
	public static int KEY           =1;
	public static int EXP           =2;
        public static int SHIP_ID       =3;
        public static int SHIP          =4;
        public static int YEAR          =5;
        public static int CHIEF_ID      =6;
        public static int CHIEF         =7;
        public static int INST_ID       =8;
        public static int INST          =9;
	
	private int key = KEY;
	private int repeate = CHIEF;
	
	public ExpeditionDS(ResultSet rs)
	{
		super(rs);
	}

	public int getColumnCount() { return 5;}
	
	public int writeAllToSheet(PrintWriter file,int offset, Vector values, int d_c) throws Exception
        {
		int row_counter = 0;
		boolean there_are = next();
		boolean go_on = false;
		String csv_str = "";
		do {
			row_counter++;
 			if ( (go_on = getExlRow(offset++,values,d_c, true)) == true)
			{
				csv_str ="";
                        	for (int i=0; i< values.size(); i++)
                                	csv_str += values.elementAt(i) +",";
				file.println(csv_str);
			}
		} while(go_on);
		if (there_are)
		{
			csv_str = ""; 
                        for (int i=0; i< values.size(); i++)
                               	csv_str +=values.elementAt(i) + ",";
			file.println(csv_str);
		}

		return row_counter;
        }
	public int writeAllToSheet(WritableSheet sheet,int offset, Vector values, int d_c) throws Exception
        {
		int row_counter = 0;
		boolean there_are = next();
		boolean go_on = false;
		do {
			row_counter++;
 			if ( (go_on = getExlRow(offset++,values,d_c, false)) == true)
                        	for (int i=0; i< values.size(); i++)
                                	sheet.addCell((jxl.write.WritableCell)values.elementAt(i));
		} while(go_on);
		if (there_are)
		{ 
                        for (int i=0; i< values.size(); i++)
                               	sheet.addCell((jxl.write.WritableCell)values.elementAt(i));
		}

		return row_counter;
        }

        public boolean getExlRow(int offset, Vector v, int d_c, boolean flag) throws Exception 
	{
		boolean r= true;
		String  key_value =getExlValue(key);
		if (!flag)
		{
		v.setElementAt(new jxl.write.Label(0,offset,getExlValue(EXP)), 0);
		v.setElementAt(new jxl.write.Label(1,offset,getExlValue(SHIP)), 1);
		v.setElementAt(new jxl.write.Label(2,offset,getExlValue(YEAR)), 2);
		v.setElementAt(getExlValue(CHIEF), 3);
		v.setElementAt(new jxl.write.Label(4,offset,getExlValue(INST)), 4);
		} else {
		v.setElementAt(getExlValue(EXP),0);
		v.setElementAt(getExlValue(SHIP),1);
		v.setElementAt(getExlValue(YEAR),2);
		v.setElementAt(getExlValue(CHIEF),3);
		v.setElementAt(getExlValue(INST),4);
		}		
		boolean first = true;
		do
		{
			if (key_value.equals(getExlValue(key)))
			{
				String p = (String)v.elementAt(3);
				String n = getExlValue(repeate);
				String f = "";
				if ( (p.length() != 0 ) && ( !p.equals(" ")) )
					if ( (n.length() != 0 ) && ( !n.equals(" ")))
						f = p + "; " + n;
					else f = p;
				else f = n;  
						
				if (!first)
					v.setElementAt(f, 3);
				else 
					first = false;
			} else
				break;
			

		}while ((r = next()));
		
		if (!flag)
			v.setElementAt(new jxl.write.Label(3,offset,(String)v.elementAt(3)), 3);

		return  r;

	}

        public int getTitleRow(Vector v) 
	{
                v.add("Expedition");
                v.add("Ship");
                v.add("Year");
                v.add("Expedition Chief");
                v.add("Institution");
		
		return 1;
	}

}
