package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {

	private String direzioneBloccata;
	private String chiave;

	public StanzaBloccata(String nome, String direzioneBloccata, String chiave) {
		super(nome);
		this.chiave = chiave;
		this.direzioneBloccata = direzioneBloccata;

	}

	public String getDirezioneBloccata() {
		return direzioneBloccata;
	}

	public void setDirezioneBloccata(String direzioneBloccata) {
		this.direzioneBloccata = direzioneBloccata;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	@Override
	public Stanza getStanzaAdiacente(String direzione) {
		if (this.hasAttrezzo(this.chiave) || !this.direzioneBloccata.equals(direzione))
			return super.getStanzaAdiacente(direzione);
		else
			return this;
	}

	@Override
	public String getDescrizione() {
		String s = super.getDescrizione();
		if (!this.hasAttrezzo(this.chiave))
			s = s + '\n' + "La direzione " + this.direzioneBloccata + " è bloccata." + '\n'
					+ "Posa l'attrezzo giusto per sbloccarla";
		else
			s = s + '\n' + "La direzione " + this.direzioneBloccata + " è sbloccata.";

		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || this.getClass() != obj.getClass())
			return false;
		StanzaBloccata that = (StanzaBloccata) obj;
		return super.equals(obj) && this.chiave.equals(that.getChiave()) && this.direzioneBloccata.equals(that.getDirezioneBloccata());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + this.chiave.hashCode() + this.direzioneBloccata.hashCode();
	}
}
