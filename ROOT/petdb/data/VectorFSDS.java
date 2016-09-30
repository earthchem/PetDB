package petdb.data;

import java.util.*; 
import java.io.*; 
import java.sql.*;
import jxl.*;
import jxl.write.*;


public class VectorFSDS implements FinalSampleDS, ExcelDS 
{
	Vector data = null;
	Vector link = null;
	private int field_counter = 18;
	
	int dynamic_count = 0;
	int r_count = -1;
	int remainder = 0;
	int total_count = 0;
	WritableCellFormat emptyFormat = new WritableCellFormat (NumberFormats.DEFAULT); 
	int A_K = DataFSDS.Analysis_Key;
	String analysis = ",";
	String sampleNums = "";

	public VectorFSDS(ResultSet rs, int d_c)
	{
		data = new Vector();
		link = new Vector();
		dynamic_count = d_c;
	//	RecordDS.printColumnNamesInResultSet(rs);
		if (rs != null)
			buildDS(rs, d_c);
	}
    
    public String getSampleNumbers() {return sampleNums;}

 	public boolean isFieldEmpty(int index) throws Exception
        {
                String val = "";
 	        Vector rec = (Vector)data.elementAt(r_count);
 	        val = (String)rec.elementAt(index-1);
                if ((val == null) || (val.length()==0))
                        return true;
                else
                        return false;

        }

	public String dispLink()
	{
		String t = "LINK";
		Vector v =(Vector)link.elementAt(r_count);
		for (int g = 0; g <v.size(); g++)
			t += g + "th = " + (String)v.elementAt(g) + "; ";
		return t;   

	}
        public String getAnalysisStr()
        {
                if (analysis.length()>1)
                        return  "(" + analysis.substring(1,analysis.length())  +")";
                else return "";
        }

	public String getAnalysis(int i)
	{
		Vector v =(Vector)link.elementAt(r_count);
		return (String)v.elementAt(i);

	}

