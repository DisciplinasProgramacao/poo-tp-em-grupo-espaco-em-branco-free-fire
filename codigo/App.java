package codigo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static Frota frota = new Frota();
    static Scanner sc;

    public static int menu(String nomeArquivo) throws IOException {
        limparTela();
        File arqMenu = new File(nomeArquivo);
        Scanner leitor = new Scanner(arqMenu, StandardCharsets.UTF_8);
        System.out.println(leitor.nextLine());
        System.out.println("==========================");
        int contador = 1;
        while (leitor.hasNextLine()) {
            System.out.println(contador + " - " + leitor.nextLine());
            contador++;
        }
        System.out.println("0 - Sair");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(sc.nextLine());
        leitor.close();
        return opcao;
    }

    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Encapsula uma leitura de teclado, com mensagem personalizada. A mensagem sempre é completada com ":". Retorna uma string
     * que pode ser posteriormente convertida.
     *
     * @param mensagem A mensagem a ser exibida, sem pontuação final.
     * @return String lida do usuário.
     */
    public static String leitura(String mensagem) {
        System.out.print(mensagem + ": ");
        return sc.nextLine();
    }

    /**
     * Pausa para leitura de mensagens em console.
     */
    static void pausa() {
        System.out.println("Enter para continuar.");
        sc.nextLine();
    }

    public static Veiculo criarVeiculo() {
        String placa = leitura("Digite a placa do veículo");
        String tipoVeiculo = leitura("Digite o tipo do veículo (CARRO, VAN, FURGAO, CAMINHAO)");
        String tipoCombustivel = leitura("Digite o tipo de combustível do veículo (ALCOOL, GASOLINA, DIESEL)");

        return new Veiculo(placa, tipoVeiculo, tipoCombustivel);
    }

    public static void menuRelatorio(int opcao) {
        limparTela();
        String response;
        switch (opcao) {
            case 1 -> {
                limparTela();
                response = frota.relatorioFrota();
                System.out.println(response);
                pausa();
            }
            case 2 -> {
                limparTela();
                response = String.valueOf(frota.quilometragemTotal());
                System.out.println(response);
                pausa();
            }
            case 3 -> {
                limparTela();
                response = String.valueOf(frota.maiorKMTotal());
                System.out.println(response);
                pausa();
            }
            case 4 -> {
                limparTela();
                response = String.valueOf(frota.maiorKMMedia());
                System.out.println(response);
                pausa();
            }
            case 5 -> {
                limparTela();
                response = frota.combustivelConsumido(informeVeiculo());
                System.out.println(response);
                pausa();
            }
            case 6 -> {
                limparTela();
                response = frota.quilometragemDeVeiculoNoMes(informeData(), informeVeiculo());
                System.out.println(response);
                pausa();
            }
            case 7 -> {
                limparTela();
                response = frota.quilometragemTotalDeVeiculo(informeVeiculo());
                System.out.println(response);
            }
            case 8 -> {
                limparTela();
                response = frota.relatorioDespesasDeVeiculo(informeVeiculo());
                System.out.println(response);
            }
            case 9 -> {
                limparTela();
                response = frota.relatorioRotasDeVeiculo(informeVeiculo());
                System.out.println(response);
            }
        }
        return 0;
    }

    public static LocalDate informeData() {
        String data = leitura("Informe uma data (dd/mm/aaaa)");
        return LocalDate.parse(data, DATE_TIME_FORMATTER);
    }

    public static String informeVeiculo() {
        return leitura("Digite a placa do veículo");
    }

    public static void menuGestao(int opcao) {
        limparTela();
        switch (opcao) {
            case 1 -> {
                limparTela();
                Veiculo veiculo = criarVeiculo();
                frota.addVeiculo(veiculo.getPlaca(), veiculo);
            }
            case 2 -> {
                limparTela();
                // TODO: Implementar
            }
            case 3 -> {
                limparTela();
                String placa = leitura("Digite a placa do veículo");
                frota.removerVeiculo(placa);
            }
            case 4 -> {
                limparTela();
                Rota rota = criarRota();
                String placa = leitura("Digite a placa do veículo");
                frota.addRota(placa, rota);
            }
        }
    }

    public static Rota criarRota() {
        LocalDate data = informeData();
        String quilometragem = leitura("Digite a quilometragem da rota");
        return new Rota(Double.parseDouble(quilometragem), data);
    }

    public static int menuPrincipal() throws IOException {
        int opcao = -1;
        while (opcao != 0) {
            limparTela();
            opcao = menu("menuPrincipal");
            switch (opcao) {
                case 1 -> {
                    limparTela();
                    opcao = menu("menuGestao");
                    menuGestao(opcao);
                    pausa();
                }
                case 2 -> {
                    limparTela();
                    opcao = menu("menuRelatorio");
                    menuRelatorio(opcao);
                    pausa();
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        sc = new Scanner(System.in);

        String nomeArq = "menuFreeFire";
        int opcao = -1;

        while (opcao != 0) {
            limparTela();
            opcao = menu(nomeArq);
            switch (opcao) {
                case 1 -> {
                    limparTela();
                    opcao = menuPrincipal();
                }
                case 0 -> System.out.println("Saindo...");
            }
            System.out.println("Free Fire agradece a preferência!");
        }
        sc.close();
    }

}
