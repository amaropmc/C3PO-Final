CREATE TABLE answers
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    version      INTEGER,
    creationTime TIMESTAMP WITHOUT TIME ZONE,
    updateTime   TIMESTAMP WITHOUT TIME ZONE,
    description  VARCHAR(255),
    correct      BOOLEAN                                 NOT NULL,
    question_id  BIGINT,
    CONSTRAINT pk_answers PRIMARY KEY (id)
);

CREATE TABLE planets
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    version      INTEGER,
    creationTime TIMESTAMP WITHOUT TIME ZONE,
    updateTime   TIMESTAMP WITHOUT TIME ZONE,
    name         VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_planets PRIMARY KEY (id)
);

CREATE TABLE players
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    version      INTEGER,
    creationTime TIMESTAMP WITHOUT TIME ZONE,
    updateTime   TIMESTAMP WITHOUT TIME ZONE,
    username     VARCHAR(255)                            NOT NULL,
    totalScore   INTEGER,
    CONSTRAINT pk_players PRIMARY KEY (id)
);

CREATE TABLE questions
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    version      INTEGER,
    creationTime TIMESTAMP WITHOUT TIME ZONE,
    updateTime   TIMESTAMP WITHOUT TIME ZONE,
    description  VARCHAR(255),
    score        INTEGER,
    planet_id    BIGINT,
    CONSTRAINT pk_questions PRIMARY KEY (id)
);

ALTER TABLE players
    ADD CONSTRAINT uc_players_username UNIQUE (username);

ALTER TABLE answers
    ADD CONSTRAINT FK_ANSWERS_ON_QUESTION FOREIGN KEY (question_id) REFERENCES questions (id);

ALTER TABLE questions
    ADD CONSTRAINT FK_QUESTIONS_ON_PLANET FOREIGN KEY (planet_id) REFERENCES planets (id);