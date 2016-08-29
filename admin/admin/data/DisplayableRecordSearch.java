package admin.data;


import java.util.*;
import admin.dbAccess.*;
import admin.config.*;

public class DisplayableRecordSearch extends Search {
        

	public String getQuery() {
		String searchString = searchID.getSearchStr();
		String tlbName = searchID.getTlbName();
                if (searchString.length()!= 0)
                        return Configurator.getDspSearchQuery(tlbName,"'"+searchString+"'");
                else return null;
        } 
	
}
	
