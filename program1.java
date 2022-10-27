// Волновой алгоритм
// прим: использование коллекций и возможно тип "object"

/**
 * program1
 */
import java.util.*;

public class program1 {

    static List<ArrayList<Integer>> labyrinth = new ArrayList<>(2);
    static ArrayList<Integer> x = new ArrayList<Integer>();
    static ArrayList<Integer> y = new ArrayList<Integer>();

    public static void main(String[] args) {
        // labyrinth.add(x);
        // labyrinth.add(y);

        poleGenerator();
        System.out.println("<<<>>>");
        labyrinth.get(1).set(1, 1);
        waveAlg(1, 1);
        labPrint();


        // test();
        // test2();

    }

    static void waveAlg(int x, int y){ // сибственно алгоритм
        int step = labyrinth.get(y).get(x);


        if (x == 0 || y == 0 || x == labyrinth.get(0).size()-1 || y == labyrinth.size()-1) return;

        if (labyrinth.get(y-1).get(x) < 0) { //up
            labyrinth.get(y-1).set(x, step + 1);
            waveAlg(x, y-1);
        }

        if (labyrinth.get(y).get(x+1) < 0) { //right
            labyrinth.get(y).set(x+1, step + 1);
            waveAlg(x+1, y);
        }

        if (labyrinth.get(y+1).get(x) < 0) { //down
            labyrinth.get(y+1).set(x, step + 1);
            waveAlg(x, y+1);
        }

        if (labyrinth.get(y).get(x-1) < 0) { //left
            labyrinth.get(y).set(x-1, step + 1);
            waveAlg(x-1, y);
        }
    }

    static void poleGenerator() { // создание двумерного поля из клеток заполненых 0. В дальнейшем 0 = стена
        int xLenght = 11;
        int yLenght = 11;

        labyrinth = new ArrayList<>(xLenght); // второе измерение будет x [x1,x2,x3]
        for (int i = 0; i < xLenght; i++) {
            labyrinth.add(new ArrayList<Integer> (yLenght)); //первое измерение будет y [y1[x1,x2,x3],y2[x1,x2,x3]] arr.get[y].get[x]
        }

        for (int i = 0; i < yLenght; i++) {
            for (int j = 0; j < xLenght; j++) {
                labyrinth.get(i).add(j,0);
            }
        }

        labyrinth.get(1).set(1, -1);
        labGenerator(1,1);


        // System.out.println(labyrinth);
        labPrint();
        // int rnd = 1 + (int)(Math.random() * 4);
        // System.out.println(rnd);
    }

    static void labGenerator(int x, int y){ // создания лабиринта от точки указанной в первом вызове. поля для движения будут отмечены -1
        int sizeY = labyrinth.size();
        int sizeX = labyrinth.get(0).size();
        boolean flag = true;
        

        while (flag) {
            int rnd = 1 + (int)(Math.random() * 4);
            flag = false;

        if (y-2 > 0 && labyrinth.get(y-2).get(x) == 0) {
            flag = true;
            if (rnd == 1) {
                labyrinth.get(y-1).set(x, -1);
                labyrinth.get(y-2).set(x, -1);
                labGenerator(x, y-2);
                
            }
        }

        if (x+2 < sizeX-1 && labyrinth.get(y).get(x+2) == 0) {
            flag = true;
            if (rnd == 2) {
                labyrinth.get(y).set(x+1, -1);
                labyrinth.get(y).set(x+2, -1);
                labGenerator(x+2, y); 
                
            }
        }

        if (y+2 < sizeY-1 && labyrinth.get(y+2).get(x) == 0) {
            flag = true;
            if (rnd == 3) {
                labyrinth.get(y+1).set(x, -1);
                labyrinth.get(y+2).set(x, -1);
                labGenerator(x, y+2);
                
            }
        }

        if (x-2 > 0 && labyrinth.get(y).get(x-2) == 0) {
            flag = true;
            if (rnd == 4) {
                labyrinth.get(y).set(x-1, -1);
                labyrinth.get(y).set(x-2, -1);
                labGenerator(x-2, y);
                
            }
        }
    }

    }

    static void labPrint() { // распечатка лабиринта
        for (int i = 0; i < labyrinth.size(); i++) {
            for (int j = 0; j < labyrinth.get(i).size(); j++) {
                Object pole = labyrinth.get(i).get(j);
                if ((Integer) pole == 0) {
                    pole = "\u25A0";
                }
                System.out.printf("%-3s", pole);
            }
            System.out.println("< - line " + i);
        }
    }

    static void test() { // пробую создать многомерный список способом создания ArrayLists to ArrayList
        // List<ArrayList<Integer>> labyrinth = new ArrayList<>(2); // инициализация
        // main листа
        // ArrayList<Integer> x = new ArrayList<Integer>(); // инициализация первого
        // листа
        // ArrayList<Integer> y = new ArrayList<Integer>(); // инициализация второго

        // labyrinth.add(x); // добавляем в main лист ссылку на первый лист
        // labyrinth.add(y); // добавляем в main лист ссылку на второй лист

        // заполняем первый лист
        x.add(1);
        x.add(2);
        x.add(3);

        // заполняем второй лист
        y.add(4);
        y.add(5);
        y.add(6);

        // System.out.println(x.getClass());
        // System.out.println(x.getClass().getName());

        System.out.println(labyrinth.get(1).get(0));

        y.set(0, 12);
        System.out.println(labyrinth);
        // не то, получилось просто 2 массива в одном.
    }

    static void test2() { 
        int x_axis_length = 10;
        int y_axis_length = 10;

        List<ArrayList<Integer>> space = new ArrayList<>(x_axis_length); // новый лист длиной x
        for (int i = 0; i < x_axis_length; i++) {
            space.add(new ArrayList<Integer> (y_axis_length)); // добавляем строки необходимое число раз (теперь создаём новую ссылку на каждую строку)
        }

        space.get(0).add(0,100);
        space.get(1).add(0,110);
        space.get(1).add(1,111);
        System.out.println(space);
        // чтото типо того
    }
}