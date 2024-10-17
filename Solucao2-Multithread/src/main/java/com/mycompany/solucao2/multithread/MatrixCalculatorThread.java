/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solucao2.multithread;

import static com.mycompany.solucao2.multithread.Main.matrixSide;
import static com.mycompany.solucao2.multithread.Main.resultantMatrix;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
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
                    for (int column = 0; column < Main.matrixSide; column++) {
                        BigDecimal sum = BigDecimal.ZERO;
                        
                        for (int i = 0; i < Main.matrixSide; i++) {
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
        } catch (InterruptedException ex) {
            Logger.getLogger(MatrixCalculatorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
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
