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

    public static void cadastrarUsuario(Usuario usuario){
        Path formularioPath = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
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
