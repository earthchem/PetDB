
package petdb.criteria;

import java.util.*;
import java.sql.*;
import petdb.query.*;
import petdb.wrapper.*;

public class ByPub2Criteria extends Criteria 
{

	public static String AUTH 	= "0";
	public static String YEAR 	= "1";
	public static String JOUR	= "2";
	public static String A_ORDER 	= "A_ORDER";
	public static String KEYWORD	= "KEYWORD";
    public static String ALERT = "ALERT";
    public static String IN_PROGRESS = "IN_PROGRESS";
    public static String COMPLETED = "COMPLETED";

	public ByPub2Criteria() 
	{
		parameters = new Hashtable();
		qryModel = new ByPub2QryModel();
		dataWrapper = WrapperCollection.get(WrapperCollection.ByPubInnerWrapper);
	}
    
    public void setStatus(String status) {
    ((ByPub2QryModel)qryModel).status = status; 
    }
}


class ByPub2QryModel extends QueryModel
{

    String status = ByPub2Criteria.COMPLETED;
    
        public String getQueryStr(Criteria criteria)
        {
            String refStatus = "r.status = '"+status+"'";
	        String    qry = "SELECT r.ref_num, p.person_num,  p.last_name||', '||p.first_name, al.author_order,"
                        + " r.pub_year, r.title, r.journal, r.volume,"
			+ " r.book_title, r.book_editor, r.publisher,"
			+ "decode(nvl(r.first_page,''),'','',''||r.first_page)"
			+ "||decode(nvl(r.last_page,''),'','',' - '||r.last_page)"
                        + " FROM reference r, person p, author_list al"
                        + " WHERE (r.ref_num in ("
                        + "     SELECT rr.ref_num"
                        + "     FROM reference rr, author_list alal"
                        + "     WHERE ";

                        if (       (criteria.isSet(ByPub2Criteria.A_ORDER) )
                                && (criteria.getValueAsStr(ByPub2Criteria.A_ORDER).equals("1"))
                           )
                     qry+="     alal.author_order = 1 AND ";
                        if ( criteria.isSet(ByPub2Criteria.AUTH) )
                     qry+="     alal.person_num in " +  criteria.getValuesAsStr(ByPub2Criteria.AUTH) +  " AND ";

                     qry+="     rr.ref_num = alal.ref_num";

                        if ( criteria.isSet(ByPub2Criteria.KEYWORD))
                     qry+="     AND rr.title like  '%" +
                                        criteria.getValueAsStr(ByPub2Criteria.KEYWORD).toUpperCase() +"%'";

                        if ( criteria.isSet(ByPub2Criteria.YEAR))
                     qry+="     AND rr.pub_year in " + criteria.getValuesAsStr(ByPub2Criteria.YEAR);

                        if ( criteria.isSet(ByPub2Criteria.JOUR) )
                     qry+="     AND rr.journal = '" +  criteria.getValueAsStr(ByPub2Criteria.JOUR) + "'";
                     qry+=") and " +refStatus +")"
                        + " AND al.ref_num(+) = r.ref_num"
                        + " AND p.person_num (+) = al.person_num"
                        + " ORDER BY r.ref_num, al.author_order";
                   //      DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\bhchen\\Downloads\\referencedctl.sql");

		return qry;
	}

        public String getQueryStr(Criteria criteria, String filter)
        {
                return getQueryStr(criteria);
        }

}
