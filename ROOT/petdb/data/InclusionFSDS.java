package petdb.data;

import java.util.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class InclusionFSDS extends ResultSetFSDS
{
	static public int Analysis_Key 	 = 1;  
	static public int Sample_ID 	 = 2;
	static public int Inclusion_Type = 3;
	static public int Mineral 	     = 4;
	static public int Mineral_Desc 	 = 5;
	static public int Heating 	     = 6;
	static public int Rim_or_Core 	 = 7;
	static public int Latitude 	     = 8;  
	static public int Longitude 	 = 9;  
	static public int Elevation 	 = 10;  
	static public int Tectonic 	     = 11;  
	static public int Rock 		     = 12;  
	static public int Reference 	 = 13;  
	static public int Reference_Num	 = 14;  
	static public int Method 	     = 15;  
    static public int Sample_Num     = 16;
	static public int Latitude_N 	 = 17;  
    static public int Longitude_N    = 18;
    static public int Expedition   	 = 19;
    static public int Expedition_Num = 20;
    static public int Sample_IGSN    = 21;

	public InclusionFSDS(ResultSet rs, int count)
	{
		super(rs,count);
	}

	public boolean getExlRow(int offset, Vector v, int dynamic_counter, boolean flag) throws Exception
    {
		if (flag) return getCSVRow( dynamic_counter,v);
		int i  =-1;
		String val ="";
        boolean r =r_set.next();
        if (r)
        {
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Sample_ID)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Sample_IGSN)),i);
			v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Mineral)), i);
                        
			for (int j=1 ;j<= dynamic_counter; j++)
                v.setElementAt(getNumCell(++i,offset,getExlValue(j)),i);
                        
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Inclusion_Type)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Heating)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Rim_or_Core)),i);
	                
			val = getExlValue(dynamic_counter+Latitude_N);
            v.setElementAt(getNumCell(++i,offset,val),i);

            val = getExlValue(dynamic_counter+Longitude_N);
            v.setElementAt(getNumCell(++i,offset,val),i);

            val = getExlValue(dynamic_counter+Elevation);
            v.setElementAt(getNumCell(++i,offset,val),i);

			v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Tectonic)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Rock)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Reference)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Method)),i);
            v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Expedition)),i);
        }
        return r;
    }

	public boolean getCSVRow( int dynamic_counter, Vector v) throws Exception
	{
		int i  =-1;
		String val ="";
        boolean r =next();
        if (r)
        {
            v.setElementAt(getExlValue(dynamic_counter+Sample_ID),++i);
            v.setElementAt(getExlValue(dynamic_counter+Sample_IGSN),++i);
			v.setElementAt(getExlValue(dynamic_counter+Mineral),++i);
                        
			for (int j=1 ;j<= dynamic_counter; j++)
                v.setElementAt(getExlValue(j),++i);
                        
            v.setElementAt(getExlValue(dynamic_counter+Inclusion_Type),++i);
            v.setElementAt(getExlValue(dynamic_counter+Heating),++i);
            v.setElementAt(getExlValue(dynamic_counter+Rim_or_Core),++i);
	                
			val = getExlValue(dynamic_counter+Latitude_N);
            v.setElementAt(val,++i);

            val = getExlValue(dynamic_counter+Longitude_N);
            v.setElementAt(val,++i);

            val = getExlValue(dynamic_counter+Elevation);
            v.setElementAt(val,++i);

			v.setElementAt(getExlValue(dynamic_counter+Tectonic),++i);
            v.setElementAt(getExlValue(dynamic_counter+Rock),++i);
            v.setElementAt(getExlValue(dynamic_counter+Reference),++i);
            v.setElementAt(getExlValue(dynamic_counter+Method),++i);
            v.setElementAt(getExlValue(dynamic_counter+Expedition),++i);
        }
        return r;

	}
    
	public int getTitleRow(Vector v)
    {
        v.insertElementAt("Sample_ID",0);
        v.insertElementAt("IGSN",1);
        v.insertElementAt("Mineral",2);
        v.add("Inclusion_Type");
        v.add("Heating");
        v.add("Rim_or_Core");
        v.add("Latitude");
        v.add("Longitute");
        v.add("Elevation");
        v.add("Tectonic");
        v.add("Rock");
        v.add("Reference");
        v.add("Method");
        v.add("Expedition");
	
		return 1;
	}

}
