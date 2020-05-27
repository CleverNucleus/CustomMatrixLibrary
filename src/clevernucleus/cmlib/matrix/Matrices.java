package clevernucleus.cmlib.matrix;

public class Matrices {
	
	/**
	 * Creates a Matrix object from an array matrix.
	 * @param par0 Array matrix.
	 * @return Returns the Matrix object. if input is null then the output is a 1x1 empty Matrix object.
	 */
	public static Matrix from(double[][] par0) {
		if(par0 == null) return new Matrix(1, 1);
		
		Matrix var0 = new Matrix(par0.length, par0[0].length);
		
		for(int var1 = 0; var1 < par0.length; var1++) {
			for(int var2 = 0; var2 < par0[0].length; var2++) {
				var0.set(var1, var2, par0[var1][var2]);
			}
		}
		
		return var0;
	}
	
	/**
	 * Adds two matrices together. Both Matrices must have the same row and column size.
	 * @param par0 Matrix 1
	 * @param par1 Matrix 2
	 * @return A new Matrix, with values from Matrix 1 + Matrix 2. If the rows or columns of Matrix 1 or 2 are not equal, respectively, an empty Matrix of dimensions Matrix 1 is returned.
	 */
	public static Matrix add(Matrix par0, Matrix par1) {
		Matrix var0 = new Matrix(par0.rows(), par0.columns());
		
		if(par0.rows() == par1.rows() && par0.columns() == par1.columns()) {
			for(int var1 = 0; var1 < par0.rows(); var1++) {
				for(int var2 = 0; var2 < par0.columns(); var2++) {
					var0.set(var1, var2, par0.get(var1, var2) + par1.get(var1, var2));
				}
			}
		}
		
		return var0;
	}
	
	/**
	 * Subtracts Matrix 2 from Matrix 1. Both Matrices must have the same row and column size.
	 * @param par0 Matrix 1
	 * @param par1 Matrix 2
	 * @return A new Matrix, with values from Matrix 1 - Matrix 2. If the rows or columns of Matrix 1 or 2 are not equal, respectively, an empty Matrix of dimensions Matrix 1 is returned.
	 */
	public static Matrix subtract(Matrix par0, Matrix par1) {
		Matrix var0 = new Matrix(par0.rows(), par0.columns());
		
		if(par0.rows() == par1.rows() && par0.columns() == par1.columns()) {
			for(int var1 = 0; var1 < par0.rows(); var1++) {
				for(int var2 = 0; var2 < par0.columns(); var2++) {
					var0.set(var1, var2, par0.get(var1, var2) - par1.get(var1, var2));
				}
			}
		}
		
		return var0;
	}
	
	/**
	 * TODO; currently works for perfect squares (i.e. both args must have equal columns and rows, and rows must be equal to columns.
	 * @param par0
	 * @param par1
	 * @return
	 */
	public static Matrix multiply(Matrix par0, Matrix par1) {
		if(par0.columns() != par1.rows()) return par0;
		
		Matrix var0 = new Matrix(par0.rows(), par1.columns());
		
		double[][] var1 = new double[par0.rows()][par0.columns()];
		
		for(int var2 = 0; var2 < par0.rows(); var2++) {
			for(int var3 = 0; var3 < par0.columns(); var3++) {
				var1[var2][var3] = 0D;
				
				for(int var4 = 0; var4 < par0.rows(); var4++) {
					var1[var2][var3] += (par0.get(var2, var4) * par1.get(var4, var3));
				}
			}
		}
		
		return var0 = from(var1);
	}
}
