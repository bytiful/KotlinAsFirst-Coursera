@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

//import java.lang.IllegalArgumentException
import kotlin.IllegalArgumentException
import kotlin.math.max

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val daysInMonth = mapOf<String, Int>("января" to 31, "февраля" to 29, "марта" to 31, "апреля" to 30, "мая" to 31,
            "июня" to 30, "июля" to 31, "августа" to 31, "сентября" to 30, "октября" to 31,
            "ноября" to 30, "декабря" to 31)
    val numberOfMounth = mapOf<String, Int>("января" to 1, "февраля" to 2, "марта" to 3, "апреля" to 4, "мая" to 5,
            "июня" to 6, "июля" to 7, "августа" to 8, "сентября" to 9, "октября" to 10,
            "ноября" to 11, "декабря" to 12)
    var result = ""
    val parts = str.split(' ')
    if (parts.size == 3) {
        try {
            val d = parts[0].toInt()
            val m = parts[1]
            val y = parts[2].toInt()
            if (m in daysInMonth) {
                if (d <= daysInMonth[m]!!.toInt()) {
                    if (numberOfMounth[m] == 2) {
                        //проверить на високосный год
                        if (!((y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0)))) {
                            if (d > 28) {
                                return result
                            }
                        }
                    }
                    result = String.format("%02d.%02d.%d", d, numberOfMounth[m], y)
                }
            }
        } catch (e: NumberFormatException) {
            return result
        }
    }
    return result
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    var result = ""
    val parts = digital.split(".")
    if (parts.size != 3) return result
    try {
        val d = parts[0].toInt()
        val m = parts[1].toInt()
        val y = parts[2].toInt()
        val isLeapYear = ((y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0)))
        //проверка корректности ввода
        val OK: Boolean
        when (m) {
            1, 3, 5, 7, 8, 10, 12 -> OK = (d in 1..31)
            2 -> OK = isLeapYear && (d in 1..29) || (d in 1..28)
            4, 6, 9, 11 -> OK = (d in 1..30)
            else -> OK = false
        }
        if (OK) {
            result = d.toString()
            when (m) {
                1 -> result += " января "
                2 -> result += " февраля "
                3 -> result += " марта "
                4 -> result += " апреля "
                5 -> result += " мая "
                6 -> result += " июня "
                7 -> result += " июля "
                8 -> result += " августа "
                9 -> result += " сентября "
                10 -> result += " октября "
                11 -> result += " ноября "
                12 -> result += " декабря "
            }
            result += parts[2]
        }

    } catch (e: NumberFormatException) {
        return result
    }

    return result
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val validCh = setOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', ' ', '(', ')')
    var result = ""
    //проверим правильность символов
    for (ch in phone) {
        if (ch !in validCh) {
            return ""
        } else {
            when (ch) {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+' -> result += ch
            }
        }
    }

    return result
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val validCh = setOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', '-', ' ')
    var result = -1
    //проверим символы на правильность
    for (ch in jumps) {
        if (ch !in validCh) return -1
    }
    val jump = jumps.split(" ")
    for (j in jump) {
        try {
            val l = j.toInt()
            result = max(result, l)
        } catch (e: NumberFormatException) {
            continue
        }
    }
    return result
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var result = -1
    val validCh = setOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '%', '-', ' ', '+')
    for (ch in jumps) {
        if (ch !in validCh) return -1
    }
    val records = jumps.split(" ")
    for (i in 0 until records.size) {
        try {
            val height = records[i].toInt()
            if (i <= records.size - 2) {
                if ('+' in records[i + 1]) {
                    result = max(result, height)
                }
            } else {
                return -1
            }
        } catch (e: java.lang.NumberFormatException) {
            continue
        }
    }

    return result
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var result = 0
    val validNum = setOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val validSign = setOf<Char>('-', ' ', '+')

    for (ch in expression) {
        if ((ch !in validNum) && (ch !in validSign)) {
            throw IllegalArgumentException("Illegal symbol '$ch' in expression")
            //println("Illegal symbol '$ch' in expression")
        }
    }

    val ars = expression.split(" ")
    if (ars.size % 2 == 0) throw IllegalArgumentException("Wrong number of arguments")
    var isTerm = true

    for (i in 0 until ars.size) {
        try {
            if (isTerm) { //обрабатываем число
                for (ch in ars[i]) {
                    if (ch !in validNum) throw IllegalArgumentException("Wrong number")
                }
                val n = ars[i].toInt()
                if (i == 0) {
                    result = n
                } else {
                    when (ars[i - 1]) {
                        "+" -> result += n
                        "-" -> result -= n
                        else -> throw IllegalArgumentException("Illegal argument")
                    }
                }
            } else { //обрабатываем действие
                if ((ars[i] != "+") && (ars[i] != "-")) {
                    throw IllegalArgumentException("Illegal argument")
                }
            }
            isTerm = !isTerm
        } catch (e: java.lang.NumberFormatException) {
            throw IllegalArgumentException("Illegal argument")
        }
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val inString = str.toLowerCase()
    val wrds = inString.split(" ")
    var n = 0
    if (wrds.size > 1) {
        for (i in 1 until wrds.size) {
            if (wrds[i -1] == wrds[i]) {
                return n
            } else {
                n += wrds[i - 1].length + 1
            }
        }
    }
    return -1
}


