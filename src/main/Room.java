package main;

public class Room {
    private int roomNr;
    private int locators;
    private int size;

    public Room(int roomNr, int locators, int size) {
        this.roomNr = roomNr;
        this.locators = locators;
        this.size = size;
    }

    public int getRoomNr() {
        return roomNr;
    }

    @Override
    public String toString() {
        int availRoom = size - locators;
        if(availRoom == 1)
            return "Pokój: " + roomNr + " ma " + availRoom + " wolne miejsce.";
        else if(availRoom > 1 && availRoom < 5)
            return "Pokój: " + roomNr + " ma " + availRoom + " wolne miejsca.";
        else
            return "Pokój: " + roomNr + " ma " + availRoom + " wolnych miejsc.";
    }
}
