package com.unisound.iot.controller.elasticSearchUtil.condition.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unisound.iot.controller.ES.AbstractEvaluator;
import com.unisound.iot.controller.ES.BracketPair;
import com.unisound.iot.controller.ES.Operator;
import com.unisound.iot.controller.ES.modle.Parameters;
import com.unisound.iot.controller.elasticSearchUtil.condition.DataSource;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FormulaUtil extends AbstractEvaluator<String> {
    private Map<Integer, String> formulaMap = Maps.newHashMap();
    /**
     * The logical AND operator.
     */
    private static final Operator AND = new Operator("and", 2, Operator.Associativity.LEFT, 2);
    /**
     * The logical OR operator.
     */
    private static final Operator OR = new Operator("or", 2, Operator.Associativity.LEFT, 1);

    private static final Parameters PARAMETERS;

    private static DataSource dataSource;

    static {
        // Create the evaluator's parameters
        PARAMETERS = new Parameters();
        // Add the supported operators
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        // Add the parentheses
        PARAMETERS.addExpressionBracket(BracketPair.PARENTHESES);
    }

    private FormulaUtil() {
        super(PARAMETERS);
    }

    public FormulaUtil(DataSource dataSource) {
        super(PARAMETERS);
        FormulaUtil.dataSource = dataSource;
    }

    @Override
    protected String toValue(String literal, Object evaluationContext) {
        return literal;
    }

    private String getValue(String literal) {
        String str = null;
        try {
            str = formulaMap.get(Integer.valueOf(literal));
        } catch (Exception e) {
            return literal;
        }
        return str;
    }

    @Override
    protected String evaluate(Operator operator, Iterator<String> operands,
                              Object evaluationContext) {
        List<String> tree = (List<String>) evaluationContext;
        String o1 = operands.next();
        String o2 = operands.next();
        String qb1;
        String qb2;
        String sequence = null;
        if (dataSource.equals(DataSource.MYSQL)) {
            if (operator == OR) {
                qb1 = getValue(o1);
                qb2 = getValue(o2);
                sequence = "(" + qb1 + " OR " + qb2 + ")";
            } else if (operator == AND) {
                qb1 = getValue(o1);
                qb2 = getValue(o2);
                sequence = "(" + qb1 + " AND " + qb2 + ")";
            } else {
                throw new IllegalArgumentException();
            }
        } else if (dataSource.equals(DataSource.ELASTICSEARCH)) {
            if (operator == OR) {
                qb1 = getValue(o1);
                qb2 = getValue(o2);
                sequence = "{\"bool\":{\"should\":[" + qb1 + "," + qb2 + "]}}";
            } else if (operator == AND) {
                qb1 = getValue(o1);
                qb2 = getValue(o2);
                sequence = "{\"bool\":{\"must\":[" + qb1 + "," + qb2 + "]}}";
            } else {
                throw new IllegalArgumentException();
            }
        }


        tree.add(sequence);

        return sequence;
    }

    public String doIt(FormulaUtil evaluator, String expression, Map<Integer, String> formulaMapParam) {
        this.formulaMap = formulaMapParam;
        List<String> sequence = Lists.newArrayList();
        evaluator.evaluate(expression, sequence);
        return sequence.get(sequence.size() - 1);
    }
}
