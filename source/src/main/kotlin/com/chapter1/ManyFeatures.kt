package com.chapter1

import kotlin.reflect.KClass

class ManyFeatures{
    //분리 선언(Destructuring Declarations)
    /*
    component1() 과 component2() 함수는 코틀린에서 광범위하게 사용하는 관례 규칙(principle of conventions) 예다( + 와 * , for -루프 등의 연산자
    를 보자). 필요한 개수의 component 함수를 호출할 수만 있으면 무엇이든 분리 선언의 오른쪽에 위치할 수 있다. 물론 component3() 과 component4() 등이
    존재할 수 있다.
     */
    operator fun component1() = "kk"
    operator fun component2() = 1


}
//에제: 함수에서 두 값 리턴하기
//함수에서 두 값을 리턴하고 싶다고 하자. 예를 들어 어떤 종류의 결과 객체와 상태를 리턴해야 한다고 가정하자. 코틀린에서 이를 간략하게 처리하는 방법은 데이터 클래스
//를 선언하고 그 클래스의 인스턴스를 리턴하는 것이다:
//데이터 클래스는 자동으로 componentN() 함수를 선언하므로 여기서 분리 선언이 작동한다.
//주의 : 위 function() 에서 표준 클래스 Pair 를 사용한 Pair<Int, Status> 를 리턴해도 된다. 하지만 데이터에 적당한 이름을 붙이는게 더 나을 때가있다
data class Result(val result: Int, val status: String)

