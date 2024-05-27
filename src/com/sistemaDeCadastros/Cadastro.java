package com.sistemaDeCadastros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;

class listaArquivos extends SimpleFileVisitor<Path>{

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String arquivo = file.toAbsolutePath().toString();
        if(arquivo.endsWith(".txt") && !(arquivo.contains("formulario.txt"))){
            Cadastro.usuariosList.add(arquivo);
        }
        return FileVisitResult.CONTINUE;
    }
    
}

public class Cadastro {
    private static List<String> perguntas = new ArrayList<>();
    private static List<Usuario> usuariosCadastrados = new ArrayList<>();
    public static List<String> usuariosList = new ArrayList<>();

    public static void criarFormulario(){
        Path formularioPath = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
        try {
            Files.createFile(formularioPath);
        } catch (IOException e) {
            System.out.println("Formulario já foi criado!");
        }

        try (BufferedWriter bw = Files.newBufferedWriter(formularioPath)) {
            for (String string : perguntas) {
                bw.write(string);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void criarArquivo(Usuario usuario) {
        String path = "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\";
        String nomeDoArquivo = usuario.getNome().toUpperCase().replace(" ", "");
        nomeDoArquivo = usuariosCadastrados.size() + "-" + nomeDoArquivo + ".txt";
        path = path + nomeDoArquivo;

        Path arquivoPath = Paths.get(path);

        try {
            Files.createFile(arquivoPath);
        } catch (IOException e) {
            System.out.println("O arquivo deste usuário já existe");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(usuario.getNome());
            bw.newLine();
            bw.write(usuario.getEmail());
            bw.newLine();
            bw.write(String.format("%d", usuario.getIdade()));
            bw.newLine();
            bw.write(String.format("%.2f", usuario.getAltura()));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarUsuario() {
        Usuario usuario = new Usuario();
        Path formularioPath = Paths.get(
                "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
        try (Scanner scan = new Scanner(System.in)) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(formularioPath)) {
                String linha;
                while ((linha = bufferedReader.readLine()) != null) {
                    perguntas.add(linha);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(perguntas.get(0));
            usuario.setNome(scan.nextLine());
            
            System.out.println(perguntas.get(1));
            usuario.setEmail(scan.nextLine());
            
            System.out.println(perguntas.get(2));
            usuario.setIdade(scan.nextInt());
            
            System.out.println(perguntas.get(3));
            try {
                usuario.setAltura(scan.nextDouble());
            } catch (InputMismatchException e) {
                System.out.println("Use virgula ao invés de ponto:");
                scan.nextLine();
                usuario.setAltura(scan.nextDouble());
            }
        }

        quantidadeUsuarios++;
        System.out.println("\n" + usuario.toString());
    }

    public static void listarUsuarios(){
        System.out.println("--------------");

        Path pasta = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros");
        try {
            Files.walkFileTree(pasta, new listaArquivos());
            for (int index = 0; index < usuariosList.size(); index++) {
                String arquivo = usuariosList.get(index);
                Path arquivoPath = Paths.get(arquivo);
                
                try (BufferedReader reader = Files.newBufferedReader(arquivoPath)) {
                    System.out.println(reader.readLine().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--------------\r\n");
    }

    public static void pesquisarNome(String nome){
        usuariosCadastrados.stream().filter(u -> u.getNome().contains(nome)).forEach(u -> System.out.println(u.getNome()));
    }

    public static void cadastrarPergunta(){
        try (Scanner scan = new Scanner(System.in)) {
            String novaPergunta;
            String prefixo = (perguntas.size() + 1) + " - ";

            do {
                System.out.println("Digite a nova pergunta");
                novaPergunta = scan.nextLine();
                novaPergunta = prefixo + novaPergunta;
                
                if (perguntas.contains(novaPergunta)) {
                    System.out.println("Essa pergunta já existe!");
                }
            } while (perguntas.contains(novaPergunta));

            Path formularioParh = Paths.get(
                "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
            try (BufferedWriter bw = Files.newBufferedWriter(formularioParh)) {
                bw.newLine();
                bw.write(novaPergunta);
                bw.flush();
            } catch (IOException e) {
                System.out.println("Não foi possível adicionar a pergunta.");
                e.printStackTrace();
            }
            
            perguntas.add(novaPergunta);
        }
    }
    
    public static boolean deletarPergunta(){
        if(perguntas.size() <= 4){
            System.out.println("Há apenas 4 perguntas no formulário, não é possível remover nenhuma delas!");
            return false;
        }

        try (Scanner scan = new Scanner(System.in)) {
            int indice;
            do {
                System.out.println("Digite a numeração da pergunta a ser deletada (exceto 1 a 4):");
                indice = scan.nextInt();
                if(indice <= 4 || indice > perguntas.size()){
                    System.out.println("Número inválido!");
                }
            } while (indice <= 4 && indice > perguntas.size());
            
            perguntas.remove(indice+1);
            Path formularioParh = Paths.get(
                "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
            try {
                Files.delete(formularioParh);
            } catch (IOException e) {
                System.out.println("Não foi possível deletar o arquivo");
                e.printStackTrace();
            }

            if(perguntas.size() > 5){
                int tam = perguntas.size();
                for (int index = indice+1; index < tam; index++) {
                    String string = perguntas.get(index);
                    string.replace(index + " - ", index-1 + " - ");
                }
            }
            
            criarFormulario();
        }
        return true;
    }
    
    public static void exibirMenu() {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Escolha uma opção...\r\n" +
                "1 - Cadastrar o usuário\r\n" +
                "2 - Listar todos usuários cadastrados\r\n" +
                "3 - Cadastrar nova pergunta no formulário\r\n" +
                "4 - Deletar pergunta do formulário\r\n" +
                "5 - Pesquisar usuário por nome");
            int opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    cadastrarPergunta();
                    break;
                case 4:
                    deletarPergunta();
                    break;
                case 5:
                    System.out.println("Digite o nome do usuario:");
                    pesquisarNome(scan.nextLine());
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Usuario pessoa1 = new Usuario();
        cadastrarUsuario(pessoa1);
        criarArquivo(pessoa1);
        
        Usuario pessoa2 = new Usuario();
        cadastrarUsuario(pessoa2);
        criarArquivo(pessoa2);
    }
}
