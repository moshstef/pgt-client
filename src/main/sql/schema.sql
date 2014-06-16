CREATE TABLE sec_authority
(
    authority_id integer PRIMARY KEY NOT NULL,
    authority_name VARCHAR(60) NOT NULL
);
CREATE TABLE sec_role
(
    role_id integer PRIMARY KEY NOT NULL,
    role_name VARCHAR(60) NOT NULL
);
CREATE TABLE sec_role_authority
(
    role_id INTEGER NOT NULL references sec_role on delete cascade,
    authority_id INTEGER NOT NULL references sec_authority on delete cascade,
    PRIMARY KEY  (role_id, authority_id)
);

create sequence seq_user_id start 100000;
CREATE TABLE sec_user
(
    user_id integer PRIMARY KEY NOT NULL,
    username VARCHAR(60)  UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    enabled BOOL DEFAULT true NOT NULL,
    login_tries integer default 0
);
CREATE TABLE sec_user_role
(
    user_id integer NOT NULL references sec_user on delete cascade,
    role_id integer NOT NULL references sec_role on delete cascade,
    PRIMARY KEY  (role_id, user_id)
);



create table audit_trace
(
  audit_trace_id              serial primary key not null,
  audit_trace_name            varchar(200) not null,
  audit_trace_session_id      varchar(500) not null,
  audit_trace_user_id         integer not null references sec_user(user_id),
  audit_trace_user_ip         varchar(300) not null,
  audit_trace_timestamp       timestamp not null,
  audit_trace_method_name     varchar(200),
  audit_trace_method_params   text,
  audit_trace_request_params  text
);
