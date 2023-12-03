package codigo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rota {

    private double quilometragem;
    private LocalDate data;

    
    public Rota(double quilometragem) {
        this.quilometragem = quilometragem;
        this.data = LocalDate.now();
    }

    public String relatorio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Rota: "+data.format(formatter)+"\nQuilometragem: "+quilometragem+"";
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public LocalDate getData() {
        return data;
    }
}
