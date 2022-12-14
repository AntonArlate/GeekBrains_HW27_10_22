// Волновой алгоритм
// прим: использование коллекций и возможно тип "object"

/**
 * program1
 */

import java.util.*;

public class program2 {

    static List<ArrayList<Integer>> labyrinth = new ArrayList<>(2);

    static List<Queue<Integer>> stack = new ArrayList<>(2);
    static Queue<Integer> stackX = new LinkedList<Integer>(); // из следующей лекции узнал что есть stack можно было попробовать через него сделать
    static Queue<Integer> stackY = new LinkedList<Integer>();
    static long time;

    public static void main(String[] args) throws InterruptedException { // спросить зачем оно для sleep
        stack.add(stackX);
        stack.add(stackY);

        poleGenerator();
        System.out.println("<<<>>>");
        labyrinth.get(1).set(1, 1);
        labyrinth.get(2).set(1, -1);
        labyrinth.get(1).set(2, -1);
        time = System.currentTimeMillis();
        waveAlg2(1, 1);
        // Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() - time);
        labPrint();

        // test();
        // test2();

    }

    static void waveAlg2(Integer x, Integer y) { // у int не может быть значения null
        stackX.add(x);
        stackY.add(y);

        while (true) {

            x = stackX.poll();
            if (x == null)
                break;
            y = stackY.poll();

            int step = labyrinth.get(y).get(x);
            if (x == 0 || y == 0 || x == labyrinth.get(0).size() - 1 || y == labyrinth.size() - 1) {
            } else {

                if (labyrinth.get(y - 1).get(x) < 0) {
                    labyrinth.get(y - 1).set(x, step + 1);
                    stackX.add(x);
                    stackY.add(y - 1);
                }

                if (labyrinth.get(y).get(x + 1) < 0) {
                    labyrinth.get(y).set(x + 1, step + 1);
                    stackX.add(x + 1);
                    stackY.add(y);
                }

                if (labyrinth.get(y + 1).get(x) < 0) {
                    labyrinth.get(y + 1).set(x, step + 1);
                    stackX.add(x);
                    stackY.add(y + 1);
                }

                if (labyrinth.get(y).get(x - 1) < 0) {
                    labyrinth.get(y).set(x - 1, step + 1);
                    stackX.add(x - 1);
                    stackY.add(y);
                }

            }
        }
    }

    static void waveAlg1(int x, int y) { // сибственно алгоритм
        int step = labyrinth.get(y).get(x);

        if (x == 0 || y == 0 || x == labyrinth.get(0).size() - 1 || y == labyrinth.size() - 1)
            return;

        if (labyrinth.get(y - 1).get(x) < 0 || labyrinth.get(y - 1).get(x) >= step + 2) { // up // labyrinth.get(y -
                                                                                          // 1).get(x) >= step + 2 ->
                                                                                          // это проверка на кольцевые
                                                                                          // пути
            labyrinth.get(y - 1).set(x, step + 1);
            waveAlg1(x, y - 1);
        }

        if (labyrinth.get(y).get(x + 1) < 0 || labyrinth.get(y).get(x + 1) >= step + 2) { // right
            labyrinth.get(y).set(x + 1, step + 1);
            waveAlg1(x + 1, y);
        }

        if (labyrinth.get(y + 1).get(x) < 0 || labyrinth.get(y + 1).get(x) >= step + 2) { // down
            labyrinth.get(y + 1).set(x, step + 1);
            waveAlg1(x, y + 1);
        }

        if (labyrinth.get(y).get(x - 1) < 0 || labyrinth.get(y).get(x - 1) >= step + 2) { // left
            labyrinth.get(y).set(x - 1, step + 1);
            waveAlg1(x - 1, y);
        }
    }

    static void poleGenerator() { // создание двумерного поля из клеток заполненых 0. В дальнейшем 0 = стена
        int xLenght = 11;
        int yLenght = 11;

        labyrinth = new ArrayList<>(yLenght); // второе измерение будет x [x1,x2,x3]
        for (int i = 0; i < yLenght; i++) {
            labyrinth.add(new ArrayList<Integer>(xLenght)); // первое измерение будет y [y1[x1,x2,x3],y2[x1,x2,x3]]
                                                            // arr.get[y].get[x]
        }

        for (int i = 0; i < yLenght; i++) {
            for (int j = 0; j < xLenght; j++) {
                labyrinth.get(i).add(j, 0);
            }
        }

        labyrinth.get(1).set(1, -1);
        labGenerator(1, 1);

        // System.out.println(labyrinth);
        labPrint();
        // int rnd = 1 + (int)(Math.random() * 4);
        // System.out.println(rnd);
    }

