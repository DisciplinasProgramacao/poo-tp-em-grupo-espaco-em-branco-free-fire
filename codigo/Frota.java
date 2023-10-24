package codigo;

public class Frota {
    private int tamanhoFrota;
    private Veiculo veiculos[];

    /***
     * Função construtura da classe, ela recebe uma array de veiculos e determina o tamanho da frota pelo array da lista.
     * @param veiculos = Array de Veiculos
     */
    Frota(Veiculo veiculos[]) {
        tamanhoFrota+= veiculos.length;
        this.veiculos = veiculos;
    }

    /***
     * Função do tipo String que retorna o relatorio da frota contendo seu tamanho e informações da lista de veiculos.
     * @return relatorio = String contendo o relatorio.
     */
    public String relatorioFrota() {
        StringBuilder relatorio = new StringBuilder("\nFrota");
        relatorio.append("Tamanho: "+tamanhoFrota);
        relatorio.append("\nLista de veiculos:");

        for (Veiculo veiculo : veiculos) {
            relatorio.append("\n"+veiculo.toString());
        }
        return relatorio.toString();
    }

    public Veiculo localizarVeiculo(String placa) {
        for (Veiculo veiculo : veiculos) {
            if(placa.equals(veiculo.getPlaca())) {
                System.out.println();
                return veiculo;
            }
        }
        System.err.print("Não existe veiculo com placa: "+placa);
        return null;
    }

    /***
     * Função double que retorna a soma da quilometragem de todos os veiculos da frota.
     * @return qTotal = Soma da quilometragem de todos os veiculos da frota.
     */
    public double quilometragemTotal() {
        double qTotal=0;
        for (Veiculo veiculo : veiculos) {
            qTotal+=veiculo.kmTotal();
        }

        return qTotal;
    }

    /***
     * Função do tipo Veiculo que retorna o veiculo com maior quilometragem total percorrida.
     * @return veiculoAtual = veiculo com maior KM total da frota.
     */
    public Veiculo maiorKMTotal() {
        double maiorKMAtual=0;
        Veiculo veiculoAtual=null;

        for (Veiculo veiculo : veiculos) {
            if(veiculo.kmTotal()>maiorKMAtual) {
                maiorKMAtual = veiculo.kmTotal();
                veiculoAtual = veiculo;
            }
        }
        return veiculoAtual;
    } 

    /*
    public Veiculo maiorKMMedia() {
        return veiculoAtual;
    } */
}
