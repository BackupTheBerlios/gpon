--
-- PostgreSQL database dump
--

SET client_encoding = 'LATIN1';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    AS '$libdir/plpgsql', 'plpgsql_call_handler'
    LANGUAGE c;


--
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    AS '$libdir/plpgsql', 'plpgsql_validator'
    LANGUAGE c;


--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


--
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    AS '$libdir/dbsize', 'database_size'
    LANGUAGE c STRICT;


--
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_database_size'
    LANGUAGE c STRICT;


--
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    AS '$libdir/admin', 'pg_dir_ls'
    LANGUAGE c STRICT;


--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$
    LANGUAGE sql STRICT;


--
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    AS '$libdir/admin', 'pg_file_read'
    LANGUAGE c STRICT;


--
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_rename'
    LANGUAGE c;


--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$
    LANGUAGE sql STRICT;


--
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    AS '$libdir/admin', 'pg_file_stat'
    LANGUAGE c STRICT;


--
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    AS '$libdir/admin', 'pg_file_unlink'
    LANGUAGE c STRICT;


--
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    AS '$libdir/admin', 'pg_file_write'
    LANGUAGE c STRICT;


--
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    AS '$libdir/admin', 'pg_logdir_ls'
    LANGUAGE c STRICT;


--
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    AS '$libdir/admin', 'pg_postmaster_starttime'
    LANGUAGE c STRICT;


--
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_relation_size'
    LANGUAGE c STRICT;


--
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    AS '$libdir/admin', 'pg_reload_conf'
    LANGUAGE c STABLE STRICT;


--
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    AS '$libdir/dbsize', 'pg_size_pretty'
    LANGUAGE c STRICT;


--
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    AS '$libdir/dbsize', 'pg_tablespace_size'
    LANGUAGE c STRICT;


--
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    AS '$libdir/dbsize', 'relation_size'
    LANGUAGE c STRICT;


--
-- Name: sp_check_assoc_multiplicity(); Type: FUNCTION; Schema: public; Owner: gpon
--

CREATE FUNCTION sp_check_assoc_multiplicity() RETURNS "trigger"
    AS $$
    DECLARE
        assTypeId          integer;
        aId                integer;
        bId 		   integer;
	tmpVar             integer;
	mult               varchar(20);
    BEGIN
   tmpVar := 0;
   mult := '';
   assTypeId:=NEW.ASSOCIATION_TYPE_ID;
   aId:=NEW.ITEM_A_ID;
   bId:=NEW.ITEM_B_ID;
   /*
    Lesen der Multiplizitaet
   */
   select multiplicity into mult from T_ASSOCIATION_TYPE where id = assTypeId;

   RAISE NOTICE 'Multiplicity: %',mult;
    
   IF mult = 'OneToMany' THEN
	   select count(*) into tmpVar from T_ASSOCIATION where ASSOCIATION_TYPE_ID = assTypeId AND ITEM_B_ID = bId;
	
	   IF tmpVar > 0 THEN
	     RAISE EXCEPTION 'OneToMany violated';
	   ELSE
	     RAISE NOTICE 'OneToMany ok: %',tmpVar;
	   END IF;
   END IF;

   RETURN NEW;
END;
$$
    LANGUAGE plpgsql;


--
-- Name: tac_to_number(character varying, character varying); Type: FUNCTION; Schema: public; Owner: gpon
--

CREATE FUNCTION tac_to_number(character varying, character varying) RETURNS real
    AS $_$
DECLARE
    value_in ALIAS FOR $1;
    fmt_in   ALIAS FOR $2;
BEGIN
    RETURN to_number(value_in,fmt_in);
EXCEPTION
 WHEN OTHERS THEN
 RETURN null;
END;
$_$
    LANGUAGE plpgsql;


--
-- Name: id_source_sq; Type: SEQUENCE; Schema: public; Owner: gpon
--

CREATE SEQUENCE id_source_sq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 20;


--
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


SET default_tablespace = '';

SET default_with_oids = true;

