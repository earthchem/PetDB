package admin.data;

import admin.config.*;
import admin.dbAccess.*;

public abstract class FrameData {
	
	protected Search multiRecSearch, singleRecSearch;
	
	public void setTlbName(String s) {
		multiRecSearch.setTlbName(s);
		singleRecSearch.setTlbName(s);
	}

	public String getTlbName() {
		return multiRecSearch.getTlbName();
	}		 	
	
	public void setMltSearchChanged(boolean f){
		multiRecSearch.searchChanged(f);
	}

	public void setMltSearchStr(String str) throws Exception {
		synchronized(multiRecSearch) {
			multiRecSearch.setSearchStr(str);
			singleRecSearch.setSearchStr("");
		}
	}

	public String getMltSearchStr() {
		return multiRecSearch.getSearchStr(); 
	}


	public String getMltSearchQuery() {
		return multiRecSearch.getQuery();
		
	}

	public QueryResultSet getMltSearchRS() {
		return multiRecSearch.getResultSet(); 
	}

//------------ methods to access single Record Search
	
	public String getSelectedRec() {
		return singleRecSearch.getSearchStr();
	}

	public void setSglSearchStr(String str) throws Exception {
		synchronized(singleRecSearch) {
			singleRecSearch.setSearchStr(str);
	
		}
	}
	public String getSglSearchQuery() {
		return singleRecSearch.getQuery();
	}

	public String getSglSearchStr() {
		return singleRecSearch.getSearchStr(); 
	}

	public QueryResultSet getSglSearchRS() {
		return singleRecSearch.getResultSet(); 

	}
	
	public String toString() {
	String tmpString = " <P> FrameData for Table: " + multiRecSearch.getTlbName() + "\n";  
	tmpString = "MultiSearch " +
			multiRecSearch.getResultSet() + " <P>" +
			multiRecSearch.getQuery() + " <P>" +
			multiRecSearch.toString() + " <P>";
	tmpString += "SingleSearch <P>" +
			singleRecSearch.getResultSet() + " <P>" +
			singleRecSearch.getQuery() + " <P>" +
			singleRecSearch.toString() + " <P>";
				
	return tmpString;
	}

	 	
}
