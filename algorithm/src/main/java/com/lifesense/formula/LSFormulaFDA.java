package com.lifesense.formula;

import java.math.BigDecimal;

/**
 * FDA 身体成分数据项计算公式
 * @author sky
 *
 */
class LSFormulaFDA {


	private LSFormulaFDA() {
	}

	/**
	 * BMI 体质指数
	 * 
	 * @param weight
	 *            单位:kg
	 * @param height
	 *            单位:m
	 * @return
	 */
	public static double getBmi(double weight, double height) {
		if (check(height, weight))
			return 0;
		double bmi = weight / (height * height);
		return bmi;
	}

	/**
	 * 体脂
	 * 单位:百分比
	 */
	public static double getFat(double weight, double height, boolean isMale,
			boolean isAthlete, double age, int resistance) {

		if (check(height, weight, age, resistance, false))
			return 0;

		double fat = 0;
		double bmi = getBmi(weight, height);
		double imp = getImp(resistance);
		if (isMale) {
			fat = (0.00044D * imp + 1.479D) * bmi + 0.1D * age - 21.764D;
		} else {
			fat = (0.0003908D * imp + 1.506D) * bmi + 0.1D * age - 12.834D;
		}

		if (isAthlete) {
			if (isMale) {
				fat = fat - 4 - imp / 500D;
			} else {
				fat = fat - 4 - imp / 500D;
			}
		}
		// 脂肪率测量范围5%~60%
		if (fat < 5) {
			fat = 5.0;
		} else if (fat > 60) {
			fat = 60;
		}

		return fat;
	}

	/**
	 * 获取水份率
	 * 单位:百分比
	 * @return 结果需要做保留一位小数处理
	 */
	public static double getTbw(double weight, double height, boolean isMale,
			boolean isAthlete, double age, int resistance) {
		if (check(height, weight, age, resistance, false))
			return 0;

		double tbw;
		double bmi = getBmi(weight, height);
		double imp = getImp(resistance);
		if (isMale) {
			tbw = (-1.162 * bmi - 0.00813 * imp) + 0.07594 * age + 87.51;
		} else {
			tbw = (-1.148 * bmi - 0.00573 * imp) + 0.06448 * age + 77.721;
		}
		if (isAthlete) {
			if (isMale) {
				tbw = tbw + 3 + 1.35D * (imp + 10) / 1500D;
			} else {
				tbw = tbw + 3 + 1.35D * (imp + 10) / 1500D;
			}
		}
		// 水分率范围43%~73%
		if (tbw < 43) {
			tbw = 43;
		} else if (tbw > 73) {
			tbw = 73;
		}
		return tbw;
	}

	/**
	 * 获取骨头
	 * 单位：kg
	 * @return 结果需要做保留一位小数处理
	 */
	public static double getBone(double weight, double height, boolean isMale,
			boolean isAthlete, double age, int resistance) {
		if (check(height, weight, age, resistance, false))
			return 0;

		double bmi = getBmi(weight, height);
		double imp = getImp(resistance);
		double bone = 0;
		if (isAthlete) {
			if (isMale) {
				bone = (-0.0856 * bmi - 0.000525 * imp) - 0.0403 * age + 8.091;
			} else {
				bone = (-0.0965 * bmi - 0.000402 * imp) - 0.0389 * age + 8.309;
			}
		} else {
			if (isMale) {
				bone = (-0.0855 * bmi - 0.000592 * imp) - 0.0389 * age + 7.829;
			} else {
				bone = (-0.0973 * bmi - 0.000484 * imp) - 0.036 * age + 7.98;
			}
		}
		// 骨头值范围0.5~10%
		if (bone < 0.5) {
			bone = 0.5;
		} else if (bone > 10) {
			bone = 10;
		}

		return bone*weight/100.0f;
	}