--
-- Name: t_association; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

CREATE TABLE t_association (
    id integer NOT NULL,
    item_a_id integer NOT NULL,
    item_b_id integer NOT NULL,
    association_type_id integer NOT NULL
);


--
-- Name: t_association_property; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

CREATE TABLE t_association_property (
    id integer NOT NULL,
    value text,
    association_prop_decl_id integer NOT NULL,
    association_id integer NOT NULL
);


--
-- Name: t_association_property_decl; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

CREATE TABLE t_association_property_decl (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    property_value_type_id integer NOT NULL,
    association_type_id integer
);


--
-- Name: t_association_type; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

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
    predicates_b character varying(200),
    CONSTRAINT t_association_type_mult_ck CHECK ((((multiplicity)::text = 'OneToMany'::text) OR ((multiplicity)::text = 'ManyToMany'::text))),
    CONSTRAINT t_association_type_visi_ck CHECK (((((visibility)::text = 'ab'::text) OR ((visibility)::text = 'ba'::text)) OR ((visibility)::text = 'abba'::text)))
);


--
-- Name: t_item; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

CREATE TABLE t_item (
    id integer NOT NULL,
    item_type_id integer NOT NULL
);


--
-- Name: t_item_property; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

CREATE TABLE t_item_property (
    id integer NOT NULL,
    value text,
    item_property_decl_id integer NOT NULL,
    item_id integer NOT NULL
);


--
-- Name: t_item_property_decl; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

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


--
-- Name: t_item_type; Type: TABLE; Schema: public; Owner: gpon; Tablespace: 
--

CREATE TABLE t_item_type (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(200),
    base_item_type_id integer
);


--
-- Name: t_association_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_association
    ADD CONSTRAINT t_association_pk PRIMARY KEY (id);


--
-- Name: t_association_property_de_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_association_property_decl
    ADD CONSTRAINT t_association_property_de_pk PRIMARY KEY (id);


--
-- Name: t_association_property_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_association_property
    ADD CONSTRAINT t_association_property_pk PRIMARY KEY (id);


--
-- Name: t_association_type_a_b_uk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_association
    ADD CONSTRAINT t_association_type_a_b_uk UNIQUE (item_b_id, item_a_id, association_type_id);


--
-- Name: t_association_type_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_association_type
    ADD CONSTRAINT t_association_type_pk PRIMARY KEY (id);


--
-- Name: t_association_type_uk1; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_association_type
    ADD CONSTRAINT t_association_type_uk1 UNIQUE (name);


--
-- Name: t_item_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_item
    ADD CONSTRAINT t_item_pk PRIMARY KEY (id);


--
-- Name: t_item_property_declarati_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_item_property_decl
    ADD CONSTRAINT t_item_property_declarati_pk PRIMARY KEY (id);


--
-- Name: t_item_property_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_item_property
    ADD CONSTRAINT t_item_property_pk PRIMARY KEY (id);


--
-- Name: t_item_type_pk; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_item_type
    ADD CONSTRAINT t_item_type_pk PRIMARY KEY (id);


--
-- Name: t_item_type_uk1; Type: CONSTRAINT; Schema: public; Owner: gpon; Tablespace: 
--

ALTER TABLE ONLY t_item_type
    ADD CONSTRAINT t_item_type_uk1 UNIQUE (name);


--
-- Name: t_association_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_association_pk_i ON t_association USING btree (id);


--
-- Name: t_association_property_de_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_association_property_de_pk_i ON t_association_property_decl USING btree (id);


--
-- Name: t_association_property_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_association_property_pk_i ON t_association_property USING btree (id);


--
-- Name: t_association_type_a_b_uk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_association_type_a_b_uk_i ON t_association USING btree (item_b_id, item_a_id, association_type_id);


--
-- Name: t_association_type_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_association_type_pk_i ON t_association_type USING btree (id);


--
-- Name: t_association_type_uk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_association_type_uk_i ON t_association_type USING btree (name);


