package jogo;

public class Jogo {
    public static void main(String[] args) {
        Jogador jogador = new Jogador("Xandão");
        /* Então enquanto eu não realizar 10 lances, eu posso realizar uma jogada */
        while(jogador.getListaLance().size() < 10){
            jogador.realizaJogada();
        }
    }
}
