package com.chapter1

/*
타입 비트 크기
Double 64
Float 32
Long 64
Int 32
Short 16
Byte 8
 */

class Basic(){
    //숫자 상수의 가독성을 높이기 위해 밑줄을 사용할 수 있다:
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 999_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010

    fun check(){
        val a: Int = 10000
        print(a === a) // 'true' 출력
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        print(boxedA === anotherBoxedA) // !!!'false' 출력!!!
        //하지만 값의 동등함은 유지한다:
    }
    fun check1(){
        val a: Int = 10000
        print(a == a) // 'true' 출력
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        print(boxedA == anotherBoxedA) // 'true' 출력

        //명시적 변환
        //표현이 다르므로 작은 타입이 큰 타입의 하위 타입은 아니다. 하위 타입이 된다면 다음과 같은 문제가 발생한다:
        // 가상의 코드로, 실제로는 컴파일되지 않음:
//        val a1: Int? = 1 // 박싱된 Int (java.lang.Integer)
//        val b1: Long? = a1 // 자동 변환은 박싱된 Long을 생성 (java.lang.Long)
//        print(a1 == b1) // 놀랍게도 이는 "false"를 리턴한다! 왜냐면 Long의 equals()은 비교 대상도 Long인지 검사하기 때문이다


        //동일성뿐만 아니라 동등함조차 모든 곳에서 나도 모르게 사라지게 된다.
        //이런 이유로 작은 타입을 큰 타입으로 자동으로 변환하지 않는다. 이는 명시적 변환없이 Byte 타입 값을 Int 변수에 할당할 수 없음을 뜻한다.
        //val b1: Byte = 1 // OK, 리터럴은 정적으로 검사함
        //val i: Int = b1 // ERROR


        //명시적으로 숫자를 넓히는 변환을 할 수 있다:
//        val i: Int = b.toInt() // OK: 명시적으로 넓힘
//        모든 숫자 타입은 다음 변환을 지원한다:
        //toByte(): Byte
        //toShort(): Short
        //toInt(): Int
        //toLong(): Long
        //toFloat(): Float
        //toDouble(): Double
        //toChar(): Char
        val l = 1L + 3 // Long + Int => Long
        val x = (1 shl 2) and 0x000FF000

        //bit연산
//        다음은 전체 비트 연산자 목록이다( Int 와 Long 에서만 사용 가능):
//        shl(bits) – 부호있는 왼쪽 시프트 (자바의 << )
//        shr(bits) – 부호있는 오른쪽 시프트 (자바의 >> )
//        ushr(bits) – 부호없는 오른쪽 시프트 (자바의 >>> )
//        and(bits) – 비트 AND
//        or(bits) – 비트 OR
//        xor(bits) – 비트 XOR
//        inv() – 비트 역

        //부동소수 비교
        /*
            이 절에서는 실수에 대한 연산을 설명한다:
            동등함 비교: a == b 와 a != b
            비교 연산자: a < b , a > b , a <= b , a >= b
            범위 생성과 검사: a..b , x in a..b , x !in a..b
            피연산자 a 와 b 가 정적으로 Float 나 Double 이거나 또는 이에 해당하는 null 가능 타입일 때(타입을 선언하거나 추정하거나 또는 스마트 변환 의 결과), 숫
            자나 범위에 대한 연산은 부동소수 연산에 대한 IEEE 754 표준을 따른다.
            하지만 범용적인 용례를 지원하고 완전한 순서를 제공하기 위해, 피연산자가 정적으로 부동소수점 타입이 아니면(예 Any , Comparable<...> , 타입 파라미터) 연
            산은 Float 과 Double 을 위한 equals 와 compareTo 구현을 사용한다. 이는 표준과 비교해 다음이 다르다.
            NaN 은 자신과 동일하다고 간주한다
            NaN 은 POSITIVE_INFINITY 를 포함한 모든 다른 요소보다 크다고 간주한다
            -0.0 는 0.0 보다 작다고 간주한다
         */
    }


    fun strings(){
        fun check(c: Char) {
//            if (c == 1) { // ERROR: 비호환 타입
//            }
        }
        /*
            문자 리터럴은 작은 따옴표 안에 표시한다: '1' . 특수 문자를 역슬래시로 표시한다. 다음 특수문자를 지원한다: \t , \b , \n , \r , \' , \" , \\ ,
            \$ . 임의의 문자를 인코딩하려면 유니코드 표기법을 사용한다: '\uFF00' .
         */
        //문자를 Int 숫자로 명시적으로 변환할 수 있다
        fun decimalDigitValue(c: Char): Int {
            if (c !in '0'..'9')
                throw IllegalArgumentException("Out of range")
            return c.toInt() - '0'.toInt() // 숫자로 명시적 변환
        }


        /*
            문자열은 String 타입으로 표현한다. 문자열은 불변이다. 문자열의 요소는 문자로서 s[i] 와 같은 인덱스 연산으로 접근할 수 있다. for -루프로 문자열을 순회할수 있다:
            trimMargin() 함수를 사용해서 앞쪽 공백을 제거할 수 있다:
         */
        val str = "Hello, world!\n"
        val text = """
        for (c in "foo")
        print(c)
        """
        for (c in str) {
            println(c)
        }
        //기본으로 \ 를 경계 접두문자로 사용하지만 trimMargin(">") 과 같이 파라미터를 이용해서 다른 문자를 경계 문자로 사용할 수 있다.
        val text2 = """
        |Tell me and I forget.
        |Teach me and I remember.
        |Involve me and I learn.
        |(Benjamin Franklin)
        """.trimMargin()

        val s = "abc"
        val str2 = "$s.length is ${s.length}" // "abc.length is 3"로 평가
        val price = """
        ${'$'}9.99
        """
    }

