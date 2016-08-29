
package petdb.criteria;

import java.util.*;
import org.apache.commons.lang.StringEscapeUtils;

import petdb.query.*;
import petdb.wrapper.*;

public abstract class  Criteria implements Cloneable
{
	Hashtable parameters;
	boolean flag = false;
	protected QueryModel qryModel;
	protected Wrapper dataWrapper;

	protected boolean  reset = false;

	public Wrapper getWrapper()
	{
		return dataWrapper;
	}
    
    public Hashtable getParameters() {return parameters; }
    public void setParameters(Hashtable par) {parameters = par; }
    
    public String getItemCodes() {
       StringBuffer sb = new StringBuffer();
       Iterator it = parameters.values().iterator();
       while(it.hasNext()) { 
           String arr[] =  ((String [])it.next());
         for(int i =0; i < arr.length; i++) {
            sb.append(",'"+arr[i]+"'");
         }
       }   
        return sb.toString().substring(1);
    }
	
	public boolean isReset()
	{
		return reset;
	}


	public String getValueAsStr(String param_key) 
	{
		synchronized(parameters)
		{
                	String ret_str ="";
                	if (parameters ==  null) return ret_str;

                	String[] val;

                	if ((val = (String[]) parameters.get(param_key)) == null) 
				return ret_str;
			else return (String)val[0];
		}
	}
	
	public void initCriteria(){;}

	public String getQryStr(String filter)
	{
		return qryModel.getQueryStr(this, filter); 
	}

	public String getQryStr()
	{
		return qryModel.getQueryStr(this); 
	}

	public String getCSV(String param_key, boolean id)
	{
		synchronized(parameters)
		{
		
			String ret_str ="";
			if (parameters ==  null) return ret_str;
		
			String[] vals;
		
			if ((vals = (String[]) 
				parameters.get(param_key)) == null) return ret_str;

			for (int i = 0; i < vals.length; i++)
				if (!id)
					if (i ==0) ret_str += "'" + vals[i] + "'";
					else ret_str += ",'" + vals[i] + "'";
				else 
					if (i ==0) ret_str +=  vals[i] ;
					else ret_str += "," + vals[i];
			return ret_str;
		}

	} 
	public String getValuesAsStr(String param_key, boolean id) 
	{
		synchronized(parameters)
		{
		
			return "(" + getCSV(param_key,id) + ")";
		}
	}
	
	public String[] getValuesArray(String param_key) 
	{
		synchronized(parameters)
		{
			String[] ret_str =null;
			if (parameters ==  null) return ret_str;
		
			String[] vals;
			if ((vals = (String[]) 
				parameters.get(param_key)) == null) return ret_str;
			else return vals;
		}
	}

	/* Assemble a string conists of a pair of string to be used fro SQL Query */
	public String getValuesAsStr(String param_key) 
	{
		synchronized(parameters)
		{
			boolean id = param_key.indexOf("ID")>=0 ? true : false;
		
			String ret_str ="";
			if (parameters ==  null) return ret_str;
		
			String[] vals;
		
			if ((vals = (String[]) 
				parameters.get(param_key)) == null) return ret_str;

			for (int i = 0; i < vals.length; i++)
			{
				if(vals[i].indexOf("''")<0) /* special symbol not treated yet */
				  vals[i]=StringEscapeUtils.escapeSql(vals[i]); /* escape special symbol such as ' etc. */
				if (!id)
					if (i ==0) ret_str += "'" + vals[i] + "'";
					else ret_str += ",'" + vals[i] + "'";
				else 
					if (i ==0) ret_str +=  vals[i] ;
					else ret_str += "," + vals[i];
			}
			return "(" + ret_str + ")";
		}
	}
	public int getValueIndex(String key, String value)
	{
		String[] param;
		if ((param = (String[])parameters.get(key)) == null)
			return -1;
		else
		{
			for (int i =0; i< param.length; i++)
				if (param[i].equals(value))
					return i;
				
			return -1;
		}
	}

	public int addValue(String key, String value)
	{

		synchronized(parameters)
		{
			if (value != null)
			{
				int i = 0;
				String[] param, new_param;
				
				if ((param = (String[])parameters.get(key)) == null)
					new_param = new String[1];
				else
				{
					new_param = new String[param.length+1];
					for (i =0; i< param.length; i++)
						new_param[i] = param[i];
				}	 
				new_param[i] = value;
				parameters.put(key, new_param);
			
			} 
			return 1;
		}
	}
			
	public int setValueAtIndex(String key, String value,int index)
	{
		synchronized(parameters)
		{
			String[] param;
			if ((param = (String[])parameters.get(key)) != null)
			{
				if (param.length <= index) return -1;	
				param[index] = value;
			}
			else
			{
				return -1;
			} 
		}
		return 1;
	}

