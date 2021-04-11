public class TestAD {
    public  static  void testAddFirstLastRemove() {
        ArrayDeque a = new ArrayDeque();
        a.addLast(0);
        a.addLast(3);
        a.removeLast();
        a.addFirst(5);
        a.addFirst(6);
        a.addFirst(7);
        a.addLast(8);
        a.removeLast();
        a.addFirst(10);
        a.removeFirst();
        System.out.print(a.get(0));

    }

    /**public static int getPosTest() {
        ArrayDeque a = new ArrayDeque();
        for (int i = 0; i <= 3; i += 1) {
            a.addFirst(i);
            a.addLast(i);
        }
        return (int) a.get(7);
    } */

    public static void main(String[] args) {
        testAddFirstLastRemove();
        //System.out.print(getPosTest());
    }
}