    static void labGenerator(int x, int y) { // созданиe лабиринта от точки указанной в первом вызове. поля для движения
                                             // будут отмечены -1
        int sizeY = labyrinth.size();
        int sizeX = labyrinth.get(0).size();
        boolean flag = true;

        while (flag) {
            int rnd = 1 + (int) (Math.random() * 4);
            flag = false;

            if (y - 2 > 0 && labyrinth.get(y - 2).get(x) == 0) {
                flag = true;
                if (rnd == 1) {
                    labyrinth.get(y - 1).set(x, -1);
                    labyrinth.get(y - 2).set(x, -1);
                    labGenerator(x, y - 2);

                }
            }

            if (x + 2 < sizeX - 1 && labyrinth.get(y).get(x + 2) == 0) {
                flag = true;
                if (rnd == 2) {
                    labyrinth.get(y).set(x + 1, -1);
                    labyrinth.get(y).set(x + 2, -1);
                    labGenerator(x + 2, y);

                }
            }

            if (y + 2 < sizeY - 1 && labyrinth.get(y + 2).get(x) == 0) {
                flag = true;
                if (rnd == 3) {
                    labyrinth.get(y + 1).set(x, -1);
                    labyrinth.get(y + 2).set(x, -1);
                    labGenerator(x, y + 2);

                }
            }

            if (x - 2 > 0 && labyrinth.get(y).get(x - 2) == 0) {
                flag = true;
                if (rnd == 4) {
                    labyrinth.get(y).set(x - 1, -1);
                    labyrinth.get(y).set(x - 2, -1);
                    labGenerator(x - 2, y);

                }
            }
        }

    }

    static void labPrint() { // распечатка лабиринта
        for (int i = 0; i < labyrinth.size(); i++) {
            for (int j = 0; j < labyrinth.get(i).size(); j++) {
                Object pole = labyrinth.get(i).get(j);
                if ((Integer) pole == 0) pole = "\u25A0";                
                else if ((Integer) pole == -1) pole = " ";

                System.out.printf("%-3s", pole);
            }
            System.out.println("< - line " + i);
        }
    }

    /*
     * static void test() { // пробую создать многомерный список способом создания
     * ArrayLists to ArrayList
     * // List<ArrayList<Integer>> labyrinth = new ArrayList<>(2); // инициализация
     * // main листа
     * // ArrayList<Integer> x = new ArrayList<Integer>(); // инициализация первого
     * // листа
     * // ArrayList<Integer> y = new ArrayList<Integer>(); // инициализация второго
     * 
     * // labyrinth.add(x); // добавляем в main лист ссылку на первый лист
     * // labyrinth.add(y); // добавляем в main лист ссылку на второй лист
     * 
     * // заполняем первый лист
     * x.add(1);
     * x.add(2);
     * x.add(3);
     * 
     * // заполняем второй лист
     * y.add(4);
     * y.add(5);
     * y.add(6);
     * 
     * // System.out.println(x.getClass());
     * // System.out.println(x.getClass().getName());
     * 
     * System.out.println(labyrinth.get(1).get(0));
     * 
     * y.set(0, 12);
     * System.out.println(labyrinth);
     * // не то, получилось просто 2 массива в одном.
     * }
     */
    static void test2() {
        int x_axis_length = 10;
        int y_axis_length = 10;

        List<ArrayList<Integer>> space = new ArrayList<>(y_axis_length); // новый лист длиной x
        for (int i = 0; i < y_axis_length; i++) {
            space.add(new ArrayList<Integer>(x_axis_length)); // добавляем строки необходимое число раз (теперь создаём
                                                              // новую ссылку на каждую строку)
        }

        space.get(0).add(0, 100);
        space.get(1).add(0, 110);
        space.get(1).add(1, 111);
        System.out.println(space);
        // чтото типо того
    }
}