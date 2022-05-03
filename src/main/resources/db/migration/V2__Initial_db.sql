
create table message (
    id bigserial primary key,
    name varchar(64) references appuser (username),
    message varchar(1024)
)