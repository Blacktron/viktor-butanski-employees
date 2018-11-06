package com.company;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter file path. Example: D:\\Downloads\\Employees Data.txt");
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();

        File file = new File(filePath);
        if (file.exists()) {
            EmployeePair pairs = new EmployeePair(filePath);
            pairs.findPair();
        } else {
            System.out.println("This is not a valid file path or the file does not exist!");
        }
    }
}