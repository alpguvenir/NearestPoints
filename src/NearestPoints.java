/*
 * NearestCoordinates.java
 * 29 October 2017
 * Version 1.0.0
 *  Alp Güvenir
*/

// /Users/AlpGuvenir/Desktop/n2c/sample_in_out/sample_input_2_8.tsv

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class NearestPoints {

    public static void main(String[] args) throws IOException {
        // Scanner definition
        Scanner keyboard = new Scanner(System.in);

        // Path of the TSV file
        String path = getPath(keyboard);

        // Copy the values in TSV file to a 2D Double array
        Double[][] coordinateMatrix = tsvToMatrix(path);

        // Print the matrix filled from the file
        // printMatrix(coordinateMatrix);

        // Array for holding the pair of coordinates
        int[] pair;

        // Declarations for timing
        long startTime;
        long endTime;

        // Find the nearest coordinates
        if(coordinateMatrix.length > 1) {

            // Timing begin
            startTime = System.nanoTime();
            pair = nearestCoordinates(coordinateMatrix);
            // Timing end
            endTime = System.nanoTime();
            System.out.println("Time elasped: " + (endTime - startTime)/1000 + " nanoseconds");

            // Write the output into a txt file
            writeOutput(pair[0], pair[1], keyboard, coordinateMatrix[pair[0]], coordinateMatrix[pair[1]]);
        }
        else
            System.out.println("There is only one point in the file, nothing to compare");
    } // main()

    public static String getPath(Scanner keyboard) {
        System.out.println("Please provide the path of the tab seperated input file: ");
        String inputPath = keyboard.next();
        return inputPath;
    } // getPath()

    public static String setPath(Scanner keyboard) {
        System.out.println("Please provide the path of the output \'.txt\' file, \nthe location you would like the output file to be saved: ");
        String outputPath = keyboard.next();
        return outputPath;
    } // setPath()

    public static String setFileName(Scanner keyboard) {
        System.out.println("Please provide a name for the output file,\nDo not put any extensions, it is automatically saved as a \'.txt\' file: ");
        String outputPath = keyboard.next();
        outputPath += ".txt";
        return outputPath;
    } // setFileName()

    public static Double[][] tsvToMatrix(String path) throws IOException {
        // Store the TSV values in a double (2D) array
        String[] row;
        Double[][] resultArray = null;

        int rowIndex = 0;
        int colIndex = 0;

        try {
            // Get the TSV row as a List of String
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            // Find the number of columns from the length of the first row
            row = lines.get(0).split("\t");
            resultArray = new Double[lines.size()][row.length];

            for (rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
                //Fill each row of the matrix by splitting the tab-separated row
                row = lines.get(rowIndex).split("\t");

                for (colIndex = 0; colIndex < row.length; colIndex++) {
                    resultArray[rowIndex][colIndex] = Double.parseDouble(row[colIndex]);
                } // Iterating inside a row

            } // Iterating for each row

        } catch (NoSuchFileException e) {
            // If such a file does not exist
            System.out.println("The path provided for the file is not valid\n" + e);
            System.exit(1);

        } catch (IndexOutOfBoundsException e) {
            // If the file is empty
            System.out.println("The file provided is empty\n" + e);
            System.exit(1);

        } catch (NumberFormatException e) {
            System.out.println("The input in the file provided is in the wrong format\n" + e);
            System.out.println("Error on row:" + rowIndex + " and column: " + colIndex);
            System.exit(1);

        } finally {
            System.out.println("The file provided is valid");
        }

        return resultArray;
    } // tsvToMatrix()

    public static void printMatrix(Double[][] coordinateMatrix) {

        for(int rowIndex = 0; rowIndex < coordinateMatrix.length; rowIndex++) {
            for(int colIndex = 0; colIndex < coordinateMatrix[0].length; colIndex++)
                System.out.print(coordinateMatrix[rowIndex][colIndex] + " ");
            System.out.println();
        }
    } // printMatrix()

    public static int[] nearestCoordinates(Double[][] coordinateMatrix) {
        // Size is 2 since we are looking for a pair
        int[] nearestCoordinates = new int[2];
        double minimumDistance = Double.POSITIVE_INFINITY;
        double squaredDistance = 0;
        double squareroottDistance = 0;

        // Two Double arrays for calculations
        Double[] row1 = null;
        Double[] row2 = null;

        // Row number of two nearest coordinates, initally -1
        nearestCoordinates[0] = -1;
        nearestCoordinates[1] = -1;

        // Outer loop iterating on rows
        for(int rowIndex = 0; rowIndex < coordinateMatrix.length - 1; rowIndex++) { // In the order of O(N-1)

            // Assign row1
            row1 = coordinateMatrix[rowIndex];

            // Inner loop for iterating on consecutive rows
            for(int innerRowIndex = rowIndex + 1; innerRowIndex < coordinateMatrix.length; innerRowIndex++) { // In the order of O(((N-1)+1)/2)

                // distance reinitialized to 0
                squaredDistance = 0;

                // Assign row2
                row2 = coordinateMatrix[innerRowIndex];
                for(int columnIndex = 0; columnIndex < row1.length; columnIndex++) { // In the order of O(M)
                    squaredDistance += Math.pow((row1[columnIndex] - row2[columnIndex]), 2);
                }

                // Taking the square root
                // Since we do not need the absolute distance values, we no dot need to take the square root**
                // squarerootDistance = Math.sqrt(distance);

                // Euclidean distance comparison
                if(squaredDistance < minimumDistance) {
                    nearestCoordinates[0] = rowIndex;
                    nearestCoordinates[1] = innerRowIndex;

                    // Assign the new minimum distance
                    minimumDistance = squaredDistance;
                }
            }
        }

        System.out.println("Points with minimum distance are at:");
        System.out.println("Row:" + (nearestCoordinates[0] + 1) + " and Row:" + (nearestCoordinates[1] + 1));

        return nearestCoordinates;
    } // nearestCoordinates()

    public static void writeOutput(int minRowIndex1, int minRowIndex2, Scanner keyboard, Double[] row1, Double[] row2) throws IOException {

        // Decimal format, if the number is a floating point number format according to 1 decimal place

        // DecimalFormat df = new DecimalFormat("0"); // for rounding to nearest integer
        DecimalFormat df = new DecimalFormat("0.#");

        // Name of the output '.txt' file
        String outputFileName = setFileName(keyboard);

        // Path for saving the file
        String outputPath = setPath(keyboard);

        // The output text
        int index = 0;

        // First row
        String outputText = (minRowIndex1 + 1) + ":"; // minRowIndex1 + 1 since index starts from 0
        for(index = 0; index < row1.length - 1; index++) {
            outputText += df.format(row1[index]) + "\t";
        }
        outputText += df.format(row1[index]) + "\n";

        // Second row
        outputText += (minRowIndex2 + 1) + ":"; // minRowIndex2 + 1 since index starts from 0
        for(index = 0; index < row2.length - 1; index++) {
            outputText += df.format(row2[index]) + "\t";
        }
        outputText += df.format(row2[index]) + "\n";

        // File creation
        File directory = new File (outputPath);
        File output = new File (directory, outputFileName);

        // Using buffered writer to write the file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(output));
            out.write(outputText);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("The path provided is not valid\n" + e);
            System.exit(1);
        }

    } // writeOutput()

}
