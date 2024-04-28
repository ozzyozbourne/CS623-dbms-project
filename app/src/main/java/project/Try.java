package project;

public final class Try {

    public record Result<R, E extends Exception>(R value, E error){}
    public record ResultException<E extends Exception>(E error){}

    private Try(){}

    @FunctionalInterface
    public interface ThrowSupplier<R>  {

        R compute() throws Exception;

        static <R, E extends Exception> Result<R, E> apply(final ThrowSupplier<R> tf, final Class<E> e) {
            Result<R, E> r;
            try {
                r = new Result<>(tf.compute(), null);
            } catch (Exception exp) {
                r = new Result<>(null, e.cast(exp));
            }
            return r;
        }
    }

    @FunctionalInterface
    public interface ThrowConsumer{

        void compute() throws Exception;

        static <E extends Exception> ResultException<E> apply(final ThrowConsumer tc, final Class<E> e){
            ResultException<E> r;
            try {
                tc.compute();
                r = new ResultException<>(null);
            } catch (Exception exp) {
                r = new ResultException<>(e.cast(exp));
            }
            return r;
        }
    }
}
