package com.mycompany.solucao2.multithread;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatrixCalculatorThread implements Runnable {

    private int startRow;
    private int endRow;

    public MatrixCalculatorThread(int startRow, int endRow) {
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {

        try {
            if (this.startRow >= 0 && this.endRow >= 0) {
                for (int row = 0; row <= this.startRow; row++) {
                    for (int column = 0; column < Main.matrixSize; column++) {
                        BigDecimal sum = BigDecimal.ZERO;

                        for (int i = 0; i < Main.matrixSize; i++) {
                            // Multiplica os elementos correspondentes e acumula a soma
                            BigDecimal product = BigDecimal.valueOf(Main.matrix1[row][i])
                                    .multiply(BigDecimal.valueOf(Main.matrix2[i][column]));
                            sum = sum.add(product);

                        }

                        // Armazena o resultado na matriz resultante com 4 casas decimais
                        Main.resultantMatrix[row][column] = sum.setScale(4, RoundingMode.HALF_UP);
                    }
                }

            } else {
                System.out.println("Error!");
            }
            Main.barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(MatrixCalculatorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

}
