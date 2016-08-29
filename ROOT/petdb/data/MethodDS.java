package petdb.data;

import java.util.*; 
import java.sql.*;
import jxl.write.*;
import jxl.*;

public class MethodDS extends RowBasedDS 
{
	public static int Key		=1;
	public static int Code          =2;
        public static int Reference     =3;
        public static int Ref_ID        =4;
        public static int Institution   =5;
        public static int Name		=6;
        public static int Note		=7;

	public MethodDS(ResultSet rs)
	{
		super(rs);
	}

	public int getColumnCount()
	{
		return 5;
	}
        public boolean getExlRow(int offset, Vector v, int dynamic_counter, boolean flag) throws Exception
        {
                int i  =-1;
                String val ="";
                boolean r =next();
                if (r)
                {
			if (!flag)
			{
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Code)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Name)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Institution)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Reference)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+Note)),i);
                	} else {
                        v.setElementAt(getExlValue(dynamic_counter+Code),++i);
                        v.setElementAt(getExlValue(dynamic_counter+Name),++i);
                        v.setElementAt(getExlValue(dynamic_counter+Institution),++i);
                        v.setElementAt(getExlValue(dynamic_counter+Reference),++i);
                        v.setElementAt(getExlValue(dynamic_counter+Note),++i);
			}
		}
                return r;
        }

        public int getTitleRow(Vector v)
        {
                v.add("Method_Code");
                v.add("Name");
                v.add("Location");
                v.add("Reference");
                v.add("Comment");

                return 1;

	}
}
