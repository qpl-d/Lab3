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