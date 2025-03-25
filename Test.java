import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        // Создаем гостиницу с 10 номерами
        Hotel hotel = new Hotel(10);
        hotel.printHotelStatus();

        // 1. Поселение одиночных гостей
        System.out.println("\n--- Поселение одиночных гостей ---");
        Guest guest1 = new Guest("Иван Петров");
        Guest guest2 = new Guest("Мария Сидорова");
        Guest guest3 = new Guest("Алексей Иванов", true); // С животным

        hotel.checkInToRoom(guest1, 3);
        hotel.checkInToRoom(guest2, 5);
        boolean animalCheckIn = hotel.checkInToRoom(guest3, 4); // Четный номер для животного
        System.out.println("Поселение с животным в номер 4: " + (animalCheckIn ? "успешно" : "не удалось"));

        hotel.printHotelStatus();

        // 2. Поселение в первый свободный номер
        System.out.println("\n--- Поселение в первый свободный ---");
        Guest guest4 = new Guest("Ольга Кузнецова");
        int roomNumber = hotel.checkInToFirstAvailable(guest4);
        System.out.println(guest4.getName() + " поселен в номер " + roomNumber);

        hotel.printHotelStatus();

        // 3. Поселение группы
        System.out.println("\n--- Поселение группы ---");
        List<Guest> group = Arrays.asList(
                new Guest("Дмитрий Смирнов"),
                new Guest("Елена Васнецова"),
                new Guest("Павел Орлов")
        );
        int placed = hotel.checkInGroup(group);
        System.out.println("Размещено " + placed + " из " + group.size() + " гостей");

        hotel.printHotelStatus();

        // 4. Поселение семьи
        System.out.println("\n--- Поселение семьи ---");
        List<Guest> family = Arrays.asList(
                new Guest("Сергей Николаев"),
                new Guest("Анна Николаева"),
                new Guest("Михаил Николаев")
        );
        boolean familyPlaced = hotel.checkInFamily(family);
        System.out.println("Семья размещена: " + (familyPlaced ? "да" : "нет"));

        hotel.printHotelStatus();

        // 5. Поселение семьи с животными
        System.out.println("\n--- Поселение семьи с животными ---");
        List<Guest> familyWithPets = Arrays.asList(
                new Guest("Андрей Волков", true),
                new Guest("Татьяна Волкова", true)
        );
        boolean familyWithPetsPlaced = hotel.checkInFamilyWithAnimals(familyWithPets);
        System.out.println("Семья с животными размещена: " + (familyWithPetsPlaced ? "да" : "нет"));

        hotel.printHotelStatus();

        // 6. Выселение гостя
        System.out.println("\n--- Выселение гостя ---");
        boolean checkedOut = hotel.checkOutFromRoom(3);
        System.out.println("Гость из номера 3 выселен: " + (checkedOut ? "да" : "нет"));

        hotel.printHotelStatus();

        // 7. Попытка поселения гостя с животным в нечетный номер
        System.out.println("\n--- Попытка поселения с животным в нечетный номер ---");
        Guest guest5 = new Guest("Николай Зверев", true);
        boolean animalCheckInFailed = hotel.checkInToRoom(guest5, 1);
        System.out.println("Поселение с животным в номер 1: " + (animalCheckInFailed ? "успешно" : "не удалось"));
    }
}