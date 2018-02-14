package com.chapter1

import javafx.scene.shape.Rectangle
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Start(){

    //두 개의 Int 파라미터와 리턴 타입이 Int 인 함수:
    fun sum1(a: Int, b: Int): Int {
        return a + b
    }
    //식 몸체(expression body)를 사용하고 리턴 타입을 추론:
    fun sum2(a: Int, b: Int) = a + b

    //의미없는 값을 리턴하는 함수:
    fun printSum1(a: Int, b: Int): Unit {
        println("sum of $a and $b is ${a + b}")
    }

    fun printSum2(a: Int, b: Int) {
        println("sum of $a and $b is ${a + b}")
    }

    //조건식 사용
    fun maxOf1(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }


        fun foo(param: Int) {
            val result = if (param == 1) {
                "one"
            } else if (param == 2) {
                "two"
            } else {
                "three"
            }
        }


        val b: Boolean? = null
        if (b == true) {
            //...
        } else {
            // `b`는 false나 null
        }
    }
    fun maxOf2(a: Int, b: Int) = if (a > b) a else b

    //null 가능 값을 리턴하는 함수는 다음과 같이 사용한다:
    fun parseInt(str: String): Int? {
        return null
    }
    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)
        // `x * y` 코드는 에러를 발생하는데, 이유는 null을 가질 수 있기 때문이다.
        if (x != null && y != null) {
        // null 검사 이후에 x와 y를 자동으로 null 불가로 변환
            println(x * y)
        }
        else {
            println("either '$arg1' or '$arg2' is not a number")
        }
        //return
    }



    //타입 자동추론
    fun getStringLength1(obj: Any): Int? {
        if (obj is String) {
        // 이 블록에서는 `obj`를 자동으로 `String`으로 변환
            return obj.length
        }
        // 타입 검사 블록 밖에서 `obj`는 여전히 `Any` 타입
        return null
    }
    fun getStringLength2(obj: Any): Int? {
        if (obj !is String) return null
        // `obj`를 자동으로 `String`으로 변환
        return obj.length
    }

    fun getStringLength3(obj: Any): Int? {
        // 우변의 `&&`에서 `obj`를 자동으로 `String`으로 변환
        if (obj is String && obj.length > 0) {
            return obj.length
        }
        return null
    }



    //for loop
    fun forloop1(){
        //읽기 전용 리스트, 맵
        val items = listOf("apple", "banana", "kiwi")
        val map = mapOf("apple" to "aval", "banana" to "bval", "kiwi" to "kval")
//        println(map["key"])
//        map["key"] = '2'

        for (item in items) {
            println(item)
        }

        for (index in items.indices) {
            println("item at $index is ${items[index]}")
        }

        for ((idx,value) in items.withIndex()) {
            println("item at $idx is ${value}")
        }

        for ((k, v) in map) {
            println("$k -> $v")
        }


        var index = 0
        while (index < items.size) {
            println("item at $index is ${items[index]}")
            index++
        }


        fun describe(obj: Any): String =
                when (obj) {
                    1 -> "One"
                    "Hello" -> "Greeting"
                    is Long -> "Long"
                    !is String -> "Not a string"
                    else -> "Unknown"
                }
    }

    //범위사용
    //in 연산자를 사용해서 숫자가 범위에 들어오는지 검사한다
    fun scope(){
        val x = 10
        val y = 9
        if (x in 1..y+1) {
            println("fits in range")
        }

        //!in 연산자를 사용해서 숫자가 범위를 벗어나는지 검사한다:
        val list = listOf("a", "b", "c")
        if (-1 !in 0..list.lastIndex) {
            println("-1 is out of range")
        }
        if (list.size !in list.indices) {
            println("list size is out of valid list indices range too")
        }


        for (i in 1..100) { /*...*/ } // 닫힌 범위: 100 포함
        for (i in 1 until 100) { /*...*/ } // 반만 열린 범위: 100 미포함
        for (x in 2..10 step 2) { /*...*/ }
        for (x in 10 downTo 1) { /*...*/ }
        if (x in 1..10) { /*...*/ }
    }

    fun scopeLoop(){
        for (x in 1..5) {
            print(x)
        }

        for (x in 1..10 step 2) {
            print(x)
        }
        for (x in 9 downTo 0 step 3) {
            print(x)
        }

        val items = listOf(1,2,3,4,56,7,8,9,5)
        for (item in items) {
            println(item)
        }

    }


    //when
    fun when1(){
        val items = listOf("gg","orange")
        when {
            "orange" in items -> println("juicy")
            "apple" in items -> println("apple is fine too")
            else -> println("No when")
        }

        var x = Start()
        when (x) {
            is Start -> println("-Start-")
            else -> println("--")
        }


        fun transform(color: String): Int {
            return when (color) {
                "Red" -> 0
                "Green" -> 1
                "Blue" -> 2
                else -> throw IllegalArgumentException("Invalid color param value")
            }
        }
    }


    fun filter1(){

        val items = listOf(1,2,3,45,6)
        val positives1 = items.filter { x -> x > 0 }
        val positives2 = items.filter { it > 0 }

        val fruits = listOf("a","orange")
        fruits.filter { it.startsWith("a") }
              .sortedBy { it }
              .map { it.toUpperCase() }
              .forEach { println(it) }
    }



    fun klass(){
        //생성시new를 사용하지 않는다.
        val r = Rectangle(4.0,2.0)
    }


    //이디엄
    //코틀린에서 종종 사용되는 이디엄을 정리했다. 선호하는 이디엄이 있다면 풀리퀘스트를 날려 기여해보자
    /*
        다음 기능을 가진 Customer 클래스를 제공한다:
        모든 프로퍼티에 대한 getter (그리고 var 의 경우 setter)
        equals()
        hashCode()
        toString()
        copy()
        모든 프로퍼티에 대한 component1() , component2() , …, ( data 클래스 참고)
     */
    data class Customer(val name: String, val email: String)

    //함수 파라미터의 기본 값
    fun foo(a: Int = 0, b: String = "") {  }


    //lazy  지연로딩  호출할때 실행된다.
    fun lazy(){
        val p: String by lazy {
            "ss"
        }
    }

    //확장 함수 extantion
    fun extantion(){
        fun String.spaceToCamelCase() { /*...*/ }
        "Convert this to camelcase".spaceToCamelCase()
    }

    //싱글톤
    object Resource {
        var name = "Name"
    }
    fun st(){
        Resource.name = "zz";
    }



    //null
    fun nulls(){
        //if not null
        val files = File("Test").listFiles()
        println(files?.size)

        //If not null and else
        println(files?.size ?: "empty")

        //null이면 문장실행
        val values = mapOf("email" to "a@a.net")
        val email = values["email"] ?: throw IllegalStateException("Email is missing!")

        //null이 아니면 실행
        values?.let {
            /*...*/ // null이 아닌 블록 실행
        }


        //null이 아니면 null 가능 값 매핑
        val mapped = values?.let { "transformValue(it)" } ?: "defaultValueIfValueIsNull"
    }



    //try catch
    fun test() {
        val result = try {
            5
        } catch (e: ArithmeticException) {
            throw IllegalStateException(e)
        }
        // 결과로 작업
    }


    //Unit 을 리턴하는 메서드의 빌더-방식 용법
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }


    //단일식 함수
    fun theAnswer1() = 42
    fun theAnswer2(): Int {
        return 42
    }

    fun transform(color: String): Int = when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }



    //객체 인스턴스의 메서드를 여러 번 호출('with')
    class Turtle {
        fun penDown(){}
        fun penUp(){}
        fun turn(degrees: Double){}
        fun forward(pixels: Double){}
    }
    fun withs(){
        val myTurtle = Turtle()
        with(myTurtle) { //100 pix 사각형 그리기
            penDown()
            for(i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }

        //자바 7의 자원을 사용한 try
        val stream = Files.newInputStream(Paths.get("/some/file.txt"))
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }
    }


    //지네릭 타입 정보를 요구하는 지네릭 함수를 위한 편리한 양식
    //inline fun <reified T: Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)



}


fun main(args: Array<String>) {
    val start = Start();
//    var l = Start().sum(1,5)
//    print("-${l}-")

    start.maxOf1(1,5);

}