package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Mago extends AbstractPersonaggio {

	private static final String MESSAGGIO_DONO = "Sei un vero simpaticone, " +
			"con una mia magica azione, troverai un nuovo oggetto " +
			"per il tuo borsone!";
	private static final String MESSAGGIO_SCUSE = "Mi spiace, ma non ho piu' nulla...";
	
	public Mago(String nome, String presentazione, Attrezzo attrezzo) {
		super(nome, presentazione, attrezzo);
	}
	
	public Mago() {
		super(null, null, null);
	}

	@Override
	public String agisci(Partita partita) {
		String msg;
		if (this.getAttrezzo()!=null) {
			partita.getStanzaCorrente().addAttrezzo(this.getAttrezzo());
			this.setAttrezzo(null);
			msg = MESSAGGIO_DONO;
		}
		else {
			msg = MESSAGGIO_SCUSE;
		}
		return msg;

	}

	@Override
	public String riceviRegalo(Attrezzo a, Partita partita) {
		a.setPeso(a.getPeso()/2);
		partita.getStanzaCorrente().addAttrezzo(a);
		return "Grazie del regalo, lo poserò nella stanza dopo avergli fatto una magia!";
	}
	
}