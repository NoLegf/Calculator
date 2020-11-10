package Stream;

public abstract class InStream {
    static int startPosOFNextValidChar; // номер символа, с которого начнется считывание слидующего числа или действия

    static  statusOf_typeNumInExpression statusTypeInExpression;
    private enum statusOf_typeNumInExpression {STATED, NOTSTATED}

    static TypeOfChar.typeOfChar typeNumInExpression;
    //private enum typeOfChar {ARAB, ROMAN, NOTVALIDCHAR, KEYSPACE, ACTION, NULL}

    private static final char[][] validCharacters = {
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'},
            {'I', 'i', 'V', 'v', 'X', 'x'},
            {'+', '-', '*', '/'}
    };
    public static String[] read(String mStr) {
        CaseForReturnedString tempCase;
        startPosOFNextValidChar = 0;
        statusTypeInExpression = statusOf_typeNumInExpression.NOTSTATED;
        typeNumInExpression = TypeOfChar.typeOfChar.NULL;
        //для первого операнда в выражении
        tempCase = InStream.forNum(mStr, startPosOFNextValidChar);
        String num1 = tempCase.str;
        startPosOFNextValidChar = tempCase.endPosChar;
        tempCase = null;
        //для ариф действия
        tempCase = InStream.forAction(mStr, startPosOFNextValidChar);
        String action = tempCase.str;
        startPosOFNextValidChar = tempCase.endPosChar;
        tempCase = null;
        //
        //для второго операнда в выражении
        tempCase = InStream.forNum(mStr, startPosOFNextValidChar);
        String num2 = tempCase.str;
        startPosOFNextValidChar = tempCase.endPosChar;
        tempCase = null;
        //
        //
        return new String[] {num1, num2, action};
        //
        //
    }
    public static TypeOfChar.typeOfChar getTypeNumInExpression() {
        return typeNumInExpression;
    }
    private static class CaseForReturnedString { //структура для возврата значений из forNum
        public String str;
        public int endPosChar;
        CaseForReturnedString(String str, int endPosChar) {
            this.str = str;
            this.endPosChar = endPosChar;
        }
    }
    private static CaseForReturnedString forNum(String mStr, int start_PointIn_mStr){  //метод возвращающий строку с числом
        int forMakeOnlyOneIteration = 0;
        int end_mStrPointOfNum = start_PointIn_mStr;
        boolean onlySpace = true;
        boolean numIsCompete = false; //true, когда после числа в строке встречается SPACE
        boolean operandIsComplete = false; //true, когда полностью считан операнд
        boolean lastCharIsOperand = false;
        try {
            if (mStr.length() - start_PointIn_mStr < 1) {
                //выбросить исключение о пустой строке
                throw new Exception("Отсутствуют данные.");
            }
            for (int i = start_PointIn_mStr; i < mStr.length(); i++) {
                if (operandIsComplete) {
                    break;
                }
                CharAttribute tempChArr = compareWithValidCharacters(mStr.charAt(i)); //получает информацию и символе строки
                if (tempChArr.isNumber) { //если число
                    if (!(forMakeOnlyOneIteration > 0)) { // выполняется только один раз, нужно для первоначальной инициализации типа чисел в выражении
                        if (statusTypeInExpression == statusOf_typeNumInExpression.NOTSTATED) {
                            typeNumInExpression = tempChArr.type;
                            statusTypeInExpression = statusOf_typeNumInExpression.STATED;
                        }
                        forMakeOnlyOneIteration++;
                    }
                    if (numIsCompete) {
                        throw new Exception("Некорректная структура выражения. Возможно, встречено два числа подряд.");
                    }
                    if (typeNumInExpression != tempChArr.type) {//проверка на не корректное число типа ARAB_ROMAN(1I)
                        //выбросить исключение о не корректном числе.
                        throw new Exception("Некорректные данные. Встречены арабские и римские символы в выражении.");
                    }
                    end_mStrPointOfNum++;
                    onlySpace = false;
                    lastCharIsOperand = true;
                    continue;
                }
                if (onlySpace && (i == mStr.length() - 1)) { // если String состоит только из SPACE
                    //выбросить исключение о отсутствии выражения.
                    throw new Exception("Отсутствуют данные.");
                }
                if (tempChArr.type == TypeOfChar.typeOfChar.KEYSPACE) { //если встречен SPACE
                    if (lastCharIsOperand) {
                        numIsCompete = true;
                        continue;
                    }
                    end_mStrPointOfNum = ++start_PointIn_mStr;
                    continue;
                }
                if (tempChArr.type == TypeOfChar.typeOfChar.ACTION) { //если встречен символ ариф действия
                    if (end_mStrPointOfNum == start_PointIn_mStr) {
                        //выбросить исключение об отсутствие левого операнда.
                        throw new Exception("Отсутствуют данные. Возможно, пропущен левый операнд.");
                    }
                    if (lastCharIsOperand) {
                        operandIsComplete = true;
                        continue;
                    }
                }
                if (tempChArr.type == TypeOfChar.typeOfChar.NOTVALIDCHAR) { //если встречен не корректный символ
                    //выбросить исключение о не корректном символе.
                    throw new Exception("Встречен некорректный символ.");
                }
            }
            return new CaseForReturnedString((String)mStr.subSequence(start_PointIn_mStr, end_mStrPointOfNum), end_mStrPointOfNum);
        }
        catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            System.out.println("Завершение программы.");
            System.exit(-1);
        }
        return null;
    }
    private static CaseForReturnedString forAction(String mStr, int start_PointIn_mStr) {
        int end_mStrPointOfNum = start_PointIn_mStr;
        int countOfActionChar = 0;
        boolean onlySpace = true;
        boolean actionIsComplete = false;
        boolean operandIsComplete = false;
        boolean lastCharIsOperand = false;

        try{
            if (mStr.length() - start_PointIn_mStr < 1) {
                //выбросить исключение о пустой строке
                throw new Exception("Отсутствуют данные.");
            }
            for (int i = start_PointIn_mStr; i < mStr.length(); i++) {
                if (operandIsComplete) {
                    break;
                }
                CharAttribute tempChArr = compareWithValidCharacters(mStr.charAt(i)); //получает информацию и символе строки
                if (tempChArr.type == TypeOfChar.typeOfChar.ACTION) {
                    if (actionIsComplete | countOfActionChar > 0) {
                        throw new Exception("Встречено некорректное арифметическое действие.");
                    }
                    end_mStrPointOfNum++;
                    countOfActionChar++;
                    onlySpace = false;
                    lastCharIsOperand = true;
                    continue;
                }
                if (onlySpace && (i == mStr.length() - 1)) { // если String состоит только из SPACE
                    //выбросить исключение о отсутствии выражения.
                    throw new Exception("Отсутствуют данные.");
                }
                if (tempChArr.type == TypeOfChar.typeOfChar.KEYSPACE) { //если встречен SPACE
                    if (lastCharIsOperand) {
                        actionIsComplete = true;
                        continue;
                    }
                    end_mStrPointOfNum = ++start_PointIn_mStr;
                    continue;
                }
                if (tempChArr.isNumber) {
                    if (end_mStrPointOfNum == start_PointIn_mStr) {
                        //выбросить исключение о ошибки структуры выражения
                        throw new Exception("Некорректная структура выражения. Возможно, встречено два числа подряд.");
                    }
                    if (lastCharIsOperand) {
                        operandIsComplete = true;
                        continue;
                    }
                }
                if (tempChArr.type == TypeOfChar.typeOfChar.NOTVALIDCHAR) { //если встречен не корректный символ
                    //выбросить исключение о не корректном символе.
                    throw new Exception("Встречен некорректный символ.");
                }
            }
            return new CaseForReturnedString((String)mStr.subSequence(start_PointIn_mStr, end_mStrPointOfNum), end_mStrPointOfNum);
        }
        catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            System.out.println("Завершение программы.");
            System.exit(-1);
        }
        return null;
    }

    private static class CharAttribute {
        public boolean isNumber;
        public TypeOfChar.typeOfChar type;
        CharAttribute(boolean isNumber, TypeOfChar.typeOfChar type) {
            this.isNumber = isNumber;
            this.type = type;
        }
    }
    private static CharAttribute compareWithValidCharacters(char ch) {
        for (int i = 0; i < validCharacters[0].length; i++) {
            if (ch == validCharacters[0][i]) {
                return new CharAttribute(true, TypeOfChar.typeOfChar.ARAB);
            }
        }
        for (int i = 0; i < validCharacters[1].length; i++) {
            if (ch == validCharacters[1][i]) {
                return new CharAttribute(true, TypeOfChar.typeOfChar.ROMAN);
            }
        }
        for (int i = 0; i < validCharacters[2].length; i++) {
            if (ch == validCharacters[2][i]) {
                return new CharAttribute(false, TypeOfChar.typeOfChar.ACTION);
            }
        }
        if (ch == ' ') {
            return new CharAttribute(false, TypeOfChar.typeOfChar.KEYSPACE);
        }
        return new CharAttribute(false, TypeOfChar.typeOfChar.NOTVALIDCHAR);
    }
}
