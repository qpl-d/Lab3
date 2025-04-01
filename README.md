## Отчет по лабораторной работе № 3

#### № группы: `ПМ-2402`

#### Выполнил: `Павлов Максим Витальевич`

#### Вариант: `18`

### Cодержание:

- [Постановка задачи](#1-постановка-задачи)
- [Математическая модель](#2-математическая-модель)
- [Алгоритм](#3-алгоритм)
- [Программа](#4-программа)
- [Анализ правильности решения](#5-анализ-правильности-решения)

### 1. Постановка задачи

> Разработать программу для управления номерами в гостинице с учётом правил размещения, включая возможность проживания с животными в чётных номерах. Реализовать функции добавления и выселения гостей или семей, а также распределения номеров в соответствии с заданными условиями.

Логика работы программы разделена на смысловые блоки: созданы отдельные классы для гостя, семьи и основной программы (гостиницы). 
Программа поддерживает такие операции, как:
- Создание гостиницы с заданным количеством номеров
- Поселение одиночных гостей, групп и семей
- Учет наличия животных у гостей
- Выселение гостей (включая автоматическое выселение семьи)

### 2. Математическая модель

 В программе используется `HashMap<Integer, Guest>` в классе `Hotel`, где `Integer` - номер комнаты, а `Guest` - объект, размещенный в комнате (если номер свободен, принимает значение `null`).
 `HashMap` используется:
 - для проверки занятости номера (`if (rooms.get(roomNumber) == null)`).
 - добавления / удаления гостей из номера (`rooms.put(roomNumber, guest)`; `rooms.put(roomNumber, null)`).
   
 `ArrayList` исользуется в классе `Hotel`:
- для хранения всех семей, проверки на принадлежность гостя к семье, а также удаления всей семьи при выселенении (`ArrayList<Family>`). Пример: `families.add(newFamily)`
- для выполнения временных операций (`ArrayList<Integer>`). Пример: `ArrayList<Integer> freeRooms = new ArrayList<>();`
- для выполнения групповых операций (`public int checkInGroup(ArrayList<Guest> guests)`)

### 3. Алгоритм

#### Описание работы классов:

1. **Класс Guest**  
   - Создает гостя гостиницы с именем `name` и флагом наличия животного `hasAnimal`. Далее класс будет использоваться в других классах, представляя из себя гостя
   ```java
   // гость без животного
    public Guest(String name) {
        this.name = name;
        this.hasAnimal = false;
    }
   ```
   ```java
   // гость с животным
    public Guest(String name, boolean hasAnimal) {
        this.name = name;
        this.hasAnimal = hasAnimal;
    }
   ```
2. **Класс Family**
   - управляет группой связанных гостей, занимающих несколько номеров. В списке `members` хранится объекты типа `Guest`, `roomNumbers` - список соответствующих номеров. Класс представляет из себя отдельный смысловой блок, который хранит информацию о семьях, состоящих из гостей (`Guest`), а также имеет ряд собственных методов, которые будут использоваться в основной программе.
   Класс реализует такие методы, как:
   ```java
   // добавление члена семьи
    public void addMember(Guest guest, int roomNumber) {
        members.add(guest);
        roomNumbers.add(roomNumber);
    }
   ```
   ```java
   // получение списка членов семьи
    public ArrayList<Guest> getMembers() {
        return new ArrayList<>(members);
    }
   ```
   
   ```java
   // получение списка членов семьи
    public ArrayList<Guest> getMembers() {
        return new ArrayList<>(members);
    }
   ```
   ```java
   // получение списка номеров, которые занимает семья
    public ArrayList<Integer> getRoomNumbers() {
        return new ArrayList<>(roomNumbers);
    }
   ```
   ```java
   // проверка, является ли гость членом семьи
    public boolean isMember(Guest guest) {
        return members.contains(guest);
    }
   ```
   ```java
   // получение количества членов семьи
    public int size() {
        return members.size();
    }
   ```
3. ***Класс Hotel***
   - Задействует классы `Guest` и `Family` и их методы, чтобы выполнить поставленные в условии задачи. Использует структуры данных `ArrayList` и `HashMap`.

### 4. Программа
Основная программа

```java
package Lab;
import java.util.ArrayList;
import java.util.HashMap;

public class Hotel {
    private int totalRooms;
    private HashMap<Integer, Guest> rooms;
    private ArrayList<Family> families;

    // создание гостиницы с указанным количеством номеров
    public Hotel(int n) {
        this.totalRooms = n;
        this.rooms = new HashMap<>();
        this.families = new ArrayList<>();

        // инициализация всех номеров как свободных
        for (int i = 1; i <= n; i++) {
            rooms.put(i, null);
        }
    }

    // получение списка свободных номеров
    public ArrayList<Integer> getFreeRooms() {
        ArrayList<Integer> freeRooms = new ArrayList<>();
        for (int i = 1; i <= totalRooms; i++) {
            if (rooms.get(i) == null) {
                freeRooms.add(i);
            }
        }
        return freeRooms;
    }

    // получение списка занятых номеров и имен гостей в этих номерах
    public HashMap<Integer, Guest> getOccupiedRooms() {
        HashMap<Integer, Guest> occupiedRooms = new HashMap<>();
        for (int i = 1; i <= totalRooms; i++) {
            if (rooms.get(i) != null) {
                occupiedRooms.put(i, rooms.get(i));
            }
        }
        return occupiedRooms;
    }

    // заселения гостя в заданный номер
    public boolean checkInToRoom(Guest guest, int roomNumber) {

        // проверка, существует ли номер
        if (roomNumber < 1 || roomNumber > totalRooms) {
            return false;
        }

        // проверка, свободен ли номер
        if (rooms.get(roomNumber) != null) {
            return false;
        }

        // проверка для гостей с животными
        if (guest.hasAnimal() && roomNumber % 2 != 0) {
            return false;
        }

        rooms.put(roomNumber, guest);
        return true;
    }

    // заселение гостя в первый свободный номер
    public int checkInToFirstAvailable(Guest guest) {
        for (int i = 1; i <= totalRooms; i++) {

            // проверка для гостей с животными
            if (guest.hasAnimal() && i % 2 != 0) {
                continue;
            }

            // заселение
            if (rooms.get(i) == null) {
                rooms.put(i, guest);
                return i;
            }
        }

        // нет свободных номеров
        return -1;
    }

    // поселение группы гостей
    public int checkInGroup(ArrayList<Guest> guests) {
        int count = 0;
        for (Guest guest : guests) {
            int roomNumber = checkInToFirstAvailable(guest);
            if (roomNumber != -1) {
                count++;
            }
        }
        return count;
    }

    // поселение группы людей начиная с определенного номера
    public int checkInGroupFromRoom(ArrayList<Guest> guests, int startRoom) {
        int count = 0;
        for (Guest guest : guests) {
            boolean placed = false;
            for (int i = startRoom; i <= totalRooms; i++) {
                if (guest.hasAnimal() && i % 2 != 0) {
                    continue;
                }

                if (rooms.get(i) == null) {
                    rooms.put(i, guest);
                    count++;
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                break;
            }
        }
        return count;
    }

    // выселение гостя из номера
    public boolean checkOutFromRoom(int roomNumber) {
        if (roomNumber < 1 || roomNumber > totalRooms || rooms.get(roomNumber) == null) {
            return false;
        }

        // удаление одного гостя
        Guest guest = rooms.get(roomNumber);
        rooms.put(roomNumber, null);

        // проверка, является ли гость частью семьи
        for (Family family : families) {
            if (family.isMember(guest)) {
                // выселение всех членов семьи
                for (int familyRoom : family.getRoomNumbers()) {
                    rooms.put(familyRoom, null);
                }
                families.remove(family);
                break;
            }
        }

        return true;
    }

    // поселение семьи в соседние номера
    public boolean checkInFamily(ArrayList<Guest> familyMembers) {
        if (familyMembers.isEmpty()) {
            return false;
        }

        // поиск последовательных свободных номеров
        for (int i = 1; i <= totalRooms - familyMembers.size() + 1; i++) {
            boolean allFree = true;
            for (int j = 0; j < familyMembers.size(); j++) {
                if (rooms.get(i + j) != null) {
                    allFree = false;
                    break;
                }
            }

            if (allFree) {
                Family family = new Family();
                for (int j = 0; j < familyMembers.size(); j++) {
                    rooms.put(i + j, familyMembers.get(j));
                    family.addMember(familyMembers.get(j), i + j);
                }
                families.add(family);
                return true;
            }
        }

        return false;
    }

    // поселение человека с животным в указанный номер
    public boolean checkInWithAnimal(Guest guest, int roomNumber) {
        if (!guest.hasAnimal()) {
            return false;
        }

        if (roomNumber % 2 != 0) {
            return false;
        }

        return checkInToRoom(guest, roomNumber);
    }

    // поселение группы людей с животными
    public int checkInGroupWithAnimals(ArrayList<Guest> guests) {
        int count = 0;
        for (Guest guest : guests) {
            if (!guest.hasAnimal()) {
                continue;
            }

            for (int i = 2; i <= totalRooms; i += 2) {
                if (rooms.get(i) == null) {
                    rooms.put(i, guest);
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    // поселение семьи с животными
    public boolean checkInFamilyWithAnimals(ArrayList<Guest> familyMembers) {
        if (familyMembers.isEmpty()) {
            return false;
        }

        for (Guest guest : familyMembers) {
            if (!guest.hasAnimal()) {
                return false;
            }
        }

        ArrayList<Integer> availableRooms = new ArrayList<>();

        for (int i = 2; i <= totalRooms; i += 2) {
            if (rooms.get(i) == null) {
                availableRooms.add(i);
            }
        }

        if (availableRooms.size() < familyMembers.size()) {
            return false;
        }

        if (availableRooms.size() >= familyMembers.size()) {
            Family family = new Family();
            for (int i = 0; i < familyMembers.size(); i++) {
                int roomNumber = availableRooms.get(i);
                rooms.put(roomNumber, familyMembers.get(i));
                family.addMember(familyMembers.get(i), roomNumber);
            }
            families.add(family);
            return true;
        }

        return false;
    }

    // вывод информации о гостинице
    public void printHotelStatus() {
        System.out.println("\n=== Статус гостиницы ===");
        System.out.println("Свободные номера: " + getFreeRooms());

        HashMap<Integer, Guest> occupied = getOccupiedRooms();
        System.out.println("Занятые номера:");
        for (int roomNumber : occupied.keySet()) {
            System.out.println("Номер " + roomNumber + ": " + occupied.get(roomNumber));
        }

        if (!families.isEmpty()) {
            System.out.println("\nСемьи в гостинице:");
            for (int i = 0; i < families.size(); i++) {
                System.out.println("Семья " + (i+1) + ": номера " + families.get(i).getRoomNumbers());
            }
        }
    }
}
```

### 5. Анализ правильности решения
Для анализа правильносты был создан отдельный класс `Test`. В нем последовательно выполняется каждый из 12 пунктов в условии задачи, а также используются методы класса `Guest` для создания новых гостей и структура данных `ArrayList`.
- создание гостиницы с свободными номерами:
  ```java
  Hotel hotel = new Hotel(25);
  ```
- отображения списков свободных номеров и занятых номеров с именами постояльцев:
  ```java
  hotel.printHotelStatus();
  ```
- размещение человека с указанным именем в переданный номер, если номер свободен, создание гостей методом `Guest`:
  ```java
  Guest guest1 = new Guest("Сергей Иванов");
  Guest guest2 = new Guest("Иван Петров");
  Guest guest3 = new Guest("Петр Сергеев");

  hotel.checkInToRoom(guest1, 1);
  hotel.checkInToRoom(guest2, 3);
  hotel.checkInToRoom(guest3, 5);
  ```
- размещение человека с указанным именем в первый свободный номер, при этом если свободных номеров нет, действие не выполняется:
  ```java
  Guest guest4 = new Guest("Анна Смирнова");
  hotel.checkInToFirstAvailable(guest4);
  ```
- принятие массива имён и размещение людей в свободные номера (если номеров недостаточно, размещаются те, для которых номера нашлись). Вывод количества размещенных людей:
  ```java
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
  ```
- размещение людей с определенного номера, возвращается количество размещенных людей:
  ```java
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
  ```
- освобождение указанного номера:
  ```java
  hotel.checkOutFromRoom(3);
  ```
- размещение семьи в последовательные свободные номера, если таких номеров нет - семья не размещается. Возвращение true или false:
  ```java
  // 8. Поселение семьи в соседние номера
  Guest guest12 = new Guest("Алексей Баранов");
  Guest guest13 = new Guest("Виктория Баранова");
  Guest guest14 = new Guest("Михаил Баранов");

  ArrayList<Guest> fam = new ArrayList<>();
  fam.add(guest12);
  fam.add(guest13);
  fam.add(guest14);

  hotel.checkInFamily(fam);
  ```
- освобождение указанного номера (при условии, что человек в номере - член семьи):
  ```java
  hotel.checkOutFromRoom(15);
  ```
- размещение человека с животным в указанный номер (если номер четный и свободный):
  ```java
  Guest guest15 = new Guest("Анастасия Воробьева", true);

  hotel.checkInWithAnimal(guest15, 14);
  ```
- размещение группы людей с животными в свободные четные номера и вывод успешно размещенных людей:
  ```java
  Guest guest16 = new Guest("Сергей Орлов", true);
  Guest guest17 = new Guest("Кирилл Белов", true);
  Guest guest18 = new Guest("Валерия Соловьева", true);

  ArrayList<Guest> aniPer = new ArrayList<>();
  aniPer.add(guest16);
  aniPer.add(guest17);
  aniPer.add(guest18);

  int num = hotel.checkInGroupWithAnimals(aniPer);
  out.println("\nРазмещено людей с животными: " + num + " из " + aniPer.size());
  ```
- размещение семьи с животными в соседние четные номера и вывод true, если размещение возможно иначе false
  ```java
  Guest guest19 = new Guest("Роман Данилов", true);
  Guest guest20 = new Guest("Валерия Данилова", true);
  Guest guest21 = new Guest("Алиса Данилова", true);

  ArrayList<Guest> aniFam = new ArrayList<>();
  aniFam.add(guest19);
  aniFam.add(guest20);
  aniFam.add(guest21);

  boolean res1 = hotel.checkInFamilyWithAnimals(aniFam);
  out.println("\n" + res1);
  ```
