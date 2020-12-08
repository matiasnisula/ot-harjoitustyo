# Arkkitehtuurikuvaus

## Rakenne

Koodin pakkausrakenne on seuraava:

![Pakkauskaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.png)

Pakkaus opintopaivakirjasovellus.ui sisältää JavaFX:llä toteutetun käyttöliittymän, opintopaivakirjasovellus.domain sovelluslogiikan, ja opintopaivakirjasovellus.dao
tiedon tallettamisesta vastaavat luokat.

### Sovelluslogiikka

![Luokkakaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.jpg)


### Tietojen pysyväistallennus

Sovellus tallettaa tietoa **SQLite-tietokantaan**. Tiedon tallennus on toteutettu pakkauksessa opintopaivakirjasovellus.dao, ja siellä tallennuksesta vastaavat >
SqliteTaskDao sekä SqliteUserDao. Luokat toteuttavat rajapinnat TaskDao sekä UserDao. Ne noudattavat **Data Access Object** -suunnittelumallia. TaskDao tarvitsee tietoa käyttäjistä, joten sillä on muuttujana UserDao-olio.

**Tietokanta**

Tietokantaan luodaan kolme taulua; Tasks, Users, ja History. Users-tauluun tallennetaan tietoa käyttäjistä. Se käyttää SQLiten tarjoamaa automaattista juoksevaa numerointia. 

![Tietokantakaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/tietokantakaavio.png)

## Päätoiminnallisuudet

### Uuden käyttäjän luominen

Käyttöliittymä kutsuu sovellulogiikasta vastaavan luokan AppService metodia createUser(). AppService selvittää tietokantaa hallinnoivalta luokalta löytyykö
haluttu käyttäjänimi jo tietokannasta palauttaen true/false. Jos käyttäjätunnusta ei ole olemassa, AppService pyytää UserDao:n lisäämään uuden käyttäjän tietokantaan.


![Sekvenssikaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Sekvenssikaavio.png)




