@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    if (age % 100 in 11..14) {
        return ("$age лет")
    } else when (age % 10) {
        1 -> return ("$age год")
        2 -> return ("$age года")
        3 -> return ("$age года")
        4 -> return ("$age года")
        else -> return ("$age лет")
    }
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val s1 = v1 * t1
    val s2 = v2 * t2
    val s3 = v3 * t3
    val s = (s1 + s2 + s3) / 2
    if (s <= s1) return s / v1
    else if (s <= (s1 + s2)) return t1 + (s - s1) / v2
    else return t1 + t2 + (s - s1 - s2) / v3
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    var underAttack = 0
    if (kingX == rookX1 || kingY == rookY1) underAttack += 1
    if (kingX == rookX2 || kingY == rookY2) underAttack += 2
    return underAttack
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    var underAttack = 0
    val dx: Int
    val dy: Int
    if (kingX == rookX || kingY == rookY) underAttack += 1
    if (kingX > bishopX) dx = kingX - bishopX else dx = bishopX - kingX
    if (kingY > bishopY) dy = kingY - bishopY else dy = bishopY - kingY
    if (dx == dy) underAttack += 2
    return underAttack
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    //проверим, существует ли треугольник
    if (a < (b + c) && b < (a + c) && c < (a + b)) {
        //длинная сторона
        var longSide = 1
        if (b > a && b > c) longSide = 2
        else if (c > a && c > b) longSide = 3
        //из теоремы косинусов
        val angle: Double
        when (longSide) {
            1 -> angle = sqr(b) + sqr(c) - sqr(a)
            2 -> angle = sqr(a) + sqr(c) - sqr(b)
            else -> angle = sqr(a) + sqr(b) - sqr(c)
        }
        when {
            angle < 0 -> return 2
            angle > 0 -> return 0
            else -> return 1
        }
    } else return -1
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    return if (c > b || a > d) -1 //начало одного отрезка за пределами конца другого
    else if (a <= c && c <= b && d >= b) b - c //пересекаются или совпадают, первый раньше начинается или кончается
    else if (a >= c && b <= d) b - a //первый отрезок внутри второго
    else if (c >= a && d <= b) d - c //второй отрезок внутри первого
    else d - a //пересекаются, но второй начинается или кончается раньше первого
}
