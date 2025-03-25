import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Hotel {
    private int totalRooms;
    private Map<Integer, Guest> rooms; // Номер -> Гость
    private List<Family> families; // Список всех семей в гостинице

    // Создание гостиницы с указанным количеством номеров
    public Hotel(int n) {
        this.totalRooms = n;
        this.rooms = new HashMap<>();
        this.families = new ArrayList<>();

        // Инициализация всех номеров как свободных
        for (int i = 1; i <= n; i++) {
            rooms.put(i, null);
        }
    }

    // 1. Получение списка свободных номеров
    public List<Integer> getFreeRooms() {
        List<Integer> freeRooms = new ArrayList<>();
        for (Map.Entry<Integer, Guest> entry : rooms.entrySet()) {
            if (entry.getValue() == null) {
                freeRooms.add(entry.getKey());
            }
        }
        return freeRooms;
    }

    // 2. Получение списка занятых номеров с именами постояльцев
    public Map<Integer, Guest> getOccupiedRooms() {
        Map<Integer, Guest> occupiedRooms = new HashMap<>();
        for (Map.Entry<Integer, Guest> entry : rooms.entrySet()) {
            if (entry.getValue() != null) {
                occupiedRooms.put(entry.getKey(), entry.getValue());
            }
        }
        return occupiedRooms;
    }

    // 3. Поселение человека в заданный номер
    public boolean checkInToRoom(Guest guest, int roomNumber) {
        // Проверка, что номер существует
        if (roomNumber < 1 || roomNumber > totalRooms) {
            return false;
        }

        // Проверка, что номер свободен
        if (rooms.get(roomNumber) != null) {
            return false;
        }

        // Проверка для гостей с животными (должен быть четный номер)
        if (guest.hasAnimal() && roomNumber % 2 != 0) {
            return false;
        }

        rooms.put(roomNumber, guest);
        return true;
    }

    // 4. Поселение человека в первый свободный номер
    public int checkInToFirstAvailable(Guest guest) {
        for (int i = 1; i <= totalRooms; i++) {
            // Для гостей с животными проверяем четность номера
            if (guest.hasAnimal() && i % 2 != 0) {
                continue;
            }

            if (rooms.get(i) == null) {
                rooms.put(i, guest);
                return i;
            }
        }
        return -1; // Нет свободных номеров
    }

    // 5. Поселение группы людей
    public int checkInGroup(List<Guest> guests) {
        int count = 0;
        for (Guest guest : guests) {
            int roomNumber = checkInToFirstAvailable(guest);
            if (roomNumber != -1) {
                count++;
            }
        }
        return count;
    }

    // 6. Поселение группы людей начиная с определенного номера
    public int checkInGroupFromRoom(List<Guest> guests, int startRoom) {
        int count = 0;
        for (Guest guest : guests) {
            boolean placed = false;
            for (int i = startRoom; i <= totalRooms; i++) {
                // Для гостей с животными проверяем четность номера
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

    // 7. Выселение человека из номера
    public boolean checkOutFromRoom(int roomNumber) {
        if (roomNumber < 1 || roomNumber > totalRooms || rooms.get(roomNumber) == null) {
            return false;
        }

        // Удаляем гостя из номера
        Guest guest = rooms.get(roomNumber);
        rooms.put(roomNumber, null);

        // Проверяем, был ли гость частью семьи
        for (Family family : families) {
            if (family.isMember(guest)) {
                // Выселяем всех членов семьи
                for (int familyRoom : family.getRoomNumbers()) {
                    rooms.put(familyRoom, null);
                }
                families.remove(family);
                break;
            }
        }

        return true;
    }

    // 8. Поселение семьи в соседние номера
    public boolean checkInFamily(List<Guest> familyMembers) {
        if (familyMembers.isEmpty()) {
            return false;
        }

        // Ищем последовательные свободные номера
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

    // 10. Поселение человека с животным в указанный номер
    public boolean checkInWithAnimal(Guest guest, int roomNumber) {
        if (!guest.hasAnimal()) {
            return false;
        }

        if (roomNumber % 2 != 0) {
            return false;
        }

        return checkInToRoom(guest, roomNumber);
    }

    // 11. Поселение группы людей с животными
    public int checkInGroupWithAnimals(List<Guest> guests) {
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

    // 12. Поселение семьи с животными
    public boolean checkInFamilyWithAnimals(List<Guest> familyMembers) {
        if (familyMembers.isEmpty()) {
            return false;
        }

        // Проверяем, что все члены семьи имеют животных
        for (Guest guest : familyMembers) {
            if (!guest.hasAnimal()) {
                return false;
            }
        }

        // Ищем последовательные четные свободные номера
        for (int i = 2; i <= totalRooms - familyMembers.size() + 1; i += 2) {
            boolean allFree = true;
            for (int j = 0; j < familyMembers.size(); j++) {
                if (rooms.get(i + j) != null || (i + j) % 2 != 0) {
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

    // Метод для вывода информации о гостинице
    public void printHotelStatus() {
        System.out.println("\n=== Статус гостиницы ===");
        System.out.println("Свободные номера: " + getFreeRooms());

        Map<Integer, Guest> occupied = getOccupiedRooms();
        System.out.println("Занятые номера:");
        for (Map.Entry<Integer, Guest> entry : occupied.entrySet()) {
            System.out.println("Номер " + entry.getKey() + ": " + entry.getValue());
        }

        if (!families.isEmpty()) {
            System.out.println("\nСемьи в гостинице:");
            for (int i = 0; i < families.size(); i++) {
                System.out.println("Семья " + (i+1) + ": номера " + families.get(i).getRoomNumbers());
            }
        }
    }
}