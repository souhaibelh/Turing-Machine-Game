package g61610.atl.ascii;

import g61610.atl.ascii.controller.Application;
import g61610.atl.ascii.model.AsciiPaint;
import g61610.atl.ascii.view.View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class here is the pure App, clicking on run will star the chain of events that will allow us to write beautiful
 * Ascii drawings on the console :)
 */
public class App {
    public static void main(String[] args) {

        View view = new View();
        Scanner keyboard = new Scanner(System.in);
        String paint_dimensions = keyboard.nextLine();
        Pattern dimensions = Pattern.compile("(?: ?)+(?:d|default|([0-9]+) ([0-9]+))(?: ?)+");
        Matcher dimensions_matcher = dimensions.matcher(paint_dimensions);

        while (!dimensions.matcher(paint_dimensions).matches()) {
            System.out.println("""
                    Invalid, enter one of the following:\s
                     - d or default for a default sized board
                     - width height for a custom sized board""");
            paint_dimensions = keyboard.nextLine();
        }
        dimensions_matcher.find();
        AsciiPaint paint = paint_dimensions.equals("d") || paint_dimensions.equals("default") ? new AsciiPaint() :
                new AsciiPaint(
                        Integer.parseInt(dimensions_matcher.group(1)),
                        Integer.parseInt(dimensions_matcher.group(2))
                );
        Application app = new Application(paint,view);
        app.start();
    }
}
