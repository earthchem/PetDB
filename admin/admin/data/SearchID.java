package admin.data;


public class SearchID {
	
	protected String tlbName = "";
	protected String searchString = "";
	protected boolean changed = false; // if add or edit was performed on the table,
				   	   // the query is run again even though the searchString is not changed!
	
	public SearchID() {
		super();
	}	


	public  void setTlbName(String nm) {
		if (!tlbName.equals(nm)) {
			tlbName = nm;
			searchString = "";
			changed = true;
		}
	}


	public String getTlbName() {
		return tlbName;
	}

	/*
	public synchronized void setSearchStr(String s_str, boolean f) {
		if (!searchString.equals(s_str)) {
			searchString = s_str;
			changed = true;
		} 
	}
	*/

	public synchronized void setSearchStr(String s_str) {
		if ((s_str != null) && (s_str.length() != 0))
			if (!tlbName.equals("Sample"))  s_str = s_str.toUpperCase();
		if (!searchString.equals(s_str)) {
			searchString = s_str;
			changed = true;
		} 
	}

	protected void changed(boolean f) {
		changed = f;
	}

	public boolean isChanged() {
		return changed;
	}

	public String getSearchStr() {
		return searchString;
	}

}

