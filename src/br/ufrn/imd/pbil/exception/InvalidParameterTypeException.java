package br.ufrn.imd.pbil.exception;

public class InvalidParameterTypeException extends Exception {

	private static final long serialVersionUID = -6993675188207153868L;

	public InvalidParameterTypeException() {
		super("This is an invalid type for parameter");
	}
		
}
