import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Room class representing a hotel room
class Room {
    private String roomNumber;
    private String category;
    private boolean isAvailable;
    private double pricePerNight;

    public Room(String roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookRoom() {
        this.isAvailable = false;
    }

    public void releaseRoom() {
        this.isAvailable = true;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return String.format("Room %s - Category: %s, Price per night: %.2f, Available: %s",
                             roomNumber, category, pricePerNight, isAvailable ? "Yes" : "No");
    }
}

// Reservation class representing a reservation
class Reservation {
    private String roomNumber;
    private String guestName;
    private int numberOfNights;

    public Reservation(String roomNumber, String guestName, int numberOfNights) {
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.numberOfNights = numberOfNights;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double calculateTotalCost(Room room) {
        return room.getPricePerNight() * numberOfNights;
    }

    @Override
    public String toString() {
        return String.format("Reservation for %s: Room %s for %d nights, Total Cost: %.2f",
                             guestName, roomNumber, numberOfNights, calculateTotalCost(RoomSystem.getRoom(roomNumber)));
    }
}

// Main system class managing rooms and reservations
public class RoomSystem {
    private static final Map<String, Room> rooms = new HashMap<>();
    private static final List<Reservation> reservations = new ArrayList<>();

    static {
        // Initialize some rooms
        rooms.put("101", new Room("101", "Single", 100.00));
        rooms.put("102", new Room("102", "Double", 150.00));
        rooms.put("201", new Room("201", "Suite", 250.00));
    }

    public static Room getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchAvailableRooms();
                    break;

                case 2:
                    makeReservation(scanner);
                    break;

                case 3:
                    viewReservations();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void searchAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms.values()) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        Room room = getRoom(roomNumber);

        if (room == null || !room.isAvailable()) {
            System.out.println("Invalid room number or room is not available.");
            return;
        }

        System.out.print("Enter number of nights: ");
        int numberOfNights = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Reservation reservation = new Reservation(roomNumber, guestName, numberOfNights);
        reservations.add(reservation);
        room.bookRoom();
        System.out.println("Reservation made successfully.");
    }

    private static void viewReservations() {
        System.out.println("Current Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
