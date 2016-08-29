package petdb.data;

import java.util.*; 
import java.sql.*; 

public class ExpRecord extends Record
{
/*
        qry = "SELECT DISTINCT t.expedition_num, t.expedition_name||'/'||t.leg,"
        + " s.ship_num, s.ship_name, t.exp_year_from,"
        + " p.person_num, p.last_name ||', '||p.first_name,"
        + " i.institution_num, i.institution"

*/
	public static int EXP 		=0;
	public static int SHIP_ID 	=1; 
	public static int SHIP 		=2; 
	public static int YEAR		=3; 
	public static int CHIEF_ID 	=4; 
	public static int CHIEF 	=5; 
	public static int INST_ID 	=6; 
	public static int INST 		=7; 

        public ExpRecord(ResultSet rs, int count)
        {
                super(rs, count);
        }
	
	protected Object value(int index, String  val)
        {
                if ((index != CHIEF_ID) && (index != CHIEF))
                        return super.value(index,val);
                else {
                        Vector v = new Vector();
                        v.add(val);
                        return v;
                }
        }

        public void update(int index, String value)
        {
                Vector v;
                if ((v= (Vector)record.elementAt(index)) != null)
                        v.add(value);
        }


        public String getValue(int index)
        {
                if ((index != CHIEF_ID) && (index != CHIEF))
                       	return (String)record.elementAt(index);
                else {
                       	String val ="";
                       	Vector v = (Vector)record.elementAt(index);
                       	for (int i=0; i< v.size(); i++)
                               	if (i != 0)
                                       	val += "; " + (String)v.elementAt(i);
                               	else val+= (String)v.elementAt(i);
                       	return val;
                }
        }

	public String display()
	{
		return (String)record.elementAt(EXP);
	}

}

