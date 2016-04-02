package com.univer.db.lab_1.base;

public class NotUniqueIdentifier extends Exception {
    public NotUniqueIdentifier() {
        super("Помилка П-01. Повторення значення ключового поля.");
    }
}
