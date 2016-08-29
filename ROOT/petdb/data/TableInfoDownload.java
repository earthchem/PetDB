package petdb.data;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import petdb.wrapper.*;
import petdb.config.*;
import petdb.query.SimpleQuery;

public class TableInfoDownload extends HttpServlet 
{


	public boolean readAndWrite(HttpServletRequest request, HttpServletResponse  response) 
	throws Exception  
	{
       		String table_id = request.getParameter("singlenum");
       		String table_num = request.getParameter("table_num");
       		String table_title = request.getParameter("table_title");
       		String rows= request.getParameter("rows");
       		String items= request.getParameter("items");
        	if (table_id == null)
        	{
                        throw new Exception("You are trying to go to Table in Reference page. "
                        + " with NO Table in Reference specified");
        	}
            Wrapper wrapper = new TblInRefInfoWrapper(table_id, true);
        	OrderedChemicals  ds1 = (OrderedChemicals)wrapper.getControlList("0");
        	TblInRefInfo2DS  ds2 = (TblInRefInfo2DS)wrapper.getControlList("1");
		if  	((ds1 == null) || (ds2==null)) throw new Exception(" Your parameters are not right!");

        	int test =0;

		HttpSession session = request.getSession();
        	
		ServletOutputStream writeAt = response.getOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook((OutputStream)writeAt);
 		WritableSheet sheet =((WritableWorkbook)workbook).createSheet("data",0);
        	

		int xls_x_start = 0; //starting row in the file
        	int xls_y_start = 4; //starting column for chemicals

        	Label label = null;
        	int columns_size = 0;
        	label = new Label(0,xls_x_start,"Sample");
        	sheet.addCell(label);
        	label = new Label(1,xls_x_start,"Sample_ID");
        	sheet.addCell(label);
        	label = new Label(2,xls_x_start,"IGSN");
        	sheet.addCell(label);
            label = new Label(3,xls_x_start,"Method");
        	sheet.addCell(label);
        	label = new Label(4,xls_x_start,"Material");
        	sheet.addCell(label);

        	Vector keys = ds1.getKeys();
        	for(int i = 0; i< DisplayConfigurator.Type_Order.length; i++)
        	{
                if (keys.indexOf(DisplayConfigurator.Type_Order[i]) >= 0)
                {
                        Vector v = (Vector)ds1.getValue(DisplayConfigurator.Type_Order[i]);
                        for (int j=0; j< v.size(); j++)
                        {
                                columns_size++;
                                label = new Label(columns_size + 4,xls_x_start,(String)v.elementAt(j));
                                sheet.addCell(label);
                        }
                }
        	}
        	ds1.buildOrderedIndex();
        	double[] columns_value = new double[columns_size];
        	String[] columns_valueStr=new String[columns_size];
        	for(int i=0;i<columns_size;i++) columns_valueStr[i]="";
        	int row_index = 0;
        	int f_index = 0;
        	String previous_id ="";
            String previous_method ="";
            String method ="";
        	String material = "";
       	 	String alias = "";
        	String s_id = "";
        	String igsn = "";
        	if (ds2 != null)
        	while (ds2.next())
        	{

                if (!previous_id.equals(ds2.getValue(TblInRefInfo2DS.Batch_Num))||
                    !previous_method.equals(ds2.getValue(TblInRefInfo2DS.Method)))
                {
                        if (previous_id.length() != 0)
                        {
                                row_index++;
                                label = new Label(0,xls_x_start + row_index,alias);
                                sheet.addCell(label);
                                label = new Label(1,xls_x_start + row_index,s_id);
                                sheet.addCell(label);
                                label = new Label(2,xls_x_start + row_index,igsn);
                                sheet.addCell(label);
                                label = new Label(3,xls_x_start + row_index,method);
                                sheet.addCell(label);
                                label = new Label(4,xls_x_start + row_index,material);
                                sheet.addCell(label);

                                for (int g =0; g < columns_value.length; g++)
                                {
                                        if (columns_value[g] == -1)
                                        {

                                                        ; //write xls
                                        }
                                        else
                                        {
                                            if(columns_valueStr[g]!=null && columns_valueStr[g].endsWith(", "))
                							    columns_valueStr[g] = columns_valueStr[g].substring(0,columns_valueStr[g].length()-2);
                							
                                            sheet.addCell(label = new Label(5+g,xls_x_start + row_index,columns_valueStr[g]));
                                            columns_valueStr[g]="";
                                        }

                                }
                        } //end of i>1

                        alias = ds2.getValue(TblInRefInfo2DS.Alias);
                        s_id = ds2.getValue(TblInRefInfo2DS.Sample_ID);
                        igsn = ds2.getValue(TblInRefInfo2DS.IGSN);
                        method = ds2.getValue(TblInRefInfo2DS.Method);
                        material =
                                (ds2.isFieldEmpty(TblInRefInfo2DS.Inclusion))
                                ?       (ds2.isFieldEmpty(TblInRefInfo2DS.Mineral))
                                        ? ds2.getValue(TblInRefInfo2DS.Material)
                                        : ds2.getValue(TblInRefInfo2DS.Mineral)
                                : ds2.getValue(TblInRefInfo2DS.Inclusion);

                        for (int g = 0; g< columns_value.length; g++) columns_value[g] = -1;
                        previous_id = ds2.getValue(TblInRefInfo2DS.Batch_Num);
                        previous_method = ds2.getValue(TblInRefInfo2DS.Method);
                        if("&nbsp;".equalsIgnoreCase(igsn)) igsn="N/A";
                }
                String item_code =  ds2.getValue(TblInRefInfo2DS.Item_Code);
                String type_code =  ds2.getValue(TblInRefInfo2DS.Type_Code);
                f_index = ds1.getIndexOf(type_code,item_code);
                if ((f_index > -1 ) && (f_index < columns_size))
                {
                        columns_value[f_index] = ds2.getDoubleValue(TblInRefInfo2DS.Value);
                        if(material.equalsIgnoreCase("Groundmass"))
                        	columns_valueStr[f_index] += columns_value[f_index]+", ";
                        else
                        	columns_valueStr[f_index] = new Double( columns_value[f_index]).toString();
                        	
                }

        	}

        	row_index++;
                label = new Label(0,xls_x_start + row_index,alias);
                sheet.addCell(label);
                label = new Label(1,xls_x_start + row_index,s_id);
                sheet.addCell(label);
            	label = new Label(2,xls_x_start + row_index,igsn);
            	sheet.addCell(label);
                label = new Label(3,xls_x_start + row_index,method);
                sheet.addCell(label);
                label = new Label(4,xls_x_start + row_index,material);
                sheet.addCell(label);

                        for (int g =0; g < columns_value.length; g++)
                        {
                                if (columns_value[g] == -1)
                                {
                                                ; //write xls
                                }
                                else
                                {
                                    //    sheet.addCell(new jxl.write.Number(4+g,xls_x_start + row_index,columns_value[g]));
                                        if(columns_valueStr[g]!=null && columns_valueStr[g].endsWith(", "))
            							    columns_valueStr[g] = columns_valueStr[g].substring(0,columns_valueStr[g].length()-2);
            							
                                        sheet.addCell(label = new Label(5+g,xls_x_start + row_index,columns_valueStr[g]));
                                        columns_valueStr[g]="";
                                }

                        }

        	
		workbook.write();
		workbook.close();
		
		int r = wrapper.closeQueries();
		return true;

	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
	{
		HttpSession session = request.getSession();

		ServletConfig config = this.getServletConfig();
		if (config == null)
		{
			return;
		}

		ServletContext application = config.getServletContext();
		if (application == null)
		{
			return;
		}
	
	//	String fileName = "data.xls";
    
        String fileName= new SimpleQuery("select p.LAST_NAME||'_'||r.JOURNAL||'_'||r.PUB_YEAR||'_'||ti.TABLE_IN_REF||'.xls' "+
                "from table_in_ref ti, reference r, AUTHOR_LIST al, person p "+
                "where ti.REF_NUM = r.REF_NUM and r.REF_NUM = al.REF_NUM and al.AUTHOR_ORDER = 1 and p.PERSON_NUM = al.PERSON_NUM "+
                "and ti.TABLE_IN_REF_NUM = "+request.getParameter("singlenum")).getSingleResult();
   	
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "file; filename=\"" + fileName+"\"");
	
		try
		{
			if (readAndWrite(request,response))
				return;
			else 
				throw new java.io.IOException("Your Session has Expired");
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			throw new java.io.IOException("Error writing to the Excel Page");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse  response)
	throws java.io.IOException, ServletException 
	{
		doGet(request, response);
	}

}
