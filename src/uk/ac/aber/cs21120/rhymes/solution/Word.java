package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IWord;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a word with its spelling and possible pronunciations.
 */
public class Word implements IWord {
    private String word; // The word's spelling
    private Set<IPronunciation> pronunciations; // Possible pronunciations of the word

    /**
     * Creates a new Word with the given spelling.
     *
     * @param word The spelling of the word (cannot be null or empty)
     */
    public Word(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
        this.word = word;
        this.pronunciations = new HashSet<>();
    }

    /**
     * Gets the spelling of the word.
     *
     * @return The word's spelling
     */
    @Override
    public String getWord() {
        return word;
    }

    /**
     * Adds a pronunciation to the word.
     *
     * @param pronunciation A pronunciation to add (cannot be null)
     */
    @Override
    public void addPronunciation(IPronunciation pronunciation) {
        if (pronunciation == null) {
            throw new IllegalArgumentException("Pronunciation cannot be null");
        }
        pronunciations.add(pronunciation);
    }

    /**
     * Gets all possible pronunciations of the word.
     *
     * @return A set of pronunciations
     */
    @Override
    public Set<IPronunciation> getPronunciations() {
        return pronunciations;
    }
}
