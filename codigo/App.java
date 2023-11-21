package codigo;

import java.util.Scanner;

public class App {

        static Scanner sc;

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
         * @param mensagem A mensagem a ser exibida, sem pontuação final.
         * @return String lida do usuário.
         */
        public static String leitura(String mensagem){
            System.out.print(mensagem+": ");
            return sc.nextLine();
        }

        /**
         * Pausa para leitura de mensagens em console.
         */
        static void pausa() {
            System.out.println("Enter para continuar.");
            sc.nextLine();
        }

        /**
         * Exibe o menu principal e retorna a opção escolhida.
         * @return Opção escolhida.
         */
        public static int menuPrincipal() {
            int opcao;
            System.out.println("1 - Gerar relatório");
            System.out.println("2 - Localizar um veículo");
            System.out.println("3 - Quilometragem Total");
            System.out.println("4 - Veículo com maior quilometragem");
            System.out.println("5 - Veículo com maior média de quilometragem");
            System.out.println("0 - Sair");
            System.out.println();
            opcao = sc.nextInt();
            sc.nextLine();
            return opcao;
        }

    public static void main(String[] args) {
            sc = new Scanner(System.in);

            Veiculo[] veiculos = new Veiculo[50];
            Frota frota = new Frota(veiculos);

            veiculos[0] = new Veiculo("ABC-1A23");
            veiculos[1] = new Veiculo("DEF-1A23");
            veiculos[2] = new Veiculo("GHI-1A23");

            int opcao = -1;

            while (opcao != 0) {
                limparTela();
                opcao = menuPrincipal();
                switch (opcao) {
                    case 1 -> {
                        System.out.println(frota.relatorioFrota());
                        pausa();
                    }
                    case 2 -> {
                        String placa = leitura("Digite a placa do veiculo");
                        Veiculo veiculo = frota.localizarVeiculo(placa);
                        if (veiculo != null) {
                            System.out.println(veiculo);
                        }
                        pausa();
                    }
                    case 3 -> {
                        System.out.println("Quilometragem total: " + frota.quilometragemTotal());
                        pausa();
                    }
                    case 4 -> {
                        Veiculo veiculo = frota.maiorKMTotal();
                        System.out.println("Veiculo com maior quilometragem total: " + veiculo);
                        pausa();
                    }
                    case 5 -> {
                        Veiculo veiculo = frota.maiorKMMedia();
                        System.out.println("Veiculo com maior quilometragem média: " + veiculo);
                        pausa();
                    }
                    case 0 -> System.out.println("Saindo...");
                }
            }
            sc.close();
        }

}
