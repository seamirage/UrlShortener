package idgeneration;

public interface Alphabet {
        long getCharacterCode(Character c);
        char getCorrespondingCharacter(int code);
        int getCount();
}
