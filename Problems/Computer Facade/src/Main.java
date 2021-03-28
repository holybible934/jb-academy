class ComputerFacadeTestDrive {
    public static void main(String[] args) {
        /* Your subsystems */
        Processor processor = new Processor();
        Monitor monitor = new Monitor();
        Keyboard keyboard = new Keyboard();

        ComputerFacade computerFacade = new ComputerFacade(processor, monitor, keyboard);

        computerFacade.turnOnComputer();
        computerFacade.turnOffComputer();
    }
}

class ComputerFacade {
    /* Your subsystems */
    Processor processor;
    Monitor monitor;
    Keyboard keyboard;

    public ComputerFacade(Processor processor, Monitor monitor, Keyboard keyboard) {
        /* Write your code here */
        this.processor = processor;
        this.monitor = monitor;
        this.keyboard = keyboard;
    }

    public void turnOnComputer() {
        /* Write your code here */
        processor.on();
        monitor.on();
        keyboard.on();
    }

    public void turnOffComputer() {
        /* Write your code here */
        keyboard.off();
        monitor.off();
        processor.off();
    }
}

class Processor {
    /* Your subsystem description */
    String description = "Processor";

    public void on() {
        /* Write your code here */
        System.out.println(description + " on");
    }

    public void off() {
        /* Write your code here */
        System.out.println(description + " off");
    }
}

class Monitor {
    /* Your subsystem description */
    String description = "Monitor";

    public void on() {
        /* Write your code here */
        System.out.println(description + " on");
    }

    public void off() {
        /* Write your code here */
        System.out.println(description + " off");
    }
}

class Keyboard {
    /* Your subsystem description */
    String description = "Keyboard";
    String backlight_description = "Backlight";
    public void on() {
        /* Write your code here */
        System.out.println(description + " on");
        turnOnBacklight();
    }

    public void off() {
        /* Write your code here */
        System.out.println(description + " off");
        turnOffBacklight();
    }

    private void turnOnBacklight() {
        /* Write your code here */
        System.out.println(backlight_description + " is turned on");
    }

    private void turnOffBacklight() {
        /* Write your code here */
        System.out.println(backlight_description + " is turned off");
    }
}