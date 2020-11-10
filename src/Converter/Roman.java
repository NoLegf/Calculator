package Converter;

public abstract class Roman {

    static final String[] ROMANNUM = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    static final int[] ARABNUM = {   1000,  900, 500,  400, 100,   90,  50,  40,   10,    9,   5,    4,   1};

    public static int toInt(String str) {
        StringBuffer romanRomanNumStr = new StringBuffer(str.toUpperCase());

        String lastNumStr = null;
        int lastNumIndex = 0;

        int finalArabNum = 0;  //число которое вернет функция
        int count = 1;
        boolean wasOneIteration = false;
        try {
            while (romanRomanNumStr.length() > 0) //пока строка не пуста
            {
                for (int i = 0; i < ROMANNUM.length; i++) {
                    if (ROMANNUM[i].length() <= romanRomanNumStr.length()) {//проверка, что длина римского цифры меньне или равна длине цисла
                        if (ROMANNUM[i].equals(romanRomanNumStr.substring(0, ROMANNUM[i].length()))) { // если есть совпадение рим. цифры в строке с числом
                            //
                            if(wasOneIteration && !(/*2*/(lastNumIndex % 4 == 0 && lastNumIndex <= i) || /*3*/(lastNumIndex + (5 - (lastNumIndex % 4)) <= i) || /*1*/((lastNumIndex - 2) % 4 == 0 && i % 4 == 0))) {
                                throw new Exception("Нарушение правил записи римского числа.");
                            }
                            //
                            if(wasOneIteration && lastNumStr.equals(ROMANNUM[i])) {
                                if (count >= 3) {
                                    //выбросить исключение о нарушение правил записи римского числа(более 3 одинаковых символов подряд)
                                    throw new Exception("Нарушение правил записи римского числа.");
                                }
                                count++;
                            }
                            else {
                                count = 1;
                            }
                            finalArabNum += ARABNUM[i];
                            romanRomanNumStr.delete(0, ROMANNUM[i].length()); //удаление считаной цифры

                            lastNumStr = ROMANNUM[i]; //атрибуты предыдущей цифры
                            lastNumIndex = i;

                            break;
                        }
                    }
                }
                wasOneIteration = true;
            }
            return finalArabNum;
        }
        catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            System.out.println("Завершение программы.");
            System.exit(-1);
        }
        return 0;
    }

    public static String toString(int num) {
        try {
            if (num == 0) {
                return "nulla";
            }
            StringBuffer str = new StringBuffer("");
            if (!(num >= 0 && num <= 100/*3999*/)) {
                throw new Exception("Число не возможно записать в римской системе счисления.");
            }
            while (num >= 100) {
                str.append(ROMANNUM[4]);
                num -= 100;
            }
            while (num >= 90) {
                str.append(ROMANNUM[5]);
                num -= 90;
            }
            while (num >= 50) {
                str.append(ROMANNUM[6]);
                num -= 50;
            }
            while (num >= 40) {
                str.append(ROMANNUM[7]);
                num -= 40;
            }
            while (num >= 10) {
                str.append(ROMANNUM[8]);
                num -= 10;
            }
            while (num >= 9) {
                str.append(ROMANNUM[9]);
                num -= 9;
            }
            while (num >= 5) {
                str.append(ROMANNUM[10]);
                num -= 5;
            }
            while (num >= 4) {
                str.append(ROMANNUM[11]);
                num -= 4;
            }
            while (num >= 1) {
                str.append(ROMANNUM[12]);
                num -= 1;
            }
            return str.toString();
        }
        catch (Exception exception) {
            System.out.println("Ошибка: " + exception.getMessage());
            System.out.println("Завершение программы.");
            System.exit(-1);
        }
        return null;
    }
}
