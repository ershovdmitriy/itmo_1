package lab;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] str){
        List <Personage> personages = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            personages.add(new Personage("Персонаж " + Integer.toString(i + 1), "Дом " + Integer.toString(i + 1),(int) (Math.random() * 10) * 100 + 300));
        }
        Personage p0 = new Personage("Незнайка", "Дом Незнайки", 500);
        Personage doctor = new Personage("Доктор Шприц", "Больница 1", 500);
        Personage factoryOwner = new Personage("Владелец фабрики", "Фабрика 1", 500);
        Personage storeOwner = new Personage("Владелец магазина", "Магазин 1", 500);

        Hospital hospital = new Hospital("Больница 1", "Больница 1", doctor, 50);
        Factory factory = new Factory("Конфетная фабрика \"Заря\"", "Фабрика 1", factoryOwner, 50, "Коврижка конфетной фабрики \"Заря\"");
        Shop shop = new Shop("Магазин 1", "Магазин 1", storeOwner, 75, factory);

        Newspaper n1 = new Newspaper("Новости");

        p0.write("Реклама коврижек", "Коврижки конфетной фабрики \"Заря\"", shop);
        p0.write("Реклама больницы", hospital);
        p0.release(n1);

        for(int i = 0; i < personages.size(); i++){
            personages.get(i).read(n1);
        }
        hospital.healAll();

    }
}