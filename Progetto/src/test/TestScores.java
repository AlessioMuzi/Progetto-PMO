package test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import utils.generics.Pair;
import controller.*;

//Test che verifica il salvataggio dei punteggi
public class TestScores {

	@Test
	public void testScore() {
		ScoreController h;
		final String fileName = "test1";

		System.out.println("Try to create a manager with invalid arguments");
		try {
			h = new ScoreControllerImpl("", 1);
			Assert.assertTrue("Created with wrong String!", false);
		} catch (final Exception e) {
			System.out.println("First attempt failed, OK!");
		}
		try {
			h = new ScoreControllerImpl(fileName, -1);
			Assert.assertTrue("Created with wrong int!", false);
		} catch (final Exception e) {
			System.out.println("Second attempt failed, OK!");
		}

		System.out.println("\nload an empty list");
		h = new ScoreControllerImpl(fileName, 3);
		final List<Pair<String, Integer>> comparisonList = new LinkedList<>();
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("The newly created score list must be empty!",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("add a new score");
		Pair<String, Integer> p = new Pair<>("Alessio", 10);
		comparisonList.add(p);
		h.addScore(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("The score list is different from the expected one",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("add two more scores (max capacity reached)");
		p = new Pair<>("Luca", 7);
		comparisonList.add(p);
		h.addScore(p);
		p = new Pair<>("AAA", 15);
		comparisonList.add(0, p);
		h.addScore(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("The score list is not correctly sorted",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("add another score (the lowest is removed)");
		p = new Pair<>("Franco", 12);
		comparisonList.remove(2);
		comparisonList.add(1, p);
		h.addScore(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("Luca should be out of the list!",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("clear the list");
		h.emptyScores();
		comparisonList.clear();
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("The list should be empty again!",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("save a non-empty list");
		p = new Pair<>("Prova1", 7);
		comparisonList.add(p);
		h.addScore(p);
		p = new Pair<>("Prova2", 6);
		comparisonList.add(p);
		h.addScore(p);
		p = new Pair<>("Prova3", 2);
		h.addScore(p);
		comparisonList.add(p);
		try {
			h.saveData();
		} catch (IllegalStateException | IOException e) {
			Assert.assertTrue("Error while saving!", false);
		}

		System.out.println("\n\nload again");
		h = new ScoreControllerImpl(fileName, 3);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("The list should be as before!", h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("load a list with a lower score limit");
		comparisonList.remove(2);
		h = new ScoreControllerImpl(fileName, 2);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("Only Prova2 and Prova1 should be in the list!",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("not saved, load again with higher score limit");
		h = new ScoreControllerImpl(fileName, 15);
		comparisonList.add(p);
		System.out.println("Current list: " + h.getScores() + "\nExpected: " + comparisonList + "\n\n");
		Assert.assertTrue("Prova3 should be in the list!",
				h.getScores().toString().equals(comparisonList.toString()));

		System.out.println("cleanup for next tests");
		h.emptyScores();
		comparisonList.clear();
		try {
			h.saveData();
		} catch (IllegalStateException | IOException e) {
			Assert.assertTrue("Error while saving!", false);
		}
	}
}