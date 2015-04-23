package projecto2.grupo4.ricardoricardo;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.ServletException;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Named
@SessionScoped
public class Calculator implements Serializable {

	private static final long serialVersionUID = 1L;
	private String expression = "0";
	private boolean reset = false;

	public void key(ActionEvent event) {
		if (reset) {
			this.expression = "0";
			reset = false;
		}
		if (this.expression.equals("0")) {
			this.expression = "";
		}
		String add = "";
		switch(event.getComponent().getId()) {
		case "AC": {
			this.expression = "0";
			break;
		} case "moreLess": { 
			negative();
			break;
		} case "percent": {
			this.expression += "%";
			getPercentage();
			break;
		} case "divide": {
			add = "/"; break;
		} case "multiply": {
			add = "*";
			break;
		} case "plus": {
			add = "+";
			break;
		} case "minus": {
			add = "-";	
			break;
		} case "equal": {
			this.getResult();
			break;
		} case "remainder": {
			add = "%";
			break;
		} case "dot": {
			add = ".";
			break;
		} case "zero": {
			if (this.expression.length() > 0) {
				add = "0";
			} else {
				add = "";
			}
			break;
		} case "one": {
			add = "1";
			break;
		} case "two": {
			add = "2";
			break;
		} case "three": {
			add = "3";
			break;
		} case "four": {
			add = "4";
			break;
		} case "five": {
			add = "5";
			break;
		} case "six": {
			add = "6";
			break;
		} case "seven": {
			add = "7";
			break;
		} case "eight": {
			add = "8";
			break;
		} case "nine": {
			add = "9";
			break;
		}
		}
		this.addToExpression(add);
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void addToExpression(String expression) {
		this.expression += expression;
	}

	public void negative() {
		String[] exp = expression.split("[-+*/]");
		int i = expression.indexOf(exp[exp.length-1]); // índice do último número (depois do operador)
		String last = exp[exp.length-1]; // último número
		if (i == 0) {
			expression = "-" + expression;
		} else if (i == 1 && expression.charAt(0) == '-') {
			expression = expression.substring(1);
		} else {
			if (expression.substring(i-1, i).equals("-")) {
				if (expression.substring(i-2, i-1).equals("+") || expression.substring(i-2, i-1).equals("*") || expression.substring(i-2, i-1).equals("/")) {
					expression = expression.substring(0, i-1) + last;
				} else {
					expression = expression.substring(0, i-1) + "+" + last;
				}
			} else {
				Double r = Double.parseDouble(last);
				Double f = r * -1;
				int ind = expression.indexOf(last);
				expression = expression.substring(0, ind) + Double.toString(f);
			}
		}
	}
	
	public void getPercentage() {
		String[] exp = expression.split("[+-/*] && ");
		String per = "";
		int indArray = 0;
		Double result = 0.0;
		for (String s:exp) {
			if (s.contains("%")) {
				per = s.substring(0, s.length()-1);
				break;
			}
			indArray++;
		}
		int i = expression.indexOf(per + "%");
		if (i == 0) {
			Double num = Double.parseDouble(per);
			Double percentage = num/100;
			expression = Double.toString(percentage);
		} else {
			double num1 = Double.parseDouble(exp[indArray-1]);
			double num2 = Double.parseDouble(per);
			if (expression.charAt(i-1) == '+') {
				result = num1 + (num1*num2)/100;
			} else if (expression.charAt(i-1) == '+') {
				result = num1 - (num1*num2)/100;
			} else if (expression.charAt(i-1) == '*') {
				result = (num1*num2)/100;
			} else {
				result = (num1*100)/num2;
			}
			String r = Double.toString(result);
			int indDireita = expression.indexOf(exp[indArray-1]);
			expression = expression.substring(0, indDireita) + r;
		}
	}

	public void getResult() {
		Expression e = new ExpressionBuilder(expression)
		.build();
		try {
			double result = e.evaluate();
			expression = Double.toString(result);
		} catch (ArithmeticException ae) {
			expression = "Divisão por zero";
			this.reset = true;
		} catch (IllegalArgumentException ia) {
			expression = "Operação inválida";
			this.reset = true;
		}
	}

}
