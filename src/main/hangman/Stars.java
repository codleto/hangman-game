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
    public static List<String> wordMask = new LinkedList<>();

    public static Set<String> correctLetters = new HashSet<>();

    public static String secretWord = chooseRandomWord();

    public static Set<String> wrongLetters = new HashSet<>();

    public static int wins = 0; // счетчик выигрышей
    public static int losses = 0; // счетчик проигрышей

    // Поле
    public static String[] hangmanStages = {
            """
           +---+
           |   |
               |
               |
               |
               |
          =========
          
          """,
            """
            +---+
            |   |
            O   |
                |
                |
                |
           =========
           
           """,
            """
             +---+
             |   |
             O   |
             |   |
                 |
                 |
            =========
            
            """,
            """
             +---+
             |   |
             O   |
            /|   |
                 |
                 |
            =========
            
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
                 |
                 |
            =========
            
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
            /    |
                 |
            =========
            
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
            / \\  |
                 |
            =========
            
            """
    };
    public static Scanner scanner = new Scanner(System.in);
    // так как все методы одинаковые то встраиваем их по логике

    public static int readMenuOption() {
        while (true) {
            String s = scanner.nextLine().trim();
            if (s.matches("\\d+")) {
                return Integer.parseInt(s);
            }
            System.out.println("Ошибка! Введите число без букв и символов!");
        }
    }

    public static String readCyrillicWord() {
        while (true) {
            String s = scanner.nextLine().trim();
            if (s.matches("[А-Яа-яЁё]")) {
                return s.toLowerCase();
            }
            System.out.println("Ошибка! Введите ОДНУ букву кириллицы без цифр и символов!");
        }
    }

    public static void statistic() {
        if (wins == 0 && losses == 0) {
            System.out.println("Ты еще не сыграл, статистики нет ;)");
        } else {
            System.out.println("Твоя статистика!");
            System.out.println("Выигрыши: " + wins);
            System.out.println("Проигрыши: " + losses);
        }
    }

    public static List<String> readWordsFromFile() {
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

    public static String chooseRandomWord() {
        Random random = new Random();
        List<String> chooseWord = readWordsFromFile();
        int randomWord = random.nextInt(chooseWord.size());
        return chooseWord.get(randomWord);
    }

    public static void initMask() {
        int a = secretWord.length();
        for (int i = 0; i < a; i++) {
            wordMask.add(i, "*");
        }
    }

    public static void printScoreboard(){
        for(String x : wordMask){
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.print("Ошибки:" + "(" + wrongLetters.size() + ") ");
        for(String y : wrongLetters){
            System.out.print(y + " ");
        }
        System.out.println();
    }// это по факту панель показа слова и ошибок

    public static void openLetters(String a) {
        String letter = a.substring(0, 1).toLowerCase().trim();

        if (correctLetters.contains(letter)) {
            System.out.println("Такая буква уже есть");
        } else {
            for (int i = 0; i < secretWord.length(); i++) {
                String secretLetter = secretWord.substring(i, i + 1);
                if (letter.equalsIgnoreCase(secretLetter.trim())) {
                    wordMask.set(i, secretLetter);
                    correctLetters.add(letter);
                }
            }
        }
    }

    public static void registerWrongLetter(String a) {
        String letter = a.substring(0, 1).toLowerCase().trim();
        if(wrongLetters.contains(letter)) {
            System.out.println("Такую НЕПРАВИЛЬНУЮ букву ты уже вводил");
        } else {
            if(!secretWord.toLowerCase().contains(letter)){
                    wrongLetters.add(letter);
            }
        }
    }

    public static int wrongCount(){
        return wrongLetters.size();
    }

    public static void showBoard() {
        String a = hangmanStages[wrongCount()];
        System.out.println(a);
        printScoreboard();
    }

    public static void launchGame() {
        //Меню в начале игры
        while (true) {
            showMenu();
            int ch = readMenuOption();
            if (ch == 1) { // если выбрали 1 - начинаем новую игру
                resetGame();
                startGame();
            } else if (ch == 2) {// если 2 - смотрим статистику//
                statistic();
                System.out.println("0 - Выход");
                while (true) {
                    int exit = readMenuOption();
                    if (exit == 0) {
                        break;
                    }
                    System.out.println("Введите 0 для выхода");
                }
            } else if (ch == 3) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Ошибка! Введите 1, 2 или 3");
            }
        }
    }

    public static void showMenu() {
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        System.out.println("3 - Выход");
    }

    public static void startGame() {//--------- ---------------исправили двойное инфо табло
        initMask();
        while (true) {
            showBoard();
            String s = readCyrillicWord();
            openLetters(s);
            registerWrongLetter(s);
            if (checkWin()) {
                showBoard();
                System.out.println("ТЫ ВЫИГРАЛ!!!");
                wins++;
                break;
            }
            if (checkLoss()) {
                losses++;
                System.out.println("Вы проиграли!");
                break;
            }
        }
    }

    public static boolean checkWin() {
        return !wordMask.contains("*");
    }

    public static boolean checkLoss() {  //проверка проиграли мы или нет
        return wrongCount() == 6;
    }

    public static void resetGame() {
        wordMask.clear();
        wrongLetters.clear();
        correctLetters.clear();
        secretWord = chooseRandomWord();
    }

    public static void main(String[] args) {
        launchGame();
    }
}