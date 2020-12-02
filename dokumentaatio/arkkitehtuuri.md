## Sovelluslogiikka

![Luokkakaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Luokkakaavio.jpg)


## Päätoiminnallisuudet

### Uuden käyttäjän luominen

Käyttöliittymä kutsuu sovellulogiikasta vastaavan luokan AppService metodia createUser(). AppService selvittää tietokantaa hallinnoivalta luokalta löytyykö
haluttu käyttäjänimi jo tietokannasta palauttaen true/false. Jos käyttäjätunnusta ei ole olemassa, AppService pyytää UserDao:n lisäämään uuden käyttäjän tietokantaan.


![Sekvenssikaavio](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/Sekvenssikaavio.png)
