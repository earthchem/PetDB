package petdb.vocabulary; 

public class MeasuredParameterVoc extends Vocabulary
{ 
    public String[] getColumnHeads(){
        return new String[]{"ITEM_CODE ITEM","DESCRIPTION"};
    }
    
    public  String getQuery(){
      return "select item_code, item_description from ITEM_MEASURED order by ITEM_CODE";
    }
} 
