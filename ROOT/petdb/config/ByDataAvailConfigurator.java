package petdb.config;

import  petdb.data.*;
import  petdb.criteria.*;


public class ByDataAvailConfigurator {


	public static String[] Materials = {"Major Oxides","REE","Trace","Isotopic Ratio",
					 "Volatile","Noble Gas","U-Series"}; 
	public static String[] Materials_Code = {"MAJ","REE","TE","IR","VO","NGAS","US",};

	public static String[] Inclusion = {"Plagioclase","Olivine","Clinopyroxene","Spinel"};
	public static String[] Inclusion_Code = {"PLAG","OL","CPX","SP"};
	public static String[] Minerals = {"Plagioclase","Olivine","Clinopyroxene","Spinel",
					"Orthopyroxene","Amphibole"};
	public static String[] Minerals_Code = {"PLAG","OL","CPX","SP","OPX","AMPH"};


	public static String getNumIDIn(DataSet ds, String code)
	{
		return ((AValuePerKeyDS)ds).getStrKey(code);
	
	}

	public static String getDescription(String type, String code)
	{
		if (type.equals(ByDataAvailCriteria.Material_Analysis))
			return getMaterialDesc(code);
		else if (type.equals(ByDataAvailCriteria.Inclusion_Analysis))
			return getInclusionDesc(code);
		else
			return getMineralDesc(code);
	}
	
	public static String getMineralDesc(String code)
	{
		int index = -1;
		for (int i=0; i< Minerals_Code.length; i++)
			if ( Minerals_Code[i].equals(code))
			{
				index = i;
				break;
			}
		
		if (index != -1) return  Minerals[index];   
		else return code;
	}

	public static String getInclusionDesc(String code)
	{
		int index = -1;
		for (int i=0; i< Inclusion_Code.length; i++)
			if ( Inclusion_Code[i].equals(code))
			{
				index = i;
				break;
			}
		
		if (index != -1) return  Inclusion[index];   
		else return code;
	}
		
	public static String getMaterialDesc(String code)
	{
		int index = -1;
		for (int i=0; i< Materials_Code.length; i++)
			if ( Materials_Code[i].equals(code))
			{
				index = i;
				break;
			}
		
		if (index != -1) return  Materials[index];   
		else return code;
	}
		
}
