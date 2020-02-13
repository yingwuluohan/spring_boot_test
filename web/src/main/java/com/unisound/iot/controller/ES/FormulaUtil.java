package com.unisound.iot.controller.ES;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unisound.iot.controller.ES.modle.Parameters;
import com.unisound.iot.controller.jdk.lambad.DataSource;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FormulaUtil extends AbstractEvaluator<String> {
    private Map<Integer, String> formulaMap = Maps.newHashMap();
    private static final Operator AND;
    private static final Operator OR;
    private static final Parameters PARAMETERS;
    private static DataSource dataSource;

    private FormulaUtil() {
        super(PARAMETERS);
    }

    public FormulaUtil(DataSource dataSource) {
        super(PARAMETERS);
        dataSource = dataSource;
    }

    protected String toValue(String literal, Object evaluationContext) {
        return literal;
    }

    private String getValue(String literal) {
        String str = null;

        try {
            str = (String)this.formulaMap.get(Integer.valueOf(literal));
            return str;
        } catch (Exception var4) {
            return literal;
        }
    }

    protected String evaluate(Operator operator, Iterator<String> operands, Object evaluationContext) {
        List<String> tree = (List)evaluationContext;
        String o1 = (String)operands.next();
        String o2 = (String)operands.next();
        String sequence = null;
        String qb1;
        String qb2;
        if (dataSource.equals(DataSource.MYSQL)) {
            if (operator == OR) {
                qb1 = this.getValue(o1);
                qb2 = this.getValue(o2);
                sequence = "(" + qb1 + " OR " + qb2 + ")";
            } else {
                if (operator != AND) {
                    throw new IllegalArgumentException();
                }

                qb1 = this.getValue(o1);
                qb2 = this.getValue(o2);
                sequence = "(" + qb1 + " AND " + qb2 + ")";
            }
        } else if (dataSource.equals(DataSource.ELASTICSEARCH)) {
            if (operator == OR) {
                qb1 = this.getValue(o1);
                qb2 = this.getValue(o2);
                sequence = "{\"bool\":{\"should\":[" + qb1 + "," + qb2 + "]}}";
            } else {
                if (operator != AND) {
                    throw new IllegalArgumentException();
                }

                qb1 = this.getValue(o1);
                qb2 = this.getValue(o2);
                sequence = "{\"bool\":{\"must\":[" + qb1 + "," + qb2 + "]}}";
            }
        }

        tree.add(sequence);
        return sequence;
    }

    public String doIt(FormulaUtil evaluator, String expression, Map<Integer, String> formulaMapParam) {
        this.formulaMap = formulaMapParam;
        List<String> sequence = Lists.newArrayList();
        evaluator.evaluate(expression, sequence);
        return (String)sequence.get(sequence.size() - 1);
    }

    static {
        AND = new Operator("and", 2, Operator.Associativity.LEFT, 2);
        OR = new Operator("or", 2, Operator.Associativity.LEFT, 1);
        PARAMETERS = new Parameters();
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        PARAMETERS.addExpressionBracket(BracketPair.PARENTHESES);
    }
}
