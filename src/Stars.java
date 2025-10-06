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


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        do {

            showStartMenu();
        } while (true);
    }

    public static void start() {
        do {
            showBoard();
            String s = scanner();
            chekUp(s);
            checkWin();
            checkLoss();
        } while (true);
    }

    public static void showStartMenu() {
        //Меню в начале игры
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        Scanner scanner = new Scanner(System.in);
        int menuChoice = scanner.nextInt();
        do {
            if (menuChoice == 1) { // если выбрали 1 - начинаем новую игру
                startNewGame();
                start();
            } else if (menuChoice == 2) { // если 2 - смотрим статистику
                if (wins == 0 && losses == 0) { //проверяем счетчики
                    System.out.println("Ты еще не сыграл, статистики нет ;)");
                    System.out.println("0 - Выход");
                    Scanner scanner1 = new Scanner(System.in);
                    int exit;
                    do {
                        exit = scanner1.nextInt();
                        if (exit != 0) {
                            System.out.println("Неверный ввод");
                        }
                    } while (exit != 0);
                    showStartMenu();
                } else {
                    System.out.println("Твоя статистика!");
                    System.out.println("Выигрыши: " + wins);
                    System.out.println("Проигрыши: " + losses);
                    System.out.println("0 - Выход");
                    Scanner scanner1 = new Scanner(System.in);
                    int exit;
                    do {
                        exit = scanner1.nextInt();
                        if (exit != 0) {
                            System.out.println("Неверный ввод");
                        }
                    } while (exit != 0);
                    showStartMenu();
                }
            } else {
                System.out.println("Ошибка ввода! Введите нужную цифру (1 или 2)");
            }
        } while (true);
    }

    public static void checkWin() {// проверка выиграли мы или нет
        int we = secretWord.length();
        int open = 0;// 4
        for(int i = 0; i < we; i++) {      //2
            if(!"_ ".equals(board[7][i])) {
                open++;
            }
            if(we == open){
            System.out.println("ТЫ ВЫИГРАЛ!!!");
            wins++;
            showStartMenu();
            }
        }
    }

    public static void checkLoss() {  //проверка проиграли мы или нет
        if (mistakesCount == 6) {
            showBoard();
            System.out.println("Вы проиграли!");
            losses++;
            showStartMenu();
        }
    }

    //СЧЕТЧИКИ
    public static int wins = 0; // счетчик выигрышей
    public static int losses = 0; // счетчик проигрышей
    public static int mistakesCount = 0; // счетчик ошибок
    public static int wrongLettersCount = 1; //счетчик неверных букв
    public static int correctLettersCount = 0; // счетчик отгаданных букв


    // Поле
    static String[][] board = {{"__", "__", "_ "},
            {"| ", "  ", " |"},
            {"| ", "  ", "  "},
            {"| ", "  ", "   "},
            {"| ", " ", "    "},
            {"A ", "  ", "  "},
            {" "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"Неверно введенные буквы :", "", "", "", "", "", ""}};
    //Список слов
    static String[] collectionWords = {"Кукуруза", "Болото", "Автомобиль", "Чебурек", "Жираф", "Бамбук"};

    // рандомное слово
    static String secretWord = getRandomWord();

    public static String getRandomWord() {
        Random random = new Random();
        int randomWord = random.nextInt(collectionWords.length);
        String chooseWord = collectionWords[randomWord];

        // показать сколько букв в слове
        int lengthWord = chooseWord.length();

        // вставка количества букв в виде "_" в поле
        for (int i = 0; i < lengthWord; i++) {
            board[7][i] = "_ ";
        }
        return chooseWord;
    } // // Вытаскиваем рандомное слово из списка и делаем под него количество ячеек в поле

    public static void chekUp(String a) {
        //  сравнить буквы
        int i = 0;
        int e = 0;
        String x = a.substring(0, 1);
        while (i < secretWord.length()) {
            String y = secretWord.substring(i, i + 1);
            if (x.equalsIgnoreCase(y)) {
                board[7][i] = y + " ";
                correctLettersCount++;
                e++;
            }
            i++;
        }
        if (e == 0) {
            board[8][wrongLettersCount] = x + " ";
            mistakesCount++;
            incrementMistakes(mistakesCount);
            wrongLettersCount++;
        }
    }  // проверка есть ли такая буква в слове и вывод неверных букв

    public static String scanner() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите ТОЛЬКО одну букву");
            String sc = scanner.nextLine();
            if (sc.length() != 1) {
                System.out.println("Вы ввели больше одной буквы");
            } else {
                return sc;
            }
        } while (true);
    } // ввод и проверка кол-во букв

    public static void incrementMistakes(int a) {
        switch (a) {
            case 1:
                board[2][2] = " 0";
                break;
            case 2:
                board[3][2] = " |";
                break;
            case 3:
                board[3][2] = "/|";
                break;
            case 4:
                board[3][2] = "/|\\";
                break;
            case 5:
                board[4][2] = " /";
                break;
            case 6:
                board[4][2] = " /˙\\";
                break;
        }
    } // считает ошибки

    public static void showBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    } // показывает поле

    public static void startNewGame() {  //при новой игре нужно обнулить счетчики (кроме статистики) и измененное поле
        mistakesCount = 0;
        correctLettersCount = 0;
        wrongLettersCount = 1;
        for (int i = 0; i < board.length; i++) {  //очищаем поле
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = resetBoard[i][j];
            }
        }
        secretWord = getRandomWord(); //генерируем новое слово
    }

    static String[][] resetBoard = {{"__", "__", "_ "},  // шаблон для расчистки поля для новой игры
            {"| ", "  ", " |"},
            {"| ", "  ", "  "},
            {"| ", "  ", "   "},
            {"| ", " ", "    "},
            {"A ", "  ", "  "},
            {" "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"Неверно введенные буквы :", "", "", "", "", "", ""}};
}