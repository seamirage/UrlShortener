package validation;

//TODO: should be a chain of validators instead of single validator
public class UriValidator {
    public UriValidator(int maxUriSize, String forbiddenSubstring) {
        this.maxUriSize = maxUriSize;
        this.forbiddenSubstring = forbiddenSubstring;
    }

    public boolean isValid(String uri) {
        return uri.length() <= maxUriSize && !uri.contains(forbiddenSubstring);
    }

    private final int maxUriSize;
    private final String forbiddenSubstring;
}
