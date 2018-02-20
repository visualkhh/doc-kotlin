package com.chapter1

import org.junit.Test
import kotlin.properties.Delegates
import kotlin.reflect.KProperty


//코틀린에서 클래스는 class 키워드를 사용해서 선언한다
//클래스 선언은 클래스 이름과 클래스 헤더(타입 파라미터를 지정, 주요 생성자 등), 중괄호로 둘러 싼 클래스 몸체로 구성된다. 헤더와 몸체는 선택적이다. 클래스에 몸체가
//없으면 중괄호를 생략할 수 있다. //class Empty
//primary 생성자  class 선언과 동시에 옆에 적어서 처리한다
//secondary생성자 안쪽에 constructor(생략가능)로 선언하여 처리할수 있다  주요생성자는 기본생성자로써 처리가된다.
class ClassObject constructor(var name: String, age: Int, var addr: String = "None"){ //수정가능( var )이거나 읽기 전용( val )

    val age = age+1 //주요 생성자 파라미터는 클래스 몸체에 선언한 프로퍼티 초기화에서도 사용할 수 있다:

    //초기화 블록
    init{
        name.plus(" Mr")
    }

    //보조 생성자
    constructor() : this("name", 11){

    }
}
//비추상 클래스
class DontCreateMe private constructor () {
}

//상속과 생성   부모클래스는 상속하여 사용되기위해 open을 사붙혀줘야하고
//자식 클래스에서 부모 생성자는 super로 부른다.
open class View(val name: String, val attribute: String){
    open val addr: String? = null
    open fun print(){
        println("hello")
    }
    //printFinal은 자식에서 오버라이딩할수 없다.
    final fun printFinal(){
        println("hello")
    }

}
class MyView : View {
    constructor(name: String) : super(name, "none")
    constructor(name: String, attribute: String) : super(name, attribute)

    //프로퍼티도 오버리이딩할수 있다.
    override val addr: String?
        get() = super.addr.plus("<-주소")

    //override 키워드를 사용하여 오버라이딩한다.
    override fun print() {
        super.print()
        print("by MyView")
    }

    fun printMyView(){
        println("MyView Print")
    }

    //내부 클래스는 super@Outer 와 같이 외부 클래스의 이름을 사용해서
    //외부 클래스의 상위 클래스에 접근할 수 있다:
    inner class SubView {
        fun g() {
            //MyView의 부모클래스(View)의 print가 호출된다.
            super@MyView.print()
            println(super@MyView.addr) // Foo의 x의 getter 사용

            //아래는 외부클래스(MyView)꺼기때문에 호출불가능
            //super@MyView.printMyView()
        }
    }

}

//인터페이스
interface Foo {
     val count: Int

    open fun f(){
        println ("foo f")
    }
}
open class Bar(val count: Int){
    open fun f(){
        println ("bar f")
    }
}
class Bar1(override val count: Int) : Foo
class Bar2 : Foo {
    override var count: Int = 0
}
//오버라이딩시 겹치는거에대한처리
class BarBox: Foo,Bar(55){

    //겹치는명에 명시적으로 호출가능
    override fun f(){
        super<Foo>.f()
        super<Bar>.f()
    }
}

//////프로퍼티와 필드
class Property(val size: Int = 0){
    val isEmpty1: Boolean
        get() = this.size == 0
    val isEmpty2 get() = this.size == 0 // Boolean 타입임

    var stringRepresentation: String = ""
        get() = this.toString()
        set(value) {
            field += value // 문자열을 파싱해서 다른 프로퍼티에 값을 할당한다 //field를 참고하고싶을때 field를 선언한다
        }

    var setterVisibility: String = "abc"
        private set // setter를 private으로 하고 기본 구현을 가짐

//    var setterWithAnnotation: Any? = null
//        @Inject set // setter에 @Inject 애노테이션 적용

    var counter = 0 // 초기값을 지원 필드에 직접 쓴다
        set(value) {
            if (value >= 0) field = value //field 식별자는 오직 프로퍼티의 접근자에서만 사용할 수 있다.
        }

    //지원(Backing) 프로퍼티
    private var _table: Map<String, Int>? = null
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // 타입 파라미터 추론
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    //컴파일 타임 상수
//    const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"
//    @Deprecated(SUBSYSTEM_DEPRECATED) fun foo() { ... }


