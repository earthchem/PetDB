package petdb.query;

import java.sql.SQLException;

public class StatisticsQuery extends Query
{
	
	public StatisticsQuery() {
		super();     
	}

   public StatisticsQuery(String qry) {
        super();
	    this.qry = qry;      
	}

	public void setQuery(String q)
	{
		this.qry = q;
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
    
}