--
-- Name: t_item_itid_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE INDEX t_item_itid_i ON t_item USING btree (item_type_id);


--
-- Name: t_item_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_item_pk_i ON t_item USING btree (id);


--
-- Name: t_item_prop_val_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE INDEX t_item_prop_val_i ON t_item_property USING btree (value);


--
-- Name: t_item_property_declarati_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_item_property_declarati_pk_i ON t_item_property_decl USING btree (id);


--
-- Name: t_item_property_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_item_property_pk_i ON t_item_property USING btree (id);


--
-- Name: t_item_property_ti_id_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE INDEX t_item_property_ti_id_i ON t_item_property USING btree (item_id);


--
-- Name: t_item_type_pk_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_item_type_pk_i ON t_item_type USING btree (id);


--
-- Name: t_item_type_uk1_i; Type: INDEX; Schema: public; Owner: gpon; Tablespace: 
--

CREATE UNIQUE INDEX t_item_type_uk1_i ON t_item_type USING btree (name);


--
-- Name: st_association_check_mult; Type: TRIGGER; Schema: public; Owner: gpon
--

CREATE TRIGGER st_association_check_mult
    BEFORE INSERT ON t_association
    FOR EACH ROW
    EXECUTE PROCEDURE sp_check_assoc_multiplicity();


--
-- Name: t_association_fk; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association
    ADD CONSTRAINT t_association_fk FOREIGN KEY (item_a_id) REFERENCES t_item(id);


--
-- Name: t_association_fk1; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association
    ADD CONSTRAINT t_association_fk1 FOREIGN KEY (item_b_id) REFERENCES t_item(id);


--
-- Name: t_association_fk2; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association
    ADD CONSTRAINT t_association_fk2 FOREIGN KEY (association_type_id) REFERENCES t_association_type(id);


--
-- Name: t_association_property_d_fk1; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association_property_decl
    ADD CONSTRAINT t_association_property_d_fk1 FOREIGN KEY (association_type_id) REFERENCES t_association_type(id);


--
-- Name: t_association_property_fk; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association_property
    ADD CONSTRAINT t_association_property_fk FOREIGN KEY (association_prop_decl_id) REFERENCES t_association_property_decl(id);


--
-- Name: t_association_property_fk1; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association_property
    ADD CONSTRAINT t_association_property_fk1 FOREIGN KEY (association_id) REFERENCES t_association(id);


--
-- Name: t_association_type_fk; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association_type
    ADD CONSTRAINT t_association_type_fk FOREIGN KEY (base_association_type_id) REFERENCES t_association_type(id);


--
-- Name: t_association_type_fk1; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association_type
    ADD CONSTRAINT t_association_type_fk1 FOREIGN KEY (item_a_type_id) REFERENCES t_item_type(id);


--
-- Name: t_association_type_fk2; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_association_type
    ADD CONSTRAINT t_association_type_fk2 FOREIGN KEY (item_b_type_id) REFERENCES t_item_type(id);


--
-- Name: t_item_fk; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_item
    ADD CONSTRAINT t_item_fk FOREIGN KEY (item_type_id) REFERENCES t_item_type(id);


--
-- Name: t_item_property_declarat_fk1; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_item_property_decl
    ADD CONSTRAINT t_item_property_declarat_fk1 FOREIGN KEY (item_type_id) REFERENCES t_item_type(id);


--
-- Name: t_item_property_fk; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_item_property
    ADD CONSTRAINT t_item_property_fk FOREIGN KEY (item_property_decl_id) REFERENCES t_item_property_decl(id);


--
-- Name: t_item_property_fk1; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_item_property
    ADD CONSTRAINT t_item_property_fk1 FOREIGN KEY (item_id) REFERENCES t_item(id);


--
-- Name: t_item_type_fk; Type: FK CONSTRAINT; Schema: public; Owner: gpon
--

ALTER TABLE ONLY t_item_type
    ADD CONSTRAINT t_item_type_fk FOREIGN KEY (base_item_type_id) REFERENCES t_item_type(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

