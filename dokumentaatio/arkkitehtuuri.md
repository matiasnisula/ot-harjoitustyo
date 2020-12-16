# Arkkitehtuurikuvaus

## Rakenne

Koodin pakkausrakenne on seuraava:

![Pakkauskaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

Pakkaus opintopaivakirjasovellus.ui sisältää JavaFX:llä toteutetun käyttöliittymän, opintopaivakirjasovellus.domain sovelluslogiikan, ja opintopaivakirjasovellus.dao
tiedon tallettamisesta vastaavat luokat.

### Sovelluslogiikka

![Luokkakaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.jpg)


### Tietojen pysyväistallennus

Sovellus tallettaa tietoa **SQLite-tietokantaan**. Tiedon tallennus on toteutettu pakkauksessa opintopaivakirjasovellus.dao, ja siellä tallennuksesta vastaavat 
luokat SqliteTaskDao sekä SqliteUserDao. Luokat toteuttavat rajapinnat TaskDao sekä UserDao. Ne noudattavat **Data Access Object** -suunnittelumallia.
TaskDao tarvitsee tietoa käyttäjistä, joten sillä on muuttujana UserDao-olio.

**Tietokanta**

Tietokantaan luodaan kolme taulua; Tasks, Users, ja History. **Users-tauluun** tallennetaan tietoa käyttäjistä. Se käyttää SQLiten tarjoamaa automaattista
juoksevaa numerointia. **Tasks-tauluun** tallennetaan tehtäväolioihin liittyvää tietoa. 
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

Käyttöliittymässä oleva napin [tapahtumankäsittelijä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/ui/OpintopaivakirjasovellusGUI.java#L38) kutsuu sovelluslogiikasta vastaavan luokan AppService metodia [login](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/domain/AppService.java#L77). Alussa tarkastetaan, onko 
kukaan jo kirjautuneena sisään. Jos ei, luokka AppService kutsuu käyttäjiin liittyvän tiedon tallennuksesta vastaavan luokkan SqliteUserDao metodia
[usernameExists](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteUserDao.java#L177), joka tarkastaa löytyykö käyttäjänimi tietokannasta. Jos löytyy, metodi paluttaa true. Tämän jälkeen kutsutaan vielä metodia 
[findByUsername](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteUserDao.java#L149), ja AppService asettaa käyttäjän sisäänkirjautuneeksi. Käyttöliittymän näkymä vaihtuu päänäkymään, jos kirjautuminen onnistui,
 muuten käyttäjälle tulostuu virheviesti "Kirjautuminen epäonnistui".


**Tehtävän luominen**

![Tehtävän luominen](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/addNewTask.png)


**Hae kaikki tehtävät**

![Hae kaikki](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/getAllTasks.png)






