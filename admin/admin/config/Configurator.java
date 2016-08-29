package admin.config;

import java.util.*;
import admin.dbAccess.*;

public class Configurator {

	public static Vector tablesList = new Vector(); 
	
	private static String searchStr = "SEARCHSTRING";
	private static String uniqueKey = "ID";
	private static String label = "LABEL";
	private static String searchID = "SEARCHID";

	private static Hashtable recsQueries = new Hashtable();
	private static Hashtable arecQueries = new Hashtable();
	private static Hashtable sequences = new Hashtable();
	private static Hashtable ids = new Hashtable();
	private static Hashtable e_ids = new Hashtable();
	
	private static boolean first = true;


	public synchronized static void populateConfig() {
		
		if ( !first ) return;
		first = false;
		String[] tempS = 
		new String[]{"Author_List", "Data_Quality", "Chief_Scientist",
				"Expedition","Location",
				"Institution", "Material",
				"Person", "Reference","Sample", "Ship", "RegisteredUser"};
		
		for (int i = 0; i<tempS.length; i++) {
			String tmpS = tempS[i];
			tablesList.add(tmpS);
			String sQ, e_rQ;
			switch (i) {
				case 0: // Table: AUTHOR_LIST
				sQ ="SELECT p.Last_Name||', '||p.First_Name||' ('||a_l.person_num||') '"
					+ " ||' : '||a_l.ref_num as LABEL,"
					+ " a_l.ref_num||'-'||a_l.person_num as ID"
					+ " FROM Person p, Author_List a_l"
					+ " WHERE a_l.person_num = p.person_num"
					+ " AND UPPER(p.last_name) like SEARCHSTRING"
					+ " ORDER BY p.Last_Name, p.First_Name";
				recsQueries.put(tmpS,sQ);
				e_rQ ="SELECT a_l.* "
					+ " FROM Author_List a_l"
					+ " WHERE upper(a_l.ref_num||'-'||a_l.person_num) = SEARCHID";
				ids.put(tmpS, "REF_NUM||'-'||PERSON_NUM"); 
				e_ids.put(tmpS, "REF_NUM||'-'||PERSON_NUM"); 
				arecQueries.put(tmpS,e_rQ);
				break;

				case 1: // Table: DATA_QUALITY
						//FROM data_quality t LEFT OUTER JOIN method m
 						//ON  t.method_num = m.method_num 
				sQ ="SELECT m.method_code||' ( '||t.method_num||') Ref_Num: '||t.ref_num"
					+ " as LABEL, t.data_quality_num as ID"
					+ " FROM data_quality t, method m"
					+ " WHERE m.method_num = t.method_num"
					+ " AND upper(m.method_code) like SEARCHSTRING"
					+ " ORDER BY  m.method_code, t.method_num, t.ref_num";
				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_DATA_QUALITY");
				e_rQ ="SELECT d_q.* "
					+ " FROM Data_Quality d_q"
					+ " WHERE upper(d_q.data_quality_num) = SEARCHID";
				ids.put(tmpS,"DATA_QUALITY_NUM"); 
				e_ids.put(tmpS,"DATA_QUALITY_NUM"); 
				arecQueries.put(tmpS,e_rQ);
				break;
				
				case 2: // Table: CHIEF_SCIENTIST
				sQ ="SELECT p.last_name||', '||p.first_name||' Exp. '||e.expedition_name"
					+ "||' leg: '||e.leg as LABEL, t.expedition_num||'-'||t.person_num as ID"
					+ " FROM chief_scientist t, expedition e, person p"
					+ " WHERE p.person_num = t.person_num"
					+ " AND e.expedition_num = t.expedition_num"
					+ " AND UPPER(p.last_name) like SEARCHSTRING"
					+ " ORDER BY p.last_name, p.first_name,e.expedition_name";
				recsQueries.put(tmpS,sQ);
				e_rQ ="SELECT e.* "
					+ " FROM chief_scientist e"
					+ " WHERE upper(e.expedition_num||'-'||e.person_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				ids.put(tmpS,"EXPEDITION_NUM||'-'||PERSON_NUM");
				e_ids.put(tmpS,"EXPEDITION_NUM||'-'||PERSON_NUM");
				break;

				case 3: // Table: EXPEDITION
				sQ ="SELECT e.expedition_name||', '||e.expedition_code|| '('||e.expedition_num||')'"
					+ "||' Leg '||e.leg as LABEL,  e.expedition_num as ID"
					+ " FROM expedition e"
					+ " WHERE UPPER(e.expedition_name) like SEARCHSTRING or UPPER(e.expedition_code) like SEARCHSTRING"
					+ " ORDER BY e.expedition_name,e.expedition_code";
				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_EXPEDITIONS");
				e_rQ ="SELECT e.* "
					+ " FROM expedition e"
					+ " WHERE upper(e.expedition_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				ids.put(tmpS,"EXPEDITION_NUM");
				e_ids.put(tmpS,"EXPEDITION_NUM");
				break;


				case 4: // Table: LOCATIONS
				sQ = "SELECT substr(t.location_comment,0,35)||'...'||' ('||t.location_num||')' as LABEL,"
					+ "t.location_num as ID"
					+ " FROM  location t "
					+ " WHERE UPPER(t.location_comment) like SEARCHSTRING"
					+ " ORDER BY t.location_comment,t.location_num";
				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_LOCATIONS");
				e_rQ ="SELECT t.* "
					+ " FROM location t"
					+ " WHERE upper(t.location_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				ids.put(tmpS,"LOCATION_NUM");
				e_ids.put(tmpS,"LOCATION_NUM");
				break;
				

				case 5: // Table: INSTITUTIONS
			/*	sQ ="SELECT ' ('||i.institution_num||') '||"
					+ " substr(i.institution,0,35)||'...'||'-'||i.department as LABEL,"
					+ " i.institution_num as ID"
					+ " FROM institution i"
					+ " WHERE UPPER(i.institution) like SEARCHSTRING"
					+ " ORDER BY i.institution, i.department, i.institution_num";
				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_INSTITUTIONS");
				ids.put(tmpS,"INSTITUTION_NUM");
				e_ids.put(tmpS,"INSTITUTION_NUM");
				e_rQ ="SELECT i.* "
					+ " FROM institution i"
					+ " WHERE upper(i.institution_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;
				*/

				sQ ="SELECT ' ('||institution_num||') '||"
						+ " substr(institution,0,35)||'...'||'-'||department as LABEL,"
						+ " institution_num as ID"
						+ " FROM institution "
						+ " WHERE UPPER(institution) like SEARCHSTRING"
						+ " ORDER BY institution, department, institution_num";
					recsQueries.put(tmpS,sQ);
					sequences.put(tmpS,"SEQ_INSTITUTIONS");
					ids.put(tmpS,"INSTITUTION_NUM");
					e_ids.put(tmpS,"INSTITUTION_NUM");
					e_rQ ="SELECT INSTITUTION_NUM,INSTITUTION, DEPARTMENT, ADDRESS_PART1, ADDRESS_PART2, CITY, state, zip, country, INSTITUTION_TYPE"
						+ " FROM institution"
						+ " WHERE upper(institution_num) = SEARCHID";
					arecQueries.put(tmpS,e_rQ);
					break;
				
				case 6: // Table: MATERIAL
				sQ ="SELECT t.material_name as LABEL, t.material_name as ID"
					+ " FROM material t"
					+ " WHERE  upper(t.material_name) like SEARCHSTRING"
					+ " ORDER BY t.material_name";
				recsQueries.put(tmpS,sQ);
				ids.put(tmpS,"MATERIAL_NUM");
				e_ids.put(tmpS,"MATERIAL_NAME");
				e_rQ ="SELECT t .* "
					+ " FROM material t"
					+ " WHERE upper(t.material_name) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;
				
				/*
				case 7: // Table: METHOD
				sQ ="SELECT m.method_code||' ('||m.method_num||') '"
					+ "||' at: '||"
					+ " substr(i.institution,0,35)||'...'||' ('||m.method_loc||')' as LABEL,"
					+ " m.method_num as ID"
					+ " FROM method m LEFT OUTER JOIN institution i"
					+ " ON i.institution_num = m.method_loc"
					+ " WHERE UPPER(m.method_code) like SEARCHSTRING"
					+ " ORDER BY m.method_code, m.method_num";

				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_METHOD");
				ids.put(tmpS,"METHOD_NUM");
				e_ids.put(tmpS,"METHOD_NUM");
				e_rQ ="SELECT m.* "
					+ " FROM method m"
					+ " WHERE upper(m.method_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;
				

				case 8: // Table: METHOD_LIST
				sQ = "SELECT t.method_name||' ('||t.method_type||')' as LABEL, t.method_type as ID"
					+ " FROM method_list t"
					+ " WHERE upper(t.method_name) like SEARCHSTRING"
					+ " ORDER BY t.method_name, t.method_type";
				recsQueries.put(tmpS,sQ);
				e_ids.put(tmpS,"METHOD_TYPE");
				e_rQ ="SELECT m.* "
					+ " FROM method_list m"
					+ " WHERE upper(m.method_type) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;
				
				*/
				case 7: // Table: PERSONS
				sQ ="SELECT p.last_name||', '||p.first_name||' ( '||p.person_num||') '||"
					+ "' at: '||i.institution||' ('||p.institution_num||') ' as LABEL,"
					+ " p.person_num as ID"
					+ " FROM person p LEFT OUTER JOIN institution i"
					+ " ON i.institution_num = p.institution_num"
					+ " WHERE UPPER(p.last_name) like SEARCHSTRING"
					+ " ORDER BY  p.last_name,p.first_name,p.person_num";
				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_PERSONS");
				ids.put(tmpS,"PERSON_NUM");
				e_ids.put(tmpS,"PERSON_NUM");
				e_rQ ="SELECT p.* "
					+ " FROM person p"
					+ " WHERE upper(p.person_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;
			
	
				case 8: // Table: REFERENCE
				sQ = "SELECT r.ref_num||'--'||substr(r.title,0,35)||'...'||"
					+ "' of: '||p.Last_name||', '|| p.First_name||"
					+ "' ('||a_l.person_num||') ' as LABEL,r.ref_num as ID"
					+ " FROM reference r LEFT OUTER JOIN author_list a_l"
					+ " join person p on a_l.person_num = p.person_num"
					+ " on r.ref_num = a_l.ref_num"
					+ " WHERE UPPER(r.ref_num||r.title) like SEARCHSTRING"
					+ " ORDER BY r.ref_num, r.title, p.last_name";
				recsQueries.put(tmpS,sQ);
				e_rQ ="SELECT r.* "
					+ " FROM reference r"
					+ " WHERE upper(r.ref_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				//ids.put(tmpS,"REF_NUM");
				ids.put(tmpS,"DATA_ENTERED_DATE");
				e_ids.put(tmpS,"REF_NUM");
				break;

				case 9: //SAMPLE Table
				sQ = "select sample_id||'('||s.sample_num||'); '||"
					+ "'Station:'||st.station_id||'('||st.station_num||') '"
					+ " as LABEL, s.sample_num as ID"
					+ " from sample s, station st"
					+ " where st.station_num(+) = s.station_num"
					+ " and s.sample_id like SEARCHSTRING"
					+ " order by s.sample_id, st.station_id "; 
				recsQueries.put(tmpS,sQ);
				ids.put(tmpS,"SAMPLE_NUM");
				e_ids.put(tmpS,"SAMPLE_NUM");
				e_rQ ="SELECT s.sample_id Old_Sample_ID, 'Enter Sample ID' as New_Sample_ID " 
					+ " FROM sample s"
					+ " WHERE upper(s.sample_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;

				case 10: //SHIP Table
				sQ = "Select "
					+ "' ('||ship_num||') '||ship_name as LABEL,ship_num as ID"
					+ " from ship"
					+ " WHERE UPPER(ship_name) like SEARCHSTRING"
					+ " order by ship_name";
				recsQueries.put(tmpS,sQ);
				sequences.put(tmpS,"SEQ_SHIP");
				ids.put(tmpS,"SHIP_NUM");
				e_ids.put(tmpS,"SHIP_NUM");
				e_rQ ="SELECT s.* "
					+ " FROM ship s"
					+ " WHERE upper(s.ship_num) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;
				case 11: //User List
				sQ = "Select "
					+ " r.last_name||', '||r.first_name as \"Name\", r.email as \"Email\", "
					+ " r.organization as \"Organization\", r.registration_date as \"Registration Date\""
					+ " FROM pbudbo.RegisteredUser r"
					+ " WHERE UPPER(r.last_name) like SEARCHSTRING "
					+ " AND user_id IN ("
					+ " SELECT user_id FROM pbudbo.Subscription "
					+ " WHERE package_id IN ("
					+ " SELECT package_id FROM pbudbo.Package "
					+ " WHERE LOWER(package_name) = LOWER('PETDB')"
					+ " )) " 
					+ " ORDER BY r.last_name||', '||r.first_name ";
				recsQueries.put(tmpS,sQ);
				//sequences.put(tmpS,"SEQ_SHIP");
				ids.put(tmpS,"USER_ID");
				e_ids.put(tmpS,"USER_ID");
				e_rQ ="SELECT s.* "
					+ " FROM pbudbo.RegisteredUser s"
					+ " WHERE upper(s.user_id) = SEARCHID";
				arecQueries.put(tmpS,e_rQ);
				break;

				case 12: // Table: INSTITUTIONS_NEW
					sQ ="SELECT ' ('||i.institution_num||') '||"
						+ " substr(i.institution,0,35)||'...'||'-'||i.department as LABEL,"
						+ " i.institution_num as ID"
						+ " FROM institution i"
						+ " WHERE UPPER(i.institution) like SEARCHSTRING"
						+ " ORDER BY i.institution, i.department, i.institution_num";
					recsQueries.put(tmpS,sQ);
					sequences.put(tmpS,"SEQ_INSTITUTIONS");
					ids.put(tmpS,"INSTITUTION_NUM");
					e_ids.put(tmpS,"INSTITUTION_NUM");
					e_rQ ="SELECT i.* "
						+ " FROM institution i"
						+ " WHERE upper(i.institution_num) = SEARCHID";
					arecQueries.put(tmpS,e_rQ);
					break;
					

			} //end of switch/case

		}
	} 
		

