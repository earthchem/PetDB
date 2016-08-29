package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;
import petdb.config.*;

public class ReferenceDCtlQuery extends DynamicCtlQuery 
{
	static public String All ="All"; 
	static public String TheQuery ="theQuery"; 
	static public String References ="References"; 
	static public String Samples ="Samples"; 
	static public String Chemical ="Chemical"; 
	static public String Expedition ="Expedition";
    static public String Expedition2 ="Expedition2";  
	static public String Publications ="Publications"; 
	static public String Contributions ="Contributions"; 

	private String s_filter = null;

	private String type = Samples;
	
	public ReferenceDCtlQuery(String filter, String type)
	{ 
	        super();
                v_filter = filter;
		this.type = type; 
		this.s_filter = null;
                int t = setFilter();
	}

	public ReferenceDCtlQuery(String filter, String s_filter, String type)
	{
	        super();
                v_filter = filter;
		this.s_filter = s_filter;
		this.type = type;
                int t = setFilter();
 	}

	public synchronized void prepareData() 
	{
		try {
			if ((type.equals(All)) || (type.equals(References)) || (type.equals(TheQuery)))
				ds = (DataSet) new EPubRecordDS(rs);
			else 
				ds = (DataSet) new PubRecordDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	public int updateData(String str, String str2)
	{
		changed = true;
                v_filter = str;
                s_filter = str2;
                return setFilter();
	}

	protected int setFilter()
	{
             String refStatus = " and r.status = 'COMPLETED'";
            if(type.equals(Expedition2)) refStatus = " and r.status = 'IN_PROGRESS'";

	if (type.equals(TheQuery)) 
	{	qry = v_filter;
	    //DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\referencedctl.sql");
		return 1;
	}
	qry = "select distinct r.ref_num, p.person_num,"
		+ " decode(nvl(p.last_name, '-'), '-', ' ',p.last_name||', '||p.first_name) name,"
		+ " al.author_order, r.pub_year, "
		+ " r.title, r.journal, r.volume";

		if ((type.equals(All)) || (type.equals(TheQuery)) || (type.equals(References)))
			qry += " ,r.book_title, r.book_editor, r.publisher, "
			+ "decode(nvl(r.first_page,''),'','',''||r.first_page)"
			+ "||decode(nvl(r.last_page,''),'','',' - '||r.last_page)";
		else 
			qry +=
			 ", r.book_title, r.book_editor, r.publisher, "
			+ "decode(nvl(r.first_page,''),'','',''||r.first_page)"
			+ "||decode(nvl(r.last_page,''),'','',' - '||r.last_page)";
		
		qry += " from person p, author_list al, reference r";

	if (s_filter != null)
	{
		 qry +=", sample_comment sc, sample s," + v_filter
		+ " where sc.ref_num = prepared.ref_num"
		+ " and sc.sample_num(+) = s.sample_num"
		+ " and ";
	} else {
		if (v_filter.length() != 0)
		{
			if (type.equals(Expedition)||type.equals(Expedition2))
			{
			qry +=", (select distinct t.ref_num from station st, sample sl, batch b,  table_in_ref t "
                    + " where sl.STATION_NUM = st.station_num and b.SAMPLE_NUM = sl.SAMPLE_NUM and t.TABLE_IN_REF_NUM = b.TABLE_IN_REF_NUM " 
					+ " and st.expedition_num =" + v_filter	
					+ " ) prepared where";
			} else if (
				   (type.equals(References)) 
				|| (type.equals(Publications))
				|| (type.equals(Contributions))
				 )
			{
				qry += " where ";
			} else 
		 		qry += " , (select distinct tir.ref_num"
                		+ "             from  table_in_ref tir, batch b"
                		+ "             where  tir.table_in_ref_num = b.table_in_ref_num"
                		+ "             and (" + DisplayConfigurator.toReplace(v_filter,'b') + ")"
                		+ "             ) prepared where ";
		} else qry += " where ";
	}

	qry += " p.person_num(+) = al.person_num"
		+ " and al.ref_num (+) = r.ref_num";

	if (s_filter != null)
	{
		qry += " and  (r.ref_num(+) = prepared.ref_num and r.status = 'COMPLETED')"
		+ " and s.sample_num = prepared.sample_num"
		+ " and " + s_filter;
	} else {
		if (v_filter.length() != 0 ) 
			if  (	(!type.equals(References)) 
				&& (!type.equals(Publications))
				&& (!type.equals(Contributions))
                && (!type.equals(Expedition))
				&& (!type.equals(Expedition2))
			     )
				qry += " and (r.ref_num = prepared.ref_num and r.status = 'COMPLETED')";
			else 
				if ((type.equals(References)))
					qry +=
					" and r.ref_num in (" + v_filter + ") and status = 'COMPLETED'";
 				else if (type.equals(Publications))  
					qry +=
					" and (r.journal is not null"
					+ " or r.book_title is not null)"
					+ " and r.data_entered_date >= to_date('"  
					+ v_filter + "','" + DisplayConfigurator.DateEnteredFormat + "')"
					+ " and r.status = 'COMPLETED'";
                else if (type.equals(Expedition) ) {
                    qry += " and (r.ref_num = prepared.ref_num and r.status = 'COMPLETED')";
                }
                else if (type.equals(Expedition2)) {
                    qry += " and (r.ref_num = prepared.ref_num and r.status = 'IN_PROGRESS' )";
                }
				else 
					qry +=
					" and r.journal is null"
					+ " and r.book_title is null"  
					+ " and r.status = 'COMPLETED'";
		if (type.equals(All)) qry +=" and r.status = 'COMPLETED'";
	}
	
	if (type.equals(Contributions))
		qry += " order by r.ref_num, al.author_order, r.pub_year";
	else 
		qry += " order by r.ref_num, al.author_order";
	
		//System.out.println("Query = " + qry);
	    //DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\referencedctl.sql");
      //   DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\bhchen\\Downloads\\referencedctl.sql");
		return 1;
	} 	  

}
