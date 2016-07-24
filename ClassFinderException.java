public class ClassFinderException extends RuntimeException {

    private static final long serialVersionUID = 993518867346902740L;

    public ClassFinderException(final String message) {

        super(message);
    }

    public ClassFinderException(final Throwable throwable, String message) {
        super(message, throwable);
    }

    public ClassFinderException(Throwable throwable) {
        super(throwable);
    }
}
