/*
 * 文件名:  WeightStatusUtil.java
 * 版权:   广州动心信息科技有限公司
 * 创建人:  liguoliang
 * 创建时间:2017-04-17
 */

package com.lifesense.formula;



import java.math.BigDecimal;
import java.util.Random;

import static com.lifesense.formula.LSBodyType.BODY_TYPE_NORMAL;


/**
 * 体重状态类
 *
 * @author liguoliang
 * @date 2017/4/17
 */
 class WeightStatusUtil {

    /**
     * 超低
     */
    public static final int STATUS_VERY_LOW = 1;

    /**
     * 偏低
     */
    public static final int STATUS_LOW = 2;
    /**
     * 标准
     */
    public static final int STATUS_NORMAL = 3;

    /**
     * 理想
     */
    public static final int STATUS_IDEAL = 4;

    /**
     * 偏高
     */
    public static final int STATUS_HIGH = 5;

    /**
     * 超高
     */
    public static final int STATUS_VERY_HIGH = 6;

    /**
     * 体重：偏低
     */
    public static final int WEIGHT_LOW = 1;
    /**
     * 体重：标准
     */
    public static final int WEIGHT_NORMAL = 2;
    /**
     * 体重：偏高
     */
    public static final int WEIGHT_HIGH = 3;

    /**
     * 四舍五入，精确至小数点后一位，如果小数为0，则返回整形double值
     *
     * @param
     * @return
     */
    private static double doubleFormat(double src) {
        double ret = src;
        long templ = Math.round(src * 10);
        double retd = templ / 10.0;
        int intd = (int) retd;
        if (retd == intd) {
            ret = intd;
        } else {
            BigDecimal b = new BigDecimal(src);
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            ret = f1;
        }
        return ret;
    }

    protected static int getWeightStatus(double height, double weight) {
        int status = WEIGHT_NORMAL;
        double condition1 = 18.5 * height * height;
        double condition2 = 24 * height * height + 0.1;
        if (weight < condition1) {
            status = WEIGHT_LOW;
        } else if (weight >= condition1 && weight < condition2) {
            status = WEIGHT_NORMAL;
        } else {
            status = WEIGHT_HIGH;
        }
        return status;
    }


    /**
     * BMI：偏瘦
     */
    public static final int BMI_THIN = 1;
    /**
     * BMI：理想
     */
    public static final int BMI_IDEAL = 2;
    /**
     * BMI：偏胖
     */
    public static final int BMI_LITTLE_FAT = 3;
    /**
     * BMI：肥胖
     */
    public static final int BMI_FAT = 4;

    protected static int getBmiStatus(double bmi) {
        int status = BMI_IDEAL;
        bmi = doubleFormat(bmi);
        if (bmi < 18.5) {
            status = BMI_THIN;
        } else if (bmi >= 18.5 && bmi < 24) {
            status = BMI_IDEAL;
        } else if (bmi >= 24 && bmi <= 28) {
            status = BMI_LITTLE_FAT;
        } else {
            status = BMI_FAT;
        }
        return status;
    }



    /**
     * 脂肪率：偏低
     */
    public static final int PBF_THIN = 1;
    /**
     * 脂肪率：理想
     */
    public static final int PBF_IDEAL = 2;
    /**
     * 脂肪率：偏高
     */
    public static final int PBF_LITTLE_FAT = 3;
    /**
     * 脂肪率：超高
     */
    public static final int PBF_FAT = 4;

    protected static int getPbfStatus(boolean isMale, int age, double pbf) {
        int status = PBF_IDEAL;
        pbf = doubleFormat(pbf);
        if (isMale) {
            if (age <= 39) {
                if (pbf < 13) {
                    status = PBF_THIN;
                } else if (pbf >= 13 && pbf < 23) {
                    status = PBF_IDEAL;
                } else if (pbf >= 23 && pbf < 28) {
                    status = PBF_LITTLE_FAT;
                } else {
                    status = PBF_FAT;
                }
            } else {
                if (pbf < 13) {
                    status = PBF_THIN;
                } else if (pbf >= 13 && pbf < 24) {
                    status = PBF_IDEAL;
                } else if (pbf >= 24 && pbf < 29) {
                    status = PBF_LITTLE_FAT;
                } else {
                    status = PBF_FAT;
                }
            }
        } else {
            if (age <= 39) {
                if (pbf < 22) {
                    status = PBF_THIN;
                } else if (pbf >= 22 && pbf < 34) {
                    status = PBF_IDEAL;
                } else if (pbf >= 34 && pbf < 39) {
                    status = PBF_LITTLE_FAT;
                } else {
                    status = PBF_FAT;
                }
            } else {
                if (pbf < 23) {
                    status = PBF_THIN;
                } else if (pbf >= 23 && pbf < 35) {
                    status = PBF_IDEAL;
                } else if (pbf >= 35 && pbf < 40) {
                    status = PBF_LITTLE_FAT;
                } else {
                    status = PBF_FAT;
                }
            }
        }
        return status;
    }


    public static final double WEIGHT_ERROR_VALUE = 0;
    protected static double getMuscleControl(double height, double weight, double pbf, double bmi, boolean isMale){
        if(isMale){
            if(bmi > 22 && pbf < 12){
                return WEIGHT_ERROR_VALUE;
            }else{
                return getMuscleControlOfMen( height) - getMuscle(true, weight, pbf);
            }
        }else{
            if(bmi > 20 && pbf < 22){
                return WEIGHT_ERROR_VALUE;
            }else{
                return getMuscleControlOfWomen(height)- getMuscle(false, weight, pbf);
            }
        }
    }
    /**
     * 获取实际肌肉量
     * */
    private static double getMuscle(boolean isMale, double weight, double pbf){
        if (isMale) {
            return  0.95 * weight - 0.0095 * pbf * weight - 0.13;
        } else {
            return  1.13 + 0.914 * weight - 0.00914 * pbf * weight;
        }
    }
    /**
     * 男性标准肌肉量
     * @param height
     * @return
     */
    protected static double getMuscleControlOfMen(double height){
        if (height < 1.60) {
            return 38.5;
        } else if (height <= 1.70) {
            return  44.0;
        } else {
            return  49.4;
        }
    }

    /**
     * 女性标准肌肉量
     * @param height
     * @return
     */
    protected static double getMuscleControlOfWomen(double height){
        if (height < 1.50) {
            return 29.1;
        } else if (height <= 1.60) {
            return  32.9;
        } else {
            return  36.5;
        }
    }


    /**
     *	获取相对于理想脂肪率区间的偏差值
     *   >0   需增加
     *   <0   需减少
     *   =0   需保持
     */
    protected static double getFatControl(int age, double weight, double pbf, double bmi, boolean isMale){
        if(isMale){
            if(bmi > 22 && pbf < 12){
                return WEIGHT_ERROR_VALUE;
            }else{
                return getFatControlOfMen(age, weight, pbf);
            }
        }else{
            if(bmi > 20 && pbf < 22){
                return WEIGHT_ERROR_VALUE;
            }else{
                return getFatControlOfWomen(age, weight, pbf);
            }
        }
    }

    public static double getFatControlOfWomen(int age,double weight, double pbf){
        double bfm = weight * (pbf/100);
        double idealFatMin ;
        double idealFatMax;
        if (age <= 39) {
            idealFatMin = 0.22 * weight;
            idealFatMax = 0.34 * weight;
        } else {
            idealFatMin = 0.23 * weight;
            idealFatMax = 0.35 * weight;
        }

        if(bfm<idealFatMin){//小于最小理想值
            return idealFatMin-bfm;
        }else if(bfm>idealFatMax){//大于最大理想值
            return idealFatMax - bfm;
        }else{//在理想区间内
            return 0;
        }
    }

    protected static double getFatControlOfMen(int age,double weight, double pbf){
        double bfm = weight * (pbf/100);
        double idealFatMin ;
        double idealFatMax;
        if (age <= 39) {
            idealFatMin = 0.13 * weight;
            idealFatMax = 0.23 * weight;
        } else {
            idealFatMin = 0.13 * weight;
            idealFatMax = 0.24 * weight;
        }
        if(bfm<idealFatMin){//小于最小理想值
            return idealFatMin-bfm;
        }else if(bfm>idealFatMax){//大于最大理想值
            return idealFatMax - bfm;
        }else{//在理想区间内
            return 0;
        }
    }


    /**
     * 骨量：偏低
     */
    public static final int BONE_LOW = 1;
    /**
     * 骨量：理想
     */
    public static final int BONE_IDEAL = 2;

    protected static int getBoneStatus(boolean isMale, double weight, double bone) {
        int status = BONE_IDEAL;
        bone = doubleFormat(bone);
        if (isMale) {
            if (weight < 60) {
                if (bone < 2.5) {
                    status = BONE_LOW;
                } else {
                    status = BONE_IDEAL;
                }
            } else if (weight >= 60 && weight <= 75) {
                if (bone < 2.9) {
                    status = BONE_LOW;
                } else {
                    status = BONE_IDEAL;
                }
            } else {
                if (bone < 3.2) {
                    status = BONE_LOW;
                } else {
                    status = BONE_IDEAL;
                }
            }
        } else {
            if (weight < 45) {
                if (bone < 1.8) {
                    status = BONE_LOW;
                } else {
                    status = BONE_IDEAL;
                }
            } else if (weight >= 45 && weight <= 60) {
                if (bone < 2.2) {
                    status = BONE_LOW;
                } else {
                    status = BONE_IDEAL;
                }
            } else {
                if (bone < 2.5) {
                    status = BONE_LOW;
                } else {
                    status = BONE_IDEAL;
                }
            }
        }
        return status;
    }



    /**
     * 水分：偏低
     */
    public static final int WATER_LOW = 1;
    /**
     * 水分：标准
     */
    public static final int WATER_NORMAL = 2;
    /**
     * 水分：理想
     */
    public static final int WATER_IDEAL = 3;

    protected static int getWaterStatus(boolean isMale, double water) {
        int status = WATER_IDEAL;
        water = doubleFormat(water);
        if (isMale) {
            if (water < 55) {
                status = WATER_LOW;
            } else if (water >= 55 && water < 65) {
                status = WATER_NORMAL;
            } else {
                status = WATER_IDEAL;
            }
        } else {
            if (water < 45) {
                status = WATER_LOW;
            } else if (water >= 45 && water < 60) {
                status = WATER_NORMAL;
            } else {
                status = WATER_IDEAL;
            }
        }
        return status;
    }



    /**
     * 肌肉量：偏低
     */
    public static final int MUSCLE_LOW = 1;
    /**
     * 肌肉量：标准
     */
    public static final int MUSCLE_NORMAL = 2;
    /**
     * 肌肉量：理想
     */
    public static final int MUSCLE_IDEAL = 3;

    protected static int getMuscleStatus(boolean isMale, double muscle, double height) {
        int status = MUSCLE_NORMAL;
        muscle = doubleFormat(muscle);
        if (isMale) {
            if (height < 1.60) {
                if (muscle < 38.5) {
                    status = MUSCLE_LOW;
                } else if (muscle >= 38.5 && muscle < 46.6) {
                    status = MUSCLE_NORMAL;
                } else if (muscle >= 46.6) {
                    status = MUSCLE_IDEAL;
                }
            } else if (height >= 1.60 && height <= 1.70) {
                if (muscle < 44) {
                    status = MUSCLE_LOW;
                } else if (muscle >= 44 && muscle < 52.5) {
                    status = MUSCLE_NORMAL;
                } else if (muscle >= 52.5) {
                    status = MUSCLE_IDEAL;
                }
            } else {
                if (muscle < 49.4) {
                    status = MUSCLE_LOW;
                } else if (muscle >= 49.4 && muscle < 59.5) {
                    status = MUSCLE_NORMAL;
                } else if (muscle >= 59.5) {
                    status = MUSCLE_IDEAL;
                }
            }
        } else {
            if (height < 1.50) {
                if (muscle < 29.1) {
                    status = MUSCLE_LOW;
                } else if (muscle >= 29.1 && muscle < 34.8) {
                    status = MUSCLE_NORMAL;
                } else if (muscle >= 34.8) {
                    status = MUSCLE_IDEAL;
                }
            } else if (height >= 1.50 && height <= 1.60) {
                if (muscle < 32.9) {
                    status = MUSCLE_LOW;
                } else if (muscle >= 32.9 && muscle < 37.6) {
                    status = MUSCLE_NORMAL;
                } else if (muscle >= 37.6) {
                    status = MUSCLE_IDEAL;
                }
            } else {
                if (muscle < 36.5) {
                    status = MUSCLE_LOW;
                } else if (muscle >= 36.5 && muscle < 42.6) {
                    status = MUSCLE_NORMAL;
                } else if (muscle >= 42.6) {
                    status = MUSCLE_IDEAL;
                }
            }
        }
        return status;
    }



    /**
     * 内脏脂肪指数:理想
     */
    public static final int VISCERAL_FAT_IDEAL = 1;
    /**
     * 内脏脂肪指数:偏高
     */
    public static final int VISCERAL_FAT_HIGH = 2;
    /**
     * 内脏脂肪指数:超高
     */
    public static final int VISCERAL_FAT_VERY_HIGH = 3;

    protected static final int getVisceralFatStatus(double visceralFat) {
        int status = VISCERAL_FAT_IDEAL;
        visceralFat = doubleFormat(visceralFat);
        if (visceralFat < 10) {
            status = VISCERAL_FAT_IDEAL;
        } else if (visceralFat >= 10 && visceralFat < 15) {
            status = VISCERAL_FAT_HIGH;
        } else {
            status = VISCERAL_FAT_VERY_HIGH;
        }
        return status;
    }


    /**
     * 基础代谢量:偏低
     */
    public static final int BASAL_METABOLISM_LOW = 1;
    /**
     * 基础代谢量:理想
     */
    public static final int BASAL_METABOLISM_IDEAL = 2;

    protected static final int getBasalMetabolismStatus(boolean isMale, int age, double basalMetabolism) {
        int status = BASAL_METABOLISM_IDEAL;
        basalMetabolism = doubleFormat(basalMetabolism);
        if (isMale) {
            if (age < 30) {
                if (basalMetabolism < 1550) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            } else if (age >= 30 && age < 50) {
                if (basalMetabolism < 1500) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            } else if (age >= 50 && age < 70) {
                if (basalMetabolism < 1350) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            } else {
                if (basalMetabolism < 1220) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            }
        } else {
            if (age < 30) {
                if (basalMetabolism < 1210) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            } else if (age >= 30 && age < 50) {
                if (basalMetabolism < 1170) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            } else if (age >= 50 && age < 70) {
                if (basalMetabolism < 1110) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            } else {
                if (basalMetabolism < 1010) {
                    status = BASAL_METABOLISM_LOW;
                } else {
                    status = BASAL_METABOLISM_IDEAL;
                }
            }
        }
        return status;
    }



    /**
     * 蛋白质：偏低
     */
    public static final int PROTEIN_LOW = 1;
    /**
     * 蛋白质：标准
     */
    public static final int PROTEIN_NORMAL = 2;
    /**
     * 蛋白质：理想
     */
    public static final int PROTEIN_IDEAL = 3;

    protected static int getProteinStatus(double protein) {
        int status = PROTEIN_NORMAL;
        protein = doubleFormat(protein);
        if (protein < 16) {
            status = PROTEIN_LOW;
        } else if (protein >= 16 && protein < 20) {
            status = PROTEIN_NORMAL;
        } else {
            status = PROTEIN_IDEAL;
        }
        return status;
    }

    /**
     *
     * 脂肪量
     *
     * @param isMale
     * @param age
     * @param weight
     * @param bfm
     * @return
     */
    protected static int getBfmStatus(boolean isMale, int age, double weight, double bfm) {
        int status = STATUS_NORMAL;
        if (isMale) {
            if (age <= 39) {
                if (bfm < 0.13 * weight) {
                    status = STATUS_LOW;
                } else if (bfm < 0.23 * weight) {
                    status = STATUS_IDEAL;
                } else if (bfm < 0.28 * weight) {
                    status = STATUS_HIGH;
                } else {
                    status = STATUS_VERY_HIGH;
                }
            } else {
                if (bfm < 0.13 * weight) {
                    status = STATUS_LOW;
                } else if (bfm < 0.24 * weight) {
                    status = STATUS_IDEAL;
                } else if (bfm < 0.29 * weight) {
                    status = STATUS_HIGH;
                } else {
                    status = STATUS_VERY_HIGH;
                }
            }
        } else {
            if (age <= 39) {
                if (bfm < 0.22 * weight) {
                    status = STATUS_LOW;
                } else if (bfm < 0.34 * weight) {
                    status = STATUS_IDEAL;
                } else if (bfm < 0.39 * weight) {
                    status = STATUS_HIGH;
                } else {
                    status = STATUS_VERY_HIGH;
                }
            } else {
                if (bfm < 0.23 * weight) {
                    status = STATUS_LOW;
                } else if (bfm < 0.35 * weight) {
                    status = STATUS_IDEAL;
                } else if (bfm < 0.40 * weight) {
                    status = STATUS_HIGH;
                } else {
                    status = STATUS_VERY_HIGH;
                }
            }
        }

        return status;
    }

    /**
     * SMM = 0.8422*TBW*weight*0.01 - 2.9903
     * @param water
     * @return
     */
    protected static double getSkeletonMuscle(double weight, double water) {
        double skeletonMuscle = 0;
        if(water>0) {
            skeletonMuscle = 0.8442 * water  * weight *  0.01  - 2.9903;
        }
        return new BigDecimal(skeletonMuscle).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *去脂体重
     * @param weight
     * @param bfm
     * @return
     */
    protected static double getLeanBodyWeight(double weight, double bfm){
        return weight - bfm;
    }
    /**
     * 肌肉率
     * @param isMale
     * @param rateOfMuscle
     * @return
     */
    protected static int getRateOfMuscleStatus(boolean isMale, double rateOfMuscle) {
        int status = STATUS_NORMAL;
        if (isMale) {
            if (rateOfMuscle < 72) {
                status = STATUS_LOW;
            } else if (rateOfMuscle <= 89) {
                status = STATUS_NORMAL;
            } else {
                status = STATUS_IDEAL;
            }
        } else {
            if (rateOfMuscle < 67) {
                status = STATUS_LOW;
            } else if (rateOfMuscle <= 82) {
                status = STATUS_NORMAL;
            } else {
                status = STATUS_IDEAL;
            }
        }

        return status;
    }



    /**
     * 骨骼肌
     * @param isMale
     * @param height
     * @param skeletonMuscle
     * @return
     */
    protected static int getSkeletonMuscleStatus(boolean isMale, double height, double skeletonMuscle) {
        int status = STATUS_NORMAL;
        // 身高单位是m
        height = height / 100;
        if (isMale) {
            double ideal = 22 * height * height * 0.47;
            if (skeletonMuscle < 0.90 * ideal) {
                status = STATUS_LOW;
            } else if (skeletonMuscle <= 1.1 * ideal) {
                status = STATUS_NORMAL;
            } else {
                status = STATUS_IDEAL;
            }
        } else {
            double ideal =   21 * height * height * 0.42;
            if (skeletonMuscle < 0.90 * ideal) {
                status = STATUS_LOW;
            } else if (skeletonMuscle <= 1.1 * ideal) {
                status = STATUS_NORMAL;
            } else {
                status = STATUS_IDEAL;
            }
        }

        return status;
    }




    /**
     * 去脂体重
     *
     * @param isMale
     * @param age
     * @param weight
     * @param ffm
     * @return
     */
    protected static int getFfmStatus(boolean isMale, int age,  double weight, double ffm) {
        int status = STATUS_NORMAL;
        if (isMale) {
            if (age <= 39) {
                double[] ffmArray = new double[]{0.72f * weight, 0.77f * weight, 0.87f * weight  };
                if (ffm <= 0.72 * weight) {
                    status = STATUS_VERY_LOW;
                } else if (ffm <= 0.77 * weight) {
                    status = STATUS_LOW;
                } else if (ffm < 0.87 * weight) {
                    status = STATUS_IDEAL;
                } else {
                    status = STATUS_HIGH;
                }
            } else {
                double[] ffmArray = new double[]{0.71f * weight, 0.77f * weight, 0.87f * weight  };
                if (ffm <= 0.71 * weight) {
                    status = STATUS_VERY_LOW;
                } else if (ffm <= 0.76 * weight) {
                    status = STATUS_LOW;
                } else if (ffm < 0.87 * weight) {
                    status = STATUS_IDEAL;
                } else {
                    status = STATUS_HIGH;
                }
            }
        } else {
            if (age <= 39) {
                double[] ffmArray = new double[] { 0.61f * weight, 0.66f * weight, 0.78f * weight };
                if (ffm <= 0.61 * weight) {
                    status = STATUS_VERY_LOW;
                } else if (ffm <= 0.66 * weight) {
                    status = STATUS_LOW;
                } else if (ffm < 0.78 * weight) {
                    status = STATUS_IDEAL;
                } else {
                    status = STATUS_HIGH;
                }
            } else {
                double[] ffmArray = new double[] { 0.60f * weight, 0.65f * weight, 0.77f * weight };
                if (ffm <= 0.60 * weight) {
                    status = STATUS_VERY_LOW;
                } else if (ffm <= 0.65 * weight) {
                    status = STATUS_LOW;
                } else if (ffm < 0.77 * weight) {
                    status = STATUS_IDEAL;
                } else {
                    status = STATUS_HIGH;
                }
            }
        }

        return status;
    }


    /**
     * 身体年龄：年轻
     */
    public static final int BODY_AGE_YOUNG = 1;
    /**
     * 身体年龄：偏大
     */
    public static final int BODY_AGE_OLD = 2;

    /**
     * 计算身体年龄
     *
     * @param isMale
     * @param pbf
     * @return
     */
    protected static int getBodyAge(boolean isMale, double pbf, int age) {
        int bodyAge = age;
        if (isMale) {
            if (pbf < 14) {
                bodyAge = age - 3;
            } else if (pbf >= 14 && pbf < 19) {
                bodyAge = age - 2;
            } else if (pbf >= 19 && pbf < 24) {
                bodyAge = age - 1;
            } else if (pbf >= 24 && pbf < 27) {
                bodyAge = age + 1;
            } else if (pbf >= 27 && pbf < 30) {
                bodyAge = age + 2;
            } else if (pbf >= 30 && pbf < 33) {
                bodyAge = age + 3;
            } else if (pbf >= 33 && pbf < 36) {
                bodyAge = age + 4;
            } else {
                bodyAge = age + 5;
            }
        } else {
            if (pbf < 24) {
                bodyAge = age - 3;
            } else if (pbf >= 24 && pbf < 28) {
                bodyAge = age - 2;
            } else if (pbf >= 28 && pbf < 32) {
                bodyAge = age - 1;
            } else if (pbf >= 32 && pbf < 35) {
                bodyAge = age + 1;
            } else if (pbf >= 35 && pbf < 38) {
                bodyAge = age + 2;
            } else if (pbf >= 38 && pbf < 42) {
                bodyAge = age + 3;
            } else if (pbf >= 42 && pbf < 46) {
                bodyAge = age + 4;
            } else {
                bodyAge = age + 5;
            }
        }
        return bodyAge;
    }

    /**
     * 获取身材评分
     *
     * @return
     */
    protected static int getBodyScore(boolean isMale, double pbf, double bmi) {
        double dScore = 0;
        if (isMale) {
            if (pbf <= 18) {
                if (bmi >= 21) {
                    dScore = 90 - (pbf - 14) + (bmi - 21) * 2;
                } else {
                    dScore = 90 - (pbf - 14) * 0.5 + (bmi - 21) * 4;
                }
            } else {
                if (bmi > 23) {
                    dScore = 90 - (pbf - 18) * 2 - (bmi - 23);
                } else {
                    dScore = 90 - (pbf - 18) * 2 - (23 - bmi);
                }
            }
        } else {
            if (pbf <= 28) {
                if (bmi >= 19) {
                    dScore = 90 - (pbf - 24) + (bmi - 19) * 2;
                } else {
                    dScore = 90 - (pbf - 24) * 0.5 + (bmi - 19) * 4;
                }
            } else {
                if (bmi >= 21) {
                    dScore = 90 - (pbf - 28) * 2 - (bmi - 21);
                } else {
                    dScore = 90 - (pbf - 28) * 2 - (21 - bmi);
                }
            }
        }
        int score = (int) Math.round(dScore);
        if (score > 100) {
            score = 100;
        } else if (score <= 19) {
            score = 19;
        }
        return score;
    }


    /**
     * 获取身材类型
     *
     * @param pbfStatus
     * @param muscleStatus
     * @return
     */
    protected static int getBodyType(int pbfStatus, int muscleStatus) {
        int bodyType = BODY_TYPE_NORMAL;
        if ((pbfStatus == PBF_LITTLE_FAT || pbfStatus == PBF_FAT) && muscleStatus == MUSCLE_LOW) {
            bodyType = LSBodyType.BODY_TYPE_RECESSIVE_FAT;
        } else if ((pbfStatus == PBF_LITTLE_FAT || pbfStatus == PBF_FAT) && muscleStatus == MUSCLE_NORMAL) {
            bodyType = LSBodyType.BODY_TYPE_FAT;
        } else if ((pbfStatus == PBF_LITTLE_FAT || pbfStatus == PBF_FAT) && muscleStatus == MUSCLE_IDEAL) {
            bodyType = LSBodyType.BODY_TYPE_STRONG_FAT;
        } else if (pbfStatus == PBF_IDEAL && muscleStatus == MUSCLE_LOW) {
            bodyType = LSBodyType.BODY_TYPE_LACK_OF_EXERCISES;
        } else if (pbfStatus == PBF_IDEAL && muscleStatus == MUSCLE_NORMAL) {
            bodyType = BODY_TYPE_NORMAL;
        } else if (pbfStatus == PBF_IDEAL && muscleStatus == MUSCLE_IDEAL) {
            bodyType = LSBodyType.BODY_TYPE_ROBUST;
        } else if (pbfStatus == PBF_THIN && muscleStatus == MUSCLE_LOW) {
            bodyType = LSBodyType.BODY_TYPE_THIN;
        } else if (pbfStatus == PBF_THIN && muscleStatus == MUSCLE_NORMAL) {
            bodyType = LSBodyType.BODY_TYPE_MODEL;
        } else if (pbfStatus == PBF_THIN && muscleStatus == MUSCLE_IDEAL) {
            bodyType = LSBodyType.BODY_TYPE_BODYBUILDING;
        }
        return bodyType;
    }


    protected static float getBeatPercent(int score) {
        float percent = 29;
        if (score < 0) {
            return percent;
        } else if (score >= 0 && score < 61) {
            percent = 29;
            int addPercent = score / 10;
            percent += addPercent;
        } else if (score >= 61 && score < 71) {
            percent = 35;
            int addPercent = (score - 61) * 3;
            percent += addPercent;
        } else if (score >= 71 && score < 81) {
            percent = 65;
            int addPercent = (score - 71) * 2;
            percent += addPercent;
        } else if (score >= 81 && score < 91) {
            percent = 85;
            int addPercent = (score - 81) * 1;
            percent += addPercent;
        } else {
            percent = 95;
            int addPercent = (int) (((float) score - 91) * 0.5);
            percent += addPercent;
        }

        int intPercnet = (int) percent;
        if (percent == intPercnet) {
            // 若小数点最后一位为0，则从1-9随机分配一个数
            int random = new Random().nextInt(9) + 1;
            float ranF = (float) random / 10;
            percent += ranF;
        }
        if (percent > 99.9) {
            // 最大为99.9
            percent = 99.9f;
        }

        return percent;
    }




}
