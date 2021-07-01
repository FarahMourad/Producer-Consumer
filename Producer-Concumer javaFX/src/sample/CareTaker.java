package sample;

import java.util.ArrayList;

public class CareTaker {
    private ArrayList<Memento> states = new ArrayList<Memento>();

    public void addMemento(Memento m){
        states.add(m);
    }

    public Memento getMemento(int index) {
        return states.get(index);
    }

    public int getSize() {
        return states.size();
    }
}
