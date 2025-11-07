/*
V1: this is the initial state of the game. 
It creates the characters tables and two core rows:
- Id 1: the player's active slot 
- Id 101: the "new game" template
*/

CREATE TABLE characters (
    id INT PRIMARY KEY, 
    name VARCHAR(50) NOT NULL,
    hp INT DEFAULT 100
);

/* Inserting the "Active Save" slot */
INSERT INTO characters (id, name, hp) 
VALUES (1, 'Hero', 100)
ON CONFLICT (id) DO NOTHING; /* ON CONFLICT is a good practice */

/* Inserting the "New Game Template" */
INSERT INTO characters (id, name, hp) 
VALUES (101, 'Hero Template', 100)
ON CONFLICT (id) DO NOTHING;