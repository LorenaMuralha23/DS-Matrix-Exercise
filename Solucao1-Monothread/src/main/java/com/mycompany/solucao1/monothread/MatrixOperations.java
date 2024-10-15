package com.mycompany.solucao1.monothread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixOperations {

    private double[][] matrix;
    private int size;

    public MatrixOperations() {
    }
    
    public void defineMatrixSize(int size){
        this.size = size;
        this.matrix = new double[size][size];
    }
    
    
    public double[][] readMatrix(File matrixFile){
        try {
            if (this.matrix == null){
                throw new Exception();
            }
             
            BufferedReader bufferedReader = new BufferedReader(new FileReader(matrixFile));
            
            String rowRead;
            int rowIndex = 0;
            while((rowRead = bufferedReader.readLine()) != null){ //while there are rows to read
                String[] valuesOfRow = rowRead.split("\\s");
                
                for (int i = 0; i < this.size; i++) {
                    matrix[rowIndex][i] = Double.parseDouble(valuesOfRow[i]);
                }
                rowIndex++;
            }
        } catch (Exception e) {
            System.out.println("You have to define the size of the matrix!");
        }
        
        return matrix;
    }
    
    public void calculateMatrix(double[][] matrix1, double[][] matrix2) {
    if (this.size > 0) {
        BigDecimal[][] resultantMatrix = new BigDecimal[this.size][this.size];

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
        
        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                System.out.print(resultantMatrix[row][column] + " ");
            }
            System.out.println("");
        }
    }
}
    
}