    fun booleans(){
        /*
            Boolean 타입은 불리언을 표현하고 두 값이 존재한다: true 와 false .
            null 가능 레퍼런스가 필요하면 불리언도 박싱된다.
            불리언에 대한 내장 연산에는 다음이 있다.
            || – 지연 논리합
            && – 지연 논리곱
            ! - 역
         */
    }



    //배열
    //코틀리은 Array 클래스로 배열을 표현하며, 이 클래스는 get 과 set 함수(연산자 오버로딩 관례에 따라 [] 로 바뀜), size 프로퍼티와 그외 유용한 멤버
    //함수를 제공한다:
    class Array<T> private constructor() {
        val size: Int=0
        operator fun get(index: Int): T?{ return null}
        operator fun set(index: Int, value: T): Unit{}
        operator fun iterator(): Iterator<T>?{ return null }
    }
    /*
        라이브러리 함수 arrayOf() 를 사용해서 배열을 생성할 수 있다. 이 함수에는 항목 값을 전달하는데 arrayOf(1, 2, 3) 은 [1, 2, 3] 배열을 생성한다.
        arrayOfNulls() 라이브러리 함수를 사용하면 주어진 크기의 null로 채운 배열을 생성할 수 있다.
        배열을 생성하는 다른 방법은 팩토리 함수를 사용하는 것이다. 팩토리 함수는 배열 크기와 해당 인덱스에 위치한 요소의 초기값을 리턴하는 함수를 인자로 받는다
     */
    fun arrays(){
        // Creates an Array<String> with values ["0", "1", "4", "9", "16"]
        val asc = Array(5, { i -> (i * i).toString() })

    }
    /*
        앞서 말했듯이, [] 연산자는 get() 과 set() 멤버 함수 호출을 의미한다.
        주의: 자바와 달리 코틀린 배열은 무공변(invariant)이다. 이는 코틀린은 Array<String> 을 Array<Any> 에 할당할 수 없음을 의미하며, 런타임 실패를 방지한
        다. (하지만 Array<out Any> 을 사용할 수 있다. 타입 프로젝션 을 참고하자.)
        코틀린은 또한 ByteArray , ShortArray , IntArray 등 기본 타입의 배열을 표현하기 위한 특수 클래스를 제공한다. 이들 클래스는 박싱 오버헤드가 없다.
        이 클래스는 Array 클래스와 상속 관계에 있지 않지만 같은 메서드와 프로퍼티를 제공한다. 각 배열 타입을 위한 팩토리 함수를 제공한다:
     */
    fun arrays2(){
        val x: IntArray = intArrayOf(1, 2, 3)
        x[0] = x[1] + x[2]
    }



    //흐름 제어: if, when, for, while
    fun ifs(){
        //코틀린에서 if 는 식(expression)으로, 값을 리턴한다. 그래서 삼항 연산자(condition ? then : else)가 없다. 일반 if 로 동일하게 할 수 있기 때문이다
        // 전통적인 용법
        var a = 10
        var b = 12
        var max = a
        if (a < b) max = b

        // else 사용
        if (a > b) {
            max = a
        } else {
            max = b
        }

        // 식으로
        max = if (a > b) a else b

        //if 브랜치는 블록일 수 있으며 마지막 식이 블록이 값이 된다.
        max = if (a > b) {
            print("Choose a")
            a
        } else {
            print("Choose b")
            b
        }
    }

    fun whens(){
        var x = 1
        when (x) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }

        when (x) {
            0, 1 -> print("x == 0 or x == 1")
            else -> print("otherwise")
        }


        var s = 1;
        when (x) {
            s -> print("s encodes x")
            else -> print("s does not encode x")
        }

        //in , !in , 범위 , 콜렉션을 사용해서 값을 검사할 수 있다
        var validNumbers = 1..7
        when (x) {
            in 1..10 -> print("x is in the range")
            in validNumbers -> print("x is valid")
            !in 10..20 -> print("x is outside the range")
            else -> print("none of the above")
        }


        //is 나 !is 로 특정 타입 여부를 확인할 수 있다. 스마트 변환 덕분에 추가 검사없이 타입의 메서드와 프로퍼티에 접근할 수 있다.
        fun hasPrefix(x: Any) = when(x) {
            is String -> x.startsWith("prefix")
            else -> false
        }


