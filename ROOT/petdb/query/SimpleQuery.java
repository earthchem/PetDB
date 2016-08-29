package petdb.query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleQuery extends Query
{

 
	public SimpleQuery() {}
    
	public SimpleQuery(String qry) {
		this.qry = qry;
		executeQuery();
        if(qry != null && qry.startsWith("insert")) {
            close();
        }
            
	}
    
    public SimpleQuery(boolean isUpdate, String qry) {
		this.qry = qry;
		executeQuery();
        if(isUpdate) {
             close();
        }
	}
    
	
	public synchronized void prepareData() {}

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
        finally{
             close();
        }
        return null;
    }
    
    public List getList(int size) {
        List list = new ArrayList();
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
