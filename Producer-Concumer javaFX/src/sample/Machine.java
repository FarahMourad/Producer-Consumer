package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static java.lang.Thread.sleep;

//consumer
public class Machine implements Runnable {
    private volatile boolean exit = false;
    private int time;
    boolean empty = true;
    private Random random = new Random();
    int id;
    private List<QueueLines> queueLinesList;
    private QueueLines nextQueue;
    private Product p;
    private Thread thread;
    private static Main form;

    public Machine(int id){
        this.id = id;
        thread = new Thread(this, "M"+id);
        time = random.nextInt(5);
    }

    public Machine(int id, int time){
        this.id = id;
        thread = new Thread(this, "M"+id);
        this.time = time;
    }

    public void addProduct(Product p){
        synchronized (this){
            System.out.println(p.getColor()[0] + " " + p.getColor()[1] + " " + p.getColor()[2] + " <<" + " added in " + this.thread.getName() );
            this.p = p;
            form.Ms.get(id).setFill(Color.rgb(p.getColor()[0], p.getColor()[1], p.getColor()[2]));
            empty = false;
            notify();
           // System.out.println("notified");
        }
    }

    //waiting
    public void receive(){
        boolean pReceived = false;
        for(int i = 0; i < queueLinesList.size(); i++){
            Queue<Product> waiting = queueLinesList.get(i).getWaitingQueue();
            System.out.println(waiting.size() + "Products waiting in "+ queueLinesList.get(i).getId());
            if(!waiting.isEmpty()){
                System.out.println(waiting.peek() + "waiting" );
                addProduct( waiting.poll() );
                pReceived = true;
                break;
            }
        }
        if(!pReceived)
            notifyObservers();
    }

    public void consume(){
        try {
            sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void passToNext(){
        form.Ms.get(id).setFill(Color.VIOLET);
        nextQueue.addProductToQ(p);

        p = null;
    }

    public void notifyObservers(){
        empty = true;
        p = null;
    }

    public void start(){
        thread.start();
    }

    public void stop(){
        exit = true;
        this.thread.interrupt();
    }

    public int getTime() {
        return time;
    }

    public void setNextQueue(QueueLines nextQueue) {
        this.nextQueue = nextQueue;
    }

    public QueueLines getNextQueue() {
        return nextQueue;
    }

    public void setPQueues(QueueLines queue){
        if(queueLinesList == null)
            queueLinesList = new ArrayList<>();
        queueLinesList.add(queue);
    }

    public List<QueueLines> getQueueList() {
        return queueLinesList;
    }

    @Override
    public void run() {
        synchronized (this){
            while(!exit){
                if(p == null){
                    try {
                        wait();
                        System.out.println("waiting");
                    } catch (InterruptedException e) {
                        System.out.println("stopped");
                        break;
                    }
                }
                if(exit){
                    System.out.println("stopped");
                    break;
                }
                consume();
                System.out.println("consuming");
                passToNext();
                System.out.println("to next");
                receive();
                System.out.println("received");
            }
        }
    }
}
