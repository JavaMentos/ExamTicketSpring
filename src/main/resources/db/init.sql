CREATE TABLE public.telegram_users
(
    id         SERIAL PRIMARY KEY,
    user_id    BIGINT,
    user_name  VARCHAR(50),
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    local_date DATE,
    counter    INTEGER,
    CONSTRAINT unique_user_id UNIQUE (user_id)
);

CREATE TABLE public.exam_tickets
(
    id             SERIAL PRIMARY KEY,
    question_topic VARCHAR(100) NOT NULL,
    question       VARCHAR(150) NOT NULL,
    answer_1       VARCHAR(100) NOT NULL,
    answer_2       VARCHAR(100) NOT NULL,
    answer_3       VARCHAR(100) NOT NULL,
    answer_4       VARCHAR(100) NOT NULL,
    full_answer    VARCHAR(2500)
);