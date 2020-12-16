# Käyttöohje

Lataa tiedosto [Opintopaivakirjasovellus.jar](https://github.com/matiasnisula/ot-harjoitustyo/releases/tag/Viikko6)

Sovellus olettaa, että käyttäjällä on asennettuna Javan versio 11.

### Ohjelman käynnistäminen

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

Päänäkymässä oikealla on lista, johon päivitetään tietoja käyttäjän tehtävistä. Lista päivittyy painamalla nappia "Hae kaikki tehtävät". Tällöin listassa
näkyy tiettyyn tehtävään käytetty aika yhteensä, sekä tehtävän aloituspäivämäärä. Tehtäviä lisätään kirjoittamalla tehtävän nimi painikkeen
 "Lisää tehtävä" alle, minkä jälkeen tehtävä lisätään painamalla "Lisää tehtävä"-nappia.
Tehtävään käytettyä aikaa lisätään kirjoittamalla tehtävän nimi ja siihen käytetty aika painikkeen "Lisää aika" alle, minkä jälkeen painetaan
"Lisää aika"-nappia. Yhteen tehtävään liittyviä suorituksia on mahdollista tarkastella kirjoittamalla "Hae tehtävä"-painikkeen alle halutun tehtävän nimi.
Listaan päivittyy yhden tehtävän suoritushistoria, josta näkee tehtävään käytetyn ajan tiettynä päivänä.



