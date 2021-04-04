public class TestAD {
    public  static  void testAddFirstLast() {
        ArrayDeque a = new ArrayDeque();
        for (int i = 0; i <= 3; i += 1) {
            a.addFirst(i);
            a.addLast(i);
        }
        a.printDeque();
    }

    public static int getPosTest(){
        ArrayDeque a = new ArrayDeque();
        for (int i = 0; i <= 3; i += 1) {
            a.addFirst(i);
            a.addLast(i);
        }
        return a.getPos(7);
    }
    public static void main(String[] args){
        testAddFirstLast();
        //System.out.print(getPosTest());
    }
}
