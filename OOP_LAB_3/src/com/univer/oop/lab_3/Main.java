package com.univer.oop.lab_3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("n = ");
        int n = input.nextInt();

        System.out.print("a = ");
        int a = input.nextInt();

        System.out.print("b = ");
        int b = input.nextInt();

        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = i;
            if ((i < a)&&(i > b)) System.out.println(i + " = " + true);
            else System.out.println(i + " = " + false);
        }
    }
}
