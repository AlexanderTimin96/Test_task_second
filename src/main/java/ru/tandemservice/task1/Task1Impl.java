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


    // Индекс колонки начинается с 0

    //Приоритет значений:
    // 1. строка равно null
    // 2. Строка, которая не имеет колонку с данным индексом
    // 3. Колонка с индексом имеет значение null
    // 4. Колонка с индексом имеет пустое значение
    // 5. Колонки с индексом начинается с подстроки которая является числом числа:
    // - вначале идет меньшее число
    // 6. Обычное сравнение строк посимвольно, через метод compareTo
    @Override
    public void sort(final List<String[]> rows, final int columnIndex) {
        //проверка, что лист не null
        if (rows == null) throw new RuntimeException();
        // Реализовываем компоратор
        rows.sort((r1, r2) -> {

            //сначала сортировка по самим строкам
            if (r1 == null && r2 == null) return 0;
            if (r1 == null) return -1;
            if (r2 == null) return 1;

            //сортировка строк с которых сортирующей колонки совсем нет
            if (r1.length < (columnIndex + 1) && r2.length < (columnIndex + 1)) return 0;
            if (r1.length < (columnIndex + 1)) return -1;
            if (r2.length < (columnIndex + 1)) return 1;

            //Сортировка строчек с null значениями
            if (r1[columnIndex] == null && r2[columnIndex] == null) return 0;
            if (r1[columnIndex] == null) return -1;
            if (r2[columnIndex] == null) return 1;

            // Создаем переменные чтобы не обращаться в массив каждый раз
            String str1 = r1[columnIndex];
            String str2 = r2[columnIndex];

            //Проверка на полное совпадение колонки
            if (str1.equals(str2)) return 0;

            //Сортировка строчек с пустыми значениями
            if (str1.equals("")) return -1;
            if (str2.equals("")) return 1;

            //Выделяем первые непрерывные максимальные фрагменты строки
            String substring1 = str1.split(" ")[0];
            String substring2 = str2.split(" ")[0];

            try {
                //Парсим строчки в инты
                int numb1 = Integer.parseInt(substring1);
                int numb2 = Integer.parseInt(substring2);
                //Сравниваем инты
                return Integer.compare(numb1, numb2);

            } catch (NumberFormatException e) {
                //Если дошли до этого момента, значит это обычные строки
                return str1.compareTo(str2);
            }
        });
    }
}
