package petdb.vocabulary; 

public class MineralVoc extends Vocabulary
{ 
    public String[] getColumnHeads(){
        return new String[]{"MINERAL_CODE","MINERAL_NAME"};
    }
    
    public  String getQuery(){
      return "select MINERAL_CODE, MINERAL_NAME from MINERAL_LIST ORDER BY MINERAL_CODE";
    }
} 
