package idgeneration;

public class AlphabetBasedLongIdToStringConverterImpl implements LongIdToStringConverter {

    public AlphabetBasedLongIdToStringConverterImpl(Alphabet alphabet, int idSize) {
        this.alphabet = alphabet;
        this.idSize = idSize;
        CalculateBaseDegrees();
        CalculateMaxNumber();
    }

    @Override
    public String Convert(long value) {
        CheckIdOnPreconditions(value);
        int index = idSize - 1;
        char[] charArray = new char[idSize];
        long currentNumber = value;

        while (index >= 0) {
            int remainder = (int)(currentNumber % alphabet.getCount());
            charArray[index--] = alphabet.GetCorrespondingCharacter(remainder);
            currentNumber = currentNumber / alphabet.getCount();
        }

        return new String(charArray);
    }

    private void CheckIdOnPreconditions(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id should be greater than zero");
        }

        if (id > maxNumberThatCanBeConverted) {
            throw new IllegalArgumentException("In given numeral system id should be lower than " + maxNumberThatCanBeConverted);
        }
    }

    private void CalculateMaxNumber() {
        long maxNumber = 0;
        int maxCoefficient = (alphabet.getCount() - 1);
        for (int i = 0; i < idSize; i++) {
            maxNumber += maxCoefficient * baseDegrees[i];
        }

        maxNumberThatCanBeConverted = maxNumber;
    }

    private void  CalculateBaseDegrees() {
        baseDegrees = new long[idSize];
        baseDegrees[0] = 1;
        for (int i = 1; i < baseDegrees.length; i++) {
            baseDegrees[i] = baseDegrees[i - 1] * alphabet.getCount();
        }
    }

    private Alphabet alphabet;
    private int idSize;
    private long maxNumberThatCanBeConverted;
    private long[] baseDegrees;
}