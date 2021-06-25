package com.lifesense.formula;

/**
 * 算法得出的18项体脂数据
 *
 */
public class LSWeightCalcuateData extends LSBodyCompositionData {

//    /**
//     * 体重 kg
//     */
//    private double weight;
//
//    /**
//     * BMI
//     */
//    private double bmi;
//    /**
//     * 脂肪率 %
//     */
//    private double pbf;
//
//
//    /**
//     * 肌肉量 kg
//     */
//    private double muscle;
//
//
//    /**
//     * 基础代谢量 千卡
//     */
//    private double basalMetabolism;
//
//    /**
//     * 水分率  %
//     */
//    private double water;
//
//    /**
//     * 骨量 kg
//     */
//    private double bone;


    /**
     * 去脂体重 kg
     */
    private double ffm;

    /**
     * 脂肪量 kg
     */
    private double bfm;

    //50K电阻值
    private double resistance50k;

    /**
     * 肌肉率 %
     */
    private double rateOfMuscle;


    /**
     * 骨骼肌 kg
     */
    private double skeletonMuscle;

    /**
     * 身体年龄
     */
    private int bodyAge;
    /**
     * 内脏脂肪等级
     */
    private double visceralFat;

    /**
     *蛋白质 %
     */
    private double protein;
    /**
     * 体型 详情请查看{@link LSBodyType}
     */
    private int bodyType;
    /**
     * 身体得分 19-100分
     */
    private int bodyScore;
    /**
     * 肌肉控制 kg 大于0表示需要增加,小于0表示要减少
     */
    private double muscleControl;
    /**
     * 脂肪控制 kg 大于0表示需要增加,小于0表示要减少
     */
    private double fatControl;

    /**
     * 调用算法入参的阻抗信息
     * @return
     */
    public double getResistance50k() {
        return resistance50k;
    }

    protected void setResistance50k(double resistance50k) {
        this.resistance50k = resistance50k;
    }

    /**
     * 当前这笔数据体重信息，单位kg
     * @return
     */
    public double getWeight() {
        return weight;
    }

//    protected void setWeight(double weight) {
//        this.weight = weight;
//    }

    /**
     * 获取去脂体重，单位kg
     * @return
     */
    public double getFfm() {
        return ffm;
    }

    protected void setFfm(double ffm) {
        this.ffm = ffm;
    }

    /**
     * 获取BMI指数，采用国际标准
     * @return
     */
    public double getBmi() {
        return bmi;
    }

//    protected void setBmi(double bmi) {
//        this.bmi = bmi;
//    }

    /**
     * 获取脂肪率，单位%
     * @return
     */
    public double getPbf() {
        return this.pbf;
    }

//    protected void setPbf(double pbf) {
//        this.pbf = pbf;
//    }

    /**
     * 获取脂肪量，单位kg
     * @return
     */
    public double getBfm() {
        return bfm;
    }

    protected void setBfm(double bfm) {
        this.bfm = bfm;
    }

    /**
     * 获取肌肉率，单位%
     * @return
     */
    public double getRateOfMuscle() {
        return rateOfMuscle;
    }

    protected void setRateOfMuscle(double rateOfMuscle) {
        this.rateOfMuscle = rateOfMuscle;
    }

    /**
     * 获取肌肉量，单位kg
     * @return
     */
    public double getMuscle() {
        return muscle;
    }


    /**
     * 获取骨络肌，单位kg
     * @return
     */
    public double getSkeletonMuscle() {
        return skeletonMuscle;
    }

    protected void setSkeletonMuscle(double skeletonMuscle) {
        this.skeletonMuscle = skeletonMuscle;
    }

    /**
     * 获取基础代谢，单位千卡
     * @return
     */
    public double getBasalMetabolism() {
        return basalMetabolism;
    }

//    protected void setBasalMetabolism(double basalMetabolism) {
//        this.basalMetabolism = basalMetabolism;
//    }

    /**
     * 获取身体年龄
     * @return
     */
    public int getBodyAge() {
        return bodyAge;
    }

    protected void setBodyAge(int bodyAge) {
        this.bodyAge = bodyAge;
    }

    /**
     * 获取内脏脂肪等级（内脏脂肪指数）
     * @return
     */
    public double getVisceralFat() {
        return visceralFat;
    }

    protected void setVisceralFat(double visceralFat) {
        this.visceralFat = visceralFat;
    }

    /**
     * 获取水分率参数，单位%
     * @return
     */
    public double getWater() {
        return water;
    }

//    protected void setWater(double water) {
//        this.water = water;
//    }

    /**
     * 获取骨量参数，单位kg
     * @return
     */
    public double getBone() {
        return bone;
    }

//    protected void setBone(double bone) {
//        this.bone = bone;
//    }

    /**
     * 获取蛋白质参数，单位%
     * @return
     */
    public double getProtein() {
        return protein;
    }

    protected void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * 获取体型参数，体型对照表请查看 {@link LSBodyType}
     * @return
     */
    public int getBodyType() {
        return bodyType;
    }

    protected void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * 获取身体得分
     * @return
     */
    public int getBodyScore() {
        return bodyScore;
    }

    protected void setBodyScore(int bodyScore) {
        this.bodyScore = bodyScore;
    }

    /**
     * 获取肌肉控制，单位kg，大于0表示需要增加，如果小于0，不推荐让用户减肌。
     * @return
     */
    public double getMuscleControl() {
        return muscleControl;
    }

    protected void setMuscleControl(double muscleControl) {
        this.muscleControl = muscleControl;
    }

    /**
     * 获取脂肪控制，单位kg，大于0表示需要增加，小于0表示要减少
     * @return
     */
    public double getFatControl() {
        return fatControl;
    }

    protected void setFatControl(double fatControl) {
        this.fatControl = fatControl;
    }

    @Override
    public String toString() {
        return "LSWeightCalcuateData{" +
                "resistance50k=" + resistance50k +
                ", weight=" + weight +
                ", ffm=" + ffm +
                ", bmi=" + bmi +
                ", pbf=" + pbf +
                ", bfm=" + bfm +
                ", rateOfMuscle=" + rateOfMuscle +
                ", muscle=" + muscle +
                ", skeletonMuscle=" + skeletonMuscle +
                ", basalMetabolism=" + basalMetabolism +
                ", bodyAge=" + bodyAge +
                ", visceralFat=" + visceralFat +
                ", water=" + water +
                ", bone=" + bone +
                ", protein=" + protein +
                ", bodyType=" + bodyType +
                ", bodyScore=" + bodyScore +
                ", muscleControl=" + muscleControl +
                ", fatControl=" + fatControl +
                '}';
    }
}
