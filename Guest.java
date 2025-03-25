class Guest {
    private String name;
    private boolean hasAnimal;

    // Конструктор для гостя без животного
    public Guest(String name) {
        this.name = name;
        this.hasAnimal = false;
    }

    // Конструктор для гостя с животным
    public Guest(String name, boolean hasAnimal) {
        this.name = name;
        this.hasAnimal = hasAnimal;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public boolean hasAnimal() {
        return hasAnimal;
    }

    // Для вывода информации о госте
    @Override
    public String toString() {
        return name + (hasAnimal ? " (с животным)" : "");
    }
}