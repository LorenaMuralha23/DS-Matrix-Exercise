package com.mycompany.solucao2.multithread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import javax.swing.JFileChooser;

public class Main {

    public static double[][] matrix1;
    public static double[][] matrix2;
    public static BigDecimal[][] resultantMatrix;
    public static int matrixSize = 0;
    public static SaveOperation svOperation;
    public static CyclicBarrier barrier;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Type the size of the matrices: ");
        matrixSize = input.nextInt();

        resultantMatrix = new BigDecimal[matrixSize][matrixSize];

        svOperation = new SaveOperation(resultantMatrix);
        barrier = new CyclicBarrier(2, svOperation);

        File matrixFile1 = openFileChooser("Choose the first matrix file");
        matrix1 = readMatrix(matrixFile1);
        File matrixFile2 = openFileChooser("Choose the second matrix file");
        matrix2 = readMatrix(matrixFile2);

        Runnable r1 = new MatrixCalculatorThread(0, (matrixSize / 2) - 1);
        Runnable r2 = new MatrixCalculatorThread(matrixSize / 2, matrixSize - 1);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
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
        double[][] matrixRead = new double[matrixSize][matrixSize];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(matrixFile));

            String rowRead;
            int rowIndex = 0;
            while ((rowRead = bufferedReader.readLine()) != null) { //while there are rows to read
                String[] valuesOfRow = rowRead.split("\\s");

                for (int i = 0; i < matrixSize; i++) {
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

}
