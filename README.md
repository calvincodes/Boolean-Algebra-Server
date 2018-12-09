# Test Assignment

## Warmup

Provided that, `f(x) = f(x-1)+f(x-1)`  
This implies, `f(x) = 2*f(x-1)`

Now, `f(0) = 1`  
`f(1) = 2*f(0) = 2^1 = 2`  
`f(2) = 2*f(1) = 2^2 = 4`  
`f(3) = 2*f(2) = 2^3 = 8`  
.. so on

Thus, `f(x) = 2^x`

Hence, we can simply define the function using scala's math library  
```scala
def f(x: Int) : Int = { 
  pow(2, x).toInt
}
```

1. big-O complexity of solution = big-O complexity of the `scala.math.pow function`. This function internally uses `java.lang.Math.pow`. Assuming that Java pow function is O(1), we can claim that our function is also having a complexity of O(1).
2. As the library function used for this problem is having O(1) complexity and is already optimised, hence the only possible way to optimize is to implement a better algorithm than the one used by Java library function.

## Using APIs and testing: JSON serialization
* JSON Library: play.api.libs.json
* Build Tool: sbt
* Testing: ScalaTest

1. Unmarshalling: Convert JSON string of boolean expression into BooleanExpression object
2. Marshalling: Converts BooleanExpression object to JSON string

* `Driver` class provides the a running example of basic test cases. Please run the same in your IDE. 
* All test cases reside in the test directory. Please run the same in your IDE. To run them from terminal, please execute `sbt test`.

## Bonus assignments

* Boolean Algebra Server using: Play Framework

1. Algebraic Transformation: Functionality of DNF and CNF transformation has been added. A running example for both is available in `Driver` class.
2. You may startup the server using your IDE. To start the server from terminal, execute the command `sbt run`. Open the page `http://localhost:9000` which provides sample cURL requests.