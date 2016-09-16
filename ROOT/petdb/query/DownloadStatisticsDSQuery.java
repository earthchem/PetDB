
package petdb.query;

import petdb.data.*;

public class DownloadStatisticsDSQuery extends NonPersistentDynamicCtlQuery 
{
	public DownloadStatisticsDSQuery(String filter) 
	{
		super();
		v_filter = filter;
        setQuery();
		
	}

	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new DownloadStatisticsDS(rs);
		} 
		catch (Exception e) 
		{
		}
	}



	protected int setQuery()
	{
		qry = "select to_char(trunc(download_date,'month'),'YYYY') as year, to_char(trunc(download_date,'month'),'MM') as month, "
	          +" count(distinct ip_address) as unique_ip, count(distinct email) as unique_email, count(id) as monthly_download from download_statistics ";
		qry +=v_filter;
		qry +=" group by trunc(download_date,'month') order by year, month";
		return 1;
	} 	  


}
