/*$Id:*/
package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class ReferenceInfo1DCtlQuery extends NonPersistentDynamicCtlQuery 
{
	public ReferenceInfo1DCtlQuery(String filter)
        {
                super(filter);
        }

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new ReferenceInfo1DS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setFilter()
	{
		qry = " select p.last_name||', '||p.first_name, r.pub_year, r.title,"
			+ "r.journal"
                	+ "||decode(nvl(r.volume,''),'','','; Vol: '||r.volume)"
                    +" ||CASE WHEN (FIRST_PAGE is null or first_page = 0) and (last_PAGE is null or last_page = 0) THEN ''"
                +"	WHEN (FIRST_PAGE is null or first_page = 0) THEN '; pg: '||last_page"
                +"	WHEN (last_PAGE is null or last_page = 0) THEN '; pg: '||first_page"
                +"	ELSE '; pg: '||first_page||'-'||last_page END,"
			+ " r.book_title, r.book_editor, r.publisher,"
			+ " prepared.locations, r.publication_doi, r.status||' ('||to_char(r.Data_entered_date, 'mm/dd/yyyy')||')', r.PUBLIC_COMMENT"
			+ " from person p, author_list al, reference r,"
			+ " (select ref_num, count(*) locations from sample_comment where ref_num =" + v_filter
			+ " group by ref_num) prepared"
			+ " where p.person_num (+) 	= al.person_num"
			+ " and al.ref_num (+) 		= r.ref_num"
			+ " and prepared.ref_num(+)	= r.ref_num"
			+ " and r.ref_num 		=" + v_filter
			+ " order by al.author_order";
         
//		DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\refino1dqry.txt");
		return 1;
	} 	  
}
