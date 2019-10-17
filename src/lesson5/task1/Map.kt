@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    var result = mapA
    for ((name, phone) in mapB) {
        if (name in result) {
            if (result[name] != phone) {
                result = result + (name to result[name] + ", " + phone)
            }
        } else {
            result = result + (name to phone)
        }
    }
    return result
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val result = mutableMapOf<Int, List<String>>()

    for ((student, grade) in grades) {
        if (grade in result) {
            result[grade] = result[grade]!!.plus(listOf(student))
        } else {
            result[grade] = listOf(student)
        }
    }
    return result
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((k, v) in a) {
        if (k !in b) {
            return false
        } else {
            if (b[k] != v) return false
        }
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val totalPrice = mutableMapOf<String, Double>()
    val totalOffers = mutableMapOf<String, Double>()
    val result = mutableMapOf<String, Double>()
    for ((offer, price) in stockPrices) {
        totalPrice[offer] = totalPrice.getOrDefault(offer, 0.0) + price
        totalOffers[offer] = totalOffers.getOrDefault(offer, 0.0) + 1.0
    }
    for ((offer, price) in totalPrice) {
        val total = totalOffers.getOrDefault(offer, 1.0)
        val aver = price / total
        result[offer] = aver
    }
    return result
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var result = ""
    var minPrice = 0.0
    var p: Pair<String, Double>
    for ((name, pr) in stuff) {
        p = stuff.getOrDefault(name, Pair("", 0.0))
        if (p.first == kind) {
            if (result == "") {
                minPrice = p.second
                result = name
            } else {
                if (p.second < minPrice) {
                    minPrice = p.second
                    result = name
                }
            }
        }
    }
    if (result == "") return null else return result
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val result = mutableMapOf<String, Set<String>>()

    //составим множество всех персон
    val allPersons = mutableSetOf<String>()
    for ((friend, hisFriend) in friends) {
        allPersons.add(friend)
        allPersons.addAll(hisFriend)
    }

    //множество всех персон
    for (person in allPersons) {
        val s = mutableSetOf<String>(person)
        result[person] = s
    }

    //добавим друзей из входного списка
    var newFrendsFound: Boolean
    var mySet: Set<String>
    do {
        newFrendsFound = false
        for ((currentPerson, hisFriends) in result) {
            val friendsCurrentPersonBefore = hisFriends.size
            mySet = hisFriends
            for (f in hisFriends) {
                if (friends[f] != null) {
                    mySet = mySet + friends[f]!!
                }
            }
            if (mySet.size > friendsCurrentPersonBefore) newFrendsFound = true
            result[currentPerson] = mySet
        }
    } while (newFrendsFound)

    for ((person, hisFriends) in result) {
        if (friends[person] != null) {
            var s = hisFriends
            s = s - setOf<String>(person)
            result[person] = s
        }
        if (result[person] == setOf(person)) result[person] = setOf()
    }
    return result
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    var l = listOf<String>()
    for ((k, v) in a) {
        if (b[k] == v) l = l + k
    }
    for (k in l) {
        a.remove(k)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    var result = listOf<String>()
    for (name in a) {
        if (name in b) result = result + name
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val lowCaseWord = word.toLowerCase()
    var lowCaseChars = listOf<Char>()
    for (c in chars) {
        lowCaseChars = lowCaseChars + c.toLowerCase()
    }
    for (i in 0 until lowCaseWord.length) {
        if (lowCaseWord[i] !in lowCaseChars) return false
    }
    return true
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    var removeIt: List<String> = emptyList()
    for (element in list) {
        if (element in result) {
            result[element] = result[element]!!.plus(1)
        } else {
            result[element] = 1
        }
    }
    for ((k, v) in result) {
        if (v == 1) removeIt = removeIt + k
    }
    for (k in removeIt) {
        result.remove(k)
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    if (words.isEmpty()) return false
    if (words.size == 1) return false
    val charsInCurrentWord = mutableListOf<Char>()
    //для слов списка, кроме последнего
    for (i in 0..(words.size - 2)) {
        val currentWrd = words[i]
        //создадим список букв текущего слова
        for (ch in currentWrd) {
            charsInCurrentWord.add(ch)
        }
        //проверим оставшиеся элемены списка слов
        for (j in (i + 1) until words.size) {
            var candidatWrd = words[j]
            if (candidatWrd == currentWrd) return true
            if (candidatWrd.length != currentWrd.length) { //совпадают по длине?
                continue //нет - продолжаем
            } else {     //да - вычёркиваем совпадающие буквы
                for (ch in charsInCurrentWord) {
                    val pos = candidatWrd.indexOf(ch)
                    if (pos >= 0) {
                        val l = candidatWrd.substring(0, pos)
                        val r = candidatWrd.substring(pos + 1)
                        candidatWrd = l + r
                    }
                    if (candidatWrd == "") return true //если все буквы вычеркнули
                }
            }
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    if (list.isNotEmpty()) {
        if (list.size > 1) {
            for (i in 0..(list.size - 2)) {
                for (j in (i + 1) until list.size) {
                    if (list[i] + list[j] == number) {
                        return Pair(i, j)
                    }
                }
            }
        }
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    var result = setOf<String>()
    var bestSum = 0
    var bestSet = 0
    val n = treasures.size
    //теоретическое число вариантов
    var numOfVatiants: Int
    if (n > 0) {
        numOfVatiants = 1
        for (i in 0 until n) {
            numOfVatiants *= 2
        }
        numOfVatiants--


        //просмотрим все варианты
        for (i in 1..numOfVatiants) {
            var currentmask = i.toString(2)
            while (currentmask.length < n) {
                currentmask = "0$currentmask"
            }
            var summaryHeight = 0
            var summaryPrice = 0
            for ((treasure, heightAndPrice) in treasures) {
                if (currentmask.first() == '1') {
                    summaryHeight += heightAndPrice.first
                    if (summaryHeight > capacity) {
                        //перевес
                        break
                    }
                    summaryPrice += heightAndPrice.second
                    //убрать в маске первый символ
                    currentmask = currentmask.substring(1)
                    //если маска пустая, то закончить
                    if (currentmask.isEmpty()) break
                }
            }
            //проверка кандидата
            if ((summaryHeight < capacity) && (summaryPrice > bestSum)) bestSet = i
        }
        //номер сета перевести в сет
        var currentMask = bestSet.toString(2)
        for ((treasure, heightAndPrice) in treasures) {
            if (currentMask.first() == '1') {
                result = result + treasure
            }
            currentMask = currentMask.substring(1)
            if (currentMask.isEmpty()) break
        }
    }
    return result
}
