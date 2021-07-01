package sample;

public class Memento {
    private State state = new State();

    Memento(State state){
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
