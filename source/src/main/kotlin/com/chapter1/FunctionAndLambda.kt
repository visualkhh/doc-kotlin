package com.chapter1

import com.sun.corba.se.impl.orbutil.graph.Graph
import sun.security.provider.certpath.Vertex
import java.util.concurrent.locks.Lock

class FunctionAndLambda(){


    //함수 fun키워드사용 함수 선언가능
    //파라미터 파스칼 표기법( name : type )을 사용해서 파라미터를 정의한다. 각 파라미터는 콤마로 구분하며 타입을 가져야 한다
    //기본 인자  함수 파라미터는 기본 값을 가질 수 있다. 이 경우 대응하는 인자를 생략하면 기본 값을 사용한다. 이는 다른 언어와 비교해 오버로딩 함수의 개수를 줄여준다.
    fun double(x: Int=1): Int {
        return 2*x
    }
    fun foo(bar: Int = 0, baz: Int) { /* ... */ }
    fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) { /* ... */ }
    //오버라이딩 메서드는 항상 베이스 메서드와 같은 기본 파라미터 값을 사용한다. 기본 파라미터 값을 갖는 메서드를 오버라이딩 할때, 시그너처에서 기본 파라미터를 값을 생
    //략해야 한다:
    open class A {
        open fun foo(i: Int = 10) { /*...*/ }
    }
    class B : A() {
        override fun foo(i: Int) { /*...*/ } // 기본 값을 허용하지 않음
    }


    //이름 가진 인자(Named Argument)
    //함수를 호출할 때 함수 파라미터에 이름을 줄 수 있다. 이름을 가진 인자를 사용하면 함수에 파라미터 개수가 많거나 기본 값이 많을 때 편리하다.
    //다음과 같은 함수가 있다고 하자:
    fun reformat(str: String,
                 normalizeCase: Boolean = true,
                 upperCaseFirstLetter: Boolean = true,
                 divideByCamelHumps: Boolean = false,
                 wordSeparator: Char = ' ') {
        /*...*/
    }


    // 펼침(spread)
    fun foo(vararg strings: String) { /* ... */ }

    //Unit 리턴 함수   void
    //함수가 어떤 유용한 값도 리턴하지 않으면, 리턴 타입으로 Unit 을 사용한다. Unit 은 Unit 을 유일한 값으로 갖는 타입이다. 이 값을 명시적으로 리턴하면 안 된다:
    fun printHello(name: String?): Unit {
        if (name != null)
            println("Hello ${name}")
        else
            println("Hi there!")
        // `return Unit` 또는 `return` 생략
    }
    //리턴타입으로 Unit를 선언하는 것 또한 생략 가능하다
    fun printHello1(name: String?) {
        /*...*/
    }

    //단일 식 함수
    fun double2(x: Int): Int = x * 2
    fun double3(x: Int) = x * 2

    //명시적 리턴 타입
    //블록 몸체를 갖는 함수는 Unit 을 리턴한다는 것을 의도하지 않는 이상 항상 리턴 타입을 명시적으로 지정해야 한다. Unit을 리턴하는 경우 생략 가능하다 . 코틀린은 블
    //록 몸체를 가진 함수의 리턴 타입을 유추할 수 없다. 왜냐면 그런 함수가 복잡한 제어 흐름을 가지면 리턴 타입을 독자가 (그리고 때때로 컴파일러 조차도) 알 수 없기 때문이다



    //가변 인자 (Varargs)
    //함수 파라미터에 (보통 마지막 파라미터) vararg 수식어를 붙일 수 있다
    /*
    T 타입의 vararg 파라미터는 함수에서 T 배열로 접근한다. 예를 들어, 위 코드에서 ts 변수는 Array<out T> 타입이다. 가변 파라미터 뒤의 파라미터 값
    은 이름 가진 인자 구문으로 전달할 수 있다. 또는 가변 파라미터 뒤의 파라미터가 함수 타입이면 람다를 괄호 밖으로 전달할 수 있다.
    vararg 함수를 호출할 때 인자를 asList(1, 2, 3) 와 같이 한 개 씩 전달할 수 있다. 또는 이미 배열이 있다면 펼침(spread) 연산자(배열 앞에 * 사용)를
    사용해서 배열을 함수의 vararg 인자로 전달할 수 있다:
     */
    fun <T> asList(vararg ts: T): List<T> {
        val result = ArrayList<T>()
        for (t in ts) // ts is an Array
            result.add(t)
        return result
    }


    //중위 부호
    /*
        다음 경우에 중위 부호를 사용해서 함수를 호출할 수 있다.
        멤버 함수이거나 확장 함수 인 경우
        파라미터가 한 개인 경우
        infix 키워드를 붙인 경우
     */
    // Int에 대한 확장 함수 정의
    //1 shl 2
    infix fun Int.shl(x: Int): Int {
        /*...*/
        return x
    }



    //함수 범위
    //코틀린은 함수를 파일에서 최상위 수준으로 선언할 수 있다. 이는 자바, C#, 스칼라와 달리 함수를 포함할 클래스를 만들 필요가 없다는 것을 의미한다. 최상위 수준 함수뿐
    //만 아니라 함수를 로컬로, 멤버 함수로, 확장 함수로 선언할 수 있다.

    //로컬 함수
    fun dfs(graph: Graph?) {
        fun dfs(current: Vertex?, visited: Set<Vertex>) {
//            if (!visited.add(current)) return
            for (v in visited)
                dfs(v, visited)
        }
        dfs(null, HashSet())//내부함수
        //this.dfs(null)//외부함수
    }

    //제네릭 함수
    //함수는 지네릭 파라미터를 가질 수 있다. 지네릭 파라미터는 함수 이름 앞에 홑화살괄호를 사용해서 지정한다
    fun <T> singletonList(item: T): List<T> {
        // ...
        return listOf(item)
    }
    fun <T> T.basicToString() : String { // 확장 함수
        return ""
    }

    //제네릭 제한
    //주어진 타입 파라미터에 올 수 있는 모든 가능한 타입 집합은 지네릭 제한 에 제약을 받는다.
    //상위 한계
    //대부분 공통 타입의 제한은 자바의 extends 키워드에 해당하는 상위 한계 이다:
    fun <T : Comparable<T>> sort(list: List<T>) {
        /*...*/
    }

    //콜론 뒤에 지정한 타입이 상위 한계 이다. 오직 Comparable<T> 의 하위타입만 T 에 사용할 수 있다. 다음 예를 보자
