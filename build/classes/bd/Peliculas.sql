Drop database peliculas;
create database peliculas;
use peliculas;


create table peliculas(
id_peliculas int(10) auto_increment primary key,
nombre_pelicula char(50) not null,
genero char(25) not null,
precio_venta int(20) not null);