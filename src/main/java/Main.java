import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    private static int textsCount = 10_000;
    private static int textLength = 100_000;

    public static ArrayBlockingQueue textWithMaxA = new ArrayBlockingQueue(100);
    public static ArrayBlockingQueue textWithMaxB = new ArrayBlockingQueue(100);
    public static ArrayBlockingQueue textWithMaxC = new ArrayBlockingQueue(100);

    public static void main(String[] args) {

        Thread textsGenerate = new Thread(() -> {

            for (int i = 0; i < textsCount; i++) {
                String text = generateText("abc", textLength);
                try {
                    textWithMaxA.put(text);
                    textWithMaxB.put(text);
                    textWithMaxC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        textsGenerate.start();


        Thread parseAThread = new Thread(() -> {

            char letter = 'a';
            int maxCount = 0;
            String maxString = null;

            for (int i = 0; i < textsCount; i++) {
                try {
                    String text = (String) textWithMaxA.take();
                    if (getCountOfLetterInString(letter, text) > maxCount) {
                        maxString = text;
                        maxCount = text.length();
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Max with a: " + maxString);
        });
        parseAThread.start();



        Thread parseBThread = new Thread(() -> {

            char letter = 'b';
            int maxCount = 0;
            String maxString = null;

            for (int i = 0; i < textsCount; i++) {
                try {
                    String text = (String) textWithMaxB.take();
                    if (getCountOfLetterInString(letter, text) > maxCount) {
                        maxString = text;
                        maxCount = text.length();
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Max with b: " + maxString);
        });
        parseBThread.start();


        Thread parseCThread = new Thread(() -> {

            char letter = 'c';
            int maxCount = 0;
            String maxString = null;

            for (int i = 0; i < textsCount; i++) {
                try {
                    String text = (String) textWithMaxC.take();
                    if (getCountOfLetterInString(letter, text) > maxCount) {
                        maxString = text;
                        maxCount = text.length();
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Max with c: " + maxString);
        });
        parseCThread.start();

        //System.out.println(texts[8]);

    }


    public static int getCountOfLetterInString(char letter, String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == letter) {
                count++;
            }
        }
        return count;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}
