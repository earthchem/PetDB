/*
 * VectorFSDSAndQuery.java
 *
 * Created on December 19, 2005, 5:07 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package petdb.data;

import java.util.*; 
import java.io.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;
/**
 *
 * @author afishman
 */
public class VectorFSDSAndQuery extends VectorFSDS
{    
    public VectorFSDSAndQuery(ResultSet rs, int d_c)
    {
       super(rs,  d_c);
       removeOrFromDS();
    }
 
    private void removeOrFromDS()
    {
        Vector v;
        int data_size = data.size();
            
        for(int i=0; i < data.size(); i++)
        {
            v = (Vector)data.elementAt(i);
            for (int j=0; j < dynamic_count; j++)
            { 
                if (v.elementAt(j).equals(""))
                {
                    data.remove(i);
                    link.remove(i);
                    i--;
                    total_count--;
                    break; 
                }
            }
        }
    }
}
