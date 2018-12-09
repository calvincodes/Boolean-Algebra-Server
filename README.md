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

## Bonus assignments