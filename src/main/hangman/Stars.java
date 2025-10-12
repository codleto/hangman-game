package main.hangman;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Stars {
    public static final int NEW_GAME = 1;
    public static final int STATISTIC = 2;
    public static final int EXIT_GAME = 3;
    public static final int EXIT = 0;


    public static final Scanner scanner = new Scanner(System.in);

    public static List<String> wordMask = new LinkedList<>();
    public static Set<String> correctLetters = new HashSet<>();
    public static Set<String> wrongLetters = new HashSet<>();

    public static  String secretWord = chooseRandomWord();

    public static int wins = 0;
    public static int losses = 0;

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

    public static void main(String[] args) {
        launchGame();
    }

    public static void launchGame() {
        while (true) {
            showMenu();
            int ch = readMenuOption();
            if (ch == NEW_GAME) {
                resetGame();
                startGame();
            } else if (ch == STATISTIC) {
                statistic();
                System.out.println("0 - Выход");
                while (true) {
                    int exit = readMenuOption();
                    if (exit == EXIT) {
                        break;
                    }
                    System.out.println("Введи 0 для выхода, если хочешь конечно");
                }
            } else if (ch == EXIT_GAME) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Введи 1, 2 или 3, а то никуда не попадешь (да это угроза)");
            }
        }
    }

    public static void showMenu() {
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        System.out.println("3 - Выход");
    }

    public static void startGame() {
        initMask();
        while (true) {
            showBoard();
            String s = readCyrillicWord();
            openLetters(s);
            registerWrongLetter(s);
            if (checkWin()) {
                showBoard();
                System.out.println("ТЫ ВЫИГРАЛ!!! ТЫ ПРОСТО ЧЕРТОВ ГЕНИЙ!!!");
                wins++;
                break;
            }
            if (checkLoss()) {
                losses++;
                System.out.println("Мда, а словарь то детский я загрузил (без негатива)");
                break;
            }
        }
    }

    public static boolean checkWin() {
        return !wordMask.contains("*");
    }

    public static boolean checkLoss() {
        return wrongCount() == 6;
    }

    public static void resetGame() {
        wordMask.clear();
        wrongLetters.clear();
        correctLetters.clear();
        secretWord = chooseRandomWord();
    }

    public static void statistic() {
        if (wins == 0 && losses == 0) {
            System.out.println("Ты еще не сыграл, статистики нет ;)");
        } else {
            System.out.println("Твоя статистика!");
            System.out.println("Выигрыши по твоей вине: " + wins);
            System.out.println("Проигрыши не по твоей вине: " + losses);
        }
    }

    public static void showBoard() {
        String a = hangmanStages[wrongCount()];
        System.out.println(a);
        printScoreboard();
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
    }

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
            System.out.println("Введи пожаааалуйста одну букву кириллицы без цифр и символов)");
        }
    }

    public static List<String> readWordsFromFile() {
        List<String> result = new ArrayList<>();
        try (InputStream is = Stars.class.getResourceAsStream("/words.txt")) {
            if (is == null) {
                throw new IllegalStateException("Не найден ресурс: /words.txt (положи файл в src/main/resources)");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String w = line.trim();
                    if (!w.isEmpty()) result.add(w);
                }
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

    public static void openLetters(String a) {
        String letter = a.substring(0, 1).toLowerCase().trim();

        if (correctLetters.contains(letter)) {
            System.out.println("Такая буква уже есть, попробуй другую");
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
            System.out.println("Такую НЕПРАВИЛЬНУЮ букву ты уже вводил, было бы кул если введешь правильную ;)");
        } else {
            if(!secretWord.toLowerCase().contains(letter)){
                    wrongLetters.add(letter);
            }
        }
    }

    public static int wrongCount(){
        return wrongLetters.size();
    }
}