package petdb.data;

import java.util.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;


public interface FinalSampleDS extends DataSet  
{
    public String getSampleNumbers();
    
  	public int getRem();
	
	public int getTotalCount();
	
	public int getCurrentRow() throws Exception; 
	
	public int getCurrentRecNum() throws Exception;
	
	public void setCount(int count);
	
	public boolean next() throws Exception;
	
	public boolean previous() throws Exception;

	public boolean goPreviousPage(int rows_num) throws Exception;

	public boolean goNextPage();

	public boolean goFirstPage() throws Exception;

	public boolean goLastPage(int rows_num) throws Exception;

	public String getExlValue(int index) throws Exception;
	
	public String getValue(int index) throws Exception;
	
	public double getDoubleValue(int index) throws Exception;
	
	public String getAnalysisStr();
	
	public boolean isFieldEmpty(int index) throws Exception;
	
	public jxl.write.WritableCell getNumCell(int c, int r, String value);
	
}
