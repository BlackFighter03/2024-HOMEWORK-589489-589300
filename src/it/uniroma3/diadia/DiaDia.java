package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author docente di POO e 589489 e 589300 (da un'idea di Michael Kolling and
 *         David J. Barnes)
 * 
 * @version v1.0
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""
			+ "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
			+ "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
			+ "I locali sono popolati da strani personaggi, " + "alcuni amici, altri... chissa!\n"
			+ "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
			+ "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
			+ "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
			+ "Per conoscere le istruzioni usa il comando 'aiuto'.";

	static final private String[] elencoComandi = { "vai", "prendi", "posa", "aiuto", "fine" };

	private Partita partita;
	private IOConsole io;

	public DiaDia(IOConsole console) {
		this.partita = new Partita();
		this.io = console;
	}

	public void gioca() {
		String istruzione;

		io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do
			istruzione = io.leggiRiga();
		while (!processaIstruzione(istruzione));
	}

	/**
	 * Processa una istruzione
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false
	 *         altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		if(!istruzione.isEmpty()) {
			Comando comandoDaEseguire = new Comando(istruzione);
			if (comandoDaEseguire.getNome().equals("fine")) {
				this.fine();
				return true;
			} else if (comandoDaEseguire.getNome().equals("vai"))
				this.vai(comandoDaEseguire.getParametro());
			else if (comandoDaEseguire.getNome().equals("aiuto"))
				this.aiuto();
			else if (comandoDaEseguire.getNome().equals("prendi"))
				this.prendi(comandoDaEseguire.getParametro());
			else if (comandoDaEseguire.getNome().equals("posa"))
				this.posa(comandoDaEseguire.getParametro());
			else
				io.mostraMessaggio("Comando sconosciuto");
		}
		if (this.partita.vinta()) {
			io.mostraMessaggio("Hai vinto!");
			return true;
		} else
			return false;
	}

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		String str = elencoComandi[0] + " ";
		for (int i = 1; i < elencoComandi.length; i++)
			str = str + elencoComandi[i] + " ";
		io.mostraMessaggio(str);
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra e ne stampa il
	 * nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if (direzione == null)
			io.mostraMessaggio("Dove vuoi andare?");
		Stanza prossimaStanza = null;
		prossimaStanza = this.partita.getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			io.mostraMessaggio("Direzione inesistente");
		else {
			this.partita.setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getGiocatore().getCfu();
			this.partita.getGiocatore().setCfu(cfu--);
		}
		io.mostraMessaggio("Stanza corrente:" + '\n' + partita.getStanzaCorrente().getDescrizione());
		io.mostraMessaggio(this.partita.getGiocatore().getBorsa().toString());
	}

	/**
	 * Comando "prendi".
	 * 
	 * @param nomeAttrezzo
	 */
	private void prendi(String nomeAttrezzo) {
		if (nomeAttrezzo == null)
			io.mostraMessaggio("Che attrezzo vuoi prendere?");
		if (this.partita.getStanzaCorrente().hasAttrezzo(nomeAttrezzo) != true)
			io.mostraMessaggio("Attrezzo non è presente nella stanza");
		else {
			Attrezzo attrezzo = this.partita.getStanzaCorrente().getAttrezzo(nomeAttrezzo);
			if (this.partita.getGiocatore().getBorsa().addAttrezzo(attrezzo)) {
				this.partita.getStanzaCorrente().removeAttrezzo(attrezzo);
				io.mostraMessaggio("Hai preso l'oggetto dalla stanza e inserito nella borsa");
			} else {
				io.mostraMessaggio("La borsa non può contenere questo attrezzo se no è troppo pesante");
			}
		}

	}

	/**
	 * Comando "Posa".
	 * 
	 * @param nomeAttrezzo
	 */
	private void posa(String nomeAttrezzo) {
		if (nomeAttrezzo == null)
			io.mostraMessaggio("Che attrezzo vuoi posare?");
		if (this.partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo)) {
			Attrezzo attrezzo = this.partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
			if (this.partita.getStanzaCorrente().addAttrezzo(attrezzo) == false) {
				io.mostraMessaggio("La stanza ha troppi oggetti. Non lo puoi posare");
				this.partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
			} else
				io.mostraMessaggio("L'attrezzo è stato posato nella stanza");
		} else
			io.mostraMessaggio("Attrezzo non è presente nella stanza");
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		io.mostraMessaggio("Grazie di aver giocato!"); // si desidera smettere
	}

	public static void main(String[] argc) {
		IOConsole console = new IOConsole();
		DiaDia gioco = new DiaDia(console);
		gioco.gioca();
	}
}