        //if - else if 체인 대신에 when 을 사용할 수도 있다. 인자를 제공하지 않으면 브랜치 조건은 단순한 블리언 식이 되고, 브랜치의 조건이 true일 때 브랜치를 실행한다
        fun Int.isOdd() :Boolean{
            return this/2==0
        }
        fun Int.isEven() :Boolean{
            return !(this/2==0)
        }
        when {
            x.isOdd() -> print("x is odd")
            x.isEven() -> print("x is even")
            else -> print("x is funny")
        }
    }



    //for 루프
    fun forloops(){
        var collection = listOf(1,2,3,4,5)
        for (item in collection) print(item)
        for (item: Int in collection) {
            //..
        }
        /*
        이전에 언급했듯이, for 는 이터레이터를 제공하는 모든 것에 동작한다.
        iterator() 멤버 함수나 확장 함수를 갖고 있고, 함수의 리턴 타입이
        next() 멤버 함수나 확장 함수를 갖고,
        리턴 타입이 Boolean 인 hasNext() 멤버 함수나 확장 함수를 가짐
        이 세 함수는 모두 operator 로 지정해야 한다.
        배열에 대한 for 루프는 이터레이터 객체를 생성하지 않는 인덱스 기반 루프로 컴파일된다.
        인덱스를 이용해서 배열이나 리스트를 반복하려면 다음과 같이 하면 된다:
         */

        for (i in collection.indices) {
            print(collection[i])
        }
        /*
            범위에 대한 반복"은 최적화한 구현으로 컴파일해서 객체를 추가로 생성하지 않는다.
            indices 대신 withIndex 라이브러리 함수를 사용해도 된다:
         */
        for ((index, value) in collection.withIndex()) {
            println("the element at $index is $value")
        }


        //while 루프
        var x = 22
        while (x > 0) {
            x--
        }
        do {
            val y = null
        } while (y != null) // 여기서 y를 사용할 수 있다!

    }



    //리턴과 점프
    /*
        코틀린에는 세 가지 구조의 점프 식이 있다.
        return : 기본으로 가장 가깝게 둘러싼 함수나 익명 함수 에서 리턴한다.
        break : 가장 가깝게 둘러싼 루프를 끝낸다.
        continue : 가장 가깝게 둘러싼 루프의 다음 단계를 진행한다.
     */
    data class Person(val name: String?="", val email: String?="")
    fun returnToJump(){
        val p = Person()
        val s =  p.name?: return

        /*
            break와 continue 라벨
            코틀린의 모든 식에 label 을 붙일 수 있다. 라벨은 @ 부호 뒤에 식별자가 붙는 형식으로, 예를 들어 abc@ , fooBar@ 는 유효한 라벨이다( 문법 참고). 식 앞에
            라벨을 위치시켜 라벨을 붙인다.
        */
        val i = 2
        loop@ for(i in 1..100) {
            //..
        }


        loop@ for (i in 1..100) {
            for (j in 1..100) {
                if (true) break@loop
            }
        }

        //라벨에 리턴하기
        //코틀린은 함수 리터럴, 로컬 함수, 오브젝트 식에서 함수를 중첩할 수 있는데, 한정한 return 을 사용하면 바깥 함수로부터 리턴할 수 있다. 가장 중요한 용도는 람다 식에
        //서 리턴하는 것이다. 아래 코드를 보자:
        val ints = listOf(1,2)
        fun foo1() {
            ints.forEach {
                if (it == 0) return // 내부 람다에서 foo()의 콜러로 바로 리턴하는 비로컬 리턴
                print(it)
            }
        }



        /*
        return -식은 가장 가깝게 둘러싼 함수(예, foo )에서 리턴한다. (이런 비로컬 리턴은 인라인 함수 )로 전달한 람다 식에서만 지원한다.) 람다 식에서 리턴하고 싶다면
        담다 식에 라벨을 붙여 return 을 한정해야 한다:
         */
        fun foo2() {
            ints.forEach lit@ {
                if (it == 0) return@lit
                print(it)
            }
        }

        //이제 위 코드는 람다 식에서 리턴한다. 종종 암묵적으로 제공하는 라벨을 사용하는 게 편리할 때가 있다. 이런 라벨은 람다를 전달한 함수와 같은 이름을 갖는다
        fun foo3() {
            ints.forEach {
                if (it == 0) return@forEach
                print(it)
            }
        }

        fun foo4() {
            ints.forEach(fun(value: Int) {
                if (value == 0) return // 익명 함수 호출에 대한 로컬 리턴. 예, forEach 루프로 리턴
                print(value)
            })
        }

        /*
            return@a 1
            이 코드는 "라벨 @a 에 1 을 리턴"하는 것을 의미한다. "라벨을 붙인 식 (@a 1) 을 리턴"하는 것이 아니다.
         */





}


fun main(args: Array<String>) {
    val start = Basic()
    start.check()
}