//    sort(listOf(1, 2, 3)) // OK. Int는 Comparable<Int>의 하위타입이다.
//    sort(listOf(HashMap<Int, String>())) // 에러: HashMap<Int, String>는
//// Comparable<HashMap<Int, String>>의 하위타입이 아니다.


    //기본 상위 한계는 (지정하지 않은 경우) Any? 이다. 홑화살괄호 안에는 한 개의 상위 한계만 지정할 수 있다. 한 개의 타입 파라미터에 한 개 이상의 상위 한계를 지정해
    //야 하면 별도의 where -절을 사용해야 한다
//    fun <T> cloneWhenGreater(list: List<T>, threshold: T): List<T>
//            where T : Comparable,
//                  T : Cloneable {
//        return list.filter { it > threshold }.map { it.clone() }
//    }





    //내부 클래스
    //inner 로 지정한 클래스는 외부 클래스의 멤버에 접근할 수 있다. 내부 클래스는 외부 클래스의 객체에 대한 레퍼런스를 갖는다
    class Outer {
        private val bar: Int = 1
        inner class Inner {
            fun foo() = bar
        }
    }

    //익명 내부 클래스
//    window.addMouseListener(object: MouseAdapter() {
//        override fun mouseClicked(e: MouseEvent) {
//// ...
//        }
//        override fun mouseEntered(e: MouseEvent) {
//// ...
//        }
//    })

    //객체가 함수형 자바 인터페이스의 인스턴스라면(예를 들어 한 개의 추상 메서드를 가진 자바 인터페이스), 인터페이스 타입을 접두어로 갖는 람다 식을 사용해서 익명 내부
    //객체를 생성할 수 있다:
    //val listener = ActionListener { println("clicked") }





    //꼬리 재귀 함수
    //코틀린은 꼬리 재귀 로 알려진 함수형 프로그래밍 방식을 지원한다. 이는 스택 오버플로우 위험 없이 루프 대신 재귀 함수로 알고리즘을 작성할 수 있도록 한다. 함수에
    //tailrec 수식어가 있고 요구 형식을 충족하면, 컴파일러는 재귀를 최적화해서 빠르고 효율적인 루프 기반 버전으로 바꾼다:
    tailrec fun findFixPoint(x: Double = 1.0): Double
            = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

    //이 코드는 코사인의 fixpoint를 계산한다. 이 코드는 Math.cos 1.0에서 시작해서 더 이상 값이 바뀌지 않을 때까지 반복해서 호출하며 결과로 0.7390851332151607을
    //생성한다. 결과 코드는 다음의 전통적인 방식과 동일하다
    private fun findFixPoint(): Double {
        var x = 1.0
        while (true) {
            val y = Math.cos(x)
            if (x == y) return y
            x = y
        }
    }

    //tailrec 수식어가 가능하려면, 함수는 수행하는 마지막 연산으로 자기 자신을 호출해야 한다. 재귀 호출 이후에 다른 코드가 있으면 꼬리 재귀를 사용할 수 없다. 그리
    //고 try/catch/finally 블록 안에서 꼬리 재귀를 사용할 수 없다. 현재 꼬리 재귀는 JVM 백엔드에서만 지원한다.



    //고차 함수와 람다
    //고차함수
    //고차 함수는 함수를 파라미터로 받거나 함수를 리턴하는 함수이다. 고차 함수의 좋은 예로 lock() 이 있다. 이 함수는 Lock 객체와 함수를 받아서, Lock을 얻고, 그 함
    //수를 실행하고, Lock을 해제한다:
    fun <T> lock(lock: Lock, body: () -> T): T {
        lock.lock()
        try {
            return body()
        }
        finally {
            lock.unlock()
        }
    }
    //위 코드를 보자. body 는 함수 타입 인 () -> T 이다. 이 함수 타입은 파라미터가 없고 T 타입을 리턴하는 함수이다. lock 으로 보호하는 동안 try 블록에
    //서 이 함수를 호출하고 그 결과를 lock() 함수의 결과로 리턴한다.
    //lock() 을 호출할 때 인자로 다른 함수를 전달할 수 있다( 함수 레퍼런스 참고):
