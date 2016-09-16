package petdb.query;

import petdb.data.*;

public class  DownloadPurposeStatisticsDSQuery extends NonPersistentDynamicCtlQuery 
{
	public  DownloadPurposeStatisticsDSQuery(String filter) 
	{
		super();
		v_filter = filter;
        setQuery();
		
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new DownloadPurposeStatisticsDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}

	protected int setQuery()
	{
		qry = "select count(purpose) as purpose_cnt, purpose from download_statistics ";
		qry +=v_filter;
		qry +=" group by purpose order by purpose";
		return 1;
	} 	  

}

