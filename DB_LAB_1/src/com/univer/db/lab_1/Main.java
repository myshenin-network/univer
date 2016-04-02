package com.univer.db.lab_1;

import com.univer.db.lab_1.gui.UI;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    private static UI ui;

    public static void main(String[] args) {
        try {
            ui = new UI();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        while (ui.makeMenu()){}
    }
}
