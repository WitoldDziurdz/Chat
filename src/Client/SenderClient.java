package Client;

import Server.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
* Класс ответственный за инициализацию логина, прием текста
* с консоли и последующее отправление сообщений на сервер.
* The class is responsible for initializing login,
* receive text from the console and sending messages to the server.
* */

public class SenderClient {

    private static String login = null;
    private  static  BufferedReader  reader;

    /*
    * Главный цикл, регистрация и отправка сообщений на сервер.
    * Main loop, check and send messages to the server.
    * */
    public static void main(String[] args) {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            singUp();
            Socket socket = start();
            sendMessage(socket);
            end();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Регистрация пользователя.
    * The user registration.
    * */
    private static void singUp() throws IOException {
        while(login==null) {
            System.out.println("Please your name: ");
            login = reader.readLine();
            login = login.replaceAll(" ","");
        }
    }

    /*
    * Инициализация класса слушателя "ReceiverClient".
    * Initialize the listener class "ReceiverClient".
    * */
    public static Socket start() throws IOException {
        Socket socket = null;
        socket = new Socket("localhost", 8888);
        ReceiverClient receiverClient = new ReceiverClient(socket);
        receiverClient.start();
        return socket;
    }

    /*
    * Отправка сообщения.
    * Sending a message.
    * */
    public static void sendMessage(Socket socket) throws IOException {
        while(true){
            String text = reader.readLine();
            if(text.equals("exit")) break;
            Message message = new Message(login,text);
            Message.write(message,socket);
        }
    }

    /*
    * Завершение сервера.
    * The end of the server.
    * */
    private static void end(){
        try {
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
