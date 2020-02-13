package com.unisound.iot.controller.ES;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unisound.iot.controller.ES.enummodle.Operator;
import com.unisound.iot.controller.ES.requestHand.BaseConditionRequest;
import com.unisound.iot.controller.ES.requestHand.DataBaseConditionRequest;
import com.unisound.iot.controller.jdk.lambad.DataSource;
import lombok.NonNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {
    private static final Map<DataSource, Map<Operator, ConditionWrapperHandle>> conditionWrapperHandleMap = Maps.newEnumMap(DataSource.class);

    static {
//        conditionWrapperHandleMap.put(DataSource.ELASTICSEARCH, elasticSearchConditionWrapperHandleMap);
//
//        conditionWrapperHandleMap.put(DataSource.MYSQL, mysqlConditionWrapperHandleMap);
//        conditionWrapperHandleMap.put(DataSource.SIMPLE_VALUE, simpleConditionWrapperHandleMap);
//        conditionWrapperHandleMap.put(DataSource.JAVABEAN, javaBeanConditionWrapperHandleMap);
    }

    public static Map<Integer, String> databaseSnippetConditionWrapper(@NonNull DataSource dataSource, @NonNull List<DataBaseConditionRequest> conditionList) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource");
        } else if (conditionList == null) {
            throw new NullPointerException("conditionList");
        } else {
            Preconditions.checkArgument(!dataSource.equals(DataSource.JAVABEAN));
            Preconditions.checkArgument(CollectionUtils.isNotEmpty(conditionList));
            Map<Integer, String> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
            Map<Operator, ConditionWrapperHandle> handleMap = (Map)conditionWrapperHandleMap.get(dataSource);
            int[] idx = new int[]{1};
            conditionList.forEach((i) -> {
                int var10004 = idx[0];
                int var10001 = idx[0];
                idx[0] = var10004 + 1;
                String var10000 = (String)formulaMap.put(var10001, String.valueOf(singleConditionWrapper(handleMap, i, dataSource)));
            });
            return formulaMap;
        }
    }

    private static Object singleConditionWrapper(Map<Operator, ConditionWrapperHandle> handleMap,
                                                 BaseConditionRequest condition, DataSource dataSource) {

            Operator operator = condition.getOperator();
            String joinSign = isNegativeOperator(operator) ? "or" : "and";
            List<BaseConditionRequest> conditionList = condition.getAttachmentList();
            condition.setAttachmentList((List)null);
            conditionList.add(condition);
            int[] idx = new int[]{1};
            LinkedHashMap formulaMap;
            if (!dataSource.equals(DataSource.JAVABEAN) && !dataSource.equals(DataSource.SIMPLE_VALUE)) {
                formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
                conditionList.forEach((i) -> {
                    int var10004 = idx[0];
                    int var10001 = idx[0];
                    idx[0] = var10004 + 1;
                    String var10000 = (String)formulaMap.put(var10001, String.valueOf(singleConditionWrapper(handleMap, i, dataSource)));
                });
                return addBracket(dataSource, databaseCombineConditionWrapper(dataSource, (Map)formulaMap, (String)joinSign));
            } else {
                formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
                conditionList.forEach((i) -> {
                    int var10004 = idx[0];
                    int var10001 = idx[0];
                    idx[0] = var10004 + 1;
                    Boolean var10000 = (Boolean)formulaMap.put(var10001, (Boolean)singleConditionWrapper(handleMap, i, dataSource));
                });
                return checkFormula(generateFullAndFormula(conditionList.size(), joinSign), formulaMap);
            }

    }
    private static boolean checkFormulaFormat(@NonNull String formula) {
        if (formula == null) {
            throw new NullPointerException("formula");
        } else {
            return checkBracket(formula) && formula.matches("\\s*\\(*\\s*([1-9]\\d*)(\\s+([Aa][Nn][Dd]|[Oo][Rr])\\s+\\(*\\s*([1-9]\\d*)\\s*\\)*)*\\s*\\)*\\s*");
        }
    }
    private static boolean checkBracket(@NonNull String formula) {
        if (formula == null) {
            throw new NullPointerException("formula");
        } else {
            Deque<Character> stack = new LinkedList();
            char[] array = formula.toCharArray();
            char[] var3 = array;
            int var4 = array.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                char c = var3[var5];
                switch(c) {
                    case '(':
                        stack.push(c);
                        break;
                    case ')':
                        if (!CollectionUtils.isNotEmpty(stack) || (Character)stack.peek() != '(') {
                            return false;
                        }

                        stack.pop();
                }
            }

            return CollectionUtils.isEmpty(stack);
        }
    }
    private static boolean checkFormula(@NonNull String formula, Map<Integer, Boolean> formulaMap) {
        if (formula == null) {
            throw new NullPointerException("formula");
        } else {
            String formatFormula = handleFormulaBeforeCheck(formula);
            if (!checkFormulaFormat(formatFormula)) {
                return false;
            } else {
                formatFormula = checkOrderAndReplaceValue(formatFormula, formulaMap);
                if (formatFormula == null) {
                    return false;
                } else {
                    SpelExpressionParser parser = new SpelExpressionParser();

                    try {
                        return (Boolean)parser.parseExpression(formatFormula).getValue(Boolean.class);
                    } catch (SpelEvaluationException | SpelParseException var5) {
                        return false;
                    }
                }
            }
        }
    }
    private static String checkOrderAndReplaceValue(@NonNull String formula, @NonNull Map<Integer, Boolean> formulaMap) {
        if (formula == null) {
            throw new NullPointerException("formula");
        } else if (formulaMap == null) {
            throw new NullPointerException("formulaMap");
        } else {
            Pattern regex = Pattern.compile("[1-9]\\d*");
            Matcher matcher = regex.matcher(formula);
            StringBuffer sb = new StringBuffer();

            while(matcher.find()) {
                Integer order = Integer.valueOf(matcher.group(0));
                if (!formulaMap.containsKey(order)) {
                    return null;
                }

                matcher.appendReplacement(sb, String.valueOf(formulaMap.get(order)));
            }

            matcher.appendTail(sb);
            return sb.toString();
        }
    }
    private static String handleFormulaBeforeCheck(@NonNull String formula) {
        if (formula == null) {
            throw new NullPointerException("formula");
        } else {
            return formula.replace('（', '(').replace('）', ')');
        }
    }


    public static String generateFullAndFormula(int number, @NonNull String joinSign) {
        if (joinSign == null) {
            throw new NullPointerException("joinSign");
        } else {
            Preconditions.checkArgument(number > 0);
            Preconditions.checkArgument("and".equals(joinSign) || "or".equals(joinSign));
            if (1 == number) {
                return "1";
            } else {
                StringJoiner sj = new StringJoiner(" " + joinSign + " ");

                for(int i = 1; i < number + 1; ++i) {
                    sj.add(String.valueOf(i));
                }

                return sj.toString();
            }
        }
    }
    public static String databaseCombineConditionWrapper(  DataSource dataSource, Map<Integer, String> snippetConditionMap, String joinSign) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource");
        } else {
            String formula = generateFullAndFormula(snippetConditionMap.size(), StringUtils.isEmpty(joinSign) ? "and" : joinSign);
            return databaseCombineConditionWrapper(dataSource, formula, snippetConditionMap);
        }
    }
    public static String databaseCombineConditionWrapper(@NonNull DataSource dataSource, @NonNull String formula, Map<Integer, String> snippetConditionMap) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource");
        } else if (formula == null) {
            throw new NullPointerException("formula");
        } else {
            Preconditions.checkArgument(!dataSource.equals(DataSource.JAVABEAN));
            if (1 == snippetConditionMap.size()) {
                return (String)snippetConditionMap.get(1);
            } else {
                FormulaUtil evaluator = new FormulaUtil(dataSource);
                return evaluator.doIt(evaluator, formula, snippetConditionMap);
            }
        }
    }
    private static String addBracket(@NonNull DataSource dataSource, String condition) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource");
        } else if (dataSource.equals(DataSource.ELASTICSEARCH)) {
            return condition;
        } else {
            return dataSource.equals(DataSource.MYSQL) ? String.format("(%s)", condition) : null;
        }
    }
    private static boolean isNegativeOperator(Operator operator) {
        List<Operator> negativeOperatorList = Lists.newArrayList(new Operator[]
                {Operator.NOT, Operator.NOT_ANY, Operator.NOT_BETWEEN, Operator.NOT_CONTAINS, Operator.NOT_CONTAINS_ALL, Operator.NOT_CONTAINS_ANY, Operator.NOT_IN_DATE, Operator.IS_NOT_NULL, Operator.PREFIX_NOT_CONTAINS, Operator.PREFIX_NOT_CONTAINS_ANY, Operator.SUFFIX_NOT_CONTAINS, Operator.SUFFIX_NOT_CONTAINS_ANY});
        return negativeOperatorList.contains(operator);
    }

//    public static String databaseConditionWrapper(@NonNull DataSource dataSource, @NonNull List<DataBaseConditionRequest> conditionList) {
//        if (dataSource == null) {
//            throw new NullPointerException("dataSource");
//        } else if (conditionList == null) {
//            throw new NullPointerException("conditionList");
//        } else {
//            Preconditions.checkArgument(!dataSource.equals(DataSource.JAVABEAN));
//            Map<Integer, String> snippetConditionMap = databaseSnippetConditionWrapper(dataSource, conditionList);
//            return databaseCombineConditionWrapper(dataSource, snippetConditionMap, "and");
//        }
//    }




}
