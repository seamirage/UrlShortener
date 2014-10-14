package idgeneration;

public interface Alphabet {
        long GetCharacterCode(Character c);
        char GetCorrespondingCharacter(int code);
        int getCount();
}
