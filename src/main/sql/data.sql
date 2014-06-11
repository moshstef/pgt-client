insert into sec_role (role_id , role_name) values (1000, 'ADMINISTRATOR');
insert into sec_role (role_id , role_name) values (1001, 'GUEST');
insert into sec_role (role_id , role_name) values (1002, 'GARAGE');

insert into sec_authority (authority_id , authority_name) values (1000, 'CREATE_CLAIM');
insert into sec_authority (authority_id , authority_name) values (1001, 'VIEW_CLAIM');
insert into sec_authority (authority_id , authority_name) values (1002, 'MODIFY_CLAIM');
insert into sec_authority (authority_id , authority_name) values (1003, 'VIEW_HOMEPAGE');
insert into sec_authority (authority_id , authority_name) values (1004, 'SUBMIT_GARAGE_PAYMENT');

insert into sec_role_authority (role_id , authority_id) values (1000, 1000);
insert into sec_role_authority (role_id , authority_id) values (1000, 1001);
insert into sec_role_authority (role_id , authority_id) values (1000, 1002);
insert into sec_role_authority (role_id , authority_id) values (1001, 1003);
insert into sec_role_authority (role_id , authority_id) values (1002, 1004);
