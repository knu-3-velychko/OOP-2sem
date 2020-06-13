-- ddl
CREATE TABLE galaxies
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE planets
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(30),
    radius      FLOAT,
    temperature FLOAT,
    has_life    BOOLEAN,
    galaxy_id   INT REFERENCES galaxies (id)
);

CREATE TABLE satelites
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR (30),
    radius    FLOAT,
    distance  FLOAT,
    planet_id INT REFERENCES planets (id)
);