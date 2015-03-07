-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- ------------------------------ UserProfile ----------------------------------

DROP TABLE Compra CASCADE ;
DROP TABLE UserProfile CASCADE;
DROP TABLE Sesion CASCADE ;
DROP TABLE Pelicula CASCADE ;
DROP TABLE Sala CASCADE ;
DROP TABLE Cine CASCADE ;
DROP TABLE Provincia;

CREATE TABLE Provincia (
    idProvincia BIGINT NOT NULL AUTO_INCREMENT,
    nProvincia VARCHAR(30) NOT NULL,
    CONSTRAINT Provincia_PK PRIMARY KEY (idProvincia))ENGINE = InnoDB;



CREATE TABLE Cine (
    idCine BIGINT NOT NULL AUTO_INCREMENT,
    nCine VARCHAR(30) NOT NULL,
    precioEntrada FLOAT NOT NULL,
    idProvincia BIGINT NOT NULL,
    CONSTRAINT Cine_PK PRIMARY KEY (idCine),
    CONSTRAINT CineidProvinciaFK FOREIGN KEY(idProvincia) 
    	REFERENCES Provincia(idProvincia) )ENGINE = InnoDB;



CREATE TABLE UserProfile (
    usrId BIGINT NOT NULL AUTO_INCREMENT,
    loginName VARCHAR(30) COLLATE latin1_bin NOT NULL,
    enPassword VARCHAR(13) NOT NULL, 
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(40) NOT NULL, email VARCHAR(60) NOT NULL,
    tipo INT NOT NULL,
    CONSTRAINT UserProfile_PK PRIMARY KEY (usrId),
    CONSTRAINT LoginNameUniqueKey UNIQUE (loginName)) ENGINE = InnoDB;


CREATE TABLE Sala (
    idSala BIGINT NOT NULL AUTO_INCREMENT,
    nSala INT NOT NULL,
    capacidad INT NOT NULL,
    idCine BIGINT NOT NULL,
    CONSTRAINT Sala_PK PRIMARY KEY (idSala),
    CONSTRAINT SalaidCineFK FOREIGN KEY(idCine)
    REFERENCES Cine(idCine))ENGINE = InnoDB;


CREATE TABLE Pelicula (
    idPelicula BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(30) NOT NULL,
    resumen VARCHAR(60) NOT NULL,
    duracion INT  NOT NULL,
    fInicio TIMESTAMP  NOT NULL,
    fFin TIMESTAMP  NOT NULL,
    CONSTRAINT Pelicula_PK PRIMARY KEY (idPelicula) )
    	ENGINE = InnoDB;

CREATE TABLE Sesion (
    idSesion BIGINT NOT NULL AUTO_INCREMENT,
    idPelicula BIGINT NOT NULL,
    idSala BIGINT NOT NULL,
    precio FLOAT NOT NULL,
    horayFecha TIMESTAMP DEFAULT 0 NOT NULL,
    nAsistentes FLOAT NOT NULL,
    version INT NOT NULL,
	CONSTRAINT Sesion_PK PRIMARY KEY (idSesion),
	CONSTRAINT SesionidPeliculaFK FOREIGN KEY(idPelicula) 
		REFERENCES Pelicula(idPelicula),
	CONSTRAINT SesionidSalaFK FOREIGN KEY(idSala) 
		REFERENCES Sala(idSala))ENGINE = InnoDB;
    
CREATE TABLE Compra (
    idCompra BIGINT NOT NULL AUTO_INCREMENT,
    numLocalidades INT NOT NULL,
    idSesion BIGINT NOT NULL,
    usrId BIGINT NOT NULL,
    numTarjeta VARCHAR(16) NOT NULL,
    fExpiracion TIMESTAMP  NOT NULL,
    fechaCompra TIMESTAMP NOT NULL,
    entregada TINYINT(1) NOT NULL,
    CONSTRAINT Compra_PK PRIMARY KEY (idCompra),
    CONSTRAINT SesionIdFK FOREIGN KEY(idSesion) 
    REFERENCES Sesion(idSesion), 
    CONSTRAINT UsrIdFK FOREIGN KEY(usrId) 
    REFERENCES UserProfile(usrId)) ENGINE = InnoDB;


