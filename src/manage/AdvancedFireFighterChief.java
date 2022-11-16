package manage;

import java.util.List;

import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * The firefighterChief that will apply the Advanced strategy (second strategy
 * of the topic)
 */
public class AdvancedFireFighterChief extends FireFighterChief {

    public AdvancedFireFighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    /**
     * <ul>
     * <li>The firefighterChief advises to all robots an {@code incendie} to
     * extinct.
     *
     * <li>The occupied robots refuse the proposition, the other ones compute the
     * shortest route to go the {@code incendie} and return the time that they'll
     * need to do so.
     *
     * <li>The chief picks the fastest robot to extinct the {@code incendie}.
     * The robot chosen programs the events necessaries to make his way to the
     * {@code incendie}.
     * Then he checks if the {@code incendie} was not extinct before pouring water.
     * When the reservoir of a robot is empty, it goes refill it from his own.
     *
     * <li>The chief can ask this for each {@code incendie}. If some remain
     * unassigned,
     * the fire chief waits for a certain amount of time and
     * waits for a certain period of time and proposes the remaining
     * {@code incendie} again.
     *
     * </ul>
     * 
     * @param sim the Simulateur.
     */
    @Override
    public void affectRobot(Simulateur sim) {
        for (Incendie incendie : incendies) {
            if (incendie.getNbL() > 0) {
                Robot fastestRobot = null;
                Path minPath = null;
                int minTime = 0;
                for (Robot robot : robots) {
                    if (robot.isWaiting(sim) && robot.isAccessible(incendie.getFireCase())) {
                        try {
                            Path path = robot.Dijkstra(robot.getPosition(), (c) -> c.equals(incendie.getFireCase()));
                            if (fastestRobot == null || path.getDuration() < minTime) {
                                minTime = path.getDuration();
                                fastestRobot = robot;
                                minPath = path;
                            }
                        } catch (UnreachableCaseException e) {
                            assert true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (fastestRobot != null) {
                    try {
                        fastestRobot.followPath(sim, minPath);
                        fastestRobot.startIntervention(sim, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}