	public int setValues(String key, String [] values)
	{
		reset = false;
		synchronized(parameters)
		{
			boolean removed = false;
			String[] param;
			if ((param = (String[])parameters.get(key)) != null)
			{	removed = true;
				parameters.remove(key);
			}
			if (
	   	 	(values != null) && (values.length != 0) && (!values[0].equals(""))  
	   		)
				parameters.put(key, values);
		
			if (removed) reset = true;
			if (parameters.size() == 0) flag = false;
			else flag = true;

		return 1;
		}
	}

	public int clear(String param)
	{
		if ((String[])parameters.get(param) != null)
			parameters.remove(param);
		if (!isSet()) clear();
		return 1;
	}
	public int clear()
	{
		//System.out.println("Clear  " + this);
		parameters.clear();
		if (dataWrapper != null) dataWrapper.closeQueries();
		reset = true;
		flag = false;
		return 1;
	}

	public String toString()
	{
		synchronized(parameters)
		{
			String str = "";
			for (Enumeration e = parameters.keys() ; e.hasMoreElements() ;) 
			{
         		Object key  = e.nextElement();
			String[] value = (String[])parameters.get(key);
			for (int i =0; i < value.length; i++)
				 str += key.toString() + " values " + value[i] + ":::";
			}
			return str;
		}  	
     	}

        public boolean isSet(String param)
        {
                return parameters.containsKey(param);
        }

	public boolean isSet()
	{
		return (parameters.size() != 0 ? true : false);
	}

	public Object getDValue(String  param)
        {
                return (parameters != null ? parameters.get(param) : null);
        }

	
	public boolean isSelected(String param, String key)
	{
		String[] a_param = (String[])parameters.get(param);
		if (a_param == null) return false;
		
		boolean found = false;
		for (int i = 0; i< a_param.length; i++)
			if (key.equals(a_param[i])) 
			{
				found = true;
				break;
			}
		return found;  
	}
	
	public String getDescription()
	{
		return "Description";

	}
	public String getDescription(String name, String p_name)
	{
		String[] val;
		if ( (val = (String[])parameters.get(p_name))!= null)
		{
			String str ="";
			if ((dataWrapper != null) && (dataWrapper.getListIndex(name) != -1))
			{
				for (int i = 0 ; i< val.length; i++)
					if (i == 0)
						str +=dataWrapper.getStrForKey(name,val[i]);
					else   
						str +="; " + dataWrapper.getStrForKey(name,val[i]);
			} else {
				for (int i = 0 ; i< val.length; i++)
					if (i == 0)
						str +=val[i];
					else   
						str +="; " + val[i];
			}
		//	System.out.println(str);
			str =str.replaceAll("''","'");
		//	System.out.println("==>"+str);
			return str; 	
		}
		else return "";
	}	
	
	public String getDescription(String name)
	{
		String[] val;
		if ( (val = (String[])parameters.get(name))!= null)
		{
			String str ="";
			if ((dataWrapper != null) && (dataWrapper.getListIndex(name) != -1))
			{
				for (int i = 0 ; i< val.length; i++)
					if (i == 0)
						str +=dataWrapper.getStrForKey(name,val[i]);
					else   
						str +="; " + dataWrapper.getStrForKey(name,val[i]);
			} else {
				for (int i = 0 ; i< val.length; i++)
					if (i == 0)
						str +=val[i];
					else   
						str +="; " + val[i];
			}
         //   System.out.println(str);
			str =str.replaceAll("''","'");
         //  System.out.println("+++++++ +++ ==>"+str);
			return str; 	
		}
		else return "";
	}	
	
	public Object clone()
	{
		try {
            		Criteria c = (Criteria)super.clone();
            		c.parameters = (Hashtable)parameters.clone();
		 	for (Enumeration e = parameters.keys(); e.hasMoreElements(); )
			{
				String  key = (String)e.nextElement();	
				String[] v  = (String[])parameters.get(key);	
				c.parameters.put(key,(String[])v.clone());
				String[] vals = (String[])c.parameters.get(key);
				for (int i =0; i<vals.length; i++ )
				{
					vals[i] = new String(v[i]);
				}
			}

				c.qryModel = (QueryModel)qryModel.clone();
				if (c.dataWrapper != null) c.dataWrapper = (Wrapper)dataWrapper.clone();
			return c;
        	} catch (CloneNotSupportedException e) {
            		throw new InternalError();
        	}
	}


}


abstract class QueryModel implements Cloneable
{


        public abstract String getQueryStr(Criteria criteria, String  filter);
        public abstract String getQueryStr(Criteria criteria);
	
	public Object clone() throws CloneNotSupportedException
	{
		return (QueryModel)super.clone();
	}
}


