package net.optim.jswarm.pso.particle;

import net.optim.jswarm.Particle;
import net.optim.jswarm.pso.Swarm;
import net.optim.jswarm.pso.RepulsiveSwarm;

/**
 * Particle update strategy 
 * Warning: It's designed to be used with SwarmRepulsive swarms
 * 
 * @author Pablo Cingolani <pcingola@users.sourceforge.net>
 */
public class RepulsiveParticleUpdate implements ParticleUpdate {

    /** Random vector for local update */
    double rlocal[];
    /** Random vector for global update */
    double rother[];
    /** Random vector for neighborhood update */
    double rneighborhood[];
    /** Random factor for random velocity update */
    double randRand;

    /**
     * Constructor 
     * @param particle : Sample of particles that will be updated later
     */
    public RepulsiveParticleUpdate(Particle particle) {
            rlocal = new double[particle.getDimension()];
            rother = new double[particle.getDimension()];
            rneighborhood = new double[particle.getDimension()];
    }

    @Override
    public void begin(Swarm swarm) {
        randRand = Math.random();// Random factor for random velocity

        int i, dim = swarm.getSampleParticle().getDimension();
        for (i = 0; i < dim; i++) {
                rlocal[i] = Math.random();
                rother[i] = Math.random();
                rneighborhood[i] = Math.random();
        }
    }


    @Override
    public void update(Swarm swarm, Particle particle) {
        double position[] = particle.getPosition();
        double velocity[] = particle.getVelocity();
        double particleBestPosition[] = particle.getBestPosition();
        double maxVelocity[] = swarm.getMaxVelocity();
        double minVelocity[] = swarm.getMinVelocity();
        RepulsiveSwarm swarmRepulsive = (RepulsiveSwarm) swarm;

        // Randomly select other particle
        int randOtherParticle = (int) (Math.random() * swarm.size());
        double otherParticleBestPosition[] = swarm.getParticle(randOtherParticle).getBestPosition();
        double neighBestPosition[] = swarm.getNeighborhoodBestPosition(particle);

        // Update velocity and position
        for (int i = 0; i < position.length; i++) {
            // Update position
            position[i] = position[i] + velocity[i];

            // Create a random velocity (one on every dimention)
            double randVelocity = velocity[i] = (maxVelocity[i] - minVelocity[i]) * Math.random() + minVelocity[i];

            // Update velocity
            velocity[i] = swarmRepulsive.getInertia() * velocity[i] // Inertia
                + rlocal[i] * swarmRepulsive.getParticleIncrement() * (particleBestPosition[i] - position[i]) // Local best
                + rneighborhood[i] * swarm.getNeighborhoodIncrement() * (neighBestPosition[i] - position[i]) // Neighborhood best					
                + rother[i] * swarmRepulsive.getOtherParticleIncrement() * (otherParticleBestPosition[i] - position[i]) // other Particle Best Position
                + randRand * swarmRepulsive.getRandomIncrement() * randVelocity; // Random velocity
        }
    }

    @Override
    public void end(Swarm swarm) {
    }
}
