package simu.evenements;

import robot.Action;
import robot.Robot;
import simu.Simulateur;

public class InterventionEven extends Evenement {
    Robot robot;
    Simulateur sim;

    public InterventionEven(long date, Robot robot, Simulateur sim) {
        super(date);
        this.robot = robot;
        this.sim = sim;
    }

    @Override
    public void execute() {
        int toContinue = robot.intervenir();
        for (int i = 1; i <= toContinue; i++) {
            sim.ajouteEvenement(new ContinuerEven(getDate() + i, robot, Action.INTERVENTION));
        }
    }
    
}
