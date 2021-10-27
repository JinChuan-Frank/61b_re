package lab9;

public class FindSameHashValue {

    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static void generateString() {

        for (int i = 0; i < CHARACTERS.length; i ++) {
            for (int k = 0; k < CHARACTERS.length; k++) {
                StringBuilder stringBuilderA = new StringBuilder();
                stringBuilderA.append(CHARACTERS[i]);
                stringBuilderA.append(CHARACTERS[k]);
                String a = stringBuilderA.toString();
                for (int j = CHARACTERS.length - 1; j >= 0 ; j --) {
                    for (int m = CHARACTERS.length - 1; m >= 0; m --) {
                        StringBuilder stringBuilderB = new StringBuilder();
                        stringBuilderB.append(CHARACTERS[j]);
                        stringBuilderB.append(CHARACTERS[m]);
                        String b = stringBuilderB.toString();
                        //System.out.println(a + "&" + b);
                        if (checkHashValue(a, b) == true) {
                            System.out.println(a + " equals----- " + b);
                        }

                    }
                }
            }

        }
        System.out.println("did not find");
    }

    private static void printHashCode() {
        System.out.println("Tg:" + "Tg".hashCode());
        System.out.println("UH:" + "UH".hashCode());
    }

    private static void printNumber() {
        System.out.println(-1 / 2);

    }

    private static boolean checkHashValue(String a, String b) {
        return !a.equals(b) && a.hashCode() == b.hashCode();
    }

    public static void main(String[] args) {
        printNumber();
    }
}