/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description.isBlank()) return ""
    val descs = description.split("; ")
    var candidateToExpensive = ""
    var expensivePrice = -1.0f
    for (i in 0 until descs.size){
        val goodsInf = descs[i].split(" ")
        if (goodsInf.size != 2) return ""
        try {
            val price = goodsInf[1].toFloat()
            if (price > expensivePrice) {
                candidateToExpensive = goodsInf[0]
                expensivePrice = price
            }
        } catch (e: java.lang.NumberFormatException) {
            return ""
        }
    }
    if (candidateToExpensive.isNotBlank()) return candidateToExpensive
    return ""
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val romanNum = mapOf('I' to 1, 'V' to 5, 'X' to 10, 'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000)
    var result = 0
    var listOfArabicNum = listOf<Int>()
    //проверим корректность символов
    for (ch in roman) {
        if (romanNum[ch] == null) {
            return -1
        } else {
            //переведём каждый символ в число
            listOfArabicNum = listOfArabicNum + romanNum[ch]!!
        }
    }
    //проверим корректность чисел
    //вычислим результат
    var pevriousNum = 0
    for (num in listOfArabicNum) {
        if (pevriousNum < num) {
            result -= (2 * pevriousNum)
        }
        pevriousNum = num
        result += num
    }
    if (result == 0) result = -1
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val symbs = setOf<Char>('>', '<', '+', '-', '[', ']', ' ')
    var adr = cells / 2
    var stepCounter = 0

    //проверим на ошибочные символы и правильность скобок
    var bracketsCount = 0
    for (ch in commands) {
        if (ch !in symbs) throw IllegalArgumentException("Invalid command $ch")
        when (ch) {
            '[' -> bracketsCount++
            ']' -> bracketsCount--
        }
        if (bracketsCount < 0) throw IllegalArgumentException("No pair brackets")
    }
    if (bracketsCount != 0) throw IllegalArgumentException("No pair brackets")

    //создадим ячейки
    val mem = (1..cells).toMutableList()
    mem.fill(0)

    //выполение программы
    try {
        var charToExecute = 0
        while (stepCounter < limit) {

            //исполнение одной команды
            when (commands[charToExecute]) {
                '>' -> {
                    adr++
                    if (adr >= cells) throw IllegalStateException("Out of range ($adr)")
                    charToExecute++
                }
                '<' -> {
                    adr--
                    if (adr < 0) throw IllegalStateException("Out of range ($adr)")
                    charToExecute++
                }
                '+' -> {
                    mem[adr]++
                    charToExecute++
                }
                '-' -> {
                    mem[adr]--
                    charToExecute++
                }
                '[' -> {
                    if (mem[adr] == 0) {
                        //найдём парную закрывающую скобку
                        bracketsCount = 1
                        charToExecute++
                        while (bracketsCount !=0) {
                            if (commands[charToExecute] == '[') bracketsCount++
                            if (commands[charToExecute] == ']') bracketsCount--
                            charToExecute++
                        }
                    } else {
                        charToExecute++
                    }
                }
                ']' -> {
                    if (mem[adr] != 0) {
                        //найдём парную открывающую скобку
                        bracketsCount = 1
                        charToExecute--
                        while (bracketsCount !=0) {
                            if (commands[charToExecute] == ']') bracketsCount++
                            if (commands[charToExecute] == '[') bracketsCount--
                            charToExecute--
                        }
                        charToExecute +=2
                    } else {
                        charToExecute++
                    }
                }
                ' ' -> charToExecute++

            }
            stepCounter++
            if (charToExecute == commands.length) break
        }
    } catch (e: StringIndexOutOfBoundsException) {
        return mem
    }
    return mem
}