    //초기화 지연(Late-Initialized) 프로퍼티
    /*
    보통 null이 아닌 타입으로 선언한 프로퍼티는 생성자에서 초기화해야 한다.
    하지만 이게 편리하지 않을 때가 있다. 예를 들어 의존 주입이나 단위 테스트의 셋업 메서드에서
    프로퍼티를 초기화한다고 하자. 이 경우 생성자에 null이 아닌 초기값을 제공할 수는 없는데, 클래스 몸체에서는 프로퍼티를 참조할 때 null 검사는 피하고 싶을 것이다.
    이런 경우 프로퍼티에 lateinit 수식어를 붙일 수 있다:
     */
    lateinit var subject: String
//    @SetUp fun setup() {
//        subject = TestSubject()
//    }
//    @Test
//    fun test() {
//        subject.method() // 직접 참조하는 객체에 접근
//    }

    //lazy properties
    /*
    호출 시점에 초기화를 진행합니다.
    val(immutable)과 함께 사용합니다.
    */
    val subjectLazy1: String by lazy {
        "제목 초기화"
    }
    // 또는
    val subjectLazy2 by lazy {
        "제목 초기화"
    }
}

//인터페이스!!
interface MyInterface {
    fun bar()
    fun foo() {
// optional body
    }
}
class Child : MyInterface {
    override fun bar() {
// body
    }
}
//인터페이스 프로퍼티
//인터페이스에 프로퍼티를 선언할 수 있다. 인터페이스에 선언한 프로퍼티는 추상이거나 또는 접근자를 위한 구현을 제공할 수 있다. 인터페이스의 프로퍼티는 지원
//(backing) 필드를 가질 수 없으므로, 인터페이스에 선언한 프로퍼티에서 지원 필드를 참조할 수 없다.
interface MyInterface1 {
    val prop: Int // 추상
    val propertyWithImplementation: String
        get() = "foo"
    fun foo() {
        print(prop)
    }
}
class Child1 : MyInterface1 {
    override val prop: Int = 29
}


//가시성 수식어
/*
    가시성 수식어를 명시하지 않으면 기본으로 public 을 사용한다. 이는 모든 곳에서 접근 가능함을 뜻한다.
    private 으로 선언하면, 그 선언을 포함한 파일 안에서만 접근할 수 있다.
    internal 로 선언하면, 같은 모듈 에서 접근 가능하다.
    protected 는 최상위 선언에 사용할 수 없다.
 */
private fun foo() {} // 같은파일 안에서 접근 가능
public var bar: Int = 5 // 모든 곳에서 접근 가능
    private set // setter는 같은파일 에서만 접근 가능
internal val baz = 6 // 같은 모듈에서 접근 가능
//클래스와 인터페이스
/*
private 은 오직 클래스 안에서만(그리고 클래스의 모든 멤버에서) 접근 가능함을 의미한다.
protected — private + 하위클래스에서 접근 가능함과 동일하다.
internal — 선언한 클래스를 볼 수 있는 모듈 안의 모든 클라이언트가 internal 멤버를 볼 수 있다.
public — 선언한 클래스를 볼 수 있는 클라이언트가 public 멤버를 볼 수 있다.
주의 자바와 달리 코틀린에서 외부 클래스는 내부 클래스의 private 멤버를 볼 수 없다.
protected 멤버를 오버라이딩할 때 가시성을 명시적으로 지정하지 않으면, 오버라이딩한 멤버 또한 protected 가시성을 갖는다.
 */
open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4 // 기본으로 public
    protected class Nested {
        public val e: Int = 5
    }
}
class Subclass : Outer() {
    // a는 접근 불가
// b, c, d는 접근 가능
// Nested와 e는 접근 가능
    override val b = 5 // 'b'는 protected
}
class Unrelated(o: Outer) {
// o.a, o.b는 접근 불가
// o.c 와 o.d는 접근 가능(같은 모듈)
// Outer.Nested는 접근 불가며, Nested::e 역시 접근 불가
}


//확장(Extensions) 중요
//확장 함수
//확장 함수를 선언하려면 _리시버(receiver) 타입_의 이름을 접두어로 가져야 한다.
// 리시버 타입의 이름은 확장할 타입의 이름이다. 다음 코드는 MutableList<Int>
//에 swap 함수를 추가한다:
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this'는 List에 대응한다.
    this[index1] = this[index2]
    this[index2] = tmp
}
fun String.khh():Int{
    return this.length+2
}

//정적인 확장 결정
/*
확장이 실제로 확장할 클래스를 변경하지 않는다. 확장을 정의하는 것은 클래스에 새 멤버를 추가하기보다는, 그 타입의 변수에 점 부호로 호출할 수 있는 새 함수를 만드는
것이다.
확장 함수는 정적으로 전달된다는 점을 강조하고 싶다. 예를 들어, 리시버 타입에 따라 동적으로(virtual) 확장 함수를 결정하지 않는다. 이는 함수 호출 식의 타입에 따라 호
출할 확장 함수를 결정한다는 것을 뜻한다. 런타임에 식을 평가한 결과 타입으로 결정하지 않는다. 다음 예를 보자
 */


