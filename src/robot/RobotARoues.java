package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;
import terrain.NatureTerrain;

import static terrain.NatureTerrain.*;

/**
 * A Robot with wheels:
 *      default speed : 80 Km/h
 *      can move only on free or built space
 *      reservoir size : 5000 L
 *      refill : 10 min next to a water tile.
 *      intervention : 100 L in 5s
 */
public class RobotARoues extends Robot {

    /**
     * RobotARoues constructor
     *
     * @param position The tile where the robot spawn.
     */
    public RobotARoues(Case position) {
        this.position = position;
        this.speed = 80 * 1000 / 3600;
        this.reservoirMax = 5000;
        this.reservoir = reservoirMax;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 5;
    }

    @Override
    public boolean findWater(Case position) {
        for (Case place : position.getCarte().getVoisins(position)) {
            if (place.getType() == NatureTerrain.EAU) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        if (position.getType() == EAU || position.getType() == FORET || position.getType() == ROCHE) {
            throw new ForbiddenMoveException("Trying to reach inappropriate case type");
        }
        this.position = position;
    }

    @Override
    public boolean isAccessible(Case position) {
        return position.getType() == HABITAT || position.getType() == TERRAIN_LIBRE;
    }
}
