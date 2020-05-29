package clevernucleus.cmlib;

import clevernucleus.cmlib.matrix.*;
/** Example use */
public class Main {
	private static final double mass_pipes = 4.83E4D, mass_topside = 10000D, k_pipes = 4.24E8D, k_topside = 2.23E9D;
	private static final Matrix M = new Matrix(2, 2, mass_pipes, 0D, 0D, mass_topside);
	private static final Matrix K = new Matrix(2, 2, k_pipes + k_topside, -k_topside, -k_topside, k_topside);
	
	public static void main(String[] par0) {
		Matrix A = Matrices.multiply(M.inverse(), K);
		double[] E = A.eigenvals();
		
		System.out.println("Freq: " + Math.sqrt(E[0]) + " Hz");
		System.out.println("Freq: " + Math.sqrt(E[1]) + " Hz");
	}
}
