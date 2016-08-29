package petdb.query;

import java.sql.*;
import java.util.*;
import petdb.data.*;
import petdb.criteria.*;

public class MethodDCtlQuery extends  NonPersistentDynamicCtlQuery
{

	String s_filter = null;

	public MethodDCtlQuery(String filter)
	{ super(filter); s_filter = null;}

	public MethodDCtlQuery(String filter, String s_filter)
	{
	        super();
                v_filter = filter;
		this.s_filter = s_filter;
                int t = setFilter();
 	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new MethodDS(rs);
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
		boolean splitYes = false;
		int numPieces=0; //number of pieces v_filter needs to be split when v_filter is too long
		if(v_filter.charAt(0)=='(') //List of data quality number
		{
            int cnt=0;
            for(int i=0;i<v_filter.length();i++)
            {
                if( v_filter.charAt(i)==',' ) cnt++;
            }
			if( cnt >= 1000) //maximum expression list number reacher. We need to split into smaller pieces
			{
				splitYes=true;
				numPieces= cnt/999 + 1;
			}
		}
		if (s_filter != null)
		{
			splitYes=false;
		}
	    if( splitYes == false)	
        {
            regularQuery();
        }
        else   unionedQuery(numPieces);
	
	   // DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\methoddctlqry.txt");
		return 1;
	}
	
	private void regularQuery()
	{
		qry = "select distinct dq.data_quality_num, m.method_code,"
				+ " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year), r.ref_num,"
				+ " i.institution, m.method_name, m.method_note"
				+ " from person p, author_list al,"
				+ " institution I, method m, reference r, data_quality dq ";

			
			if (s_filter != null)
			{
				 qry +=", " + v_filter;
			}
			
			qry += 	  " where m.method_num 		= dq.method_num"
				+ " and i.institution_num 	= dq.institution_num"
				+ " and p.person_num(+) 	= al.person_num"
				+ " and (al.ref_num(+) = r.ref_num and al.author_order = 1)";

			if (s_filter != null)
			{
				qry += " and (r.ref_num 		= dq.ref_num and r.status = 'COMPLETED')"
					+ " and dq.data_quality_num 	= prepared.data_quality_num"
					+ " and " + s_filter;
			} else 
			{
				qry += " and (r.ref_num 		= dq.ref_num and r.status = 'COMPLETED')"
					+ " and dq.data_quality_num 	in " + v_filter;
			}
			
			qry += " order by m.method_code";
	}
	
	private void unionedQuery(int splitnum)
	{
		int len = v_filter.length();
		int pos = len/splitnum;
		qry ="";
		int pos1=0;
        int pos2= 0;
		for(int i=0;i<splitnum;i++)
		{
		  if( i == splitnum-1) //last string
			  pos2 = len;
		  else
		      pos2=v_filter.lastIndexOf(",",pos*(i+1)-1);
		  String subStr = v_filter.substring(pos1+1,pos2-1);
		  qry += "select distinct dq.data_quality_num, m.method_code,"
				+ " p.last_name||decode(nvl(r.pub_year,''),'','',', '||r.pub_year), r.ref_num,"
				+ " i.institution, m.method_name, m.method_note"
				+ " from person p, author_list al,"
				+ " institution I, method m, reference r, data_quality dq "
                + " where m.method_num 		= dq.method_num"
				+ " and i.institution_num 	= dq.institution_num"
				+ " and p.person_num(+) 	= al.person_num"
				+ " and (al.ref_num(+) = r.ref_num and al.author_order = 1)"
                + " and (r.ref_num 		= dq.ref_num and r.status = 'COMPLETED')"
					+ " and dq.data_quality_num 	in (" + subStr+")";
            if(i != splitnum -1 )
                qry +=" union ";
		    pos1=pos2;
	      }	
			qry += " order by 2";
	}
}
