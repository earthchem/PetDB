/* Delete a reference and its related samples and stations. reference number is required as pass-in value. */
CREATE OR REPLACE PROCEDURE PRC_DEL_REF  (ref_num_in IN NUMBER )

IS    

ref_num_to_delete number(6) :=  ref_num_in ; /* The reference paper number which will be deleted */
ref_counter number(6) :='0';                 /* Counter of this reference */
delStnNum number(6);                         /* Related station number which will be deleted */
sample_counter number(6) :='0';              /* counter of sample which is related to the reference */
station_counter number(6) :='0';             /* counter of station which is related to the reference */

TYPE sample_num_rec is RECORD                /* Type declaration. It is used to hold all sample numbers relate to the reference */

 ( sample_num batch.sample_num%type );
 
sample_nums sample_num_rec;                  /* Hold all sample numbers relate to the reference */

CURSOR sample_num_cur IS                     /* Define cursor pointing to the sample numbers relate to the reference */ 
 
 SELECT unique(b.sample_num)                 /* Get all samples numbers in batch table which is related to the to-be-deleted reference */

 INTO sample_nums

 FROM batch b, table_in_ref tif
 where b.TABLE_IN_REF_NUM = tif.table_in_ref_num and tif.REF_NUM=ref_num_to_delete
 order by b.sample_num;

BEGIN                                       /* Begin the deleting process */
  /* Clean up samples which are related to this to-be-deleted reference */
  IF NOT sample_num_cur%ISOPEN THEN
     OPEN sample_num_cur;                   /* Open the cursor pointing to samples numbers which may need to be deleted */
  END IF;
  
  LOOP 
    FETCH sample_num_cur INTO sample_nums;    /* Loop through each sample number which is related to this to-be-deleted reference */
    EXIT WHEN sample_num_cur%NOTFOUND;
    
    dbms_output.put_line(sample_nums.sample_num);  /* Print out sample number for debugging purpose. It can be turned off. */
   
   /* Check if there is any other reference paper pointing to this sample. If ref_counter is 1, it means only this reference tied to this sample,
    then do something. Otherwise leave this sample alone. */
   select count( distinct REF_NUM )  into ref_counter from TABLE_IN_REF where table_in_ref_num in
  (select table_in_ref_num from BATCH where sample_num in
  (select sample_num from sample where sample_num=sample_nums.sample_num)); 
  dbms_output.put_line('sample is in '||ref_counter||' references');  /* Debugging statement */  
  IF (ref_counter = 1) THEN
    /* dbms_output.put_line('Delete this sample from Sample table'); */
     delete from sample where sample_num=sample_nums.sample_num; /* Delete this sample from SAMPLE table*/
     delete from sample_chemistry where sample_num=sample_nums.sample_num;  /* delete this sample from SAMPLE_CHEMISTRY table*/
     delete from igsn_info where sample_num=sample_nums.sample_num;  /* delete this sample from IGSN_INFO table*/
     
    /* Clean up stations related to this sample.*/ 
    /* First, find all stations related to this sample. */
    select count( distinct station_num) into station_counter from sample where sample_num=sample_nums.sample_num;
    /* There are some station(s) related to this sample, Do something */
    IF (station_counter > 0 ) THEN 
      select distinct station_num into delStnNum from sample where sample_num=sample_nums.sample_num;  /* Hold the station number relate to this sample in delStnNum */
      select count (distinct sample_num ) into sample_counter from sample where station_num=delStnNum; /* Check to see if the station is only related to this sample or not. */
                                                                                                       /* If sample_counter = 1, it means this station only ties to this sample, then do something.*/
      IF( sample_counter =1 ) THEN
    
        /* dbms_output.put_line('Delete this station '||delStnNum||' from Station, IGSN_INFO and Station_by_location tables'); */
    
          delete from STATION where station_num = delStnNum;              /* delete this station information from STATION table */
          delete from IGSN_INFO where station_num = delStnNum;            /* delete this station information from IGSN_INFO table */
          delete from STATION_BY_LOCATION where station_num=delStnNum;    /* delete this station information from STATION_BY_LOCATION table*/
      END IF; 
    END IF;    
  END IF;

  END LOOP;
  
  IF  sample_num_cur%ISOPEN THEN
      CLOSE sample_num_cur;           /* Close the cursor */
  END IF;

  DELETE FROM STATION_COMMENT WHERE REF_NUM=ref_num_to_delete;      /* Delete the reference from STATION_COMMENT table */
  DELETE FROM SAMPLE_COMMENT WHERE REF_NUM=ref_num_to_delete;       /* Delete the reference from SAMPLE_COMMENT table */
  DELETE FROM SAMPLE_AGE WHERE REF_NUM=ref_num_to_delete;           /* Delete the reference from SAMPLE_AGE table */
  DELETE FROM ROCK_MODE_ANALYSIS WHERE REF_NUM=ref_num_to_delete;   /* Delete the reference from ROCK_MODE_ANALYSIS table */
  DELETE FROM DATA_QUALITY WHERE REF_NUM=ref_num_to_delete;         /* Delete the reference from DATA_QUALITY table */
  DELETE FROM TABLE_IN_REF WHERE REF_NUM=ref_num_to_delete;         /* Delete the reference from TABLE_IN_REF table  */
END;


