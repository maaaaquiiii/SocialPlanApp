package org.example.view;

import java.util.Scanner;

public class View {
    protected static Scanner scanner = new Scanner(System.in);


    public static Scanner getScanner() {
        return scanner;
    }

}