open class C
class D: C()
fun C.foo() = "c"
fun D.foo() = "d"
fun printFoo(c: C) {
    println(c.foo())
}
fun printFoo(c:  D) {
    println(c.foo())
}

//클래스가 해당 클래스를 리시버 타입으로 갖는 확장 함수와 동일한 멤버 함수를 가진 경우, 항상 멤버 함수가 이긴다 . 다음 예를 보자:
class C1 {
    fun foo() { println("member") }
}
fun C1.foo() { println("extension") }
fun C1.foo(i: Int) { println("extension Int"+i) }


//null 가능 리서버
//확장이 null 가능 리시버 타입을 가질 수 있도록 정의할 수 있다. 이 확장은 객체 변수가 null인 경우에도 호출할 수 있고, 몸체 안에서 this == null 로 이를 검사할
//수 있다. 이는 코틀린에서 null 검사 없이 toString()을 호출할 수 있도록 한다. 확장 함수 안에서 이 검사를 한다.
fun Any?.toString(): String {
    if (this == null) return "null"
// null 검사 후에 'this'는 자동으로 non-null 타입으로 변환된다. 따라서 아래 toString()을 모든 클래스의
// 멤버 함수로 처리한다.
    return toString()
}
//확장 프로퍼티
val <T> List<T>.lastIndex: Int
    get() = size - 1
//val Foo.bar = 1 // 에러: 확장 프로퍼티에 대한 초기화는 허용하지 않는다.


//컴페니언 오브젝트 확장
//클래스에 컴페니언 오브젝트 가 있으면, 컴페니언 오브젝트를 위한 확장 함수와 프로퍼티를 정의할 수 있다:
class MyClass {
    companion object { } // "Companion"으로 접근
}
fun MyClass.Companion.foo() {
 println("extenstion MyClass Compainon Foo")
}


//멤버로 확장 선언하기
//클래스 안에 다른 클래스를 위한 확장을 선언할 수 있다. 그런 확장안에서는 한정자 없이 접근할 수 있는 암묵적인(implicit ) 리시버 객체 멤버가 존재한다. 확장 함수를 선언
//하고 있는 클래스의 인스턴스를 _디스패치 리시버(전달 리시버)_라고 부르며, 확장 메서드의 리시버 타입 인스턴스를 _확장 리시버_라고 부른다.
class DM {
    fun bar() { println("DM_bar") }
}
class CM {
    fun baz() { println("CM_baz") }
    fun DM.foo() {
        bar() // D.bar 호출
        baz() // C.baz 호출
    }
    fun caller(d: DM) {
        d.foo() // 확장 함수를 호출
    }
}
class CMM {
    fun DM.foo() {
        toString() // D.toString() 호출
        this@CMM.toString() // C.toString() 호출
    }
}

//데이터 클래스
//데이터를 보관하기 위한 목적으로 클래스를 자주 만든다. 그런 클래스는 종종 데이터에서 표준 기능이나 유틸리티 함수를 기계적으로 생성한다. 코틀린에서 이를 _데이터 클
//래스_라고 부르며 data 로 표시한다:
/*
equals() / hashCode() 쌍
"User(name=John, age=42)" 형식의 toString()
선언 순서대로 프로퍼티에 대응하는 componentN() 함수
copy() 함수 (아래 참고).
 */
data class User(val name: String, val age: Int)




//실드 클래스
/*
값이 제한된 타입 집합 중 하나를 가질 수 있지만 다른 타입은 가질 수 없을 때, 클래스 게층의 제한을 표현하기 위해 실드(Sealed) 클래스를 사용한다. 어떤 의미에서 실드
클래스는 enum 클래스를 확장한 것이다 enum 타입도 값 집합이 제한되어 있지만, 각 enum 상수는 단지 한 개 인스턴스로 존재하는 반면에 실드 클래스의 하위 클래스는
상태를 가질 수 있는 여러 인스턴스가 존재할 수 있다.
실드 클래스를 선언하려면 클래스 이름 앞에 sealed 수식어를 붙인다. 실드 클래스는 하위 클래스를 가질 수 있지만 모든 하위클래스는 반드시 실드 클래스와 같은 파일
에 선언해야 한다. (코틀린 1.1 이전에, 이 규칙은 더 엄격했었다. 실드 클래스 선언 내부에 클래스를 중첩해야 했다.)
 */
