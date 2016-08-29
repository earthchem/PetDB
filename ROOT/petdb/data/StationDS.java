package petdb.data;

import java.util.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class StationDS extends RowBasedDS 
{
	public static int Station_Num   =1;
	public static int Station_ID    =2;
        public static int Sampling      =3;
        public static int Latitude      =4;
        public static int Longitude     =5;
        public static int Elev_Min      =6;
        public static int Elev_Max      =7;
        public static int Expedition    =8;
        public static int Tectonic   	=9;
        public static int Latitude_N    =10;
        public static int Longitude_N   =11;

	public StationDS(ResultSet rs)
	{
		super(rs);
	}
        

        public int getColumnCount() { return 8;}

        public boolean getExlRow(int offset,Vector v, int d_c, boolean flag) throws Exception
        {
		String val = "";
		boolean r=next();
		if (r) {
			if (!flag)
			{
                	v.setElementAt(new Label(0,offset,getExlValue(Station_ID)), 0);
                	v.setElementAt(new Label(1,offset,getExlValue(Sampling)), 1);
                	
			val = getExlValue(Latitude_N);
                        v.setElementAt(getNumCell(2,offset,val),2);
			
			val = getExlValue(Longitude_N);
                        v.setElementAt(getNumCell(3,offset,val),3);
			
			val = getExlValue(Elev_Min);
                        v.setElementAt(getNumCell(4,offset,val),4);
			
			val = getExlValue(Elev_Max);
                        v.setElementAt(getNumCell(5,offset,val),5);

                	v.setElementAt(new Label(6,offset,getExlValue(Expedition)), 6);
                	v.setElementAt(new Label(7,offset,getExlValue(Tectonic)), 7);
                	} else {
			v.setElementAt(getExlValue(Station_ID), 0);
                	v.setElementAt(getExlValue(Sampling), 1);
                	
			val = getExlValue(Latitude_N);
                        v.setElementAt(val,2);
			
			val = getExlValue(Longitude_N);
                        v.setElementAt(val,3);
			
			val = getExlValue(Elev_Min);
                        v.setElementAt(val,4);
			
			val = getExlValue(Elev_Max);
                        v.setElementAt(val,5);

                	v.setElementAt(getExlValue(Expedition), 6);
                	v.setElementAt(getExlValue(Tectonic), 7);
			}
		}
		return  r;
        }

        public int getTitleRow(Vector v)
        {
                v.add("Station_ID");
                v.add("Sampling");
                v.add("Latitude");
                v.add("Longitude");
                v.add("Elevation_Min");
                v.add("Elevation_Max");
                v.add("Expedition");
                v.add("Tectonic");
                return 1;
        }


	
}
