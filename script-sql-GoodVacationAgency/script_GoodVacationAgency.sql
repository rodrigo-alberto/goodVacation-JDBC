CREATE DATABASE IF NOT EXISTS bd_GoodVacationAgency;

USE bd_GoodVacationAgency;

CREATE TABLE IF NOT EXISTS userClient (
    idUser int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `password` varchar(20) NOT NULL,
    isClient boolean NOT NULL,
    profilePicture varchar(255) NOT NULL,
    acessLevel enum('adm', 'com') NOT NULL,	
    dateBirth date DEFAULT '2000/02/02',
    cpf varchar(14) DEFAULT "000.000.000-00",
    sex enum('M', 'F', 'I') DEFAULT 'I'
);

CREATE TABLE IF NOT EXISTS destiny (
    idDestiny int PRIMARY KEY AUTO_INCREMENT,
    `nameDestiny` varchar(50) NOT NULL,
    images varchar(255) NOT NULL,
    city varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS trip (
    idTrip int PRIMARY KEY AUTO_INCREMENT,
    departureDate datetime NOT NULL,
    arrivalDate datetime NOT NULL,
    travelPrice double NOT NULL,
    isPromotion boolean NOT NULL DEFAULT false,
    fk_destiny_idDestiny int NOT NULL,
	FOREIGN KEY (fk_destiny_idDestiny) REFERENCES destiny (idDestiny)
    ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS contact (
    idContact int PRIMARY KEY AUTO_INCREMENT,
    email varchar(254) NOT NULL,
    `subject` varchar(50) NOT NULL,
    message varchar(500) NOT NULL,
    messageTime datetime NOT NULL,
    fk_userClient_idUser int NOT NULL,
    FOREIGN KEY (fk_userClient_idUser) REFERENCES userClient (idUser)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS travelPackage (
	idPackage int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fk_trip_idTrip int NOT NULL,
    fk_userClient_idUser int NOT NULL,
    FOREIGN KEY (fk_trip_idTrip ) REFERENCES trip (idTrip) 
    ON DELETE RESTRICT,
    FOREIGN KEY (fk_userClient_idUser) REFERENCES userClient (idUser) 
	ON DELETE CASCADE
);

INSERT INTO userClient (`name`, `password`, isClient, profilePicture, acessLevel, dateBirth, cpf, sex) 
VALUES ("admin", "admin", true, "C:\Users\admin\Images\adminProfilePicture.png", 'adm', '2000/04/11', "123.456.789-00", 'I');

INSERT INTO destiny (`nameDestiny`, images, city) VALUES 
("Praia do porto", "C:\Users\admin\Images\imagemDaPraia.jpg", "Rio de janeiro"),
("Cachoeira do mosquito", "C:\Users\admin\Images\cachoeira-do-mosquito.png", "Bahia");

INSERT INTO trip (departureDate, arrivalDate, travelPrice, isPromotion, fk_destiny_idDestiny) VALUES 
('2023-10-14 08:30:00', '2023-11-12 17:00:00', 650.50, false, 1),
('2023-10-23 09:30:00', '2023-11-21 17:30:00', 450.25, true, 2),
('2023-11-01 08:30:00', '2023-11-25 17:00:00', 450.25, true, 2);