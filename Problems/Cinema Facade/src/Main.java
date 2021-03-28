class CinemaFacadeTestDrive {
    public static void main(String[] args) throws InterruptedException {
        PopcornPopper popcorn = new PopcornPopper();
        Lights lights = new Lights();
        Projector projector = new Projector();

        CinemaFacade cinemaFacade = new CinemaFacade(popcorn, lights, projector);

        cinemaFacade.watchMovie();
        System.out.println("We are watching a movie");
        Thread.sleep(5000);
        System.out.println("The End");
        cinemaFacade.endMovie();
    }
}

class CinemaFacade {
    private PopcornPopper popcorn;
    private Lights lights;
    private Projector projector;

    public CinemaFacade(PopcornPopper popcorn, Lights lights, Projector projector) {
        this.popcorn = popcorn;
        this.lights = lights;
        this.projector = projector;
    }

    public void watchMovie() {
        /* write your code here */
		System.out.println("Get ready to watch a movie...");
		this.popcorn.on();
		this.popcorn.pop();
		this.lights.off();
		this.projector.on();
    }

    public void endMovie() {
        /* write your code here */
		this.popcorn.off();
		this.lights.on();
		this.projector.off();
    }
}

class PopcornPopper {
	String description = "PopcornPopper";

	public void on() {
		System.out.println(description + " on");
	}

	public void off() {
		System.out.println(description + " off");
	}

	public void pop() {
		System.out.println(description + " popping popcorn!");
	}
}

class Projector {
	String description = "Projector";

	public void on() {
		System.out.println(description + " on");
	}

	public void off() {
		System.out.println(description + " off");
	}
}

class Lights {
	String description = "Lights";

	public void on() {
		System.out.println(description + " on");
	}

	public void off() {
		System.out.println(description + " off");
	}

	public void dim(int level) {
		System.out.println(description + " dimming to " + level  + "%");
	}

	public String toString() {
		return description;
	}
}
