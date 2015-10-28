create sequence hibernate_sequence start with 1 increment by 1
create table t_conference (id bigint not null, city varchar(255), country varchar(255), date date, name varchar(255), version integer, primary key (id))
create table t_expense (id bigint not null, amount float, currency integer, date date, description varchar(255), expenseType integer, version integer, primary key (id))
create table t_reimbursement (id bigint not null, currency integer, date date, version integer, conference_id bigint, user_id bigint, primary key (id))
create table t_reimbursement_t_expense (Reimbursement_id bigint not null, expenses_id bigint not null, primary key (Reimbursement_id, expenses_id))
create table t_user (id bigint not null, login varchar(255), name varchar(255), password varchar(255), version integer, primary key (id))
alter table t_reimbursement add constraint FKerfadapb600fyumya4gevcyhh foreign key (conference_id) references t_conference
alter table t_reimbursement add constraint FKp2h0hvxb2wapjn17klfwtflce foreign key (user_id) references t_user
alter table t_reimbursement_t_expense add constraint UK_575u7gu9yrf7nd30wrnq6e0bx unique (expenses_id)
alter table t_reimbursement_t_expense add constraint FKm5w6qi6nst8hght0llhh08o4m foreign key (expenses_id) references t_expense
alter table t_reimbursement_t_expense add constraint FKf08bod2wlottgc6ds7y85n6ye foreign key (Reimbursement_id) references t_reimbursement
