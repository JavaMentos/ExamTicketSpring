create table public.telegram_users
(
    id         integer default nextval('users_id_seq'::regclass) not null
        constraint users_pkey
            primary key,
    user_id    bigint
        constraint users_user_id_key
            unique,
    user_name  varchar,
    first_name varchar,
    last_name  varchar,
    local_date date,
    counter    integer
);

create table public.exam_tickets
(
    id             serial
        primary key,
    question_topic varchar not null,
    question       varchar not null,
    answer_1       varchar not null,
    answer_2       varchar not null,
    answer_3       varchar not null,
    answer_4       varchar not null,
    right_answer   varchar not null,
    full_answer    varchar(2500),
    correct_answer integer
);

create trigger update_correct_answer_trigger
    before insert
    on public.exam_tickets
    for each row
    execute procedure public.update_correct_answer();

