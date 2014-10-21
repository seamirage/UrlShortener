package idgeneration;

/**
 * Represents an alphabet consisting of the symbols.
 */
public interface Alphabet {
    long getCharacterCode(Character c);
    char getCorrespondingCharacter(int code);

    /**
     * @return total count of symbols used in alphabet
     */
    int getCount();
}