/*
실드 클래스를 사용해서 얻게 되는 핵심 이점은 when 식 과 함께 사용할 때 드러난다. when 식이 모든 경우를 다루는 것을 확신할 수 있다면 else 절을 문장에 추가할
필요가 없다. 하지만 이는 when 을 문장이 아닌 식으로 사용하는 경우에만 동작한다.
 */
sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()
fun eval(expr: Expr): Double = when(expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
// 모든 경우를 다루므로 `else` 절이 필요 없다
}


//제네릭
class Box<T>(t: T) {
    var value = t
}


//변성(Variance)
/*
자바 타입 시스템에서 가장 다루기 힘든 것 중 하나가 와일드카드 타입이다( 자바 지네릭 FAQ 참고), 코틀린에는 와일드카드 타입이 없다. 대신 선언 위치 변성
(declaration-site variance)과 타입 프로젝션(type projections)을 제공한다.
첫째로 자바에 왜 그런 미스테리한 와일드카드가 필요한자 생각해보자. Effective Java , Item 28: Use bounded wildcards to increase API flexibility 에서 문제를 설
명하고 있다. 먼저 자바의 지네릭 타입은 무공변(invariant) 이다. 이는 List<String> 이 List<Object> 의 하위타입이 아님을 의미한다. 왜 그럴까? 만약 리
스트가 무공변 이면 자바 배열보다 나을 게 없다. 왜냐면 다음 코드가 컴파일되고 런타임에 익셉션이 발생하기 때문이다:
 */

abstract class Comparable<in T> {
    abstract fun compareTo(other: T): Int
}
fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0은 Double 타입인데 이는 Number의 하위타입이다.
// 따라서 Comparable<Double> 타입의 변수에 x를 할당할 수 있다.
    val y: Comparable<Double> = x // OK!
}
/*
in 과 out 단어가 자명하다고 믿기에(이미 C#에서 꽤나 오랫동안 성공적으로 이 용어를 사용하고 있다), 앞서 언급한 약자 PECS는 더 이상 필요 없으며, 더 상위 목적으로
약어를 바꿔볼 수 있다
 */

/*사용 위치 변성(Use-site variance): 타입 프로젝션
타입 파라미터 T를 out 으로 선언하면 매우 편리하며, 사용 위치에서 하위타입에 대한 문제를 피할 수 있다. 하지만 어떤 클래스는 실제로 오직 T 만 리턴하도록 제약할
수 없다. 배열이 좋은 예이다:
 */

//스타 프로젝션(Star-projections)
//때때로 타입 인자에 대해 알지 못하지만 안전한 방법으로 타입 인자를 사용하고 싶을 때가 있다. 여기서 안전한 방법은 지네릭 타입의 프로젝션을 정의하는 것이다. 그 지네
//릭 타입의 모든 실제 인스턴스는 그 프로젝션의 하위타입이 된다.
/*
    코틀린은 이를 위해 스타 프로젝션 이라 불리는 구문을 제공한다:
    Foo<out T> 에서 T 는 상위 한계로 TUpper 를 가진 공변 타입 파라미터면, Foo<*> 는 Foo<out TUpper> 와 동일하다. 이는 T 를 알 수 없
    을 때 Foo<*> 에서 안전하게 TUpper 의 값을 읽을 수 있다는 것을 의미한다.
    Foo<in T> 에서 T 가 반공변 타입 파라미터면, Foo<*> 는 Foo<in Nothing> 과 동일하다. 이는 T 를 알 수 없을 때 안전하게 Foo<*> 에 쓸
    수 없다는 것을 의미한다.
    Foo<T> 에서 T 가 상위 한계로 TUpper 를 가진 무공변 타입 파라미터면, Foo<*> 는 값을 읽는 것은 Foo<out TUpper> 와 동일하고 값을 쓰는
    것은 Foo<in Nothing> 과 동일하다.
    지네릭 타입이 타입 파라미터가 여러 개이면, 타입 파라미터별로 따로 프로젝션할 수 있다. 예를 들어, interface Function<in T, out U> 타입이 있을 때 다
    음의 스타-프로젝션을 만들 수 있다:
    Function<*, String> 은 Function<in Nothing, String> 을 의미한다.
    Function<Int, *> 은 Function<Int, out Any?> 를 의미한다.
    Function<*, *> 은 Function<in Nothing, out Any?> 를 의미한다.
    주의 : 스타-프로젝션은 자바의 raw 타입과 거의 같지만 안전하다.
 */



//중첩 클래스와 내부 클래스
class Outer1 {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}

