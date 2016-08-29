package petdb.vocabulary; 

public class UnitVoc extends Vocabulary
{ 
    public String[] getColumnHeads(){
        return new String[]{"UNIT_CODE"};
    }
    
    public  String getQuery(){
      return "select distinct unit from chemistry where unit is not null order by unit";
    }
} 
