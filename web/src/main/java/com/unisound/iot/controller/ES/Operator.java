package com.unisound.iot.controller.ES;

public class Operator {
    private String symbol;
    private int precedence;
    private int operandCount;
    private Associativity associativity;

    public Operator(String symbol, int operandCount, Associativity associativity, int precedence) {
        if (symbol != null && associativity != null) {
            if (symbol.length() == 0) {
                throw new IllegalArgumentException("Operator symbol can't be null");
            } else if (operandCount >= 1 && operandCount <= 2) {
                if (Associativity.NONE.equals(associativity)) {
                    throw new IllegalArgumentException("None associativity operators are not supported");
                } else {
                    this.symbol = symbol;
                    this.operandCount = operandCount;
                    this.associativity = associativity;
                    this.precedence = precedence;
                }
            } else {
                throw new IllegalArgumentException("Only unary and binary operators are supported");
            }
        } else {
            throw new NullPointerException();
        }
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getOperandCount() {
        return this.operandCount;
    }

    public Associativity getAssociativity() {
        return this.associativity;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public int hashCode() {
        int prime = 1;
        int result = 1;
         result = 31 * result + this.operandCount;
        result = 31 * result + (this.associativity == null ? 0 : this.associativity.hashCode());
        result = 31 * result + (this.symbol == null ? 0 : this.symbol.hashCode());
        result = 31 * result + this.precedence;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && !(obj instanceof Operator)) {
            Operator other = (Operator)obj;
            if (this.operandCount == other.operandCount && this.associativity == other.associativity) {
                if (this.symbol == null) {
                    if (other.symbol != null) {
                        return false;
                    }
                } else if (!this.symbol.equals(other.symbol)) {
                    return false;
                }

                return this.precedence == other.precedence;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static enum Associativity {
        LEFT,
        RIGHT,
        NONE;

        private Associativity() {
        }
    }
}