fun main(args: Array<String>) {
    //분리 선언(Destructuring Declarations)

    val (name, age) = ManyFeatures()
    //for ((a, b) in collection) { ... }


    //에제: 함수에서 두 값 리턴하기
    val (result, status) = Result(1,"g")



    //예제: 분리 선언과 맵
    /*
    이게 작동하려면 다음을 충족해야 한다
    맵에서 값의 시퀀스로 제공하는 iterator() 함수를 제공한다.
    각 요소를 component1() 과 component2() 함수를 제공하는 Pair로 제공한다.
     */
    val map = mapOf("tot" to "aa", "tot1" to "aa1", "tot2" to "aa2")
    for ((key, value) in map) {
    // 키와 값으로 무엇을 함
    }


    //람다에서의 분리 선언 (1.1부터)
    //람다 파라미터에도 분린 선언 구문을 사용할 수 있다. 람다가 Pair 타입의 파라미터를 가지면(또는 Map.Entry , 또는 적당한 componentN 함수를 가진 다른
    //타입), 괄호 안에 Pair 타입 파라미터를 넣는 대신 분리한 새 파라미터를 사용할 수 있다:
    map.mapValues { entry -> "${entry.value}!" }
    map.mapValues { (key, value) -> "$value!" }



    //두 파라미터를 선언하는 것과 한 개 파라미터 대신에 분리한 쌍을 선언하는 것의 차이를 보자
//    { a -> ... } // 한 개 파라미터
//    { a, b -> ... } // 두 개 파라미터
//    { (a, b) -> ... } // 분리한 쌍
//    { (a, b), c -> ... } // 분리한 쌍과 다른 파라미터


    //분리한 파라미터의 컴포넌트를 사용하지 않으면, 이름 대신에 밑줄로 대체할 수 있다
    map.mapValues { (_, value) -> "$value!" }
//    분리한 파라미터 전체 타입이나 일부 컴포넌트의 타입을 개별적으로 지정할 수 있다:
//    map.mapValues { (_, value): Map.Entry<Int, String> -> "$value!" }
    map.mapValues { (_, value: String) -> "$value!" }









    //콜렉션: List, Set, Map
    /*
    많은 언어와 달리 코틀린은 변경 가능한 콜렉션과 불변 콜렉션을 구분한다(리스트, 집합, 맵 등). 콜렉션을 수정할 수 있는지 정확하게 제어하는 것은 버그를 줄이고 좋은
    API를 설계할 때 유용하다.
    수정 가능한 콜렉션의 읽기 전용 _뷰_와 실제로 불변인 콜렉션의 차이는 미리 이해하는 것이 중요하다. 둘다 생성하기 쉽지만 타입 시스템은 이 차이를 표현하지 않는다. 따
    라서 (만약 관련이 있다면) 그것을 추적하는 것은 당신의 몫이다.
    코틀린의 List<out T> 타입은 size , get 과 같은 읽기 전용 오퍼레이션을 제공하는 인터페이스이다. 자바처럼 Iterable<T> 를 상속한
    Collection<T> 을 상속한다. 리스트를 수정하는 메서드는 MutableList<T> 에 있다. 이 패턴은 또한 Set<out T>/MutableSet<T> 과 Map<K,
    out V>/MutableMap<K, V> 에도 적용된다
     */
    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
    val readOnlyView: List<Int> = numbers
    println(numbers) // "[1, 2, 3]" 출력
    numbers.add(4)
    println(readOnlyView) // prints "[1, 2, 3, 4]"
//    readOnlyView.clear() // -> 컴파일 안 된다
    val strings = hashSetOf("a", "b", "c", "c")
    assert(strings.size == 3)





//    코틀린은 리스트나 집합을 생성하기 위한 전용 구문 구성 요소가 없다. listOf() , mutableListOf() , setOf() , mutableSetOf() 와 같은 표준
//    라이브러리에 있는 메서드를 사용해서 생성한다.
//            성능이 중요하지 않은 코드에서는 간단한 이디엄 인 mapOf(a to b, c to d) 를 사용해서 맵을 생성할 수 있다.
//    readOnlyView 변수는 같은 리스트를 가리키고 하부 리스트를 변경할 때 바뀐다는 것에 주목하자. 만약 리스트에 존재하는 유일한 참조가 읽기 전용 타입이면, 콜렉션
//    이 완전히 불변이라고 간주할 수 있다. 그런 콜렉션을 만드는 간단한 방법은 다음과 같다:
    val items = listOf(1, 2, 3,4)






//    현재 listOf 메서드는 배열 리스트를 사용해서 구현하는데, 앞으로는 리스트가 바뀌지 않는다는 사실을 이용해서 메모리에 더 효율적인 완전히 불변인 콜렉션 타입을
//    리턴하도록 구현할 것이다.
//            읽기 전용 타입은 공변 이다. 이는 Rectangle이 Shape를 상속한다고 가정할 때 List<Rectangle> 를 List<Shape> 에 할당할 수 있다는 것을 의미한다. 변
//    경가능 콜렉션 타입에서는 이를 허용하지 않는다. 왜냐면 런타임에 실패가 발생할 수 있기 때문이다.
//    때때로 특정 시점의 콜렉션에 대해 변경되지 않는 것을 보장하는 스냅샷을 호출자에게 리턴하고 싶을 때가 있다:
    class Controller {
        private val _items = mutableListOf<String>()
        val items: List<String> get() = _items.toList()
    }
//    toList 확장 메서드는 단지 리스트 항목을 중복해서 갖는 리스트를 생성하며, 리턴한 리스트가 결코 바뀌지 않는다는 것을 보장한다.
//    리스트나 맵은 유용하게 쓸 수 있는 다양한 확장 메서드를 제공하는데, 이들 메서드에 익숙해지면 좋다:
    items.first() == 1
    items.last() == 4
    items.filter { it % 2 == 0 } // [2, 4]를 리턴
    val rwList = mutableListOf(1, 2, 3)
    rwList.requireNoNulls() // [1, 2, 3]을 리턴
    if (rwList.none { it > 6 }) println("No items above 6") // "No items above 6" 출력
    val item = rwList.firstOrNull()
//    … 또한 sort, zip, fold, reduce 등 있었으면 하는 유틸리티도 제공한다.
//    맵은 다음 패턴을 따른다. 다음과 같이 쉽게 생성하고 접근할 수 있다:

    val readWriteMap = hashMapOf("foo" to 1, "bar" to 2)
    println(readWriteMap["foo"]) // "1"을 출력
    val snapshot: Map<String, Int> = HashMap(readWriteMap)





    //범위
//    범위(range) 식은 연산자 형식이 .. 인 rangeTo 함수로 구성한다. .. 은 in 과 !in 을 보완한다. 모든 비교가능한 타입에 대해 범위를 정의할 수 있는데, 정
//    수 기본 타입의 경우 구현을 최적화한다. 다음은 범위의 사용 예이다
//    if (i in 1..10) { // 1 <= i && i <= 10과 동일
//        println(i)
//    }

//    정수 타입 범위( IntRange , LongRange , CharRange )는 반복할 수 있는 추가 특징이 있다. 컴파일러는 추가 오버헤드없이 정수 타입 범위를 동일한 자바의
//    인덱스 기반 for -루프로 변환한다:
    for (i in 1..4) print(i) // "1234" 출력
    for (i in 4..1) print(i) // 아무것도 출력하지 않음



//    숫자를 역순으로 반복하려면? 간단하다. 표준 라이브리에 있는 downTo() 함수를 사용하면 된다:
    for (i in 4 downTo 1) print(i) // "4321" 출력
//    1씩 간격이 아닌 다른 간격으로 숫자를 반복할 수 있나? 물론 가능하다. step() 을 사용하면 된다:
    for (i in 1..4 step 2) print(i) // "13" 출력
    for (i in 4 downTo 1 step 2) print(i) // "42" 출력
//    마지막 요소를 포함하지 않는 범위를 생성하려면 until 함수를 사용한다:
    for (i in 1 until 10) { // i in [1, 10), 10은 제외
        println(i)
    }




    //동작방식
//    범위는 라이브러리의 공통 인터페이스인 ClosedRange<T> 를 구현한다.
//            ClosedRange<T> 는 , 비교가능한 타입에 정의한 것으로 수학적 의미로 닫힌 구간을 표시한다. 이는 두 개의 끝점인 start 와 endInclusive 을 갖는다. 이
//    두 점은 범위에 포함된다. 주요 연산자인 contains 는 in / !in 연산자에서 주로 사용한다.
//            정수 타입 Progression( IntProgression , LongProgression , CharProgression )은 산술적 증가를 표현한다. Progression은 first 요소,
//    last , 0이 아닌 step 으로 정의한다. 첫 번째 요소는 first 이고 이어지는 요소는 앞 요소에 step 을 더한 값이 된다. last 요소는 Progression이 비어
//    서 더 이상 없을 때까지 반복을 통해 도달한다.
//    Progression은 Iterable<N> 의 하위 타입으로 N 은 각각 Int , Long 또는 Char 이다. 따라서 Progression을 for 나 map , fileter 와 같
//    은 함수에서 사용할 수 있다. Progression 에 대한 반복은 자바/자바스크립트의 인덱스 기반 for -루프와 동일하다:
//    for (int i = first; i != last; i += step) {
//         ...
//    }
//    정수 타입에서 .. 연산자는 ClosedRange<T> 와 *Progression 를 구현한 객체를 생성한다. 예를 들어, IntRange 는 ClosedRange<Int> 를
//    구현했고, IntProgression 를 상속하므로 IntProgression 에 정의한 모든 오퍼레이션을 IntRange 에서 사용가능하다.. downTo() 와 step()
//    함수의 결과는 항상 *Progression 이다.
//    Progression의 컴페니언 오브젝트가 제공하는 fromClosedRange 함수를 사용해서 Progression을 생성한다:
//    IntProgression.fromClosedRange(start, end, step)
//    Progression의 last 요소는 양수 step 에 대해서는 end 값보다 크지 않은 최댓값을 계산해서 구하고 음수 step 에 대해서는 end 보다 작지 않은 최솟값
//    을 계산해서 구한다. 계산 결과로 얻은 값은 (last - first) % step == 0 이 된다.




    //유틸리티 함수
    //정수 타입의 rangeTo() 연산자는 단순히 *Range 생성자를 호출한다

    //downTo() 모든 정수 타입 쌍에 대해 downTo() 확장 함수를 정의했다. 다음의 두 예를 보자:
    //reversed() reversed() 확장 함수는 각 *Progression 클래스에 존재하며 역순의 Progression을 리턴한다
    //step() *Progression 클래스를 위한 step() 확장 함수를 제공한다, 이 함수는 step 값(함수 파라미터)을 갖는 Progression을 리턴한다. 증분 값은 항상 양수여야
    //하므로, 이 함수는 반복의 방향을 바꿀 수 없다:





    //타입 검사와 변환: 'is'와 'as'

    //is 와 !is 연산자
    //is 연산자를 사용하면 런타임에 객체가 특정 타입을 따르는지 검사할 수 있다. !is 연산자는 반대를 검사한다:
    val obj = "g"
    if (obj is String) {
        print(obj.length)
    }
    if (obj !is String) { // !(obj is String)와 동일
        print("Not a String")
    }
    else {
        print(obj.length)
    }




    //스마트 타입 변환
//    코틀린에서는 많은 경우 명시적인 타입 변환 연산이 필요 없다. 왜냐면 컴파일러가 불변 값에 대해 is -검사와 명시적 타입 변환 을 추적해서 자동으로 필요한 곳에 (안전
//    한) 타입 변환을 넣어주기 때문이다

    fun demo(x: Any) {
        if (x is String) {
            print(x.length) // x를 자동으로 String으로 타입 변환
        }
    }

    //!is 검사를 통해 리턴할 경우 컴파일러는 타입 변환을 안전하게 할 수 있다는 것을 알고, 자동으로 타입 변환을 추가한다:
    if (obj !is String) return
    print(obj.length) // x를 자동으로 String으로 타입 변환


    //또한 && 와 || 의 오른쪽에도 자동 타입 변환을 추가한다:
    // `||`의 오른쪽에서 x를 자동으로 String으로 타입 변환
    if (obj !is String || obj.length == 0) return
// `&&`의 오른쪽에서 x를 자동으로 String으로 타입 변환
    if (obj is String && obj.length > 0) {
        print(obj.length) // x를 자등으로 String으로 타입 변환
    }

    //이런 _스마트 타입 변환_은 when -식 과 while -루프 에서도 동작한다
//    when (obj) {
//        is Int -> print(obj + 1)
//        is String -> print(obj.length + 1)
//        is IntArray -> print(obj.sum())
//    }

    /*
    변수 검사와 사용 사이에 변수가 바뀌지 않는다는 것을 컴파일러가 확신할 수 없으면 스마트 타입 변환이 동작하지 않는다. 구체적으로 다음 규칙에 따라 스마트 타입 변환을
    적용한다:
    val 로컬 변수 - 항상 적용
    val 프로퍼티 - 프로퍼티가 private이거나 internal이거나 프로퍼티를 선언한 같은 모듈에서 검사를 한 경우에 적용. 커스텀 getter를 가진 프로퍼티나 open 프로퍼
    티에는 스마트 변환이 적용되지 않음
    var 로컬 변수 - 변수 검사와 사용 사이에 수정이 없고 변수를 캡처한 람다가 변수를 수정하지 않으면 적용
    var 프로퍼티- 적용하지 않음 (왜냐면 다른 코드가 언제든지 변수를 수정할 수 있기 때문).
     */




    //"불안전" 타입 변환 연산자
    val x: String = obj as String

//    x는 null이 가능 하지 않기 때문에 null 을 String 타입으로 변환할 수 없다. 예를 들어, y 가 null이면 위 코드는 익셉션을 던진다. 자바의 변환 세만틱에 맞추려
//    면, 다음과 같이 변환 연산자의 오른쪽에 null 가능 타입이 와야 한다:
    val x2: String? = obj as String?



    //"안전한" (nullable) 타입 변환 연산자
//    익셉션이 발생하는 것을 피하려면 안전한 타입 변환 연산자인 as? 를 사용할 수 있다. 이 연산자는 실패시 null 을 리턴한다.
    val x3: String? = obj as? String
    //as? 의 오른쪽이 null이 아닌 String 타입이지만 타입 변환 결과는 null 가능 타입이다.



    //this 식
    /*
    현재 _리시버_를 지정하려면 this 식을 사용한다.
    클래스 의 멤버에서 this 는 그 클래스의 현재 객체를 참조한다.
    확장 함수 나 리시버를 가진 함수 리터럴 에서 this 는 점의 왼쪽 편에 전달한 리시버 파라미터를 지정한다.
    this 에 한정자가 없으면 _가장 안쪽을 둘러썬 범위_를 참조한다. 다른 범위의 this 를 참조하려면 _라벨 한정자_를 사용한다.
     */
    //한정된 this
    //외부 범위( 클래스 나 확장 함수 , 또는 라벨이 있는 리시버를 가진 함수 리터럴 )에서 this 에 접근하려면 this@label 형식을 사용한다. 여기서 @label 은
    //this 가 속한 범위를 의미하는 라벨 이다
    class A { // 암묵적으로 @A 라벨 사용
        inner class B { // 암묵적으로 @B 라벨 사용
            fun Int.foo() { // 암묵적으로 @foo 라벨 사용
                val a = this@A // A의 this
                val b = this@B // B의 this
                val c = this // foo()의 리시버인 Int
                val c1 = this@foo // foo()'의 리시버인 Int
                val funLit = lambda@ fun String.() {
                    val d = this // funLit의 리시버
                }
                val funLit2 = { s: String ->
                    // 둘러싼 람다식에 리시버가 없으므로
// foo()의 리시버
                    val d1 = this
                }
            }
        }
    }




    //동등성(Equality)
    //코틀린에는 두 가지 종류의 동등성이 있다:
    //참조 동등성 (두 참조가 동일 객체를 가리킴)
    //구조 동등성 ( equals() 로 검사).
    //참조 동등은 === 오퍼레이션으로 검사한다(반대는 !== 로 검사). a === b 는 a 와 b 가 동일 객체를 가리키는 경우에만 true이다

    //구조 동등
    //구조 동등은 == 오퍼레이션으로 검사한다(반대는 != 로 검사). a == b 와 같은 식을 다음과 같이 바꾼다.

    //a?.equals(b) ?: (b === null)
    //a 가 null 이 아니면 equals(Any?) 함수를 호출하고, a 가 null 이면 b 가 null 인지 검사한다. null 을 직접 비교할 때에는 코드를 최적화할
    //필요가 없다. a == null 을 자동으로 a === null 로 바꾼다.

    //부동 소수점 숫자의 동등
    //동등 비교의 피연산자가 정적으로 (null 가능이거나 아닌) Float 나 Double 임을 알 수 있으면, 부동 소수점 계산을 위한 IEEE 754 표준에 따라 검사한다.
    //이 외의 경우 구조 동등을 사용한다. 이는 표준을 따르지 않으므로 NaN 는 그 자신과 같고, -0.0 은 0.0 과 같지 않다.
    //부동 소수점 비교 를 참고한다.






    //연산자 오버로딩
    //코틀린은 타입에 대해 미리 정의한 연산자의 구현을 제공할 수 있다. 이 연산자는 + 나 * 과 같이 부호 표기가 고정되어 있고 정해진 우선순위 를 갖는다. 연산자를 구현
    //하려면 정해진 이름을 가진 멤버 함수 나 확장 함수 를 해당 타입에 제공한다. 이 타입은 이항 연산자의 왼쪽 타입이고 단항 연산자의 인자 타입이다. 연산자를 오버로딩한 함
    //수는 operator 수식어를 붙여야 한다.
    //이어서 다른 연산자를 위한 연산자 오버로딩을 규정하는 관례를 살펴보자.

    //단항 접두 연사자
    /*
    +a      a.unaryPlus()
    -a      a.unaryMinus()
    !a      a.not()
     */
    //증감 연산자
//    a++     a.inc()
//    a--     a.dec()

    //이항 연산자
    /*in 과 !in 의 절차는 동일하다. 결과만 반대이다
    a + b       a.plus(b)
    a - b       a.minus(b)
    a * b       a.times(b)
    a / b       a.div(b)
    a % b       a.rem(b) , a.mod(b) (deprecated)
    a..b        a.rangeTo(b)
     */

    //in 연산자
    /*
    a in b      b.contains(a)
    a !in b     !b.contains(a)
     */

    //인덱스 기반 접근 연산자
    /*대괄호는 각각 알맞은 개수의 인자를 사용한 get 과 set 호출로 바뀐다
    a[i]                    a.get(i)
    a[i, j]                 a.get(i, j)
    a[i_1, ..., i_n]        a.get(i_1, ..., i_n)
    a[i] = b                a.set(i, b)
    a[i, j] = b             a.set(i, j, b)
    a[i_1, ..., i_n] = b    a.set(i_1, ..., i_n,b)
     */

    //호출 연산자
    /*
    a()             a.invoke()
    a(i)            a.invoke(i)
    a(i, j)         a.invoke(i, j)
    a(i_1, ...,i_n) a.invoke(i_1, ...,i_n)
     */

    //증강 할당(Augmented assignments)
    /*
    a += b          a.plusAssign(b)
    a -= b          a.minusAssign(b)
    a *= b          a.timesAssign(b)
    a /= b          a.divAssign(b)
    a %= b          a.remAssign(b) , a.modAssign(b) (deprecated)
     */

    //동등과 비동등 연산자
    /*
    a == b          a?.equals(b) ?: (b === null)
    a != b          !(a?.equals(b) ?: (b ===null))
     */


    //비교 연산자
    /*
    a > b       a.compareTo(b) > 0
    a < b       a.compareTo(b) < 0
    a >=b       a.compareTo(b) >=0
    a <=b       a.compareTo(b) <=0
     */

    //이 표는 컴파일러가 다음 절차에 따라 처리한다는 것을 보여준다(예로 +a 를 사용했다):
    //a 의 타입을 결정한다. 타입을 T 라고 해 보자.
    //리시버 T 에 대해 파라미터가 없고 operator 이고 이름이 unaryPlus() 인 멤버 함수나 확장 함수를 찾는다.
    //함수가 없거나 모호하면 컴파일 에러가 발생한다.
    //함수가 존재하고 리턴 타입이 R 이면 +a 식의 타입은 R 이다.
    //이 오퍼레이션뿐만 아니라 다른 오퍼레이션은 기본 타입 에 대해 최적화를 하므로 함수 호출에 따른 오버헤드가 발생하지 않는다.
    //다음 예는 단항 빼기 연산자를 오버라이딩하는 방법을 보여준다:
    data class Point(val xx: Int, val yy: Int)
    operator fun Point.unaryMinus() = Point(-xx, -yy)
    val point = Point(10, 20)
    println(-point) // "(-10, -20)" 출력






    //Null 안정성
    //Null 가능 타입과 Not-null 타입
//    코틀린 타입 시스템은 10억 달러 실수 라고 알려진, 코드에서 null을 참조하는 위험을 없애는 데 도움을 준다.
//    자바를 포함한 많은 프로그래밍 언어에서 공통으로 발견되는 실수 중 하나는 null 레퍼런스의 멤버를 참조해서 null 참조 익셉션이 발생하는 것이다. 자바에서는
//    NullPointerException , 줄여서 NPE가 이에 해당한다.
//    코틀린 타입 시스템은 코드에서 NullPointerException 을 제거하는데 도움을 준다. NPE는 다음 상황에서만 가능하다:
//    throw NullPointerException() 를 명시적으로 실행
//            뒤에서 설명한 !! 연산자 사용
//            NPE를 발생하는 외부의 자바 코드 사용
//            초기화와 관련해서 데이터의 일관성이 깨졌을 때(생성자 어딘가에서 초기화되지 않은 this 를 사용할 수 있음)
//    코틀린 타입 시스템은 null 을 가질 수 있는 레퍼런스(nullable 레퍼런스)와 가질 수 없는 레퍼런스(non-null 레퍼런스)를 구분한다. 예를 들어, String 타입 변수는
//    null 을 가질 수 없다:
//    var a: String = "abc"
//    a = null // 컴파일 에러
//    null을 가질 수 있으려면 String? 와 같이 변수를 null 가능 String으로 선언해야 한다:
//    var b: String? = "abc"
//    b = null // ok
//    이제 a 의 프로퍼티에 접근하거나 메서드를 호출할 때 NPE가 발생하지 않고 안전하게 사용할 수 있다는 것을 보장한다:
//    val l = a.length
//    하지만, b 의 동일한 프로퍼티에 접근시도를 하면 안전하지 않으므로 컴파일러 에러가 발생한다:

//    val l = b.length // 에러: 변수 'b'는 null일 수 있다
//    하지만, 여전히 그 프로퍼티에 접근해야 한다면, 이를 위한 몇 가지 방법이 있다.
    //조건으로 null 검사하기
//    첫 번째로 b 가 null 인지 명시적으로 검사하고 두 옵션을 따로 처리할 수 있다:
//    val l = if (b != null) b.length else -1
//    컴파일러는 검사 결과 정보를 추적해서 if 안에서 length 를 호출할 수 있도록 한다. 더 복잡한 조건도 지원한다:
//    if (b != null && b.length > 0) {
//        print("String of length ${b.length}")
//    } else {
//        print("Empty string")
//    }
//    이 코드는 b 가 불변인 경우에만 작동한다(예를 들어, null 검사와 사용 사이에서 수정할 수 없는 로컬 변수이거나 또는 지원 필드가 있고 오버라이딩할 수 없는 val 멤
//            버인 경우). 왜냐면, 그렇지 않을 경우 검사 이후에 b 를 null 로 바꿀 수 있기 때문이다.

    //안전한 호출
    //두 번째는 안전 호출 연산자인 ?. 를 사용하는 것이다:
    //b?.length
    //이 코드는 b 가 null이 아니면 b.length 를 리턴하고 그렇지 않으면 null 을 리턴한다. 이 식의 타입은 Int? 이다.


    //안전 호출은 체인에서 유용하다. Employee 타입의 Bob이 Department에 속할(또는 속하지 않을) 수 있고, department가 또 다른 Employee를 department의 head
    //로 가질 수 있다고 할 때, 존재할 수도 있는 Bod의 department의 head를 구하는 코드를 다음과 같이 작성할 수 있다:
    //bob?.department?.head?.name



    //이 체인은 프로퍼티 중 하나라도 null이면 null 을 리턴한다.
    //non-null 값에만 특정 오퍼레이션을 수행하고 싶다면 안전 호출 연산자를 let 과 함게 사용하면 된다:
    //val listWithNulls: List<String?> = listOf("A", null)
    //for (item in listWithNulls) {
    //item?.let { println(it) } // A를 출력하고 null을 무시
    //}



    //엘비스 연산자
    //null 가능 레퍼런스인 r 에 대해, " r 이 null이 아니면 그것을 사용하고 그렇지 않으면 다른 not-null 값인 x 를 사용한다"는 코드를 다음과 같이 작성할 수 있다:
    //val l: Int = if (b != null) b.length else -1
    //완전한 if -식 외에, 엘비스 연산자인 ?: 를 사용해서 다음과 같이 표현할 수도 있다:
    //val l = b?.length ?: -1

    //?: 의 왼쪽 식이 null이 아니면, 엘비스 연산자는 그것을 리턴하고, 그렇지 않으면 오른쪽 식을 리턴한다. 오른쪽 식은 왼쪽이 null일 경우에만 평가한다.
    //코틀린에서 throw 와 return 은 식이므로, 엘비스 연산자의 오른쪽에 둘을 사용할 수 있다. 이는 함수 인자를 검사할 때 매우 유용하다:
    //fun foo(node: Node): String? {
    //val parent = node.getParent() ?: return null
    //val name = node.getName() ?: throw IllegalArgumentException("name expected")
    //// ...
    //}






    //!! 연산자
    //!! 연산자는 NPE를 선호나는 개발자를 위한 세 번째 옵션이다. b!! 라고 작성하면 b 가 null이 아니면 b를 리턴하고(예를 들어, String ), null이면 NPE를
    //발생한다:


    //val l = b!!.length
    //따라서 NPE를 원한다면 이 연산자를 사용하면 된다. 하지만, NPE는 명시적으로 사용해야 하며 뜻밖의 코드에서 나타나지 않도록 한다.


    //안전한 타입 변환
    //일반 타입 변환은 객체가 지정한 타입이 아니면 ClassCastException 을 발생한다. 타입 변환 방법을 위한 다른 방법은 타입 변환에 실패하면 null 을 리턴하는
    //안전한 타입 변환을 사용하는 것이다:
    val aInt: Int? = x as? Int


    //null 가능 타입의 콜렉션
    //null 가능 타입의 요소를 갖는 콜렉션에서 null이 아닌 요소를 걸러내고 싶다면, filterNotNull 를 사용한다:
    val nullableList: List<Int?> = listOf(1, 2, null, 4)
    val intList: List<Int> = nullableList.filterNotNull()


    //익셉션
    //익셉션 클래스

//    코틀린의 모든 익셉션 클래스는 Throwable 클래스의 자식 클래스이다. 모든 익셉션은 메시지, 스택 트레이스, 선택적인 원인을 포함한다.
//    익셉션 객체를 던지려면, throw -식을 사용한다:
//    throw MyException("Hi There!")
//    익셉션을 잡으려면 try -식을 사용한다:
        try {
            // 어떤 코드
        }
        catch (e: Exception) {
            // 익셉션 처리
        }
        finally {
            // 선택적인 finally 블록
        }
//        catch 블록은 없거나 여러 개 올 수 있다. finally 블록은 생략할 수 있다. 하지만, 최소 한 개의 catch 블록이나 finally 블록은 존재해야 한다:

    //try는 식이다
    //try 는 식이어서 값을 리턴할 수 있다:
    //val a: Int? = try { parseInt(input) } catch (e: NumberFormatException) { null }
    //try -식이 리턴한 값은 try 블록의 마지막 식이거나 catch 블록(또는 블록들의)의 마지막 식이다. finally 블록의 내용은 식의 결과에 영향을 주지 않는다.


    //Checked 익셉션
    //코틀린에는 Checked 익셉션이 없다. 여기에는 여러 이유가 있는데, 간단한 예로 살펴보자.
    //다음은 JDK의 StringBuilder 클래스가 구현한 인터페이스 예이다.
    //Appendable append(CharSequence csq) throws IOException;


    //이 시그너처는 무엇을 말할까? 이것은 어딘가에( StringBuilder , 어떤 종류의 로그, 콘솔 등) 문자열을 추가할 때마다 IOExceptions 을 잡아야 한다고 말한
    //다. 왜? 그것이 IO 연산을 할 수도 있기 때문이다( Writer 도 Appendable 를 구현하고 있다). 이는 모든 곳에 다음과 같이 익셉션을 무시하는 코드를 만든다:
    //try {
    //log.append(message)
    //}
    //catch (IOException e) {
    //// 안전해야 함
    //}






    //코틀린에서 throw 는 식이므로, 식을 사용할 수 있다. 예를 들어, 엘비스 식에서 사용할 수 있다:
    val s = x ?: throw IllegalArgumentException("Name required")

//    throw 식의 타입은 특수 타입인 Nothing 이다. 이 타입은 값을 갖지 않으면 도달할 수 없는 코드를 표시하는 용도로 사용한다. 코드에서 리턴하지 않는 함수를 표시
//    할 때 Nothing 을 사용할 수 있다:
    fun fail(message: String): Nothing {
        throw IllegalArgumentException(message)
    }


//    이 함수를 호출하면, 컴파일러는 호출 이후 실행이 계속되지 않는 것을 알 것이다:
    val s2 = x ?: fail("Name required")
    println(s2)




//    Nothing 타입을 만나는 또 다른 경우는 타입 추론이다. Nothing의 null 가능 타입인 Nothing? 은 정확하게 null 한 가지 값만 가진다. null 을 사용해서 추론
//            할 타입의 값을 초기화하면 더 구체적인 타입을 결정할 수 있는 다른 정보가 없기 때문에 컴파일러는 Nothing? 타입으로 유추한다:
    val s3 = null // 'x'는 `Nothing?` 타입을 가짐
    val l = listOf(null) // 'l'은 `List<Nothing?> 타입을 가짐





    //애노테이션
//    애노테이션은 코드에 메타데이터를 붙이는 방법이다. 클래스 앞에 annotation 수식어를 붙여서 애노테이션을 선언한다:
//    annotation class Fancy


    //애노테이션 클래스에 메타-애노테이션을 붙여서 애노테이션에 추가 속성을 지정할 수 있다:
    //@Target 은 애노테이션을 어떤 요소에(클래스, 함수, 프로퍼티, 식 등) 적용할 수 있는지 지정한다.
    //@Retention 은 애노테이션을 컴파일한 클래스 파일에 보관할지, 런타임에 리플렉션을 통해서 접근할 수 있는지를 지정한다. (기본값은 둘 다 true이다.)
    //@Repeatable 은 한 요소의 같은 애노테이션을 여러 번 적용하는 것을 허용한다.
    //@MustBeDocumented 은 애노테이션이 공개 API에 속하며 생성한 API 문서에 클래스나 메서드 시그너처를 포함시켜야 함을 지정한다.

    //@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
    //AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
    //@Retention(AnnotationRetention.SOURCE)
    //@MustBeDocumented
    //annotation class Fancy


//    @Fancy class Foo {
//        @Fancy fun baz(@Fancy foo: Int): Int {
//            return (@Fancy 1)
//        }
//    }
//    클래스의 주요 생성자에 애노테이션이 필요하면, constructor 키워드를 생성자 선언에 추가하고 그 앞에 애노테이션을 추가해야 한다:
//    class Foo @Inject constructor(dependency: MyDependency) {
//// ...
//    }
//    프로퍼티 접근자에도 애노테이션을 붙일 수 있다:
//    class Foo {
//        var x: MyDependency? = null
//            @Inject set
//    }



    //생성자
    //애노테이션은 파라미터가 있는 생성자를 가질 수 있다
    //annotation class Special(val why: String)
    //@Special("example") class Foo {}
    //허용하는 파라미터 타입은 다음과 같다:
    //자바 기본 타입에 대응하는 타입(Int, Long 등)
    //문자열
    //클래스 ( Foo::class )
    //열거 타입
    //다른 애노테이션
    //위에서 열거한 타입의 배열
    //추가 파라미터는 null 가능 타입일 수 없는데, 왜냐면 JVM은 애노테이션 속성의 값으로 null 을 저장하는 것을 지원하지 않기 때문이다.



    //다른 애노테이션의 파라미터로 애노테이션을 사용할 경우, 해당 애노테이션의 이름에 @ 문자를 접두어로 붙이지 않는다.
    //annotation class ReplaceWith(val expression: String)
    //annotation class Deprecated(
    //val message: String,
    //val replaceWith: ReplaceWith = ReplaceWith(""))
    //@Deprecated("This function is deprecated, use === instead", ReplaceWith("this === other"))
    //애노테이션의 인자로 클래스를 사용하고 싶다면, 코틀린 클래스( KClass )를 사용한다. 코틀린 컴파일러는 이를 자동으로 자바 클래스로 변환하므로, 자바 코드에서 애노테
    //이션과 인자를 사용할 수 있다.
    //import kotlin.reflect.KClass
//    annotation class Ann(val arg1: KClass<*>, val arg2: KClass<out Any?>)
//    @Ann(String::class, Int::class) class MyClass




    //람다
//    람다에도 애노테이션을 사용할 수 있다. 람다의 몸체에 생성한 invoke() 메서드에 적용된다. 병렬 제어를 위해 애노테이션을 사용하는 Quasar 와 같은 프레임워크에
//    서 이를 유용하게 사용한다.
    annotation class Suspendable
    val f = @Suspendable { Thread.sleep(10) }




    //애노테이션 사용 위치 대상
//    프로퍼티나 주요 생성자 파라미터에 애노테이션을 적용할 때, 코틀린 요소에서 여러 자바 요소가 생성될 수 있고 따라서 생성된 자바 바이트코드에는 애노테이션이 여러 위
//    치에 붙을 수 있다. 애노테이션을 정확하게 어떻게 생성할지 지정하려면 다음 구문을 사용한다:
//    class Example(@field:Ann val foo, // 자바 필드에 적용
//                  @get:Ann val bar, // 자바 getter에 적용
//                  @param:Ann val quux) // 자바 생성자 파라미터에 적용
//    파일에 애노테이션을 적용하기 위해 같은 구문을 사용할 수 있다. 이를 위해 파일의 최상단에 패키지 디렉티브 이전에 또는 기본 패키지면 모든 임포트 이전에 적용 대상으로
//    file 을 가진 애노테이션을 붙인다:
//    @file:JvmName("Foo")
//    package org.jetbrains.demo
//    동일 대상에 여러 애노테이션을 붙일 경우 대상 뒤의 대괄호에 모든 애노테이션을 위치시켜서 대상을 반복하는 것을 피할 수 있다:
//    class Example {
//        @set:[Inject VisibleForTesting]
//        var collaborator: Collaborator
//    }
//    지원하는 사용 위치 대상은 다음과 같다:
//    file
//    property (이 대상을 가진 애노테이션은 자바에는 보이지 않는다)
//    field
//    get (프로퍼티 getter)
//    set (프로퍼티 setter)
//    receiver (확장 함수나 프로퍼티의 리시버 파라미터)
//    param (생성자 파라미터)
//    람다
//    애노테이션 사용 위치 대상
//    —
//    setparam (프로퍼티 setter 파라미터)
//    delegate (위임 프로퍼티의 위임 인스턴스를 보관하는 필드)
//    확장 함수의 리시버 파라미터에 애노테이션을 붙이려면 다음 구문을 사용한다:
//    fun @receiver:Fancy String.myExtension() { }
//    사용 위치 대상을 지정하지 않으면, 사용할 애노테이션의 @Target 애노테이션에 따라 대상을 선택한다. 적용 가능한 대상이 여러 개면, 다음 목록에서 먼저 적용가능한
//    대상을 사용한다:
//    param
//    property
//    field



    //자바 애노테이션
    //자바 애노테이션은 코틀린과 100% 호환된다:

    //리플렉션
    /*
    리플렉션은 란타임에 프로그램의 구조를 볼 수 있게 해주는 라이브러리와 언어 특징이다. 코틀린에서 함수와 프로퍼티는 1급이며, 이 둘의 내부를 알아내는 것은(예, 런타임
    에 이름, 프로퍼티 타입이나 함수를 알아내는 것) 함수형 또는 리액티브 방식을 간단하게 사용하는 것과 밀접하게 관련있다.
    자바에서 리플렌션 특징을 사용하는데 필요한 런타임 컴포넌트는 별도 JAR 파일( kotlin-reflect.jar )로 제공한다. 이를 통해 리플렉션 특징을 사용
    하지 않는 어플리케이션이 필요로 하는 런타임 라이브러리의 크기를 줄였다. 리플렉션을 사용한다면 그 jar 파일을 프로젝트의 클래스패스에 추가해야 한다.
     */


    //클래스 레퍼런스
//    가장 기본적인 리플렉션 특징은 코틀린 클래스에 대한 런타임 레퍼런스를 구하는 것이다. 정적으로 알려진 코틀린 클래스에 대한 레퍼런스를 얻으려면 class 리터럴 구문을
//    사용하면 된다:
    val c = MyClass::class
    //레퍼런스의 값은 KClass 타입이다.
    //코틀린 클래스 레퍼런스는 자바 클래스 레퍼런스와 같지 않다. 자바 클래스 레퍼런스를 구하려면 KClass 인스턴스의 .java 프로퍼티를 사용한다.
    //val widget: Widget = ...
    //assert(widget is GoodWidget) { "Bad widget: ${widget::class.qualifiedName}" }
    //예를 들어, 위 코드는 리시버 식의 타입( Widget )에 상관없이 GoodWidget 또는 BadWidget 인스턴스의 클래스에 대한 레퍼런스를 구한다.



    //함수 레퍼런스
    //다음과 같이 이름 가진 함수를 선언했다고 하자:
//    fun isOdd(x: Int) = x % 2 != 0
//    isOdd(5) 와 같이 쉽게 함수를 바로 호출할 수 있지만, 또한 함수를 값으로 다른 함수에 전달할 수 있다. 이를 위해 :: 연산자를 사용한다:
//    val numbers = listOf(1, 2, 3)
//    println(numbers.filter(::isOdd)) // [1, 3] 출력
//    여기서 ::isOdd 는 함수 타입 (Int) -> Boolean 의 값이다.
//    :: 는 문백을 통해 원하는 타입을 알 수 있을 때, 오버로딩한 함수에서도 사용할 수 있다. 다음 예를 보자:
//    fun isOdd(x: Int) = x % 2 != 0
//    fun isOdd(s: String) = s == "brillig" || s == "slithy" || s == "tove"
//    val numbers = listOf(1, 2, 3)
//    println(numbers.filter(::isOdd)) // isOdd(x: Int)를 참조
//    또는 변수에 명시적으로 타입을 지정해서 메서드 레퍼런스를 저장할 때 필요한 문맥을 제공할 수 있다:
//    val predicate: (String) -> Boolean = ::isOdd // isOdd(x: String)를 참고
//    클래스 멤버나 확장 함수를 사용해야 한다면 한정자를 붙여야 한다. 예를 들어, String::toCharArray 는 String : String.() -> CharArray 타입
//    을 위한 확장 함수를 제공한다.
//            다음 함수를 보자:




    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
        return { x -> f(g(x)) }
    }
