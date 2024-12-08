INSERT INTO planets (NAME, VERSION, CREATIONTIME, UPDATETIME)
VALUES
    ('TATTOINE',1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('BESPIN',1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ENDOR', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ALDERAAN', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('HOTH', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('DAGOBAH', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO questions (DESCRIPTION, SCORE, PLANET_ID, VERSION, CREATIONTIME, UPDATETIME)
VALUES
    ('C3PO appears for the first time in this world?', 1, 1,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Who buys C3PO and R2D2 in Tattoine?', 2, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Who assembled C3PO?', 3, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Who reassembled C3PO after the shooting in Bespin?', 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What happened to Han Solo while on Bespin?', 1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('From what planet did they evacuate before Bespin?', 2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('What species is native to the Forest Moon of Endor?', 1, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What special status do the Ewoks give C-3PO?', 2, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What is the relationship between the Forest Moon of Endor and the nearby Death Star II?', 3, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('What resource is Ilum best known for?', 1, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What role does Ilum play in Jedi training?', 2, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What is the name of the ceremony where Jedi younglings retrieve their kyber cristals from Ilum?', 3, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('What significant event took place on Polis Massa?', 1, 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Which of the following best describes Polis Massa`s terrain?', 2, 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What was the Polis Massans primary form of comunication', 3, 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What is Mandalore primarily known for in the galaxy?', 1, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What is the name of the dark weapon that symbolizes leadership among Mandalorians?', 2, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('What event is known as the “Great Purge” of Mandalore?', 3, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO answers (DESCRIPTION, CORRECT, QUESTION_ID, VERSION, CREATIONTIME, UPDATETIME)
VALUES

    ('True', true, 1,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('False', false, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Owen Lars and Luke Skywalker', true, 2,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Shmi', false, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('R2D2', false, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Obi-Wan Kenobi', false, 2,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Anakin Skywalker', true, 3,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Shmi', false, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Cliegg Lars', false, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Sand people', false, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Chewbacca', true, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('R2D2', false, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Han Solo', false, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Leia', false, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('He was frozen in carbonite', true, 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Stayed behind fighting stormtroopers', false, 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Hoth', true, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mandalore', false, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Aldeeran', false, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Dagobah', false, 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Ewoks', true, 7, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Jawas', false, 7, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Tusken Raiders', true, 7, 1,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Wookies', false, 7, 1,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('War leader', false, 8, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Village elder', false, 8, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Deity', true, 8, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Chieftain', false, 8, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Endor powers the Death Star II', false, 9, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Endor´s orbit is used to hide the Death Star II', true, 9, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Endor is used as a testing ground for Death Star weapons', false, 9, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Death Star II is stationed to protect Endor', false, 9, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Kyber Crystals', true, 10, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Spice', false, 10, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP ),
    ('Durasteel', false, 10,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Tibanna gas', false, 10,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('It is the location for the Jedi Academy', false, 11, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('It is where Jedi go to find kyber crystals for their lightsabers', true, 11, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('It is a testing ground for Jedi combat skills', false, 11, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('It is the site for Jedi Council meetings', false, 11, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('The Initiation', false, 12, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Gathering', true, 12, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Trial of Crystals', false, 12, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The rite of passage', false, 12, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('The signing of the Galactic Concordance', false, 13,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The birth of Luke and Leia', true, 13, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The construction of the death star', false, 13, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The execution of Order 66', false, 13, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Rocky asteroid with scattered ruins', true, 14,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lush green forests', false, 14,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Icy tundra', false, 14,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Volcanic Wastelands', false, 14,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Verbal Speech', false, 15, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Holographic signals', false, 15,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Telepathy', true, 15,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Binary Code', false, 15,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Its strong connection to te force', false, 16, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Its legendary warrior culture', true, 16,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Its advanced medical facilities', false, 16,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Its powerful navy', false, 16,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('Lightsaber of Mandalore', false, 17,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Darksaber', true, 17,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mandalorian Blade', false, 17,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Shadow Saber', false, 17,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('The Republic’s invasion of Mandalore', false, 18,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Empire’s assault that decimated the Mandalorians and seized their Beskar', true, 18,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Jedi’s attack to destroy Mandalorian warriors', false, 18,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('The Sith’s destruction of the Mandalorian homeworld', false, 18,1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO players (USERNAME, TOTALSCORE, VERSION, CREATIONTIME, UPDATETIME)
VALUES
    ('Pedro', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