//내부 클래스
//inner 로 지정한 클래스는 외부 클래스의 멤버에 접근할 수 있다. 내부 클래스는 외부 클래스의 객체에 대한 레퍼런스를 갖는다:
class Outer2 {
    private val bar: Int = 1
    inner class Inner {
        fun foo() = bar
    }
}
//익명 내부 클래스
//window.addMouseListener(object: MouseAdapter() {
//    override fun mouseClicked(e: MouseEvent) {
//// ...
//    }
//    override fun mouseEntered(e: MouseEvent) {
//// ...
//    }
//})
//객체가 함수형 자바 인터페이스의 인스턴스라면(예를 들어 한 개의 추상 메서드를 가진 자바 인터페이스), 인터페이스 타입을 접두어로 갖는 람다 식을 사용해서 익명 내부
//객체를 생성할 수 있다: val listener = ActionListener { println("clicked") }


//Enum 클래스
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
//초기화
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}
//익명 클래스
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },
    TALKING {
        override fun signal() = WAITING
    };
    abstract fun signal(): ProtocolState
}

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}



//오브젝트 식과 선언
//때때로 하위클래스를 새로 만들지 않고 특정 클래스를 약간 수정한 객체를 만들고 싶을 때가 있다. 자바에서는 익명 내부 클래스 를 사용해서 이런 경우를 처리한다. 코틀린
//은 오브젝트 식 과 오브젝트 선언 으로 이 개념을 일부 일반화했다.
//window.addMouseListener(object : MouseAdapter() {
//    override fun mouseClicked(e: MouseEvent) {
//// ...
//    }
//    override fun mouseEntered(e: MouseEvent) {
//// ...
//    }
//})

//상위타입이 생성자를 가지면, 알맞은 생성자 파라미터를 전달해야 한다. 상위타입이 여러 개면, 콜론 뒤에 콤마로 구분해서 지정한다:
open class A(x: Int) {
    public open val y: Int = x
}
interface B {
    public open val x: Int
}
val ab: A = object : A(1), B {
    override val x: Int
        get() = 5
    override val y = 15
}


//만약 별도 상위타입 없이 "단지 객체"가 필요한거라면, 다음과 같이 단순히 생성할 수 있다:
fun foo1() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    print(adHoc.x + adHoc.y)
}


//익명 객체는 로컬과 private 선언에서만 타입으로 사용할 수 있다. public 함수의 리턴 타입이나
//public 프로퍼티의 타입으로 익명 객체를 사용하면, 그 함수나 프로퍼티의
//실제 타입은 익명 객체에 선언한 상위 타입이 된다. 상위 타입을 선언하지 않았다면 Any 가 된다. 익명 객체에 추가한 멤버는 접근하지 못한다.
class CC {
    // private 함수이므로 리턴 타입은 익명 객체 타입이다
    private fun foo() = object {
        val x: String = "x"
    }
    // public 함수이므로 리턴 타입은 Any이다
    fun publicFoo() = object {
        val x: String = "x"
    }
    fun bar() {
        val x1 = foo().x // 동작
        //val x2 = publicFoo().x // 에러: 레퍼런스'x'를 찾을 수 없음
    }
}

//오브젝트 선언 싱글톤
//이를 오브젝트 선언 이라고 부른다. 오브젝트 선언에는 object 키워드 뒤에 이름이 위치한다. 변수 선언처럼, 오브젝트 선언은 식이 아니며 할당 문장의 오른쪽에 사용할
//수 없다.
object DataProviderManager {
    fun registerDataProvider(provider: String) {
    // ...
    }
    val allDataProviders: Collection<String>
        get() = listOf("1")
}

//컴페니언 오브젝트
//클래스 내부의 오브젝트 선언은 companion 키워드를 붙일 수 있다:
class MyClassCom {
    companion object Factory {
        fun create(): MyClassCom = MyClassCom()
    }
}

//컴페니언 오브젝트의 이름은 생략이 가능하며, 이 경우 이름으로 Companion 을 사용한다:
class MyClassCom2 {
    companion object {
    }
}

//컴페니언 오브젝트의 멤버가 다른 언어의 정적 멤버처럼 보이겠지만, 런타임에 컴페니언 오브젝트는 실제 객체의 인스턴스 멤버이므로 인터페이스를 구현할 수 있다
interface Factory<T> {
    fun create(): T
}
class MyClassCom3 {
    companion object : Factory<MyClassCom3> {
        override fun create(): MyClassCom3 = MyClassCom3()
    }
}
/*오브젝트 식과 선언의 의미 차이
오브젝트 식과 오브젝트 선언 사이에는 중요한 의미 차이가 있다:
오브젝트 식은 사용한 위치에서 즉시 초기화되고 실행된다.
오브젝트 선언은 처음 접근할 때까지 초기화를 지연 한다.
컴페니언 오브젝트는 대응하는 클래스를 로딩할 때 초기화된다. 이는 자바의 정적 초기화와 동일하다
 */

