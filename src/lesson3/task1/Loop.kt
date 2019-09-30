@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlinx.html.currentTimeMillis
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int =
        when {
            n < 10 -> 1
            else -> digitNumber(n / 10) + 1
        }


/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int =
        when {
            n == 1 -> 1
            n == 2 -> 1
            else -> fib(n - 1) + fib(n - 2)
        }

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k = m
    if (n > m) {
        k = n
    } //выбрали наибольшиее число k = max(m, n)
    for (i in k..(m * n)) { //поиск перебором от наибольшего из заданных, до произведения заданных чисел
        if ((i % m == 0) && (i % n == 0)) {
            k = i
            break //если нашли, то прекращаем поиск
        }
    }
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var result = n
    for (i in 2..n / 2) {
        if (n % i == 0) {
            result = i
            break
        }
    }
    return result
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    //для простых чисел
    var result = 1
    //для непостых чётных чисел
    if (n % 2 == 0) {
        result = n / 2
    } else {    //для непростых нечётных чисел
        for (i in 3..sqrt(n.toDouble()).toInt() step 2) {
            if (n % i == 0) {
                result = n / i
                break
            }
        }
    }
    return result
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    //найдем наибольший общий делитель (НОД) по алгоритму Евклида
    var a = max(m, n)
    var b = min(m, n)
    var c: Int
    do {
        c = a % b
        a = b
        b = c
    } while (b > 1)
    //для взаимно простых чисел НОД = 1
    return b == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val m_sqrt = sqrt(m.toFloat()).toInt()
    val n_sqrt = sqrt(n.toFloat()).toInt()
    var result = false
    for (k in m_sqrt..n_sqrt) {
        if (k * k in m..n) {
            result = true
            break
        }
    }
    return result
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var result = 0
    var xNext = x
    while (xNext > 1) {
        when {
            xNext % 2 == 0 -> xNext /= 2
            else -> xNext = 3 * xNext + 1
        }
        result++
    }
    return result
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var oldSin = 0.0 //Значение на предыдущем шаге
    var newSin: Double //Значение на текущем шаге
    var s: Int //знак
    var f: Double //факториал
    var m: Double //множитель
    var n = 1 //номер члена ряда
    var xx = x
    if (xx < 0.0) {
        xx *= -1 //Будем вести расчёт для положительных значений, т.к. sin(-x)=-sin(x)
    }
    xx %= (2 * PI) //привидём к диапазону 0..2*PI
    do {
        if (n % 2 == 0) s = -1 else s = 1
        f = factorial(2 * n - 1)

        //возведение в степень 2n-1
        m = 1.0
        for (i in 1..(2 * n - 1)) {
            m *= xx
        }

        m /= f
        newSin = oldSin + s * m
        oldSin = newSin
        n++
    } while (m > eps)
    //учтём знак аргумента x
    if (x < 0.0) {
        newSin *= -1.0
    }
    return newSin
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var oldCos = 1.0
    var newCos: Double
    var s: Int
    var f: Double
    var m: Double
    var n = 2
    val xx = x % (2 * PI)
    do {
        if (n % 2 == 0) s = -1 else s = 1
        f = factorial(2 * n - 2)
        //возведение в степень 2n-2
        m = 1.0
        for (i in 1..(2 * n - 2)) {
            m *= xx
        }
        m /= f
        newCos = oldCos + s * m
        oldCos = newCos
        n++
    } while (m > eps)
    return newCos
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var nn = n
    var nRevert = nn % 10
    while (nn > 9) {
        nRevert *= 10
        nn /= 10
        nRevert += nn % 10
    }
    return nRevert
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = (n == revert(n)) //используем функцию из предыдущей задачи

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var nn = n
    val lastDigit = n % 10
    var result = false
    while (nn > 9) {
        nn /= 10
        if (nn % 10 != lastDigit) {
            result = true
            break
        }
    }
    return result
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var sqrLen: Int
    var seqLen = 0
    var curSqr = 0
    var m = 0

    //последовательность длиной не меньше n
    while (seqLen < n) {

        //новый элемент последовательности
        m++
        curSqr = m * m

        //число цифр в текущем квадрате
        sqrLen = 0
        while (curSqr > 9) {
            sqrLen++
            curSqr /= 10
        }
        sqrLen++

        //число цифр в последовательности
        seqLen += sqrLen
        curSqr = m * m
    }

    //последовательность длиной n
    while (seqLen > n) {
        curSqr /= 10
        seqLen--
    }

    return (curSqr % 10)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var fibLen: Int
    var seqLen = 0
    var curFib = 0
    var m = 0
    var temp: Int

    //последовательность длиной не меньше n
    while (seqLen < n) {

        //новый элемент последовательности
        m++
        curFib = fib(m)
        temp = curFib

        //число цифр в текущем квадрате
        fibLen = 0
        while (temp > 9) {
            fibLen++
            temp /= 10
        }
        fibLen++

        //число цифр в последовательности
        seqLen += fibLen
    }

    //последовательность длиной n
    while (seqLen > n) {
        curFib /= 10
        seqLen--
    }

    return (curFib % 10)
}
