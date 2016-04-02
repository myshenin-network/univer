package com.univer.oop.lab_2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Назва регіону: ");
        String reg_name = input.next();

        System.out.print("Кількість міст: ");
        City[] cities = new City[input.nextInt()];

        String city_name = null;
        int pop = 0;
        for (int i = 0; i < cities.length; i++){
            System.out.print("Назва міста: ");
            city_name = input.next();

            System.out.print("Кількість населення: ");
            pop = input.nextInt();

            cities[i] = new City(city_name, pop);
        }

        System.out.print("Назва мегаполісу: ");
        city_name = input.next();

        System.out.print("Кількість населення: ");
        pop = input.nextInt();

        System.out.print("Ліміт швидкості: ");
        int speed_lim = input.nextInt();

        MegaCity megaCity = new MegaCity(city_name, pop, speed_lim);
        Region region = new Region(reg_name, megaCity, cities);
        region.diplay();
    }
}
