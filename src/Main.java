import java.util.Scanner;

import Stream.*;
import Converter.*;

public class Main {

    public static void main(String[] args) {
        System.out.print("Программа Калькулятор для Java Mentor.\n" +
                "Разрешенные арифметические действия [ +, -, *, / ].\n" +
                "Допустима запись числа в арабской[0123456789] и римской[IVX] системе счисления.\n" +
                "Пример входного выражения: 4 + 9 или IV + IX.\n" +
                "Разрешены только натуральные числа в диапазоне [1...10].\n" +
                "Калькулятору не важна растановка пробелов (4_+_9; 4+_9; 4_+9; 4+9).\n" +
                "Запись числа римскими цифрами допускает заглавные и строчные символы.\n\n");

        System.out.print("Введите выражение: ");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();

        String[] operands = InStream.read(expression);
        TypeOfChar.typeOfChar typeNumInExpression = InStream.getTypeNumInExpression();
        scanner.close();

        int lNum = 0;
        int rNum = 0;
        String action = operands[2];

        if (typeNumInExpression == TypeOfChar.typeOfChar.ROMAN) {
            lNum = Roman.toInt(operands[0]);
            rNum = Roman.toInt(operands[1]);
        }
        else if (typeNumInExpression == TypeOfChar.typeOfChar.ARAB) {
            lNum = Arab.toInt(operands[0]);
            rNum = Arab.toInt(operands[1]);
        }

        int result = Calculator.computing(lNum, rNum, action);

        System.out.print("Ответ: ");
        if (typeNumInExpression == TypeOfChar.typeOfChar.ROMAN) {
            System.out.println(Roman.toString(result));
        }
        else if (typeNumInExpression == TypeOfChar.typeOfChar.ARAB) {
            System.out.println(Arab.toString(result));
        }
    }
}