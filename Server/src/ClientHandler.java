import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;

public class ClientHandler implements Runnable {
    private Socket clientSocket = null;
    private boolean exit = false;
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try{
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Bem vindo ao servidor! Digite suas mensagens:");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = in.readLine();
            while(message != null && !message.equals("sair")){
                System.out.println("Mensagem do Cliente: " + message);
                if(message.trim().toLowerCase().contains("arquivo")) {
                    System.out.println("Cliente esta requisitando um arquivo");
                    findFile(message.substring(7).trim());

                }
                message = in.readLine();
            }
            System.out.println("Conexão com o cliente encerrada.");
            this.clientSocket.close();
            this.stop();




        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        this.exit = true;
    }
    public void findFile(String path) {
        String currentPath = System.getProperty("user.dir");
        System.out.println("Diretorio atual do processo:: " + currentPath);
        String [] fileParams = path.split("\\.");
        File dir = new File(currentPath);
        FileFinder ff = new FileFinder(fileParams[0],fileParams[1]);
        String [] arr = dir.list(ff);

        if(arr == null || arr.length == 0) {
            System.out.println("Arquivo não encontrado no diretorio " + currentPath);
        }
        else {
            for (String s : arr) {
                System.out.println(s + " encontrado.");
            }

        }
    }
}
