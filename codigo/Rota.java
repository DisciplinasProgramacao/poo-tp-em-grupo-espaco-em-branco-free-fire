package codigo;

import java.time.LocalDate;

public class Rota {

    private double quilometragem;
    private LocalDate data;

    public String relatorio() {
        return "";
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