	/**
	 * 获取肌肉
	 * 单位：kg
	 * @return //如果需要转化为%显示，需要转化为MUS / weight 结果需要做保留一位小数处理
	 */
	public static double getMus(double weight, double height, boolean isMale,
			boolean isAthlete, double age, int resistance) {
		if (check(height, weight, age, resistance, false))
			return 0;

		double mus;
		double bmi = getBmi(weight, height);
		double imp = getImp(resistance);
		if (isAthlete) {
			if (isMale) {
				mus = (-0.819 * bmi - 0.00486 * imp) - 0.382 * age + 77.389;
			} else {
				mus = (-0.685 * bmi - 0.00283 * imp) - 0.274 * age + 59.225;
			}
		} else {
			if (isMale) {
				mus = (-0.811 * bmi - 0.00565 * imp) - 0.367 * age + 74.627;
			} else {
				mus = (-0.694 * bmi - 0.00344 * imp) - 0.255 * age + 57;
			}
		}
		// 肌肉值范围25~75%
		if (mus < 25) {
			mus = 25;
		} else if (mus > 75) {
			mus = 75;
		}
		return mus*weight/100.0f;
	}

	/**
	 * 基础代谢量
	 */
	public static double getBasalMetabolism(double weight, double height,
			boolean isMale, double age, double resistance) {

		if (weight == 0 || height == 0 || age == 0 || resistance == 0)
			return 0;
		double calorie;
		if (isMale) {
			calorie = 66.5 + 13.75 * weight + 5.0 * height * 100 - 6.76 * age;
		} else {
			calorie = 655.1 + 9.56 * weight + 1.85 * height * 100 - 4.68 * age;
		}
		return calorie;
	}

	/**
	 * 
	 * 获取内脏脂肪指数, 暂时无FDA的公式，用国内公式
	 * 
	 * @param isMale
	 *            性别，是否男性
	 * @param bmi
	 *            体质指数
	 * @param imp
	 *            阻抗（测量阻抗，用于计算时需要减少10欧姆）
	 * @param age
	 *            年龄
	 * @return 结果需要做保留一位小数处理
	 */
	public static double getVisceralFat(boolean isMale, double bmi, double imp,
			double age) {
		if (bmi == 0 || imp == 0 || age == 0) {
			return 0;
		}
		double visceralFat = 0.0;
		// 参与计算时需要减少10欧姆
		imp -= 10;
		if (isMale) {
			visceralFat = 0.758 * bmi - 105.877 * bmi / imp + 0.15 * age
					- 9.486;
		} else {
			visceralFat = 0.533 * bmi - 50.833 * bmi / imp + 0.05 * age - 6.819;
		}
		return visceralFat;
	}

	/**
	 * 
	 * 获取蛋白质, 暂时无FDA的公式，用国内公式
	 * 
	 * @param isMale
	 *            性别，是否男性
	 * @param weight
	 *            体重（kg）
	 * @param fat
	 *            脂肪率
	 * @param tbw
	 *            水份率
	 * @param bone
	 *            骨量
	 * @return
	 */
	public static double getProtein(boolean isMale, double weight, double fat,
			double tbw, double bone) {
		if (weight == 0 || fat == 0 || tbw == 0 || bone == 0) {
			return 0;
		}
		double protein = 0;
		if (isMale) {
			protein = (weight - weight * fat / 100 - weight * tbw / 100 - bone)
					/ weight * 100;
		} else {
			protein = (weight - weight * fat / 100 - weight * tbw / 100 - bone)
					/ weight * 100;
		}
		return protein;
	}

	/**
	 * 转换阻抗
	 * 
	 * @param resistance
	 * @return
	 */
	public static final double getImp(int resistance) {
		if (resistance >= 410)
			return (0.3D * (resistance - 400));
		return 3.0D;
	}

	// 检查参数有效性
	private static boolean check(double height, double weight) {
		boolean result = true;
		result &= (100 >= (height * 100) && (height * 100) <= 220);
		return result;
	}

	private static boolean check(double height, double weight, double age,
			int resistance, boolean isSportsman) {
		boolean result = true;
		result &= check(height, weight);
		if (isSportsman) {
			result &= (age >= 10 && age <= 85);
		} else {
			result &= (age >= 15 && age <= 85);
		}
		result &= (resistance >= 200 && resistance <= 1200);
		return result;
	}

	/**
	 * 保留小数点
	 * 
	 * @param count
	 *            小数点后的位数
	 * @return
	 */
	private static double keepDecimalPoint(double value, int count) {
		BigDecimal b = new BigDecimal(value);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
