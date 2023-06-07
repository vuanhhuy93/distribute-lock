create table user_balance
(
    user_id         integer                      not null
        primary key,
    user_balance    double precision default 0.0 not null,
    reverse_balance double precision default 0.0 not null,
    version         double precision default 1.0 not null
);

alter table user_balance
    owner to postgres;

create table user_balance_transaction
(
    id          serial
        primary key,
    user_id     integer                        not null,
    action_type integer          default 1     not null,
    amount      double precision default 0.0   not null,
    status      integer          default 1     not null,
    created_at  timestamp        default now(),
    updated_at  timestamp        default now() not null
);

alter table user_balance_transaction
    owner to postgres;

create table product
(
    id           serial
        primary key,
    product_name varchar(200)                 not null,
    price        double precision default 0.0 not null
);

alter table product
    owner to postgres;

create table warehouse
(
    product_id     integer           not null
        constraint store_pkey
            primary key,
    total          integer default 0 not null,
    active_number  integer default 0 not null,
    reverse_number integer default 0 not null
);

alter table warehouse
    owner to postgres;

create table "order"
(
    id             serial
        primary key,
    product_id     integer                      not null,
    total          integer          default 0   not null,
    amount         double precision default 0.0 not null,
    user_id        integer                      not null,
    status         integer          default 1   not null,
    transaction_id bigint           default 0   not null,
    version        double precision default 1.0 not null
);

alter table "order"
    owner to postgres;