//위임
//위임 패턴 은 구현 상속보다 좋은 대안임이 증명됐다. 코틀린은 중복 코드없는 위임 패턴을 지원한다. 아래 코드에서 Derived 클래스는 Base 인터페이스를 상속할
//수 있으며, 모든 public 메서드를 지정한 객체로 위임한다:
/*
Derived 의 상위타입 목록에 있는 by 절은 Derived 의 객체 내부에 b 를 저장한다는 것을 의미한다. 컴파일러는 Base 의 모든 메서드를 b 로 전달하도
록 Derived 를 생성한다.
override는 그대로 동작한다. override 가 존재하면 컴파일러는 위임 객체의 메서드 대신 override 구현을 사용한다. Derived 에 override fun
print() { print("abc") } 를 추가하면 프로그램은 "10" 대신 "abc"를 출력한다
 */
interface Base {
    fun print()
}
class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}
class Derived(b: Base) : Base by b



//위임 프로퍼티
/*
필요할 때마다 수동으로 구현할 수 있지만 한 번만 구현하고 라이브러리에 추가하면 좋을 만한 공통 프로퍼티가 존재한다. 다음은 예이다.
lazy 프로퍼티: 처음 접근할 때 값을 계산한다.
observable 프로퍼티: 프로퍼티가 바뀔 때 리스너에 통지한다.
맵에 저장할 프로퍼티: 각 프로퍼티를 별도 필드에 저장하는 대신 맵에 저장한다.
이런 (그리고 다른) 경우를 다를 수 있도록 코틀린은 _위임 프로퍼티_를 지원한다:
 */
class Example {
    var p: String by Delegate()
}

/*
구문은 val/var <property name>: <Type> by <expression> 이다. by 뒤의 식이 _대리객체(delegate)_로 프로퍼티에 대응하는 get() (그리
고 set() )은 대리객체의 getValue() 와 setValue() 메서드에 위임한다.
프로퍼티 대리객체는 인터페이스를 구현하면 안 되며, getValue() 함수를 제공해야 한다(그리고 var 프로퍼티의 경우 setValue() 도 제공). 다음은 예이다
 */
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name} in $thisRef.'")
    }
}
//위 예제의 p 프로퍼티를 읽을 때, 위임한 Delegate 인스턴스의 getValue() 함수를 호출한다. getValue() 의 첫 번째 파라미터는 p 를 포함한 객체이
//고, 두 번째 파라미터는 p 자체에 대한 설명을 포함한다(예를 들어, 이름을 포함한다). 다음 예를 보자.




//표준 위임
//Lazy
//lazy() 는 람다를 파라미터로 받고 Lazy<T> 인스턴스를 리턴하는 함수이다. 이는 lazy 프로퍼티를 구현하기 위한 대리객체로 동작한다. 이 객체는 get() 을 처
//음 호출할 때 lazy() 에 전달한 람다를 실행하고 그 결과를 기억한다. 이어진 get() 호출은 단순히 기억한 결과를 리턴한다.
/*
기본적으로 lazy 프로퍼티의 평가를 동기화 한다. 한 쓰레드에서만 값을 계산하고, 모든 쓰레드는 같은 값을 사용한다. 위임 초기화에 동기화가 필요없으면 lazy() 함
수에 파라미터로 LazyThreadSafetyMode.PUBLICATION 을 전달해서 동시에 여러 쓰레드가 초기화를 실행할 수 있게 허용할 수 있다. 단일 쓰레드가 초기화를
할 거라 확신할 수 없다면 LazyThreadSafetyMode.NONE 모드를 사용한다. 이는 쓰레드 안정성을 보장하지 않으며 관련 부하를 발생하지 않는다.
 */
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}



//Observable
//Delegates.observable() 은 두 개의 인자를 갖는다. 첫 번째는 초기 값이고 두 번째는 수정에 대한 핸들러이다. 프로퍼티에 값을 할당할 때마다 (할당이 끝난 이후
//에 ) 핸들러를 호출한다. 핸들러는 할당된 프로퍼티, 이전 값, 새 값의 세 파라미터를 갖는다:
//만약 할당을 가로채서 그것을 "거부"하고 싶다면, observable() 대신 vetoable() 을 사용한다. 프로퍼티에 새 값을 할당하기 전에 vetoable 에 전달한 핸
//들러를 호출한다.
class UserUs {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$old -> $new")
    }
}





