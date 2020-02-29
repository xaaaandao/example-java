package main;

public class Game {

    private int lancamentoCorrente = 0;
    private int[] lancamentos = new int[21];

    public void derruba(int pinos) {
        lancamentos[lancamentoCorrente++] = pinos;
    }

    public int pontuacao() {
        int score = 0;
        int jogadaCorrente = 0;
        for (int jogada = 0; jogada < 10; jogada++) {
            if (isStrike(jogadaCorrente)) {
                score += 10 + proximosDoisLancamentos(jogadaCorrente + 1);
                jogadaCorrente ++;
            }
            else {
                if (isSpare(jogadaCorrente))
                    score += 10 + proximoLancamento(jogadaCorrente);
                else
                    score += proximosDoisLancamentos(jogadaCorrente);

                jogadaCorrente += 2;
            }
        }

        return score;
    }

    private boolean isStrike(int jogadaCorrente) {
        return lancamentos[jogadaCorrente] == 10;
    }

    private int proximoLancamento(int jogadaCorrente) {
        return lancamentos[jogadaCorrente + 2];
    }

    private boolean isSpare(int jogadaCorrente) {
        return proximosDoisLancamentos(jogadaCorrente) == 10;
    }

    private int proximosDoisLancamentos(int jogadaCorrente) {
        return lancamentos[jogadaCorrente] + lancamentos[jogadaCorrente + 1];
    }
}
