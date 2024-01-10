CREATE TABLE Driver(
                       id_driver INT,
                       lastname_driver VARCHAR(50) NOT NULL,
                       firstname_driver VARCHAR(50) NOT NULL,
                       PRIMARY KEY(id_driver)
);

CREATE TABLE Constructor(
                            id_constructor INT,
                            name_constructor VARCHAR(250) NOT NULL,
                            PRIMARY KEY(id_constructor),
                            UNIQUE(name_constructor)
);

/*
CREATE TABLE Standing(
                         id_standing INT,
                         points_standing INT NOT NULL,
                         year_standing VARCHAR(50) NOT NULL,
                         PRIMARY KEY(id_standing)
);
*/

CREATE TABLE Race(
                     id_race INT,
                     location_race VARCHAR(250) NOT NULL,
                     PRIMARY KEY(id_race),
                     UNIQUE(location_race)
);

CREATE TABLE Teamed(
                       id_driver INT,
                       id_constructor INT,
                       year_teamed VARCHAR(50) NOT NULL,
                       PRIMARY KEY(id_driver, id_constructor),
                       FOREIGN KEY(id_driver) REFERENCES Driver(id_driver),
                       FOREIGN KEY(id_constructor) REFERENCES Constructor(id_constructor)
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

CREATE TABLE Earned(
                       id_driver INT,
                       id_race INT,
                       points_earned INT NOT NULL,
                       year_earned VARCHAR(50) NOT NULL,
                       PRIMARY KEY(id_driver, id_race),
                       FOREIGN KEY(id_driver) REFERENCES Driver(id_driver),
                       FOREIGN KEY(id_race) REFERENCES Race(id_race)
);