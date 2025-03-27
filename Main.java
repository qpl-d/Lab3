import Lab.Guest;
import Lab.Hotel;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static PrintStream out = System.out;
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {

        // 1. Создание гостиницы
        Hotel hotel = new Hotel(25);

        // 2. Вывод списка номеров
        hotel.printHotelStatus();

        // 3. Поселение человека в заданный номер
        Guest guest1 = new Guest("Сергей Иванов");
        Guest guest2 = new Guest("Иван Петров");
        Guest guest3 = new Guest("Петр Сергеев");

        hotel.checkInToRoom(guest1, 1);
        hotel.checkInToRoom(guest2, 3);
        hotel.checkInToRoom(guest3, 5);

        // 4. Поселение человека в первый свободный номер
        Guest guest4 = new Guest("Анна Смирнова");

        hotel.checkInToFirstAvailable(guest4);

        // 5. Поселение группы людей
        Guest guest5 = new Guest("Дмитрий Козлов");
        Guest guest6 = new Guest("Екатерина Волкова");
        Guest guest7 = new Guest("Артём Новиков");

        ArrayList<Guest> group1 = new ArrayList<>();
        group1.add(guest5);
        group1.add(guest6);
        group1.add(guest7);

        int placed1 = hotel.checkInGroup(group1);
        out.println("\nРазмещено: " + placed1 + " из " + group1.size());

        // 6. Поселение группы людей начиная с определенного номера
        Guest guest8 = new Guest("Ольга Кузнецова");
        Guest guest9 = new Guest("София Морозова");
        Guest guest10 = new Guest("Максим Зайцев");
        Guest guest11 = new Guest("Алина Лебедева");

        ArrayList<Guest> group2 = new ArrayList<>();
        group2.add(guest8);
        group2.add(guest9);
        group2.add(guest10);
        group2.add(guest11);

        int placed2 = hotel.checkInGroupFromRoom(group2, 10);
        out.println("\nРазмещено: " + placed2 + " из " + group2.size());

        // 7. Выселение человека из номера
        hotel.checkOutFromRoom(3);

        // 8. Поселение семьи в соседние номера
        Guest guest12 = new Guest("Алексей Баранов");
        Guest guest13 = new Guest("Виктория Баранова");
        Guest guest14 = new Guest("Михаил Баранов");

        ArrayList<Guest> fam = new ArrayList<>();
        fam.add(guest12);
        fam.add(guest13);
        fam.add(guest14);

        hotel.checkInFamily(fam);

        // 9. Выселение человека с учетом семьи
        hotel.checkOutFromRoom(15);


        // 10. Поселение человека с животным в указанный номер
        Guest guest15 = new Guest("Анастасия Воробьева", true);

        boolean res = hotel.checkInWithAnimal(guest15, 14);
        out.println(res);

        // 11. Поселение группы людей с животными
        Guest guest16 = new Guest("Сергей Орлов", true);
        Guest guest17 = new Guest("Кирилл Белов", true);
        Guest guest18 = new Guest("Валерия Соловьева", true);

        ArrayList<Guest> aniPer = new ArrayList<>();
        aniPer.add(guest16);
        aniPer.add(guest17);
        aniPer.add(guest18);

        int num = hotel.checkInGroupWithAnimals(aniPer);
        out.println("\nРазмещено людей с животными: " + num + " из " + aniPer.size());

        // 12. Поселение семьи с животными
        Guest guest19 = new Guest("Роман Данилов", true);
        Guest guest20 = new Guest("Валерия Данилова", true);
        Guest guest21 = new Guest("Алиса Данилова", true);

        ArrayList<Guest> aniFam = new ArrayList<>();
        aniFam.add(guest19);
        aniFam.add(guest20);
        aniFam.add(guest21);

        out.println(hotel.getFreeRooms());

        boolean res1 = hotel.checkInFamilyWithAnimals(aniFam);
        out.println("\n" + res1);



    }
}