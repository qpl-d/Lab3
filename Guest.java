package Lab;

public class Guest {
    private String name;
    private boolean hasAnimal;

    // гость без животного
    public Guest(String name) {
        this.name = name;
        this.hasAnimal = false;
    }

    // гость с животным
    public Guest(String name, boolean hasAnimal) {
        this.name = name;
        this.hasAnimal = hasAnimal;
    }

    public String getName() {
        return name;
    }

    public boolean hasAnimal() {
        return hasAnimal;
    }

    @Override
    public String toString() {
        return name + (hasAnimal ? " (с животным)" : "");
    }
}