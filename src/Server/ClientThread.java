package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*
* Класс на стороне сервера  ответственный
* за получение сообщение и его оправку всем пользователям.
* The class on the server side is responsible
* for receiving the message and its mandrel all users.
* */

public class ClientThread implements  Runnable {
    private Thread thread;
    private  Socket socket;

    /*
    * Список сокетов.
    * List of sockets.
    * */
    private static ArrayList<Socket> sockets = new ArrayList<Socket>();

    ClientThread(Socket socket){
        thread = new Thread(this);
        synchronized (sockets){
            sockets.add(socket);
        }
        this.socket =socket;
    }

    /*
    * Метод запускает нить.
    * Method starts the thread.
    * */
    public  void start(){
        thread.start();
    }

    /*
    * Главный цикл нити, получение и отправка
    * сообщений всем пользователям.
    * The main cycle of threads, receive
    * and send messages to all users.
    * */
    @Override
    public void run() {
        try{
            while(true){
                try {
                    Message message = Message.read(socket);
                    Message.write(message,sockets);
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
