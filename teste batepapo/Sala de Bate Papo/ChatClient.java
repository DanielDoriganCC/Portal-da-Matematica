import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6666);
            Scanner serverIn = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Solicita e define o nome do cliente
            // Scanner scanner = new Scanner(System.in);
            // System.out.print("Digite seu nome: ");
            // String nome = scanner.nextLine();
            // out.println(nome); // CORRIGIR !!!!!!!!!!!!!!!!!!!!

            String nome = perguntarNome();

            // Thread para receber mensagens do servidor
            Thread receiveThread = new Thread(() -> {
                while (true) {
                    String message = serverIn.nextLine();
                    System.out.println(message); // CORRIGIR!!!!!!!!!!!!!!
                }
            });
            receiveThread.start();

            // Thread para enviar mensagens para o servidor
            Thread sendThread = new Thread(() -> {
                while (true) {
                    String message = scanner.nextLine();
                    out.println(message); // CORRIGIR, não precisará de um while true, ele será ativado pelo comando externo
                }
            });
            sendThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String perguntarNome(){

    }
}
