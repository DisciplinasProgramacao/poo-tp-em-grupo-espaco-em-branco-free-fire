package codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static Frota frota = new Frota();
    static Scanner sc;

    public static int menu(String nomeArquivo) {
        try {
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
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo: " + nomeArquivo);
            return -1;
        } catch (IOException e) {
            System.out.println("Erro de leitura: " + e.getMessage());
            return -1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            return -1;
        }
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
        String placa;
        do {
            placa = leitura("Digite a placa do veículo (formato AAA-0000): ");
            if (!placa.matches("[A-Z]{3}-\\d{4}"))
                System.out.println("Placa em formato inválido.");
        } while (!placa.matches("[A-Z]{3}-\\d{4}"));

        String tipoVeiculo;
        do {
            tipoVeiculo = leitura("Digite o tipo do veículo (CARRO, VAN, FURGAO, CAMINHAO): ");
            if (!tipoVeiculo.matches("(?i)(CARRO|VAN|FURGAO|CAMINHAO)"))
                System.out.println("Tipo de veículo inválido.");
        } while (!tipoVeiculo.matches("(?i)(CARRO|VAN|FURGAO|CAMINHAO)"));

        String tipoCombustivel;
        do {
            tipoCombustivel = leitura("Digite o tipo de combustível do veículo (ALCOOL, GASOLINA, DIESEL): ");
            if (!tipoCombustivel.matches("(?i)(ALCOOL|GASOLINA|DIESEL)"))
                System.out.println("Tipo de combustível inválido.");
        } while (!tipoCombustivel.matches("(?i)(ALCOOL|GASOLINA|DIESEL)"));

        return new Veiculo(placa, tipoVeiculo, tipoCombustivel);
    }


    public static String[] lerLinhaDeArquivo(Scanner leitor) {
        String linha = leitor.nextLine();
        return linha.split(";");
    }

    public static void menuRelatorio(int opcao) {
        limparTela();
        String response;
        switch (opcao) {
            case 1 -> {
                limparTela();
                response = frota.relatorioFrota();
                System.out.println(response);
            }
            case 2 -> {
                limparTela();
                response = String.valueOf(frota.quilometragemTotal());
                System.out.println(response);
            }
            case 3 -> {
                limparTela();
                response = String.valueOf(frota.maiorKMTotal());
                System.out.println(response);
            }
            case 4 -> {
                limparTela();
                response = String.valueOf(frota.maiorKMMedia());
                System.out.println(response);
            }
            case 5 -> {
                limparTela();
                response = frota.combustivelConsumido(informeVeiculo());
                System.out.println(response);
            }
            case 6 -> {
                limparTela();
                response = frota.quilometragemDeVeiculoNoMes(informeData(), informeVeiculo());
                System.out.println(response);
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
            case 10 -> {
                limparTela();
                response = frota.relatorioManutencoesDeVeiculo(informeVeiculo());
                System.out.println(response);
            }
        }
    }

    public static LocalDate informeData() {
        LocalDate data = null;
        do {
            String dataInput = leitura("Informe uma data (dd/MM/yyyy): ");
            try {
                data = LocalDate.parse(dataInput, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Por favor, digite uma data válida.");
            }
        } while (data == null);

        return data;
    }


    public static String informeVeiculo() {
        return leitura("Digite a placa do veículo");
    }

    public static String informeArquivo() {
        return leitura("Digite o nome do arquivo");
    }

    public static void menuGestao(int opcao) {
        try {
            limparTela();
            switch (opcao) {
                case 1 -> {
                    limparTela();
                    Veiculo veiculo = criarVeiculo();
                    frota.addVeiculo(veiculo.getPlaca(), veiculo);
                }
                case 2 -> {
                    limparTela();
                    String arquivo = informeArquivo();
                    Scanner leitor = new Scanner(new File(arquivo), StandardCharsets.UTF_8);
                    while (leitor.hasNextLine()) {
                        String[] dados = lerLinhaDeArquivo(leitor);
                        Veiculo veiculo = new Veiculo(dados[0], dados[1], dados[2]);
                        frota.addVeiculo(veiculo.getPlaca(), veiculo);
                    }
                    leitor.close();
                }
                case 3 -> {
                    limparTela();
                    frota.removerVeiculo(informeVeiculo());
                }
                case 4 -> {
                    limparTela();
                    Rota rota = criarRota();
                    frota.addRota(informeVeiculo(), rota);
                }
                case 5 -> {
                    limparTela();
                    String placa = informeVeiculo();
                    String arquivo = informeArquivo();
                    Scanner leitor = new Scanner(new File(arquivo), StandardCharsets.UTF_8);
                    while (leitor.hasNextLine()) {
                        String[] dados = lerLinhaDeArquivo(leitor);
                        Rota rota = new Rota(Double.parseDouble(dados[0]), LocalDate.parse(dados[1], DATE_TIME_FORMATTER));
                        frota.addRota(placa, rota);
                    }
                    leitor.close();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo.");
        } catch (IOException e) {
            System.out.println("Erro de leitura do arquivo.");
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido no arquivo.");
        }
    }

    public static Rota criarRota() {
        LocalDate data = informeData();

        double quilometragem;
        do {
            String input = leitura("Digite a quilometragem da rota: ");
            try {
                quilometragem = Double.parseDouble(input);
                if (quilometragem < 0) {
                    System.out.println("A quilometragem deve ser um valor não negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
                quilometragem = -1;
            }
        } while (quilometragem < 0);

        return new Rota(quilometragem, data);
    }

    public static int menuPrincipal() {
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

    public static void main(String[] args) {
        try {
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
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
