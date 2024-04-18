create table clientes
(
    id    serial primary key,
    nome  text not null,
    email text not null unique,
    senha text not null,
    tipo  text default 'cliente'
)

create table lojista
(
    id    serial primary key,
    nome  text not null,
    email text not null unique,
    senha text not null,
    tipo  text default 'lojista'
)

create table produtos
(
    id        serial primary key,
    nome      text not null,
    descricao text not null,
    preco     int  not null,
    estoque   int  not null,

)
