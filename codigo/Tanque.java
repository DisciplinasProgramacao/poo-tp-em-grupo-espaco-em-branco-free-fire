package codigo;

public class Tanque {
    private double capacidadeMaxima;
    private double capacidadeAtual;
    private EtipoCombustivel combustivel;

    /**
     * Construtor do tanque.
     *
     * @param tamanhoTanque O tamanho do tanque em litros.
     * @param combustivel   O tipo de combustível que o tanque armazena.
     */
    Tanque(ETipoVeiculo tamanhoTanque, EtipoCombustivel combustivel) {
        this.capacidadeMaxima = tamanhoTanque.getTamanhoTanque(); // Obtendo a capacidade máxima do tipo de veículo
        this.capacidadeAtual = capacidadeMaxima;
        this.combustivel = combustivel;
    }

    public boolean autonomiaParaRota(double kmRota) {
        double autonomiaAtual = autonomiaAtual(); // quantos kms o veiculo ainda roda
        return kmRota <= autonomiaAtual;
    }

    public double litrosParaAbastecer(double kmRota) { // quantos litros são necessários para fazer a rota
        double consumo = combustivel.getConsumo();
        double litroNecessario = kmRota / consumo;
        double litroFaltante = litroNecessario - capacidadeAtual;
        return litroFaltante;
    }

    /**
     * Abastece o tanque com a quantidade de litros especificada pelo usuário. Caso
     * a quantidade abastecida ultrapasse a capacidade máxima, será retornado o
     * quanto foi possível abastecer.
     *
     * @param litros A quantidade de litros a ser abastecida.
     * @return A capacidade atual do tanque após o abastecimento.
     */
    public double abastecer(double litros) {
        capacidadeAtual += litros;
        return litros * combustivel.getPreco();

    }

    /**
     * Consome a quantidade de litros com base no consumo por quilômetro rodado.
     *
     * @param litros A quantidade de litros a ser consumida.
     * @return A capacidade atual do tanque após o consumo.
     */
    public double consumirCombustivel(double litros) {
        capacidadeAtual -= litros;
        return capacidadeAtual;
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

    public double getConsumo() {
        return combustivel.getConsumo();
    }
}
