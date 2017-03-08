package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.*;

/*
 * Класс который является отправляемым сообщением.
 * The class which is the sent message.
 * */
public class Message implements Serializable{
    static final long serialVersionUID = 1;
    private String text;
    private String login;
    GregorianCalendar gcalendar = new GregorianCalendar();
    public Message(String login, String text){
        this.text = text;
        this.login = login;
        gcalendar = new GregorianCalendar();
    }

    /*
     * Получение текста текущего сообщения.
     * Getting the text of the current message.
     * */
    public String getText() {
        return text;
    }

    /*
    * Получение логина пользователя отправителя сообщения.
    * Getting login user sender of the message.
    * */
    public String getLogin() {
        return login;
    }

    /*
    * Получение времени отправления сообщения.
    * The time of departure of the message.
    * */
    public Date getDate() {
        return gcalendar.getTime();
    }

    /*
    * Отправление сообщения всем пользователям.
    * Send message to all users.
    * */
    public static void write(Message message, ArrayList<Socket> sockets) throws IOException {
        if(message!=null) {
                synchronized (sockets) {
                    for (Socket s : sockets) {
                        Message.write(message,s);
                    }
                }
        }
    }

    /*
    * Отправка сообщения по сокету.
    * Sending a message over the socket.
    * */
    public static void write(Message message,Socket socket) throws IOException {
        if(message!=null) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        }
    }

    /*
    * Чтение сообщение из сокета.
    * Reading a message from a socket.
    * */
    public static Message read(Socket socket) throws IOException, ClassNotFoundException {
       return (Message) new ObjectInputStream(socket.getInputStream()).readObject();
    }
}
