package ru.tandemservice.task1;

import java.util.List;

/**
 * <h1>Задание №1</h1>
 * Реализуйте интерфейс {@link IStringRowsListSorter}.
 *
 * <p>Мы будем обращать внимание в первую очередь на структуру кода и владение стандартными средствами java.</p>
 */
public class Task1Impl implements IStringRowsListSorter {

    // ваша реализация должна работать, как singleton. даже при использовании из нескольких потоков.

    //Приватное поле INSTANCE (с некэшуруемым значением)
    private static volatile IStringRowsListSorter INSTANCE;

    //Приватный конструктор
    private Task1Impl() {
    }

    // Реализация многопоточного синглтона с «Double-Checked Locking»
    public static IStringRowsListSorter getINSTANCE() {
        if (INSTANCE == null)
            synchronized (Task1Impl.class) {
                if (INSTANCE == null)
                    INSTANCE = new Task1Impl();
            }
        return INSTANCE;
    }

    @Override
    public void sort(final List<String[]> rows, final int columnIndex) {

        //Проверяем корректность индекса
        for (String[] arr : rows) {
            if (arr.length - 1 > columnIndex) {
                throw new RuntimeException("Введенный индекс превышает количество строк");
            }
        }

        rows.sort((o1, o2) -> {
            //Сортировка строчек с null значениями
            if (o1[columnIndex] == null & o2[columnIndex] == null) return 0;
            if (o1[columnIndex] == null) return 1;
            if (o2[columnIndex] == null) return 1;

            // Создаем переменные чтобы не обращаться в массив каждый раз
            String str1 = o1[columnIndex];
            String str2 = o2[columnIndex];

            //Проверка на полное совпадение строчек
            if (str1.equals(str2)) return 0;

            //Сортировка строчек с пустыми значениями
            if (str1.equals("")) return 1;
            if (str2.equals("")) return 1;

            //Находим индекс последнего вхождение числа в каждую строку
            int numbIndex1 = findLastIndexInSubstringWhichIsNumber(str1);
            int numbIndex2 = findLastIndexInSubstringWhichIsNumber(str2);

            //Выбираем минимальный индекс из двух
            int minIndex = Math.min(numbIndex1, numbIndex2);

            //Находим числа из строк с одинаковым разрядом
            int numb1 = findFirstNumeric(str1, minIndex);
            int numb2 = findFirstNumeric(str2, minIndex);

            //Если числа не равны, то сравниваем их
            if (numb1 != numb2) {
                return Integer.compare(numb1, numb2);
            }

            return str1.compareTo(str2);
        });
    }

    private int findLastIndexInSubstringWhichIsNumber(String str) {

        String[] path = str.split(" ");

        int count = 0;
        for (int i = 0; i < path[0].length(); i++) {
            if (!Character.isDigit(path[0].charAt(i))) {
                break;
            }
            count++;
        }
        return count;
    }

    private int findFirstNumeric(String str, int index) {

        String[] path = str.split(" ");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < index; i++) {
            if (!Character.isDigit(path[0].charAt(i))) {
                break;
            }
            stringBuilder.append(path[0].charAt(i));
        }
        return Integer.parseInt(stringBuilder.toString());
    }
}
