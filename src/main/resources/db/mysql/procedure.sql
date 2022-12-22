
DELIMITER $$
DROP PROCEDURE IF EXISTS createTheater;
CREATE PROCEDURE createTheater()
BEGIN
    SELECT count(*) INTO @cinemaNumber FROM cinema;
    SET @theaterNameNumber := 8;
    SET @i := 1;

    WHILE @i <= @cinemaNumber
        DO
            SET @j := 1;

            WHILE @j <= @theaterNameNumber
                DO
                    SET @theaterName = concat(@j, '관');
                    INSERT INTO theater(name, cinema_id) VALUES (@theaterName, @i);

                    SET @j := @j + 1;
                END WHILE;

            SET @i := @i + 1;
        END WHILE;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS createSeat;
CREATE PROCEDURE createSeat()
BEGIN
    SELECT count(*) INTO @theaterCount FROM Theater;

    SET @cols := 18;
    SET @i := 1;
    WHILE @i <= @theaterCount
        DO
            SET @rows := 'A,B,C,D,E,F,G,H,I,';
            WHILE (LOCATE(',', @rows) > 0)
                DO
                    SET @row := SUBSTRING(@rows, 1, 1);
                    SET @rows := SUBSTRING(@rows, LOCATE(',', @rows) + 1);
                    SET @col := 1;
                    WHILE @col <= @cols
                        DO
                            INSERT INTO seat(seat_col, seat_row, theater_id) VALUES(@col, @row, @i);
                            SET @col := @col + 1;
                        END WHILE;
                END WHILE;
            SET @i := @i + 1;
        END WHILE;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS createScreen;
CREATE PROCEDURE createScreen()
BEGIN
    SET @rounds := 8;
    SET @theaterId := 1;
    SELECT count(*) INTO @theaterIdLen FROM theater;
    SET @movieId := 1;
    SELECT count(*) INTO @movieIdLen FROM movie;

    WHILE @theaterId <= @theaterIdLen
        DO
            SET @round := 1;
            SET @minute := 480;

            WHILE @round <= @rounds
                DO
                    INSERT INTO screen(round, start_datetime, movie_id, theater_id) VALUES (@round, DATE_ADD(CURDATE(), INTERVAL @minute MINUTE), @movieId, @theaterId);

                    SET @round := @round + 1;
                    SET @minute := @minute + (SELECT runtime FROM movie WHERE movie_id = @movieId);
                END WHILE;

            SET @theaterId := @theaterId + 1;
            SET @movieId := (@movieId % @movieIdLen) + 1;
        END WHILE;
END $$
DELIMITER ;
