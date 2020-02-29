package test;

import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void shouldExistsAGame() {
        assertTrue(game instanceof Game);
    }

    @Test
    void shouldRollZeroPoints() {
        game.derruba(0);
        assertEquals(0, game.pontuacao());
    }

    @Test
    void shouldRollOnePoint() {
        game.derruba(1);
        assertEquals(1, game.pontuacao());
    }

    @Test
    void shouldRollAllGameWithZeroPoints() {
        lancamentos(0, 20);
        assertEquals(0, game.pontuacao());
    }

    @Test
    void shouldRollAllGameWithOnePoint() {
        game.derruba(1);
        lancamentos(0, 19);
        assertEquals(1, game.pontuacao());
    }

    @Test
    void shouldRollAllGameWithTwentyPoint() {
        lancamentos(1, 20);
        assertEquals(20, game.pontuacao());
    }

    @Test
    void shouldDoASpare() {
        fazUmSpare();
        game.derruba(3);
        lancamentos(0, 17);
        assertEquals(16, game.pontuacao());
    }

    @Test
    void shouldDoAStrike() {
        fazUmStrike();
        game.derruba(3);
        game.derruba(4);
        lancamentos(0, 16);
        assertEquals(24, game.pontuacao());
    }

    @Test
    void shouldValidateAPerfectGame() {
        for (int i = 0; i < 12; i++) {
            fazUmStrike();
        }
        assertEquals(300, game.pontuacao());
    }

    @Test
    void shouldValidateACompleteGame() {
        game.derruba(1);
        game.derruba(4); //5

        game.derruba(4);
        game.derruba(5); //14

        game.derruba(6);
        game.derruba(4); //24

        game.derruba(5);
        game.derruba(5); //39

        game.derruba(10);//59

        game.derruba(0);
        game.derruba(1);//61

        game.derruba(7);
        game.derruba(3);//71

        game.derruba(6);
        game.derruba(4);//87

        game.derruba(10);//107

        game.derruba(2);//111
        game.derruba(8);//127
        game.derruba(6);//133

        assertEquals(133, game.pontuacao());
    }

    private void fazUmStrike() {
        game.derruba(10);
    }

    private void fazUmSpare() {
        game.derruba(5);
        game.derruba(5);
    }

    private void lancamentos(int pinos, int qtdeLancamentosRestantes) {
        for (int i = 0; i < qtdeLancamentosRestantes; i++) {
            game.derruba(pinos);
        }
    }

}
