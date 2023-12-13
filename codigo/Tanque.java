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
        this.capacidadeMaxima = tamanhoTanque.getTamanhoTanque();// Obtendo a capacidade máxima do tipo de veículo
        this.capacidadeAtual = capacidadeMaxima;
        this.combustivel = combustivel;
    }

    /**
     * Verifica se o veículo é capaz de fazer uma rota com base na quantidade de
     * litros no tanque.
     *
     * @param kmRota O km de rota que o veículo irá receber.
     * @return quantidade de litros faltantes para fazer a rota.
     */
    public boolean autonomiaParaRota(double kmRota) {
        double autonomiaAtual = autonomiaAtual(); // quantos kms o veiculo ainda roda
        return kmRota <= autonomiaAtual;
    }

    /**
     * Calcula quantidade de litros será necessário abastecer para completar a rota
     *
     * @param kmRota O km de rota que o veículo irá receber.
     * @return quantidade de litros faltantes para fazer a rota.
     */
    public double litrosParaAbastecer(double kmRota) { // quantos litros são necessários para fazer a rota
        double consumo = combustivel.getConsumo(); // 10 litros por km
        double litroNecessario = kmRota / consumo; // 20 litros necessarios por rota
        double litroFaltante = capacidadeAtual - litroNecessario; // 1 rota sobra 30, 2 rota sobra 10
        return Math.abs(litroFaltante);
    }

    /**
     * Abastece o tanque com a quantidade de litros especificada pelo usuário. Caso
     * a quantidade abastecida ultrapasse a capacidade máxima, será retornado o
     * quanto foi possível abastecer.
     *
     * @param litros A quantidade de litros a ser abastecida.
     * @return A capacidade atual do tanque após o abastecimento.
     */
    public double abastecerParaRota(double litros) {
        capacidadeAtual = litros;
        return litros * combustivel.getPreco();
    }

    /**
     * Consome a quantidade de litros com base no consumo por quilômetro rodado.
     *
     * @param kmRota O km que será percorrido.
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

    public double getConsumo() {
        return combustivel.getConsumo();
    }
}
