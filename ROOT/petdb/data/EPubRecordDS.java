package petdb.data;

import java.util.*;
import java.sql.*;
import jxl.*;
import jxl.write.*;

public class EPubRecordDS  extends PubRecordDS
{

        public EPubRecordDS(ResultSet rs)
        {
                super(rs);
        }

        public EPubRecordDS(ResultSet rs, String qry)
        {
                super(rs, qry);
        }




	protected Record newRecord(ResultSet rs, int count)
	{
		return new EPubRecord(rs, count);
		
	}

	public int getColumnCount() { return 9;}
        
        public boolean getExlRow(int offset, Vector v, int d_c, boolean flag) throws Exception
        {
		
                boolean r = (browising < data.size());
		if (r) 
		{
			PubRecord pr= (PubRecord)data.elementAt(browising);
			if (!flag)
			{
			v.setElementAt(new Label(0,offset,pr.getValue(PubRecord.AUTH)),0);		
			v.setElementAt(new Label(1,offset,pr.getValue(PubRecord.YEAR)),1);		
			v.setElementAt(new Label(2,offset,pr.getValue(PubRecord.TITLE)),2);
			v.setElementAt(new Label(3,offset,pr.getValue(PubRecord.Journal)),3);
			v.setElementAt(new Label(4,offset,pr.getValue(PubRecord.Volume)),4);
			v.setElementAt(new Label(5,offset,pr.getValue(PubRecord.Book_Title)),5);
			v.setElementAt(new Label(6,offset,pr.getValue(EPubRecord.PAGE)),6);		
			v.setElementAt(new Label(7,offset,pr.getValue(PubRecord.Editors)),7);
			v.setElementAt(new Label(8,offset,pr.getValue(PubRecord.Publishers)),8);
			}
			else 
			{
			v.setElementAt(pr.getValue(PubRecord.AUTH),0);		
			v.setElementAt(pr.getValue(PubRecord.YEAR),1);		
			v.setElementAt(pr.getValue(PubRecord.TITLE),2);
			v.setElementAt(pr.getValue(PubRecord.Journal),3);
			v.setElementAt(pr.getValue(PubRecord.Volume),4);
			v.setElementAt(pr.getValue(PubRecord.Book_Title),5);
			v.setElementAt(pr.getValue(EPubRecord.PAGE),6);		
			v.setElementAt(pr.getValue(PubRecord.Editors),7);
			v.setElementAt(pr.getValue(PubRecord.Publishers),8);
			}
			browising++;
		}
                return  r;

        }

        public int getTitleRow(Vector v)
        {
                v.add("Author");
                v.add("Year");
                v.add("Title");
                v.add("Journal");
                v.add("Volume");
                v.add("Book_Title");
                v.add("Pages");
                v.add("Editors");
                v.add("Publishers");

                return 1;
        }



}

