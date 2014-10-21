package idgeneration;

/**
 * Represents a generator of unique long identifiers.
 */
public interface IdGenerator {
    /**
     * @return next unique identifier
     */
    long next();
}
