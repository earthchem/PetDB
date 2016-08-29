package petdb.wrapper;

import java.util.*;

import org.apache.commons.lang.StringEscapeUtils;

/* This class try to assemble list of Geographic location names which will be used in the HTML selection list */
public class JavaSCSerializer{

        static  String geo_delimiter ="@@@";
        static  String exp_delimiter ="|";
        static  char geo_space 	 ='!';

        public static String getGeoList(Vector v)
        {
        	 String  r_str = "";
             for (int i= 0; i < v.size() ; i++)
             {
                 	String eleStr = (String) v.elementAt(i);
                 	eleStr =StringEscapeUtils.escapeJavaScript(eleStr); 
                    r_str += geo_delimiter +eleStr;
             }
             return r_str.replace(' ',geo_space);
        }
        
	public static String getExpeditionList(Vector v)
        {
                String  r_str = "";
                for (int i= 0; i < v.size() ; i++)
                    r_str += exp_delimiter + StringEscapeUtils.escapeJavaScript((String)v.elementAt(i));
               
                return r_str; //.replace(' ',exp_space);
        }
}
