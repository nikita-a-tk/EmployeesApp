-- User: root
-- DROP USER root;
CREATE USER root WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION;

-- Database: employees
-- DROP DATABASE employees;
CREATE DATABASE employees
    WITH 
    OWNER = nikitatkachev
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE employees TO nikitatkachev;
GRANT TEMPORARY, CONNECT ON DATABASE employees TO PUBLIC;
GRANT ALL ON DATABASE employees TO root;

-- Table: public.employee
-- DROP TABLE public.employee;
CREATE TABLE public.employee
(
    id integer NOT NULL DEFAULT nextval('employee_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    age numeric(25,3),
    gender numeric(1,0),
    CONSTRAINT employee_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.employee
    OWNER to postgres;

GRANT ALL ON TABLE public.employee TO postgres;
GRANT ALL ON TABLE public.employee TO root;

-- Table: public.job
-- DROP TABLE public.job;
CREATE TABLE public.job
(
    id integer NOT NULL DEFAULT nextval('job_id_seq'::regclass),
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    emp_id integer NOT NULL DEFAULT nextval('job_emp_id_seq'::regclass),
    salary numeric(25,3),
    CONSTRAINT job_pkey PRIMARY KEY (id),
    CONSTRAINT employee FOREIGN KEY (emp_id)
        REFERENCES public.employee (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.job
    OWNER to postgres;

GRANT ALL ON TABLE public.job TO postgres;
GRANT ALL ON TABLE public.job TO root;
