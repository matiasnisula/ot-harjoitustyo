# Arkkitehtuurikuvaus

## Rakenne

Koodin pakkausrakenne on seuraava:

![Pakkauskaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

Pakkaus opintopaivakirjasovellus.ui sisältää JavaFX:llä toteutetun käyttöliittymän, opintopaivakirjasovellus.domain sovelluslogiikan, ja opintopaivakirjasovellus.dao
tiedon tallettamisesta vastaavat luokat.


### Käyttöliittymä

Käyttöliittymä koostuu kolmesta erilaisesta näkymästä:

- Päänäkymä

- Kirjautumisnäkymä

- Luo uusi käyttäjä -näkymä

Jokainen näkymä on rakennettu omassa luokassaan. Luokat palauttavat "layoutin" [Parent](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Parent.html)-oliona, ja käyttöliittymän rakentamisesta vastaava luokka
 [opintopaivakirjasovellus.ui.OpintopaivakirjasovellusGUI](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/ui/OpintopaivakirjasovellusGUI.java#L19) luo niistä [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)-olion, ja päättää mikä näkymistä on sijoitettuna sovelluksen [stageen](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html). 
Kaikki tapahtumankäsittelijät ovat toteuttetu luokassa OpintopaivakirjasovellusGUI. Ne hyödyntävät sovelluslogiikasta vastaavan luokan [AppService](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L9) metodeja.   


### Sovelluslogiikka

Sovelluksen looginen datamalli muodostuu olioista [User](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/User.java#L4) ja [Task](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/Task.java#L4). User kuvaa sovelluksen käyttäjiä, ja Task taas käyttäjien tehtäviä.
Luokka [AppService](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L9) vastaa sovelluslogiikasta, ja sillä on käytössään pakkauksessa opintopaivakirjasovellus.dao sijaitsevat luokat SqliteTaskDao sekä SqliteUserDao. Nämä luokat vastaavat sovelluksen tietokantatoiminnoista.

![Datamalli](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kaavioSovelluslogiikka.png)


Luokkien suhteita toisiinsa on kuvattu seuraavassa luokkakaaviossa;


![Luokkakaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.jpg)


### Tietojen pysyväistallennus

Sovellus tallettaa tietoa [SQLite-tietokantaan](https://www.sqlite.org/index.html). Tiedon tallennus on toteutettu pakkauksessa opintopaivakirjasovellus.dao, ja siellä tallennuksesta vastaavat 
luokat [SqliteTaskDao](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteTaskDao.java#L9) sekä [SqliteUserDao](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteUserDao.java#L11). Luokat toteuttavat rajapinnat [TaskDao](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/TaskDao.java#L10) sekä [UserDao](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/UserDao.java#L6). Ne noudattavat [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object) -suunnittelumallia.
TaskDao tarvitsee tietoa käyttäjistä, joten sillä on muuttujana UserDao-olio.

**Tietokanta**

Tietokantaan luodaan kolme taulua; Tasks, Users, ja History. **Users-tauluun** tallennetaan tietoa käyttäjistä. Se käyttää SQLiten tarjoamaa automaattista
juoksevaa numerointia. Lisäksi sarakkeeseen *username* on asetettu ehto **unique**, mikä varmistaa, että jokainen käyttäjätunnus on yksilöllinen. **Tasks-tauluun** tallennetaan tehtäväolioihin liittyvää tietoa. 
Tehtävä liittyy aina yhteen käyttäjään, ja käyttäjällä voi olla useita tehtäviä. Tehtävällä on nimi, siihen käyttty aika, tieto siitä,
onko se tehty vai ei, sekä lisäysaika.
Sarakkeeseen done tallennetaan totuusarvo kokonaislukuna (false=0, true=1), koska SQLite ei mahdollista totuusarvojen tallennusta. 
**History-tauluun** talletetaan jokainen käyttäjän tekemä "suoritus" johonkin tehtävään. Täten käyttäjä pystyy tarkastelemaan tiettyyn tehtävään tehtyjä
suorituksia. Suoritukseen liittyy tehtävän nimi, yhden suorituksen kesto, sekä päivämäärä, milloin suoritus on tehty.


![Tietokantakaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/tietokantakaavio.png)

## Päätoiminnallisuudet

Sovelluksen päätoiminnalisuutta kuvattuna sekvenssikaavioina.

**Uuden käyttäjän luominen**

Käyttöliittymä kutsuu sovellulogiikasta vastaavan luokan AppService metodia createUser(). AppService selvittää tietokantaa hallinnoivalta luokalta löytyykö
haluttu käyttäjänimi jo tietokannasta palauttaen true/false. Jos käyttäjätunnusta ei ole olemassa, AppService pyytää UserDao:n lisäämään uuden käyttäjän tietokantaan.


![Sekvenssikaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/createNewUser.png)


**Sisäänkirjautuminen**

Kun käyttäjä on syöttänyt kirjautumisnäkymässä olevaan tekstikenttään käyttäjänimen, ja painanut Kirjaudu sisään-nappia, sovelluksen kontrolli etenee
seuraavasti:

![Sisäänkirjautuminen](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/logIn.png)

Käyttöliittymässä oleva napin [tapahtumankäsittelijä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/ui/OpintopaivakirjasovellusGUI.java#L38) kutsuu sovelluslogiikasta vastaavan luokan AppService metodia [login](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L83). Alussa tarkastetaan, onko 
kukaan jo kirjautuneena sisään. Jos ei, luokka AppService kutsuu käyttäjiin liittyvän tiedon tallennuksesta vastaavan luokkan SqliteUserDao metodia
[usernameExists](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteUserDao.java#L179), joka tarkastaa löytyykö käyttäjänimi tietokannasta. Jos löytyy, metodi paluttaa true. Tämän jälkeen kutsutaan vielä metodia 
[findByUsername](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteUserDao.java#L153), ja AppService asettaa käyttäjän sisäänkirjautuneeksi. Jos kirjautuminen onnistui, näkymä vaihtuu päänäkymään ja siihen päivitetään käyttäjän tehtävät.
 Muuten käyttäjälle tulostuu virheviesti "Kirjautuminen epäonnistui".





**Tehtävän luominen**

Kun käyttäjä on kirjautuneena sisään, kirjoittanut tekstikenttään haluamansa tehtävän nimen, ja painanut nappia Lisää tehtävä:

![Tehtävän luominen](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/addNewTask.png)

[Tapahtumankäsittelijä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/ui/OpintopaivakirjasovellusGUI.java#L126)
kutsuu sovelluslogiikasta vastaavan luokan metodia [createTask(String taskName)](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L31),
joka tarkastaa ensin, että jokin käyttäjä on kirjautuneena sisään. Sen jälkeen kutsutaan SqlteTaskDao-luokan metodia [getTask()](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteTaskDao.java#L292), joka palauttaa null, jos tehtävää, ei ole vielä olemassa. Muuten metodi palauttaa tehtäväolion.
Tämän jälkeen luokka AppService kutsuu SqliteTaskDao-luokan metodia [create(Task task, User loggedIn)](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteTaskDao.java#L114), joka tallettaa tehtävän tietokantaan. Metodin 
parametrina annettava tehtäväolio luodaan AppService-luokan metodissa createTask(String taskName). Samassa luokassa oleva metodi [getTimestamp()](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L263), palauttaa
tämänhetkisen päivämäärän merkkijonona, josta tulee tehtäväolion luomispäivämäärä. 




**Hae kaikki tehtävät**

Kun käyttäjä painaa päänäkymässä nappia "Hae kaikki tehtävät", sovelluksen kontrolli etenee seuraavasti:

![Hae kaikki](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/getAllTasks.png)

[Tapahtumankäsittelijä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/ui/OpintopaivakirjasovellusGUI.java#L101) kutsuu sovelluslogiikasta vastaavan luokan AppService metodia [getAll()](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L143), joka tarkastaa ensin, että jokin käyttäjä on 
kirjautunut sisään. Tämän jälkeen metodin sisällä kutsutaan SqliteTaskDao-luokan metodia [getAll(User loggedIn)](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteTaskDao.java#L141), joka hakee sisäänkirjautuneen käyttäjän tehtävät
tietokannasta ja palauttaa ne listana. Jos käyttäjällä ei ole yhtään tehtävää, palautetaan tyhjä lista.



### Sovellukseen jääneitä heikkouksia

Käyttäjän lisäämä aika tallennetaan kokonaislukuina, mikä mahdollistaa vain kokonaisten tuntien lisäämisen. Lisäksi tietokannan tauluun *Tasks* sarakkeeseen 
*user_id* ei ole asetettu varmistavaa ehtoa, vaikka se viittaa Users-taulun sarakkeeseen *id*.  




