package com.siam.package_order.model.example.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PointsMallOrderRefundGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PointsMallOrderRefundGoodsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdIsNull() {
            addCriterion("order_refund_id is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdIsNotNull() {
            addCriterion("order_refund_id is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdEqualTo(Integer value) {
            addCriterion("order_refund_id =", value, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdNotEqualTo(Integer value) {
            addCriterion("order_refund_id <>", value, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdGreaterThan(Integer value) {
            addCriterion("order_refund_id >", value, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_refund_id >=", value, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdLessThan(Integer value) {
            addCriterion("order_refund_id <", value, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_refund_id <=", value, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdIn(List<Integer> values) {
            addCriterion("order_refund_id in", values, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdNotIn(List<Integer> values) {
            addCriterion("order_refund_id not in", values, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdBetween(Integer value1, Integer value2) {
            addCriterion("order_refund_id between", value1, value2, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderRefundIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_refund_id not between", value1, value2, "orderRefundId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdIsNull() {
            addCriterion("order_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdIsNotNull() {
            addCriterion("order_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdEqualTo(Integer value) {
            addCriterion("order_detail_id =", value, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdNotEqualTo(Integer value) {
            addCriterion("order_detail_id <>", value, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdGreaterThan(Integer value) {
            addCriterion("order_detail_id >", value, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_detail_id >=", value, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdLessThan(Integer value) {
            addCriterion("order_detail_id <", value, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_detail_id <=", value, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdIn(List<Integer> values) {
            addCriterion("order_detail_id in", values, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdNotIn(List<Integer> values) {
            addCriterion("order_detail_id not in", values, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("order_detail_id between", value1, value2, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallOrderDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_detail_id not between", value1, value2, "orderDetailId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameIsNull() {
            addCriterion("goods_name is null");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameIsNotNull() {
            addCriterion("goods_name is not null");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameEqualTo(String value) {
            addCriterion("goods_name =", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameNotEqualTo(String value) {
            addCriterion("goods_name <>", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameGreaterThan(String value) {
            addCriterion("goods_name >", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameGreaterThanOrEqualTo(String value) {
            addCriterion("goods_name >=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameLessThan(String value) {
            addCriterion("goods_name <", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameLessThanOrEqualTo(String value) {
            addCriterion("goods_name <=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameLike(String value) {
            addCriterion("goods_name like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameNotLike(String value) {
            addCriterion("goods_name not like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameIn(List<String> values) {
            addCriterion("goods_name in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameNotIn(List<String> values) {
            addCriterion("goods_name not in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameBetween(String value1, String value2) {
            addCriterion("goods_name between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andPointsMallGoodsNameNotBetween(String value1, String value2) {
            addCriterion("goods_name not between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andMainImageIsNull() {
            addCriterion("main_image is null");
            return (Criteria) this;
        }

        public Criteria andMainImageIsNotNull() {
            addCriterion("main_image is not null");
            return (Criteria) this;
        }

        public Criteria andMainImageEqualTo(String value) {
            addCriterion("main_image =", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotEqualTo(String value) {
            addCriterion("main_image <>", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageGreaterThan(String value) {
            addCriterion("main_image >", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageGreaterThanOrEqualTo(String value) {
            addCriterion("main_image >=", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageLessThan(String value) {
            addCriterion("main_image <", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageLessThanOrEqualTo(String value) {
            addCriterion("main_image <=", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageLike(String value) {
            addCriterion("main_image like", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotLike(String value) {
            addCriterion("main_image not like", value, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageIn(List<String> values) {
            addCriterion("main_image in", values, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotIn(List<String> values) {
            addCriterion("main_image not in", values, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageBetween(String value1, String value2) {
            addCriterion("main_image between", value1, value2, "mainImage");
            return (Criteria) this;
        }

        public Criteria andMainImageNotBetween(String value1, String value2) {
            addCriterion("main_image not between", value1, value2, "mainImage");
            return (Criteria) this;
        }

        public Criteria andSpecListIsNull() {
            addCriterion("spec_list is null");
            return (Criteria) this;
        }

        public Criteria andSpecListIsNotNull() {
            addCriterion("spec_list is not null");
            return (Criteria) this;
        }

        public Criteria andSpecListEqualTo(String value) {
            addCriterion("spec_list =", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListNotEqualTo(String value) {
            addCriterion("spec_list <>", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListGreaterThan(String value) {
            addCriterion("spec_list >", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListGreaterThanOrEqualTo(String value) {
            addCriterion("spec_list >=", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListLessThan(String value) {
            addCriterion("spec_list <", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListLessThanOrEqualTo(String value) {
            addCriterion("spec_list <=", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListLike(String value) {
            addCriterion("spec_list like", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListNotLike(String value) {
            addCriterion("spec_list not like", value, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListIn(List<String> values) {
            addCriterion("spec_list in", values, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListNotIn(List<String> values) {
            addCriterion("spec_list not in", values, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListBetween(String value1, String value2) {
            addCriterion("spec_list between", value1, value2, "specList");
            return (Criteria) this;
        }

        public Criteria andSpecListNotBetween(String value1, String value2) {
            addCriterion("spec_list not between", value1, value2, "specList");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("number not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andSubtotalIsNull() {
            addCriterion("subtotal is null");
            return (Criteria) this;
        }

        public Criteria andSubtotalIsNotNull() {
            addCriterion("subtotal is not null");
            return (Criteria) this;
        }

        public Criteria andSubtotalEqualTo(BigDecimal value) {
            addCriterion("subtotal =", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalNotEqualTo(BigDecimal value) {
            addCriterion("subtotal <>", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalGreaterThan(BigDecimal value) {
            addCriterion("subtotal >", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("subtotal >=", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalLessThan(BigDecimal value) {
            addCriterion("subtotal <", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("subtotal <=", value, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalIn(List<BigDecimal> values) {
            addCriterion("subtotal in", values, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalNotIn(List<BigDecimal> values) {
            addCriterion("subtotal not in", values, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("subtotal between", value1, value2, "subtotal");
            return (Criteria) this;
        }

        public Criteria andSubtotalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("subtotal not between", value1, value2, "subtotal");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}