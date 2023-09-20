# TD 1

### Question 1

1. This program will output:

    ```console
    (0.0,0.0)

    (2.0,2.0)
    ```

2. It asks us to declare the public class TestClass in a separate java file named TestClass.java

3. I dont get an error

### Question 2

1. We get the error x has private access, this is because we are trying to access a private member of the class
Point from outside the class scope (Private class members can only be accessed within the class scope).

2. This program will output: 

    ```console
    (0.0,0.0)

    méthode move(int, int)

    (2.0,2.0)
    ```

    This is because we are passing in 2 integers as an argument and we have defined a function that actually accepts those 
    integers so instead of converting them to doubles and using the other method we will use the newly added one.

3. This won't compile because we already have a method with the same name that takes in the same arguments, how is java
supposed to know if we want the one that returns a boolean or the one that moves the point ?, if we want to "surcharger"
a method inside a class we must either give a different amount of arguments, or different type arguments, so instead
of taking in double dx and double dy if it took in a boolean dx and a boolean dy it would work.

### Question 3 

1. We get the error "Call to 'this' must be first statement in constructor body"

2. We get the error Cannot resolve constructor Point() when we try to instantiate the point p.
When we delete the other constructor, our class has no constructor so java gives it a default constructor, therefore
we don't get any more error messages.

    This program will output: 

    ```console
    (0.0,0.0)

    (2.0,2.0)
    ```

    This is because both attributes in the class are "valeurs numeriques" which means the default constructor given by 
    java to this class will initialize both of them to 0, thats why as a first output we get (0.0,0.0), then we use the method
    p.move(2,2) and then we output them so we get the (2.0,2.0)

3. Both attributes are initialized to 10, so the default constructor doesn't initialize them anymore, hence we get this 
output: 

    ```console
    (10.0,10.0)

    (12.0,12.0)
    ```

### Question 4

1. This program will output:

    ```console
    Circle: [(0.0,0.0), 5.0]

    Circle: [(2.0,5.0), 5.0]

    Circle: [(2.0,5.0), 10.0]
    ```

2. Only one instance of Point is created.

### Question 5

1. This program will output: 

    ```console
    Circle: [(0.0,0.0), 5.0]

    Circle: [(2.0,5.0), 5.0]

    Circle: [(0.0,0.0), 5.0]
    ```

2. One instance of Point is created and one instance of Circle is created.
Since we only have one instance of the class Point, both p and p2 are referencing the same instance of class Point.
attribute center is a point and it references the same point p and p2 are referencing, we basically instantiated one 
point and we assigned to the center attribute the reference to that point and we did the same with p2 because p2 is 
basically just calling the function getCenter() which doesn't return a new instance of point, it returns the same one
so basically same point shareda mongst all the 3.

3. This program will output:

    ```console
    Circle: [(0.0,0.0), 5.0]

    Circle: [(0.0,0.0), 5.0]

    Circle: [(-2.0,-5.0), 5.0]
    ```

4. This program will output: 

    ```console
    Circle: [(0.0,0.0), 5.0]

    Circle: [(0.0,0.0), 5.0]

    Circle: [(0.0,0.0), 5.0]
    ```

5. Now we have 3 instances of class Point (one when we create the Point p, the other one when we call the constructor of 
class Circle which creates a new point with same coordiantes as point p and assigns it to our attribute center, and the last
one is created by the method getCenter() when it is and we assign it to p2)
We still have only one instance of class Circle.

    The instance referenced by the variable p is the point that has coordinates 0.0,0.0
    The instance referenced by the variable p2 is the point that the method getCenter() created when we called it, then we move
    this point to the coordinates -2 and -5 in the next line.
    The instance referenced by the attribute center is the one created by the Circle constructor when we passed in the point p 
    as an argument, what we did was make a new point with the same coordiantes as point p and assign it to the attribute center.

### Question 6

1. This program will output:

    ```console
    Rectangle: [(0.0,0.0), (5.0,3.0)]

    perimeter: 16.0

    Rectangle: [(2.0,5.0), (7.0,8.0)]

    perimeter: 16.0
    ```

2. Variable bl will reference a point with coordinates 0.0,0.0
Variable ur will refernce a different point than bl with coordinates 5.0,3.0
Variable r references a rectangle with 2 attributes, bl and ur, both attributes reference respectively bl and ur (the ones
explained above).

3. Invariant is not respected since now the point bl will have as coordinates: (10.0,10.0) and the point ur will have as coordinates
(5.0,3.0), both coordinates of point br are bigger than the respective coordinates of point ur, which means that the point bl is 
now actually in the top right of the point ur, so the invariant wasn't respected at all, since we always want to have point ur in the
top right of the point bl and not the other way around.

    This program will now output:

    ```console
    Rectangle: [(0.0), (5.0,3.0)]

    perimeter: 16.0

    Rectangle: [(10.0,10.0), (5.0,3.0)]

    perimeter: -24.0
    ```

    The perimeter is now negative which makes sense because of how we defined the function that gives us the perimeter, the method that gives us 
    the perimeter always substracts the values of bl from the values of ur, but since they are greater we are left with negative values, after
    multiplying by 2 we are left with -24.

4. In the constructor we will initialize both attributes like this:

    ```java
    this.bl = new Point(bottomLeft);

    this.ur = new Point(upperRight);
    ```


