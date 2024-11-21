package uk.ac.aber.cs21120.rhymes.solution;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;


/**
 * The Phoneme class represents a phoneme in a pronunciation, consisting of an
 * Arpabet enum and an optional stress value.
 */
public class Phoneme implements IPhoneme {
    private Arpabet arpabet;
    private int stress;

    /**
     * Constructs a Phoneme with the specified Arpabet phoneme and stress.
     * The stress should be -1 if the phoneme is not a vowel.
     *
     * @param arpabet the Arpabet phoneme
     * @param stress the stress value, or -1 if the phoneme is not a vowel
     * @throws IllegalArgumentException if the stress is invalid or inconsistent with the phoneme type
     */
    public Phoneme(Arpabet arpabet, int stress) {
        if (arpabet == null) {
            throw new IllegalArgumentException("Arpabet phoneme cannot be null");
        }

        // Valid stress values are -1 (non-vowel), 0, 1, or 2
        if (stress != -1 && stress != 0 && stress != 1 && stress != 2) {
            throw new IllegalArgumentException("Invalid stress value: " + stress);
        }

        boolean isVowel = arpabet.isVowel();

        // Enforce that stress is -1 if the phoneme is not a vowel
        if (!isVowel && stress != -1) {
            throw new IllegalArgumentException("Non-vowel phoneme must have stress -1");
        }

        // Enforce that stress is not -1 if the phoneme is a vowel
        if (isVowel && stress == -1) {
            throw new IllegalArgumentException("Vowel phoneme must have a stress value of 0, 1, or 2");
        }

        this.arpabet = arpabet;
        this.stress = stress;
    }

    /**
     * Returns the ARPABET phoneme.
     *
     * @return the ARPABET phoneme
     */
    @Override
    public Arpabet getArpabet() {
        return arpabet;
    }

    /**
     * Gets the optional stress value.
     *
     * @return the stress value, or -1 if the phoneme is not a vowel
     */
    @Override
    public int getStress() {
        return stress;
    }

    /**
     * Returns true if the ARPABET value is the same as in the other phoneme. Stress is ignored.
     *
     * @param other the other phoneme to compare
     * @return true if the ARPABET value is the same as in the other phoneme
     * @throws IllegalArgumentException if the other phoneme is null
     */
    @Override
    public boolean hasSameArpabet(IPhoneme other) {
        if (other == null) {
            throw new IllegalArgumentException("Other phoneme cannot be null");
        }
        return this.arpabet == other.getArpabet();
    }
}
