
create table message (
    id bigserial primary key,
    name varchar(64) not null references appuser (username),
    message varchar(1024) not null
)