package codigo;

import java.time.LocalDate;

public class Manutencao {
    private EtipoManutencao tipo;
    private LocalDate data;
    private double valor;

    
    /**
     * Construtor Manutencao que recebe o tipo e a data em que a manutencao foi realizada, decidindo o valor com base no tipo de serviço feito.
     * @param tipo Tipo de manuntencao feita, atualmente pode ser "Preventiva" ou "Troca de pecas"
     * @param data Data da manutenção
     */
    public Manutencao(EtipoManutencao tipo, LocalDate data) {
        this.tipo = tipo;
        this.data = data;
        valor = tipo.getPreco();
    }

    /**
     * Função tipo double que retorna o valor da manutencao;
     * @return
     */
    public double getValor() {
        return valor;
    }
}
