package petdb.data;

import java.util.*; 
import petdb.query.DataDCtlQuery;
import petdb.query.SimpleQuery;

public class DataBrowser
{
	private Map authorMap = new HashMap();
	private String authorQ;
	private String sampleQ;
	private final int COLUMNS = 9;
	private String type;
	int step = 1000;
    private List samples = new ArrayList();
	private String authorQD =  "select a.REF_NUM, p.LAST_NAME||' '||p.first_name||'; '  "+
	    " from  author_list a, person p where  a.PERSON_NUM = p.PERSON_NUM and a.REF_NUM in "+        
	    " (select distinct r.ref_NUM from MINERAL m, batch b, table_in_ref t, reference r where m.BATCH_NUM = b.BATCH_NUM and m.MINERAL_NUM = 151 "+
	    " and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and t.REF_NUM = r.REF_NUM and r.status = 'COMPLETED') order by a.ref_num, a.AUTHOR_ORDER";

	private String sampleQD = "select distinct s.sample_num, s.sample_id, l.LATITUDE , l.LONGITUDE, l.LOC_PRECISION, e.EXPEDITION_code, r.TITLE||'; '||r.journal||', '||r.pub_year,  r.REF_NUM, rt.ROCKTYPE_NAME||', '||rc.ROCKCLASS "+
		"from MINERAL m, batch b, sample s, table_in_ref t, reference r, station st, station_by_location sl, LOCATION l, EXPEDITION e, sample_comment sc, ROCKCLASS rc, rocktype rt "+
		"where rc.ROCKTYPE_NUM=rt.rocktype_num and rc.ROCKCLASS_NUM = sc.ROCKCLASS_NUM and sc.sample_num = s.sample_num and e.EXPEDITION_NUM =st.EXPEDITION_NUM and l.LOCATION_NUM= sl.LOCATION_NUM and sl.STATION_NUM = st.STATION_NUM and sl.LOCATION_ORDER=1 and st.STATION_NUM=s.STATION_NUM and m.BATCH_NUM = b.BATCH_NUM and b.SAMPLE_NUM = s.SAMPLE_NUM "+
		"and m.MINERAL_NUM = 151 "+
		"and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and t.REF_NUM = r.REF_NUM and r.status = 'COMPLETED' order by s.sample_num, r.ref_num";    

	private String sampleQX = "select distinct s.sample_num, s.sample_id, l.LATITUDE , l.LONGITUDE, l.LOC_PRECISION, e.EXPEDITION_code, r.TITLE||'; '||r.journal||', '||r.pub_year,  r.REF_NUM, rt.ROCKTYPE_NAME||', '||rc.ROCKCLASS "+
		"from batch b, sample s, sample_comment sc, table_in_ref t, reference r, rockclass rc, rocktype rt, station st, station_by_location sl, LOCATION l, EXPEDITION e "+
		"where s.SAMPLE_NUM = sc.SAMPLE_NUM and e.EXPEDITION_NUM =st.EXPEDITION_NUM and l.LOCATION_NUM= sl.LOCATION_NUM and sl.STATION_NUM = st.STATION_NUM and sl.LOCATION_ORDER=1 and st.STATION_NUM=s.STATION_NUM "+
		"and b.SAMPLE_NUM = sc.SAMPLE_NUM and sc.rockclass_num = rc.rockclass_num and rc.rocktype_num = 37 and rc.ROCKCLASS_num in (157,122,142,171,158,172,170,144) and rc.ROCKTYPE_NUM=rt.rocktype_num "+
		"and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and t.REF_NUM = r.REF_NUM and r.status = 'COMPLETED'";

	private String authorQX ="select a.REF_NUM, p.LAST_NAME||' '||p.first_name||'; ' from  author_list a, person p where  a.PERSON_NUM = p.PERSON_NUM and a.REF_NUM in "+       
	    "(select distinct r.REF_NUM from batch b, sample_comment s, table_in_ref t, reference r, rockclass rc "+ 
	    "where b.SAMPLE_NUM = s.SAMPLE_NUM and s.rockclass_num = rc.rockclass_num and rc.rocktype_num = 37 and rc.ROCKCLASS_num in (157,122,142,171,158,172,170,144) "+ 
	    "and b.TABLE_IN_REF_NUM = t.TABLE_IN_REF_NUM and t.REF_NUM = r.REF_NUM and r.status = 'COMPLETED' ) order by a.ref_num, a.AUTHOR_ORDER"; 


        public DataBrowser(String type) {
        	this.type = type;
        	if("diamond".equals(type)) {
            this.authorQ = authorQD;
            this.sampleQ = sampleQD;
        	} else {
        		this.authorQ = authorQX;
                this.sampleQ = sampleQX;
        	}
     }
       
       public String getData() {
            createAuthorMap();
            return generateData();
       }
       
