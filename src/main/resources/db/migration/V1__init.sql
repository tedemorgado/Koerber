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
    id                      serial NOT NULL,
    uuid                    UUID   NOT NULL,
    original_filter_id      bigint not null,
    original_filter_version bigint not null,
    filter_id               bigint not null,
    primary key (id),
    CONSTRAINT fk_branch_original_filter_id_filter_id FOREIGN KEY (original_filter_id) REFERENCES filter (id),
    CONSTRAINT fk_branch_filter_id_filter_id FOREIGN KEY (filter_id) REFERENCES filter (id)
);

insert into users(uuid, name)
values ('4413fb3e-d8f9-41ad-ace6-18816fda1e68', 'user1'),
       ('7a2678dc-9402-4532-88bf-ff58459130db', 'user2');

insert into filter(uuid, user_id, name, Data, output_filter, screen_id, version)
values ('66b6049c-cf3d-4756-a350-e4170bbb9fd0', '1', 'filter1', 'data', 'outputfilter', null, 1),
       ('45262bd8-3870-430e-b004-4cca08265894', '1', 'filter2', 'data2', 'outputfilte2r', null, 1),
       ('f693c0e1-ef4c-4580-995c-96b136e299fb', '2', 'filter3', 'data2', 'outputfilter3', null, 1),
       ('18f34399-decc-4955-98b8-4774856aeb31', '2', 'This is a filter branch', 'data2', 'outputfilterbranch', null, 1);

insert into branch(uuid, original_filter_id, original_filter_version, filter_id)
values ('42bd549a-d066-4330-a6a5-e5855c79ccc1', 3, 1, 1);
