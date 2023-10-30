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

    public double autonomiaMaxima() {
        return capacidadeMaxima * CONSUMO;
    } 

    public double autonomiaAtual() {
        return capacidadeAtual * CONSUMO;
    }
}
