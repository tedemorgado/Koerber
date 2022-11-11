create table users
(
    id UUID NOT NULL,
    name character varying(255) ,
    PRIMARY KEY (id)
);

create table screen
(
    id UUID NOT NULL,
    name character varying(255),
    contentJson text,
    PRIMARY KEY (id)
);

create table myfilter
(
    id UUID NOT NULL,
    user_id bigint NOT NULL,
    name character varying(255),
    Data text,
    outputFilter character varying(255),
    screen_id UUID,
    PRIMARY KEY (id)
);
