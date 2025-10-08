package main;

/*
+ Создать метод вызова консольного отображения меню

- Пустой ввод ломает игровой процесс
- Буква "ё" не считается за кириллицу, сделать чтобы и строчные и прописные
- если вводишь цифры то говорить что нельзя вводить цифры или типа больше одной цифры
- сделать файл словаря
- повторно использованная неправильная буква недолжна увеличивать счетчик
- сделать чтобы писало в консоли что эту буква уже угадана
- сделать чтобы после выхода статистика ( создание файла куда идет запись) не сбрасывалась и добавить кнопку сброса статистики
- во всех ответвлениях где есть сканнер нужно условно продублировать защиту от ошибок ввода
- убрать коменты
-  в мейне цикл не нужен ( подумай почему)
- лучше всегда использовать while вместо do в наших примерах в основном нужен бесконечный цикл выход из него брейк,
 если вошли в какой то иф то континье
- каждый метод должен выполнять одну задачу а не несколько как в showStartMenu где и валидация ввода и прочее
- сделать маскировку слов * для прикола


+ в мейне не нужен цикл, он бесмысленный , нам просто нужно запустить метод и все

- отдельно сделать метод где пользователь вводит

 */

//
import java.util.Random;
import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
            showStartMenu();
    }

    public static void showMenu(){
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        System.out.println("3 - Выход");
    }

    public static void start() {

        while(true) {
            showBoard();
            String s = scanner();
            chekUp(s);
            checkWin();
            checkLoss();
        }
    }

    public static void showStartMenu() {
        //Меню в начале игры
        boolean stoped = true;
        char numberer;
        do {
            showMenu();
            Scanner scanner = new Scanner(System.in);
            String menuChoice = scanner.nextLine().trim();


            if(!menuChoice.matches("[123]")){
                System.out.println("Нужно ввести только 1, либо 2, либо 3");
                continue;
            }
            int num = Integer.parseInt(menuChoice);
            if (num == 1) { // если выбрали 1 - начинаем новую игру
                startNewGame();
                start();
            } else if (num == 2) { // если 2 - смотрим статистику
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
            } else if (num == 3) {
                System.out.println("Выход");
                stop = false;
            }
        } while (stop);
    }

    public static void checkWin() {// проверка выиграли мы или нет
        int length = secretWord.length();
        int openСells = 0;// 4
        for(int i = 0; i < length; i++) {      //2
            if(!"_ ".equals(board[7][i])) {
                openСells++;
            }
            if(length == openСells){
                showBoard();
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
    public static int correctLettersCount = 0;// счетчик отгаданных букв
    public static boolean stop = true;// если false то завершаем программу


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
            board[8][0] = "Неверно введенные буквы :(" + mistakesCount + ")";
            incrementMistakes(mistakesCount);
            wrongLettersCount++;
        }
    }  // проверка есть ли такая буква в слове и вывод неверных букв

    public static String scanner() {
        Scanner scanner = new Scanner(System.in);
        char letter;
        do {
            System.out.println("Введите ТОЛЬКО одну строчную букву на кириллице");
            String sc = scanner.nextLine();
            letter = sc.charAt(0);
            if (sc.length() != 1) {
                System.out.println("Вы ввели больше одной буквы");
            } else if (letter >= 'а' && letter <= 'я') {
                return sc;
            } else {
                System.out.println("Ошибка! Нужно ввести СТРОЧНУЮ букву на КИРИЛЛИЦЕ");
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