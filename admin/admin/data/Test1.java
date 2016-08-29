package admin.data;

import java.util.*;

public class  Test1{

	public Test1() {
		admin.config.Configurator.populateConfig();
		displayConfig();
	}

			 
	public static void main (String[] args) {
		Test1 t = new Test1();
	}

	void displayConfig() {
               	
	admin.dbAccess.QueryResultSet multipleSrcRS, singleSrcRS;
	admin.data.FrameData e_frameData = new admin.data.EditableFrameData();
	String searchID = "";                    
	String label = "";                    
	
	e_frameData.setTlbName("Sample");

        try {
		System.out.println("setting multiSearchString");
		e_frameData.setMltSearchStr("SALMR79-034-B");
		System.out.println("setting resultSet");
		admin.dbAccess.QueryResultSet rs = e_frameData.getMltSearchRS();

		System.out.println("MLTI Query = " + e_frameData.getMltSearchQuery());
                rs = e_frameData.getMltSearchRS();
		if (rs == null) {
			System.out.println("MultiResultSet = null");
			return;
		}
		System.out.println("setting multiSearchString");

		searchID = "23213";
		((admin.data.EditableFrameData)e_frameData).setAction(admin.data.UpdateableRecordSearch.EDIT);
		System.out.println("Set single SearchID = " + searchID);
		e_frameData.setSglSearchStr(searchID);
		singleSrcRS = e_frameData.getSglSearchRS();
		admin.data.UpdateableRecordSearch urs =
				 ((admin.data.EditableFrameData)e_frameData).getUpdateableRec();
                
		System.out.println("Query = " + e_frameData.getSglSearchQuery());
		rs = e_frameData.getSglSearchRS();


                Vector cols = singleSrcRS.getColumnsLabels();
                admin.dbAccess.Record rec1 = singleSrcRS.getRecordAt(0);
		admin.data.Executable ex= ((admin.data.EditableFrameData)e_frameData).getExecutable();
                String action = ((admin.data.EditableFrameData)e_frameData).getSglSearchAction();
         	/*
		for (int i = 0; i< cols.size(); i++) {
                	String column = (String)cols.elementAt(i);
			System.out.println(column);
                	if (action.equals(admin.data.UpdateableRecordSearch.NEW)) 
			{
                        	if ( column.equals(admin.config.Configurator.getTablesKey(e_frameData.getTlbName())) ) 
				{        
                        		System.out.println("record's ID here");
                        	} else {                
                        		System.out.println("value here = ");
                      			ex.setValue(i,"val");
				}
                 	} else {                       
                        	if ( column.equals(admin.config.Configurator.getTablesKey(e_frameData.getTlbName())) ) 
				{        
                        		System.out.println("value here = " + rec1.valueAt(i));
                        	} else {                 
                        		System.out.println("value here = " + rec1.valueAt(i));
                        	}
                 	}                              

        
        	} //end of FOR
		*/
		ex.setValue(0, "23213");
		ex.setValue(1, "23213");
		System.out.println("Run Action");
		ex.runAction();

	} catch (Exception e) {
			System.out.println("Exception.getMessage() = " + e.getMessage());
	}

	} 
				
}
