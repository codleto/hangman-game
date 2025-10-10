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
    public static Set<String> ddd = new HashSet<>();

    public static int wins = 0; // счетчик выигрышей
    public static int losses = 0; // счетчик проигрышей
    public static int correctLettersCount = 0;// счетчик отгаданных букв

    // Поле
    public static String[] HANGMAN_STAGES = {
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
    public static int scannerInt() {
        while (true) {
            String s = scanner.nextLine().trim();
            if (s.matches("\\d+")) {
                return Integer.parseInt(s);
            }
            System.out.println("Ошибка! Введите число без букв и символов!");
        }
    }

    public static String scannerLine() {
        while (true) {
            String s = scanner.nextLine().trim();
            if (s.matches("[А-Яа-яЁё]")) {
                return s.toLowerCase();
            }
            System.out.println("Ошибка! Введите ОДНУ букву кириллицы без цифр и символов!");
        }
    }

    public static void statistic() {
        if (wins == 0 && losses == 0) { //проверяем счетчики
            System.out.println("Ты еще не сыграл, статистики нет ;)");
        } else {
            System.out.println("Твоя статистика!");
            System.out.println("Выигрыши: " + wins);
            System.out.println("Проигрыши: " + losses);
        }
    }

    public static List<String> load() {
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

    public static String rraannddoomm() {
        Random random = new Random();
        List<String> raWord = load();
        int randomWord = random.nextInt(raWord.size());
        return raWord.get(randomWord);
    }

    // метод вставляет звезды под количество букв
    public static void vstavkazvezd() {
        int a = SECRET.length();
        for (int i = 0; i < a; i++) {
            asd.add(i, "* ");
        }
    }

    public static void proverkaASD(){
        for(String x : asd){
            System.out.print(x);
        }
        System.out.println();
        for(String y : ddd){
            System.out.print(y);
        }
        System.out.println();
    }

    // вставка буквы если правильная
    public static void vstavkabukv(String a) {
        String bookva = a.substring(0, 1).toLowerCase().trim();

                if(ignorRegPravSlov.contains(bookva)){
                    System.out.println("Такая буква уже есть");
                } else {
                    for (int i = 0; i < SECRET.length(); i++) {
                        String bukvazagadanogoslova = SECRET.substring(i, i + 1);
                        if (bookva.equalsIgnoreCase(bukvazagadanogoslova.trim())) {
                            asd.set(i, bukvazagadanogoslova + " ");
                            ignorRegPravSlov.add(bookva);
                        }
                    }
                }

    }

    public static void esliNePravilno(String a) {
        String bookva = a.substring(0, 1).toLowerCase().trim();
        if(ddd.contains(bookva)) {
            System.out.println("Такую НЕПРАВИЛЬНУЮ букву ты уже вводил");
        } else {
            for (int i = 0; i < SECRET.length(); i++) {
                String bukvazagadanogoslova = SECRET.substring(i, i + 1);
                if (!bookva.equalsIgnoreCase(bukvazagadanogoslova.trim())) {
                    ddd.add(bookva);
                }
            }
        }
    }

    public static void sshowBboard() {

        String a = HANGMAN_STAGES[2];
        System.out.println(a);
        proverkaASD();
        //неверно введеные буквы и счетчик
        // поле под буквы в виде звездочек
        //сообщение что уже вводил эту букву
    }

    // сюда закинуть отгаданные буквы и потом через этот массив проверять и отсеивать повторный ввод
    public static void ooooiit(String a) {
        Set<String> otgadanieSlova = new HashSet<>();
       otgadanieSlova.add(a);
        for(String x : otgadanieSlova){
        }
    }

    // тут закидывать неотгаданные буквы так убрается повторность введенной буквы
    public static void Neotgadanie() {
        Set<String> Neotgadanie = new HashSet<>();
    }

    // метод который подставлет звезды под размер слова и угаданных букв

    // метод который проверяет

    // наше поле букв
    public static List<String> asd = new LinkedList<>();

    public static Set<String> ignorRegPravSlov = new HashSet<>();

    public static String SECRET = rraannddoomm();

    //--------------------------------------------------------------------------
    public static void showStartMenu() {
        //Меню в начале игры
        while (true) {
            showMenu();
            int ch = scannerInt();
            if (ch == 1) { // если выбрали 1 - начинаем новую игру
                restartNewGame();
                start();
            } else if (ch == 2) {// если 2 - смотрим статистику//
                statistic();
                System.out.println("0 - Выход");
                while (true) {
                    int exit = scannerInt();
                    if (exit == 0) {
                        break;
                    }
                    System.out.println("Введите 0 для выхода");
                }
            } else if (ch == 3) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Ошибка ввода");
            }
        }
    }

    public static void showMenu() {
        System.out.println("1 - Новая игра");
        System.out.println("2 - Статистика");
        System.out.println("3 - Выход");
    }

    public static void start() {
        vstavkazvezd();
        while (true) {
            proverkaASD();
            sshowBboard();
            String s = scannerLine();
            vstavkabukv(s);
            //esliNePravilno(s); // тут где то ошибка
            //chekUp(s);
            if (checkWin()) {
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
        if(correctLettersCount == SECRET.length()){
            return true;
        }
        return false;
    }

    public static boolean checkLoss() {  //проверка проиграли мы или нет
        if (6 == 6) {
            return true;
        }
        return false;
    }

    public static void restartNewGame() {  //при новой игре нужно обнулить счетчики (кроме статистики) и измененное поле

        correctLettersCount = 0;

    }

    public static void main(String[] args) {
        showStartMenu();
    }
}