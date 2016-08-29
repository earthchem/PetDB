package petdb.config;

/**
 */

public class DisplayConfigurator  
{

	public static char Location_Abrv = 'X';
	public static String toBeReplaced = "TO_BE_REPLACED";
	
	public static String DateEnteredFormat = "YYYY-MM-DD";
//	public static String DataEnteredValue = "Y";

    public static String Expedition_Year_Mask = " X.exp_year_from ||"
                        + " decode(nvl(X.exp_year_to, 0),0,' ',"
			            + " decode(X.exp_year_to,X.exp_year_from,' ','-'||X.exp_year_to)) year";
        
	public static String Age_Mask = ""
			+ "decode(nvl(X.age_max, 0),0,"
				+ "decode(nvl(X.age_min,0),0,'',X.age_min||' My'),"
				+ "decode(X.age_max,X.age_min,"
				+ "decode(nvl(X.age_min,0),0,'',X.age_min||' My'),"
				+ "decode(nvl(X.age_min,0),0,X.age_max|| '"
				+ " My',X.age_min||'-'||X.age_max||' My'))) age";
	

	public static String Elevation_Mask = " X.elevation_min ||"
                        + " decode(nvl(X.elevation_max, 0),0,' ',"
			+ " decode(X.elevation_max,X.elevation_min,' ',' to '||X.elevation_max)) elevation";

	public static String Longitude_Mask = "decode(nvl(X.longitude,0),0,'',"
			+ " abs(X.longitude)||'\260'||decode(X.longitude,abs(X.longitude),'E','W'))";
	public static String Latitude_Mask = " decode(nvl(X.latitude,0),0,'',"
			+ " abs(X.latitude)||'\260'||decode(X.latitude,abs(X.latitude),'N','S'))";


	public static String[] Type_Order = {"MAJ", "IR","NGAS","REE","US","VO"
                                ,"TE","IS","AGE","EM","MODE","MD","RT","SPEC"}; //FIXME:Need to get from database

    public static String[] Label_Order = {"Major_Oxides", "Isotopic_Ratio","Noble_Gas","REE",
				                              "U_Series","Volatile","TE","IS","AGE","EM","Rock_Mode",
				                              "Model Data","Ratio","SPEC"};


	public static String DataExistence_Mask = ""
		+ " decode("
			+ "sum(decode(X.material_num,3,1,5,10,6,100,0)),"
			+ "1,'Rock',10, 'Inclusion',100,'Mineral',"
			+ "111,'Rock, Mineral,Inclusion',101,'Rock, Mineral',"
			+ "110,'Mineral, Inclusion',11,'Rock, Inclusion',''"
		+ ") data_existence";

	public static String DataType_Mask = ""
		+ "decode(X.material_num,7,3,8,3,X.material_num) material_num"; 
			

	public static String DateEnteredExcluded = ",2003-12-03,2003-11-25,";



	public static boolean  isVersionActive(String version)
	{
		return (DateEnteredExcluded.indexOf(","+version+",")<0 ? true : false); 
	}


	public static String toReplace(String result, char with_str)
	{
		StringBuffer sb = new StringBuffer(result);
		int start = 0;
		while ( (start = sb.indexOf(toBeReplaced)) >-1)
		{ 
			//System.out.println("start = " + start);
			sb.delete(start , start + toBeReplaced.length());
			sb.insert(start,with_str);
		}
		return sb.toString();
	}


	public static void main (String [] arg)
	{

		//System.out.println(toReplace("ASDFLKADFJLK" + toBeReplaced+ "gggg", 'b'));
		//System.out.println(toReplace(toBeReplaced + "ASDFLKADFJLK" + toBeReplaced+ "gggg", 'b'));
	}		
}
