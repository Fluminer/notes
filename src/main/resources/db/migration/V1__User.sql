
    create table appuser(
        id bigserial primary key,
        username varchar(64) not null unique,
        password varchar(60) not null
    )