-- Skript for å opprette databasen og legge inn litt data
    -- Skjema = obligatorisk3
        -- Tabell(er) = ansatt, avdeling, prosjektDeltaker, prosjekt 

-- MERK!!! DROP SCHEMA ... CASCADE sletter alt !!!
DROP SCHEMA IF EXISTS obligatorisk3 CASCADE;

CREATE SCHEMA obligatorisk3;
SET search_path TO obligatorisk3;
    
-- Ikke nødvendig å slette tabellen(e) siden vi har tomt skjema, men ...
-- DROP TABLE ansatt, avdeling, prosjektDeltaker, prosjekt;

CREATE TABLE ansatt
(
    ansatt_id SERIAL PRIMARY KEY,
    brukernavn CHAR(4) NOT NULL,
    fornavn VARCHAR(50),
    etternavn VARCHAR(50),
    ans_dato DATE,
    stilling VARCHAR(50),
    mnd_lonn FLOAT,
    avd_fk INTEGER
);

CREATE TABLE avdeling
(
    avd_id SERIAL NOT NULL,
    navn VARCHAR(50) NOT NULL,
    sjef INTEGER,
    CONSTRAINT avd_pk PRIMARY KEY (avd_id)
);

CREATE TABLE prosjekt
(
    prosjekt_id SERIAL NOT NULL,
    navn VARCHAR(50) NOT NULL,
    beskrivelse VARCHAR(150),
    CONSTRAINT prosjekt_pk PRIMARY KEY (prosjekt_id)
);

CREATE TABLE prosjektDeltaker
(
    deltaker_id SERIAL NOT NULL,
    rolle VARCHAR(50) NOT NULL,
    timer FLOAT,
    ansatt_fk INTEGER NOT NULL,
    prosjekt_fk INTEGER NOT NULL, 
    CONSTRAINT deltaker_pk PRIMARY KEY (deltaker_id)
);

INSERT INTO
  ansatt(brukernavn, fornavn, etternavn, ans_dato, stilling, mnd_lonn, avd_fk)
VALUES
    ('NKRI', 'Nora', 'Kristiansen', '2000-07-17', 'Frilanseren', 50000, 3),
    ('SKS', 'Siri', 'Kaarvik Slyk', '2001-03-10', 'Macekspert', 50000, 2),
    ('ASAE', 'Aurora', 'Sætran', '1800-08-20', 'Hundegal', 50000, 1),
    ('MEJE', 'Marie', 'Eun Jee Eide', '2005-10-20', 'Sjef', 50000, 2),
    ('OBS', 'Oda', 'Bastesen Storebø', '2010-04-20', 'Iskaffesluker', 50000, 3);

    
INSERT INTO
  avdeling(navn, sjef)
VALUES
    ('Agilitybanen', 3),
    ('Økonomiavd', 4),
    ('Kantina', 1);


ALTER TABLE ansatt
    ADD CONSTRAINT avd_fk FOREIGN KEY (avd_fk) REFERENCES avdeling(avd_id);

ALTER TABLE avdeling
    ADD CONSTRAINT sjef FOREIGN KEY (sjef) REFERENCES ansatt(ansatt_id);

ALTER TABLE prosjektDeltaker
    ADD CONSTRAINT ansatt_fk FOREIGN KEY (ansatt_fk) REFERENCES ansatt(ansatt_id),
    ADD CONSTRAINT prosjekt_fk FOREIGN KEY (prosjekt_fk) REFERENCES prosjekt(prosjekt_id);


INSERT INTO
  prosjekt(navn, beskrivelse)
VALUES
    ('Lage database', 'Sette opp en fungerende database til innlevering'),
    ('Løse matteoppgaver', 'Løse oppgaver til innlevering i matte'),
    ('Effektivisere algoritmer', 'Lage sorteringsalgoritmer som er mer effektiv');


INSERT INTO
  prosjektDeltaker(rolle, timer, ansatt_fk, prosjekt_fk)
VALUES
    ('Mattewhiz', 2, 1, 2),
    ('Programmør', 4, 2, 1),
    ('Designer', 3, 3, 1),
    ('Programmør', 6, 4, 3),
    ('Korrekturleser', 2, 5, 2);