	private int buildDS(ResultSet rs, int d_c)
	{
		String key = "";
		String key_1 = "";
		int offset = d_c;
        String prev ="";
		try {
		while (rs.next())
		{
               
			String next_analysis = "";
                        if (rs.getString(dynamic_count+A_K)!= null)
                        {
                        	next_analysis = rs.getString(dynamic_count + A_K) + ",";
                            	if (analysis.indexOf("," + next_analysis) < 0) 
                                	analysis += next_analysis;
                        }
			
			boolean first = false; /* flag to know if it is first time appear in the resultset */
			
			if (
				(!key.equals(rs.getString(offset+DataFSDS.Sample_ID)))
				||
				(!key_1.equals(rs.getString(offset+DataFSDS.Material)))
			    )
			{
				data.add(new Vector(field_counter+offset));
				link.add(new Vector(offset));
				r_count++;
				Vector r   = (Vector)data.elementAt(r_count);
				Vector r_l = (Vector)link.elementAt(r_count);
				for (int i=0; i<(field_counter+offset); i++)
					r.add("");
				for (int i=0; i<offset; i++)
					r_l.add("");
				first = true;
				
				key = rs.getString(offset+DataFSDS.Sample_ID);
				key_1 = rs.getString(offset+DataFSDS.Material);
				
				String s = "";
				if (link.size()>1) {
					r_l = (Vector)link.elementAt(r_count-1);
				for (int k=0; k< r_l.size();k++)
					s += "; " + (String)r_l.elementAt(k);
				}
				//System.out.println(" Sample = " + rs.getString(offset+DataFSDS.Sample_ID) + " Mat: " + 
				//		rs.getString(offset+DataFSDS.Material) + " R_count = " + r_count + " links = " + s);
			}
			Vector rec 	= (Vector)data.elementAt(r_count);
			Vector rec_link = (Vector)link.elementAt(r_count);
			if (first)
			{
			rec.setElementAt(rs.getString(offset+DataFSDS.Sample_ID),offset+DataFSDS.Sample_ID-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Material),offset+DataFSDS.Material-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Material_Desc),offset+DataFSDS.Material_Desc-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Latitude),offset+DataFSDS.Latitude-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Longitute),offset+DataFSDS.Longitute-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Elevation),offset+DataFSDS.Elevation-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Tectonic),offset+DataFSDS.Tectonic-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Rock),offset+DataFSDS.Rock-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Reference),offset+DataFSDS.Reference-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Reference_Num),offset+DataFSDS.Reference_Num-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Expedition),offset+DataFSDS.Expedition-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Expedition_Num),offset+DataFSDS.Expedition_Num-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Method),offset+DataFSDS.Method-1);
            String sample_Num = rs.getString(offset+DataFSDS.Sample_Num);
            
                        if(!sample_Num.equals(prev)) sampleNums +=","+sample_Num;
                        prev = sample_Num;
            
            rec.setElementAt(sample_Num,offset+DataFSDS.Sample_Num-1);
		//	rec.setElementAt(rs.getString(offset+DataFSDS.Sample_Num),offset+DataFSDS.Sample_Num-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Latitude_N),offset+DataFSDS.Latitude_N-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Longitute_N),offset+DataFSDS.Longitute_N-1);
			rec.setElementAt(rs.getString(offset+DataFSDS.Sample_IGSN),offset+DataFSDS.Sample_IGSN-1);
			
			first = false;
			}

			if ( 
				((String)rec.elementAt(offset+DataFSDS.Reference_Num-1)).indexOf(
					rs.getString(offset + DataFSDS.Reference_Num))
			 	< 0 )
			{
				rec.setElementAt(
					rec.elementAt(offset+DataFSDS.Reference-1)+
					 "; " + rs.getString(offset + DataFSDS.Reference)
					,offset+DataFSDS.Reference-1
					);
				rec.setElementAt(
					rec.elementAt(offset+DataFSDS.Reference_Num-1)+
					 "; " + rs.getString(offset + DataFSDS.Reference_Num)
					,offset+DataFSDS.Reference_Num-1
					);

			}

            // Artem: AND query on sample chemistry
            // must check if all chem columns are filled in 
                           
                            
            for (int i=0; i< offset; i++)
            {
                if (	(rs.getString(i+1) != null)
                        && (rs.getString(i+1).length() != 0)
                        && (!rs.getString(i+1).equals(" "))
                    )
                {
                    rec.setElementAt(rs.getString(i+1),i);
                    rec_link.setElementAt(rs.getString(offset + DataFSDS.Analysis_Key),i);
                    if ( ((String)rec.elementAt(offset+DataFSDS.Method-1)).indexOf(rs.getString(offset + DataFSDS.Method)) < 0 )
                        rec.setElementAt( rec.elementAt(offset+DataFSDS.Method-1)+"; " + rs.getString(offset + DataFSDS.Method),offset+DataFSDS.Method-1);

                }
            }


		}  // end of while
        sampleNums = sampleNums.substring(1);
		analysis = analysis.substring(0, analysis.length()-1);
		total_count = r_count + 1;
		r_count = -1;
		} catch (Exception e) 
		{
			System.out.println("error reading the data: " + e.getMessage());
		}
		return 1;
	}

	public int getRem() {return remainder;}
	
	public int  getTotalCount() { return total_count;}
	
	public int getCurrentRow() throws Exception { return r_count;}
	
	public int getCurrentRecNum() throws Exception { return r_count;}
	
	private int setTotalCount()
	{
		total_count = data.size();
		return 1;
	}

	public void setCount(int count)
	{ ; }
	
	
        public Vector getKeys() { return null;}

        public Object getValue(String key) {return null;}

	public Vector getValues()  {return null;}
	
	public String getKeyAt(String index)  {return null;}
	
	public String getStrValue(String key)  {return null;}

	public boolean next() throws Exception
	{
		if (r_count +1 < data.size())
		{
			++r_count;
			return true;
		} else return false; 
	}

	public boolean previous() throws Exception
	{
		if (r_count -1 >= 0)
		{
			--r_count;
			return true;
		} else return false; 
	}
	
	public boolean goPreviousPage(int rows_num) throws Exception
	{
		if (r_count >= (total_count-1))
		{
			int rem = 0;

			if  (Math.floor(total_count/rows_num) ==  Math.ceil(total_count/rows_num))
				rem =rows_num * ((int) Math.floor(total_count/rows_num) -2);
			else 	
				rem =rows_num * ((int) Math.floor(total_count/rows_num) -1);
			if (rem <= 0 ) r_count = -1;
			else r_count = rem-1; 
			remainder = rem;
		}
		else if (r_count < 2*rows_num)
			r_count = -1; 
		else 
		{ 
			int r_count = this.r_count - 2*rows_num;
			if (r_count <= 0) r_count = -1;
			this.r_count = r_count; 
		}
		return true;
	}

	public boolean goNextPage()
	{
		return true;
	}

	public boolean goFirstPage() throws Exception
	{
		r_count = -1;
		return true;
	}

	public boolean goLastPage(int rows_num) throws Exception
	{
		if (total_count <= rows_num) {
			r_count = -1; //r_set.beforeFirst();
		}else {
			int rem =rows_num * (int) Math.floor(total_count/rows_num);
			if (rem == total_count)
				rem = total_count - rows_num; 
			r_count = rem-1; //r_set.absolute(rem);
			remainder = rem;
		}
		return true;
	}

	public String getExlValue(int index) throws Exception
	{
		String val = "";
		Vector rec = (Vector)data.elementAt(r_count);
		val = (String)rec.elementAt(index-1);
		return (val == null ? " " : val);
	}

	public double getDoubleValue(int index) throws Exception
	{
		String val = "";
		Vector rec = (Vector)data.elementAt(r_count);
		val = (String)rec.elementAt(index-1);
		return (val == null ? 0 : Double.parseDouble(val));
	}
	
	public String getValue(int index) throws Exception
	{
		String val = "";
		Vector rec = (Vector)data.elementAt(r_count);
		val = (String)rec.elementAt(index-1);
		return (val == null ? "&nbsp;" : val);
	}
	
	private String dispRow()
	{
		String t="";
		Vector v = (Vector)data.elementAt(r_count);
		for (int i = 0; i<v.size();i++)
			t += " " + i + "th = '" + (String)v.elementAt(i) + "' ;";
		t += "LINK = ";
	 	v = (Vector)link.elementAt(r_count);
                for (int i = 0; i<v.size();i++)
                        t += " " + i + "th = '" + (String)v.elementAt(i) + "' ;";
 	
		return t;
	}
	
	public int writeAllToSheet(PrintWriter file,int offset, Vector values, int d_c) throws Exception
	{	
		int row_counter = 0;
		int preserve  = r_count;
		r_count = -1;
		while (getExlRow(offset++,values,d_c,true))
		{
			String csv_str = "";
			row_counter++;
			for (int i=0; i< values.size(); i++)
                        	csv_str +=values.elementAt(i) + ",";
			file.println(csv_str);
		}
		r_count = preserve;
		return row_counter;
	}
	public int writeAllToSheet(WritableSheet sheet,int offset, Vector values, int d_c) throws Exception
	{	
		int row_counter = 0;
		int preserve  = r_count;
		r_count = -1;
		while (getExlRow(offset++,values,d_c, false))
		{
			 row_counter++;
			for (int i=0; i< values.size(); i++)
                        	if (values.elementAt(i) != null)
					sheet.addCell((jxl.write.WritableCell)values.elementAt(i));
		}
		r_count = preserve;
		return row_counter;
	}

	public jxl.write.WritableCell getNumCell(int c, int r, String value)
	{		
			try {
				double v = Double.parseDouble(value);
				return  new jxl.write.Number(c,r,v);
 			} catch (Exception e)
			{

				return  null; //new jxl.write.Label(c,r,"", emptyFormat);
			}
	}

	public boolean getExlRow(int offset, Vector v, int dynamic_counter, boolean flag) throws Exception
        {
                int i = -1;
                boolean r =next();
                String val = "";
                if (r)
                {
			if (!flag)
			{
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Sample_ID)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Sample_IGSN)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Material)),i);

                        for (int j=1 ;j<= dynamic_counter; j++)
                                v.setElementAt(getNumCell(++i,offset,getExlValue(j)),i);


                        val = getExlValue(dynamic_counter+DataFSDS.Latitude_N);
                        v.setElementAt(getNumCell(++i,offset,val),i);

                        val = getExlValue(dynamic_counter+DataFSDS.Longitute_N);
                        v.setElementAt(getNumCell(++i,offset,val),i);

                        val = getExlValue(dynamic_counter+DataFSDS.Elevation);
                        v.setElementAt(getNumCell(++i,offset,val),i);

                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Tectonic)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Rock)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Reference)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Method)),i);
                        v.setElementAt(new Label(++i,offset,getExlValue(dynamic_counter+DataFSDS.Expedition)),i);
			} else {
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Sample_ID),++i);
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Sample_IGSN),++i);
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Material),++i);

                        for (int j=1 ;j<= dynamic_counter; j++)
                                v.setElementAt(getExlValue(j),++i);


                        val = getExlValue(dynamic_counter+DataFSDS.Latitude_N);
                        v.setElementAt(val,++i);

                        val = getExlValue(dynamic_counter+DataFSDS.Longitute_N);
                        v.setElementAt(val,++i);

                        val = getExlValue(dynamic_counter+DataFSDS.Elevation);
                        v.setElementAt(val,++i);

                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Tectonic),++i);
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Rock),++i);
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Reference),++i);
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Method),++i);
                        v.setElementAt(getExlValue(dynamic_counter+DataFSDS.Expedition),++i);
			}
                }
                return r;
        }

        public int getTitleRow(Vector v)
        {
                v.insertElementAt("Sample_ID",0);
                v.insertElementAt("IGSN",1);
                v.insertElementAt("Material",2);
                v.add("Latitude");
                v.add("Longitude");
                v.add("Elevation");
                v.add("Tectonic");
                v.add("Rock");
                v.add("Reference");
                v.add("Method");
                v.add("Expedition");

                return 1;
        }

 
	
	public int getColumnCount(){return 0;}


}
