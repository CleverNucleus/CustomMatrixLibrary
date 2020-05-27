package clevernucleus.cmlib.matrix;

import java.util.Arrays;

/**
 * A matrix object.
 *
 */
public class Matrix {
	private int rows, columns;
	private double[][] matrix;
	
	/**
	 * Constructor. Leave par2 Values empty to set all values to 0.
	 * @param par0 Number of rows.
	 * @param par1 Number of columns.
	 * @param par2 Value entries, from left to right, up to down. 
	 */
	public Matrix(int par0, int par1, double ... par2) {
		this.rows = par0;
		this.columns = par1;
		this.matrix = new double[par0][par1];
		
		int var0 = 0;
		
		for(int var1 = 0; var1 < par0; var1++) {
			for(int var2 = 0; var2 < par1; var2++) {
				this.matrix[var1][var2] = (par2.length == (par0 * par1) ? par2[var0] : 0D);
				
				var0++;
			}
		}
	}
	
	/**
	 * Sets a value in a matrix.
	 * @param par0 Row.
	 * @param par1 Column.
	 * @param par2 Value.
	 * @return The Matrix object.
	 */
	public Matrix set(int par0, int par1, double par2) {
		if(par0 < this.rows && par0 >= 0 && par1 < this.columns && par1 >= 0) {
			this.matrix[par0][par1] = par2;
		}
		
		return this;
	}
	
	/**
	 * Sets all values in a matrix.
	 * @param par2 Values.
	 * @return The Matrix object.
	 */
	public Matrix set(double ... par2) {
		if(par2.length == (this.rows * this.columns)) {
			int var0 = 0;
			
			for(int var1 = 0; var1 < this.rows; var1++) {
				for(int var2 = 0; var2 < this.columns; var2++) {
					this.matrix[var1][var2] = par2[var0];
					
					var0++;
				}
			}
		}
		
		return this;
	}
	
	/**
	 * Gets this objects respective identity matrix.
	 * @return The Matrix object.
	 */
	public Matrix identity() {
		if(!isSquare()) return this;
		
		Matrix var0 = new Matrix(this.rows, this.columns);
		
		for(int var1 = 0; var1 < this.rows; var1++) {
			for(int var2 = 0; var2 < this.columns; var2++) {
				var0.set(var1, var2, (var1 == var2 ? 1D : 0D));
			}
		}
		
		return var0;
	}
	
	/**
	 * Finds the determinant of this matrix.
	 * @return If square, the determinant; if not, 0D.
	 */
	public double det() {
		if(!isSquare()) return 0.0D;
		if(this.size() == 4) return det2(this);
		
		return det3(this);
	}
	
	/**
	 * Finds the determinant of a 2x2 matrix.
	 * @param par0 The 2x2 matrix input.
	 * @return The determinant of Matrix par0.
	 */
	private double det2(Matrix par0) {
		return (par0.get(0, 0) * par0.get(1, 1)) - (par0.get(0, 1) * par0.get(1, 0));
	}
	
	/**
	 * Finds the determinant of the square matrix input. Must be square.
	 * @param par0 The matrix input; if larger than 2x2, uses recursion to solve.
	 * @return The determinant of Matrix par0.
	 */
	private double det3(Matrix par0) {
		double var0 = 0.0D;
		
		for(int var = 0; var < par0.rows(); var++) {
			Matrix var1 = subMatrixFromPoint(par0, 0, var);
			
			var0 += (((var % 2) == 0 ? 1 : -1) * (par0.get(0, var)) * (var1.size() == 4 ? det2(var1) : det3(var1)));
		}
		
		return var0;
	}
	
	/**
	 * Used to create a matrix from rows and columns in the input matrix excluding the rows and columns that pass through the input coordinates.
	 * @param par0 Matrix input.
	 * @param par1 Row input.
	 * @param par2 Column input.
	 * @return Returns a matrix comprising of the input Matrix of a magnitude of 1 less and without rows par1 and columns par2. Returns the input matrix if it is not square or if it is 2x2.
	 */
	public Matrix subMatrixFromPoint(Matrix par0, int par1, int par2) {
		if(par0.size() == 4 || !par0.isSquare()) return par0;
		
		int var0 = par0.rows() - 1;
		int var1 = (int)Math.pow(var0, 2);
		int var2 = 0;
		
		Matrix var3 = new Matrix(var0, var0);
		
		double[] var4 = new double[var1];
		
		for(int var5 = 0; var5 < par0.rows(); var5++) {
			for(int var6 = 0; var6 < par0.columns(); var6++) {
				if(var5 != par1 && var6 != par2) {
					var4[var2] = par0.get(var5, var6);
					
					var2++;
				}
			}
		}
		
		var3.set(var4);
		
		return var3;
	}
	
