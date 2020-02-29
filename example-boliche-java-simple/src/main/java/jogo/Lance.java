package jogo;

import lombok.Getter;
import lombok.Setter;

public class Lance {
    @Getter
    int primeiroLance;

    @Getter
    int segundoLance = 0;

    @Getter
    @Setter
    int pontuacaoTotal = 0;

    public void setPrimeiroLance(int primeiroLance){
        if(primeiroLance == 10){
            this.primeiroLance = primeiroLance;
            setPontuacaoTotal(getPontuacaoTotal() + primeiroLance);
            setSegundoLance(-1);
        } else {
            this.primeiroLance = primeiroLance;
        }
    }

    public void setSegundoLance(int segundoLance){
        this.primeiroLance = segundoLance;
        setPontuacaoTotal(getPontuacaoTotal() + segundoLance);
    }
}
