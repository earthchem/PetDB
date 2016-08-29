package petdb.vocabulary; 

public class MethodVoc extends Vocabulary
{ 
    public String[] getColumnHeads(){
        return new String[]{"METHOD_CODE","METHOD_NAME"};
    }
    
    public  String getQuery(){
      return "select METHOD_CODE, METHOD_NAME from METHOD ORDER BY METHOD_CODE";
    }
} 
