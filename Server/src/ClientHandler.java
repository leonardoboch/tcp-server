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
                    if(findFile(message.substring(7).trim())){
                        System.out.println("Enviando Arquivo para Cliente");
                    }
                    else {
                        out.println("ERRO: Arquivo não encontrado");
                    }

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
    public boolean findFile(String path) {
        String currentPath = System.getProperty("user.dir");
        System.out.println("Diretorio atual do processo:: " + currentPath);
        String [] fileParams = path.split("\\.");
        if(fileParams.length <= 1) {
            System.out.println("Formato da string do arquivo errado");
        }
        File dir = new File(currentPath);
        FileFinder ff = new FileFinder(fileParams[0],fileParams[1]);
        String [] arr = dir.list(ff);

        if(arr == null || arr.length == 0) {
            System.out.println("Arquivo não encontrado no diretorio " + currentPath);
            return false;
        }
        else {
            for (String s : arr) {
                System.out.println(s + " encontrado.");
            }
            return true;

        }
    }
}