	/**
	 * Finds the transpose of the matrix. Does not have to be square.
	 * @return The transpose of this matrix.
	 */
	public Matrix transpose() {
		Matrix var0 = new Matrix(this.columns, this.rows);
		
		for(int var1 = 0; var1 < this.rows; var1++) {
			for(int var2 = 0; var2 < this.columns; var2++) {
				var0.set(var2, var1, this.get(var1, var2));
			}
		}
		
		return var0;
	}
	
	/**
	 * Finds the adjugate of this matrix. Uses manual method for matrices equal to or smaller than 2x2.
	 * @return The adjugate of this matrix. If it is not square, returns this. 
	 */
	public Matrix adjugate() {
		if(!isSquare()) return this;
		
		Matrix var0 = new Matrix(this.rows, this.columns);
		
		if(this.size() == 4) {
			var0.set(0, 0, this.get(1, 1));
			var0.set(0, 1, (-1D) * this.get(0, 1));
			var0.set(1, 0, (-1D) * this.get(1, 0));
			var0.set(1, 1, this.get(0, 0));
		} else {
			Matrix var2 = this.transpose();
			
			for(int var4 = 0; var4 < this.rows; var4++) {
				for(int var5 = 0; var5 < this.columns; var5++) {
					var0.set(var4, var5, ((var4 + var5) % 2 == 0 ? 1D : -1D) * subMatrixFromPoint(var2, var4, var5).det());
				}
			}
		}
		
		return var0;
	}
	
	/**
	 * Finds the inverse of this matrix.
	 * @return If this matrix is square and the determinant does not equal zero, returns the inverse; if not, returns this matrix.
	 */
	public Matrix inverse() {
		if(!isSquare() || det() == 0D) return this;
		
		Matrix var0 = adjugate();
		
		double var1 = 1.0D / det();
		
		for(int var2 = 0; var2 < this.rows; var2++) {
			for(int var3 = 0; var3 < this.columns; var3++) {
				var0.set(var2, var3, var1 * var0.get(var2, var3));
			}
		}
		
		return var0;
	}
	
	/**
	 * Gets a row of values (array).
	 * @param par0 Row.
	 * @return Row array.
	 */
	public double[] getRows(int par0) {
		if(par0 >= this.rows || par0 < 0) return this.matrix[0];
		
		return this.matrix[par0];
	}
	
	/**
	 * Gets a columns of values (array).
	 * @param par0 Column.
	 * @return Column array.
	 */
	public double[] getColumns(int par0) {
		double[] var0 = new double[this.rows];
		
		if(par0 < this.columns && par0 >= 0) {
			for(int var = 0; var < this.rows; var++) {
				var0[var] = this.matrix[var][par0];
			}
		} else {
			for(int var = 0; var < this.rows; var++) {
				var0[var] = this.matrix[var][0];
			}
		}
		
		return var0;
	}
	
	/**
	 * Gets a value.
	 * @param par0 Row.
	 * @param par1 Column.
	 * @return Value in Row and Column.
	 */
	public double get(int par0, int par1) {
		if(par0 >= this.rows || par0 < 0 || par1 >= this.columns || par1 < 0) return 0D;
		
		return this.matrix[par0][par1];
	}
	
	/**
	 * Gets the matrix in double array format.
	 * @return double array.
	 */
	public double[][] get() {
		return this.matrix;
	}
	
	/**
	 * The size of the matrix object.
	 * @return The size (rows x columns).
	 */
	public int size() {
		return this.rows * this.columns;
	}
	
	/**
	 * Gets the number of rows.
	 * @return Rows.
	 */
	public int rows() {
		return this.rows;
	}
	
	/**
	 * Gets the number of columns.
	 * @return Columns.
	 */
	public int columns() {
		return this.columns;
	}
	
	/**
	 * Checks to see if this matrix is square.
	 * @return true if square.
	 */
	public boolean isSquare() {
		return this.columns == this.rows;
	}
	
	@Override
	public String toString() {
		String var0 = "";
		
		for(int var1 = 0; var1 < this.rows; var1++) {
			var0 += Arrays.toString(this.matrix[var1]);
		}
		
		return "{" + var0 + "}";
	}
}
