CREATE TABLE Driver
(
    id_driver          SERIAL PRIMARY KEY,
    lastname_driver    VARCHAR(250) NOT NULL,
    firstname_driver   VARCHAR(250) NOT NULL,
    nationality_driver VARCHAR(250) NOT NULL
);

CREATE TABLE Constructor
(
    id_constructor          SERIAL PRIMARY KEY,
    name_constructor        VARCHAR(250) NOT NULL,
    nationality_constructor VARCHAR(250) NOT NULL,
    UNIQUE (name_constructor)
);

/*
CREATE TABLE Standing(
                         id_standing INT,
                         points_standing INT NOT NULL,
                         year_standing VARCHAR(50) NOT NULL,
                         PRIMARY KEY(id_standing)
);
*/

CREATE TABLE Race
(
    id_race       SERIAL PRIMARY KEY,
    city_location_race VARCHAR(250) NOT NULL UNIQUE,
    country_location_race VARCHAR(250) NOT NULL
);

CREATE TABLE Teamed
(
    id_driver      INT,
    id_constructor INT,
    year_teamed    VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_driver, id_constructor),
    FOREIGN KEY (id_driver) REFERENCES Driver (id_driver),
    FOREIGN KEY (id_constructor) REFERENCES Constructor (id_constructor)
);

/*
CREATE TABLE Stood(
                      id_driver INT,
                      id_standing INT,
                      PRIMARY KEY(id_driver, id_standing),
                      FOREIGN KEY(id_driver) REFERENCES Driver(id_driver),
                      FOREIGN KEY(id_standing) REFERENCES Standing(id_standing)
);
*/

CREATE TABLE Earned
(
    id_driver     INT,
    id_race       INT,
    points_earned INT         NOT NULL,
    year_earned   VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_driver, id_race),
    FOREIGN KEY (id_driver) REFERENCES Driver (id_driver),
    FOREIGN KEY (id_race) REFERENCES Race (id_race)
);