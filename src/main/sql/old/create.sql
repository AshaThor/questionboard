CREATE TABLE
	question
	(
		id INTEGER NOT NULL,
		name varchar NOT NULL,
		char_class varchar NOT NULL,
		race varchar NOT NULL,
		level INTEGER,
		player varchar NOT NULL
	);
CREATE TABLE
	hibernate_sequence
	(
		next_val INTEGER
	);
CREATE TABLE Ref_Ability
(
    id uuid DEFAULT uuid_generate_v4(),
    index question varying NOT NULL,
    name question varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE Ref_Ability
    OWNER to postgres;
COMMENT ON TABLE Ref_Ability
    IS 'A table to hold reference data for the skills in the RPG game';