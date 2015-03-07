-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pojo" database.
-------------------------------------------------------------------------------

INSERT INTO Provincia 
VALUES(1,'LUGO');

INSERT INTO Provincia 
VALUES(2,'PONTEVEDRA');

INSERT INTO Provincia 
VALUES(3,'ASTURIAS');

INSERT INTO Provincia 
VALUES(4,'LEON');

-------------------------------------------------------------------------------

INSERT INTO Cine 
VALUES(1,'CINESA LUGO',6.5,1);

INSERT INTO Cine 
VALUES(2,'FILMAX PONTEVEDRA',7.5,2);

INSERT INTO Cine 
VALUES(3,'YELMO CINEPLEX GIJON',5,3);

INSERT INTO Cine 
VALUES(4,'CINES IAGO LEON',6.5,4);

-------------------------------------------------------------------------------


INSERT INTO UserProfile
VALUES(1,'ADMIN','XO3gubXut24kI','ADMIN','ADMIN','admin@udc.es',1);

INSERT INTO UserProfile
VALUES(2,'TAQUILLERO','XO3gubXut24kI','TAQUILLERO','TAQUILLERO','taq@udc.es',0);

INSERT INTO UserProfile
VALUES(3,'JLOPEZ','XO3gubXut24kI','JESUS','LOPEZ','jlopez@udc.es',0);

INSERT INTO UserProfile
VALUES(4,'EMOSQUEIRA','XO3gubXut24kI','EDUARDO','MOSQUEIRA','emosqueira@udc.es',2);

INSERT INTO UserProfile
VALUES(5,'DCABRERO','XO3gubXut24kI','DAVID','CABRERO','dcabrero@udc.es',2);

-------------------------------------------------------------------------------

INSERT INTO Sala
VALUES(1,1,10,1);

INSERT INTO Sala
VALUES(2,2,120,1);

INSERT INTO Sala
VALUES(3,3,200,1);

INSERT INTO Sala
VALUES(4,1,50,2);

INSERT INTO Sala
VALUES(5,1,300,3);

INSERT INTO Sala
VALUES(6,2,250,3);

INSERT INTO Sala
VALUES(7,1,250,4);

-------------------------------------------------------------------------------


INSERT INTO Pelicula
VALUES(1,'SNATCH','Resumen de Snatch',127,'2014-04-10 00:00:01','2015-06-28 00:00:01');

INSERT INTO Pelicula
VALUES(2,'EL LOBO DE WALL STREET','Resumen de El Lobo de Wall Street',165,'2014-04-14 00:00:01','2015-06-27 00:00:01');

INSERT INTO Pelicula
VALUES(3,'ROCKNROLLA','Resumen de la pelicula Rocknrolla',142,'2014-03-03 00:00:01','2014-07-28 00:00:01');

INSERT INTO Pelicula
VALUES(4,'AMERICAN HISTORY X','Resumen de la pelicula American History X',136,'2014-02-27 00:00:01','2014-07-23 00:00:01');


-------------------------------------------------------------------------------

-- now + 3 DAY
INSERT INTO Sesion
VALUES (1,1,1,5.50,(SELECT DATE_ADD(NOW(), INTERVAL 3 DAY)), 0, 0);

-- now + 1 DAY + 4 HOURS
INSERT INTO Sesion
VALUES (2,1,1,5.50,(SELECT DATE_ADD( (SELECT DATE_ADD(NOW(), INTERVAL 4 HOUR)) ,INTERVAL 1 DAY)), 0, 0);

-- now + 12 HOURS
INSERT INTO Sesion
VALUES (3,1,1,5.50,(SELECT DATE_ADD(NOW(), INTERVAL 12 HOUR)), 0, 0);

-- now 
INSERT INTO Sesion
VALUES (4,2,2,5.50,(SELECT NOW()), 0, 0);

-- now + 2 HOURS
INSERT INTO Sesion
VALUES (4,2,2,5.50,(SELECT DATE_ADD(NOW(), INTERVAL 2 HOUR)), 0, 0);

-- now + 4 HOURS
INSERT INTO Sesion
VALUES (4,2,2,5.50,(SELECT DATE_ADD(NOW(), INTERVAL 4 HOUR)), 0, 0);

-- now + 1 DAY + 3 HOURS
INSERT INTO Sesion
VALUES (5,2,2,5.50,(SELECT DATE_ADD( (SELECT DATE_ADD(NOW(), INTERVAL 3 HOUR)) ,INTERVAL 1 DAY)), 0, 0);

-- now + 1 DAY + 10 HOURS
INSERT INTO Sesion
VALUES (6,3,3,5.50,(SELECT DATE_ADD( (SELECT DATE_ADD(NOW(), INTERVAL 10 HOUR)) ,INTERVAL 1 DAY)), 0, 0);

-- now + 2 DAY + 6 HOURS
INSERT INTO Sesion
VALUES (7,4,7,5.50,(SELECT DATE_ADD( (SELECT DATE_ADD(NOW(), INTERVAL 6 HOUR)) ,INTERVAL 2 DAY)), 0, 0);

-- now + 3 DAY + 1 HOURS
INSERT INTO Sesion
VALUES (8,4,6,5.50,(SELECT DATE_ADD( (SELECT DATE_ADD(NOW(), INTERVAL 1 HOUR)) ,INTERVAL 3 DAY)), 0, 0);

-- Tomorrow 2:00
INSERT INTO Sesion
VALUES (9,2,4,5.50,(select CONCAT( (SELECT ADDDATE( (SELECT CURDATE()), INTERVAL 1 DAY)),' 02:00:00')), 0, 0);

-------------------------------------------------------------------------------


INSERT INTO Compra
VALUES(1,2,1,4,'1111222233334444','2014-04-03 00:00:01','2014-04-01 00:00:01',1);


INSERT INTO Compra
VALUES(2,10,2,4,'1111222233334444','2014-04-04 00:01:01','2014-04-02 00:01:01',1);


INSERT INTO Compra
VALUES(3,2,3,4,'1111222233334444','2014-04-05 22:20:31','2014-04-03 22:20:31',1);


INSERT INTO Compra
VALUES(4,1,5,4,'1111222233334444','2014-04-12 00:00:01','2014-04-10 00:00:01',0);


INSERT INTO Compra
VALUES(5,8,1,5,'1111222233334445','2014-04-03 00:00:01','2014-04-01 00:00:01',1);


INSERT INTO Compra
VALUES(6,7,2,5,'1111222233334445','2014-04-10 16:07:54','2014-04-08 16:07:54',0);


INSERT INTO Compra
VALUES(7,7,9,5,'1111222233334445','2014-04-10 16:07:54','2014-04-08 16:07:54',0);

