package codigo;

public enum EtipoManutencao {
    PREVENTIVA (520.00),
    TROCAPECA (315.00);

    public double preco;

    /**
     * Construtor o enum do tipo de manutenções
     */
    EtipoManutencao(double preco) {
        this.preco = preco;
    }

    /**
     * Funcao do tipo double que retorna o preco com base no enum chamado, ou seja, se o enum for de "PREVENTIVA", retorna o valor dessa manutenção
     * @return preco da manutencao
     */
    public double getPreco() {
        return preco;
    }
}
