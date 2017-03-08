package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
* Сервер чата
* The chat server
* */

public class Server {
    private  static ServerSocket serverSocket;
    private  static Socket socket;

    public static void main(String[] args) {
        start();
        handler();
        end();
    }

    /*
    * Инициализация сервера.
    * Server initialization.
    * */
    private static void start(){
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Подключения к серверу нового пользователя.
    * Connect to the server for the new user.
    * */
    private static void handler(){
        try {
            while (true){
                socket = serverSocket.accept();
                ClientThread thread = new ClientThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    * Завершение работы сервера.
    * The closing server.
    * */
    private static void end(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
