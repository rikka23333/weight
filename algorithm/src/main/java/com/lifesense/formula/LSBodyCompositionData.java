package com.lifesense.formula;

/**
 * <div class="en">Body Composition Data</div>
 * <div class="zh">身体成分数据</div>
 * 
 * @author sky
 */
public class LSBodyCompositionData {


	/**
	 * <div class="en">User's Weight,Unit:kg</div>
	 * <div class="zh">用户体重</div>
	 */
	protected double weight;

	/**
	 * BMI
	 */
	protected double bmi;

	/**
	 * <div class="en">Fat Rate,Unit:%</div>
	 * <div class="zh"> 脂肪率,单位:%</div>
	 */
	protected double pbf;

	/**
	 * <div class="en">Basal metabolism</div>
	 * <div class="zh">基础代谢</div>
	 */
	protected double basalMetabolism;


	/**
	 * <div class="en">Body Water</div>
	 * <div class="zh">身体水分含量</div>
	 */
	protected double water;

	
	/**
	 * <div class="en">Bone Density,Unit:kg</div>
	 * <div class="zh">骨质密度,单位:kg</div>
	 */
	protected double bone;

	/**
	 * <div class="en">Muscle Content,Unit:kg</div>
	 * <div class="zh">肌肉含量,单位:kg</div>
	 */
	protected double muscle;


	/**
	 * <div class="en">Impedance conversion based on resistance</div>
	 * <div class="zh">根据电阻值转换的阻抗</div>	
	 */
	protected double imp;


	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public double getPbf() {
		return pbf;
	}

	public void setPbf(double pdf) {
		this.pbf = pdf;
	}

	public double getBasalMetabolism() {
		return basalMetabolism;
	}

	public void setBasalMetabolism(double basalMetabolism) {
		this.basalMetabolism = basalMetabolism;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getBone() {
		return bone;
	}

	public void setBone(double bone) {
		this.bone = bone;
	}

	public double getMuscle() {
		return muscle;
	}

	public void setMuscle(double muscle) {
		this.muscle = muscle;
	}

	public double getImp() {
		return imp;
	}

	public void setImp(double imp) {
		this.imp = imp;
	}

	@Override
	public String toString() {
		return "LSBodyCompositionData{" +
				"weight=" + weight +
				", bmi=" + bmi +
				", pbf=" + pbf +
				", basalMetabolism=" + basalMetabolism +
				", water=" + water +
				", bone=" + bone +
				", muscle=" + muscle +
				", imp=" + imp +
				'}';
	}
}
