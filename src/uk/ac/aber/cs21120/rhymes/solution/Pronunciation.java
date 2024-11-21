package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pronunciation as a list of phonemes.
 */
public class Pronunciation implements IPronunciation {
    private List<IPhoneme> phonemes; // List of phonemes in the pronunciation

    /**
     * Creates an empty Pronunciation.
     */
    public Pronunciation() {
        phonemes = new ArrayList<>();
    }

    /**
     * Adds a phoneme to the pronunciation.
     *
     * @param p The phoneme to add (cannot be null)
     * @throws IllegalArgumentException if the phoneme is null
     */
    @Override
    public void add(IPhoneme p) {
        if (p == null) {
            throw new IllegalArgumentException("Phoneme cannot be null");
        }
        phonemes.add(p);
    }

    /**
     * Gets the list of phonemes in this pronunciation.
     *
     * @return A list of phonemes
     */
    @Override
    public List<IPhoneme> getPhonemes() {
        return phonemes;
    }

    /**
     * Finds the index of the final stressed vowel in the pronunciation.
     *
     * @return The index of the final stressed vowel, or -1 if none is found
     */
    @Override
    public int findFinalStressedVowelIndex() {
        // First, look for the last vowel with primary stress (1)
        for (int i = phonemes.size() - 1; i >= 0; i--) {
            IPhoneme p = phonemes.get(i);
            if (p.getArpabet().isVowel() && p.getStress() == 1) {
                return i;
            }
        }
        // Next, look for the last vowel with secondary stress (2)
        for (int i = phonemes.size() - 1; i >= 0; i--) {
            IPhoneme p = phonemes.get(i);
            if (p.getArpabet().isVowel() && p.getStress() == 2) {
                return i;
            }
        }
        // Finally, look for the last vowel with neutral stress (0)
        for (int i = phonemes.size() - 1; i >= 0; i--) {
            IPhoneme p = phonemes.get(i);
            if (p.getArpabet().isVowel() && p.getStress() == 0) {
                return i;
            }
        }
        return -1; // No stressed vowel found
    }

    /**
     * Checks if this pronunciation rhymes with another pronunciation.
     *
     * @param other The other pronunciation to compare
     * @return True if the pronunciations rhyme, false otherwise
     */
    @Override
    public boolean rhymesWith(IPronunciation other) {
        int thisIndex = this.findFinalStressedVowelIndex();
        int otherIndex = other.findFinalStressedVowelIndex();

        // If either pronunciation has no stressed vowel, they can't rhyme
        if (thisIndex == -1 || otherIndex == -1) {
            return false;
        }

        // Get the sequences from the final stressed vowel to the end
        List<IPhoneme> thisPhonemes = phonemes.subList(thisIndex, phonemes.size());
        List<IPhoneme> otherPhonemes = other.getPhonemes().subList(otherIndex, other.getPhonemes().size());

        // If the sequences differ in length, they can't rhyme
        if (thisPhonemes.size() != otherPhonemes.size()) {
            return false;
        }

        // Compare each phoneme in the sequences
        for (int i = 0; i < thisPhonemes.size(); i++) {
            if (!thisPhonemes.get(i).hasSameArpabet(otherPhonemes.get(i))) {
                return false;
            }
        }

        return true; // All phonemes match, so the pronunciations rhyme
    }
}
