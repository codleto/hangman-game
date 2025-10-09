package main.hangman;

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
- лучше всегда использовать while вместо do в наших примерах в основном нужен бесконечный цикл выход из него брейк,
 если вошли в какой то иф то континье
- каждый метод должен выполнять одну задачу а не несколько как в showStartMenu где и валидация ввода и прочее
- сделать маскировку слов * для прикола


+ в мейне не нужен цикл, он бесмысленный , нам просто нужно запустить метод и все

- отдельно сделать метод где пользователь вводит

- как я понимаю список хуевых букв можно сделать через Set чтобы буквы были уникальные чтобы не множить счетчик ошибок
 */

//
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Stars {
    //СЧЕТЧИКИ
    public static int wins = 0; // счетчик выигрышей
    public static int losses = 0; // счетчик проигрышей
    public static int mistakesCount = 0; // счетчик ошибок
    public static int wrongLettersCount = 1; //счетчик неверных букв
    public static int correctLettersCount = 0;// счетчик отгаданных букв
    public static boolean stop = true;// если false то завершаем программу

    // Поле
    public static final String[] HANGMAN_STAGES = {
            """
            ========================
            |//                    |
            ||                     /\\
            ||                     \\/
            ||                   (×_×)
            ||                    /||\\
            ||                   / || \\\\
            ||                    //\\\\
            ||                   //  \\\\
            ||======================||
          __||______________________||
             💀 GAME OVER 💀
            """,
            """
                                             ========================
                                             |//                   |
                                             ||                   /
                                             ||                  /
                                             ||                 /
                                             ||      \\        /
                                             ||               /  ___(×_×)
                                             ||              /  /   /||\\
                                             ||             /  /  / || \\\\
                                             ||            |  /    //\\\\
                             ________        ||            \\/    //  \\\\
                            |    |==|=|      ||===========================||
                     ____________|==|=|______||___________________________||
                                              💀 GAME OVER 💀
            """,
            """
            ========================
            |//                    |
            ||                     /\\
            ||                     \\/
            ||                   (×_×)
            ||                    /||\\
            ||                   / || \\\\
            ||                    //\\\\
            ||                   //  \\\\
            ||======================||
          __||______________________||
             💀 GAME OVER 💀
            """,
            """
            ========================
            |//                    |
            ||                     /\\
            ||                     \\/
            ||                   (×_×)
            ||                    /||\\
            ||                   / || \\\\
            ||                    //\\\\
            ||                   //  \\\\
            ||======================||
          __||______________________||
             💀 GAME OVER 💀
            """,
            """
            ========================
            |//                    |
            ||                     /\\
            ||                     \\/
            ||                   (×_×)
            ||                    /||\\
            ||                   / || \\\\
            ||                    //\\\\
            ||                   //  \\\\
            ||======================||
          __||______________________||
             💀 GAME OVER 💀
            """,
            """
            ========================
            |//                    |
            ||                     /\\
            ||                     \\/
            ||                   (×_×)
            ||                    /||\\
            ||                   / || \\\\
            ||                    //\\\\
            ||                   //  \\\\
            ||======================||
          __||______________________||
             💀 GAME OVER 💀
            """
    };
    public static Scanner scanner = new Scanner(System.in);
    public static String[][] board = {{"__", "__", "_ "},
            {"| ", "  ", " |"},
            {"| ", "  ", "  "},
            {"| ", "  ", "   "},
            {"| ", " ", "    "},
            {"A ", "  ", "  "},
            {" "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"Неверно введенные буквы :", "", "", "", "", "", ""}};
    //Список слов
    public static String[] collectionWords = {"Кукуруза", "Болото", "Автомобиль", "Чебурек", "Жираф", "Бамбук"};

    public static String secretWord = getRandomWord();

    public static String[][] resetBoard = {{"__", "__", "_ "},  // шаблон для расчистки поля для новой игры
            {"| ", "  ", " |"},
            {"| ", "  ", "  "},
            {"| ", "  ", "   "},
            {"| ", " ", "    "},
            {"A ", "  ", "  "},
            {" "},
            {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
            {"Неверно введенные буквы :", "", "", "", "", "", ""}};

    // так как все методы одинаковые то встраиваем их по логике
    public static int scannerInt(){
        while(true){
            String s = scanner.nextLine().trim();
            if(s.matches("\\d+")){
                return Integer.parseInt(s);
            }
            System.out.println("Ошибка! Введите число без букв и символов!");
        }
    }

    public  static String scannerLine(){
        while (true) {
            String s = scanner.nextLine().trim();
            if(s.matches("[А-Яа-яЁё]")){
                return s.toLowerCase();
            }
            System.out.println("Ошибка! Введите букву кириллицы без цифр и символов!");
        }
    }

    public static void statistic(){
        if (wins == 0 && losses == 0) { //проверяем счетчики
            System.out.println("Ты еще не сыграл, статистики нет ;)");
        } else {
            System.out.println("Твоя статистика!");
            System.out.println("Выигрыши: " + wins);
            System.out.println("Проигрыши: " + losses);
        }
    }

    public static List<String> load(){
        List<String> result = new ArrayList<>();
        try (InputStream is = Stars.class.getResourceAsStream("/words.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String w = line.trim();
                if (!w.isEmpty()) result.add(w);
            }
        } catch (Exception e) {
            System.out.println("Не удалось загрузить words.txt из ресурсов: " + e.getMessage());
        }
        // На всякий случай, если слов нет — подставим минимальный набор, чтобы игра не упала
        if (result.isEmpty()) {
            result = Arrays.asList("java", "код", "виселица");
        }
        return result;
    }

    public static String rraannddoomm(){
        Random random = new Random();
        List<String> raWord = load();
        int randomWord = random.nextInt(raWord.size());
        return raWord.get(randomWord);
    }



//--------------------------------------------------------------------------
    public static void showStartMenu() {
        //Меню в начале игры
        while(true){
            showMenu();
            int ch = scannerInt();
            if (ch == 1) { // если выбрали 1 - начинаем новую игру
                startNewGame();
                start();
            }
            else if(ch == 2) {// если 2 - смотрим статистику//
                statistic();
               System.out.println("0 - Выход");
               while(true) {
                   int exit = scannerInt();
                   if (exit == 0) {
                       break;
                   }
                   System.out.println("Введите 0 для выхода");
               }
            }
            else if(ch == 3) {
                System.out.println("Выход");
                break;
            }
            else {
                System.out.println("Ошибка ввода");
            }
        }
    }

    public static void showMenu(){
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        System.out.println("3 - Выход");
    }

    public static void start() {

        while(true) {
            showBoard();
            String s = scannerLine();
            chekUp(s);
            if(checkWin()){
                System.out.println("ТЫ ВЫИГРАЛ!!!");
                wins++;
                break;
            }
            if(checkLoss()){
                losses++;
                System.out.println("Вы проиграли!");
                break;
            }
        }
    }

    public static boolean checkWin() {// проверка выиграли мы или нет
        int length = secretWord.length();
        int openСells = 0;// 4
        for(int i = 0; i < length; i++) {      //2
            if(!"_ ".equals(board[7][i])) {
                openСells++;

            }
            if(length == openСells){
                showBoard();
            return true;
            }
        }
        return false;
    }

    public static boolean checkLoss() {  //проверка проиграли мы или нет
        if (mistakesCount == 6) {
            showBoard();
            return true;
        }
        return false;
    }

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

    public static void main(String[] args) {
        System.out.println(load());
        showStartMenu();
    }

}