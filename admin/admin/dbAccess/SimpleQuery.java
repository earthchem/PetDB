package admin.dbAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleQuery extends Query
{

	
	public SimpleQuery(String qry) {
		this.qry = qry;
		executeQuery();
	}

	
	public synchronized void prepareData() 
	{
		
	}

	public ResultSet getResultSet()
	{
		return rs;
	}
	
	public String toString(){
		return qry;
	}

    public String getSingleResult() {
        
        try {
            if(rs.next()) return rs.getString(1); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List getList(int size) {
        List list = new ArrayList();
        int j=0;
        try {
           while(rs.next()) {
            String row[] = new String[size];
            for(int i = 0;  i < size;) row[i] = rs.getString(++i);
            list.add(row);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
}
