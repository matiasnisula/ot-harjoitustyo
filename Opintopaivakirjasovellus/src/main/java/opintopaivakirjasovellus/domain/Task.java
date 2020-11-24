
package opintopaivakirjasovellus.domain;

public class Task {
    private String name;
    private int timeUsed;
    private boolean done;
    private User user;
    /**
    * Tehtäväluokka.
    */
    
    /**
    * Luokan konstruktori.
    * @param name luotavan tehtävän nimi
    */
    public Task(String name, User user) {
        this.name = name;
        this.timeUsed = 0;
        boolean done = false;
        this.user = user;
    }

    public String getName() {
        return this.name;
    }
    /**
    * Kasvattaa käytettyä kokonaisaikaa.
    * @param time lisättävä aika
    */
    public void addTime(int time) {
        this.timeUsed += time;
    }
    public int getTimeUsed() {
        return this.timeUsed;
    }
    /**
    * Asettaa tehtävän tehdyksi.
    */
    public void setDone() {
        this.done = true;
    }
    @Override
    public String toString() {
        return this.name + ", Time used: " + this.timeUsed;
    }
}
