# Ohjelmistotekniikka, harjoitustyö

## Opintopäiväkirjasovellus

Sovelluksen avulla käyttäjän on mahdollista pitää kirjaa opintoihin tai muihin aktiiviteetteihin käyttämästään ajasta. Sovellus käyttää SQLite-tietokantaa.

Sovelluksessa on toiminnot                                                                                                     

 - Käyttäjän luomiselle

 - Sisäänkirjautumiselle

 - Vapaavalintaisen tehtävän (esim.kurssin) lisäämiselle
 
 - Tehtävän merkkaaminen tehdyksi

 - Tehtävän poistaminen

 - Tehtyjen ja tekemättömien tehtävien ja niihin käytetyn ajan tarkasteleminen

 - Tehtävään käytetyn ajan lisääminen

 - Tehtäväkohtaisten suoritushistorian tarkasteleminen


Sovellusta käytetään graafisen käyttöliittymän kautta.

## Releaset

[Viikko 5](https://github.com/matiasnisula/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/matiasnisula/ot-harjoitustyo/releases/tag/Viikko6)

[Loppupalautus](https://github.com/matiasnisula/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

> mvn test

Testikattavuusraportti luodaan komennolla

> mvn jacoco:report

### Checkstyle raportti saadaan generoitua komennolla

> mvn jxr:jxr checkstyle:checkstyle

### Javadoc

Komennolla

> mvn javadoc:javadoc

### Suoritettavan jarin generointi

Komento

> mvn package

generoi hakemistoon target suoritettavan jar-tiedoston Opintopaivakirjasovellus-1.0-SNAPSHOT.jar


### Suoritus

Projektin koodin pystyy suorittamaan komennolla

> mvn compile exec:java -Dexec.mainClass=opintopaivakirjasovellus.Main



## Dokumentaatio

[Vaativuusmäärittely](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/vaativuusmaarittely.md)

[Työaikakirjanpito](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Testaus](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)
