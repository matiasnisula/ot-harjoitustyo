# Ohjelmistotekniikka, harjoitustyö

## Opintopäiväkirjasovellus

Sovelluksen avulla käyttäjän on mahdollista pitää kirjaa opintoihin käyttämästään ajasta.

Tällä hetkellä sovelluksessa on toiminnot                                                                                                     

	-Käyttäjän luomiselle

	-Sisäänkirjautumiselle

	-Vapaavalintaisen tehtävän (esim.kurssin) lisäämiselle
 
	-Lisättyjen tehtävien näyttäminen


Sovellusta käytetään tekstikäyttöliittymän kautta.

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

> mvn test

Testikattavuusraportti luodaan komennolla

> mvn jacoco:report

### Suoritus

Projektin koodin pystyy suorittamaan komennolla

> mvn compile exec:java -Dexec.mainClass=opintopaivakirjasovellus.ui.Main



## Dokumentaatio

[Vaativuusmäärittely](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/vaativuusmaarittely.md)

[Työaikakirjanpito](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)
