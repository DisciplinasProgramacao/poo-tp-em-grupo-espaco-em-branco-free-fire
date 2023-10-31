package codigo;

public class Tanque {
    private final double CONSUMO;
    private double capacidadeMaxima;
    private double capacidadeAtual;

    Tanque (double consumo, double capacidadeMaxima) {
        this.CONSUMO = consumo;
        this.capacidadeMaxima = capacidadeMaxima;
        this.capacidadeAtual = capacidadeMaxima;
    }
    

    /**
     * Função que abastece o tanque com a quantidade de litros especificada pelo usuário. Caso
     * a quantidade abastecida ultrapasse a capacidade máxima, será retornado a capacidade máxima.
     * 
     * @param litros A quantidade de litros a ser abastecida.
     * @return A capacidade atual do tanque após o abastecimento.
     */
    public double abastecer(double litros) {
        if (litros > 0) {
            double capacidadeRestante = capacidadeMaxima - capacidadeAtual;
            if (litros <= capacidadeRestante) {
                capacidadeAtual += litros;
            } else {
                capacidadeAtual = capacidadeMaxima;
            }
        }
        return capacidadeAtual;
    }


    /**
     * Função que desabastece o tanque com a quantidade de litros gastos com base no consumo por quilometros rodados.
     * 
     * @param litros A quantidade de litros a ser retirada.
     * @return A capacidade atual do tanque após a retirada.
     */
    public double desabastecer(double litros) {
        if (litros > 0) {
            if (litros <= capacidadeAtual) {
                capacidadeAtual -= litros;
            } else {
                capacidadeAtual = 0;
            }
        }
        return capacidadeAtual;
    }

    public double autonomiaMaxima() {
        return capacidadeMaxima * CONSUMO;
    } 

    public double autonomiaAtual() {
        return capacidadeAtual * CONSUMO;
    }
}
