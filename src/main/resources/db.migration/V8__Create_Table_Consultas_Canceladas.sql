create table consultas_canceladas(

                          id bigint not null auto_increment,
                          id_consulta bigint not null,
                          motivo varchar(255) not null,

                          primary key(id),
                          constraint fk_consultas_id foreign key(id_consulta) references consultas(id)

);