//    이 함수는 파라미터로 전달한 두 함수를 조합한다( compose(f, g) = f(g(*)) ). 이제 이 함수에 호출가능한 레퍼런스를 적용할 수 있다:
//    fun length(s: String) = s.length
//    val oddLength = compose(::isOdd, ::length)
//    val strings = listOf("a", "ab", "abc")
//    println(strings.filter(oddLength)) // "[a, abc]" 출력


    //프로퍼티 참조
    //코틀린에서 1급 객체로서 프로퍼티에 접근할 때에도 :: 연산자를 사용한다:
//    var x6 = 1
//    fun main(args: Array<String>) {
//        println(::x6.get()) // "1" 출력
//        ::x6.set(2)
//        println(x) // "2" 출력
//    }




    //::x 식은 KProperty<Int> 타입의 프로퍼티 객체로 평가하는데 이는 get() 을 사용해서 프로퍼티의 값을 읽거나 name 프로퍼티를 사용해서 프로퍼티 이
    //름을 가져올 수 있도록 해 준다. 더 자세한 정보는 KProperty 클래스 문서 를 참고한다.
    //수정 가능한 프로퍼티, 예를 들어 var y = 에 대해 ::y 는 set() 함수를 가진 KMutableProperty<Int> 타입의 값을 리턴한다.
    //파라미터가 없는 함수가 필요한 곳에 프로퍼티 레퍼런스를 사용할 수 있다:
    //val strs = listOf("a", "bc", "def")
    //println(strs.map(String::length)) // [1, 2, 3] 출력
    //클래스 멤버인 프로퍼티에 접근할 때에는 클래스로 한정한다:
    //class A(val p: Int)
    //fun main(args: Array<String>) {
    //val prop = A::p
    //println(prop.get(A(1))) // "1" 출력
    //}
    //확장 프로퍼티의 경우:
    //val String.lastChar: Char
    //get() = this[length - 1]
    //fun main(args: Array<String>) {
    //println(String::lastChar.get("abc")) // "c" 출력
    //}



    //타입 별칭
