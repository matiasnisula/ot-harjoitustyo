
package opintopaivakirjasovellus.domain;

public class Task {
    private String name;
    private int timeUsed;
    private boolean done;
    private User user;
    private String date;
    /**
    * Tehtäväluokka.
    */
    
    /**
    * Luokan konstruktori.
    * @param name luotavan tehtävän nimi
    * @param user Käyttäjä, jolle tehtävä luodaan
    * @param date tehtävän lisäysaika(päivämäärä)
    */
    public Task(String name, User user, String date) {
        this.name = name;
        this.timeUsed = 0;
        this.done = false;
        this.user = user;
        this.date = date;
    }
    public String getDate() {
        return this.date;
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
    public void setDoneInt(int n) {
        this.done = n == 1;
    }
    public boolean getDone() {
        return this.done;
    }
    /**
    * Palauttaa 1 = true, 0 = false tietokantaa varten.
    * @return 1 tai 0
    */
    public int getDoneInt() {
        if (this.done == true) {
            return 1;
        } else {
            return 0;
        }
    }
    @Override
    public String toString() {
        return this.name + ", Käytetty aika: " + this.timeUsed + ", Lisätty " + this.date;
    }
}
