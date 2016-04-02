package com.univer.db.lab_1.gui;

import com.univer.db.lab_1.base.BaseDriver;
import com.univer.db.lab_1.base.ElementIsNotOccurred;
import com.univer.db.lab_1.base.NotUniqueIdentifier;
import com.univer.db.lab_1.base.Picture;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class UI {
    private Scanner console;
    private BaseDriver baseDriver;
    private String workspace;

    public UI() throws ParserConfigurationException, IOException, SAXException {
        console = new Scanner(System.in);

        Document settings = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("D:\\univer\\DB_LAB_1\\src\\com\\univer\\db\\lab_1\\res\\settings.xml"));
        workspace = settings.getChildNodes().item(0).getChildNodes().item(1).getTextContent();
    }

    public boolean makeMenu(){
        System.out.print("Виберіть дію над базою даних:\n" +
                "1 - створити/вибрати\n" +
                "2 - роздрокувати\n" +
                "3 - покомандна обробка\n" +
                "4 - видалити\n" +
                "5 - створити файл групової обробки\n" +
                "6 - застосувати файл групової обробки\n" +
                "7 - вихід\n" +
                ">> ");
        switch (console.nextInt()){
            case 1:{
                System.out.print(">> ");
                try {
                    baseDriver = new BaseDriver(workspace + console.next(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            case 2:{
                try {
                    baseDriver.print();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            }

            case 3:{
                while (makeRecordMenu()){}
                return true;
            }

            case 4:{
                baseDriver.delete();
                return true;
            }

            case 5:{
                try {
                    baseDriver.optimizeMessage(baseDriver.makeMessage(console));
                } catch (NullPointerException e0){
                    try {
                        baseDriver = new BaseDriver(workspace + "fiction.txt", this);
                        baseDriver.optimizeMessage(baseDriver.makeMessage(console));
                        baseDriver.delete();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return true;
            }

            case 6:{
                System.out.print(">> ");
                try {
                    baseDriver.executeGroup(console.next());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (NotUniqueIdentifier notUniqueIdentifier) {
                    notUniqueIdentifier.printStackTrace();
                } catch (ElementIsNotOccurred elementIsNotOccurred) {
                    elementIsNotOccurred.printStackTrace();
                }
                return true;
            }
            case 7:{
                baseDriver.exit();
                return false;
            }
        }
        return false;
    }

    public String getWorkspace() {
        return workspace;
    }

    private boolean makeRecordMenu(){
        System.out.print("Оберіть дію над записом:\n" +
                "1 - додати\n" +
                "2 - змінити\n" +
                "3 - пошук\n" +
                "4 - видалити\n" +
                "5 - назад\n" +
                ">> ");
        switch (console.nextInt()){
            case 1:{
                try {
                    baseDriver.insert(new Picture(console));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NotUniqueIdentifier notUniqueIdentifier) {
                    notUniqueIdentifier.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            }

            case 2:{
                System.out.print("ID шуканого запису: ");
                try {
                    baseDriver.replace(console.nextInt(), new Picture(console));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ElementIsNotOccurred elementIsNotOccurred) {
                    elementIsNotOccurred.printStackTrace();
                }
                return true;
            }

            case 3:{
                System.out.print("Введіть маску пошуку: ");
                String mask = console.next();

                System.out.println("Введіть шукані дані (непотрібне заповнити нулями)");
                try {
                    System.out.println(baseDriver.search(new Picture(console), mask).toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            }

            case 4:{
                System.out.print("ID шуканого запису: ");
                try {
                    baseDriver.remove(console.nextInt());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ElementIsNotOccurred elementIsNotOccurred) {
                    elementIsNotOccurred.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
