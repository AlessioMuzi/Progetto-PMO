package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import utils.generics.Pair;

//Classe che gestisce i punteggi dei giocatori
public class ScoreControllerImpl implements ScoreController {

	private final String                          fileName;
	private final int                             nScores;
	private boolean                               notSaved;
	private Optional<List<Pair<String, Integer>>> cache;

	//Costruttore
	public ScoreControllerImpl(final String file, final int n) {
		this.cache = Optional.empty();
		this.fileName = file;
		this.nScores = n;
		this.notSaved = false;
	}

	@Override
	public void addScore(final Pair<String, Integer> s) {
		if (!this.cache.isPresent())
			this.loadData();
		final List<Pair<String, Integer>> scores = this.cache.get();
		scores.add(s);
	    Collections.sort(scores, (a, b) -> b.getSecond() - a.getSecond());
		this.removeExcessScores(scores);
		this.cache = Optional.of(scores);
		this.notSaved = true;
	}

	//Metodo che legge i punteggi salvati
	private void loadData() {
		final List<Pair<String, Integer>> scores = new LinkedList<>();
		try (DataInputStream in = new DataInputStream(new FileInputStream(this.fileName))) {
			while (true) {
				final String name = in.readUTF();
				final Integer score = Integer.valueOf(in.readInt());
				scores.add(new Pair<String, Integer>(name, score));
			}
		} catch (final Exception e) {}
		Collections.sort(scores, (a, b) -> b.getSecond() - a.getSecond());
		if (this.removeExcessScores(scores))
			this.notSaved = true;
		this.cache = Optional.of(scores);
	}

	//Metodo che rimuove i punteggi in eccesso
	private boolean removeExcessScores(final List<Pair<String, Integer>> scores) {
		final boolean removedScores = scores.size() > this.nScores;
		while (scores.size() > this.nScores)
			scores.remove(this.nScores);
		
		return removedScores;
	}

	@Override
	public void saveData() throws IllegalStateException, IOException {
		if (this.cache.isPresent() && this.notSaved) {
			try (DataOutputStream out = new DataOutputStream(new FileOutputStream(this.fileName))) {
				for (final Pair<String, Integer> p : this.cache.get()) {
					out.writeUTF(p.getFirst());
					out.writeInt(p.getSecond().intValue());
				}
				this.notSaved = false;
			} catch (final Exception e) {
			    throw new IOException();
			}
		}
		  throw new IllegalStateException();
	}
	
    @Override
    public void emptyScores() {
        this.cache = Optional.of(new LinkedList<Pair<String, Integer>>());
        this.notSaved = true;
    }
	
	//Metodo getter (con copia in cache)
    @Override
    public List<Pair<String, Integer>> getScores() {
        if (!this.cache.isPresent())
            this.loadData();
        
        return new LinkedList<>(this.cache.get());
    }
}