//맵에 프로퍼티 저장하기
//위임 프로퍼티의 공통된 용도는 맵에 프로퍼티 값을 저장하는 것이다. JSON을 파싱하거나 다른 "동적인" 것을 하는 어플리케이션에 주로 사용한다. 이 경우 위임 프로퍼티
//를 위한 대리 객체로 맵 인스턴스 자체를 사용할 수 있다.
//읽기 전용 Map 대신에 MutableMap 을 사용하면 var 프로퍼티에 동작한다: class MutableUser(val map: MutableMap<String, Any?>) {
class UserMap(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}


//로컬 위임 프로퍼티
//로컬 변수를 위임 프로퍼티로 선언할 수 있다. 예를 들어, 로컬 변수를 lazy로 만들 수 있다
//fun example(computeFoo: () -> Foo) {
//    val memoizedFoo by lazy(computeFoo)
//    if (someCondition && memoizedFoo.isValid()) {
//        memoizedFoo.doSomething()
//    }
//}
//프로퍼티 위임 요구사항
/*
    읽기 전용 프로퍼티의 경우( val ), 위임 객체는 이름이 getValue 이고 다음 파라미터를 갖는 함수를 제공해야 한다:
    thisRef — _프로퍼티 소유자_와 같거나 또는 상위 타입이어야 한다(확장 프로퍼티의 경우 — 확장한 타입).
    property — KProperty<*> 타입 또는 그 상위타입이어야 한다.
    이 함수는 프로퍼티와 같은 타입(또는 하위 타입)을 리턴해야 한다.
    변경 가능 프로퍼티의 경우 ( var ), 위임 객체는 추가로 이름이 setValue 이고 다음 파라미터를 갖는 함수를 제공해야 한다:
    thisRef — getValue() 와 동일
    property — getValue() 와 동일
    새 값 — 프로퍼티와 같은 타입 또는 상위타입이어야 한다.
    getValue() 와 setValue() 함수는 위임 클래스의 멤버 함수나 확장 함수로도 제공할 수 있다. 원래 이 함수를 제공하지 않는 객체에 위임 프로퍼티가 필요한 경
    우 확장 함수가 편하다. 두 함수 모두 operator 키워드를 붙여야 한다.
    위임 클래스는 요구한 operator 메서드를 포함하고 있는 ReadOnlyProperty 와 ReadWriteProperty 인터페이스 중 하나를 구현할 수 있다. 이 두 인
    터페이스는 코틀린 표준 라이브러리에 선언되어 있다:
 */

interface ReadOnlyProperty<in R, out T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T
}
interface ReadWriteProperty<in R, T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T)
}
//변환 규칙
//모든 위임 프로퍼티에 대해, 코틀린 컴파일러는 보조 프로퍼티를 생성하고 그 프로퍼티에 위임한다. 예를 들어, prop 프로퍼티에 대해 prop$delegate 라는 숨겨
//진 프로퍼티를 생성하고, 접근자 코드는 단순히 이 프로젝트에 위임한다
//class C {
//    var prop: Type by MyDelegate()
//}
// 이 코드는 컴파일러가 대신 생성한다
//class C {
//    private val prop$delegate = MyDelegate()
//    var prop: Type
//        get() = prop$delegate.getValue(this, this::prop)
//    set(value: Type) = prop$delegate.setValue(this, this::prop, value)
//}



//위임객체 제공
/*
provideDelegate 연산자를 정의하면, 위임 프로퍼티가 위임할 객체를 생성하는 로직을 확장할 수 있다. by 의 오른쪽에서 사용할 객체가
provideDelegate 를 멤버 함수나 확장 함수로 정의하면, 프로퍼티의 위임 대상 인스턴스를 생성할 때 그 함수를 호출한다.
provideDelegate 의 가능한 한 가지 용도는 프로퍼티의 getter나 setter뿐만 아니라 생성할 때 프로퍼티의 일관성을 검사하는 것이다.
예를 들어, 값을 연결하기 전에 프로퍼티 이름을 검사하고 싶다면, 다음 코드로 이를 처리할 수 있다:
 */

