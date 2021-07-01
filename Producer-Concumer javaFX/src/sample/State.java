package sample;

import java.util.ArrayList;

public class State {
    private ArrayList<Integer> timeOfMachine;
    private ArrayList<int[]> colors;
    private int rate;
    private int generatedNum;

    public ArrayList<int[]> getColor() { return colors; }

    public int getGeneratedNum() { return generatedNum; }

    public int getRate() { return rate; }

    public ArrayList<Integer> getTimeOfMachine() { return timeOfMachine; }

    public void setColor(int[] color) {
        if (this.colors == null)
            this.colors = new ArrayList<int[]>();
        this.colors.add(color);
    }

    public void setGeneratedNum(int generatedNum) { this.generatedNum = generatedNum; }

    public void setRate(int rate) { this.rate = rate; }

    public void setTimeOfMachine(int timeOfMachine, int index) {
        if(this.timeOfMachine == null)
            this.timeOfMachine = new ArrayList<Integer>();
        this.timeOfMachine.add(index, timeOfMachine);
    }
}
