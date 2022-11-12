create table users
(
    id   SERIAL NOT NULL,
    uuid UUID   NOT NULL,
    name character varying(255),
    PRIMARY KEY (id)
);

create table screen
(
    id          SERIAL NOT NULL,
    uuid        UUID   NOT NULL,
    name        character varying(255),
    contentJson text,
    PRIMARY KEY (id)
);

create table filter
(
    id           SERIAL NOT NULL,
    uuid         UUID   NOT NULL,
    user_id      bigint NOT NULL,
    name         character varying(255),
    Data         text,
    outputFilter character varying(255),
    screen_id    bigint,
    PRIMARY KEY (id),
    CONSTRAINT fk_filter_user_id_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_filter_screen_id_screen_id FOREIGN KEY (screen_id) REFERENCES screen (id)
);

create table branch
(
    id                 serial NOT NULL,
    uuid               UUID   NOT NULL,
    original_filter_id bigint not null,
    filter_id          bigint not null,
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
    id          bigint   not null,
    rev         bigint   not null,
    revtype     smallint not null,
    uuid        UUID     NOT NULL,
    name        character varying(255),
    contentJson text,
    primary key (rev, id),
    constraint fk_screen_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

create table user_audit
(
    id      bigint   not null,
    rev     bigint   not null,
    revtype smallint not null,
    uuid    UUID     NOT NULL,
    name    character varying(255),
    primary key (rev, id),
    constraint fk_user_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

create table filter_audit
(
    id           bigint   not null,
    rev          bigint   not null,
    revtype      smallint not null,
    uuid         UUID     NOT NULL,
    user_id      bigint   NOT NULL,
    name         character varying(255),
    Data         text,
    outputFilter character varying(255),
    screen_id    bigint,
    primary key (rev, id),
    constraint fk_filter_audit_audit_info foreign key (rev) references audit_info (revision_id)
);

create table branch_audit
(
    id                 bigint   not null,
    rev                bigint   not null,
    revtype            smallint not null,
    uuid               UUID     NOT NULL,
    original_filter_id bigint   not null,
    filter_id          bigint   not null,
    primary key (rev, id),
    constraint fk_branch_audit_audit_info foreign key (rev) references audit_info (revision_id)
);
