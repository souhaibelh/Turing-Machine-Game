package g61610.atl.ascii;

import g61610.atl.ascii.controller.Application;

/**
 * This class here is the pure App, clicking on run will star the chain of events that will allow us to write beautiful
 * Ascii drawings on the console :)
 */

public class App {
    public static void main(String[] args) {

        /*
         new model
         new view
         new app (model, view)
         start.
         */

        new Application().start();
    }
}
