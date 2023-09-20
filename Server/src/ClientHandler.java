import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;

public class ClientHandler implements Runnable {
    private PrintWriter  out = null;
    private BufferedReader in = null;
    private Socket clientSocket = null;
    private boolean exit = false;

    public ClientHandler(Socket socket) throws IOException {
        try {
            this.clientSocket = socket;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        try{
            out.println("Bem vindo ao servidor! Digite suas mensagens:");
            String message = in.readLine();
            while(message != null && !message.equals("sair")){
                System.out.println("Mensagem do Cliente: " + message);
                if(message.trim().toLowerCase().contains("arquivo")) {
                    System.out.println("Cliente esta requisitando um arquivo");
                    File file = findFile(message.substring(7).trim());
                    if(file != null) {
                        System.out.println("Calculando Hash");
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        byte[] hash = digest.digest(Files.readAllBytes(file.toPath()));
                        out.println("Status: OK!");
                        out.println("Nome do Arquivo: " + file.getName());
                        out.println("Tamanho do Arquivo em bytes: " + file.length());
                        long kbytes = file.length() /1024;
                        out.println("Tamanho do Arquivo em Kbytes: " + kbytes);
                        long mbytes = kbytes / 1024;
                        out.println("Tamanho do Arquivo em Mbytes: " + mbytes);
                        out.println("Hash SHA-256 do Arquivo: " + bytesToHex(hash));
                        sendFile(file);

                    }

                }
                message = in.readLine();
            }
            System.out.println("Conexão com o cliente encerrada.");
            out.close();
            in.close();
            this.clientSocket.close();
            this.stop();




        }catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public void stop() {
        this.exit = true;
    }
    public File findFile(String path) {
        String currentPath = System.getProperty("user.dir");
        System.out.println("Diretorio atual do processo:: " + currentPath);
        String [] fileParams = path.split("\\.");
        if(fileParams.length <= 1) {
            System.out.println("Formato da string do arquivo errado");
            out.println("Formato da string do arquivo errado, o formato deve ser Nome.ext");
        }
        File dir = new File(currentPath);
        FileFinder ff = new FileFinder(fileParams[0],fileParams[1]);
        String [] arr = dir.list(ff);

        if(arr == null || arr.length == 0) {
            System.out.println("Arquivo não encontrado no diretorio " + currentPath);
            out.println("Status: NOT FOUND!");
            out.println("Arquivo Não Encontrado !");
            return null;
        }
        else {
            for (String s : arr) {
                System.out.println(s + " encontrado.");
            }
            return new File(currentPath + "/" + path);

        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    private void sendFile(File file) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
            bufferedOutputStream.flush();
            System.out.println("Arquivo enviado para o cliente.");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
