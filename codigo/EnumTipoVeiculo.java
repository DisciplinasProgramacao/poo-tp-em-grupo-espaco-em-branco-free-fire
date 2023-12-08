package codigo;

public enum EnumTipoVeiculo {
    CARRO(50, 10000, 10000),
    VAN(60, 10000, 12000),
    FURGAO(80, 10000, 12000),
    CAMINHAO(250, 20000, 20000);

    private double tamanhoTanque;
    private int manuPreventiva;
    private int manuPeriodica;

    private EnumTipoVeiculo(double tamanhoTanque, int manuPreventiva, int manuPeriodica) {
        this.tamanhoTanque = tamanhoTanque;
        this.manuPreventiva = manuPreventiva;
        this.manuPeriodica = manuPeriodica;
    }

    public double getTamanhoTanque() {
        return tamanhoTanque;
    }

    public int getManuPreventiva() {
        return manuPreventiva;
    }

    public int getManuPeriodica() {
        return manuPeriodica;
    }

}
