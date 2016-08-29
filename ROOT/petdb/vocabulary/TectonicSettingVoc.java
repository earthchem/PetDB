package petdb.vocabulary; 

public class TectonicSettingVoc extends Vocabulary
{ 
    public String[] getColumnHeads(){
        return new String[]{"TECTONIC_SETTING_NAME"};
    }
    
    public  String getQuery(){
      return "select tectonic_setting_name from tectonic_setting_list order by tectonic_setting_name";
    }
} 
