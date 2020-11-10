public class Calculator {

    public static int computing(int lNum, int rNum, String action) {
        try {
            if(lNum > 10 || rNum > 10) {
                throw new Exception("Выход за диапазон допустимых значений.");
            }
            switch (action.charAt(0)) {
                case '+': {
                    return lNum + rNum;
                }
                case '-': {
                    return lNum - rNum;
                }
                case '*': {
                    return lNum * rNum;
                }
                case '/': {
                    return lNum / rNum;
                }
            }
        }
        catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            System.out.println("Завершение программы.");
            System.exit(-1);
        }
        return 0;
    }
}
