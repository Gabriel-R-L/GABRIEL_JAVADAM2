-- Active: 1697553361595@@127.0.0.1@3307@bdmiscelaneadam2

################################
# CREACIÓN DE LA BASE DE DATOS #
################################
DROP DATABASE IF EXISTS BDMISCELANEADAM2;
CREATE DATABASE BDMISCELANEADAM2;
USE BDMISCELANEADAM2;

#########################
# CREACIÓNDE LAS TABLAS #
#########################
DROP TABLE IF EXISTS usuarios;

CREATE TABLE
    usuarios(
        idUsuario INT NOT NULL AUTO_INCREMENT,
        nombre VARCHAR(30),
        apellido1 VARCHAR(45) NOT NULL,
        apellido2 VARCHAR(45) NOT NULL,
        dni CHAR(9) NOT NULL,
        alta INT NOT NULL DEFAULT 1,
        baja INT NOT NULL DEFAULT 0,
        PRIMARY KEY(idUsuario)
    );

DROP TABLE IF EXISTS cajas_supermercado;

CREATE TABLE
    cajas_supermercado (
        id SMALLINT NOT NULL AUTO_INCREMENT,
        fecha VARCHAR(15) NOT NULL,
        beneficioCaja1 FLOAT NOT NULL,
        beneficioCaja2 FLOAT NOT NULL,
        beneficioCaja3 FLOAT NOT NULL,
        beneficioCaja4 FLOAT NOT NULL,
        totalDineroCajas FLOAT NOT NULL,
        clientesTotales INT NOT NULL,
        PRIMARY KEY(id)
    );

##################
# INSERTAR DATOS #
##################
INSERT INTO
    usuarios(
        nombre,
        apellido1,
        apellido2,
        dni
    )
VALUES (
        'marcos',
        'garcia',
        'lopez',
        '23467123P'
    );

INSERT INTO
    usuarios(
        nombre,
        apellido1,
        apellido2,
        dni
    )
VALUES (
        'vix',
        'garcia',
        'dominguez',
        '23467223V'
    );

INSERT INTO
    usuarios(
        nombre,
        apellido1,
        apellido2,
        dni
    )
VALUES (
        'marcos',
        'jona',
        'asd',
        '23464121J'
    );

INSERT INTO
    usuarios(nombre, apellido1, apellido2, dni)
VALUES (
        'mario',
        'del',
        'campo',
        '23423151M'
    );

INSERT INTO
    usuarios(nombre, apellido1, apellido2, dni)
VALUES (
        'alejandro',
        'gonzalez',
        'gonzalez',
        '23433151W'
    );

#############
# BÚSQUEDAS #
#############
SELECT * FROM usuarios;
SELECT * FROM cajas_supermercado; 
SELECT fecha FROM cajas_supermercado; 

SELECT DISTINCT MONTH(fecha) as 'mes_fecha', YEAR(fecha) as 'anyo_fecha' FROM cajas_supermercado WHERE fecha IS NOT NULL;
