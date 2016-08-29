package petdb.data;

import java.io.*;
import java.util.*;
import petdb.criteria.ByDataCriteria;
import petdb.criteria.ByInclusionCriteria;
import petdb.criteria.ByMineralCriteria;

import petdb.criteria.Criteria;
import petdb.query.DataDCtlQuery;


public class TagAlongData 
{
    private Criteria criteria;
    private Set availableItemType;
    private final String itemTypes = "'MAJ','TE','IR'";
	
	public TagAlongData(Criteria criteria, Set itemSet, String sampleFilter) 
	{
        this.criteria = criteria;
        String itemType = getItemType(itemSet);
        DataDCtlQuery dcq = new DataDCtlQuery(sampleFilter, getCriteriaType(), itemType);
        setParameterValues(dcq, itemSet);
	}
    
    public TagAlongData(Criteria criteria, String sampleFilter) 
	{
        this.criteria = criteria;
        DataDCtlQuery dcq = new DataDCtlQuery(sampleFilter, getCriteriaType(), itemTypes);
        setAvailableItemType(dcq);
	}
    
    private void setAvailableItemType(DataDCtlQuery dcq)
    {
        DataSet ds = dcq.getDataSet();
        String [] arr = itemTypes.split(",");
        availableItemType = new HashSet();
        
       for(int i = 0; i < arr.length;i++) {
    	   arr[i] = arr[i].replaceAll("'","");
            Vector v = (Vector)ds.getValue(arr[i]);  
            if(v == null) continue; 
            else availableItemType.add(arr[i]);
        }
    }  
    
    
    private String getItemType(Set itemSet)
    {
        String itemType = null;         
        Iterator i = itemSet.iterator();
        while(i.hasNext()) {
             String item =(String)i.next();
             criteria.clear(item);
             if(itemType == null) itemType = "'"+item+"'";
             else itemType +=","+"'"+item+"'";
        }  
        return itemType;
       
    }
	
    public boolean getAvailableItemType(String itemType) { 
       return availableItemType.contains(itemType);       
    }
    
    
    
    
    
    private void setParameterValues(DataDCtlQuery dcq, Set itemSet)
    {
        DataSet ds = dcq.getDataSet();
        Iterator j = itemSet.iterator(); 
      
        while(j.hasNext()) {
            String itemType =(String)j.next();     
            Vector v = (Vector)ds.getValue(itemType);  
            if(v == null) continue; 
            criteria.clear(itemType);
            int size = v.size();              
            String arr[] = new String[v.size()];
            for(int i = 0; i < size; i++) {
                DataRecord dr = (DataRecord)v.elementAt(i);
                arr[i] = (String)dr.getColumn(DataRecord.Name);
            }
            criteria.setValues(itemType,arr);
        }
    }  
    
    private String getCriteriaType () {
        if(criteria instanceof ByMineralCriteria) return DataDCtlQuery.Mineral;
        if(criteria instanceof ByInclusionCriteria) return DataDCtlQuery.Inclusion;
        else return DataDCtlQuery.Rock;
    }    
     
    
    public Criteria getCriteria() {return criteria;}
  }
