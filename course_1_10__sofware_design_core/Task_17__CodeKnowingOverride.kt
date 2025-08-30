// If you try to implement the method without the override modifier, compilation
// will fail. In this case, if the case of the function to be redefined
// is not respected, the original method will be called.


open class Animal {
    open fun makeSound() {
        println("Generic animal sound")
    }
}


class Cat : Animal() {
    override fun makeSound() {
        println("Meow")
    }
}


class Dog : Animal() {
    fun makesound() {
        println("Bark")
    }
}

fun main() {
    val cat: Animal = Cat() // Meow
    val dog: Animal = Dog() // Generic animal sound
    cat.makeSound()
    dog.makeSound()
}





