package petdb.xml; 


import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import petdb.config.*;

import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class GetSamplesXML extends HttpServlet
{
    Connection connection = null;
    public void service(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
         try
        {
            setConnection();
            res.setContentType("text/xml");
            PrintWriter out = res.getWriter();
            ServletContext ctx = getServletContext();
            
            String xslFileName = getInitParameter("TransfromFromDBStepOne");
            String fullPathXSLFile = ctx.getRealPath(xslFileName);   
            File xslFile = new File(fullPathXSLFile);
            
            String samplenum = req.getParameter("samplenum");
            
            // user just wants to know minimun and maximum sample numbers
            if (samplenum.equals("minmax"))
            {
                String minmax = getMinAndMaxSampleNum();
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.println("<minmax>");    
                out.println(minmax);        
                out.println("</minmax>");
            }
            else
            {   // easier to check DB for sample number before requesting XML
                if (checkForSampleNum(samplenum)) 
                {
                    Document outXML = getXmlFromDB(samplenum);
                
                    //DOMSerializer ser = new DOMSerializer();
                    //ser.serialize(outXML, new File("D:\\temp\\test23.xml"));
                    // XSLT transformation
                    Source xmlSource = new DOMSource(outXML);
                    //Source xmlSource = new StreamSource(xmlFile);
                    Source xslSource = new StreamSource(xslFile);
             
                    TransformerFactory transFact = TransformerFactory.newInstance();
                    Transformer trans = transFact.newTransformer(xslSource);
                    trans.transform(xmlSource, new StreamResult(out));
                }
                else
                {
                    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                    out.println("<error>");    
                    out.println("SAMPLE NUMBER: " + samplenum + " DOES NOT EXIST IN PETDB DATABASE");        
                    out.println("</error>");
                }
            }
        }
        catch (TransformerConfigurationException tce)
        {
            throw new ServletException(tce);
        }
        catch (TransformerException te)
        {
            throw new ServletException(te);
        }
    }
            
    private Document getXmlFromDB(String sampleNum)
    {
        Document doc = null;
		try
		{
           
			long lSampleNum = Long.parseLong(sampleNum);
			
			CallableStatement SQL2XMLProc = connection.prepareCall("CALL prc_getsamplesasxml(?, ?, ?, ?)");
			// set params
			SQL2XMLProc.setString(1, "EarthChemModel");
			SQL2XMLProc.setString(2, "EarthChemSample");	
            SQL2XMLProc.setLong(3, lSampleNum);			
			SQL2XMLProc.registerOutParameter(4, Types.CLOB);
			
			boolean ok = SQL2XMLProc.execute();
			Clob outXML = SQL2XMLProc.getClob(4);
            //System.out.println(outXML.getCharacterStream());
            //System.out.println(ok);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(false);
        
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(outXML.getAsciiStream()); // pass CLOB to parser
            connection.close();
        }
        catch (ParserConfigurationException pce)
        {
            System.out.println("ParserConfigurationException " + pce);
        }
        catch (FactoryConfigurationError fce)
        {
            System.out.println("FactoryConfigurationError " + fce);
        }

		catch (SQLException sqle)
		{
			System.err.println("Could not connect to the database " + sqle);
		}
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return doc;
    }
    
    private void setConnection()
    {
        try
        {
            DatabaseAdapter dbAdapt = DatabaseAdapter.getDatabaseAdapter();
            
            petdb.config.QueryCounter.created++;
            connection = dbAdapt.getConnection();
        }
        catch (SQLException sqle) 
		{
			System.err.println("Could not connect to the database " + sqle);
		}
        //catch (javax.naming.NamingException jne)
        //{
         //   System.err.println("Connection Pool problem -  NamingException: " + jne);
        //}
    }
    
    private boolean checkForSampleNum(String sampleNum)
    {
        try
        {
            boolean sampleexists = false;
            String sSql = "select sample_num from sample where sample_num = " + sampleNum;
			
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
            ResultSet rs = statement.executeQuery(sSql);
            if (rs.next())
                sampleexists = true;
            rs.close();
            return sampleexists;
        }
        catch (SQLException sqle) 
		{
			System.err.println("Could not connect to the database " + sqle);
            return false;
		}
    }
    
    private String getMinAndMaxSampleNum()
    {
        try
        {
            String sSql = "select min(sample_num) as min, max(sample_num) as max from sample";
			
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
            ResultSet rs = statement.executeQuery(sSql);
            rs.next();
            String output = "<minimum>" + rs.getString("min") + "</minimum>\n<maximum>" + rs.getString("max") + "</maximum>";
            rs.close();
            return output;
        }
        catch (SQLException sqle) 
		{
			System.err.println("Could not connect to the database " + sqle);
            return "PROBLEM";
		}
    }
} 
