package encryptdecrypt;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) {

        String operation = "";
        String message = "";
        String pathFileIn = "";
        String pathFileOut = "";
        String resultMessage = "";
        String algorithm = "";
        int key = 0;
        Algorithm alg;


        // args check
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    operation = args[i + 1];
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    message = args[i + 1];
                    break;
                case "-in":
                    pathFileIn = args[i + 1];
                    break;
                case "-out":
                    pathFileOut = args[i + 1];
                    break;
                case "-alg":
                    algorithm = args[i + 1];
                    break;
            }
        }

        if (!pathFileIn.isEmpty() && message.isEmpty()) {
            message = readFile(pathFileIn);
        }

        if ("shift".equals(algorithm)) {
            alg = new Shift();
        } else if ("unicode".equals(algorithm)) {
            alg = new Unicode();
        } else {
            alg = new Shift();
        }

        if (operation.isEmpty() || operation.equals("enc")) {
            resultMessage = alg.encrypt(message, key);
        } else if (operation.equals("dec")) {
            resultMessage = alg.decrypt(message, key);
        } else {
            System.out.println("No valid mode selected!");
        }

        if (!pathFileOut.isEmpty()) {
            writeFile(pathFileOut, resultMessage);
        } else {
            System.out.println(resultMessage);
        }
    }

    private static String readFile(String path) {
        File inFile = new File(path);
        String message = "";
        try (Scanner scanner = new Scanner(inFile)) {
            message = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
        return message;
    }

    private static void writeFile(String path, String message) {
        File outFile = new File(path);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.print(message);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

}

abstract class Algorithm {

    abstract String encrypt(@NotNull String message, int key);
    abstract String decrypt(@NotNull String message, int key);
}

class Shift extends Algorithm {

    protected String encrypt(@NotNull String message, int key) {
        key %= 26;
        char[] charArray = message.toCharArray();
        String encryptedMessage = "";
        for (char singleChar : charArray) {
            char newChar = singleChar;

            if (singleChar >= 65 && singleChar <= 90) {
                newChar += key;
                if (newChar < 65) {
                    newChar += 26;
                } else if (newChar > 90) {
                    newChar -= 26;
                }
            } else if (singleChar >= 97 && singleChar <= 122) {
                newChar += key;
                if (newChar < 97) {
                    newChar += 26;
                } else if (newChar > 122) {
                    newChar -= 26;
                }
            }

            encryptedMessage += newChar;
        }
        return encryptedMessage;
    }

    protected String decrypt(@NotNull String message, int key) {
        key %= 26;
        char[] charArray = message.toCharArray();
        String decryptedMessage = "";
        for (char singleChar : charArray) {
            char newChar = singleChar;

            if (singleChar >= 65 && singleChar <= 90) {
                newChar -= key;
                if (newChar < 65) {
                    newChar += 26;
                } else if (newChar > 90) {
                    newChar -= 26;
                }
            } else if (singleChar >= 97 && singleChar <= 122) {
                newChar -= key;
                if (newChar < 97) {
                    newChar += 26;
                } else if (newChar > 122) {
                    newChar -= 26;
                }
            }

            decryptedMessage += newChar;
        }
        return decryptedMessage;
    }
}

class Unicode extends Algorithm {

    protected String encrypt(@NotNull String message, int key) {
        //key %= 26;
        char[] charArray = message.toCharArray();
        String encryptedMessage = "";
        for (char singleChar : charArray) {
            char newChar = singleChar;
            newChar += key;
            encryptedMessage += newChar;
        }
        return encryptedMessage;
    }

    protected String decrypt(@NotNull String message, int key) {
        //key %= 26;
        char[] charArray = message.toCharArray();
        String decryptedMessage = "";
        for (char singleChar : charArray) {
            char newChar = singleChar;
            newChar -= key;
            decryptedMessage += newChar;
        }
        return decryptedMessage;
    }
}