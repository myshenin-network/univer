package com.univer.db.lab_1.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Picture implements Comparable<Picture>{
    private int id;
    private String path;
    private int size;
    private Date update;
    private String user;
    private COLOR_MODEL model;
    private int whiteBalance;
    private double diaphragm;
    private double shutterSpeed;
    private String camera;
    private boolean inserted;

    public Picture() {
    }

    public Picture(int id, String path, int size, Date update, String user, COLOR_MODEL model, int whiteBalance, double diaphragm, double shutterSpeed, String camera) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.update = update;
        this.user = user;
        this.model = model;
        this.whiteBalance = whiteBalance;
        this.diaphragm = diaphragm;
        this.shutterSpeed = shutterSpeed;
        this.camera = camera;
        this.inserted = false;
    }

    public Picture(Scanner input){
        System.out.print("id: ");
        this.id = input.nextInt();

        System.out.print("Шлях до файлу: ");
        this.path = input.next();

        System.out.print("Розмір: ");
        this.size = input.nextInt();

        this.update = new Date();

        System.out.print("Користувач: ");
        this.user = input.next();

        System.out.print("Кольворова модель(\n0 - CMYK,\n1 - RGB,\n2 - LAB,\n3 - HSB,\n4 - GREY,\n5 - BLACKWHITE): ");
        switch (input.nextInt()){
            case 0:{
                this.model = COLOR_MODEL.CMYK;
            } break;

            case 1:{
                this.model = COLOR_MODEL.RGB;
            } break;

            case 2:{
                this.model = COLOR_MODEL.LAB;
            } break;

            case 3:{
                this.model = COLOR_MODEL.HSB;
            } break;

            case 4:{
                this.model = COLOR_MODEL.GREY;
            } break;

            case 5:{
                this.model = COLOR_MODEL.BLACKWHITE;
            } break;
        }

        System.out.print("Баланс білого: ");
        this.whiteBalance = input.nextInt();

        System.out.print("Діафрагма: ");
        this.diaphragm = input.nextDouble();

        System.out.print("Витримка: ");
        this.shutterSpeed = input.nextDouble();

        System.out.print("Модель камери: ");
        this.camera = input.next();

        this.inserted = false;
    }

    @Override
    public String toString() {
        return id + ";" + path + ";" + size + ";" + new SimpleDateFormat("dd-MM-yyyy/hh:mm").format(update) + ";" + user + ";" + model.toString() + ";" + whiteBalance +
                ";" + diaphragm + ";" + shutterSpeed + ";" + camera + "\n";
    }

    public static Picture fromString(String picString) throws ParseException {
        String[] characteristics = picString.split(";");
        return new Picture(Integer.parseInt(characteristics[0]), characteristics[1], Integer.parseInt(characteristics[2]),
                new SimpleDateFormat("dd-MM-yyyy/hh:mm").parse(characteristics[3]), characteristics[4], COLOR_MODEL.valueOf(characteristics[5]),
                Integer.parseInt(characteristics[6]), Double.parseDouble(characteristics[7]), Double.parseDouble(characteristics[8]), characteristics[9]);
    }
    @Override
    public int compareTo(Picture picture) {
        return this.id - picture.id;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    public int getId() {
        return id;
    }

    public boolean aboutEquals(Picture picture, String mask){
        boolean[] fields = new boolean[10];
        boolean generalResult = true;

        fields[0] = mask.charAt(0) == '0'? true: this.camera.equals(picture.camera);
        fields[1] = mask.charAt(1) == '0'? true: this.shutterSpeed == picture.shutterSpeed;
        fields[2] = mask.charAt(2) == '0'? true: this.diaphragm == picture.diaphragm;
        fields[3] = mask.charAt(3) == '0'? true: this.whiteBalance == picture.whiteBalance;
        fields[4] = mask.charAt(4) == '0'? true: this.model.equals(picture.model);
        fields[5] = mask.charAt(5) == '0'? true: this.user.equals(picture.user);
        fields[6] = mask.charAt(6) == '0'? true: this.update.equals(picture.update);
        fields[7] = mask.charAt(7) == '0'? true: this.size == picture.size;
        fields[8] = mask.charAt(8) == '0'? true: this.path.equals(picture.path);
        fields[9] = mask.charAt(9) == '0'? true: this.id == picture.id;
        for(int i = 0; i < 10; i++)
            generalResult = generalResult&&fields[i];
        return generalResult;
    }
}
