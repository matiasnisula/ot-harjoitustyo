# Ohjelmistotekniikka, harjoitustyö

## Opintopäiväkirjasovellus

Sovelluksen avulla käyttäjän on mahdollista pitää kirjaa opintoihin käyttämästään ajasta.

Tällä hetkellä sovelluksessa on toiminnot                                                                                                     

 - Käyttäjän luomiselle

 - Sisäänkirjautumiselle

 - Vapaavalintaisen tehtävän (esim.kurssin) lisäämiselle
 
 - Lisättyjen tehtävien näyttäminen

 - Tehtävään käytetyn ajan lisääminen

 - Tehtäväkohtaisten suoritusten tarkasteleminen


Sovellusta käytetään graafisen käyttöliittymän kautta.

## Releaset

[Viikko 5](https://github.com/matiasnisula/ot-harjoitustyo/releases/tag/viikko5)


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

> mvn compile exec:java -Dexec.mainClass=opintopaivakirjasovellus.ui.Main



## Dokumentaatio

[Vaativuusmäärittely](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/vaativuusmaarittely.md)

[Työaikakirjanpito](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