//    fun toBeSynchronized() = sharedResource.operation()
//    val result = lock(lock, ::toBeSynchronized)

    //보통 더 간편한 방법은 람다 식 을 전달하는 것이다:
    //val result = lock(lock, { sharedResource.operation() })


    //람다 식에 대한 자세한 내용은 아래에서 설명한다 . 하지만 이 절을 계속하기 위해 간단하게 람다 식의 개요를 정리했다.
    //람다 식은 항상 증괄호로 둘러 싼다.
    //-> 앞에 파라미터를 선언한다. (파라미터 타입은 생략할 수 있다.)
    //-> 뒤에 몸체가 온다(몸체가 존재할 때).
    //코틀린에서 함수의 마지막 파라미터가 함수면, 괄호 밖에서 람다 식을 인자로 전달할 수 있다.
//    lock (lock) {
//        sharedResource.operation()
//    }


    //고차 함수의 다른 예는 map() 이다:
    fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this)
            result.add(transform(item))
        return result
    }
    //이 함수는 다음과 같이 부를 수 있다
    //val doubled = ints.map { value -> value * 2 }

    //함수를 호출할 때 인자가 람다뿐이면 괄호를 완전히 생략할 수 있다.
    //it : 단일 파라미터의 암묵적 이름
    //다른 유용한 한 가지 규칙은 함수 리터럴의 파라미터가 한 개면, ( -> 를 포함한) 파라미터 선언을 생략할 수 있고, 파라미터 이름이 it 이 된다는 것이다
    //ints.map { it * 2 }
    //strings.filter { it.length == 5 }.sortedBy { it }.map { it.toUpperCase() }

    //사용하지 않는 변수의 밑줄 표기
    //map.forEach { _, value -> println("$value!") }



    //인라인 함수
    //람다 식과 익명 함수
    //람다 식 또는 익명 함수는 함수 선언 없이 바로 식으로 전달한 함수인 "함수 리터럴"이다. 다음 예를 보자:
    //max(strings, { a, b -> a.length < b.length })

    //max 함수는 고차 함수로서 두 번째 인자로 함수 값을 취한다. 두 번째 인자는 자체가 함수인 식으로 함수 리터럴이다. 다음 함수와 동등하다
    fun compare(a: String, b: String): Boolean = a.length < b.length



    //함수 타입
    //함수가 다른 함수를 파라미터로 받을 때, 파라미터를 위한 함수 타입을 지정해야 한다. 예를 들어, 앞서 언급한 max 함수는 다음과 같이 정의했다
    fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
        var max: T? = null
        for (it in collection)
            if (max == null || less(max, it))
                max = it
        return max
    }


    /*
    less 파라미터는 (T, T) -> Boolean 타입인데, 이 함수는 T 타입의 파라미터가 두 개이고 Boolean 을 리턴한다. less 는 첫 번째가 두 번째보다
    작으면 true를 리턴한다.
    위 코드의 4번째 줄에서 less 를 함수로 사용한다. T 타입의 두 인자를 전달해서 less 를 호출한다.
    함수 타입을 위와 같이 작성하거나 또는 각 파라미터의 의미를 문서화하고 싶다면 파라미터에 이름을 붙일 수 있다.
     */
    //val compare: (x: T, y: T) -> Int = ...

    //함수 타입을 null 가능 변수로 선언하려면 전체 함수 타입을 괄호로 감싸고 그 뒤에 물음표룔 붙인다:
    //var sum: ((Int, Int) -> Int)? = null




    //람다 식 구문
    //람다 식(즉 함수 타입의 리터럴)의 완전한 구문 형식은 다음과 같다
    val sum = { x: Int, y: Int -> x + y }
    //모든 선택 사항을 생략하면 람다 식은 다음과 같이 보인다
    val sum2: (Int, Int) -> Int = { x, y -> x + y }

    //람다 식이 파라미터가 한 개인 경우는 흔하다. 코틀린이 시그너처 자체를 알아낼 수 있다면, 한 개인 파라미터를 선언하지 않는 것이 가능하며 it 이라는 이름으로 파라미
    //터를 암묵적으로 선언한다:
    //ints.filter { it > 0 } // 이 리터럴은 '(it: Int) -> Boolean' 타입이다
    /*
    ints.filter {
        val shouldFilter = it > 0
        shouldFilter
    }
    ints.filter {
        val shouldFilter = it > 0
        return@filter shouldFilter
    }
     */

    //익명 함수
    //fun(x: Int, y: Int): Int = x + y
    //fun(x: Int, y: Int): Int {
    //    return x + y
    //}

    //파라미터와 리턴 타입은 일반 함수와 같은 방법으로 지정한다. 문맥에서 파라미터 타입을 유추할 수 있다면 생략할 수 있다는 점은 다르다.
    //ints.filter(fun(item) = item > 0)

    /*
    익명 함수에 대한 리턴 타입 추론은 보통 함수와 동일하게 동작한다. 몸체가 식인 익명 함수에 대한 리턴 타입은 자동으로 유추한다. 블록 몸체를 가진 익명 함수의 경우 명시
    적으로 리턴 타입을 지정해야 한다(아니면 Unit 으로 가정).
    익명 함수 파라미터는 항상 괄호 안에 전달해야 한다. 괄호 밖에 함수 전달을 허용하는 간단 구문은 람다 식에 대해서만 동작한다.
    람다 식과 익명 함수 간의 한 가지 다른 차이점은 비로컬 리턴 의 동작 방식이다. 라벨이 없는 return 문장은 항상 fun 키워드로 선언한 함수에서 리턴한다. 이는 람다
    식의 return 은 둘러싼 함수를 리턴하는 반면 익명 함수의 return 은 익명 함수 자체로부터 리턴한다는 것을 의미한다.
     */


    //클로저
    //람다 식 또는 익명 함수(그리고 로컬 함수 와 오브젝트 식 )은 그것의 _클로저_에, 즉 외부 범위에 선언된 변수에 접근할 수 있다. 자바와 달리 클로저에 캡처한 변수를 수정
    //할 수 있다:
    //var sum = 0
    //ints.filter { it > 0 }.forEach {
    //    sum += it
    //}
    //print(sum)



    //리시버를 가진 함수 리터럴
    //코틀린은 _리시버 객체_를 가진 함수 리터럴을 호출하는 기능을 제공한다. 함수 리터럴 몸체 안에서 별도 한정자 없이 리시버 객체의 메서드를 호출할 수 있다. 이는 함수 몸
    //체 안에서 리서버 객체의 멤버에 접근할 수 있는 확장 함수와 유사하다. 이의 중요한 예제 중 하나는 타입에 안전한 그루비 방식 빌더 이다.
    //이런 함수 리터럴의 타입은 리시버를 가진 함수 타입이다:
    //sum : Int.(other: Int) -> Int

    //마치 리시버 객체에 메서드가 존재하는 것처럼 함수 리터럴을 호출할 수 있다: 1.sum(2)


    //익명 함수 구문은 함수 리터럴의 리시버 타입을 직접 지정할 수 있게 한다. 이는 리시버를 사용한 함수 타입의 변수를 선언하고 나중에 사용해야 할 때 유용하다
    val sum3 = fun Int.(other: Int): Int = this + other


    /*
    리시버 타입을 갖는 함수의 비-리터럴 값을 할당하거나 추가로 리시버 타입의 첫 번째 파라미터를 가진 걸로 기대하는 일반 함수의 인자로 전달할 수 있다. 예를 들어,
    String.(Int) -> Boolean 과 (String, Int) -> Boolean 은 호환된다

    val represents: String.(Int) -> Boolean = { other -> toIntOrNull() == other }
    println("123".represents(123)) // true
    fun testOperation(op: (String, Int) -> Boolean, a: String, b: Int, c: Boolean) =
            assert(op(a, b) == c)
    testOperation(represents, "100", 100, true) // OK
     */

    /*class HTML {
        fun body() { ... }
    }
    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML() // 리시버 객체 생성
        html.init() // 리시버 객체를 람다에 전달
        return html
    }
    html { // 리시버로 시작하는 람다
        body() // 리시버 객체에 대한 메서드 호출
    }*/
}

