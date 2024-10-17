/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.solucao2.multithread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

/**
 *
 * @author User
 */
public class SaveOperation implements Runnable {

    private BigDecimal[][] matrixToSave;

    public SaveOperation(BigDecimal[][] matrixToSave) {
        this.matrixToSave = matrixToSave;
    }

    @Override
    public void run() {
        StringBuilder strBuilder = new StringBuilder();
        String userDir = System.getProperty("user.dir");
        strBuilder.append(userDir);
        strBuilder.append("\\resultants\\resultant.txt");
        String filePath = strBuilder.toString();

        strBuilder.setLength(0);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Itera sobre as linhas da matriz
            for (int row = 0; row < matrixToSave.length; row++) {
                strBuilder.setLength(0);

                // Itera sobre as colunas da linha atual
                for (int col = 0; col < matrixToSave[row].length; col++) {
                    // Adiciona o valor da célula ao StringBuilder
                    strBuilder.append(matrixToSave[row][col].toPlainString());

                    // Adiciona um espaço entre os números, mas não no final da linha
                    if (col < matrixToSave[row].length - 1) {
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
