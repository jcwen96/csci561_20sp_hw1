package hw1.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;


public class GenerateTestCase {

    private static String[] algoType = {"BFS", "UCS", "A*"};

    public static void main(String[] args) {
        try {
            Random random = new Random();
            PrintStream output = new PrintStream(new File("input.txt"));
            Input i = new Input(algoType[random.nextInt(3)], random);
            i.print(output);


        } catch (FileNotFoundException e) {
            System.err.println("File write error!");
        }


    }

}