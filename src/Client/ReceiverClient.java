package Client;

import Server.Message;
import java.net.Socket;
import java.util.Date;

/*
* Класс ответственный за прием сообщений их обработку и вывод в консоль.
* The class is responsible for receiving messages, processing, and output to the console.
*
* */

public class ReceiverClient implements Runnable{

    private  Thread thread;
    private Socket socket;
    ReceiverClient(Socket socket){
        thread = new Thread(this);
        this.socket = socket;
    }

    /*
    * Этот метод служит для старта нити.
    * This method is used to start the threads.
    * */
    public void start(){
        thread.start();
    }

    /*
    * Главный цикл нити с приемом сообщений и выводом их на консоль.
    * Main loop of thread receiving messages and output them to the console.
    * */
    @Override
    public void run() {
        try {
            while (true){
                Message message = Message.read(socket);
                printMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
     * Метод который выводит сообщение в консоль.
     * Method that prints a message to the console.
     * */
    public void printMessage(Message message){
        if(message!=null) {
          Date date = message.getDate();
          String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
            System.out.println(time + " ["+message.getLogin()+ "]" + message.getText());
        }
    }
}
