DECLARE

ref_num_to_delete number(6) :=  1612 ; 
ref_samplecounter number(6) :=0;
samplecounter number(6) :=0;
mysampleid  sample.sample_id%type;
mystation_num station.station_num%type;

/* Station num record */
TYPE station_num_rec is RECORD 

 ( station_num station.station_num%type );
 
station_nums station_num_rec;

CURSOR station_num_cur 
IS 
  SELECT unique(s.station_num) 
  INTO station_nums
  FROM batch b, table_in_ref tif,sample s
  where b.TABLE_IN_REF_NUM = tif.table_in_ref_num and s.sample_num = b.sample_num and tif.REF_NUM=ref_num_to_delete
  order by s.station_num; 

BEGIN
  IF NOT station_num_cur%ISOPEN THEN
     OPEN station_num_cur;
  END IF;
  
  LOOP 
    FETCH station_num_cur INTO station_nums;
    EXIT WHEN station_num_cur%NOTFOUND;
    
    dbms_output.put_line(station_nums.station_num); 
    select count( distinct s.sample_num ) into samplecounter from sample s, station st where s.station_num = station_nums.station_num;
    
    dbms_output.put_line('station '||station_nums.station_num||' has '||samplecounter||' sample');
    
    SELECT count(s.sample_num) into ref_samplecounter
    FROM batch b, table_in_ref tif,sample s
    where b.TABLE_IN_REF_NUM = tif.table_in_ref_num and s.sample_num = b.sample_num and s.station_num=station_nums.station_num and tif.REF_NUM=ref_num_to_delete; 
    
    IF (ref_samplecounter = samplecounter) THEN
     dbms_output.put_line('Same sample counter. Check to see if they have same samples and then delete');
     /* FIXME: will replace with real delete statement */
     /*delete from station where station_num=station_nums.station_num;*/
     
    ELSE 
      dbms_output.put_line('The station '||station_nums.station_num||' has data from other reference paper'||'refcounter='||ref_samplecounter);
    END IF;

  END LOOP;
  
  IF  station_num_cur%ISOPEN THEN
      CLOSE station_num_cur;
  END IF;

END;