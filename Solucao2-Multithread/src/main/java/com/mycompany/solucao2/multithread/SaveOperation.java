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
            for (int row = 0; row < matrixToSave.length; row++) {
                strBuilder.setLength(0);

                for (int col = 0; col < matrixToSave[row].length; col++) {
                    strBuilder.append(matrixToSave[row][col].toPlainString());

                    if (col < matrixToSave[row].length - 1) {
                        strBuilder.append(" ");
                    }
                }

                writer.write(strBuilder.toString());
                writer.newLine(); 
            }

            writer.close();
            System.out.println("Resultant matrix saved successfully!");
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

}
