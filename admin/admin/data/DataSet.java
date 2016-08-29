package admin.data;

import java.util.*; 

/* Interface declares common functions used by all data storage related classes.
 * 
 * All Known Subinterfaces:
 *    FinalSampleDS
 *
 *  All Known Implementing Classes:
 *    AnalysisInfo1DS, AnalysisInfo2DS, AValuePerKeyDS, CriteriaDS, DataFSDS, DataIIRecordDS, DataRecordDS,
 *    DataSummaryDS, EPubRecordDS, ExpeditionDS, ExpeditionInfoDS, ExpRecordDS, ExpRRecordDS, InclusionFSDS, 
 *    IndexedDS, ListDS, MethodDS, MethodInfo1DS, MethodInfo2DS, MethodInfo3DS, MethodInfo4DS, MethodInfo5DS, MethodInfo6DS,
 *    MineralFSDS, OrderedChemicals, PubRecordDS, RecordDS, ReferenceInfo1DS, ReferenceInfo2DS, ResultSetFSDS,
 *    RMDataRecordDS, RockModeAnalysisDS, RockModeDS, RockModeFSDS, RockModeMineralsDS, RockRecordDS, RowBasedDS,
 *    SampleIDDS, SampleInfo1DS, SampleInfo2DS, SampleInfoAnalysisDS, SampleInfoIncAnalysisDS, SampleRecordDS, 
 *    SamplesDS, StationDS, StationInfo1DS, StationInfo2DS, StationInfo3DS, TblInRefInfo1DS, TblInRefInfo2DS, 
 *    UniformKeyedValueDS, UniformValueDS, VectorFSDS, VectorFSDSAndQuery  
 *
*/
public interface DataSet {
	
    public Vector getKeys();

    public Object getValue(String key);

	public Vector getValues();
	
	public String getKeyAt(String index);
	
	public String getStrValue(String key);

}
