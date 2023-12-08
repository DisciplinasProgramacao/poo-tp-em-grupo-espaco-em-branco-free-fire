package codigo;

public enum EtipoCombustivel {
    ALCOOL(7, 3.29),
    GASOLINA(10, 5.19),
    DIESEL(4, 6.09);

    private final double consumo; // Consumo por litro
    private final double preco; // Preço por litro

    /**
     * Construtor para o enum de tipo de combustível.
     * 
     * @param consumo Consumo por litro do combustível
     * @param preco   Preço por litro do combustível
     */
    EtipoCombustivel(double consumo, double preco) {
        this.consumo = consumo;
        this.preco = preco;
    }

    /**
     * Obtém o preço por litro do combustível.
     * 
     * @return Preço por litro do combustível
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Obtém o consumo por litro do combustível.
     * 
     * @return Consumo por litro do combustível
     */
    public double getConsumo() {
        return consumo;
    }
}
