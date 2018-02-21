package com.chapter1

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

}