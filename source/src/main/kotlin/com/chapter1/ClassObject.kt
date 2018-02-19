package com.chapter1

import org.junit.Test


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


//익명 객체는 로컬과 private 선언에서만 타입으로 사용할 수 있다. public 함수의 리턴 타입이나 public 프로퍼티의 타입으로 익명 객체를 사용하면, 그 함수나 프로퍼티의
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


}
