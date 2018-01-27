/*
 * Lucia
 */
drop table if exists peo_role;

drop table if exists role_pri;

drop table if exists t_dept;

drop table if exists t_leader;

drop table if exists t_org;

drop table if exists t_people;

drop table if exists t_privildge;

drop table if exists t_role;

/*==============================================================*/
/* Table: peo_role                                              */
/*==============================================================*/
create table peo_role
(
   role_id              varchar(32) not null,
   p_id                 varchar(32) not null,
   state                int,
   primary key (role_id, p_id)
);

alter table peo_role comment '1,有效
0,无效';

/*==============================================================*/
/* Table: role_pri                                              */
/*==============================================================*/
create table role_pri
(
   role_id              varchar(32) not null,
   pri_id               varchar(1024) not null,
   primary key (role_id, pri_id)
);

/*==============================================================*/
/* Table: t_dept                                                */
/*==============================================================*/
create table t_dept
(
   dept_d               varchar(32) not null,
   org_id               varchar(32),
   name                 varchar(50),
   primary key (dept_d)
);

/*==============================================================*/
/* Table: t_leader                                              */
/*==============================================================*/
create table t_leader
(
   p_id                 varchar(32) not null,
   dept_d               varchar(32),
   name                 varchar(50) not null,
   position             varchar(1024),
   primary key (p_id)
);

/*==============================================================*/
/* Table: t_org                                                 */
/*==============================================================*/
create table t_org
(
   org_id               varchar(32) not null,
   name                 varchar(50),
   primary key (org_id)
);

/*==============================================================*/
/* Table: t_people                                              */
/*==============================================================*/
create table t_people
(
   p_id                 varchar(32) not null,
   dept_d               varchar(32),
   name                 varchar(50) not null,
   primary key (p_id)
);

/*==============================================================*/
/* Table: t_privildge                                           */
/*==============================================================*/
create table t_privildge
(
   pri_id               varchar(1024) not null,
   name                 varchar(50),
   primary key (pri_id)
);

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   role_id              varchar(32) not null,
   name                 varchar(50),
   primary key (role_id)
);

drop table if exists complain;

/*==============================================================*/
/* Table: complain                                              */
/*==============================================================*/
create table complain
(
   comp_id              varchar(32) not null,
   comp_company         varchar(100),
   comp_name            varchar(20),
   comp_mobile          varchar(20),
   is_NM                bool,
   comp_time            datetime,
   comp_title           varchar(200) not null,
   to_comp_name         varchar(20),
   to_comp_dept         varchar(100),
   comp_content         text,
   state                varchar(1),
   primary key (comp_id)
);  

drop table if exists complain_reply;

/*==============================================================*/
/* Table: complain_reply                                        */
/*==============================================================*/
create table complain_reply
(
   reply_id             varchar(32) not null,
   comp_id              varchar(32) not null,
   replyer              varchar(20),
   reply_dept           varchar(100),
   reply_time           datetime,
   reply_content        varchar(300),
   primary key (reply_id)
);

alter table complain_reply add constraint FK_comp_reply foreign key (comp_id)
      references complain (comp_id) on delete restrict on update restrict;


alter table peo_role add constraint FK_peo_role foreign key (role_id)
      references t_role (role_id) on delete restrict on update restrict;

alter table peo_role add constraint FK_peo_role2 foreign key (p_id)
      references t_people (p_id) on delete restrict on update restrict;

alter table role_pri add constraint FK_belong foreign key (role_id)
      references t_role (role_id) on delete restrict on update restrict;

alter table role_pri add constraint FK_own foreign key (pri_id)
      references t_privildge (pri_id) on delete restrict on update restrict;

alter table t_dept add constraint FK_org_dept foreign key (org_id)
      references t_org (org_id) on delete restrict on update restrict;

alter table t_leader add constraint FK_peo_leader foreign key (p_id)
      references t_people (p_id) on delete restrict on update restrict;

alter table t_people add constraint FK_dept_peo foreign key (dept_d)
      references t_dept (dept_d) on delete restrict on update restrict;
