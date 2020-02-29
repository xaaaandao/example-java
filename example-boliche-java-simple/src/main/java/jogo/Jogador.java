package jogo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class Jogador {
    private int quantidadeMaximoDePinos = 10;

    @Setter
    @Getter
    String nome;

    @Getter
    List<Lance> listaLance;

    public Jogador(String nome){
        this.nome = nome;
    }

    public int geraNumeroAleatorio(int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt((max - 0) + 1) + 0;
    }

    public void realizaJogada(){
        /* Enquanto eu não realizar 10 lances */
        if(listaLance.size() < 10) {
            /* Eu gero um número aleatório de 0 a 10 (que é a quantidade de pinos) */
            int quantidadePinosDerrubados = geraNumeroAleatorio(quantidadeMaximoDePinos);
            /* Ai eu instancio um Lance */
            Lance lance = new Lance();
            /* Ai a partir da quantidade de pinos derrubado eu seto como primeiro lance */
            lance.setPrimeiroLance(quantidadePinosDerrubados);
            /* Para validar o spear eu tenho que estar pelo menos na segunda rodada */
            if(listaLance.size() > 0) {
                Lance lanceAnterior = listaLance.get(listaLance.size() - 1);
                /* Se no lance anterior foi um spear ,ou seja, a soma total é 10 e o valor do primeiro lance é diferente de 10 (porque se for igual é strike) */
                if(lanceAnterior.getPontuacaoTotal() == 10 && lanceAnterior.getPrimeiroLance() != 10) {
                    /* Então eu pego o valor do primeiro lance dessa atual rodada e somo com o valor total da passada */
                    lanceAnterior.setPontuacaoTotal(lanceAnterior.getPontuacaoTotal() + lance.getPrimeiroLance());
                }
                /* Então independemente se for um spear ou não
                nessa rodada eu tenho que pegar o valor da pontuação total da rodada passada
                e soma com a da rodada atual */
                lance.setPontuacaoTotal(lanceAnterior.getPontuacaoTotal() + lance.getPontuacaoTotal());
            }
            /* Quando é diferente de menos um significa que não é um strike, então posso realizar mais uma jogada */
            if(lance.getSegundoLance() != -1) {
                /* Ai a partir da quantidade de pinos que existe menos o derrubado eu seto como segundo lance */
                quantidadePinosDerrubados = geraNumeroAleatorio(quantidadeMaximoDePinos - quantidadePinosDerrubados);
                lance.setSegundoLance(quantidadePinosDerrubados);
            }
            /* Se tiver mais de dois elementos */
            if(listaLance.size() > 2) {
                /* Eu verifico se o antepenúltimo é um strike */
                Lance antePenultimo = listaLance.get(listaLance.size() - 2);
                if(antePenultimo.getPrimeiroLance() == 10) {
                    /* Se for o penúltimo tem que ter a sua pontuação dobrada e o atual também */
                    Lance penultimo = listaLance.get(listaLance.size() - 1);
                    penultimo.setPontuacaoTotal(penultimo.getPontuacaoTotal() * 2);
                    lance.setPontuacaoTotal(lance.getPontuacaoTotal() * 2);
                }
            }
            /* Ai adiciono na lista */
            listaLance.add(lance);
        }
    }
}
