package codigo;

public class Tanque {
    private double capacidadeMaxima;
    private double capacidadeAtual;
    private EtipoCombustivel combustivel;

    /**
     * Construtor do tanque de combustível.
     *
     * @param tamanhoTanque O tamanho do tanque em litros.
     * @param combustivel   O tipo de combustível que o tanque armazena.
     */
    public Tanque(ETipoVeiculo tamanhoTanque, EtipoCombustivel combustivel) {
        this.capacidadeMaxima = tamanhoTanque.getTamanhoTanque(); // Obtendo a capacidade máxima do tipo de veículo
        this.capacidadeAtual = capacidadeMaxima;
        this.combustivel = combustivel;
    }

    /**
     * Verifica se o veículo é capaz de fazer uma rota com base na quantidade de
     * litros no tanque.
     *
     * @param kmRota A distância da rota que o veículo irá fazer em quilômetros.
     * @return true se o veículo tiver autonomia para a rota, false caso contrário.
     */
    public boolean autonomiaParaRota(double kmRota) {
        double autonomiaAtual = autonomiaAtual();
        return kmRota <= autonomiaAtual; // Se o cálculo for verdadeiro retorna true
    }

    /**
     * Calcula a quantidade de litros necessários para abastecer o veículo e
     * completar uma rota.
     *
     * @param kmRota A distância da rota que o veículo irá fazer em quilômetros.
     * @return quantidade de litros faltantes para fazer a rota.
     */
    public double litrosParaAbastecer(double kmRota) {
        double consumo = combustivel.getConsumo();
        double litroNecessario = kmRota / consumo;
        double litroFaltante = litroNecessario - capacidadeAtual;
        return Math.abs(litroFaltante); // Retorna o valor absoluto
    }

    /**
     * Abastece o tanque com a quantidade de litros necessários para completar a
     * rota.
     *
     * @param litros A quantidade de litros a ser abastecida.
     * @return o valor gasto no abastecimento.
     */
    public double abastecerParaRota(double litros) {
        capacidadeAtual += litros;
        return litros * combustivel.getPreco();
    }

    /**
     * Consome a quantidade de litros com base no consumo por quilômetro rodado.
     *
     * @param kmRota O quilômetro que será percorrido.
     * @return A capacidade atual do tanque após o consumo.
     */
    public void consumirCombustivel(double kmRota) {
        capacidadeAtual -= kmRota / getConsumo();
    }

    /**
     * Calcula a autonomia máxima do tanque com base no consumo por quilômetro
     * rodado.
     *
     * @return A autonomia máxima do tanque em quilômetros.
     */
    public double autonomiaMaxima() {
        return capacidadeMaxima * combustivel.getConsumo();
    }

    /**
     * Calcula a autonomia atual do tanque com base no consumo por quilômetro
     * rodado.
     *
     * @return A autonomia atual do tanque em quilômetros.
     */
    public double autonomiaAtual() {
        return capacidadeAtual * combustivel.getConsumo();
    }

    /**
     * Obtém o consumo de combustível por quilômetro rodado para o veículo.
     *
     * @return O consumo de combustível por quilômetro rodado.
     */
    public double getConsumo() {
        return combustivel.getConsumo();
    }
}
