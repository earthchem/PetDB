package petdb.data;

import java.util.*; 
import java.sql.*;
import petdb.config.*;

/* Extends from UniformValueDS who Contains two Vectors, 'index' Vector and 'data' Vector. 
 * The 'index' Vector contains all the keys to the 'data' Vector and two vectors are filled data from ResultSet.
 * This class contains another Hashtable which will map 
 */
public class OrderedChemicals extends UniformValueDS 
{

	Hashtable hsh = new Hashtable(); 


	public OrderedChemicals(ResultSet rs)
	{
		super(rs);
	}

    public void buildOrderedIndex()
    {
        int j = 0;
		hsh.clear();
        for (int i= 0; i<DisplayConfigurator.Type_Order.length; i++)
        {  // Type_Order are ITEM_TYPEs: "MAJ", "IR","NGAS","REE","US","VO","TE","IS","AGE","EM","SPEC","MODE","MD","RT"
            String c_type = DisplayConfigurator.Type_Order[i];
            if ( getKeys().indexOf(c_type) >= 0 )
            {
                     Vector v = (Vector)getValue(c_type);
                     for (int k=0; k < v.size();k++)
                     {
                          hsh.put(c_type+v.elementAt(k),new Integer(j++));
                         // System.out.println("-- "+c_type+v.elementAt(k)+"==>"+j);
                     }
            }

        }
    }

    /* Material num will be: "3" => "Rock Data"
	                         "6" => "Groundmass"
	                         "5" => "Mineral Data"
	                         "4" => "Inclusion Data"
	                         "2" => "Rock Mode data"
	*/
	public void buildOrderedIndex(String material_num)
	{
		hsh.clear();
	//	System.out.println("HashTable Cleared");
		int j = 0;
		for (int i= 0; i<DisplayConfigurator.Type_Order.length; i++)
		{ // Type_Order are ITEM_TYPEs: "MAJ", "IR","NGAS","REE","US","VO","TE","IS","AGE","EM","SPEC","MODE","MD","RT"
			String c_type = DisplayConfigurator.Type_Order[i];			
			  if ( getKeys().indexOf(material_num+c_type) >= 0 )
			   {
			  	    Vector v = (Vector)getValue(material_num+c_type);
				    for (int k=0; k < v.size();k++)
				    {
					  hsh.put(c_type+v.elementAt(k),new Integer(j++));
				      //System.out.println(" material_num+c_type ->"+(material_num+c_type)+"==>"+(c_type+v.elementAt(k))+"==>"+j);
				    }//end of for
			    }// end of getKeys().indexOf(material_num+c_type) >= 0
		}
	}

	public int getOrderedChemicals(Vector columns)
	{
    
       		Vector keys = getKeys();		

        	for(int i = 0; i< DisplayConfigurator.Type_Order.length; i++)
        	{
			
                	if (keys.indexOf(DisplayConfigurator.Type_Order[i]) >= 0)
               		{
                        	Vector v = (Vector)getValue(DisplayConfigurator.Type_Order[i]);
                        	for (int j=0; j< v.size(); j++)
                                	columns.add(v.elementAt(j));
                        }
                }
		return 1;
	}

    /* 'columns' vector will be filled with chemical names such as Si02, Ti02 and Al203, Fe203 . Type string is ITEM_TYPE_CODE such as MAJ, IR, IS etc. */
	public int getOrderedChemicals(String type, Vector columns)
	{
       		Vector keys = getKeys();		

        	for(int i = 0; i< DisplayConfigurator.Type_Order.length; i++)
        	{
			
                	if (keys.indexOf(type+DisplayConfigurator.Type_Order[i]) >= 0)
               		{
                	//	if( DisplayConfigurator.Type_Order[i].indexOf("MODE") >= 0 ) continue; //Do not include those rock mode data
                        	Vector v = (Vector)getValue(type+DisplayConfigurator.Type_Order[i]);
                        	for (int j=0; j< v.size(); j++)
                                	columns.add(v.elementAt(j));
                    }
            }
		    return 1;
	}
	
	 /* 'columns' vector will be filled with index of items such as CPX, OL and OPX, etc. ITEM_TYPE_CODE "MODE" */
	public int getRockModeItemIndex(Vector columns)
	{
       		Vector keys = getKeys();
            String type="3"; //Rock data
            int idx=0;
        	for(int i = 0; i< DisplayConfigurator.Type_Order.length; i++)
        	{
			
                	if (keys.indexOf(type+DisplayConfigurator.Type_Order[i]) >= 0)
               		{   Vector v = (Vector)getValue(type+DisplayConfigurator.Type_Order[i]);
                		if( DisplayConfigurator.Type_Order[i].indexOf("MODE") >= 0 ) {
                        	for (int j=0; j< v.size(); j++)
                                	columns.add(new Integer(idx++));
                		}
                		else
                	        for (int j=0; j< v.size(); j++)
                		        idx++;
                    }
            }
		    return 1;
	}

	public int getIndexOf(String type, String elem)
	{
		Integer i_j = (Integer)hsh.get(type+elem);
		if (i_j != null) return i_j.intValue();
		else return -1;
	}

}
