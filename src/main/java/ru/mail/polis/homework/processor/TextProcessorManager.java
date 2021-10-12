package ru.mail.polis.homework.processor;

/**
 * Задание: написать систему обработки текста.
 * Надо реализовать 4 обработчика текста
 * 1) Схлопывание всех подстрок соответствующих регулярному выражению \s+ в один пробел
 * 2) Замена первой подстроки, соответствующей заданному регулярному выражению на заданную подстроку
 * 3) Обрезание текста до заданной длины
 * 4) Замена всех символов на заглавные
 * Для реализации обработчиков рекомендуется использовать уже имеющиеся методы класса String
 * <p>
 * Класс TextProcessorManager содержит последовательность обработчиков. Обработка текста состоит в последовательном
 * применении всех обработчиков к тексту. Если вместо текста на вход подается null, то всегда возвращается null.
 * <p>
 * Каждый обработчик относится к некоторой стадии обработки: препроцессинг, процессинг, постпроцессинг
 * (перечислить их в ProcessingStage). Обработчик с более ранней стадии не может применяться после обработчика
 * с более поздней стадии (порядок стадий дан выше).
 * Обработчики с одной стадии могут применяться друг после друга, без ограничений.
 * Если последовательность обработчиков, не удовлетворяет этому свойству, то TextProcessorManager не должен ничего
 * делать с исходным текстом при обработке.
 * <p>
 * Чтобы не усложнять логику TextProcessorManager, предлагается инициализировать экземпляр TextProcessorManager во
 * вспомогательном статическом методе construct. Здесь будет проверяться корректность заданной последовательности,
 * и если она некорректна, то вернем заглушку, которая ничего не делает. Иначе создадим экземпляр с помощью приватного
 * конструктора. Таким образом мы гарантируем, что экземпляр класса всегда проинициализирован с корректной
 * последовательностью обработчиков. Этот шаблон уже частично реализован, достаточно только реализовать
 * проверку на корректность (статический метод isValidSequence) и экземпляр заглушки (статическое поле EMPTY).
 * Обратите внимание, что метод isValidSequence не имеет модификатора доступа, и таким образом, он доступен в коде
 * юнит-тестов (т.к. они относятся к тому же пакету).
 *
 * Базовая обвязка класса 2 балла + 3 балла за валидацию. Итого 5
 * Суммарно, по всему заданию 15 баллов
 */
public class TextProcessorManager {

    private static final TextProcessorManager EMPTY = new TextProcessorManager(new TextProcessor[0]);
    private final TextProcessor[] processors;

    private TextProcessorManager(TextProcessor[] processors) {
        this.processors = processors;
    }

    public String processText(String text) {
        if (text == null) {
            return null;
        }
        String newText = text;
        for (TextProcessor processor: this.processors) {
            newText = processor.processText(newText);
        }
        return newText;
    }

    public static TextProcessorManager construct(TextProcessor[] processors) {
        if (!isValidSequence(processors)) {
            return EMPTY;
        }
        return new TextProcessorManager(processors);
    }

    // visible for tests
    static boolean isValidSequence(TextProcessor[] processors) {
        for (int i = 0; i < processors.length - 1; i++) {
            if (processors[i].getStage().getPriority() > processors[i + 1].getStage().getPriority()) {
                return false;
            }
        }
        return true;
    }
}