package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual acao de cargo deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca Funcionario nome");
            System.out.println("2 - Busca Funcionario nome, data e salario");
            System.out.println("3 - Busca Funcionario data contratacao");
            System.out.println("4 - Busca Funcionario salario");

            int acao = scanner.nextInt();

            switch (acao) {
                case 1:
                    buscaFuncionarioNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioNomeSalarioMaiorData(scanner);
                    break;
                case 3:
                    buscaFuncionarioDataContratacao(scanner);
                    break;
                case 4:
                    pesquisaFuncionarioSalario();
                    break;
                default:
                    system = false;
                    break;

            }
        }
    }

    private void pesquisaFuncionarioSalario() {
        List<FuncionarioProjecao> funcionarios = funcionarioRepository.findFuncionarioSalario();

        funcionarios.forEach(funcionario ->
                System.out.println("ID: " + funcionario.getId() + ", nome: " + funcionario.getNome() + ", salario: " + funcionario.getSalario()));
    }

    private void buscaFuncionarioNome(Scanner scanner) {
        System.out.println("Qual nome deseja pesquisar");
        String nome = scanner.next();

        List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);

        funcionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
        System.out.println("Qual nome deseja pesquisar");
        String nome = scanner.next();

        System.out.println("Qual data contratacao deseja pesquisar");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        System.out.println("Qual salario deseja pesquisar");
        BigDecimal salario = scanner.nextBigDecimal();

        List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome,
                salario,
                localDate);

        funcionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {
        System.out.println("Qual data contratacao deseja pesquisar");
        String data = scanner.next();
        LocalDate localDate = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = funcionarioRepository.findByDataContratacaoMaior(localDate);

        funcionarios.forEach(System.out::println);
    }

}
