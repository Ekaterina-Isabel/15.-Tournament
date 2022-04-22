package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Player;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game = new Game();

    private Player first = new Player(1, "first", 1);
    private Player second = new Player(2, "second", 2);
    private Player third = new Player(3, "thiRd", 3);
    private Player forth = new Player(4, "forth", 3);
    private Player fifth = new Player(5, "fifth", 5);
    private Player sixth = new Player(6, "sixth", 6);


    @BeforeEach
    void setUp() {
        game.register(first);
        game.register(second);
        game.register(third);
        game.register(forth);
    }

    @Test
    void shouldWinFirstPlayer() {
        int actual = game.round(forth.getName(), second.getName());

        assertEquals(1, actual);
    }

    @Test
    void shouldWinSecondPlayer() {
        int actual = game.round(first.getName(), "SECOND");

        assertEquals(2, actual);
    }

    @Test
    void shouldDraw() {
        int actual = game.round(third.getName(), forth.getName());

        assertEquals(0, actual);
    }

    @Test
    void shouldThrowException() {
        assertThrows(RuntimeException.class,
                () -> game.round(third.getName(), fifth.getName())
        );
    }

    @Test
    void shouldThrowExceptionBoth() {
        assertThrows(RuntimeException.class,
                () -> game.round(fifth.getName(), sixth.getName())
        );
    }
}