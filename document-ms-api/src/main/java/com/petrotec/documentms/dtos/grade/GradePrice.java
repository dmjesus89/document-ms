package com.petrotec.documentms.dtos.grade;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GradePrice
 */
@Deprecated
public class GradePrice {
    @JsonProperty("GradeId")
    private final String gradeCode;
    @JsonProperty("NewPrice")
    private final String price;

    public GradePrice(String gradeCode, String price) {
        this.gradeCode = gradeCode;
        this.price = price;
    }

    public String getGradeCode() {
        return this.gradeCode;
    }

    public String getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GradePrice)) {
            return false;
        }
        GradePrice gradePrice = (GradePrice) o;
        return Objects.equals(gradeCode, gradePrice.gradeCode) && Objects.equals(price, gradePrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradeCode, price);
    }

    @Override
    public String toString() {
        return "{" + " gradeCode='" + getGradeCode() + "'" + ", price='" + getPrice() + "'" + "}";
    }
}