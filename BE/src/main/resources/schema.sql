drop table if exists LOG;
drop table if exists CARD;
drop table if exists `COLUMN`;
drop table if exists BOARD;
drop table if exists USER;

create table if not exists `USER`
(
	ID bigint auto_increment,
	USERNAME varchar(24) null,
	PROFILE_IMAGE_URL varchar(500) null,
	constraint USER_pk
		primary key (ID)
);

create table if not exists BOARD
(
	ID bigint auto_increment,
	NAME varchar(24) null,
	CRT_USER_ID bigint null,
	UPD_USER_ID bigint null,
	constraint BOARD_pk
		primary key (ID),
    constraint BOARD_USER_ID_fk
        foreign key (CRT_USER_ID) references USER (ID),
    constraint BOARD_USER_ID_fk_2
        foreign key (UPD_USER_ID) references USER (ID)
);

create table if not exists `COLUMN`
(
	ID bigint auto_increment,
	NAME varchar(24) null,
	CREATED_AT datetime not null default now(),
	UPDATED_AT datetime not null default now(),
	ARCHIVED_AT datetime null,
	IS_ARCHIVED boolean not null default false,
	`ORDER` int null,
	BOARD_ID bigint null,
	CRT_USER_ID bigint null,
	UPD_USER_ID bigint null,
	constraint COLUMN_pk
		primary key (ID),
	constraint COLUMN_BOARD_ID_fk
		foreign key (BOARD_ID) references BOARD (ID),
    constraint COLUMN_USER_ID_fk
        foreign key (CRT_USER_ID) references USER (ID),
    constraint COLUMN_USER_ID_fk_2
        foreign key (UPD_USER_ID) references USER (ID)
);

create table if not exists CARD
(
	ID bigint auto_increment,
	CONTENTS varchar(500) null,
	CREATED_AT datetime not null default now(),
	UPDATED_AT datetime not null default now(),
	ARCHIVED_AT datetime null,
	IS_ARCHIVED boolean not null default false,
	`ORDER` int null,
	COLUMN_ID bigint null,
	CRT_USER_ID bigint null,
	UPD_USER_ID bigint null,
	constraint CARD_pk
		primary key (ID),
	constraint CARD_COLUMN_ID_fk
		foreign key (COLUMN_ID) references `COLUMN` (ID),
	constraint CARD_USER_ID_fk
		foreign key (CRT_USER_ID) references USER (ID),
	constraint CARD_USER_ID_fk_2
		foreign key (UPD_USER_ID) references USER (ID)
);

create table if not exists LOG
(
	ID bigint auto_increment,
	ACTION varchar(24) null,
	TYPE varchar(24) null,
	BEFORE_CARD_CONTENTS varchar(500) null,
	AFTER_CARD_CONTENTS varchar(500) null,
	BEFORE_CARD_ID bigint null,
	AFTER_CARD_ID bigint null,
	FROM_COLUMN_ID bigint null,
	TO_COLUMN_ID bigint null,
	ACTIONED_AT datetime not null default now(),
	USER_ID bigint null,
	constraint LOG_pk
		primary key (ID)
);
