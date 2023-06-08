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

CREATE OR REPLACE FUNCTION user_db_v2.updateReverseNumberOnWarehouse(productId integer, updateNumber integer)
RETURNS user_db_v2.warehouse AS $$
DECLARE
product_warehouse user_db_v2.warehouse;
BEGIN
SELECT * INTO product_warehouse
FROM user_db_v2.warehouse
WHERE product_id = productId
    FOR UPDATE;

product_warehouse.active_number := product_warehouse.active_number - updateNumber;
    product_warehouse.reverse_number := product_warehouse.reverse_number + updateNumber;
    if product_warehouse.active_number < 0 THEN

        return null;
end if;

       if product_warehouse.reverse_number < 0 THEN

        return null;
end if;
UPDATE user_db_v2.warehouse
SET reverse_number = product_warehouse.reverse_number,
    active_number = product_warehouse.active_number
WHERE product_id = productId;
RETURN product_warehouse;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION user_db_v2.updateReverseNumberAndTotalOnWarehouse(productId integer, updateNumber integer)
RETURNS user_db_v2.warehouse AS $$
DECLARE
product_warehouse user_db_v2.warehouse;
BEGIN

       if updateNumber < 0 THEN

        return null;
end if;
SELECT * INTO product_warehouse
FROM user_db_v2.warehouse
WHERE product_id = productId
    FOR UPDATE;

product_warehouse.total := product_warehouse.total - updateNumber;
    product_warehouse.reverse_number := product_warehouse.reverse_number - updateNumber;
    if product_warehouse.total < 0 THEN

        return null;
end if;

       if product_warehouse.reverse_number < 0 THEN

        return null;
end if;
UPDATE user_db_v2.warehouse
SET reverse_number = product_warehouse.reverse_number,
    total = product_warehouse.total
WHERE product_id = productId;
RETURN product_warehouse;
END;
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION user_db_v2.updateUserBalance(userId integer, activeNumberChange double precision, reverseNumberChange double precision)
RETURNS user_db_v2.user_balance AS $$
DECLARE
user_balance user_db_v2.user_balance;
BEGIN

       if userId < 1 THEN

        return null;
end if;
SELECT * INTO user_balance
FROM user_db_v2.user_balance
WHERE user_id = userId
    FOR UPDATE;

user_balance.reverse_balance := user_balance.reverse_balance +  reverseNumberChange;
    user_balance.user_balance := user_balance.user_balance + activeNumberChange;
    if user_balance.reverse_balance < 0 THEN

        return null;
end if;

       if user_balance.user_balance < 0 THEN

        return null;
end if;
UPDATE user_db_v2.user_balance
SET user_balance = user_balance.user_balance,
    reverse_balance = user_balance.reverse_balance,
    version = version + 1
WHERE user_id = userId;
RETURN user_balance;
END;
$$ LANGUAGE plpgsql;