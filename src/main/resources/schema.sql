CREATE TABLE record(
    id LONG PRIMARY KEY AUTO_INCREMENT,
    country VARCHAR(30),
    continent VARCHAR(30),
    gamesPlayed int,
    gamesWon    int,
    gamesDrawn  int,
    gamesLost   int
);