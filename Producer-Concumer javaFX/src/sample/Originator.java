package sample;

public class Originator {
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public Memento storeState(){
        return new Memento(state);
    }

    public State restoreState(Memento m){
        state = m.getState();
        return state;
    }
}
