package admin.data;

import admin.config.*;

public class EditableFrameData extends FrameData{
	
	
	public EditableFrameData() {
		multiRecSearch = new MultiRecordSearch();
		singleRecSearch = new UpdateableRecordSearch();
	}
	
	public void setAction(String a) {
		((UpdateableRecordSearch)singleRecSearch).action = a;
		((UpdateableRecordSearch)singleRecSearch).actionPerformed = false;
 	}

	public UpdateableRecordSearch getUpdateableRec() {
	
		return (UpdateableRecordSearch)singleRecSearch;
	}

	public Executable getExecutable() {
		return (UpdateableRecordSearch)singleRecSearch;
	}

	public String getSglSearchAction() {
                return ((UpdateableRecordSearch)singleRecSearch).action;
        }

	public String toString() {
		return ((UpdateableRecordSearch)singleRecSearch).toString();
 	}
       

}

