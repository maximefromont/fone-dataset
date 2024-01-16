/* Note : if not already done, Create database manually with the name fone-database before running this script */

DROP TABLE IF EXISTS teamed;
DROP TABLE IF EXISTS earned;
DROP TABLE IF EXISTS constructor;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS race;

CREATE TABLE driver
(
    idDriver          SERIAL PRIMARY KEY,
    lastnameDriver    VARCHAR(250) NOT NULL,
    firstnameDriver   VARCHAR(250) NOT NULL,
    nationalityDriver VARCHAR(250) NOT NULL
);

CREATE TABLE constructor
(
    idConstructor          SERIAL PRIMARY KEY,
    nameConstructor        VARCHAR(250) NOT NULL,
    nationalityConstructor VARCHAR(250) NOT NULL,
    UNIQUE (nameConstructor)
);

/*
CREATE TABLE Standing(
                         idStanding INT,
                         pointsStanding INT NOT NULL,
                         yearStanding VARCHAR(50) NOT NULL,
                         PRIMARY KEY(idStanding)
);
*/

CREATE TABLE race
(
    idRace              SERIAL PRIMARY KEY,
    cityLocationRace    VARCHAR(250) NOT NULL UNIQUE,
    countryLocationRace VARCHAR(250) NOT NULL
);

CREATE TABLE teamed
(
    idDriver      INT,
    idConstructor INT,
    yearTeamed    VARCHAR(50) NOT NULL,
    PRIMARY KEY (idDriver, idConstructor),
    FOREIGN KEY (idDriver) REFERENCES Driver (idDriver),
    FOREIGN KEY (idConstructor) REFERENCES Constructor (idConstructor)
);

/*
CREATE TABLE Stood(
                      idDriver INT,
                      idStanding INT,
                      PRIMARY KEY(idDriver, idStanding),
                      FOREIGN KEY(idDriver) REFERENCES Driver(idDriver),
                      FOREIGN KEY(idStanding) REFERENCES Standing(idStanding)
);
*/

CREATE TABLE earned
(
    idDriver     INT,
    idRace       INT,
    pointsEarned INT         NOT NULL,
    yearEarned   VARCHAR(50) NOT NULL,
    PRIMARY KEY (idDriver, idRace),
    FOREIGN KEY (idDriver) REFERENCES Driver (idDriver),
    FOREIGN KEY (idRace) REFERENCES Race (idRace)
);