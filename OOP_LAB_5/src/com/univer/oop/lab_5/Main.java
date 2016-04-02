package com.univer.oop.lab_5;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            if(Math.random() > 0.5) makeDumm();
            else ioEx();
        } catch (DummException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeDumm() throws DummException {
        throw new DummException();
    }

    private static void ioEx() throws IOException {
        throw new IOException();
    }
}
