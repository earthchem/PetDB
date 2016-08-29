package admin.data;


import jxl.write.*;

/* The SampleExlSheet class represents 'Samples' sheet of data entry Excel file. 
 * 
 * The pass-in data will fill into this sheet.
 * 
 */
public class SampleExlSheet
{
	WritableSheet sheet=null;
	
	/* Constructor. It will fill the pass-in WritableSheet. 
	 *   
	 * Input Parameter: info contains all information needed to build 'Samples' sheet.
	 * Output: sht will be filled.
	*/
	public SampleExlSheet(WritableSheet sht, Sample2DS info) throws Exception
	{
        sheet=sht;
        
        /* Fill all data the sheet */
        fillSheet(info);
    //    info.close();        
	}

	/* Add all cell information for TableTitles sheet */
	public void fillSheet(Sample2DS info) throws Exception
	{ 
   //     info.writeColumnToSheet(sheet, 0, 2);
        info.writeColumnToSheet2(sheet, 0, 2);
		sheet.addCell(new Label(0,0,"SAMPLE_ID"));
	    sheet.addCell(new Label(1,0,"SAMPLE NAME"));
	    sheet.addCell(new Label(2,0,"STATION_NAME"));
	    sheet.addCell(new Label(3,0,"SAMPLE_COMMENT"));
	    sheet.addCell(new Label(4,0,"ROCK_TYPE"));
	    sheet.addCell(new Label(5,0,"ROCK CLASS"));
	    sheet.addCell(new Label(6,0,"ROCK_CLASS_DETAIL"));
	    sheet.addCell(new Label(7,0,"ROCK_TEXTURE"));
	    sheet.addCell(new Label(8,0,"ALTERATION"));
	    sheet.addCell(new Label(9,0,"ALTERATION_TYPE"));
	    sheet.addCell(new Label(10,0,"GEOLGICAL_AGE"));
	    sheet.addCell(new Label(11,0,"PREFIX"));
	    sheet.addCell(new Label(12,0,"AGE_MIN"));
	    sheet.addCell(new Label(13,0,"AGE_MAX"));
	    sheet.addCell(new Label(14,0,"ARCHIVING_INSTITUTION"));
	    sheet.addCell(new Label(15,0,"INSTITUTION_NUM"));
    
	    sheet.addCell(new Label(0,1,"PLEASE, LEAVE EMPTY FOR USE BY DBA ONLY"));
	    sheet.addCell(new Label(2,1,"NEEDS TO BE IDENTICAL TO STATION_NAME IN WORKSHEET 'STATIONS'"));
	    sheet.addCell(new Label(3,1,"E.G. PILLOW FRAGMENT"));
	    sheet.addCell(new Label(4,1,"V=VOLCANIC,   S=SUBVOLCANIC, I=INTRUSIVE, P=MANTLE, M=METAMORPHIC"));
	    sheet.addCell(new Label(5,1,"BASALT ETC."));
	    sheet.addCell(new Label(6,1,"ANDESITIC, TRANSITIONAL ETC."));
	    sheet.addCell(new Label(8,1,"F=FRESH, S=SLIGHTLY ALTERED, M=MODERATELY ALTERED, E=EXTENSIVELY ALTERED, A=ALMOST COMPLETELY ALTERED"));
	    sheet.addCell(new Label(9,1,"E.G. 'SERPENTINIZATION'"));
	    sheet.addCell(new Label(10,1,"E.G. 'MIOCENE'"));
	    sheet.addCell(new Label(11,1,"LOWER    MIDDLE    UPPER"));
	    sheet.addCell(new Label(12,1,"MILLION YEARS"));
	    sheet.addCell(new Label(13,1,"MILLION YEARS"));
	    sheet.addCell(new Label(15,1,"PLEASE, LEAVE EMPTY FOR USE BY DBA ONLY"));
	   
	   	}
}