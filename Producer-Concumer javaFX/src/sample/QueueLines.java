package sample;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//producer
public class QueueLines {
    int id;
    private boolean flag = false;
    private List<Machine> machineList;
    private Queue<Product> waitingQueue;
    private int productsNum;
    private static Main form;

    public QueueLines(int id){
        this.id = id;
        waitingQueue = new ArrayDeque<>();
        machineList = new ArrayList<Machine>();
    }

    public void passPtoM(Product p){
        flag = false;
        Machine temp;
        // if last index excluded
        if(!machineList.isEmpty()) {
            for (int i = 0; i < machineList.size(); i++) {
                temp = machineList.get(i);
                System.out.println(temp.id);
                System.out.println(temp.empty);
                if(temp.empty){
                    temp.addProduct(p);
                    decProductsNum();
                    flag = true;
                    break;
                }
            }
            if(!flag){
                waitingQueue.add(p);
            }
    }}

    public void addProductToQ(Product product) {
        incProductNum();
        passPtoM( product);
    }
    public void addDestMachine(Machine machine){
        machineList.add(machine);
    }

    public List<Machine> getMachineList() { return machineList; }

    public void decProductsNum() {
        this.productsNum-- ;
    }

    public Queue<Product> getWaitingQueue() { return waitingQueue; }

    public int getProductsNum() { return productsNum; }

    public int getId() {
        return id;
    }
    public  void incProductNum(){
        this.productsNum++ ;
    }
}
