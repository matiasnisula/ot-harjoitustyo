# Käyttöohje

Lataa tiedosto [Opintopaivakirjasovellus.jar](https://github.com/matiasnisula/ot-harjoitustyo/releases/tag/loppupalautus)

Sovellus olettaa, että käyttäjällä on asennettuna Javan versio 11. Uusin release sisältää tiedoston config.properties, joka määrittelee 
tietokantojen nimet. Se **täytyy** sijoittaa samaan kansioon suoritettavan jar-tiedoston kanssa. Sovellus luo käynnistyksen yhteydessä tietokantatiedoston 
*tietokanta.db*. Tietokantatiedosto *testi.db* luodaan, kun testit ajetaan ensimmäisen kerran. Tiedostot luodaan samaan kansioon, missä suoritettava 
jar-tiedosto sijaitsee.

### Tiedoston config.properties sisältö

**db.url=jdbc:sqlite:tietokanta.db**

**db.testiUrl=jdbc:sqlite:testi.db**

Oletuksena sovelluksen käyttämän tietokannan nimeksi tulee *tietokanta.db*, ja testausta varten tarvittavan tietokantatiedoston nimeksi *testi.db*.

Jos haluat muuttaa tietokantatiedostojen nimet, avaa config.properties tekstieditorilla, ja vaihda kohtaan **tietokanta** haluamasi nimi. 
Sovellus luo tällöin uuden **tyhjän** tietokannan uudella nimellä. 


### Ohjelman käynnistäminen

Kun tiedosto config.properties on sijoitettu samaan kansioon;

> java -jar Opintopaivakirjasovellus.jar


### Uuden käyttäjän luominen ja kirjautuminen sisään

**Sovelluksen kirjautumisnäkymä**

![Kirjautumisnäkymä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/logInView.png)


1. Sovelluksen kirjautumisnäkymästä, pääsee luomaan käyttäjän painamalla nappia "Luo käyttäjä".

**Käyttäjän luominen**

![Käyttäjänluomisnäkymä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/CreateNewUserView.png)

2. Kirjoita oma nimi ja haluamasi käyttäjätunnus tekstikenttiin.
3. Paina nappia "Luo käyttäjä"
4. Paina "Kirjaudu sisään" --> sovelluksen kirjautumisnäkymä avautuu
5. Kirjoita luomasi käyttäjätunnus ja kirjaudu sisään.


### Tehtävien lisääminen ja näyttäminen

![Päänäkymä](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/MainView.png)

Päänäkymässä oikealla on lista, johon päivitetään tietoja käyttäjän tehtävistä. Lista päivittyy painamalla nappia "Hae tehdyt tehtävät" tai "Hae tekemättömät tehtävät". Tällöin listassa
näkyy tiettyyn tehtävään käytetty aika yhteensä, sekä tehtävän aloituspäivämäärä. Tehtäviä lisätään kirjoittamalla tehtävän nimi painikkeen
 "Lisää tehtävä" alle, minkä jälkeen tehtävä lisätään painamalla "Lisää tehtävä"-nappia.
Tehtävään käytettyä aikaa lisätään kirjoittamalla tehtävän nimi ja siihen käytetty aika painikkeen "Lisää aika" alle, minkä jälkeen painetaan
"Lisää aika"-nappia. Yhteen tehtävään liittyviä suorituksia on mahdollista tarkastella kirjoittamalla "Hae tehtävä"-painikkeen alle halutun tehtävän nimi.
Listaan päivittyy yhden tehtävän suoritushistoria, josta näkee tehtävään käytetyn ajan tiettynä päivänä. Tehtävän voi merkata tehdyksi kirjoittamalla napin 
"Merkkaa tehdyksi" alle tehtävän nimi ja painamalla nappia.



