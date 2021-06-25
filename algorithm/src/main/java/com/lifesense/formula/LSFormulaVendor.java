package com.lifesense.formula;

/**
 * 体重算法
 *
 * @author wenhao
 */
 class LSFormulaVendor {

    private LSFormulaVendor() {
    }

    // BMI 体质指数
    public static double getBmi(double weight, double height) {
        if (weight == 0 || height == 0)
            return 0;
        return weight / (height * height);
    }

//    public static void main(String arg[]) {
//        //男    7.7kg 100cm 80cm    17岁  电阻
//        double fat = getFat(true, 7.7d, 1, 0.8, 45, 830.7, true);
//        double mus = getMus(true, 7.7d, fat);
//        System.out.println(mus+"");//6.819249999999999
//    }
    // PBF 脂肪率

    /**
     * 获取脂肪率
     *
     * @param isMale    性别，是否男性
     * @param weight    体重（kg）
     * @param height    身高（m）
     * @param age       年龄
     * @param imp       阻抗（测量阻抗，用于计算时需要减少10欧姆）
     * @return 结果需要做保留一位小数处理
     */
    public static double getFat(boolean isMale, double weight, double height, double age, double imp
                                ) {

        if (weight == 0 || height == 0 || age == 0 || imp == 0)
            return 0;

        // 最新脂肪率
        // FAT=60.3-486583*Height*Height/Weight/Imp+9.146*Weight/Height/Height/Imp-251.193*Height*Height/Weight/Age+1625303/Imp/Imp-0.0139*Imp+0.05975*Age
        // FAT=57.621-186.422*Height*Height/Weight-382280*Height*Height/Weight/Imp+128.005*Weight/Height/Imp-0.0728*Weight/Height+7816.359/Height/Imp-3.333*Weight/Height/Height/Age

        double fat = 0.0;

        if (imp == 0) {
            fat = 0;
        } else {
            // 参与计算时需要减少10欧姆
            imp -= 10;

            // if(deviceModelNum.indexOf(WechatConstants.WEIGHT_DEVICE_MODEL_NUM)>=0){//旧公式
            if (isMale) {
                fat = 60.3 - 486583 * height * height / weight / imp + 9.146 * weight / height / height / imp
                        - 251.193 * height * height / weight / age + 1625303 / imp / imp - 0.0139 * imp
                        + 0.05975 * age;
            } else {
                fat = 57.621 - 186.422 * height * height / weight - 382280 * height * height / weight / imp
                        + 128.005 * weight / height / imp - 0.0728 * weight / height + 7816.359 / height / imp
                        - 3.333 * weight / height / height / age;
            }
            // 脂肪率测量范围5%~80%
            if (fat < 5) {
                fat = 5.0;
            } else if (fat > 80) {
                fat = 80.0;
            }

        }

        return fat;
    }

    // WATER 水分率

    /**
     * 获取水份率
     *
     * @param isMale 性别，是否男性
     * @param height 身高（m）
     * @param weight 体重（kg）
     * @param imp    阻抗（测量阻抗，用于计算时需要减少10欧姆）
     * @return 结果需要做保留一位小数处理
     */
    public static double getTbw(boolean isMale, double height, double weight, double imp) {
        if (imp == 0 || height == 0 || weight == 0) {
            return 0;
        }
        double tbw = 0;
        // TBW=30.849+259672.5*Height*Height/Weight/Imp+0.372*Imp/Height/Weight-2.581*Height*Weight/Imp;
        // TBW=23.018+201468.7*Height*Height/Weight/Imp+421.543/Weight/Height+160.445*Height/Weight;
        // 参与计算时需要减少10欧姆
        imp -= 10;
        if (isMale) {
            tbw = 30.849 + 259672.5 * height * height / weight / imp + 0.372 * imp / height / weight
                    - 2.581 * height * weight / imp;
        } else {
            tbw = 23.018 + 201468.7 * height * height / weight / imp + 421.543 / weight / height
                    + 160.445 * height / weight;
        }
        // 水分率范围25%~90%
        if (tbw < 25) {
            tbw = 25;
        } else if (tbw > 90) {
            tbw = 90;
        }
        return tbw;
    }

    // BONE 骨骼量

    /**
     * 获取骨头
     *
     * @param isMale 性别，是否男性
     * @param mus    肌肉
     * @return 结果需要做保留一位小数处理
     */
    public static double getBone(boolean isMale, double mus) {
        if (mus == 0)
            return 0;

        // BONE=0.116+0.0525*MUS;
        // BONE=-1.22+0.0944*MUS
        double bone = 0;
        if (isMale) {
            bone = 0.116 + 0.0525 * mus;
        } else {
            bone = -1.22 + 0.0944 * mus;
        }
        // 骨头值范围0.5~10kg
        if (bone < 0.5) {
            bone = 0.5;
        } else if (bone > 10) {
            bone = 10;
        }

        return bone;
    }

    // MUSCLE 肌肉量

    /**
     * 获取肌肉
     *
     * @param isMale 性别，是否男性
     * @param weight 体重（kg）
     * @param fat    脂肪率
     * @return //如果需要转化为%显示，需要转化为MUS / weight 结果需要做保留一位小数处理
     */
    public static double getMus(boolean isMale, double weight, double fat) {
        if (weight == 0 || fat == 0)
            return 0;
        // MUS=0.95*Weight-0.0095*FAT*Weight-0.13;
        // MUS=1.13+0.914*Weight-0.00914*FAT*Weight;
        double mus = 0;
        if (isMale) {
            mus = 0.95 * weight - 0.0095 * fat * weight - 0.13;
        } else {
            mus = 1.13 + 0.914 * weight - 0.00914 * fat * weight;
        }
        return mus;
    }

    /**
     *
     * 获取内脏脂肪指数
     *
     * @param isMale 性别，是否男性
     * @param bmi  体质指数
     * @param imp 阻抗（测量阻抗，用于计算时需要减少10欧姆）
     * @param age 年龄
     * @return 结果需要做保留一位小数处理
     */
    public static double getVisceralFat(boolean isMale, double bmi, double imp, double age) {
        if (bmi == 0 || imp == 0 || age == 0) {
            return 0;
        }
        double visceralFat = 0.0;
        // 参与计算时需要减少10欧姆
        imp -= 10;
        if (isMale) {
            visceralFat = 0.758 * bmi - 105.877 * bmi / imp + 0.15 * age - 9.486;
        } else {
            visceralFat = 0.533 * bmi - 50.833 * bmi / imp + 0.05 * age - 6.819;
        }
        return visceralFat;
    }

    /**
     *
     * 获取基础代谢量
     *
     * @param isMale 性别，是否男性
     * @param weight 体重（kg）
     * @param fat 脂肪率
     * @return 结果需要做保留一位小数处理
     */
    public static double getBasalMetabolism(boolean isMale, double weight, double fat) {
        if (weight == 0 || fat == 0) {
            return 0;
        }
        double basalMetabolism = 0.0;
        if (isMale) {
            basalMetabolism = 370 + 21.6 * weight* (1 - fat / 100);
        } else {
            basalMetabolism = 370 + 21.6 * weight* (1 - fat / 100);
        }
        return basalMetabolism;
    }

    /**
     *
     * 获取蛋白质
     *
     * @param isMale 性别，是否男性
     * @param weight 体重（kg）
     * @param fat 脂肪率
     * @param tbw 水份率
     * @param bone 骨量
     * @return
     */
    public static double getProtein(boolean isMale, double weight, double fat, double tbw, double bone) {
        if (weight == 0 || fat == 0 || tbw == 0 || bone == 0) {
            return 0;
        }
        double protein = 0;
        if (isMale) {
            protein = (weight - weight * fat / 100 - weight * tbw / 100 - bone) / weight * 100;
        } else {
            protein = (weight - weight * fat / 100 - weight * tbw / 100 - bone) / weight * 100;
        }
        return  protein;
    }

    // TODO LEVEL 体重水平

    /**
     * 获取补偿系数（国内）
     *
     * @param imp 电阻
     * @param k   K值
     * @return
     */
    private static double getS(double imp, double k) {
        if (imp < 860) {
            return (imp / 500 * k - 2);
        }
        return 1.96;
    }

    /**
     * 获取K值（国内）
     *
     * @param bmi 体质指数
     * @param imp 电阻
     * @return
     */
    private static double getK(boolean isMale, double bmi, double imp) {
        double k = 0;
        if (isMale) { // 男
            if (bmi < 18) {
                k = getSimpleK(imp, 550, 600, 860);
            } else if (bmi < 25) {
                k = getSimpleK(imp, 430, 580, 860);
            } else {
                k = getSimpleK(imp, 400, 500, 860);
            }
        } else {
            if (bmi < 18) {
                k = getSimpleK(imp, 500, 700, 860);
            } else if (bmi < 25) {
                k = getSimpleK(imp, 480, 650, 860);
            } else {
                k = getSimpleK(imp, 450, 550, 860);
            }
        }
        return k;
    }

    /**
     * 简易计算K值（国内）
     *
     * @param range1 范围1（imp < range1）
     * @param range2 范围2（range1 <= imp < range2）
     * @param range3 范围3（range2 <= imp < range3）
     * @return
     */
    private static double getSimpleK(double imp, double range1, double range2, double range3) {
        if (imp < range1) {
            return 1.5;
        } else if (imp < range2) {
            return 2;
        } else if (imp < range3) {
            return 2.3;
        }
        return 0;
    }


}
