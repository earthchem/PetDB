package petdb.data;

import java.util.Arrays;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import petdb.query.SimpleQuery;

public class ExpeditionWS {
    private String url = "http://www.marine-geo.org/tools/new_search/entryservice.php?alias_id=";
    Expedition ex = null;
    
    public ExpeditionWS(String expeditionNum) {
      String eName = getExpeditionName(expeditionNum);
      if(eName == null) return;
      sendRequests(eName);
    }
    
    public Expedition getExpedition() { return ex;}

    private void sendRequests(String expditionName) {
        try {
            HttpURLConnection conn = get_connection(url+expditionName, "GET");
            conn.connect();
            parse(conn);
        }
        catch(IOException e) { System.err.println(e); }
        catch(NullPointerException e) { System.err.println(e); }
    }

    private HttpURLConnection get_connection(String url_string,
                                             String verb) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(url_string);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(verb);
        }
        catch(MalformedURLException e) { System.err.println(e); }
        catch(IOException e) { System.err.println(e); }
        return conn;
    }

    private void parse(HttpURLConnection conn) {
        try {
            String xml = "";
            BufferedReader reader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String next = null;
            
            while ((next = reader.readLine()) != null)  xml += next;
            
            if(xml.length() < 30)   return;
            else {
                ex = new Expedition();
                SAXParser parser =SAXParserFactory.newInstance().newSAXParser();
                parser.parse(new ByteArrayInputStream(xml.getBytes()),new SaxParserHandler());
            }
        }
        catch(IOException e) { System.err.println(e); }
        catch(ParserConfigurationException e) { System.err.println(e); }
        catch(SAXException e) { System.err.println(e); }
    }
    
    private String getExpeditionName(String expeditionNum) {
        String q = "select expedition_name from expedition where expedition_num ="+expeditionNum;
        SimpleQuery sq = new SimpleQuery(q);
        ResultSet rs = sq.getResultSet();
        String name = null;
        try {
            if(rs.next()) name = rs.getString(1);
        } catch (SQLException e) {
				System.out.println("SQLException in getExpeditionName: "+ e.getMessage());
        }	catch (Exception e) {
				System.out.println("Exception in getExpeditionName: "+ e.getMessage());
        } finally {
            sq.close();
        }    
    
        return name;
    }
    

   class SaxParserHandler extends DefaultHandler {
       boolean isDataset = false;
       String tagName = "";
      
        public void startElement(String uri, String name,
                                 String qname, Attributes att) {
                                    boolean startDate = false;
           tagName = qname; 
        
            if (qname.equals("entry")) {                
                ex.setName(att.getValue("id"));
                ex.setNameUrl(att.getValue("url"));
            } else if (qname.equals("platform") && !isDataset) {
                ex.setShip(att.getValue("id"));
                ex.setShipUrl(att.getValue("url"));
            } else if (qname.equals("data_set")) { 
                isDataset = true;
            } else if(tagName.equals("operator")) {
                ex.setInstitution(att.getValue("full_name"));    
                ex.setInstitutionUrl(att.getValue("url"));         
            } else if(tagName.equals("scientist")) {
                String cs = ex.getChiefScientists();
                if( cs == null) ex.setChiefScientists(att.getValue("name"));
                else ex.setChiefScientists(cs+"; "+att.getValue("name"));   
            }
        }

        public void characters(char[ ] ch, int start, int length) {
            if(tagName.equals("start_date")) ex.setDate(new String(ch, start, length));
            if(tagName.equals("stop_date")) ex.setDate(ex.getDate()+" to "+new String(ch, start, length));
        }
   
    }
}
