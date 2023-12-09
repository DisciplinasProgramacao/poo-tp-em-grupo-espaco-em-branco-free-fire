package codigo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Manutencao {

    private Map<EtipoManutencao, Double> manutencoesRealizadas;
    
    /**
     * Construtor Manutencao que recebe o tipo e a data em que a manutencao foi realizada, decidindo o valor com base no tipo de serviço feito.
     * @param tipo Tipo de manuntencao feita, atualmente pode ser "Preventiva" ou "Troca de pecas"
     * @param data Data da manutenção
     */
    public Manutencao() {
        manutencoesRealizadas = new HashMap<>();
    }


    /**
     * Função tipo double que retorna o valor da manutencao;
     * @return
     */
    public void aplica() {

    }

    public boolean verifica(double kmTotal, double kmRota) {
        //seila 
        return false;
    }

    /**
     * Função tipo double que permite adicionar o valor da manutencao;
     * @return valor atualizado da manutenção.
     */
    public double addValorManutencao(LocalDate date, double valor) {
        //seila
        return valor;
    }

}
