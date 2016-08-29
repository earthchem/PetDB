CREATE OR REPLACE PROCEDURE         "PRC_COMPILECHEMISTRY" is
nWRnum integer;
nROCKnum integer;
nGLnum integer;
nITnum integer;
nRownum integer;
nSample_num integer;
cItem_measured varchar2(20);
cItem_type varchar2(10);
cMaterial varchar2 (10);
nValue_meas number;
cUnit varchar2(20);
nRef_num integer;
nData_quality_num integer;
nStdev number;
cStdev_type varchar2(20);
nPub_year integer;
--LS  nMethodnum integer;
--LS  cMethodCode varchar2(255);
  nPubYear integer;
begin
   DBMS_OUTPUT.ENABLE(99999);
-- Clear bf_samplechemfinal
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE bf_compilechemfinal');
--   dbms_utility.exec_ddl_statement ('alter table bf_samplechemfinal  drop constraint PK_bf_samplechemfinal cascade');
   select material_num into nWRnum from material where material_code = 'WR';
   select material_num into nROCKnum from material where material_code = 'ROCK';
   select material_num into nGLnum from material where material_code = 'GL';
-- Start Trace Section
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMMPR');
   select it.item_type_num into nITnum from item_type it where it.item_type_code = 'TE';
   
   INSERT INTO BF_COMPILECHEM (sample_num, item_measured_num, material_num, item_type,
--LS                                     data_quality_num, ref_num, method_num, pub_year,
                                         data_quality_num, ref_num, pub_year,
                                     value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT b.sample_num, c.item_measured_num, b.material_num, 'TE', 
            a.data_quality_num, t.ref_num, null, 
            c.value_meas, c.unit, c.stdev, c.stdev_type, c.data_entered_date
   FROM  batch b, analysis a, chemistry c, itemtype_list itl, table_in_ref t
   WHERE t.table_in_ref_num = b.table_in_ref_num
     and b.batch_num = a.batch_num
     AND a.analysis_num = c.analysis_num
     AND c.itemtypelist_num = itl.itemtypelist_num
     AND itl.item_type_num = nITnum
     AND b.material_num in (nWRnum, nROCKnum, nGLnum);
   commit;
   --update null values
   --LS for curBC in (select distinct data_quality_num from BF_COMPILECHEM)
   --LS loop
   --LS  select m.method_num, m.method_code into nMethodnum, cMethodCode
   --LS  from method m, data_quality dq
   --LS  where m.method_num = dq.method_num
    --LS   and dq.data_quality_num = curBC.data_quality_num;
   --LS  update BF_COMPILECHEM b
   --LS set b.method_num = nMethodnum,
 
   --LS  where b.data_quality_num = curBC.data_quality_num;
   --LS end loop;
   --LS commit;
   
   --LS update null pub_year
   for curBC in (select distinct ref_num from BF_COMPILECHEM)
   loop
     select r.pub_year into nPubYear
     from reference r
     where r.ref_num = curBC.ref_num;
     update BF_COMPILECHEM b
     set b.pub_year = nPubYear
     where b.ref_num = curBC.ref_num;
   end loop;
   commit;
   --update 'ROCK' to 'WR'
   update BF_COMPILECHEM b set b.material_num = nWRnum where b.material_num = nROCKnum;
   commit;
   -- Select best data based on year published
   update BF_COMPILECHEM t
   --LS set t.methodprominence = to_number(decode(method_code, 'MS-ID',8,'MS',7, 'ICPMS',6,'SSMS',5,'XRF',4,'DCP',3,'INAA',2,'AAS',1,0))*10000 + nvl(pub_year,0);
   set t.methodprominence = nvl(pub_year,0);
   INSERT INTO BF_COMPILECHEMMPR (sample_num, item_measured_num, item_type, material_num, 
   --LS                                     data_quality_num, ref_num, method_num, pub_year,
                                        data_quality_num, ref_num, pub_year,
                                        value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT a.sample_num, a.item_measured_num, a.item_type, a.material_num, 
          a.data_quality_num, a.ref_num, a.pub_year,
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.data_etnered_date
   FROM BF_COMPILECHEM a
   WHERE a.methodprominence =
         (SELECT max(methodprominence)
          FROM BF_COMPILECHEM
          WHERE sample_num = a.sample_num
            AND material_num = a.material_num
            AND item_measured_num = a.item_measured_num);
   DBMS_OUTPUT.PUT_LINE ('TE rows Inserted into MPR: '||sql%rowcount);
   commit;
   -- Insert single record, ignore others
   INSERT INTO bf_compilechemfinal (sample_num, material_num, item_measured_num, item_type, 
                                 value_meas, unit, stdev, stdev_type, ref_num, data_quality_num, data_entered_date)
   SELECT a.sample_num, a.material_num, a.item_measured_num, a.item_type, 
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.ref_num, a.data_quality_num, a.data_etnered_date
   FROM BF_COMPILECHEMMPR a,
        (SELECT min(rowid) rid
         FROM BF_COMPILECHEMmpr 
         GROUP BY sample_num, item_measured_num, material_num) b
   WHERE a.rowid = b.rid;
   COMMIT;
   -- Start MAJ Section-----------------------------------------------------
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMMPR');
   --Insert Data into buffer
   select it.item_type_num into nITnum from item_type it where it.item_type_code = 'MAJ';
   INSERT INTO BF_COMPILECHEM (sample_num, item_measured_num, material_num, item_type,
   --LS                                  data_quality_num, ref_num, method_num, method_code,
                                     data_quality_num, ref_num,
                                     value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT b.sample_num, c.item_measured_num, b.material_num, 'MAJ',
            a.data_quality_num, t.ref_num,
            c.value_meas, c.unit, c.stdev, c.stdev_type, c.data_entered_date
   FROM  batch b, analysis a, chemistry c, itemtype_list itl, table_in_ref t
   WHERE t.table_in_ref_num = b.table_in_ref_num
     and b.batch_num = a.batch_num
     AND a.analysis_num = c.analysis_num
     AND c.itemtypelist_num = itl.itemtypelist_num
     AND itl.item_type_num = nITnum
     AND b.material_num in (nWRnum, nROCKnum, nGLnum);
   commit;
   --Update data for missing columns
   --LS for curBC in (select distinct data_quality_num from BF_COMPILECHEM)
   --LS loop
   --LS select m.method_num, m.method_code into nMethodnum, cMethodCode
   --LS  from method m, data_quality dq
   --LS  where m.method_num = dq.method_num
   --LS    and dq.data_quality_num = curBC.data_quality_num;
   --LS  update BF_COMPILECHEM b
   --LS  set b.method_num = nMethodnum,    
   --LS  where b.data_quality_num = curBC.data_quality_num;
   --LS end loop;
   --LS commit;
   --LS Update data for pub_year
   for curBC in (select distinct ref_num from BF_COMPILECHEM)
   loop
     select r.pub_year into nPubYear
     from reference r
     where r.ref_num = curBC.ref_num;
     update BF_COMPILECHEM b
     set b.pub_year = nPubYear
     where b.ref_num = curBC.ref_num;
   end loop;
   commit;
   --update 'ROCK' to 'WR'
   update BF_COMPILECHEM b set b.material_num = nWRnum where b.material_num = nROCKnum;
   -- Select best data based on method used, then year published
   --LS Select best data based on year published
   update BF_COMPILECHEM t
   --LS set t.methodprominence = to_number(decode (t.method_code, 'XRF',6,'DCP',5,'WET',4,'EMP',3,'ES',2,'CALC',1,0))*10000 + nvl(t.pub_year,0);
   set t.methodprominence = nvl(t.pub_year,0);
  
   INSERT INTO BF_COMPILECHEMMPR (sample_num, item_measured_num, item_type, material_num, 
   --LS                                     data_quality_num, ref_num, method_num, pub_year,
                                            data_quality_num, ref_num, pub_year,
                                        value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT a.sample_num, a.item_measured_num, a.item_type, a.material_num, 
   --LS       a.data_quality_num, a.ref_num, a.method_num, a.pub_year,
          a.data_quality_num, a.ref_num, a.pub_year,
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.data_etnered_date
   FROM BF_COMPILECHEM a
   WHERE a.methodprominence =
         (SELECT max(methodprominence)
          FROM BF_COMPILECHEM
          WHERE sample_num        = a.sample_num
            AND item_measured_num = a.item_measured_num
            AND material_num      = a.material_num);   
   DBMS_OUTPUT.PUT_LINE ('MAJ rows Inserted into MPR: '||sql%rowcount);
   commit;
   -- Ther are some duplicates where item is measured both as MAJ and TE, TE has preference, MAJ has to be deleted not to raise PK exception  
   DELETE FROM BF_COMPILECHEMmpr a
   WHERE a.rowid in
      (Select b.rowid
       from BF_COMPILECHEMmpr b, bf_compilechemfinal c
       where b.sample_num = c.sample_num
         and b.item_measured_num = c.item_measured_num
         and b.material_num = c.material_num);
   commit;
   -- Insert single record, ignore others
   INSERT INTO bf_compilechemfinal (sample_num, material_num, item_measured_num, item_type, 
                                 value_meas, unit, stdev, stdev_type, ref_num, data_quality_num, data_entered_date)
   SELECT a.sample_num, a.material_num, a.item_measured_num, a.item_type, 
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.ref_num, a.data_quality_num, a.data_etnered_date
   FROM BF_COMPILECHEMmpr a,
        (SELECT min(rowid) rid
         FROM BF_COMPILECHEMmpr 
         GROUP BY sample_num, item_measured_num, material_num) b
   WHERE a.rowid = b.rid;
   COMMIT;
   -- Start REE Section-----------------------------------------------------
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMMPR');
   --Insert Data into buffer
   INSERT INTO BF_COMPILECHEM (sample_num, item_measured_num, item_type, material_num, 
   --LS                                  data_quality_num, ref_num, method_num, method_code,
                                     data_quality_num, ref_num,
                                     value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT b.sample_num, c.item_measured_num, it.item_type_code, m.material_num,
   --LS         a.data_quality_num, null , null, null,
            a.data_quality_num, null ,
            c.value_meas, c.unit, c.stdev, c.stdev_type, c.data_entered_date
   FROM  material m, batch b, analysis a, chemistry c, itemtype_list itl, item_type it
   WHERE m.material_num = b.material_num
     AND b.batch_num = a.batch_num
     AND a.analysis_num = c.analysis_num
     AND c.itemtypelist_num = itl.itemtypelist_num
     AND itl.item_type_num = it.item_type_num
     AND m.material_code in ('GL', 'WR', 'ROCK')
     AND it.item_type_code = 'REE';
   commit;
   --Update data for missing columns
   update BF_COMPILECHEM b
   --LS  set (b.method_num, b.method_code, b.ref_num, b.pub_year) =
   --LS  (select m.method_num, m.method_code, r.ref_num, r.pub_year
   set ( b.ref_num, b.pub_year) =
       (select r.ref_num, r.pub_year
        from method m, reference r, data_quality dq
        where dq.method_num = m.method_num
          and dq.ref_num = r.ref_num
          and dq.data_quality_num = b.data_quality_num);
   commit;
   --update 'ROCK' to 'WR'
   update BF_COMPILECHEM b set b.material_num = nWRnum where b.material_num = nROCKnum;
   -- Select best data based on method used, then year published   
   INSERT INTO BF_COMPILECHEMmpr (sample_num, item_measured_num, item_type, material_num, 
   --LS                                     data_quality_num, ref_num, method_num, pub_year,
                                        data_quality_num, ref_num, pub_year,
                                        value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT a.sample_num, a.item_measured_num, a.item_type, a.material_num, 
   --LS       a.data_quality_num, a.ref_num, a.method_num, a.pub_year,
          a.data_quality_num, a.ref_num, a.pub_year,
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.data_etnered_date
   FROM BF_COMPILECHEM a
   --LS WHERE to_number(decode (a.method_code, 'MS-ID',8,'MS',7, 'ICPMS',6,'DCP',5,'SSMS',4,'INAA',3,'NAA',2,0))*10000 + nvl(a.pub_year,0) =
   --LS      (SELECT max(to_number(decode(method_code, 'MS-ID',8,'MS',7, 'ICPMS',6,'DCP',5,'SSMS',4,'INAA',3,'NAA',2,0))*10000 + nvl(pub_year,0))
   WHERE nvl(a.pub_year,0) =
         (SELECT max(nvl(pub_year,0))
          FROM BF_COMPILECHEM
          WHERE sample_num        = a.sample_num
            AND item_measured_num = a.item_measured_num
            AND material_num      = a.material_num);   
   DBMS_OUTPUT.PUT_LINE ('REE rows Inserted into MPR: '||sql%rowcount);
   commit;
   -- Insert single record, ignore others
   INSERT INTO bf_compilechemfinal  (sample_num, material_num, item_measured_num, item_type, 
                                 value_meas, unit, stdev, stdev_type, ref_num, data_quality_num, data_entered_date)
   SELECT a.sample_num, a.material_num, a.item_measured_num, a.item_type, 
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.ref_num, a.data_quality_num, a.data_etnered_date
   FROM BF_COMPILECHEMmpr a,
        (SELECT min(rowid) rid
         FROM BF_COMPILECHEMmpr 
         GROUP BY sample_num, item_measured_num, material_num) b
   WHERE a.rowid = b.rid;
   COMMIT;   
   -- Start VO (volatiles) F  Cl Section-----------------------------------------------------
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMMPR');
   --Insert Data into buffer
   INSERT INTO BF_COMPILECHEM (sample_num, item_measured_num, item_type, material_num, 
   --LS                                  data_quality_num, ref_num, method_num, method_code,
                                     data_quality_num, ref_num,
                                     value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT b.sample_num, c.item_measured_num, it.item_type_code, m.material_num,
   --LS         a.data_quality_num, null , null, null,
                a.data_quality_num, null ,
            c.value_meas, c.unit, c.stdev, c.stdev_type, c.data_entered_date
   FROM material m, batch b, analysis a, chemistry c,  item_measured im, itemtype_list itl, item_type it
   WHERE m.material_num = b.material_num
     AND b.batch_num = a.batch_num
     AND a.analysis_num = c.analysis_num
     AND c.itemtypelist_num = itl.itemtypelist_num
     AND itl.item_type_num = it.item_type_num
     AND c.item_measured_num = im.item_measured_num
     AND m.material_code in ('GL', 'WR', 'ROCK')
     AND it.item_type_code = 'VO'
     AND im.item_code in ('F', 'Cl');
   commit;
   --Update data for missing columns
   update BF_COMPILECHEM b
   --LS set (b.method_num, b.method_code, b.ref_num, b.pub_year) =
   --LS    (select m.method_num, m.method_code, r.ref_num, r.pub_year
   set ( b.ref_num, b.pub_year) =
       (select r.ref_num, r.pub_year
        from method m, reference r, data_quality dq
        where dq.method_num = m.method_num
          and dq.ref_num = r.ref_num
          and dq.data_quality_num = b.data_quality_num);
   commit;
   --update 'ROCK' to 'WR'
   update BF_COMPILECHEM b set b.material_num = nWRnum where b.material_num = nROCKnum;
   -- Select best data based on method used, then year published   
   INSERT INTO BF_COMPILECHEMmpr (sample_num, item_measured_num, item_type, material_num, 
   --LS                                     data_quality_num, ref_num, method_num, pub_year,
                                        data_quality_num, ref_num, pub_year,
                                        value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT a.sample_num, a.item_measured_num, a.item_type, a.material_num, 
   --LS       a.data_quality_num, a.ref_num, a.method_num, a.pub_year,
              a.data_quality_num, a.ref_num, a.pub_year,
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.data_etnered_date
   FROM BF_COMPILECHEM a
--LS      WHERE to_number(decode (a.method_code, 'MS',8,'ANC',7,'ISE',6,'INAA',5,'EMP',4,'NN',3,0))*10000 + nvl(a.pub_year,0) =
--LS         (SELECT max(to_number(decode(method_code, 'MS',8,'ANC',7,'ISE',6,'INAA',5,'EMP',4,'NN',3,0))*10000 + nvl(pub_year,0))
   WHERE nvl(a.pub_year,0) =
         (SELECT max(nvl(pub_year,0))
          FROM BF_COMPILECHEM
          WHERE sample_num        = a.sample_num
            AND item_measured_num = a.item_measured_num
            AND material_num      = a.material_num);   
   DBMS_OUTPUT.PUT_LINE ('FCL rows Inserted into MPR: '||sql%rowcount);
   commit;
   -- Insert single record, ignore others
   INSERT INTO bf_compilechemfinal (sample_num, material_num, item_measured_num, item_type, 
                                 value_meas, unit, stdev, stdev_type, ref_num, data_quality_num, data_entered_date)
   SELECT a.sample_num, a.material_num, a.item_measured_num, a.item_type, 
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.ref_num, a.data_quality_num, a.data_etnered_date
   FROM BF_COMPILECHEMmpr a,
        (SELECT min(rowid) rid
         FROM BF_COMPILECHEMmpr 
         GROUP BY sample_num, item_measured_num, material_num) b
   WHERE a.rowid = b.rid;
   COMMIT;      
   -- Start IR Section -----------------------------------------------------
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMMPR');
   --Insert Data into buffer
   INSERT INTO BF_COMPILECHEM (sample_num, item_measured_num, item_type, material_num, 
   --LS                                  data_quality_num, ref_num, method_num, method_code,
                                     data_quality_num, ref_num,
                                     value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT b.sample_num, c.item_measured_num, it.item_type_code, m.material_num,
   --LS         a.data_quality_num, null , null, null,
             a.data_quality_num, null ,
            c.value_meas, c.unit, c.stdev, c.stdev_type, c.data_entered_date
   FROM  material m, batch b, analysis a, chemistry c, itemtype_list itl, item_type it
   WHERE m.material_num = b.material_num
     AND b.batch_num = a.batch_num
     AND a.analysis_num = c.analysis_num
     AND c.itemtypelist_num = itl.itemtypelist_num
     AND itl.item_type_num = it.item_type_num
     AND m.material_code in ('GL', 'WR', 'ROCK')
     AND it.item_type_code  in ('IR', 'AGE','IR2', 'IS', 'NGAS', 'US');
   commit;
   --Update data for missing columns
   update BF_COMPILECHEM b
   --LS set (b.method_num, b.method_code, b.ref_num, b.pub_year) =
   --LS    (select m.method_num, m.method_code, r.ref_num, r.pub_year
   set ( b.ref_num, b.pub_year) =
       (select r.ref_num, r.pub_year
        from method m, reference r, data_quality dq
        where dq.method_num = m.method_num
          and dq.ref_num = r.ref_num
          and dq.data_quality_num = b.data_quality_num);
   commit;
   --update 'ROCK' to 'WR'
   update BF_COMPILECHEM b set b.material_num = nWRnum where b.material_num = nROCKnum;
   -- Select best data based on latest year published, then minimum stdev
   INSERT INTO BF_COMPILECHEMmpr (sample_num, item_measured_num, item_type, material_num, 
   --LS                                     data_quality_num, ref_num, method_num, pub_year,
                                        data_quality_num, ref_num, pub_year,
                                        value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT a.sample_num, a.item_measured_num, a.item_type, a.material_num, 
   --LS       a.data_quality_num, a.ref_num, a.method_num, a.pub_year,
          a.data_quality_num, a.ref_num, a.pub_year,
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.data_etnered_date
   FROM BF_COMPILECHEM a
   --LS WHERE  nvl(a.pub_year,0)*100000 - nvl(a.stdev, 100000)  =
   --LS      (SELECT max(nvl(pub_year,0)*100000 - nvl(a.stdev, 100000))
   WHERE  nvl(a.pub_year,0)  =
        (SELECT max(nvl(pub_year,0))       
          FROM BF_COMPILECHEM
          WHERE sample_num        = a.sample_num
            AND item_measured_num = a.item_measured_num
            AND material_num      = a.material_num);   
   DBMS_OUTPUT.PUT_LINE ('IR rows Inserted into MPR: '||sql%rowcount);
   commit;
   -- Insert single record, ignore others
   INSERT INTO bf_compilechemfinal (sample_num, material_num, item_measured_num, item_type, 
                                 value_meas, unit, stdev, stdev_type, ref_num, data_quality_num, data_entered_date)
   SELECT a.sample_num, a.material_num, a.item_measured_num, a.item_type, 
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.ref_num, a.data_quality_num, a.data_etnered_date
   FROM BF_COMPILECHEMmpr a,
        (SELECT min(rowid) rid
         FROM BF_COMPILECHEMmpr 
         GROUP BY sample_num, item_measured_num, material_num) b
   WHERE a.rowid = b.rid;
   commit;
   -- Start Others Section : Others and (Volatiles excluding F and Cl)----------------------------
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMMPR');
   --Insert Data into buffer
   INSERT INTO BF_COMPILECHEM (sample_num, item_measured_num, item_type, material_num, 
   --LS                                  data_quality_num, ref_num, method_num, method_code,
                                     data_quality_num, ref_num,
                                     value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT b.sample_num, c.item_measured_num, it.item_type_code, m.material_num,
  --LS          a.data_quality_num, null, null, null,
            a.data_quality_num, null,
            c.value_meas, c.unit, c.stdev, c.stdev_type, c.data_entered_date
   FROM material m, batch b, analysis a, chemistry c, item_measured im, itemtype_list itl, item_type it
   WHERE m.material_num = b.material_num
     AND b.batch_num = a.batch_num
     AND a.analysis_num = c.analysis_num
     AND c.itemtypelist_num = itl.itemtypelist_num
     AND itl.item_type_num = it.item_type_num
     AND c.item_measured_num = im.item_measured_num
     AND m.material_code in  ('GL', 'WR', 'ROCK')
     AND (it.item_type_code in ('UN','VO') and im.item_code not in ('F', 'Cl'));
   commit;
   --Update data for missing columns
   update BF_COMPILECHEM b
   --LS set (b.method_num, b.method_code, b.ref_num, b.pub_year) =
   --LS     (select m.method_num, m.method_code, r.ref_num, r.pub_year
   set ( b.ref_num, b.pub_year) =
       (select r.ref_num, r.pub_year
        from method m, reference r, data_quality dq
        where dq.method_num = m.method_num
          and dq.ref_num = r.ref_num
          and dq.data_quality_num = b.data_quality_num);
   commit;
   --update 'ROCK' to 'WR'
   update BF_COMPILECHEM b set b.material_num = nWRnum where b.material_num = nROCKnum;
   -- Select best data based on latest year published, then minimum stdev
   INSERT INTO BF_COMPILECHEMmpr (sample_num, item_measured_num, item_type, material_num, 
   --LS                                     data_quality_num, ref_num, method_num, pub_year,
                                        data_quality_num, ref_num, pub_year,
                                        value_meas, unit, stdev, stdev_type, data_etnered_date)
   SELECT a.sample_num, a.item_measured_num, a.item_type, a.material_num, 
   --LS       a.data_quality_num, a.ref_num, a.method_num, a.pub_year,
          a.data_quality_num, a.ref_num, a.pub_year,
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.data_etnered_date
   FROM BF_COMPILECHEM a
   --LS WHERE  nvl(a.pub_year,0)*100000 - nvl(a.stdev, 100000)  =
   --LS      (SELECT max(nvl(pub_year,0)*100000 - nvl(a.stdev, 100000))
   WHERE  nvl(a.pub_year,0)  =
         (SELECT max(nvl(pub_year,0))          FROM BF_COMPILECHEM
          WHERE sample_num        = a.sample_num
            AND item_measured_num = a.item_measured_num
            AND material_num      = a.material_num);   
   DBMS_OUTPUT.PUT_LINE ('OTHERS rows Inserted into MPR: '||sql%rowcount);
   commit;
   -- Insert single record, ignore others
   INSERT INTO bf_compilechemfinal (sample_num, material_num, item_measured_num, item_type, 
                                 value_meas, unit, stdev, stdev_type, ref_num, data_quality_num, data_entered_date)
   SELECT a.sample_num, a.material_num, a.item_measured_num, a.item_type, 
          a.value_meas, a.unit, a.stdev, a.stdev_type, a.ref_num, a.data_quality_num, a.data_etnered_date
   FROM BF_COMPILECHEMmpr a,
        (SELECT min(rowid) rid
         FROM BF_COMPILECHEMmpr 
         GROUP BY sample_num, item_measured_num, material_num) b
   WHERE a.rowid = b.rid;
   commit;
   --Replace data in sample_chemistry with newly compiled data
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE SAMPLE_CHEMISTRY');
   insert into sample_chemistry (sample_num, item_measured_num, material_num, item_type, 
                                 value_meas, stdev, stdev_type, unit, ref_num, data_quality_num, data_entered_date)
   select sample_num, item_measured_num, material_num, item_type, 
          value_meas, stdev, stdev_type, unit, ref_num, data_quality_num, data_entered_date
   from bf_compilechemfinal;
   commit;
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE bf_compilechemfinal');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEMmpr');
   dbms_utility.exec_ddl_statement ('TRUNCATE TABLE BF_COMPILECHEM');
end;