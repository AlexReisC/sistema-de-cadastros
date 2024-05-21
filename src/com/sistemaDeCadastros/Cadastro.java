package com.sistemaDeCadastros;

import java.io.BufferedReader;
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

    public static Path criarFormulario(String path){
        Path formularioPath = Paths.get(path);
        try {
            Files.createFile(formularioPath);
        } catch (IOException e) {
            System.out.println("Arquivo já existe");
        }
        
        return formularioPath;
    }

    public static void cadastrarUsuario(Usuario usuario, Path formularioPath){
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
        
        scan.close();
        System.out.println("\n" + usuario.toString());
    }

    public static void main(String[] args) {
        Path form = criarFormulario("C:\\Estudos e Projetos\\Projetos\\Sistema de Cadastros\\src\\com\\sistemaDeCadastros\\formulario.txt");

        Usuario pessoa1 = new Usuario();
        cadastrarUsuario(pessoa1, form);

        
    }
}