       public String getDataByPage(int pageNum) {
         createAuthorMap();
         generateDataList();
         int max = pageNum * step;
         int min = (pageNum-1)*step;
         if(max >= samples.size()) max = samples.size();
         if(min > max) return "The max page number is "+samples.size()/step+".  Please re-select page number.";
         StringBuffer sb = new StringBuffer("[");
         int i = min;
         for( ; i < max; i++) {
            if(i > min) sb.append(",");
            sb.append((String)samples.get(i));
         }
         String json = sb.append(",{\"Total Samples\":\""+(max-min)+"\"}]").toString();
         return json;
       }
       
       
        private void createAuthorMap() {
	        SimpleQuery sq = new SimpleQuery(authorQ);
	        List list = sq.getList(2);
	        Iterator it = list.iterator();
	        String previous="";
	        String names =null;
	        
	        while(it.hasNext()) {
	            String a[] = (String[])it.next();
	            if(previous.equals(a[0])) names +=a[1];
	            else {
	                if(!"".equals(previous)) authorMap.put(previous,names);                  
	                names = a[1];
	                previous = a[0];
	            }
	        }
        }
        
        
        public String generateData() {
        	SimpleQuery sq = new SimpleQuery(sampleQ);
            StringBuffer sbr = new StringBuffer();
	        List list = sq.getList(COLUMNS);
	        Iterator it = list.iterator();
	        sbr.append("["); 
	        int i = 0;   
	        String pSample ="";
	        String pRef ="";
	        String p[] = null;
	        String citation = "";
	        String first ="";
            boolean samePrevious = false;
	         while(it.hasNext()){
                String a[] = (String[])it.next();
	            if("".equals(pSample)) first = a[0];
	            if(!a[0].equals(pSample)) { 
                     samePrevious = false;
	                 if(!"".equals(pSample)) {
	                    if(!first.equals(pSample)) sbr.append(","); 
	                    p[6] = citation;
                        sbr.append("{"+getRow(p)+"}"); 
	                 }            
	                citation = (String)authorMap.get(a[7])+" "+a[6];   
	            } else if(!a[7].equals(pRef)) {  
                    samePrevious = true;
	                citation +="<br/>"+ (String)authorMap.get(a[7])+" "+a[6]; 
	            }  
	            p = a;
	            pSample = a[0];
	            pRef = a[7]; 
	        } 
            
            if(!samePrevious)  sbr.append(","); 
            p[6] = citation;
            sbr.append("{"+getRow(p)+"}");   
	     sbr.append("]");  
	     return sbr.toString();
	   //  DataDCtlQuery.writeQueryToFile(sbr.toString(), "C:\\Users\\bhchen\\Downloads\\"+type+ ".txt");
     }
     
     
     private void generateDataList() {
        	SimpleQuery sq = new SimpleQuery(sampleQ);
            StringBuffer sbr = new StringBuffer();
	        List list = sq.getList(COLUMNS);
	        Iterator it = list.iterator();
	   //     sbr.append("["); 
	        int i = 0;   
	        String pSample ="";
	        String pRef ="";
	        String p[] = null;
	        String citation = "";
	        String first ="";
            boolean samePrevious = false;
	         while(it.hasNext()){
	             String a[] = (String[])it.next();
	            if("".equals(pSample)) first = a[0];
	            if(!a[0].equals(pSample)) { 
                     samePrevious = false;
	                 if(!"".equals(pSample)) {
	                    if(!first.equals(pSample)) {samples.add(sbr.toString()); sbr =  new StringBuffer();};
	                    p[6] = citation;
                        sbr.append("{"+getRow(p)+"}"); 
	                 }            
	                citation = (String)authorMap.get(a[7])+" "+a[6];   
	            } else if(!a[7].equals(pRef)) {  
                    samePrevious = true;
	                citation +="<br/>"+ (String)authorMap.get(a[7])+" "+a[6]; 
	            }  
	            p = a;
	            pSample = a[0];
	            pRef = a[7]; 
	        } 
            
            if(!samePrevious)  {samples.add(sbr.toString()); sbr =  new StringBuffer();}; 
            p[6] = citation;
            sbr.append("{"+getRow(p)+"}");   
            samples.add(sbr.toString());
	    // sbr.append("]");  
	   //  return sbr.toString();
     }

	 public String getRow(String a[])
	 {
	          StringBuffer sb = new StringBuffer(); 
	          sb.append(getNameValue("name",a[1])+",");
	          sb.append(getNameValue("lat",a[2])+",");
	          sb.append(getNameValue("lon",a[3])+",");
	          sb.append(getNameValue("content",getContent(a)));
	           return sb.toString();
	 }
	 
	 private String getNameValue(String name, String value) {
		 return "\""+name+"\":"+"\""+value+"\"";
	 }
	 
	 private String getContent(String a[]) {
	    String content ="<table style='line-height:110%;'><tbody>"+
	            "<tr><td nowrap=''><b>Sample ID:</b></td><td nowrap=''>WASMRTN-007-002</td></tr>"+
	            "<tr><td valign='top'><b>Geospatial Info:</b></td><td>Longitude: "+a[3]+"<br>Latitude: "+a[2]+"<br>Precision: "+a[4]+"<br></td></tr>"+
	            "<tr><td valign='top'><b>Sample Type:</b></td><td>"+a[8]+"</td></tr>"+
	            "<tr><td><b>Cruise ID:</b></td><td>"+a[5]+"</td></tr>"+
	            "<tr><td><b>Source Database:</b></td><td><a target='_blank' href='http://petdb.org/petdbWeb/search/sample_info.jsp?sampleID="+a[1]+"'><img src='http://ecp.iedadata.org/magglass.png'>PETDB</a></td></tr>"+
	            "<tr><td><b>Citation(s):</b></td><td></td></tr>"+
	            "<tr><td colspan='2' style='font-size:.8em;line-height:100%'>"+a[6]+"</td></tr>"+
	            "</tbody></table>";
	           return content;
	 }
			
}
