package codigo;

public enum ETipoVeiculo {
    CARRO(50, 200, 400),
    VAN(60, 10000, 12000),
    FURGAO(80, 10000, 12000),
    CAMINHAO(250, 20000, 20000);

    private double tamanhoTanque;
    private int manuPreventiva;
    private int manuTrocaDePeca;

    private ETipoVeiculo(double tamanhoTanque, int manuPreventiva, int manuTrocaDePeca) {
        this.tamanhoTanque = tamanhoTanque;
        this.manuPreventiva = manuPreventiva;
        this.manuTrocaDePeca = manuTrocaDePeca;
    }

    public double getTamanhoTanque() {
        return tamanhoTanque;
    }

    public int getManuPreventiva() {
        return manuPreventiva;
    }

    public int getManuTrocaDePeca() {
        return manuTrocaDePeca;
    }

}
