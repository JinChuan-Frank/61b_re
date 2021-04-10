public class TestAD {
    public  static  void testAddFirstLastRemove() {
        ArrayDeque a = new ArrayDeque();
        for (int i = 0; i <= 3; i += 1) {
            a.addFirst(i);
            a.addLast(i);
        }
        for (int i = 0; i <= 5; i += 1) {
            a.removeFirst();
        }
        a.printDeque();
    }

    public static int getPosTest() {
        ArrayDeque a = new ArrayDeque();
        for (int i = 0; i <= 3; i += 1) {
            a.addFirst(i);
            a.addLast(i);
        }
        return a.getPos(7);
    }
    public static void main(String[] args) {
        testAddFirstLastRemove();
        //System.out.print(getPosTest());
    }
}
