package it.uniroma3.diadia.ambienti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.uniroma3.diadia.Direzioni;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

/**
 * Classe Stanza - una stanza in un gioco di ruolo. Una stanza e' un luogo
 * fisico nel gioco. E' collegata ad altre stanze attraverso delle uscite. Ogni
 * uscita e' associata ad una direzione.
 * 
 * @author docente di POO e 589489 e 589300
 * @see Attrezzo
 * @version v1.0
 */

public class Stanza implements Comparable<Stanza>{

	private String nome;
	private List<Attrezzo> attrezzi;
	private Map<Direzioni, Stanza> stanzeAdiacenti;
	private AbstractPersonaggio personaggio;

	/**
	 * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
	 * 
	 * @param nome il nome della stanza
	 * @param personaggio nella stanza
	 */
	public Stanza(String nome, AbstractPersonaggio p) {
		this.nome = nome;
		this.stanzeAdiacenti = new HashMap<>();
		this.attrezzi = new ArrayList<>();
		this.personaggio = p;
	}
	
	/**
	 * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
	 * 
	 * @param nome il nome della stanza
	 */
	public Stanza(String nome) {
		this(nome, null);
	}

	/**
	 * Imposta una stanza adiacente.
	 *
	 * @param direzione direzione in cui sara' posta la stanza adiacente.
	 * @param stanza    stanza adiacente nella direzione indicata dal primo
	 *                  parametro.
	 */
	public void impostaStanzaAdiacente(String direzione, Stanza stanza) {
		for(Direzioni d : Direzioni.values())
			if(d.toString().equals(direzione))
				this.stanzeAdiacenti.put(d, stanza);
	}

	/**
	 * Restituisce la stanza adiacente nella direzione specificata
	 * 
	 * @param direzione
	 */
	public Stanza getStanzaAdiacente(String direzione) {
		for(Direzioni d : Direzioni.values())
			if(d.toString().equals(direzione))
				return this.stanzeAdiacenti.get(d);
		return null;
	}

	/**
	 * Restituisce la mappa delle stanze adiacenti nella direzione specificata
	 * 
	 * 
	 */
	public Map<Direzioni, Stanza> getStanzeAdiacenti(){
		return this.stanzeAdiacenti;
	}

	/**
	 * Restituisce la nome della stanza.
	 * 
	 * @return il nome della stanza
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Restituisce la descrizione della stanza.
	 * 
	 * @return la descrizione della stanza
	 */
	public String getDescrizione() {
		return this.toString();
	}

	/**
	 * Restituisce l'insieme delle direzioni in cui poter andare
	 * 
	 * @return l'insieme delle direzioni
	 */
	public Set<Direzioni> getDirezioni() {
		return this.stanzeAdiacenti.keySet();
	}

	/**
	 * Restituisce la lista di attrezzi presenti nella stanza.
	 * 
	 * @return la lista di attrezzi nella stanza.
	 */
	public List<Attrezzo> getAttrezzi() {
		return this.attrezzi;
	}

	public Map<Direzioni, Stanza> getMapStanzeAdiacenti() {
		return this.stanzeAdiacenti;
	}

	/**
	 * Mette un attrezzo nella stanza.
	 * 
	 * @param attrezzo l'attrezzo da mettere nella stanza.
	 * @return true se riesce ad aggiungere l'attrezzo, false atrimenti.
	 */
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (attrezzo == null || this.attrezzi.contains(attrezzo))
			return false;
		return this.attrezzi.add(attrezzo);
	}

	/**
	 * Restituisce una rappresentazione stringa di questa stanza, stampadone la
	 * descrizione, le uscite e gli eventuali attrezzi contenuti
	 * 
	 * @return la rappresentazione stringa
	 */
	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append(this.nome);
		risultato.append("\nUscite: ");
		if(!this.stanzeAdiacenti.isEmpty())
			risultato.append(this.getDirezioni().toString());
		risultato.append("\nAttrezzi nella stanza: ");
		if(!this.attrezzi.isEmpty())
			risultato.append(this.attrezzi.toString()+"\n");
		if(this.getPersonaggio() != null)
			risultato.append(this.getPersonaggio().getNome());
		return risultato.toString();
	}

	/**
	 * Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	 * 
	 * @return true se l'attrezzo esiste nella stanza, false altrimenti.
	 */
	public boolean hasAttrezzo(String nomeAttrezzo) {
		if(nomeAttrezzo == null)
			return false;
		Attrezzo a = new Attrezzo(nomeAttrezzo, 0);
		return this.attrezzi.contains(a);
	}

	/**
	 * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * 
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza. null se l'attrezzo non e' presente.
	 */
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		if (!this.hasAttrezzo(nomeAttrezzo))
			return null;
		Attrezzo a = new Attrezzo(nomeAttrezzo, 0);
		return this.attrezzi.get(this.attrezzi.indexOf(a));
	}

	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * 
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(Attrezzo attrezzo) {
		return this.attrezzi.remove(attrezzo);
	}


	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		Stanza that = (Stanza) obj;
		return this.nome.equals(that.getNome());
	}
	
	@Override
	public int compareTo(Stanza s1) {
		return this.nome.compareTo(s1.getNome());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode()+ this.nome.hashCode();
	}

	public void setPersonaggio(AbstractPersonaggio p) {
		this.personaggio = p;
	}
	
	public AbstractPersonaggio getPersonaggio() {
		return this.personaggio;
	}

}