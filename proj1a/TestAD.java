public class TestAD {
    public  static  void testAddFirstLastRemove() {
        ArrayDeque a = new ArrayDeque();
        for (int i = 0; i < 20; i += 1){
            a.addLast(i);
        }
        a.addFirst(100);
        a.addFirst(101);

        a.printDeque();
        System.out.println("get" + a.get(21));

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
