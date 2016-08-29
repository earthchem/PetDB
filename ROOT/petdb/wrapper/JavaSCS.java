package petdb.wrapper;


import java.util.*;

public class JavaSCS{

        static  String geo_delimiter ="@@@";
        static  String exp_delimiter ="|";
        static  char geo_space 	 ='!';

        public static String getGeoList(Vector v)
        {
                String  r_str = "";
                for (int i= 0; i < v.size() ; i++)
                        r_str += geo_delimiter + v.elementAt(i);

                return r_str.replace(' ',geo_space);
        }
        
	public static String getExpL(Vector v)
        {
                String  r_str = "";
                for (int i= 0; i < v.size() ; i++)
                        r_str += exp_delimiter + v.elementAt(i);

                return r_str; 
        }
}
