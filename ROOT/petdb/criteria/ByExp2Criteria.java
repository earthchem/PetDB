
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByExp2Criteria extends Criteria 
{

	public static String EXP = "0";
	public static String SHIP = "1";
	public static String YEAR = "2";
	public static String CHIEF= "3";
	public static String INST = "4";
	
	int type =1;
 
	public ByExp2Criteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByExp2QryModel();
		
		dataWrapper = WrapperCollection.get(WrapperCollection.ByExpInnerWrapper);
	}

        public int setValues(String key, String [] values)
        {

		if (key.equals(EXP)) 
		{
			type = 1;
			if (isSet(SHIP)) parameters.remove(SHIP);
			if (isSet(YEAR)) parameters.remove(YEAR);
			if (isSet(CHIEF)) parameters.remove(CHIEF);
			if (isSet(INST))parameters.remove(INST);
		} else {
			if (isSet(EXP)) parameters.remove(EXP);
			type = 2;
		}
		return super.setValues(key,values);
	}



	public int type()
	{
		return type; 
	}

}


class ByExp2QryModel extends QueryModel
{

        public String getQueryStr(Criteria criteria)
        {

                /*String qry = "SELECT DISTINCT t.expedition_num,"
												+ " t.expedition_name||decode(nvl(t.leg, '-'), '-', ' ','-'||t.leg) name_leg,"
                        + " s.ship_num, s.ship_name, t.exp_year_from,"
                        + " p.person_num,"
												+ " p.last_name||decode(nvl(p.first_name, '-'), '-', ' ',', '||p.first_name) chief,"
                        + " i.institution_num, i.institution"
                        + " FROM expedition t, ship s, person p,"
                        + " institution i, chief_scientist c";
                */
                String qry = "SELECT DISTINCT t.expedition_num,"
												+ " t.expedition_name||decode(nvl(t.leg, '-'), '-', ' ','-'||t.leg) name_leg,"
                        + " s.ship_num, s.ship_name, nvl(TO_CHAR(t.exp_year_from), 'UNKNOWN') year_from,"
                        + " p.person_num,"
												+ " p.last_name||decode(nvl(p.first_name, '-'), '-', ' ',', '||p.first_name) chief,"
                        + " i.institution_num, nvl(i.institution, 'UNKNOWN') institution"
                        + " FROM expedition t, ship s, person p,"
                        + " institution i, chief_scientist c";
                if ( ((ByExp2Criteria)criteria).type() == 2)
                {

                    qry+= " WHERE t.expedition_num in ("
                        + "      SELECT DISTINCT tt.expedition_num"
                        + "      FROM expedition tt, chief_scientist cc"
                        + "      WHERE ";
                 if ( criteria.isSet(ByExp2Criteria.CHIEF) )
                    qry+= "      cc.person_num in " + criteria.getValuesAsStr(ByExp2Criteria.CHIEF,true) + " AND "
                        + "      tt.expedition_num = cc.expedition_num ";
                 else
                     qry+= "     tt.expedition_num = cc.expedition_num (+) ";

                 if (criteria.isSet(ByExp2Criteria.SHIP))
                    qry+= "     AND tt.ship_num = " +  criteria.getValueAsStr(ByExp2Criteria.SHIP);

                 if (criteria.isSet(ByExp2Criteria.YEAR))
                    qry+= "     AND tt.exp_year_from in " + criteria.getValuesAsStr(ByExp2Criteria.YEAR);

                 if (criteria.isSet(ByExp2Criteria.INST))
                    qry+= "     AND tt.institution_num = " +criteria.getValueAsStr(ByExp2Criteria.INST);
		
		    qry+= " ) ";

                    qry+= " AND s.ship_num = t.ship_num"
                        + " AND c.expedition_num(+)= t.expedition_num"
                        + " AND p.person_num(+) = c.person_num"
                        + " AND i.institution_num (+) = t.institution_num";
                }
                else 
                {
                   qry+= " WHERE ";

                   if (criteria.isSet(ByExp2Criteria.EXP))
                   qry+= " t.expedition_num in " +  criteria.getValuesAsStr(ByExp2Criteria.EXP,true) + " AND ";


                   qry+= " c.expedition_num (+)= t.expedition_num "
                        + " AND s.ship_num (+)= t.ship_num"
                        + " AND p.person_num (+)= c.person_num"
                        + " AND i.institution_num (+) = t.institution_num";

                }

                qry += " ORDER BY t.expedition_name||decode(nvl(t.leg, '-'), '-', ' ','-'||t.leg), s.ship_name, "
                        + " year_from, p.last_name||decode(nvl(p.first_name, '-'), '-', ' ',', '||p.first_name), institution";

                return qry;
        }

        public String getQueryStr(Criteria criteria, String filter)
        {
                return getQueryStr(criteria);
        }


}
