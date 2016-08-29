package petdb.data;

import java.sql.*;

/* Will store IGSN search result from IGSN_INFO table as ResultSet object. It extends RowBasedDS. 
 * User will Get all information from the stored ResultSet. */
public class IGSNInfo1DS extends RowBasedDS{
	/* Indices for the ResultSet. Indices for IGSN_INFO table */
	public static int IGSN     =1;
    public static int SAMPLE_NUM      =2;
    public static int STATION_NUM     =3;
    
    /* Store pass-in ResultSet */
	public IGSNInfo1DS(ResultSet rs)
	{
		super(rs);
	}

}