package com.mycompany.solucao2.multithread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Savepoint;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import javax.swing.JFileChooser;

public class Main {

    public static double[][] matrix1;
    public static double[][] matrix2;
    public static BigDecimal[][] resultantMatrix;
    public static int matrixSide = 2;
    public static SaveOperation svOperation;
    public static CyclicBarrier barrier;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        resultantMatrix = new BigDecimal[2][2];
        
        svOperation = new SaveOperation(resultantMatrix);
        barrier = new CyclicBarrier(2, svOperation);
        
        File matrixFile1 = openFileChooser("Choose the first matrix file");
        matrix1 = readMatrix(matrixFile1);
        File matrixFile2 = openFileChooser("Choose the second matrix file");
        matrix2 = readMatrix(matrixFile2);
        
        System.out.println("Starting the Calculation...");
        System.out.println(matrix1.length);
        
        Runnable r1 = new MatrixCalculatorThread(0, (matrixSide/2) - 1);
        Runnable r2 = new MatrixCalculatorThread(matrixSide/2,  matrixSide - 1);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        
        System.out.println("Calculation the first time:");
        t1.start();
        t2.start();
        
        
//        
//        
//        System.out.println("Type the size of the matrices: ");
//        matricesSize = input.nextInt();
//
//        resultantMatrix = new BigDecimal[matricesSize][matricesSize];
//        
//        Thread t1 = new Thread(new MatrixCalculatorThread(0, (matricesSize/2)-1));
//        Thread t2 = new Thread(new MatrixCalculatorThread(matricesSize/2, matricesSize-1));
//

//        
//        
//        t1.start();
//        t2.start();
        
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

    public static double[][] readMatrix(File matrixFile) {
        double[][] matrixRead = new double[matrixSide][matrixSide];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(matrixFile));

            String rowRead;
            int rowIndex = 0;
            while ((rowRead = bufferedReader.readLine()) != null) { //while there are rows to read
                String[] valuesOfRow = rowRead.split("\\s");

                for (int i = 0; i < matrixSide; i++) {
                    matrixRead[rowIndex][i] = Double.parseDouble(valuesOfRow[i]);
                }
                rowIndex++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("You have to define the size of the matrix!");
        }

        return matrixRead;
    }
    
    public static void saveMatrixToFile(BigDecimal[][] matrix) {
        StringBuilder strBuilder = new StringBuilder();
        String userDir = System.getProperty("user.dir");
        strBuilder.append(userDir);
        strBuilder.append("\\resultants\\resultant.txt");
        String filePath = strBuilder.toString();

        strBuilder.setLength(0);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Itera sobre as linhas da matriz
            for (int row = 0; row < matrix.length; row++) {
                strBuilder.setLength(0);

                // Itera sobre as colunas da linha atual
                for (int col = 0; col < matrix[row].length; col++) {
                    // Adiciona o valor da célula ao StringBuilder
                    strBuilder.append(matrix[row][col].toPlainString());

                    // Adiciona um espaço entre os números, mas não no final da linha
                    if (col < matrix[row].length - 1) {
                        strBuilder.append(" ");
                    }
                }

                // Escreve a linha no arquivo
                writer.write(strBuilder.toString());
                writer.newLine(); // Nova linha após cada linha da matriz
            }

            writer.close();
            System.out.println("Resultant matrix saved successfully!");
        } catch (IOException e) {
            e.printStackTrace(); // Trata a exceção se ocorrer um erro ao gravar no arquivo
        }
    }
}
