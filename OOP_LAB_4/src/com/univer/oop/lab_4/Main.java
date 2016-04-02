package com.univer.oop.lab_4;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print(">> ");
        Scanner input = new Scanner(System.in);
        String[] words = input.nextLine().split(" ");

        int count_letters_a = 0;
        int count_words_with_3a = 0;
        for (int i = 0; i < words.length; i++){
            for(int j = 0 ; j < words[i].length(); j++){
                if(words[i].charAt(j) == 'a') count_letters_a++;
                if(count_letters_a == 3) count_words_with_3a++;
            }
            count_letters_a = 0;
        }

        System.out.println("Кількість слів з трьома \'а\': " + count_words_with_3a);
    }
}
