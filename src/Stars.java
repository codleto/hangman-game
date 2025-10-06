/*
самое базовое что нужно сделать
1 картинка это массив 2д походу массив (создать массив который будет менять заполнение массива )
2 массив с подготовленными словами и рандом который будет доставать слово по рандомному индексу
3 метод должен посчитать сколько букв в слове и начертить соответсвующие поля, а точнее изменить самый
псоледний список двемерного массива
4 вся игра тоже должна быть в цикле чтобы начать заново или когда проиграл вышибает в конец
но сначала пишет что проиграл
5 панель ввода цикличного через ду вайл
- проверка есть ли напечатанная буква в слове и открывать эту букву если есть а если нет изменять картинку масива 2д

далее улучшения после работующего прототипа
 */


import java.util.Random;
import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        do {
            startOfTheGame();

        } while (true);


    }

    public static void game(){
        do {
            show();
            String s = scanner();
            chekUp(s);
            winGame();
            loseGame();
        } while (true);
    }

    public static void startOfTheGame() {
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        Scanner inputChose = new Scanner(System.in);
        int newGame = inputChose.nextInt();
        do {
            if (newGame == 1) {
                restart();

                game();
            }
            else if(newGame == 2) {
                if (win == 0 && lose == 0){
                    System.out.println("Ты еще не сыграл, статистики нет ;)");
                    System.out.println("0 - Выход");
                    Scanner exit = new Scanner(System.in);
                    int exi;
                    do {
                        exi = exit.nextInt();
                        if(exi != 0) {
                            System.out.println("Неверный ввод");
                        }
                    }while(exi != 0);
                    startOfTheGame();
                } else {
                    System.out.println("Твоя статистика!");
                    System.out.println("Выигрыши: " + win);
                    System.out.println("Проигрыши: " + lose);
                    System.out.println("0 - Выход");
                    Scanner exit = new Scanner(System.in);
                    int exi;
                    do {
                        exi = exit.nextInt();
                    if(exi != 0) {
                        System.out.println("Неверный ввод");
                    }
                    }while(exi != 0);
                    startOfTheGame();

                }

            } else {
                System.out.println("Ошибка ввода! Введите нужную цифру (1 или 2)");
            }
        } while (true);
    }

    public static void winGame() {
        if(word.length() == good) {
            System.out.println("ТЫ ВЫИГРАЛ!!!");
            win++;
            startOfTheGame();
        }
    }

    public static void loseGame() {
        if(initial == 6){
            show();
            System.out.println("Вы проиграли!");
            lose++;
            startOfTheGame();
        }

    }

    //СЧЕТЧИКИ
    public static int win = 0; // счетчик выигрышей
    public static int lose = 0; // счетчик проигрышей
    public static int initial = 0; // счетчик ошибок
    public static int nev = 1; //счетчик неверных букв
    public static int good = 0; // счетчик отгаданных букв

    // Поле
    static String[][] playingField = {{"__", "__", "_ "},
            {"| ", "  ", " |"},
            {"| ", "  ", "  "},
            {"| ", "  ", "   "},
            {"| ", " ", "    "},
            {"A ", "  ", "  "},
            {" "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"Неверно введенные буквы :", "", "", "", "", "", ""}};
    //Список слов
    static String[] collectionWord = {"Кукуруза", "Болото", "Автомобиль", "Чебурек", "Жираф", "Бамбук"};

    // рандомное слово
    static String word = randomWord();


    public static String randomWord() {
        Random random = new Random();
        int randomWord = random.nextInt(collectionWord.length);
        String chooseWord = collectionWord[randomWord];

        // показать сколько букв в слове
        int lengthWord = chooseWord.length();

        // вставка количества букв в виде "_" в поле
        for (int i = 0; i < lengthWord; i++) {
            playingField[7][i] = "_ ";
        }
        return chooseWord;
    } // // Вытаскиваем рандомное слово из списка и делаем под него количество ячеек в поле

    public static void chekUp(String a) {
        //  сравнить буквы
        int i = 0;
        int e = 0;
        String x = a.substring(0, 1);
        while (i < word.length()) {
            String y = word.substring(i, i + 1);
            if (x.equalsIgnoreCase(y)) {
                playingField[7][i] = y + " ";
                good++;
                e++;
            }
            i++;

        }
        if (e == 0) {
            playingField[8][nev] = x + " ";
            initial++;
            error(initial);
            nev++;
        }


    }  // проверка есть ли такая буква в слове и вывод неверных букв

    public static String scanner() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите ТОЛЬКО одну букву");
            String sc = scanner.nextLine();
            if (sc.length() != 1) {
                System.out.println("Вы ввели больше одной буквы");
                continue;
            } else {
                return sc;
            }
        } while (true);
    } // ввод и проверка кол-во букв

    public static void error(int a) {
        switch (a) {
            case 1:
                playingField[2][2] = " 0";
                break;
            case 2:
                playingField[3][2] = " |";
                break;
            case 3:
                playingField[3][2] = "/|";
                break;
            case 4:
                playingField[3][2] = "/|\\";
                break;
            case 5:
                playingField[4][2] = " /";
                break;
            case 6:
                playingField[4][2] = " /˙\\";
                break;

        }
    } // считает ошибки

    public static void show() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                System.out.print(playingField[i][j]);
            }
            System.out.println();

        }
        //System.out.println(word);

    } // показывает поле

    public static void restart() {
        initial = 0;
        good = 0;
        nev = 1;
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                playingField[i][j] = playingFieldRestart[i][j];
            }
        }
        word = randomWord();

    }

    static String[][] playingFieldRestart = {{"__", "__", "_ "},
            {"| ", "  ", " |"},
            {"| ", "  ", "  "},
            {"| ", "  ", "   "},
            {"| ", " ", "    "},
            {"A ", "  ", "  "},
            {" "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"Неверно введенные буквы :", "", "", "", "", "", ""}};

}


/*class ku{
    {{"__", "__", "_ "},
        {"| ", "  ", " |"},
        {"| ", "  ", " 0" },
        {"| ", "  ", "/|\\"},
        {"| ", " ", " /˙\\"},
        {"A ", "  ", "  "},
        {" "},
        {" "," "," "," "," "," "," "," "," "," "}};
}

 */