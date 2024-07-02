create table file_info(
    id                 uuid primary key,
    file_name          varchar not null,
    path               varchar not null,
    file_size          bigint not null,
    processed_time     timestamp with time zone not null,
    last_modified_time timestamp with time zone not null,
    unique (path, file_name, last_modified_time)
);
