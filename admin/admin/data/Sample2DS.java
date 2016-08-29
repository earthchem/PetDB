package admin.data;

import admin.dbAccess.ReferenceDS;
import admin.dbAccess.SimpleQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableSheet;



/* The Sample2DS class stores the search results from Sample2DCtlQuery
 * It extends RowBasedDS class
 * 
 * It contains multiple rows information with 16 columns: SAMPLE_ID,	SAMPLE NAME,	STATION_NAME,	SAMPLE_COMMENT,	ROCK_TYPE,	ROCK CLASS,	ROCK_CLASS_DETAIL,	ROCK TEXTURE,	ALTERATION,	
 * ALTERATION_TYPE, GEOLGICAL AGE,	PREFIX,	AGE MIN,	AGE MAX,	ARCHIVING INSTITUTION,	INSTITUTION_NUM.
 * 
 */
public class Sample2DS extends ReferenceDS
{
    public static int SAMPLE_ID=1;
    public static int SAMPLE_NAME=2;
    public static int STATION_NAME=3;
    public static int SAMPLE_COMMENT=4;
    public static int ROCK_TYPE=5;
    public static int ROCK_CLASS=6;
    public static int ROCK_CLASS_DETAIL=7;   
    public static int ROCK_TEXTURE=8;
    public static int ALTERATION=9;
    public static int ALTERATION_TYPE=10;
    public static int GEOLGICAL_AGE=11;
    public static int PREFIX=12;
    public static int AGE_MIN=13;
    public static int AGE_MAX=14;
    public static int ARCHIVING_INSTITUTION=15;
    public static int INSTITUTION_NUM=16;
    private static int COL_NUM =14;
 
	public Sample2DS(String v_filter)
	{
        super(getSample2DS(v_filter),COL_NUM);  
	}
	
	
    public void writeColumnToSheet2(WritableSheet sheet, int columnOff, int rowOff)
	{
        int row = 0;
      //  List list = getList(COL_NUM);
    
        Iterator it =  dsList.iterator();
        int last = rowOff+row;
		try {   
			
			while(it.hasNext())			 
			{
                String [] dt = (String[]) it.next();
                int j = 0;
				sheet.addCell(new Label(columnOff+0,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+1,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+2,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+3,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+4,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+5,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+6,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+7,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+8,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+9,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+10,last,dt[j++])); 
				sheet.addCell(new Label(columnOff+11,last,dt[j++])); 
                String min = dt[12];
				if(min != null) sheet.addCell(new Label(columnOff+12,last,min));
				String max = dt[13];
				if(max != null ) sheet.addCell(new Label(columnOff+13,last,max));	
                last++;
            }
			sheet.addCell(new Number(columnOff+0,last,-1)); 
        }  
         catch (Exception e){
            System.out.println("Exception in Sample2DS: " +e.getMessage());
        } 
	}

}