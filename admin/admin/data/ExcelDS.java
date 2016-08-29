package admin.data;

import java.util.*; 
import java.io.*; 
import jxl.*; 
import jxl.write.*; 

/* Interface will be implemented for those classes which need to write out data to spreadsheet */
public interface ExcelDS {
	
	public int writeAllToSheet(WritableSheet sh, int offset, Vector values, int dynamic_columns) throws Exception;
	
	public int writeAllToSheet(PrintWriter pr_file, int offset, Vector values, int dynamic_columns) throws Exception;

    public boolean getExlRow(int offset, Vector v, int d_c, boolean flag) throws Exception ;

    public int getTitleRow(Vector v);

	public int getColumnCount();
}
