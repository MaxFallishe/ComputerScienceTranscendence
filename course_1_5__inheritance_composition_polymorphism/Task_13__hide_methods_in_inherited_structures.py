# To what extent are method inheritance and method visibility concepts similar in meaning? These concepts are orthogonal, in fact, disjoint.
# Inheritance is about the relationship between classes within the class hierarchy,
# and method visibility is about the relationship between a class and its external user, the programmer (the user of the public interface, the set of operations of the corresponding administrative division)
#
# There are four ways to hide the method in total:
# 1. the method is public in the parent class A and public in its descendant B;
# 2. the method is public in the parent class A and hidden in its descendant B;
# 3. the method is hidden in the parent class A and public in its descendant B;
# 4. The method is hidden in the parent class A and hidden in its descendant B.


# the method is public in the parent class A and public in its descendant B;
class A:
    def public_method(self):
        print("Public method in class A")


class B(A):
    def __init__(self):
        super().__init__()


# the method is public in the parent class A and hidden in its descendant B;
# -- ther is no clear example in python
# 3. the method is hidden in the parent class A and public in its descendant B;
class A:
    def _private_method(self):
        print("Private method in class A")


class B(A):
    def __init__(self):
        super().__init__()

    def public_method(self):
        self._private_method()


# 4. The method is hidden in the parent class A and hidden in its descendant B.
class A:
    def _private_method(self):
        print("Private method in class A")


class B(A):
    def __init__(self):
        super().__init__()

    def _private_method(self):
        print("Private method in class B")
