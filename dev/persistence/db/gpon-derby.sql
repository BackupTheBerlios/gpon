-- drop 
drop function CHECK_MULTIPLICITY;
drop function GPON_TO_NUMBER;
DROP TABLE t_association_property;
DROP TABLE t_association_property_decl;
drop table t_association;
drop table t_association_type;
DROP TABLE t_item_property;
DROP TABLE t_item;
DROP TABLE t_item_property_decl;
DROP TABLE t_item_type;


-- gpon_to_number


CREATE FUNCTION 
GPON_TO_NUMBER (VAL VARCHAR(30), FMT VARCHAR(30)) 
RETURNS DOUBLE PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA EXTERNAL  NAME 'de.berlios.gpon.persistence.misc.JavaDbGponToNumber.gponToNumber';

-- check multiplicity


CREATE FUNCTION 
CHECK_MULTIPLICITY(a integer, b integer, c integer) 
RETURNS integer PARAMETER STYLE JAVA READS SQL DATA LANGUAGE JAVA EXTERNAL NAME 'de.berlios.gpon.persistence.misc.JavaDbMultiplicityCheck.checkMultiplicityDB';

-- associations



CREATE TABLE t_association (
    id integer NOT NULL,
    item_a_id integer NOT NULL,
    item_b_id integer NOT NULL,
    association_type_id integer NOT NULL
);

-- check multiplicity trigger
create trigger association_check_mult NO CASCADE BEFORE INSERT on T_ASSOCIATION 
REFERENCING NEW AS NEWROW
FOR EACH ROW MODE DB2SQL 
values CHECK_MULTIPLICITY(NEWROW.ASSOCIATION_TYPE_ID, NEWROW.ITEM_A_ID, NEWROW.ITEM_B_ID);

-- association types


CREATE TABLE t_association_type (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    multiplicity character varying(20) NOT NULL,
    description character varying(200),
    base_association_type_id integer,
    item_a_type_id integer NOT NULL,
    item_b_type_id integer NOT NULL,
    item_a_rolename character varying(50),
    item_b_rolename character varying(50),
    visibility character varying(20),
    predicates character varying(200),
    predicates_a character varying(200),
    predicates_b character varying(200));

-- association properties (not used actually)



CREATE TABLE t_association_property (
    id integer NOT NULL,
    value varchar(20000),
    association_prop_decl_id integer NOT NULL,
    association_id integer NOT NULL
);



CREATE TABLE t_association_property_decl (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    property_value_type_id integer NOT NULL,
    association_type_id integer
);



CREATE TABLE t_item (
    id integer NOT NULL,
    item_type_id integer NOT NULL
);




CREATE TABLE t_item_property (
    id integer NOT NULL,
    value varchar(20000),
    item_property_decl_id integer NOT NULL,
    item_id integer NOT NULL
);



CREATE TABLE t_item_property_decl (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    property_value_type_id integer NOT NULL,
    item_type_id integer,
    mandatory character(1),
    rank integer,
    typic character(1)
);



CREATE TABLE t_item_type (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    base_item_type_id integer
);

ALTER TABLE t_association
    ADD CONSTRAINT t_association_pk PRIMARY KEY (id);

ALTER TABLE t_association_property_decl
    ADD CONSTRAINT t_association_property_de_pk PRIMARY KEY (id);

ALTER TABLE t_association_property
    ADD CONSTRAINT t_association_property_pk PRIMARY KEY (id);

ALTER TABLE t_association
    ADD CONSTRAINT t_association_type_a_b_uk UNIQUE (item_b_id, item_a_id, association_type_id);

ALTER TABLE t_association_type
    ADD CONSTRAINT t_association_type_pk PRIMARY KEY (id);

ALTER TABLE t_association_type
    ADD CONSTRAINT t_association_type_uk1 UNIQUE (name);

ALTER TABLE  t_item
    ADD CONSTRAINT t_item_pk PRIMARY KEY (id);

ALTER TABLE  t_item_property_decl
    ADD CONSTRAINT t_item_property_declarati_pk PRIMARY KEY (id);

ALTER TABLE  t_item_property
    ADD CONSTRAINT t_item_property_pk PRIMARY KEY (id);

ALTER TABLE  t_item_type
    ADD CONSTRAINT t_item_type_pk PRIMARY KEY (id);

ALTER TABLE  t_item_type
    ADD CONSTRAINT t_item_type_uk1 UNIQUE (name);

ALTER TABLE  t_association
    ADD CONSTRAINT t_association_fk FOREIGN KEY (item_a_id) REFERENCES t_item(id);

ALTER TABLE t_association
    ADD CONSTRAINT t_association_fk1 FOREIGN KEY (item_b_id) REFERENCES t_item(id);

ALTER TABLE  t_association
    ADD CONSTRAINT t_association_fk2 FOREIGN KEY (association_type_id) REFERENCES t_association_type(id);

ALTER TABLE  t_association_property_decl
    ADD CONSTRAINT t_association_property_d_fk1 FOREIGN KEY (association_type_id) REFERENCES t_association_type(id);

ALTER TABLE  t_association_property
    ADD CONSTRAINT t_association_property_fk FOREIGN KEY (association_prop_decl_id) REFERENCES t_association_property_decl(id);

ALTER TABLE  t_association_property
    ADD CONSTRAINT t_association_property_fk1 FOREIGN KEY (association_id) REFERENCES t_association(id);

ALTER TABLE  t_association_type
    ADD CONSTRAINT t_association_type_fk FOREIGN KEY (base_association_type_id) REFERENCES t_association_type(id);

ALTER TABLE  t_association_type
    ADD CONSTRAINT t_association_type_fk1 FOREIGN KEY (item_a_type_id) REFERENCES t_item_type(id);

ALTER TABLE  t_association_type
    ADD CONSTRAINT t_association_type_fk2 FOREIGN KEY (item_b_type_id) REFERENCES t_item_type(id);

ALTER TABLE  t_item
    ADD CONSTRAINT t_item_fk FOREIGN KEY (item_type_id) REFERENCES t_item_type(id);

ALTER TABLE  t_item_property_decl
    ADD CONSTRAINT t_item_property_declarat_fk1 FOREIGN KEY (item_type_id) REFERENCES t_item_type(id);

ALTER TABLE  t_item_property
    ADD CONSTRAINT t_item_property_fk FOREIGN KEY (item_property_decl_id) REFERENCES t_item_property_decl(id);

ALTER TABLE  t_item_property
    ADD CONSTRAINT t_item_property_fk1 FOREIGN KEY (item_id) REFERENCES t_item(id);

ALTER TABLE  t_item_type
    ADD CONSTRAINT t_item_type_fk FOREIGN KEY (base_item_type_id) REFERENCES t_item_type(id);
