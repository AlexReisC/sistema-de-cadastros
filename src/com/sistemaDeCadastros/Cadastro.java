package com.sistemaDeCadastros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;

class ListaArquivos extends SimpleFileVisitor<Path>{

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String arquivo = file.toAbsolutePath().toString();
        if(arquivo.endsWith(".txt") && !(arquivo.contains("formulario.txt"))){
            Cadastro.usuariosCadastrados.add(arquivo);
        }
        return FileVisitResult.CONTINUE;
    }
    
}

public class Cadastro {
    private static List<String> perguntas = new ArrayList<>();
    public static List<String> usuariosCadastrados = new ArrayList<>();

    public static void criarArquivo(List<String> respotas) {
        Path path = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\");
        try {
            Files.walkFileTree(path, new ListaArquivos());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String nomeDoArquivo = (usuariosCadastrados.size()+1) + "-" + respotas.getFirst().toUpperCase().replace(" ", "") + ".txt";
        String pathString = path.toString() + "\\" + nomeDoArquivo;
        Path arquivoPath = Paths.get(pathString);

        try {
            Files.createFile(arquivoPath);
        } catch (IOException e) {
            System.out.println("O arquivo deste usuário já existe");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(arquivoPath)) {
            for (int i = 0; i < respotas.size(); i++) {
                writer.write(respotas.get(i));
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarUsuario() {
        List<String> respostas = new ArrayList<>();

        Path formularioPath = Paths.get(
                "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
        try (Scanner scan = new Scanner(System.in)) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(formularioPath)) {
                String linha;
                while ((linha = bufferedReader.readLine()) != null) {
                    System.out.println(linha);
                    String texto = scan.nextLine();
                    respostas.add(texto);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        criarArquivo(respostas);
    }

    public static void listarUsuarios(){
        System.out.println("--------------");

        Path pasta = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros");
        try {
            Files.walkFileTree(pasta, new ListaArquivos());
            for (int index = 0; index < usuariosCadastrados.size(); index++) {
                String arquivo = usuariosCadastrados.get(index);
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
        System.out.println();

        Path pasta = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros");
        Path arquivoPath;
        try {
            Files.walkFileTree(pasta, new ListaArquivos());
            for (int index = 0; index < usuariosCadastrados.size(); index++) {
                String arquivo = usuariosCadastrados.get(index);
                arquivoPath = Paths.get(arquivo);
                
                try (BufferedReader reader = Files.newBufferedReader(arquivoPath)) {
                    String texto = reader.readLine().toString();
                    if(texto.contains(nome)){
                        System.out.println(texto);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cadastrarPergunta(){
        lerFormulario();
        try (Scanner scan = new Scanner(System.in)) {
            String novaPergunta;
            String prefixo = (perguntas.size() + 1) + " - ";

            do {
                System.out.println("Digite a nova pergunta");
                novaPergunta = scan.nextLine();
                if (perguntas.contains(novaPergunta)) {
                    System.out.println("Essa pergunta já existe!");
                }
            } while (perguntas.contains(novaPergunta));
            
            novaPergunta = prefixo + novaPergunta;
            perguntas.add(novaPergunta);
            
            Path formularioPath = Paths.get(
                "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
                try (BufferedWriter bw = Files.newBufferedWriter(formularioPath)) {
                for (int i = 0; i < perguntas.size(); i++) {
                    bw.write(perguntas.get(i));
                    bw.newLine();
                }
                bw.flush();
            } catch (IOException e) {
                System.out.println("Não foi possível adicionar a pergunta.");
                e.printStackTrace();
            }
        }
    }
    
    public static boolean deletarPergunta(){
        lerFormulario();
        if(perguntas.size() == 4){
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
            
            perguntas.remove(indice-1);
            
            int tam = perguntas.size();
            if(tam >= indice){
                for (int index = indice-1; index < tam; index++) {
                    String string = perguntas.get(index);
                    String numeracaoAntiga = (index+2) + " - ";
                    String numeracaoAtual = (index+1) + " - ";
                    string = string.replace(numeracaoAntiga, numeracaoAtual);
                    perguntas.set(index, string);
                }
            }

            Path formularioPath = Paths.get(
                "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
            
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
            scan.nextLine();

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
                    String nome = scan.nextLine();
                    pesquisarNome(nome);
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    public static void lerFormulario(){
        Path formularioPath = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
        try (BufferedReader reader = Files.newBufferedReader(formularioPath)) {
            String linha;
            while((linha = reader.readLine()) != null){
                perguntas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Não foi possivel ler o arquivo.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        exibirMenu();

    }
}
