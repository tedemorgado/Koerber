create table users
(
    id   SERIAL NOT NULL,
    uuid UUID   NOT NULL,
    name character varying(255),
    PRIMARY KEY (id)
);

create table screen
(
    id           SERIAL NOT NULL,
    uuid         UUID   NOT NULL,
    name         character varying(255),
    content_json text,
    PRIMARY KEY (id)
);

create table filter
(
    id            SERIAL           NOT NULL,
    uuid          UUID             NOT NULL,
    user_id       bigint           NOT NULL,
    name          character varying(255),
    Data          text,
    output_filter character varying(255),
    screen_id     bigint,
    version       bigint default 1 not null,
    PRIMARY KEY (id),
    CONSTRAINT fk_filter_user_id_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_filter_screen_id_screen_id FOREIGN KEY (screen_id) REFERENCES screen (id)
);

create table branch
(
    id                      serial           NOT NULL,
    uuid                    UUID             NOT NULL,
    original_filter_id      bigint           not null,
    original_filter_version bigint           not null,
    filter_id               bigint           not null,
    version                 bigint default 1 not null,
    primary key (id),
    CONSTRAINT fk_branch_original_filter_id_filter_id FOREIGN KEY (original_filter_id) REFERENCES filter (id),
    CONSTRAINT fk_branch_filter_id_filter_id FOREIGN KEY (filter_id) REFERENCES filter (id)
);


-- AUDIT
create table audit_info
(
    revision_id   serial primary key,
    rev_timestamp bigint not null,
    user_id       UUID   not null
);

create table screen_audit
(
    id           bigint   not null,
    rev          bigint   not null,
    revtype      smallint not null,
    uuid         UUID,
    name         character varying(255),
    content_json text,
    primary key (rev, id),
    constraint fk_screen_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

create table user_audit
(
    id      bigint   not null,
    rev     bigint   not null,
    revtype smallint not null,
    uuid    UUID,
    name    character varying(255),
    primary key (rev, id),
    constraint fk_user_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

create table filter_audit
(
    id            bigint   not null,
    rev           bigint   not null,
    revtype       smallint not null,
    uuid          UUID,
    user_id       bigint,
    name          character varying(255),
    Data          text,
    output_filter character varying(255),
    screen_id     bigint,
    version       bigint,
    primary key (rev, id),
    constraint fk_filter_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

create table branch_audit
(
    id                      bigint   not null,
    rev                     bigint   not null,
    revtype                 smallint not null,
    uuid                    UUID,
    original_filter_id      bigint,
    original_filter_version bigint,
    filter_id               bigint,
    version                 bigint,
    primary key (rev, id),
    constraint fk_branch_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

insert into users(uuid, name)
values ('4413fb3e-d8f9-41ad-ace6-18816fda1e68', 'user1'),
       ('7a2678dc-9402-4532-88bf-ff58459130db', 'user2');

insert into filter(uuid, user_id, name, Data, output_filter, screen_id, version)
values ('66b6049c-cf3d-4756-a350-e4170bbb9fd0', '1', 'filter1', 'data', 'outputfilter', null, 1),
       ('66b6049c-cf3d-4756-a350-e4170bbb9fd0', '1', 'filter1', 'data2', 'outputfilte2r', null, 2);
