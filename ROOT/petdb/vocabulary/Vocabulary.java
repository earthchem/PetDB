package petdb.vocabulary; 

import java.util.List;
import petdb.query.SimpleQuery;

public abstract class Vocabulary 
{ 
    public static String MeasuredParameter = "Measured Parameter";
    public static String Method = "Method";
    public static String Mineral = "Mineral";
    public static String TectonicSetting = "Tectonic Setting";
    public static String Unit = "Unit";
    
    private List list;
    
    public Vocabulary () {
        setList();       
    }
 
    public abstract String[] getColumnHeads();
    public abstract String getQuery();
    private void setList() {
        SimpleQuery sq = new SimpleQuery(getQuery());
        list = sq.getList(getColumnHeads().length);
    }
    public List getList() {return list;}  
} 
