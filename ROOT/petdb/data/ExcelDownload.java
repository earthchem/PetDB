/*$Id:*/
package petdb.data;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import petdb.wrapper.*;
import petdb.criteria.*;
import petdb.query.*;
import petdb.config.*;

public class ExcelDownload extends HttpServlet 
{


	public boolean readAndWrite(HttpServletRequest request, HttpServletResponse  response) 
	throws IOException  
	{
		CCriteriaCollection c_c_collection;
        	CombinedCriteria c_criteria;
        	Criteria criteria;

        	int test =0;

		HttpSession session = request.getSession();

		if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
		{
          System.err.println("ExcelDownload:Error getting 'ccColl' attribute. Ignored.");
          return false; //ignore the request.
		}
        	c_criteria = c_c_collection.getCurrentCCriteria();


        	String criteria_type = CombinedCriteria.ByDataCriteria;
        	if ( (criteria_type = (String)request.getParameter("criteria_type")) == null)
                	criteria_type = CombinedCriteria.ByDataCriteria;

        	boolean mth_flag = (!criteria_type.equals(CombinedCriteria.ByRockModeCriteria));
        	Criteria criteriaOrg =c_criteria.getCriteria(criteria_type);
            Set itemSet = getKeySet(request);
            Wrapper wrapper = null;  
            FinalSampleDS final_data = null;
            Wrapper prev_wrapper = null; 
   
            if(itemSet.size() > 0) { 
                String resultSamples = (String)session.getAttribute("tagAlongSamples");
                TagAlongData tagAlong = new TagAlongData((Criteria)criteriaOrg.clone(), getKeySet(request), resultSamples);    
                criteria = tagAlong.getCriteria(); 
                prev_wrapper = criteria.getWrapper();
                wrapper = new FinalSampleWrapper(criteria, resultSamples);
                final_data= (FinalSampleDS)wrapper.getControlList("0");
            } else {
                wrapper = (FinalSampleWrapper)session.getAttribute("final_wrapper");
                prev_wrapper = criteriaOrg.getWrapper();
                final_data = (FinalSampleDS)wrapper.getControlList("0");               
                criteria = criteriaOrg;
            }
                     

            DataRecordDS data = (DataRecordDS)prev_wrapper.getControlList("0");
            String[] s_keys  =((DataRecordDS)data).getOrderedKeys();           
       
        int count = 0;
        	Vector columns = new Vector();
        	for (int j=0; j<s_keys.length; j++)
        	{
                	String[]  fields = ((ByChemistryCriteria)criteria).getParam(s_keys[j]);

                	if (fields == null ) continue;
              
                	for (int i=0; i< fields.length; i++) {
                        	columns.addElement( fields[i]);
                    }
        	}
        	 
        	ExcelDS excelDS = (ExcelDS)wrapper.getControlList("0");
        	Wrapper ref_wrapper = null;
        	Wrapper mth_wrapper = null;
        	Vector ref_columns = new Vector();
        	Vector mth_columns = new Vector();
        	ExcelDS ref_excelDS = null;
        	ExcelDS mth_excelDS = null;

        	if (criteria.isSet())
        	{
                	ref_wrapper = new ByPubWrapper(criteria);
                	ref_excelDS = (ExcelDS)ref_wrapper.getControlList("0");
                	((PubRecordDS)ref_excelDS).goFirst();
                	ref_columns = new Vector(ref_excelDS.getColumnCount());
                	if (mth_flag) //Not Rock Mode Analysis
                	{
                        	mth_wrapper = new MethodListWrapper((String)final_data.getAnalysisStr());                      
                        	mth_excelDS = (ExcelDS)mth_wrapper.getControlList("0");
                        	mth_columns = new Vector(mth_excelDS.getColumnCount());
                	}
        	}
		ServletOutputStream writeAt = null;
        try {
        writeAt = response.getOutputStream();
        } catch(Exception e)
        { 
            System.err.println("Error open OutputStream."+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error open OutputStream."+e.getMessage());
        }
        
        ExlDS exlDS=null;
        try{
        	exlDS = new ExlDS(excelDS, columns, writeAt);
        } catch(Exception e)
        { 
            System.err.println("Error New ExlDS."+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error new ExlDS."+e.getMessage());

        }
        try {		
		   exlDS.write();
        } catch(Exception e)
        { 
            throw new java.io.IOException("ExcelDownload:Error write to exlDS."+e.getMessage());
        }  
        
		WritableSheet sheet = exlDS.getSheet(ExlDS.DataSheet);
        int i = exlDS.getOffset() + 1;
        try {
            sheet.addCell(new Label(0,++i,"Criteria Set:")); 
        	if (c_criteria.isSet(CombinedCriteria.ByLongLatCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Longtitude/Latitude:"));
                 sheet.addCell(
                        new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByLongLatCriteria)));
        	}
        	if (c_criteria.isSet(CombinedCriteria.ByGeoCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Geographical Name:"));
                 sheet.addCell(new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByGeoCriteria)));
        	}
        	if (c_criteria.isSet(CombinedCriteria.ByTectCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Tectonic Name:"));
                 sheet.addCell(new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByTectCriteria)));
        	}

        	if (c_criteria.isSet(CombinedCriteria.ByRockCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Sample Name:"));
                 sheet.addCell(new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByRockCriteria)));
        	}
        	if (c_criteria.isSet(CombinedCriteria.ByExpCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Expedition Name:"));
                 sheet.addCell(new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByExpCriteria)));
       		}
        	if (c_criteria.isSet(CombinedCriteria.ByPubCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Publication Name:"));
                 sheet.addCell(new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByPubCriteria)));
        	}
        	if (c_criteria.isSet(CombinedCriteria.ByDataAvailCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Data Availability:"));
                 sheet.addCell(
                        new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByDataAvailCriteria)));
        	}
        	if (c_criteria.isSet(CombinedCriteria.ByDataVersionCriteria))
        	{
                 sheet.addCell(new Label(0,++i,"Data Version Date:"));
                 sheet.addCell(
                        new Label(1,i,c_criteria.getDsc(CombinedCriteria.ByDataVersionCriteria)));
        	}
	    } catch(Exception e)
        { 
            System.err.println("Error sheet addCell"+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error sheet.addCell::"+e.getMessage());
        }  	
		i = 0;
		exlDS.addSheet("References and Methods");
        try {
		    exlDS = new ExlDS(ref_excelDS, ref_columns, exlDS);
        } catch(Exception e)
        { 
            System.err.println("Error exlDS new 2."+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error exlDS new 2::"+e.getMessage());
        }
		exlDS.setDefault("References and Methods");
		sheet = exlDS.getSheet("References and Methods");
        try{
		    sheet.addCell(new Label(0,++i,"References:"));
        } catch(Exception e)
        { 
            System.err.println("Error exlDS.addCell 2."+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error exlDS.addCell 2::"+e.getMessage());
        }
		exlDS.setOffset(++i);
        try{
		    exlDS.write();
        } catch(Exception e)
        { 
            System.err.println("Error exlDS.write 2::"+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error exlDS.write 2::"+e.getMessage());
        }

        	i = exlDS.getOffset();
        	i +=2;

        	if (mth_flag)
        	{
                try{
         	        exlDS = new ExlDS(mth_excelDS, mth_columns, exlDS);
                } catch(Exception e)
                { 
                    System.err.println("Error exlDS.new 3::"+e.getMessage());
                    throw new java.io.IOException("ExcelDownload:Error exlDS.new 3::"+e.getMessage());
                }
                try{
			        exlDS.setDefault("References and Methods");
			        sheet = exlDS.getSheet("References and Methods");
			        sheet.addCell(new Label(0,++i,"Methods:"));
                } catch(Exception e)
                { 
                    System.err.println("Error exlDS.addCell 3."+e.getMessage());
                    throw new java.io.IOException("ExcelDownload:Error exlDS.addCell 3::"+e.getMessage());
                }
			    try{   
			        exlDS.setOffset(++i);
                  	exlDS.write();
                } catch(Exception e)
                { 
                    System.err.println("Error exlDS.write 3."+e.getMessage());
                    throw new java.io.IOException("ExcelDownload:Error exlDS.write 3::"+e.getMessage());
                }

        	}

        try{	
		    exlDS.getWorkbook().write();
        } catch(Exception e)
        { 
            System.err.println("Error exlDS.getWorkbook().write()::"+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error exlDS.getWorkbook().write()::"+e.getMessage());
        }
        try{
        	exlDS.closeBook();
        } catch(Exception e)
        { 
            System.err.println("Error exlDS.closeBook()::"+e.getMessage());
            throw new java.io.IOException("ExcelDownload:Error exlDS.closeBook()::"+e.getMessage());
        }
		int r;
            r = ref_wrapper.closeQueries();
        if (mth_flag)
            r = mth_wrapper.closeQueries(); 

		return true;

	}

	public int saveDownloadStatistics(HttpServletRequest request, HttpServletResponse  response)
	{
	    HttpSession ses = request.getSession();
		String ds = ses.getServletContext().getInitParameter("datasource");
	    String email = (String) ses.getAttribute("email");
	    String purpose = (String) ses.getAttribute("purpose");
        StatisticsQuery statq = new StatisticsQuery();
        
	    statq.setQuery("select count(*) from download_statistics");
	    statq.runQuery();
	    int num = Integer.parseInt(statq.getSingleResult());
	    if(num !=0 )
	    {
	        String s = new SimpleQuery("select max(id) from download_statistics").getSingleResult();
	        num = Integer.parseInt(s);
	    }
	    
	    String ipAddress = new IPAddress().getIpAddrWithFilter(request);
	    if(ipAddress != null)
	    {

	    	//saved information into download_statistis table
	        String insertQry = "insert into download_statistics (id,download_date,ip_address,email,purpose,system_info) values ("+ (++num) +",SYSDATE,'"+ipAddress+"','"+email+"','"+purpose+"','"+ds+"')";
            statq.setQuery(insertQry);
	        statq.runQuery();
	          
            int download_cnt = 0; //holds current download counts
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            
	        //update sample download counts             
			HashSet samples = (HashSet) ses.getAttribute("searched_samples");
            Iterator iter2 = samples.iterator();

	        while (iter2.hasNext()) {
	        	int samp_num = ((Integer) iter2.next()).intValue();
	            //System.out.println(samp_num);
	        	//Get the count of the sample with sample number samp_num
	        	//select download_cnt from sample_download where sample_num = 100 and MONTH_INFO = '2016-09';
	        	statq.setQuery("select download_cnt from sample_download where sample_num="+samp_num+" and month_info='"+strDate+"'");
	        	statq.runQuery();
	        	String it = statq.getSingleResult();
	        	
	        	//Max number of sample_download table
	        	statq.setQuery("select max(id) from sample_download");
	        	statq.runQuery();
	        	String s = statq.getSingleResult();
	            int download_max =0;
	            if(s != null )
	            	download_max= Integer.parseInt(s);
	            
	            if(it == null )
	            {
	            	//insert new entry when download_cnt = 1
	            	 String qrs = "insert into sample_download (id,sample_num,download_cnt,month_info,system_info) values ("+ (++download_max)+","+samp_num+",1,'"+strDate+"','"+ds+"')";
		             statq.setQuery(qrs);
		             statq.runQuery();
	            }
	            else
	            { //update the entry
		          download_cnt =  Integer.parseInt(it);
		          String qrs = "update sample_download set download_cnt = " +(++download_cnt) +"where sample_num="+samp_num+" and month_info='"+strDate+"'";
		          statq.setQuery(qrs);
		          statq.runQuery();
	            }	        	
	        }

	        //Update reference download counts
	        HashSet refs = (HashSet) ses.getAttribute("searched_refs");
	        Iterator riter = refs.iterator();
	        while (riter.hasNext()) {
	        	
	        	int ref_num = ((Integer) riter.next()).intValue();
	            //System.out.println(ref_num);
	        	//Get the count of download for reference with ref_num
	        	//select download_cnt from sample_download where sample_num = 100 and MONTH_INFO = '2016-09';
	            statq.setQuery("select download_cnt from reference_download where ref_num="+ref_num+" and month_info='"+strDate+"'");
	            statq.runQuery();
	            String it = statq.getSingleResult();
	            
	            //Max number of sample_download table
	            statq.setQuery("select max(id) from reference_download");
	            statq.runQuery();
	            String s = statq.getSingleResult();
	            
	            int download_max =0;
	            if(s != null )
	            	download_max= Integer.parseInt(s);
	            if(it == null )
	            {
	            	//insert new entry with download_cnt = 1
	            	 String qrs = "insert into reference_download (id,ref_num,download_cnt,month_info,system_info) values ("+ (++download_max)+","+ref_num+",1,'"+strDate+"','"+ds+"')";
	            	 statq.setQuery(qrs);
			         statq.runQuery();
	            }
	            else
	            { //update the entry
		          download_cnt =  Integer.parseInt(it);
		          String qrs = "update reference_download set download_cnt = " +(++download_cnt) +"where ref_num="+ref_num+" and month_info='"+strDate+"'";
		          statq.setQuery(qrs);
		          statq.runQuery();
	            }		        	
	        }	        
            statq.close();
            ses.removeAttribute("searched_refs");
            ses.removeAttribute("searched_samples");           
	        return num;
	    }
	    
	    return -1; //When -1 is returned. It means no statistics saved to database.
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
	{
		HttpSession session = request.getSession();

		ServletConfig config = this.getServletConfig();
		if (config == null)
		{
			return;
		}

		ServletContext application = config.getServletContext();
		if (application == null)
		{
			return;
		}
		
		String fileName = "data.xls";
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachement; filename=" + fileName);
	//	response.addHeader("Cache-Control", "no-transform, max-age=0");  
		
		readAndWrite(request,response);
        
        //Save download statistics        
		int statistisId = saveDownloadStatistics(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
	{
		doGet(request, response);
	}
    
    private Set getKeySet(HttpServletRequest request) {
        Set set = new HashSet();
        if("on".equals(request.getParameter("MAJ")))  set.add("MAJ");
        if("on".equals(request.getParameter("IR")))  set.add("IR");
        if("on".equals(request.getParameter("TE")))  set.add("TE");
        return set;
    }

}
