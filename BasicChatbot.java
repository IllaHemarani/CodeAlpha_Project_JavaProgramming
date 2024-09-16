import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BasicChatbot {

    private static final Map<String, String> responses = new HashMap<>();

    static {
        responses.put("hello", "Hi there! How can I assist you today?");
        responses.put("how are you", "I'm just a bot, but I'm doing well! How can I help you?");
        responses.put("what is your name", "I am a chatbot created by OpenAI.");
        responses.put("bye", "Goodbye! Have a great day!");
        responses.put("default", "Sorry, I don't understand that.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Chatbot! Type 'bye' to exit.");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("bye")) {
                System.out.println("Chatbot: Goodbye! Have a great day!");
                break;
            }

            String response = responses.getOrDefault(input, responses.get("default"));
            System.out.println("Chatbot: " + response);
        }

        scanner.close();
    }
}