//멤버 함수
class Sample() {
    fun foo() { print("Foo") }
}

//중첩 클래스와 내부 클래스
class Outers {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}


fun main(args: Array<String>) {

    val f = FunctionAndLambda()
    val result = f.double(2)
    f.foo(baz = 1) // 기본 값 bar = 0을 사용한다
    //하지만 함수 호출시 마지막 인자를 괄호 밖에 람다 로 전달하면, 기본 파라미터에 값을 전달하지 않는 것을 허용한다:
    f.foo { println("hello") }
    //기본인자 사용해서 호출
    f.reformat("str")
    //그리고 모든 인자가 필요한 것이 아니면 다음과 같이 작성할 수 있다:
    f.reformat("str", wordSeparator = '_')

    //위치 기반 인자와 이름 가진 인자를 함께 사용해서 함수를 호출할 때, 모든 위치 기반 인자는 첫 번째 이름 가진 인자보다 앞에 위치해야 한다. 예를 들어, f(1, y = 2)
    //는 허용하지만 f(x = 1, 2) 는 허용하지 않는다.

    // 펼침(spread)
    f.foo(strings = *arrayOf("a", "b", "c"))
    f.foo(strings = "a") // 단일 값에는 필요 없다

    //가변 인자 (Varargs)
    val list1 = f.asList(1, 2, 3)

    //가변인자 쪽 펼처서넣기  *
    val a = arrayOf(1, 2, 3)
    val list2 = f.asList(-1, 0, *a, 4)

    //중위부호
    1 shl 2
    1.shl(2)


    //멤버함수
    Sample().foo() // Sample 클래스의 인스턴스를 생성하고 foo를 호출


    //제네릭 함수
    //지네릭 함수를 호출하려면 호출 위치에서 함수 이름 뒤에 타입 인자를 지정한다
    val l = f.singletonList<Int>(1)


    //중첩 클래스와 내부 클래스
    val demo = Outers.Nested().foo() // == 2

    //내부 클래스
    val demo1 = FunctionAndLambda.Outer().Inner().foo() // == 1
}
