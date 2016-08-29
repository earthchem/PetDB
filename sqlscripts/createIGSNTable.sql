CREATE TABLE IGSN_INFO
    ( igsn     char(9) NOT NULL,
      sample_num     number,
      station_num    number,
      info_id  int,
      PRIMARY KEY (igsn)
);
	
CREATE SEQUENCE IGSN_INFO_sequence
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER IGSN_INFO_trigger
BEFORE INSERT
ON IGSN_INFO
REFERENCING NEW AS NEW
FOR EACH ROW
BEGIN
SELECT IGSN_INFO_sequence.nextval INTO :NEW.info_id FROM dual;
END;

insert into petdb.igsn_info (igsn,sample_num) values('55500002G', 49236);
insert into petdb.igsn_info (igsn,sample_num) values('55500002H', 49237);
