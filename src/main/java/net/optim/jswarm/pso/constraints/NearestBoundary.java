
package net.optim.jswarm.pso.constraints;

import net.optim.jswarm.Particle;

/**
 * Set the particle onto the boundary
 * @author Paulius Danėnas <danpaulius@gmail.com>
 */
public class NearestBoundary implements ConstraintsHandler {

    @Override
    public double getVelocity(Particle particle, int index, double[] boundingVelocity) {
        return boundingVelocity[index];
    }

    @Override
    public double getPosition(Particle particle, int index, double[] boundingPosition) {
        return boundingPosition[index];
    }
    
}
