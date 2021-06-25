package com.lifesense.formula;

/**
 * Scale User Info
 */
public class LSFormulaUser {

    /**
     * 是否男性
     */
    private boolean isMale;

    /**
     * 用户体重，单位kg
     */
    private double weight;

    /**
     * 用户身高，单位M
     */
    private double height;

    /**
     * 用户年龄
     */
    private int age;

    /**
     * 是否运动员
     */
    private boolean isAthlete;

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAthlete() {
        return isAthlete;
    }

    public void setAthlete(boolean athlete) {
        isAthlete = athlete;
    }

    @Override
    public String toString() {
        return "LSFormulaUser{" +
                "isMale=" + isMale +
                ", weight=" + weight +
                ", height=" + height +
                ", age=" + age +
                ", isAthlete=" + isAthlete +
                '}';
    }
}
