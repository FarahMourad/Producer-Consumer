package sample;

import java.util.ArrayList;
import java.util.Random;

public class Facade {
    static int terminator;
    static State state = new State();
    static Originator origin = new Originator();
    static CareTaker careTaker = new CareTaker();

    public static void sendData(ArrayList<ArrayList<ArrayList<Integer>>> machines,
                                ArrayList<ArrayList<Integer>> queues) {
        ArrayList<Machine> Machines = new ArrayList<>();
        ArrayList<QueueLines> Queues = new ArrayList<>();
        assemblyLine(machines, queues, Machines, Queues);
        for (int i = 0; i < Machines.size(); i++)
            Machines.get(i).start();
        q0(Queues.get(0));
        for (Machine m : Machines)
            state.setTimeOfMachine(m.getTime(), m.id);
        origin.setState(state);
        careTaker.addMemento(origin.storeState());
        waitThread(Queues, Machines);
    }

    public static void assemblyLine(ArrayList<ArrayList<ArrayList<Integer>>> machines,
                                    ArrayList<ArrayList<Integer>> queues, ArrayList<Machine> Machines,
                                    ArrayList<QueueLines> Queues) {
        for (int i = 0; i < queues.size(); i++) {
            QueueLines q = new QueueLines(i);
            Queues.add(q);

        }
        for (int i = 0; i < machines.size(); i++ ) {
            Machine m;
            m = new Machine(i);
            Machines.add(m);
        }
        update(machines, queues, Machines, Queues);
    }

    public static void replay(ArrayList<ArrayList<ArrayList<Integer>>> machines, ArrayList<ArrayList<Integer>> queues) {
        ArrayList<Machine> Machines = new ArrayList<>();
        ArrayList<QueueLines> Queues = new ArrayList<>();
        State stateReplay = origin.restoreState(careTaker.getMemento(careTaker.getSize() - 1));
        for (int i = 0; i < queues.size(); i++) {
            QueueLines q = new QueueLines(i);
            Queues.add(q);
        }
        for (int i = 0; i < machines.size(); i++ ) {
            Machine machine;
            machine = new Machine(i, stateReplay.getTimeOfMachine().get(i));
            Machines.add(machine);
        }
        update(machines,queues,Machines,Queues);
        for (int i = 0; i < Machines.size(); i++)
            Machines.get(i).start();
        qR(Queues.get(0), stateReplay.getColor(), stateReplay.getRate(), stateReplay.getGeneratedNum());
        waitThread(Queues, Machines);
    }

    public static void update(ArrayList<ArrayList<ArrayList<Integer>>> machines, ArrayList<ArrayList<Integer>> queues,
                              ArrayList<Machine> Machines, ArrayList<QueueLines> Queues) {
        for(int j = 0; j < queues.size(); j++) {
            for(int i = 0; i < queues.get(j).size(); i++)
                Queues.get(j).addDestMachine(Machines.get(queues.get(j).get(i)));
        }
        for(int j = 0 ; j < machines.size(); j++) {
            for(int i = 0; i < machines.get(j).get(1).size(); i++)
                Machines.get(j).setPQueues(Queues.get(machines.get(j).get(1).get(i)));
            //set next queues for each machine
            for(int i = 0; i < machines.get(j).get(0).size(); i++)
                Machines.get(j).setNextQueue(Queues.get(machines.get(j).get(0).get(i)));
        }
    }

    public static void q0(QueueLines q) {
        Random random = new Random();
        int numOfProduct = random.nextInt(10);
        state.setGeneratedNum(numOfProduct);
        int randomTime = random.nextInt(3);
        state.setRate(randomTime);
        terminator = numOfProduct;
        Runnable input =() -> {
            for(int i = 0; i < numOfProduct; i++){
                Product p = new Product();
                state.setColor(p.getColor());
                q.addProductToQ(p);
                try {
                    Thread.currentThread().sleep(randomTime*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread inputT = new Thread(input, "input");
        inputT.start();

    }

    public static void qR(QueueLines q, ArrayList<int[]> color, int rate, int generatedNum) {
        terminator = generatedNum;
        Runnable input =() -> {
        for (int i = 0; i < generatedNum; i++) {
            q.addProductToQ(new Product(color.get(i)[0], color.get(i)[1], color.get(i)[2]));
            try {
                Thread.currentThread().sleep(rate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        };
        Thread inputT = new Thread(input, "input");
        inputT.start();
    }

    public static void stopThread(ArrayList<Machine> Machines) {
        for (int i = 0; i < Machines.size(); i++){
            Machines.get(i).stop();
        }
    }

    public static void waitThread(ArrayList<QueueLines> Queues, ArrayList<Machine> Machines) {
        Runnable waitS =() ->{
            while ((Queues.get(Queues.size()-1)).getProductsNum() < terminator){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopThread(Machines);
        };
        Thread waitT = new Thread(waitS,"waiting");
        waitT.start();
    }
}
