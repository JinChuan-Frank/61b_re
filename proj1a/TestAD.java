public class TestAD {
    public  static  void testAddFirstLastRemove() {
        ArrayDeque a = new ArrayDeque();
        a.addFirst(0);
        a.addLast(1);
        a.addFirst(3);
        a.addLast(7);
        a.addLast(9);
        a.addLast(11);
        a.addLast(12);
        a.addFirst(13);
        a.addFirst(14);
        a.addFirst(15);
        a.removeFirst();
        a.addLast(20);
        a.addLast(21);
        /**
        a.removeLast();
        a.addFirst(7);

        a.removeLast();
        a.addFirst(10);
        a.removeFirst(); */
        a.printDeque();
        //System.out.print(a.get(9));

    }

    /**public  static  void testSort() {
        ArrayDeque a = new ArrayDeque();
        a.addFirst(0);
        a.addLast(1);
        a.addFirst(3);
        a.addLast(7);
        a.addLast(9);
        for (int i = 0; i < a.items.length; i += 1) {
            System.out.print(a.sortArray(a.items)[i]);
        }
    } */

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
        //testSort();
    }
}
