BEGIN TRANSACTION;
DROP TABLE IF EXISTS 'USER';
CREATE TABLE IF NOT EXISTS 'USER'
(
    'id_user'       INTEGER             NOT NULL PRIMARY KEY AUTOINCREMENT,
    'name'          VARCHAR(255)        NOT NULL,
    'last_name'     VARCHAR(255)        NOT NULL,
    'mail_address'  VARCHAR(255) UNIQUE NOT NULL,
    'password'      VARCHAR(255)        NOT NULL,
    'max_pompe'     INTEGER             NOT NULL,
    'max_traction'  INTEGER             NOT NULL,
    'max_abdo'      INTEGER             NOT NULL,
    'max_km_course' INTEGER             NOT NULL,
    'taille'        FLOAT             NOT NULL,
    'poids'         FLOAT               NOT NULL,
    'NightMode'     BOOLEAN             DEFAULT FALSE,
    'Language'      VARCHAR(255)        DEFAULT 'fr',
    'lenght_unit' BOOLEAN DEFAULT TRUE,
    'mass_unit' BOOLEAN DEFAULT TRUE
);

DROP TABLE IF EXISTS 'MUSCLE';
CREATE TABLE IF NOT EXISTS 'MUSCLE'
(
    'id_muscle' INTEGER             NOT NULL PRIMARY KEY AUTOINCREMENT,
    'name'      VARCHAR(255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS 'EXERCICE';
CREATE TABLE IF NOT EXISTS 'EXERCICE'
(
    'id_exercice'       INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
    'user_id'           INTEGER,
    'name'              VARCHAR(255) NOT NULL,
    'difficulty'        INTEGER      NOT NULL,
    'type'              VARCHAR(255) NOT NULL,
    'description'       TEXT         NOT NULL,
    'image_name'        VARCHAR(255),
    'image_data'        LONGBLOB     NOT NULL,
    'exercise_duration' INTEGER,
    'calories'          INTEGER,
    CONSTRAINT uq_name_user UNIQUE (name, user_id)
);

DROP TABLE IF EXISTS 'EXERCICE_MUSCLE';
CREATE TABLE IF NOT EXISTS 'EXERCICE_MUSCLE'
(
    'id_muscle'   INTEGER NOT NULL,
    'id_exercice' INTEGER NOT NULL,
    PRIMARY KEY (id_muscle, id_exercice),
    FOREIGN KEY (id_muscle) REFERENCES MUSCLE (id_muscle),
    FOREIGN KEY (id_exercice) REFERENCES EXERCICE (id_exercice)
);

DROP TABLE IF EXISTS 'PROGRAMME';
CREATE TABLE IF NOT EXISTS 'PROGRAMME'
(
    'id_programme' INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
    'name'         VARCHAR(255) NOT NULL,
    'description'  VARCHAR(255) NOT NULL,
    'difficulty'   INTEGER DEFAULT 0,
    'user_id'      INTEGER      NOT NULL,
    CONSTRAINT uq_name_user UNIQUE (name, user_id)
);

DROP TABLE IF EXISTS 'EXERCICE_PROGRAMME';
CREATE TABLE IF NOT EXISTS 'EXERCICE_PROGRAMME'
(
    'id_exercice_programme' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    'id_programme'          INTEGER NOT NULL,
    'id_exercice'           INTEGER NOT NULL,
    'position'              INTEGER NOT NULL,
    FOREIGN KEY (id_programme) REFERENCES PROGRAMME (id_programme),
    FOREIGN KEY (id_exercice) REFERENCES EXERCICE (id_exercice)
);

COMMIT;
