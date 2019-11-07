@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val inputData = File(inputName).readText().toLowerCase()
    val result = mutableMapOf<String, Int>()

    for (subs in substrings) {
        val matchResult = Regex(subs.toLowerCase()).findAll(inputData)
        result += subs to matchResult.count()
    }

    return result
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val res = File(inputName).readText()
    var perviousCh = ' '
    var result = ""
    for (ch in res) {
        when (perviousCh) {
            'ж', 'ч', 'ш', 'щ' -> when (ch) {
                'ы' -> result += 'и'
                'Ы' -> result += 'И'
                'я' -> result += 'а'
                'Я' -> result += 'А'
                'ю' -> result += 'у'
                'Ю' -> result += 'У'
                else -> result += ch
            }
            else -> result += ch
        }
        perviousCh = ch.toLowerCase()
    }
    writer.write(result)
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val lines = mutableListOf<String>()
    var maxLen = 0

    for (line in File(inputName).readLines()) {
        lines.add(line)
        maxLen = max(maxLen, line.length)
    }

    val writer = File(outputName).printWriter() //bufferedWriter()
    for (line in lines) {
        var s = line.trim()
        val numOfSpaces = (maxLen - s.length) / 2
        for (i in 1..numOfSpaces) {
            s = " $s"
        }
        writer.println(s)
    }
    writer.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val lines = mutableListOf<String>()
    var maxLen = 0

    for (line in File(inputName).readLines()) {
        lines.add(line.trim())
        maxLen = max(maxLen, line.trim().length)
    }
    val writer = File(outputName).printWriter() //bufferedWriter()
    for (line in lines) {
        var s = ""
        var lengthCurrentLine = 0
        val words = line.split(Regex("""\s+"""))
        for (word in words) {
            lengthCurrentLine += word.length
            //lengthCurrentLine += words.size - 1
        }
        when (words.size) {
            0 -> s = ""
            1 -> s = words[0]
            else -> {
                val numOfFreeSpaces = maxLen - lengthCurrentLine
                val spacesPerWord = numOfFreeSpaces / (words.size - 1)
                var numOfLeftSpaces = numOfFreeSpaces % (words.size - 1)
                for (word in words) {
                    var wordSpaces = ""
                    if (numOfLeftSpaces >= 0) {
                        wordSpaces = " $wordSpaces"
                        numOfLeftSpaces--
                    }
                    for (i in 1..spacesPerWord) wordSpaces = " $wordSpaces"
                    s = "$s$wordSpaces$word"
                }
            }
        }
        writer.println(s.trim())
    }
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    var result = mutableMapOf<String, Int>()
    val list = File(inputName).readText().split(Regex("""[^a-zA-ZА-Яа-яЁё]+"""))
    if (list.isNotEmpty()) {
        val tempMap = mutableMapOf<String, Int>()
        for (word in list) {
            val k = word.toLowerCase()
            if (k.isNotBlank()) {
                when (k) {
                    in tempMap -> {
                        val v = 1 + tempMap[k]!!
                        tempMap[k] = v
                    }
                    else -> tempMap[k] = 1
                }
            }
        }
        when {
            tempMap.size <= 20 -> result = tempMap
            else -> {
                for (i in 1..20) {
                    val topW = tempMap.maxBy { it.value }
                    result[topW!!.key] = topW.value
                    tempMap.remove(topW.key)
                }
            }
        }
    }
    return result
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val temp = File(inputName).readText()
    var s = ""
    for (ch in temp) {
        var transCh: String
        when {
            ch.toLowerCase() in dictionary -> {
                transCh = dictionary[ch.toLowerCase()]!!.toLowerCase()
                if (ch.isUpperCase()) transCh = transCh.capitalize()
                s += transCh
            }
            ch.toUpperCase() in dictionary -> {
                transCh = dictionary[ch.toUpperCase()]!!.toLowerCase()
                transCh = transCh.toLowerCase()
                if (ch.isUpperCase()) transCh = transCh.capitalize()
                s += transCh
            }
            else -> s += ch
        }
    }
    val writer = File(outputName).bufferedWriter()
    writer.write(s)
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val inputWords = File(inputName).readLines()
    var maxLength = 0
    val outputWords = mutableListOf<String>()
    //выбираем слова с несовпадающими буквами, считаем максимальную длину
    for (word in inputWords) {
        val chrs = mutableSetOf<Char>()
        for (ch in word.toLowerCase()) {
            chrs += ch
        }
        if (chrs.size == word.length) {
            outputWords += word
            maxLength = max(maxLength, word.length)
        }
    }
    //выбираем слова смаксимальной длиной, пишем в файл
    val writer = File(outputName).bufferedWriter()
    var firstWord = true
    for (word in outputWords) {
        if (word.length == maxLength) {
            if (!firstWord) writer.write(", ")
            writer.write(word)
            firstWord = false
        }
    }
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    var inputText = File(inputName).readText()
    val writer = File(outputName).bufferedWriter()
    if (inputText.isNotEmpty()) {
        var italic = false
        var bold = false
        var strike = false

        //расставим тэги <s>
        var l1 = inputText.split(Regex("""(~~)+"""))
        inputText = ""
        for (i in 0 until l1.size) {
            inputText += l1[i]
            if (i < (l1.size - 1)) {
                if (strike) inputText += "</s>" else inputText += "<s>"
                strike = !strike
            }
        }

        //расставим тэги <b>
        l1 = inputText.split(Regex("""(\*\*)+"""))
        inputText = ""
        for (i in 0 until l1.size) {
            inputText += l1[i]
            if (i < (l1.size - 1)) {
                if (bold) inputText += "</b>" else inputText += "<b>"
                bold = !bold
            }
        }

        //расставим тэги <i>
        l1 = inputText.split(Regex("""(\*)+"""))
        inputText = ""
        for (i in 0 until l1.size) {
            inputText += l1[i]
            if (i < (l1.size - 1)) {
                if (italic) inputText += "</i>" else inputText += "<i>"
                italic = !italic
            }
        }

        //расставим тэги <p>
        l1 = inputText.split(Regex("""(\r\n\r\n)+"""))
        inputText = ""
        for (i in 0 until l1.size) {
            inputText += l1[i]
            if (i < l1.size - 1) inputText += "</p><p>"
        }

        writer.write("<html><body><p>")
        writer.write(inputText)
        writer.write("</p></body></html>")
    }
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val inputText = File(inputName).readText().split("\r\n").toMutableList()
    var lvl = -1
    var bufLists = ""
    //анализируем построчно
    for (i in 0 until inputText.size) {
        val u = inputText[i].contains(Regex("""^(\s*)\*"""))
        if (u) {
            //немаркированный список
            val currentUlLvl = inputText[i].indexOf('*') / 4
            var newText: String
            when {
                currentUlLvl > lvl -> {
                    //откроем новый уровень
                    newText = inputText[i].replace(Regex("""^(\s*)\*"""), "<ul><li>") + "</li>"
                    bufLists += "u"
                }
                currentUlLvl < lvl -> {
                    //Понизим уровень
                    if (bufLists[lvl] == 'u') {
                        newText = inputText[i].replace(Regex("""^(\s*)\*"""), "</ul><li>") + "</li>"
                    } else {
                        newText = inputText[i].replace(Regex("""^(\s*)\*"""), "</ol><li>") + "</li>"
                    }
                    bufLists = bufLists.substring(0, bufLists.lastIndex)
                }
                else -> {
                    newText = inputText[i].replace(Regex("""^(\s*)\*"""), "<li>") + "</li>"
                }
            }
            inputText[i] = newText
            lvl = currentUlLvl
        }
    }
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val lines = mutableListOf<String>()
    val result = " " + (lhv * rhv).toString()
    val n = result.length
    //множимое
    var formatStr = "%" + (n).toString() + "d"
    lines.add(String.format(formatStr, lhv))
    //множитель
    formatStr = "*%${n - 1}d"
    lines.add(String.format(formatStr, rhv))
    //подчеркивание
    formatStr = ""
    for (i in 1..n) formatStr = "-$formatStr"
    lines.add(formatStr)
    //слагаемые
    var rm = rhv
    for (i in 0 until rhv.toString().length) {
        formatStr = "%${n - i - 1}d"
        val m = rm % 10
        rm /= 10
        if (i > 0) {
            lines.add("+" + String.format(formatStr, m * lhv))
        } else {
            lines.add(" " + String.format(formatStr, m * lhv))
        }
    }
    //подчеркивание
    formatStr = ""
    for (i in 1..n) formatStr = "-$formatStr"
    lines.add(formatStr)
    lines.add(result)
    val writer = File(outputName).bufferedWriter()
    for (i in 0 until lines.size) {
        writer.write(lines[i])
        if (i < lines.size - 1) writer.newLine()
    }
    writer.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val lhvString = "$lhv"
    var quotientString = ""
    var diffString = ""
    val result = mutableListOf<String>()
    result.add(" $lhv | $rhv")

    var flag = false
    var i = 0
    for (ch in lhvString) {
        i++
        if (flag) diffString = "${diffString.toInt()}"
        diffString += ch
        var s: String
        s = diffString
        while (s.length <= i) s = " $s"
        when {
            diffString.toInt() < rhv -> {
                if (flag) {
                    result.add(s)
                    quotientString += "0"
                    var subtrahend = "-0"
                    while (subtrahend.length <= i) subtrahend = " $subtrahend"
                    var underLine = diffString.replace(Regex("""(\d)"""), "-")
                    while (underLine.length <= i) underLine = " $underLine"
                    result.add(subtrahend)
                    result.add(underLine)
                }
            }
            else -> {
                if (flag) result.add(s)
                flag = true
                val diff = diffString.toInt()
                val quotient = diff / rhv
                quotientString += quotient.toString()
                diffString = (diff % rhv).toString()
                //частичное вычитаемое
                var subtrahend = "-" + (quotient * rhv).toString()
                while (subtrahend.length <= i) subtrahend = " $subtrahend"
                val underLine = subtrahend.replace(Regex("""(\d)"""), "-")
                result.add(subtrahend)
                result.add(underLine)
            }
        }
    }

    var s: String
    if (result.size == 1) {
        s = "-0"
        while (s.length < " $lhv".length) s = " $s"
        result.add(s)
        s = s.replace(Regex("""(\d)"""), "-")
        result.add(s)
        quotientString = "0"
    }

    s = result[1]
    while (s.length <= ("$lhv | ").length) s += " "
    s += quotientString
    result[1] = s

    s = " "
    diffString = "${diffString.toInt()}"
    for (n in 1..(i - diffString.length)) s += " "
    s += diffString
    result.add(s)
    val writer = File(outputName).bufferedWriter()

    var startSpaceDelete = true
    for (l in result) {
        val ch = l[0]
        if (ch != ' ') {
            startSpaceDelete = false
            break
        }
    }

    for (l in result) {
        //удалим лишний пробел
        if (startSpaceDelete) {
            writer.write(l.replace(Regex("""^[\s]"""), ""))
        } else writer.write(l)
        writer.newLine()
    }
    writer.close()
}

