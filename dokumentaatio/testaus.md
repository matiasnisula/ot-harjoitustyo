# Testausdokumentti

Ohjelmaa on testattu automatisoiduin yksikkö- ja integraatiotestein [JUnitilla](https://en.wikipedia.org/wiki/JUnit). Käytössä on JUnitin versio 4.12. 

## Yksikkö- ja integraatiotestaus


### DAO-luokat

DAO-luokkien toiminnallisuutta testataan luomalla testejä varten erillinen tietokantatiedosto. DAO-luokkiin on toteutettu testejä varten metodi 
[emptyTables()](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/main/java/opintopaivakirjasovellus/dao/SqliteTaskDao.java#L89)
, joka tyhjentää testauksessa käytettävän tietokantatiedoston taulut. 

### Sovelluslogiikka

Sovelluslogiikasta vastaavan luokan AppService testaus on jaettu kahteen luokkaan; [AppServiceUserTest](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/test/java/opintopaivakirjasovellus/domain/AppServiceUserTest.java) ja [AppServiceTaskTest](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/Opintopaivakirjasovellus/src/test/java/opintopaivakirjasovellus/domain/AppServiceTaskTest.java#L23). Käyttöliittymä hyödyntää 
suoraan luokkaa AppService, joten sen testaus on yritetty toteuttaa mahdollisimman kattavasti. Luokalle [Task]() on tehty muutamia testejä varmistamaan 
equals-metodin toimiminen. Sovelluslogiikkakerroksen testit hyödyntävät myöskin testaukseen tarkoitettua erillistä tietokantatiedostoa.  

### Testauskattavuus

Sovelluksen testauksen rivikattavuus on 88% ja haarautumakattavuus 84%. Testaamatta jäivät muutamat tilanteet, joissa metodit heittävät poikkeuksen.
 Testauksesta on jätetty ulkopuolelle käyttöliittymäkerros. 

![Testauskattavuus](https://github.com/matiasnisula/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testausKattavuus.png)


### Sovellukseen jääneitä laatuongelmia

Osa opintopaivakirjasovellus.dao luokkien tallentavista metodeista tulostavat tekstiä havaitessaan poikkeuksen. Tämä kiersi tietokantaluokkien 
poikkeusten testaamisessa eteen tulleet ongelmat. Tallennettava tieto tarkastetaan ennen tallennusta hyvin, joten tulostuksia ei pitäisi tapahtua 
käytettäessä graafista käyttöliittymää.
