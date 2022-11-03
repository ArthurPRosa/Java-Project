package robot;

import exceptions.ForbiddenMoveException;
import pathfinding.Path;
import pathfinding.SelfDriving;
import simu.Simulateur;
import simu.evenements.robot_evenements.InterventionEven;
import simu.evenements.robot_evenements.RemplissageEven;
import simu.evenements.robot_evenements.mouvements.RobotBougeEven;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public abstract class Robot extends SelfDriving {
    protected Case position;
    /* !! En m/s !! */
    protected int speed;
    protected int reservoirMax;
    protected int reservoir;
    protected int timeRefill;
    protected int volumeIntervention;
    protected int timeIntervention;

    private long timeFree = 0;
    // private Action currentAction = Action.ATTENTE;

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) throws ForbiddenMoveException {
        this.position = position;
    }

    public int getSpeed() {
        return getSpeedOn(this.position);
    }

    public void setSpeed(int speed) {
        this.speed = speed * 1000 / 3600;
    }

    @Override
    public int getSpeedOn(Case place) {
        return speed;
    }

    @Override
    public int getTimeOn(Case pos) {
        return pos.getCarte().getTailleCases() / getSpeedOn(pos);
    }

    public int deverserEau() {
        int tmpVol = Integer.min(volumeIntervention, reservoir);
        reservoir -= tmpVol;
        return tmpVol;
    }

    public int getReservoir() {
        return reservoir;
    }

    public void startMove(Simulateur sim, Direction dir) {
        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + getTimeOn(getPosition());
        System.out.println("FIN A " + timeEnd);
        sim.ajouteEvenement(new RobotBougeEven(timeEnd, sim, this, dir));
        this.timeFree = timeEnd;
    }

    /**
     * Plan to begin move toward given direction from given planned case as soon as
     * possible.
     * This function will plan how long the move will take and create an event at
     * the end to make the move concrete.
     * 
     * @param sim         The simulation in which the move happens.
     * @param dir         The direction toward which the robot will move.
     * @param plannedCase The Case where the robot will be at the beginning of the
     *                    move.
     */
    public void startMove(Simulateur sim, Direction dir, Case plannedCase) {
        long timeEnd = Long.max(this.timeFree + 1, sim.getDateSimulation()) + getTimeOn(plannedCase);
        System.out.println("FIN A " + timeEnd);
        sim.ajouteEvenement(new RobotBougeEven(timeEnd, sim, this, dir));
        this.timeFree = timeEnd;
    }

    public abstract boolean isAccessible(Case position);

    /**
     * Plan to begin an intervention as soon as possible.
     * This function will plan how long the intervention will take and create an
     * event at the end to make it concrete.
     * 
     * @param sim The simulation in which the intervention will happen.
     */
    public void startIntervention(Simulateur sim) {
        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + this.timeIntervention;
        sim.ajouteEvenement(new InterventionEven(timeEnd, sim, this));
        this.timeFree = timeEnd - 1;
    }

    /**
     * Intervene on fire at given date. If the reservoir is not full enough, it will
     * be emptied on the fire;
     * 
     * @param sim  The simulation in which the intervention will happen.
     * @param date Precise the time at which the intervention should start.
     * @throws IllegalStateException if the robot is not free at the given date.
     */
    public void intervenir(Simulateur sim, long date) throws IllegalStateException {
        if (this.timeFree > date) {
            throw new IllegalStateException(date + " : The robot can't intervene, it is already occupied !");
        }
        sim.ajouteEvenement(new InterventionEven(date + this.timeIntervention, sim, this));
        this.timeFree = date + this.timeIntervention;
    }

    /**
     * Return True if there is accessible water.
     *
     * @return the boolean.
     */
    protected abstract boolean findWater();

    /**
     * Plan to refill robot's reservoir as soon as possible.
     * This function will plan how long it will take to refill and create an event
     * at the end to make it concrete.
     * 
     * @param sim The simulation in which the event will happen.
     */
    public void remplir(Simulateur sim) {

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + this.timeRefill;
        sim.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
    }

    /**
     * Plan to refills the reservoir at given date. If it was already filled, will
     * still try to full it, and therefore take unnecessary time.
     * 
     * @param sim  The simulation in which the event will happen.
     * @param date Precise the time at which the intervention should start.
     * @throws IllegalStateException In case the robot is already occupied at given
     *                               date or if there is no available water.
     */
    public void remplir(Simulateur sim, long date) {
        if (this.timeFree > date) {
            throw new IllegalStateException(date + " : The robot can't refill, it is already occupied !");
        }

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = date + this.timeRefill;
        sim.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
    }

    public void remplirReservoir() {
        reservoir = reservoirMax;
    }

    /**
     * A robot can be occupied either by extinguishing a wildfire, by moving or by
     * filling up.
     *
     * @return True if the robot is not occupied.
     */
    public boolean isWaiting(Simulateur sim) {
        return sim.getDateSimulation() >= this.timeFree;
    }

    /**
     * !!! Ajouter déverser eau à la fin !!!
     */
    public long followPath(Simulateur sim, Path path, Carte carte) {
        Case plannedCase = path.getStart();
        for (Direction direction : path.getPath()) {
            this.startMove(sim, direction, plannedCase);
            plannedCase = carte.getVoisin(plannedCase, direction);
        }
        return timeFree;
    }
}
