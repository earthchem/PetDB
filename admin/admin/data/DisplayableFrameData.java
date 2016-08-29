package admin.data;

import admin.config.*;

public class DisplayableFrameData extends FrameData{
	
	
	public DisplayableFrameData() {
		multiRecSearch = new MultiRecordSearch();
		singleRecSearch = new DisplayableRecordSearch();
	}
	
}
