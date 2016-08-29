DECLARE   
stn_num number(6) :=0;
stn_num_counter number(6) :=0;
prev_stn_num number(6) :=-1;
stn_name_list BF_STATION.stationname%type :='';
current_stn_name BF_STATION.stationname%type :='';
prev_stn_name BF_STATION.stationname%type :='null';

TYPE staion_id_name_rec is RECORD
 ( station_id BF_station.stationid%type,
   station_name BF_station.stationname%type
 );
 
st_names staion_id_name_rec;

CURSOR st_id_name_cur IS  
 SELECT stationid, stationname 
 INTO st_names
 FROM BF_Station 
 order by stationid;

BEGIN
  IF NOT st_id_name_cur%ISOPEN THEN
     OPEN st_id_name_cur;
  END IF;
  
  LOOP 
    FETCH st_id_name_cur INTO st_names;
    EXIT WHEN st_id_name_cur%NOTFOUND;
    /* dbms_output.put_line(st_names.station_id||' '||st_names.station_name);  */
    IF st_names.station_id != st_names.station_name THEN 
     /* dbms_output.put_line('Not Same ====>'||st_names.station_id||' '||st_names.station_name); */      
      Select count(station_num) into stn_num_counter from STATION where STATION_ID (+) = st_names.station_id;
      IF stn_num_counter != 0 THEN
          Select station_num into stn_num from STATION where STATION_ID = st_names.station_id; 
          current_stn_name :=st_names.station_name;
          IF prev_stn_num != stn_num THEN
            /* dbms_output.put_line('--------------------------------'); */
              IF prev_stn_name != current_stn_name THEN
              stn_name_list := current_stn_name; 
              END IF;
          /*  dbms_output.put_line('Update station num is ====>'||stn_num||' '||st_names.station_name); */
            Update station_comment set STATION_NAME = stn_name_list where station_num=stn_num;
            stn_name_list :='';
          ELSE
             IF prev_stn_name != current_stn_name THEN
                 stn_name_list := stn_name_list || ':'||current_stn_name;
             END IF;
          END IF;
          prev_stn_num := stn_num;
          prev_stn_name :=current_stn_name;
      ELSE
          dbms_output.put_line('No station_num found for station id ='||st_names.station_id||' in Station table');
      END IF;
    END IF;
     
  END LOOP;
         EXCEPTION WHEN NO_DATA_FOUND THEN 
         dbms_output.put_line ('A SELECT...INTO did not return any row.'); 
  IF  st_id_name_cur%ISOPEN THEN
      CLOSE st_id_name_cur;
  END IF;
  
END;