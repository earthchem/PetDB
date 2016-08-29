
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class TestQuery extends ControlQuery 
{

	public TestQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		//qry = " select distinct session_id, session_id from sessionparam";
		qry = " select count(*),'1' from sessionparam where session_id = "
		+ "'BYQdZoAqDSYD1tXGWsH1Opc1cH8Qofy1MZBhRbZ5KOV12zeoiiuI!130330283!1100533917692'"; 
		//qry = "select distinct item_type_code, item_type_code from item_type";
		/*
                qry ="select distinct to_char(r.data_entered_date,'YYYY-MM-DD'),"
                        + " to_char(r.data_entered_date,'YYYY-MM-DD') "
                        + " from reference r"
                        + " where r.data_entered ='Y'"
                        + " and (r.journal is not null or r.book_title is not null) "
                        + " order by to_char(r.data_entered_date,'YYYY-MM-DD') DESC ";
                return 1;
		*/
		/*
		qry = "SELECT  to_char(data_entered_date,'YYYY-MM-DD')"
			+ " FROM Reference where data_entered = 'Y'"
			+ " and  to_char(data_entered_date,'YYYY-MM-DD') >= '2004-01-01'";
		*/
		/*
		qry = "SELECT DISTINCT alteration_code, alteration_name"
                        + " FROM alteration"
                        + " ORDER BY alteration_code";
		qry = " select t.samp_technique_code, t.samp_technique_desc"
                        + " from samp_technique_list t order by t.samp_technique_code";
		*/
		System.out.println("Query SET");
		return 1;
	}


	public synchronized void  prepareData() 
	{
		try {
			ds = (DataSet) new ListDS(rs);
			System.out.println("Data Prepared");
		} 
		catch (Exception e) 
		{
			System.out.println("Exception = " + e.getMessage());
		}
	}

	static public void main(String[] arg)
	{
		TestQuery tq = new TestQuery();
		DataSet ds = tq.getDataSet();
	}
	

}

