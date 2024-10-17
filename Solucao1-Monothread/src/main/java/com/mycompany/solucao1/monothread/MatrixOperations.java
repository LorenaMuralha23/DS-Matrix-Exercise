package com.mycompany.solucao1.monothread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixOperations {

    private double[][] matrix;
    private int size;

    public MatrixOperations() {
    }

    public void defineMatrixSize(int size) {
        this.size = size;
        this.matrix = new double[size][size];
    }

    public double[][] readMatrix(File matrixFile) {
        try {
            if (this.matrix == null) {
                throw new Exception();
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(matrixFile));

            String rowRead;
            int rowIndex = 0;
            while ((rowRead = bufferedReader.readLine()) != null) { //while there are rows to read
                String[] valuesOfRow = rowRead.split("\\s");

                for (int i = 0; i < this.size; i++) {
                    matrix[rowIndex][i] = Double.parseDouble(valuesOfRow[i]);
                }
                rowIndex++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("You have to define the size of the matrix!");
        }

        return matrix;
    }

    public void calculateMatrix(double[][] matrix1, double[][] matrix2) {
        if (this.size > 0) {
            BigDecimal[][] resultantMatrix = new BigDecimal[this.size][this.size];

            //time variables
            long startTime = System.nanoTime();
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            long startCpuTime = bean.getCurrentThreadCpuTime();

            for (int row = 0; row < this.size; row++) {
                for (int column = 0; column < this.size; column++) {
                    // Inicializa o valor atual da célula com BigDecimal.ZERO
                    BigDecimal sum = BigDecimal.ZERO;

                    for (int i = 0; i < this.size; i++) {
                        // Multiplica os elementos correspondentes e acumula a soma
                        BigDecimal product = BigDecimal.valueOf(matrix1[row][i])
                                .multiply(BigDecimal.valueOf(matrix2[i][column]));
                        sum = sum.add(product);
                    }

                    // Armazena o resultado na matriz resultante com 4 casas decimais
                    resultantMatrix[row][column] = sum.setScale(4, RoundingMode.HALF_UP);
                }
            }
            
            // Fim da medição do tempo total de relógio
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;

            // Fim da medição do tempo total de CPU
            long endCpuTime = bean.getCurrentThreadCpuTime();
            long elapsedCpuTime = endCpuTime - startCpuTime;

            // Converte o tempo para milissegundos
            double elapsedTimeInMs = elapsedTime / 1_000_000.0;
            double elapsedCpuTimeInMs = elapsedCpuTime / 1_000_000.0;

            System.out.println("Real clock time: " + elapsedTimeInMs + " ms");
            System.out.println("CPU time: " + elapsedCpuTimeInMs + " ms");
            
            saveMatrixToFile(resultantMatrix);

        }
    }

    public void saveMatrixToFile(BigDecimal[][] matrix) {
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
