package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IDictionary;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;

import java.util.Scanner;
import java.util.Set;

/**
 * The Main class allows the user to type in a word and get all the rhymes for that word.
 */
public class Main {
    public static void main(String[] args) {
        // Create a new dictionary instance
        IDictionary dictionary = new Dictionary();

        // Load the dictionary file
        // Assuming the dictionary file is named "cmudict.txt" and is in the working directory
        System.out.println("Loading dictionary...");
        dictionary.loadDictionary("src/uk/ac/aber/cs21120/rhymes/solution/cmudict.dict"); // The filename is ignored as per your Dictionary implementation
        System.out.println("Dictionary loaded.");

        // Create a Scanner to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a word
        System.out.print("Enter a word to find its rhymes: ");
        String inputWord = scanner.nextLine().trim().toLowerCase();

        // Close the scanner
        scanner.close();

        // Get the rhymes for the input word
        try {
            Set<String> rhymes = dictionary.getRhymes(inputWord);

            // Check if any rhymes were found
            if (rhymes.isEmpty()) {
                System.out.println("No rhymes found for the word \"" + inputWord + "\".");
            } else {
                System.out.println("Words that rhyme with \"" + inputWord + "\":");
                for (String rhyme : rhymes) {
                    System.out.println(rhyme);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("The word \"" + inputWord + "\" is not in the dictionary.");
        }
    }
}
