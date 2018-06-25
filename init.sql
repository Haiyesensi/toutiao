delete database toutiao if exists;
create database toutiao;
use toutiao;

drop table comment if exists;
create table comment
(
	  id           int auto_increment
	    primary key,
	  content      text            not null,
	  user_id      int             not null,
	  entity_id    int             not null,
	  entity_type  int             not null,
	  created_date datetime        not null,
	  status       int default '0' not null
)
  charset = utf8;

create index entity_index
  on comment (entity_id, entity_type);

drop table login_ticket if exists;
create table login_ticket
(
	  id      int auto_increment
	    primary key,
	  userId  int             not null,
	  ticket  varchar(45)     not null,
	  expired datetime        not null,
	  status  int default '0' not null,
	  constraint ticket_UNIQUE
	  unique (ticket)
);

drop table message if exists;
create table message
(
	  id              int auto_increment
	    primary key,
	  from_id         int         not null,
	  to_id           int         not null,
	  content         text        not null,
	  created_date    datetime    not null,
	  has_read        int         not null,
	  conversation_id varchar(45) not null
)
  charset = utf8;

create index conversation_index
  on message (conversation_id);

create index created_date
  on message (created_date);


drop table news if exists;
create table news
(
	  id           int(11) unsigned auto_increment
	    primary key,
	  title        varchar(128) default '' not null,
	  link         varchar(256) default '' not null,
	  image        varchar(256) default '' not null,
	  likeCount    int                     not null,
	  commentCount int                     not null,
	  createdDate  datetime                not null,
	  userId       int                     not null
)
  charset = utf8;


drop table user if exists;
create table user
(
	  id       int(11) unsigned auto_increment
	    primary key,
	  name     varchar(64) default ''  not null,
	  password varchar(128) default '' not null,
	  salt     varchar(32) default ''  not null,
	  headUrl  varchar(256) default '' not null,
	  constraint name
	  unique (name)
)
  charset = utf8;
