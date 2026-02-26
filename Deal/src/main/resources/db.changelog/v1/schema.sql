create table clients (
    client_id UUID primary key,
    last_name text not null,
    first_name text not null,
    middle_name text,
    birth_date date not null,
    email text,
    gender text,
    marital_status text,
    dependent_amount int,
    passport_id jsonb,
    employment_id jsonb,
    account_number text
);
GO

create table credits (
    credit_id UUID primary key,
    amount decimal(22, 2),
    term int not null,
    monthly_payment decimal(22, 2),
    rate decimal(22, 2),
    psk decimal(22, 2),
    payment_schedule jsonb,
    insurance_enabled boolean,
    salary_client boolean,
    credit_status text
);
GO

create table statements (
    statement_id UUID primary key,
    client_id UUID,
    credit_id UUID,
    application_status text,
    creation_date timestamp,
    applied_offer text,
    sign_date timestamp,
    ses_code text,
    status_history jsonb
);
GO

alter table statements
add constraint FK_statemets_clients foreign key(client_id) references clients(client_id);
GO

alter table statements
add constraint FK_statemets_credits foreign key(credit_id) references credits(credit_id);