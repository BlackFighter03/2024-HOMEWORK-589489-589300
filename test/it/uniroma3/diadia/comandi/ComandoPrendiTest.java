package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;

import org.junit.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;

public class ComandoPrendiTest {

	@Test
	public void testPrendiNulla() {
		Partita p = new Partita(new Labirinto());
		Comando prendi = new ComandoPrendi(null);
		prendi.esegui(p, new IOConsole());
		assertTrue(p.getGiocatore().getBorsa().isEmpty());
	}
	
	@Test
	public void testPrendiOsso() {
		Partita p = new Partita(new Labirinto());
		Comando prendi = new ComandoPrendi("osso");
		prendi.esegui(p, new IOConsole());
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("osso"));
	}
	
	@Test
	public void testNonPrendiOggettoInesistente() {
		Partita p = new Partita(new Labirinto());
		Comando prendi = new ComandoPrendi("spada");
		prendi.esegui(p, new IOConsole());
		assertTrue(p.getGiocatore().getBorsa().isEmpty());
	}

	@Test
	public void testTentativoFallitoOggettoTroppoPesante() {
		Labirinto l = new LabirintoBuilder()
				.addStanzaIniziale("atrio").addAttrezzo("piombo pesantissimo", 12)
				.getLabirinto();
		Partita p = new Partita(l);
		Comando prendi = new ComandoPrendi("piombo pesantissimo");
		prendi.esegui(p, new IOConsole());
		assertTrue(p.getGiocatore().getBorsa().isEmpty());
	}
	
	@Test
	public void testPrendiMoltepliciAttrezzi() {
		Labirinto l = new LabirintoBuilder()
				.addStanzaIniziale("atrio").addAttrezzo("spada", 2)
				.addAttrezzo("libro", 1)
				.getLabirinto();
		Partita p = new Partita(l);
		/*prende la spada*/
		Comando prendi = new ComandoPrendi("spada");
		prendi.esegui(p, new IOConsole());
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("spada"));
		/*prende il libro*/
		prendi = new ComandoPrendi("libro");
		prendi.esegui(p, new IOConsole());
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("libro"));
	}
}
