package utils.generics;

//Classe che implementa una generica coppia di elementi
public class Pair<A, B> {

	private final A a;
	private final B b;

	//Costruttore
	public Pair(final A first, final B second) {
		this.a = first;
		this.b = second;
	}

    //Metodi getter
	public A getFirst() {
		return this.a;
	}

	public B getSecond() {
		return this.b;
	}
}