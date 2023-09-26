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
one is created by the method getCenter() when it is called and we assign it to p2)
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
now actually at the top right of the point ur, so the invariant wasn't respected at all, since we always want to have point ur in the
top right of the point bl and not the other way around.

    This program will now output:

    ```console
    Rectangle: [(0.0,0.0), (5.0,3.0)]

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

    This program now outputs:

    ```console
    Rectangle: [(0.0,0.0), (5.0,3.0)]

    perimeter: 16.0

    Rectangle: [(0.0,0.0), (5.0,3.0)]

    perimeter: 16.0
    ```
# TD2

### Question 1

1. This program will output:

    ```console
    (3.0,6.0) - FF0000FF
    x: 2.0
    color: FF0000FF
    ```
2. We get a symbol couldn't be found error (line 10), this is because the compiler checks the available methods for the reference type, not the actual object. So when we do Point p = new ColoredPoint(...), the compiler actually checks the reference type, which in this case is Point, but Point itself doesnt define a method to getColor(). (Basically the reference has like a contract with the compiler, it tells him hey this object here will have access to the methods in my class! Except Point doesnt have any getColor() method).

3. No we can't add this line, when we declare the reference type to be ColoredPoint it means this variable can only reference ColoredPoint or it's children (subclasses), the Parent class (Point) is a more general version of ColoredPoint, it also doesn't satisfy the is-a principle in object oriented programming. We can't say Point is-a coloredpoint, it is simply not true, a Point is more general, it would be like saying Animal is-a hippo, it makes no sense, but the other way around it makes sense, ColoredPoint is-a Point, Hippo is-a Animal...

4. No we can't, because it uses directly this.x and this.y and those commands can only be used inside the Point class, this is because they are defined as private (only available within their class scope), but that is fine because we have a method getX() and getY() we could use instead. But in this current state we get an error.

5. We get a cycle inheritance error, this is not allowed in Java.

6. We get a "cannot inherit from final", it makes sense a final class is a class that is immutable, this keyword is used in some of the java classes like java.lang.String.

### Question 2

1. Yes we can do that, because Object is a parent of all classes, either explicitly or implicitly, in this case it is directly the parent of class Point but indirectly the parent of class ColoredPoint, this is usually called ancestor and descendant, Object is the mother of all classes it is the top ancestor.

2. Yes we can do that too, even though the parent of ColoredPoint is Point, we must remember that the parent of Point is Object, so ColoredPoint is a descendant of class Object, once again, Object is the mother of all classes, you can use the Object reference type to instantiate any class in java, the only drawback is that you can only use methods the Object class knows about (hashCode, toString, equals...)

3. Yes we can do that, this method is defined in the class Object, we can call this method because ColoredPoint/Point class are children of the class Object, so they inherit all its attributes and methods, if we don't redefine the method hashCode() in the ColoredPoint/Point class it's still fine! We will use the version that the parent (Object) has.

List of methods of class Object:

     - clone()
     - equals(Object obj)
     - finalize()
     - getClass()
     - hashCode()
     - notify()
     - notifyAll()
     - toString()
     - wait()
     - wait(long timeoutMillis)
     - wait(long timeoutMillis, int nanos)

### Question 3

1. We get the error: Call to super must be the first line in constructor, this actually makes sense, so ColoredPoint is the child of Point, it doesn't make sense to initialize ColoredPoint before, imagine we wanted to use the getX() or getY() methods inside ColoredPoint constructor, those are the methods the parent Point has, we wouldn't be able, so we always must put a call to the super constructor to prepare the variables that we might in the ColoredPoint constructor. By the way, Point itself has a super() call too, it is not visible because the compiler gives it by default and it is used to initialize the attributes and methods it gets from the Object class, so when you instantiate a new object what you are creating is a chain reaction of constructors, and it is important that the Parent is born before the Child is born!

2. We get a error: There is no default constructor in class Point, but why ? because as I said in the earlier answer, if you don't call the super class constructor yourself the compiler puts in a default call that looks like: super(), except we haven't defined a default constructor in class Point! and there is another constructor so the compiler doesn't give us a default constructor in Point ! This line's: super(x,y) purpose is to initialize the variables x and y that our child ColoredPoint needs to function. 

3. If we add a default constructor in class Point, then we don't get the error anymore, the compiler puts in a default super() in our subclass ColoredPoint that calls for the parents default constructor, and since we have defined one, then everything works fine! Our x and y values are both 0.

### Question 4

1. This program Outputs:

    ```console
    constructor of A
    constructor of B
    constructor of C
    ```
    
It makes sense because the constructor of C even though it doesn't explicitly call for the super constructor, the compiler adds to it in the first line a default super() call (it works because the parent has a default constructor this super call is refering to), then constructor B itself has a default call super() to its parents constructor (given by the compiler aswell) and it doesn't give an error because A has a default constructor that the super() keyword is referring to, A's constructor itself has a super() call given by the compiler that calls the Object class constructor, once the Object constructor is done initializing methodds and variables, we go back to constructor A and finish executing the lines of code left on the constructor, in this case a system out print of "constructor of A", once constructor A finishes we go back to constructor B and execute the left code after the super() call that has just finished, which system out prints "Constructor of B", once we finish initializing B completely we go back to C's constructor now, which after calling it's parents constructor only system outputs "constructor of C", that is why we get these messages in this order.

2. If we delete C's constructor, then class C has no constructor, so the compiler gives it a constructor by default, this default constructor contains a default call to super(), which calls constructor and so on so forth (I explained the chain reaction in the previous question 4.1), so we get as an output:

    ```console
    constructor of A
    constructor of B
    ```

3. Yes the effect is the same and I explained why in the question 4.1 ^^.

4. Class Object has one default constructor, and since it can be called from anywhere I assume its a public constructor, so maybe something like: public Object() {}

### Question 5

1. This program will output:

    ```console
    (0.0, 0.0) - not pinned
    (1.0, 1.0) - pinned
    ```

2. The method move that gets executed is the one of the object that is being referenced, so we execute the redefinition in PinnablePoint.

3. We get the error: unreported exception java.lang.Exception; must be caught or declared to be thrown

4. Yes I still get an error

5. Yes I still get an error

6. Yes I still get an error

7. Yes I still get an error

8. It says it clashes with the method in the parent (this is because we kept the same name, the same order of the argument list, the same type of the argument list but we attempted to restrict its visibility from public to protected, keep in mind that if in the parent it was defined as a protected and we tried to redefine it as a public one it would be fine, we just must not restrict it)

9. super.move() calls the move method of the parent, in this case the move method in the class Point

### Question 6

1. This program outputs:

    ```console
    (1.0, 1.0)
    (3.0, 5.0) - FF0000FF
    (2.0, 2.0) - not pinned
    ```

First of all we instantiate a List that contains Points so any type thats a subclass of Point will be accepted (ColoredPoint, PinnablePoint) and obviously an object of type Point too.

We then add 3 new points to the list, new Point(0,0), new ColoredPoint(2,4,0xFF0000FF), new PinnablePoint(1,1)

Our list contains these 3 points in the same order, then we call the method moveAndPrintPoints with our list as an argument.

Once inside the method we ignore the first if statement because our list is not empty, we declare a reference of type Point that will reference to the element in the position 0 of the list, and at the same time pop it out (since we use remove), so p holds the first element added and then that element is removed, p refers to: the first Point we made, of coordinates 0,0.

Now we move this point, the move method in Point simply moves the point without doing anything extra so our Point p now has coordinates of 1,1 because (0+1,0+1) <- the point was moved.

And then we system out print such Point p, this point refers to a object type Point so we will use the tostring method inside the Point class, which just outputs the point like this: (x, y) so in our case it will be (1.0, 1.0)

Then we call the same method once again with the same list, keep in mind this list now only has 2 elements inside it, a Object of type ColoredPoint and another one of PinnablePoint

The list is not empty yet so we ignore the first if statement we declare a reference variable of type Point that will reference to the first Object of the list, in this case its the Object of type ColoredPoint with coordinates (2,4) and color value of: 0xFF0000FF (this Object of type ColoredPoint gets removed out of the list because of the method list.remove(0)), then we move this point by (1,1), the method move used is the one in class Point because ColoredPoint doesn't redefine it, so our point gets moved to the coordinates (3,5).

Then we print out the ColoredPoint, since the ColoredPoint redefines a toString method then we will use the one of ColoredPoint, this method will output the point as: (3.0, 5.0) - FF0000FF

Then we call the method again with the same list (principle of recursion), now the list only contains 1 element, the last one, the Object of type PinnablePoint, since the list is not empty we ignore the first if statement, we assign the Object of type PinnablePoint to the reference variable p of type Point, and we remove the PinnablePoint from the list, now the list is empty.

We call the method move on this point, this point is of type PinnablePoint and it does actually redefine the method move, so the method move that will be called will be the redefined one, this method move though is almost the same, it's only different if the point is pinned, the point here is not pinned so we basically just end up calling the method move of the super inside the method move, so we are just moving the coordinates normally, we move this point by (1,1), so we end up having coordinates (1+1,1+1) = (2,2) (because our PinnablePoint was instantiated with coordinates 1,1). 

Then we system out print the Point p (references to Object type of PinnablePoint), this object PinnablePoint has a redefinition of the method toString so we are going to use that to string method instead to print it out, and we end up with an input of: (2.0, 2.0) - not pinned

We call the function again with the same list that is now empty, since the list is empyt we do not ignore the first if statement and we return nothing, and we finish executing the method.

### Question 7

1. I get the error modifier protected not allowed here

2. I get the error modifier public is rebundant here

4. I get the same input as before:

    ```console
    (1.0, 1.0)
    (3.0, 5.0) - FF0000FF
    (2.0, 2.0) - not pinned
    ```

# TD5

### Classes Generiques

1. We get an error: Required type: Integer, provided: Double. This is because the reference type of the object is of type Integer and we are trying to assign a Double to it.

2. It tells us that its rebundant to specify on the object instantiation that we are working with an integer this is because the compiler can guess we are working with an integer when we write Box<Integer>. (We use this all the time when we instantiate Lists as ArrayLists we almost never put the type, for example: List<Integer> listofIntegers = new ArrayList<>()).

### Heritage et generiques

3. No, Box<Integer> isnt a subclass of type of Box<Object>.

4. It says that we must provide a Box<Integer> but that we have given it a Box<Object>.

5. Now the error is in line 6, it says new Integer(42) is deprecated and it will be removed, we also get an error at line 11 because, the same one actually.

### Jokers

6. The error we get is capture of ? is what is required and we provide it with an Integer.

7. Well if we don't specify that the class implements Comparable which means its going to be comparable, then we cant guess the compareTo method, every class that extends Comparable must override the compareTo method inside it, because Comparable is a functional Interface which means it has only one abstract method that must be overriden in the class, so if we know it implements Comparable it means it has a compareTo method, but if we don't specify it implements Comparable then we can't know if it has or no the compareTo method, so the compiler tells us it doesn't know about it. So the error we get is compareTo method not found.

8. The error we get is that java.lang.Number doesn't implement the Comparable interface, if it doesn't implement the Comparable interface then we can't magically call its compareTo method because it might not even have it.

9. An example of a call to the method copy would be:

    ```java
    List<Number> numberList = new ArrayList<>();
    List<Integer> integerList = new ArrayList<>();
    copy(numberList,integerList);
    ```

10. Let's assume that Point is the parent of ColoredPoint, and that the ColoredPoint only adds a color to the Point, the following code can use the sort method: 

    ```java
    List<ColoredPoint> coloredPoints = new ArrayList<>();
    coloredPoints.add(new ColoredPoint(5.0,4.0,blue));
    coloredPoints.add(new ColoredPoint(6.0,2.0,red));
    Comparator<Point> pointComparator = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            if (o1.equals(o2)) {
                return 0; // If both points are equal we return 0
            }
            // If the points we are different we compare their coordinates and return an integer that will represent if point o1
            // is below to the left of point o2 or no, I assumed Points take doubles as coordinates x and y so we must cast to an
            //int the result of Math.round, Math.round gives us a long! we can safely cast it to a int!
            return (int) Math.round((o1.getX() - o2.getY()) + (o1.getY() - o2.getY()));
        }
    };
    sort(coloredPoints, pointComparator);
    ```



