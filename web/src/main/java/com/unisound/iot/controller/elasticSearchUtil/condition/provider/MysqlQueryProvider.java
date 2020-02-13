package com.unisound.iot.controller.elasticSearchUtil.condition.provider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class MysqlQueryProvider {

    @NonNull
    private String selectClause;
    @NonNull
    private String fromClause;
    @NonNull
    private String whereClause;
    private String orderClause;
    private String groupClause;
    private String pageClause;
    private Integer pageNum;
    private Integer pageSize;

    public void setSelectClause(String selectClause) {
        this.selectClause = this.removeKeyWord("select", selectClause);
    }

    public void setFromClause(String fromClause) {
        this.fromClause = this.removeKeyWord("from", fromClause);
    }

    public void setWhereClause(String whereClause) {
        if (StringUtils.hasText(whereClause)) {
            this.whereClause = this.removeKeyWord("where", whereClause);
        }

    }

    public MysqlQueryProvider setOrderClause(String orderClause) {
//        StringJoiner keyWordString = new StringJoiner(" ", "order by", null);
//        sortKeys.forEach((k, v) ->
//                keyWordString.add(k).add(v.name())
//        );
        this.orderClause = orderClause;
        return this;
    }

    public void setGroupClause(String groupClause) {
        if (StringUtils.hasText(groupClause)) {
            this.groupClause = this.removeKeyWord("group by", groupClause);
        } else {
            this.groupClause = null;
        }

    }

    public MysqlQueryProvider setPageClause(Integer pageNum, Integer pageSize) {
        if (null != pageNum && null != pageSize) {
            this.pageClause = "limit " + (pageNum - 1) + "," + pageSize;
        }
        return this;
    }

    public String getSentence() {
        String sentence = "SELECT %s FROM %s WHERE 1=1 AND %s";
        String result = String.format(sentence, this.selectClause, this.fromClause, this.whereClause);
        if (StringUtils.hasText(this.orderClause)) {
            result = result + " order by " + this.orderClause;
        }
        if (StringUtils.hasText(this.pageClause)) {
            result = result + " " + this.pageClause;
        }
        return result;
    }

    private String removeKeyWord(String keyWord, String clause) {
        String temp = clause.trim();
        String keyWordString = keyWord + " ";
        return temp.toLowerCase().startsWith(keyWordString) && temp.length() > keyWordString.length() ? temp.substring(keyWordString.length()) : temp;
    }
}