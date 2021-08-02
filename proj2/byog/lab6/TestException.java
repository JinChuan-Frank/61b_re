package byog.lab6;

public class TestException {
    public static void checkIfZero(int x) throws Exception{
        if (x == 0) {
            throw new Exception("x was zero!");
        }
        System.out.println(x);
    }
    public static int mystery(int x) {
        int counter = 0;
        try {
            while (true) {
                x = x / 2;
                checkIfZero(x);

            }
        }
    }
}



