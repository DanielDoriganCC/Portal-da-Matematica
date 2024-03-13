import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {
    // variável que armazena os IPs dos usuários
    private static List<PrintWriter> clients = new ArrayList<>();
    private static List<String> nomes = new ArrayList<>();

    // abre o servidor e configura para que este receba novos clientes
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("Servidor escutando na porta 5555...");


            // verificar conexão
            Thread timerThread = new Thread(() -> {
                while (true){
                    try {
                        Thread.sleep(10000); // Aguarda 10 segundos
                        verificarConexao();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            timerThread.start();


            while (true) { // se prepara para receber novos clientes
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão estabelecida com " + clientSocket);

                // Cria um thread para cada cliente
                Thread clientThread = new Thread(new ClientHandler(clientSocket)); // lida com a adição do cliente ao servidor
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para enviar mensagens a todos os clientes
    public static void broadcast(String message, PrintWriter sender) {
        for (PrintWriter client : clients) {
            if (client != sender) {
                client.println(getNome(sender) + ": " + message);
            }
        }
    }

    // Função para enviar mensagem privada a um cliente específico
    private static void sendPrivateMessage(String destinatario, String mensagemPrivada, PrintWriter out) {
        for (PrintWriter client : clients) {
            if (client != out) {
                // Envia a mensagem apenas para o destinatário especificado

                if (client.equals(getEndereco(destinatario))) {
                    client.println("[" + destinatario + " (mensagem privada)] " + mensagemPrivada);
                    return;
                }
            }
        }
        // Se o destinatário não for encontrado, informa ao remetente
        out.println("Cliente '" + destinatario + "' não encontrado ou offline.");
    }

    // Thread para lidar com um cliente específico
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                Scanner in = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                clients.add(out);

                try{
                String clientName = in.nextLine();
                nomes.add(clientName);
                } finally {}

                while (true) { // comandos especiais
                    String message = in.nextLine();
                    if (message.equals("exit")) {
                        break;
                    }

                    if (message.startsWith("/msg")) {
                        String[] parts = message.split(" ", 3);
                        if (parts.length == 3) {
                            String destinatario = parts[1];
                            String mensagemPrivada = parts[2];
                            
                            // Envia a mensagem privada para o destinatário
                            sendPrivateMessage(destinatario, mensagemPrivada, out);
                        } else {
                            out.println("Uso correto: /msg destinatario mensagem");
                        }
                    }
                    else{
                        // Broadcast da mensagem para todos os clientes
                        broadcast(message, out);
                    }                    
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clients.remove(out);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static PrintWriter getEndereco(String nome){
        return clients.get(nomes.indexOf(nome));
    }
    
    public static String getNome(PrintWriter endereco){
        return nomes.get(clients.indexOf(endereco));
    }

    private static void verificarConexao() {
        for(PrintWriter client : clients){
            try{
                client.print(""); // Se não houver exceção, a conexão está ativa
            } catch (Exception e) { // Se ocorrer uma exceção, a conexão foi perdida
                clients.remove(client);
                nomes.remove(clients.indexOf(client));
                System.out.println(client + " removido");
            }
        }
    }
}
