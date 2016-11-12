package net.optim.pso;

import net.metaopt.swarm.FitnessFunction;
import net.metaopt.swarm.Particle;
import net.metaopt.swarm.pso.RepulsiveSwarm;
import net.metaopt.swarm.pso.Swarm;

/**
 * Maximize Alpine function,  http://clerc.maurice.free.fr/pso/Alpine/Alpine_Function.htm
 * 	
 * 		f( x1 , x2 ) = sin( x2 ) * sin( x2 ) * Sqrt( x1 * x2 )
 * 	
 * @author Įlvaro Jaramillo Duque <aduque@inescporto.pt>
 */
public class AlpineFunction {
    
    public static class AlpineParticle extends Particle {

        public AlpineParticle() {
            super(2);
        }
    }
    
    public void optimize() {
        
        System.out.println("Alpine function example\n");
        Swarm swarm = new RepulsiveSwarm(RepulsiveSwarm.DEFAULT_NUMBER_OF_PARTICLES, new AlpineParticle(), new FitnessFunction(false) {

            @Override
            public double evaluate(double[] position) {
		return Math.sin(position[0]) * Math.sin(position[1]) * Math.sqrt(position[0] * position[1]); 
            }
                    
        });
        swarm.setInertia(0.95);
        swarm.setMaxPosition(100);
        swarm.setMinPosition(0);
        swarm.setMaxMinVelocity(0.1);

        int numberOfIterations = 10000;

        for( int i = 0; i < numberOfIterations; i++ )
            swarm.evolve();

        System.out.println(swarm.toStringStats());
    }
    
    public static void main(String[] args) { 
        new AlpineFunction().optimize();
    }
    
}
