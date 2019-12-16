@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val d = center.distance(other.center) - radius - other.radius
        return if (d > 0) d else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException("Мало точек для поиска диаметра")
    var result = Segment(points[0], points[1])

    for (f in 0..points.size - 2) {
        for (s in 1 until points.size) {
            if (result.begin.distance(result.end) < points[f].distance(points[s])) result = Segment(points[f], points[s])
        }
    }
    return result
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val xmax = max(diameter.begin.x, diameter.end.x)
    val xmin = min(diameter.begin.x, diameter.end.x)
    val cx = (xmax - xmin) / 2 + xmin
    val ymax = max(diameter.begin.y, diameter.end.y)
    val ymin = min(diameter.begin.y, diameter.end.y)
    val cy = (ymax - ymin) / 2 + ymin
    val cr = diameter.begin.distance(diameter.end) / 2
    return Circle(Point(cx, cy), cr)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val result: Point
        val x = (other.b * cos(this.angle) - this.b * cos(other.angle)) / (sin(this.angle) * cos(other.angle) - sin(other.angle) * cos(this.angle))
        val y= (x * sin(other.angle) + other.b) / cos(other.angle)
        result = Point(x, y)
        return result
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val cosAlpha: Double
    var sinAlpha: Double
    val l = s.begin.distance(s.end)
    cosAlpha = (s.end.x - s.begin.x) / l
    sinAlpha = (s.end.y - s.begin.y) / l
    if (s.end.y < s.begin.y) sinAlpha += PI
    val alpha: Double
    alpha = acos(cosAlpha)
    return Line(Point(s.begin.x, s.begin.y), alpha)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val xmax = max(a.x, b.x)
    val xmin = min(a.x, b.x)
    val cx = (xmax - xmin) / 2 + xmin
    val ymax = max(a.y, b.y)
    val ymin = min(a.y, b.y)
    val cy = (ymax - ymin) / 2 + ymin
    val l = a.distance(b)
    val cosAlpha = (a.x - b.x) / l
    var alpha = acos(cosAlpha)
    if (b.y < a.y) alpha *= -1.0
    alpha += PI / 2
    if (alpha >= PI) alpha -= PI
    return Line(Point(cx, cy), alpha)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException("Короткий список окружностей")
    var result = Pair(circles[0], circles[1])
    for (i in 0..(circles.size - 2)) {
        for (j in i+1 until circles.size) {
            val disij = circles[i].distance(circles[j])
            val disresult = result.first.distance(result.second)
            if (disij < disresult) result = Pair(circles[i], circles[j])
        }
    }
    return result
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    //построим серединный перпендикуляр к отрезку ab
    val l1 = bisectorByPoints(b, a)
    //прстроим серединный перпендикуляр к отрезку bc
    val l2 = bisectorByPoints(a, c)
    //найдем точку пересечения перпендикуляров (Центр окружности)
    val cen = l1.crossPoint(l2)
    //найдем расстояние до одной из точек (радиус окружности)
    val r = cen.distance(a)
    return Circle(cen, r)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

