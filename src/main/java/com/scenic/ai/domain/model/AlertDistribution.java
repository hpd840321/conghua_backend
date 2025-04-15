package com.scenic.ai.domain.model;

import java.util.List;
import java.util.Objects;

/**
 * 告警分布信息领域模型
 */
public class AlertDistribution {
    private List<String> labels;
    private List<Integer> values;
    private Integer total;
    
    public AlertDistribution() {
    }
    
    public AlertDistribution(List<String> labels, List<Integer> values, Integer total) {
        this.labels = labels;
        this.values = values;
        this.total = total;
    }
    
    public List<String> getLabels() {
        return labels;
    }
    
    public AlertDistribution setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }
    
    public List<Integer> getValues() {
        return values;
    }
    
    public AlertDistribution setValues(List<Integer> values) {
        this.values = values;
        return this;
    }
    
    public Integer getTotal() {
        return total;
    }
    
    public AlertDistribution setTotal(Integer total) {
        this.total = total;
        return this;
    }
    
    public void validate() {
        Objects.requireNonNull(labels, "标签列表不能为空");
        Objects.requireNonNull(values, "值列表不能为空");
        Objects.requireNonNull(total, "总数不能为空");
        if (labels.size() != values.size()) {
            throw new IllegalArgumentException("标签列表和值列表的长度必须相同");
        }
    }
    
    public static AlertDistribution create(List<String> labels, List<Integer> values) {
        AlertDistribution distribution = new AlertDistribution();
        distribution.setLabels(labels);
        distribution.setValues(values);
        distribution.setTotal(values.stream().mapToInt(Integer::intValue).sum());
        distribution.validate();
        return distribution;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertDistribution that = (AlertDistribution) o;
        return Objects.equals(labels, that.labels) &&
               Objects.equals(values, that.values) &&
               Objects.equals(total, that.total);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(labels, values, total);
    }
    
    @Override
    public String toString() {
        return "AlertDistribution{" +
               "labels=" + labels +
               ", values=" + values +
               ", total=" + total +
               '}';
    }
} 