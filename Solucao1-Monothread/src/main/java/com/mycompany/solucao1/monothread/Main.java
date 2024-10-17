package com.mycompany.solucao1.monothread;

import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class Main {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MatrixOperations matrixOperations = new MatrixOperations();

        System.out.println("Type the size of the matrix: ");
        int size = input.nextInt();

        matrixOperations.defineMatrixSize(size);
        
        File matrixFile1 = openFileChooser("Choose the first matrix file");
        double[][] matrix1 = matrixOperations.readMatrix(matrixFile1);
        File matrixFile2 = openFileChooser("Choose the second matrix file");
        double[][] matrix2 = matrixOperations.readMatrix(matrixFile2);

        
        matrixOperations.calculateMatrix(matrix1, matrix2);
    }

    public static File openFileChooser(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(dialogTitle);

        // Define que pode selecionar apenas arquivos (pode ser modificado para pastas)
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Abre o FileChooser e captura a resposta do usuário (se clicou em "Abrir" ou "Cancelar")
        int result = fileChooser.showOpenDialog(null);

        // Se o usuário clicou em "Abrir", pegamos o arquivo selecionado
        if (result == JFileChooser.APPROVE_OPTION) {
            File matrixFile = fileChooser.getSelectedFile();
            return matrixFile;
        } else {
            System.out.println("No file selected.");
            return null;
        }
    }

    
}