//class ResourceLoader<T>(id: ResourceID<T>) {
//    operator fun provideDelegate(
//            thisRef: MyUI,
//            prop: KProperty<*>
//    ): ReadOnlyProperty<MyUI, T> {
//        checkProperty(thisRef, prop.name)
//// 대리 객체 생성
//    }
//    private fun checkProperty(thisRef: MyUI, name: String) { ... }
//}
//fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> { ... }
//class MyUI {
//    val image by bindResource(ResourceID.image_id)
//    val text by bindResource(ResourceID.text_id)
//}
/*
provideDelegate 파라미터는 getValue 와 동일하다:
thisRef — _프로퍼티 소유자_와 같거나 또는 상위 타입이어야 한다(확장 프로퍼티의 경우 — 확장한 타입).
property — KProperty<*> 타입 또는 그 상위타입이어야 한다.
MyUI 인스턴스를 생성하는 동안 각 프로퍼티에 대해 provideDelegate 메서드를 호출하고 즉시 필요한 검증을 수행한다.
프로퍼티와 대리객체 사이의 연결을 가로채는 능력이 없는데, 같은 기능을 구현하려면 편한 방법은 아니지만 명시적으로 프로퍼티 이름을 전달해야 한다.
 */
// "provideDelegate" 기능 없이 프로퍼티 이름을 검사
//class MyUI {
//    val image by bindResource(ResourceID.image_id, "image")
//    val text by bindResource(ResourceID.text_id, "text")
//}
//fun <T> MyUI.bindResource(
//        id: ResourceID<T>,
//        propertyName: String
//): ReadOnlyProperty<MyUI, T> {
//    checkProperty(this, propertyName)
//// 대리객체를 생성한다
//}

/*
생성한 코드는 보조 프로퍼티인 prop$delegate 를 초기화하기 위해 provideDelegate 를 호출한다. val prop: Type by MyDelegate() 프로
퍼티 선언을 위해 생성한 코드와 위에서 생성한 코드( provideDelegate 메서드가 없는 경우)를 비교하자.
 */
//class C {
//    var prop: Type by MyDelegate()
//}
//// 'provideDelegate'가 사용가능할 때
//// 컴파일러가 이 코드를 생성한다:
//class C {
//    // 추가 "delegate" 프로퍼티를 생성하기 위해 "provideDelegate"를 호출
//    private val prop$delegate = MyDelegate().provideDelegate(this, this::prop)
//    val prop: Type
//        get() = prop$delegate.getValue(this, this::prop)
//}
//provideDelegate 메서드는 보조 프로퍼티의 생성에만 영향을 주고, getter나 setter를 위한 코드 생성에는 영향을 주지 않는다.

fun main(args: Array<String>) {
    //new 없이 생성
    val c = ClassObject("name",55)
    val myView = MyView("name")
    myView.SubView().g()


    println("asdasdasd".khh())
    //지연 초기화
    Property().subject="ggg"

    //확장
    val l = mutableListOf(1, 2, 3)
    l.swap(0, 2) // 'swap()'에서 'this'는 'l' 값을 갖는다

    //이 예는 "c"를 출력한다. printFoo() 함수의 c 파라미터 타입이 C 클래스이므로 C 타입에 대한 확장 함수를 호출하기 때문이다.
    //던저진 클래스의 플래그로 비교후 콜한다.
    printFoo(C()) //-> c
    printFoo(D()) //-> d


    C1().foo() //member
    C1().foo(1) //extension Int 1


    //프로퍼티 확장
    val list = listOf(1,5)
    list.lastIndex
    //companion extention
    MyClass.foo()



    //데이터 선언과 분리 선언
    val jane = User("Jane", 35)
    val (name, age) = jane
    println("$name, $age years of age") // "Jane, 35 years of age" 출력

    //제너릭
    val box: Box<Int> = Box<Int>(1)

    //중첩클래스
    val demo1 = Outer1.Nested().foo() // == 2
    //내부 클래스
    val demo2 = Outer2().Inner().foo() // == 1

    //enum 상수 사용
    Direction.valueOf("SOUTH") //: EnumClass
    Color.values()//: Array<EnumClass>

    printAllValues<Direction>() // NORTH, SOUTH, WEST, EAST 출력

    //컴페니언 오브젝트
    val instance = MyClassCom.create()
    val x = MyClassCom2.Companion


    //위임
    val b = BaseImpl(10)
    Derived(b).print() // 10 출력

    //위임프로퍼티
    val e = Example()
    println(e.p) //Example@33a17727, thank you for delegating ‘p’ to me!
    e.p = "NEW" //NEW has been assigned to ‘p’ in Example@33a17727.

    //표준위임
    //Lazy
    println(lazyValue)
    println(lazyValue)



    //Observable
    val userUs = UserUs()
    userUs.name = "first" //<no name> -> first
    userUs.name = "second" //first -> second

    //맵에 프로퍼티 저장하기
    val userMap = UserMap(mapOf(
            "name" to "John Doe",
            "age" to 25
    ))
    println(userMap.name) // "John Doe"를 출력
    println(userMap.age) // 25를 출력
}
