package com.sistemaDeCadastros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;

public class Cadastro {
    private static List<String> perguntas = new ArrayList<>();
    private static int quantidadeUsuarios = 0;

    public static void cadastrarUsuario(Usuario usuario){
        Path formularioPath = Paths.get("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");
        Scanner scan = new Scanner(System.in);

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

        quantidadeUsuarios++;
        System.out.println("\n" + usuario.toString());
    }

    public static void criarArquivo(Usuario usuario){
        String path = "C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\";
        String nomeDoArquivo = usuario.getNome().toUpperCase().replace(" ", "");
        nomeDoArquivo = quantidadeUsuarios + "-" + nomeDoArquivo + ".txt";
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
