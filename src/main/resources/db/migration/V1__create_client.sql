CREATE TABLE client (
    id bigint NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL,
    offset_hours int4 NOT NULL,
    next_invocation timestamptz NOT NULL
);