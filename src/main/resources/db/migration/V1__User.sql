
    create table appuser(
        id bigserial primary key,
        username varchar(64) unique,
        password varchar(60)
    )