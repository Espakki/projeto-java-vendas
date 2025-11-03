package org.example;

import dao.ClienteDAO;
import model.Cliente;
import dao.ProdutoDAO;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final ProdutoDAO produtoDAO = new ProdutoDAO();

    public static void main(String[] args) {
            while (true) {
                exibirMenuPrincipal();
                int opcao = lerOpcao();
                switch (opcao) {
                    case 1:
                        gerenciarClientes();
                        break;
                    case 2:
                        gerenciarProdutos();
                        break;
                    case 3:
                        System.out.println("Funcionalidade 'Criar Pedido' ainda será implementada.");
                        pressioneEnter();
                        break;
                    case 0:
                        System.out.println("Obrigado por usar o sistema. Saindo...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        pressioneEnter();
                        break;
                }
            }
        }

        private static void exibirMenuPrincipal () {
            System.out.println("\n--- SISTEMA DE GERENCIAMENTO DE VENDAS ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Criar Novo Pedido");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
        }

        private static void gerenciarClientes () {
            while (true) {
                System.out.println("\n--- Gerenciamento de Clientes ---");
                System.out.println("1. Cadastrar Novo Cliente");
                System.out.println("2. Listar Todos os Clientes");
                System.out.println("3. Atualizar Cliente (por ID)");
                System.out.println("4. Deletar Cliente (por ID)");
                System.out.println("0. Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");

                int opcao = lerOpcao();

                switch (opcao) {
                    case 1: // CADASTRAR
                        System.out.print("Digite o nome do cliente: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite o e-mail do cliente: ");
                        String email = scanner.nextLine();
                        System.out.print("Digite o telefone do cliente: ");
                        String telefone = scanner.nextLine();

                        Cliente novoCliente = new Cliente();
                        novoCliente.setNome(nome);
                        novoCliente.setEmail(email);
                        novoCliente.setTelefone(telefone);

                        clienteDAO.salvar(novoCliente); // Chama o DAO
                        break;
                    case 2: // LISTAR
                        System.out.println("--- Lista de Clientes ---");
                        List<Cliente> clientes = clienteDAO.listarTodos();
                        if (clientes.isEmpty()) {
                            System.out.println("Nenhum cliente cadastrado.");
                        } else {
                            for (Cliente c : clientes) {
                                System.out.println(c);
                            }
                        }
                        break;
                    case 3: // ATUALIZAR
                        System.out.print("Digite o ID do cliente que deseja atualizar: ");
                        int idAtualizar = lerOpcao();
                        Cliente clienteAtualizar = clienteDAO.buscarPorId(idAtualizar); // Chama o DAO

                        if (clienteAtualizar == null) {
                            System.out.println("Cliente com ID " + idAtualizar + " não encontrado.");
                        } else {
                            System.out.println("Cliente encontrado: " + clienteAtualizar);
                            System.out.print("Digite o novo nome (ou deixe em branco para manter): ");
                            String novoNome = scanner.nextLine();
                            if (!novoNome.isEmpty()) {
                                clienteAtualizar.setNome(novoNome);
                            }

                            System.out.print("Digite o novo e-mail (ou deixe em branco para manter): ");
                            String novoEmail = scanner.nextLine();
                            if (!novoEmail.isEmpty()) {
                                clienteAtualizar.setEmail(novoEmail);
                            }

                            System.out.print("Digite o novo telefone (ou deixe em branco para manter): ");
                            String novoTelefone = scanner.nextLine();
                            if (!novoTelefone.isEmpty()) {
                                clienteAtualizar.setTelefone(novoTelefone);
                            }

                            clienteDAO.atualizar(clienteAtualizar);
                        }
                        break;
                    case 4: // DELETAR
                        System.out.print("Digite o ID do cliente que deseja deletar: ");
                        int idDeletar = lerOpcao();
                        clienteDAO.deletar(idDeletar);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
                pressioneEnter();
            }
        }

        private static void gerenciarProdutos () {
            while (true) {
                System.out.println("\n--- Gerenciamento de Produtos ---");
                System.out.println("1. Cadastrar Novo Produto");
                System.out.println("2. Listar Todos os Produtos");
                System.out.println("3. Atualizar Produto (por ID)");
                System.out.println("4. Deletar Produto (por ID)");
                System.out.println("0. Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");

                int opcao = lerOpcao();

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do produto: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite a descricao do produto: ");
                        String descricao = scanner.nextLine();

                        int quantidade = 0;
                        while (quantidade <= 0) {
                            System.out.print("Digite a quantidade do produto: ");
                            try {
                                quantidade = Integer.parseInt(scanner.nextLine());
                                if (quantidade <= 0) {
                                    System.out.println("Erro: A quantidade deve ser maior que 0.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida. Digite um número.");
                            }
                        }
                        double valor = 0;
                        while (valor <= 0) {
                            System.out.print("Digite o valor do produto: ");
                            String valorInput = scanner.nextLine();
                            try {
                                String valorCorrigido = valorInput.replace(',', '.');
                                valor = Double.parseDouble(valorCorrigido);
                                if (valor <= 0) {
                                    System.out.println("Erro: O valor deve ser maior que 0.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada inválida. Digite um número (ex: 10,50).");
                            }
                        }

                        Produto novoProduto = new Produto();
                        novoProduto.setNome(nome);
                        novoProduto.setDescricao(descricao);
                        novoProduto.setQuantidadeEstoque(quantidade);
                        novoProduto.setPreco(valor);

                        produtoDAO.salvar(novoProduto);
                        break;
                    case 2:
                        System.out.println("--- Lista de Produtos ---");
                        List<Produto> produtos = produtoDAO.listarTodos();
                        if (produtos.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            for (Produto p : produtos) {
                                System.out.println(p);
                            }
                        }
                        break;
                    case 3:
                        System.out.print("Digite o ID do produto que deseja atualizar: ");
                        int idAtualizar = lerOpcao();
                        Produto produtoAtualizar = produtoDAO.buscarPorId(idAtualizar);

                        if (produtoAtualizar == null) {
                            System.out.println("Produto com ID " + idAtualizar + " não encontrado.");
                        } else {
                            System.out.println("Produto encontrado: " + produtoAtualizar);
                            System.out.print("Digite o novo nome (ou deixe em branco para manter): ");
                            String novoNome = scanner.nextLine();
                            if (!novoNome.isEmpty()) {
                                produtoAtualizar.setNome(novoNome);
                            }

                            System.out.print("Digite a descricão nova (ou deixe em branco para manter): ");
                            String novoDescricao= scanner.nextLine();
                            if (!novoDescricao.isEmpty()) {
                                produtoAtualizar.setDescricao(novoDescricao);
                            }

                            System.out.print("Digite o novo numero de estoque (ou deixe em branco para manter): ");
                            String estoqueInput = scanner.nextLine();
                            if (!estoqueInput.isEmpty()) {
                                try{
                                    int novoEstoque = Integer.parseInt(estoqueInput);
                                    if(novoEstoque > 0){
                                        produtoAtualizar.setQuantidadeEstoque(novoEstoque);
                                    } else{
                                        System.out.println("Error: O número deve ser maior que 0.");
                                    }
                                } catch (NumberFormatException e){
                                    System.out.println("Entrada inválida. O estoque deve ser um número. O estoque não foi alterado.");
                                }
                            }

                            System.out.print("Digite o novo valor (ou deixe em branco para manter): ");
                            String valorInput = scanner.nextLine();
                            if (!valorInput.isEmpty()) {
                                try {
                                    String valorCorrigido = valorInput.replace(',', '.');
                                    double novoValor = Double.parseDouble(valorCorrigido);
                                    if (novoValor > 0) {
                                        System.out.println("Preço atualizado para: R$ " + novoValor);
                                        produtoAtualizar.setPreco(novoValor);
                                    } else {
                                        System.out.println("Erro: O valor deve ser positivo. Preço não alterado.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Entrada inválida. O valor deve ser um número (ex: 10.50 ou 10,50). Preço não alterado.");
                                }
                            }
                            produtoDAO.atualizar(produtoAtualizar);
                        }
                        break;
                    case 4: // DELETAR
                        System.out.print("Digite o ID do produto que deseja deletar: ");
                        int idDeletar = lerOpcao();
                        produtoDAO.deletar(idDeletar);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
                pressioneEnter();
            }
        }

        private static int lerOpcao () {
            while (true) {
                try {
                    int opcao = Integer.parseInt(scanner.nextLine());
                    return opcao;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    System.out.print("Escolha uma opção: ");
                }
            }
        }

        private static void pressioneEnter () {
            System.out.println("\nPressione [Enter] para continuar...");
            scanner.nextLine();
        }
    }