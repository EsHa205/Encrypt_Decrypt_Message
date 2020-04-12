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
        int key = 0;
        boolean dataArgExist = false;
        boolean inArgExist = false;
        boolean outArgExist = false;
        String pathFileIn = "";
        String pathFileOut = "";


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
                    dataArgExist = true;
                    break;
                case "-in":
                    pathFileIn = args[i + 1];
                    inArgExist = true;
                    break;
                case "-out":
                    pathFileOut = args[i + 1];
                    outArgExist = true;
                    break;
            }
        }

        // default mode
        if (operation.isEmpty()) {
            operation = "enc";
        }

        // processing
        if (operation.equals("enc") && !inArgExist) {
            if (outArgExist) {
                encryptToFile(message, pathFileOut, key);
            } else {
                encrypt(message, key);
            }
        } else if (operation.equals("enc") && inArgExist) {
            if (outArgExist && dataArgExist) {
                encryptToFile(message, pathFileOut, key);
            } else if (outArgExist && !dataArgExist) {
                encryptFromFileToFile(pathFileIn, pathFileOut, key);
            } else if (!outArgExist && dataArgExist) {
                encrypt(message, key);
            } else if (!outArgExist && !dataArgExist) {
                encryptFromFile(pathFileIn, key);
            }
        } else if (operation.equals("dec") && !inArgExist) {
            if (outArgExist) {
                decryptToFile(message, pathFileOut, key);
            } else {
                decrypt(message, key);
            }
        } else if (operation.equals("dec") && inArgExist) {
            if (outArgExist && dataArgExist) {
                decryptToFile(message, pathFileOut, key);
            } else if (outArgExist && !dataArgExist) {
                decryptFromFileToFile(pathFileIn, pathFileOut, key);
            } else if (!outArgExist && dataArgExist) {
                decrypt(message, key);
            } else if (!outArgExist && !dataArgExist) {
                decryptFromFile(pathFileIn, key);
            }
        }
    }

    private static void encrypt(@NotNull String message, int key) {
        key %= 26;
        char[] charArray = message.toCharArray();
        String encryptedMessage = "";
        for (char singleChar : charArray) {
            char newChar = singleChar;
            newChar += key;
            encryptedMessage += newChar;
        }
        System.out.println(encryptedMessage);
    }

    private static void encryptFromFile(@NotNull String pathFileIn, int key) {
        key %= 26;
        File inFile = new File(pathFileIn);
        try (Scanner scanner = new Scanner(inFile)) {
            String message = scanner.nextLine();
            char[] charArray = message.toCharArray();
            String encryptedMessage = "";
            for (char singleChar : charArray) {
                char newChar = singleChar;
                newChar += key;
                encryptedMessage += newChar;
            }
            System.out.println(encryptedMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

    private static void encryptToFile(@NotNull String message, @NotNull String pathFileOut, int key) {
        key %= 26;
        File outFile = new File(pathFileOut);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            char[] charArray = message.toCharArray();
            String encryptedMessage = "";
            for (char singleChar : charArray) {
                char newChar = singleChar;
                newChar += key;
                encryptedMessage += newChar;
            }
            printWriter.print(encryptedMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

    private static void encryptFromFileToFile(@NotNull String pathFileIn, @NotNull String pathFileOut, int key) {
        key %= 26;
        File inFile = new File(pathFileIn);
        String encryptedMessage = "";
        try (Scanner scanner = new Scanner(inFile)) {
            String message = scanner.nextLine();
            char[] charArray = message.toCharArray();
            for (char singleChar : charArray) {
                char newChar = singleChar;
                newChar += key;
                encryptedMessage += newChar;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }

        File outFile = new File(pathFileOut);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.print(encryptedMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

    private static void decrypt(@NotNull String message, int key) {
        key %= 26;
        char[] charArray = message.toCharArray();
        String decryptedMessage = "";
        for (char singleChar : charArray) {
            char newChar = singleChar;
            newChar -= key;
            decryptedMessage += newChar;
        }
        System.out.println(decryptedMessage);
    }

    private static void decryptFromFile(@NotNull String pathFileIn, int key) {
        key %= 26;
        File inFile = new File(pathFileIn);
        try (Scanner scanner = new Scanner(inFile)) {
            String message = scanner.nextLine();
            char[] charArray = message.toCharArray();
            String decryptedMessage = "";
            for (char singleChar : charArray) {
                char newChar = singleChar;
                newChar -= key;
                decryptedMessage += newChar;
            }
            System.out.println(decryptedMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

    private static void decryptToFile(@NotNull String message, @NotNull String pathFileOut, int key) {
        key %= 26;
        File outFile = new File(pathFileOut);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            char[] charArray = message.toCharArray();
            String decryptedMessage = "";
            for (char singleChar : charArray) {
                char newChar = singleChar;
                newChar -= key;
                decryptedMessage += newChar;
            }
            printWriter.print(decryptedMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

    private static void decryptFromFileToFile(@NotNull String pathFileIn, @NotNull String pathFileOut, int key) {
        key %= 26;
        File inFile = new File(pathFileIn);
        String decryptedMessage = "";
        try (Scanner scanner = new Scanner(inFile)) {
            String message = scanner.nextLine();
            char[] charArray = message.toCharArray();
            for (char singleChar : charArray) {
                char newChar = singleChar;
                newChar -= key;
                decryptedMessage += newChar;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }

        File outFile = new File(pathFileOut);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            printWriter.print(decryptedMessage);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Datei wurde nicht gefunden!");
        }
    }

    /*private static void encryptOldVer (@NotNull String message, int key) {
        key %= 26;
        char[] charArray = message.toCharArray();
        String encryptedMessage = "";

        for (char singleChar : charArray) {
            if (singleChar >= 65 && singleChar <= 90
                    || singleChar >= 97 && singleChar <= 122) {
                char newChar = singleChar;
                if (Character.isLowerCase(singleChar)) {
                    newChar += key;
                    if (!Character.isLowerCase(newChar)) {
                        newChar -= 26;
                    }
                } else if (Character.isUpperCase(singleChar)) {
                    newChar += key;
                    if (!Character.isUpperCase(newChar)) {
                        newChar -= 26;
                    }
                }
                encryptedMessage += newChar;
            } else {
                encryptedMessage += singleChar;
            }
        }
        System.out.println(encryptedMessage);
    }*/
}
