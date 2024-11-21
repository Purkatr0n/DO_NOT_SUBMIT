package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.*;

import java.io.*;
import java.util.*;

/**
 * Manages a collection of words and their pronunciations from the CMU Pronouncing Dictionary.
 */
public class Dictionary implements IDictionary {

    private Map<String, IWord> words; // Stores words with their spellings as keys

    /**
     * Creates an empty dictionary.
     */
    public Dictionary() {
        words = new HashMap<>();
    }

    @Override
    public IWord getWord(String wordStr) {
        return words.get(wordStr);
    }

    @Override
    public void addWord(IWord word) {
        if (word == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }
        if (words.containsKey(word.getWord())) {
            throw new IllegalArgumentException("Word already exists in the dictionary");
        }
        words.put(word.getWord(), word);
    }

    @Override
    public int getWordCount() {
        return words.size();
    }

    @Override
    public int getPronunciationCount() {
        int count = 0;
        for (IWord word : words.values()) {
            count += word.getPronunciations().size();
        }
        return count;
    }

    @Override
    public void parseDictionaryLine(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith(";;;")) {
            return; // Ignore empty or comment lines
        }

        String[] parts = line.trim().split("\\s+", 2);
        if (parts.length < 2) return;

        String wordStr = parts[0].replaceAll("\\(\\d+\\)", ""); // Remove pronunciation numbers
        String[] phonemeStrings = parts[1].trim().split("\\s+");

        IWord word = words.computeIfAbsent(wordStr, Word::new); // Create word if it doesn't exist
        IPronunciation pronunciation = new Pronunciation();

        for (String ps : phonemeStrings) {
            String phonemeCode = ps;
            int stress = -1;

            // Extract stress if present
            if (Character.isDigit(ps.charAt(ps.length() - 1))) {
                stress = ps.charAt(ps.length() - 1) - '0';
                phonemeCode = ps.substring(0, ps.length() - 1);
            }

            try {
                Arpabet arpabet = Arpabet.valueOf(phonemeCode);
                pronunciation.add(new Phoneme(arpabet, stress));
            } catch (IllegalArgumentException ignored) {
                // Skip invalid phonemes
            }
        }

        word.addPronunciation(pronunciation);
    }

    @Override
    public void loadDictionary(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseDictionaryLine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading dictionary file: " + fileName, e);
        }
    }

    @Override
    public Set<String> getRhymes(String wordStr) {
        if (wordStr == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }

        IWord word = getWord(wordStr);
        if (word == null) {
            throw new IllegalArgumentException("Word is not in the dictionary");
        }

        Set<IPronunciation> targetPronunciations = word.getPronunciations();
        Set<String> rhymingWords = new HashSet<>();

        for (IWord otherWord : words.values()) {
            // Include the input word itself to allow for self-rhyming

            for (IPronunciation otherPron : otherWord.getPronunciations()) {
                for (IPronunciation targetPron : targetPronunciations) {
                    if (targetPron.rhymesWith(otherPron)) {
                        rhymingWords.add(otherWord.getWord());
                        break;
                    }
                }
                // Break out of the loop if we've added the word to avoid duplicate entries
                if (rhymingWords.contains(otherWord.getWord())) {
                    break;
                }
            }
        }

        return rhymingWords;
    }
}
