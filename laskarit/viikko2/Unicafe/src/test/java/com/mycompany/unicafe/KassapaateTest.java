/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matnisul
 */
public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
      
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(10000);
    }
    
    @Test
    public void kassapaatteenRahamaaraOikeinAlussa() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void myydytLounaatOikeinAlussa() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void syoEdullisestiToimiiOikeinKunMaksuRiittava() {
        assertEquals(260, kassa.syoEdullisesti(500));
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void syoEdullisestiToimiiOikeinKunMaksuLiianVahan() {
        assertEquals(230, kassa.syoEdullisesti(230));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void syoMaukkaastiToimiiOikeinKunMaksuRiittava() {
        assertEquals(300, kassa.syoMaukkaasti(700));
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void syoMaukkaastiToimiiOikeinKunMaksuLiianVahan() {
        assertEquals(300, kassa.syoMaukkaasti(300));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void syoEdullisestiToimiiMaksukortillaKunKortillaTarpeeksiRahaa() {
        kortti.otaRahaa(9760);
        assertEquals(true, kassa.syoEdullisesti(kortti));
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kortti.saldo());
    }
    @Test
    public void syoEdullisestiToimiiOikeinKunKortillaEiOleTarpeeksiRahaa() {
        kortti.otaRahaa(9800);
        assertEquals(false, kassa.syoEdullisesti(kortti));
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(200, kortti.saldo());
    }
    @Test
    public void syoMaukkaastiToimiiOikeinKortillaKunRahaaOnTarpeeksi() {
        kortti.otaRahaa(9600);
        assertEquals(true, kassa.syoMaukkaasti(kortti));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(0, kortti.saldo());
    }
    @Test
    public void syoMaukkaastiToimiiOikeinKunKortillaEiTarpeeksiRahaa() {
        kortti.otaRahaa(9700);
        assertEquals(false, kassa.syoMaukkaasti(kortti));
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(300, kortti.saldo());
    }
    @Test
    public void lataaRahaaKortilleToimiiOikein() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
        assertEquals(11000, kortti.saldo());
    }
    @Test
    public void lataaRahaaKortilleToimiiOikeinKunSummaNegatiivinen() {
        kassa.lataaRahaaKortille(kortti, -300);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(10000, kortti.saldo());
    }
}