//    타입 별칭은 타입을 위한 다른 이름을 제공한다. 타입 이름이 너무 길면 더 짧은 이름을 추가해서 그 이름을 대신 사용할 수 있다.
//    이는 긴 지네릭 타입 이름을 짧게 만들 때 유용하다. 예를 들어, 콜렉션 타입을 축약할 때 사용한다.
//    typealias NodeSet = Set<Network.Node>
//    typealias FileTable<K> = MutableMap<K, MutableList<File>>
//    함수 타입을 위한 다른 별칭을 제공할 수 있다:
//    typealias MyHandler = (Int, String, Any) -> Unit
//    typealias Predicate<T> = (T) -> Boolean
//    내부와 중첩 클래스를 위한 새 이름을 가질 수 있다:
//    class A {
//        inner class Inner
//    }
//    class B {
//        inner class Inner
//    }
//    typealias AInner = A.Inner
//    typealias BInner = B.Inner
//    타입 별칭이 새 타입을 추가하는 것은 아니다. 대응하는 기저 타입은 동일하다. 코드에 typealias Predicate<T> 를 추가하고 Predicate<Int> 를 사용하
//    면 코틀린 컴파일러는 이를 (Int) -> Boolean 로 확장한다. 그래서 일반 함수 타입이 필요한 곳에 별칭 타입 변수를 전달할 수 있으며 반대도 가능하다:
//    typealias Predicate<T> = (T) -> Boolean
//    fun foo(p: Predicate<Int>) = p(42)
//    fun main(args: Array<String>) {
//        val f: (Int) -> Boolean = { it > 0 }
//        println(foo(f)) // "true" 출력
//        val p: Predicate<Int> = { it > 0 }
//        println(listOf(1, -2).filter(p)) // "[1]" 출력
//    }
}