		public static String getEditSearchQuery(String tName, String srch) { 
			return getDspSearchQuery(tName,srch);
		}

		public static String getTlbSequence(String tName) {
			return (String)sequences.get(tName);
		}
			
		public static String getDspSearchQuery(String tName, String srch) {
		synchronized (arecQueries) {
			String retString = (String) arecQueries.get(tName);
			if (retString != null) {
				String tS = retString.substring(0, retString.indexOf(searchID));
				tS = tS.concat(srch);
				return tS;
			} 
			return retString;
		 }
		}

		public static String getETablesKey(String tlbName) {
			return (String)e_ids.get(tlbName);
		}
		public static String getTablesKey(String tlbName) {
			return (String)ids.get(tlbName);
		}

		public static String getMltSearchQuery(String tName, String srch) {
		synchronized (recsQueries) {
			String retString = (String) recsQueries.get(tName);
            if (retString != null) {
			//	String tS = retString.substring(0, retString.indexOf(searchStr));
			//	String tS1 = retString.substring(
			// 			retString.indexOf(searchStr) + searchStr.length(),
			//			retString.length()
			//		);
			//	tS = tS.concat(srch);
			//	tS = tS.concat(tS1);
                String tS = retString.replaceAll(searchStr, srch);
				return tS;
			} 
			return retString;
		 }
		}


}
