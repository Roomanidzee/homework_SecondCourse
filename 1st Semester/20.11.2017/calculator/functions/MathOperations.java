package calculator.functions;

/**
 * 20.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class MathOperations {

    public long add(long a, long b){
        return a + b;
    }

    public long subtract(long a, long b){

        long result;

        if (a <= b) {
            if(a < b){
                result = -(a - b);
            }else{
                result = 0;
            }
        } else {
            result = a - b;
        }

        return result;
    }

    public long multiply(long a, long b){
        return a * b;
    }

    public long divide(long a, long b){

        if(b == 0){
            throw new IllegalArgumentException("Делить на 0 нельзя!");
        }

        return a / b;

    }
}
