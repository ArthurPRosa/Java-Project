package robot;

import exceptions.ForbiddenMove;
import terrain.Case;
import terrain.NatureTerrain;

public class RobotAPattes extends Robot {
    public RobotAPattes(Case position) {
        this.position = position;
        this.speed = 30;
        this.volumeIntervention = 10;
        this.timeIntervention = 1;
    }

    @Override
    public int getSpeed() {
        return this.position.getType() == NatureTerrain.ROCHE ? 10 : super.getSpeed();
    }

    @Override
    public int deverserEau(int vol) {
        return vol;
    }

    @Override
    public void remplirReservoir() {
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMove {
        if (position.getType() == NatureTerrain.EAU) {
            throw new ForbiddenMove("Trying to inappropriate case type");
        }
        super.setPosition(position);
    }
}
