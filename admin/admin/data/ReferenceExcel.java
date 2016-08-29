package admin.data;

import java.io.*;
import java.util.*;
import jxl.*;
import jxl.write.*;


public class ReferenceExcel 
{
	/* Get actual class name to be printed on */
//	static Logger log = Logger.getLogger(ReferenceExcel.class.getName());
	
	protected OutputStream file = null;
	protected PrintWriter pr_file = null;
	protected boolean csv = false; //By default it uses Microsoft Excel format.
	HashMap methodMap;
	static HashMap itemCodeTypeMap;
	
	WritableWorkbook workbook=null;
	ReferenceExlSheet ref_sheet=null;
	TableTitlesExlSheet table_title_sheet=null;
	CruisesExlSheet cruises_sheet=null;
	StationExlSheet station_sheet=null;
	SampleExlSheet sample_sheet=null;
	RockModeExlSheet rock_mode_sheet=null;
	MethodExlSheet method_sheet=null;
	RockExlSheet rock_sheet=null;
	MineralExlSheet mineral_sheet=null;
	InclusionExlSheet inclusion_sheet=null;
	
	public ReferenceExcel(String ref_num, OutputStream outStr,  boolean flag ) throws IOException
	{
		file = outStr;
		pr_file = new PrintWriter(file);
		csv = flag;
		itemCodeTypeMap = getItemCodeType();
		/* create the book */
		if(flag == false)	
		    workbook = Workbook.createWorkbook(file);
		try {
		  /*Create first sheet */
		  ref_sheet = new ReferenceExlSheet( workbook.createSheet("REFERENCE", 0), ref_num);
        }
        catch (Exception e)
        {
            System.err.println("Error in ReferenceExcel: "+e.getMessage());
            throw new IOException(e.getMessage());
        }
		
	}
    
 
	public void addTableInRefSheet(TableInRefTable2DS info) throws Exception
	{
		table_title_sheet = new TableTitlesExlSheet(workbook.createSheet("TABLE_TITLES", 1), info);
	}
	
	public void addCruiseSheet(Cruises2DS info) throws Exception
	{
		cruises_sheet = new CruisesExlSheet(workbook.createSheet("CRUISES", 2), info);
	}
	
	public void addStationSheet(Station2DS info) throws Exception
	{
		station_sheet = new StationExlSheet(workbook.createSheet("STATIONS", 3), info);
	}

	public void addSampleSheet(Sample2DS info) throws Exception
	{
		sample_sheet = new SampleExlSheet(workbook.createSheet("SAMPLES", 4), info);
	}
	
	public void addRockModeSheet(RockMode2DS info) throws Exception
	{
		rock_mode_sheet = new RockModeExlSheet(workbook.createSheet("ROCK MODE", 5), info);
	}
	
	public void addMethodSheet(Method2DS info) throws Exception
	{
		method_sheet = new MethodExlSheet(workbook.createSheet("METHODS", 6), info);
		methodMap = method_sheet.getMethodMap();
	}
	
	public void addRockSheet(Rock2DS info) throws Exception
	{
		rock_sheet = new RockExlSheet(workbook.createSheet("ROCKS", 7), info, methodMap);
	}
	
	public void addMineralSheet(Mineral2DS info) throws Exception
	{
		mineral_sheet = new MineralExlSheet(workbook.createSheet("MINERALS", 8), info, methodMap);
	}
      
	public void addInclusionSheet(Inclusion2DS info) throws Exception
	{
		inclusion_sheet = new InclusionExlSheet(workbook.createSheet("INCLUSIONS", 9), info, methodMap);
	}
	/* Create Wookbook for writing out microsoft excel file*/
	
	
	/* Close Workbook object. All content in OutputStream or PrintStream are written out */
	public void writeAndCloseBook() throws Exception
	{
		if (!csv)
        {
            workbook.write();
			workbook.close();
        }
		else 
		{
			pr_file.flush();
			pr_file.close();
		}
	}
	
	private static HashMap getItemCodeType()
	{
		String query ="select itl.itemtypelist_num, im.item_code||decode(itl2.item_measured_num, null,'','['||it.item_type_code||']') codetype "+
				" from item_type it, item_measured im, itemtype_list itl left join (select item_measured_num from itemtype_list itl group by item_measured_num having count(item_measured_num) > 1) itl2 "+
				" on itl.item_measured_num = itl2.item_measured_num "+
				" where it.item_type_num=itl.item_type_num and itl.item_measured_num=im.item_measured_num";
        return ExcelUtil.getMap(query);
	}
}
