package com.lifesense.formula;

/**
 * 脂肪秤计算公式
 */
public class LSWeightCalcuate {

    private static final String VERSION="1.0.1 build2 20190812";
    private static LSWeightCalcuate mFormula;


    private LSWeightCalcuate(){}

    public static synchronized LSWeightCalcuate getInstance()
    {
        if(mFormula==null){
            return mFormula=new LSWeightCalcuate();
        }
        else return mFormula;
    }

    /**
     * 获取计算公式SDK版本
     * @return
     */
    public String getVersion(){
        return VERSION;
    }

    /**
     * 根据公式计算身体成分数据
     * @param formula
     * @param userInfo
     * @param resistance
     * @return
     */
    public LSBodyCompositionData calculateBodyCompositionData(LSFormulaType formula, LSFormulaUser userInfo, double resistance)
    {
        if(userInfo == null || resistance <=0){
            return  null;
        }
        if(LSFormulaType.FDA == formula){
            //FDA 计算公式
            return formulaFDA(userInfo,resistance);
        }
        else{
            //厂商自定义计算公式
            double weight= userInfo.getWeight();
            double height= userInfo.getHeight();
            int age= userInfo.getAge();
            boolean isMale= userInfo.isMale();
            return formulaVendor(weight,height,age,isMale,resistance);
        }
    }


    /**
     * FDA 计算公式
     * @param userInfo
     * @param resistance
     * @return
     */
    private LSBodyCompositionData formulaFDA(LSFormulaUser userInfo, double resistance)
    {
        if(resistance==0 || userInfo==null){
            return null;
        }
        LSBodyCompositionData bodyCompositionData=new LSBodyCompositionData();
        double weight_kg=userInfo.getWeight();
        double height_m=userInfo.getHeight();
        int age=userInfo.getAge();
        boolean isAthlete=userInfo.isAthlete();

        //new FDA calculate formula
        boolean isMale=userInfo.isMale();
        double fat= LSFormulaFDA.getFat(weight_kg, height_m, isMale, isAthlete, age, (int) resistance);
        double basm= LSFormulaFDA.getBasalMetabolism(weight_kg, height_m, isMale, age, resistance);
        double imp= LSFormulaFDA.getImp((int)resistance);
        double bmi= LSFormulaFDA.getBmi(weight_kg, height_m);
        double bone= LSFormulaFDA.getBone(weight_kg, height_m, isMale, isAthlete, age, (int)resistance);
        double mus= LSFormulaFDA.getMus(weight_kg, height_m, isMale, isAthlete, age, (int)resistance);
        double tbw= LSFormulaFDA.getTbw(weight_kg, height_m, isMale, isAthlete, age, (int)resistance);
        double protein= LSFormulaFDA.getProtein(isMale, weight_kg, fat, tbw, bone);
        double visceralFat= LSFormulaFDA.getVisceralFat(isMale, bmi, imp, age);

        //用户体重
        bodyCompositionData.setWeight(weight_kg);
        //基础代谢
        bodyCompositionData.setBasalMetabolism(basm);
        //fat 脂肪率
        bodyCompositionData.setPbf(fat);
        //阻抗
        bodyCompositionData.setImp(imp);
        //Bone
        bodyCompositionData.setBone(bone);
        //mus
        bodyCompositionData.setMuscle(mus);
        //body water
        bodyCompositionData.setWater(tbw);
        //BMI指数
        bodyCompositionData.setBmi(bmi);
        return bodyCompositionData;
    }

    /**
     * 通过体重、阻抗、个人信息，计算身体18项体脂数据
     *
     * @param weight        体重（kg）必须大于0
     * @param height        身高（m）必须大于0
     * @param age           年龄 必须大于0
     * @param isMale        是否男性(true 男性 false 女性)
     * @param resistance50k 体脂秤采集到的阻抗 必须大于0
     * @return 算法得出的18项体脂数据，如果有参数小于等于0，则返回null
     */
    private LSWeightCalcuateData formulaVendor(double weight, double height, int age,
                                               boolean isMale, double resistance50k) {
        if (weight <= 0 || height <= 0 || resistance50k <= 0) {
            return null;
        }
        LSWeightCalcuateData weightStatus = new LSWeightCalcuateData();
        weightStatus.setResistance50k(resistance50k);
        //体重
        weightStatus.setWeight(weight);
        double pdf = LSFormulaVendor.getFat(isMale, weight, height, age, resistance50k);
        double bfm = weight * pdf / 100D;
        double muscle = LSFormulaVendor.getMus(isMale, weight, pdf);
        double ffm = WeightStatusUtil.getLeanBodyWeight(weight, bfm);
        double muscleRate = muscle / weight * 100D;
        double water = LSFormulaVendor.getTbw(isMale, height, weight, resistance50k);
        double skeletonMuscle = WeightStatusUtil.getSkeletonMuscle(weight, water);
        double bmi = LSFormulaVendor.getBmi(weight, height);
        double bone = LSFormulaVendor.getBone(isMale, muscle);
        int pbfStatus = WeightStatusUtil.getPbfStatus(isMale, age, pdf);
        int muscleStatus = WeightStatusUtil.getMuscleStatus(isMale, muscle, height);
        int bodyType = WeightStatusUtil.getBodyType(pbfStatus, muscleStatus);

        //去脂体重	kg
        weightStatus.setFfm(ffm);
        //BMI
        weightStatus.setBmi(bmi);
        //脂肪率	%
        weightStatus.setPbf(pdf);
        //脂肪量	kg
        weightStatus.setBfm(bfm);
        //肌肉率	%;
        weightStatus.setRateOfMuscle(muscleRate);
        //肌肉量	kg
        weightStatus.setMuscle(muscle);
        //骨骼肌 kg
        weightStatus.setSkeletonMuscle(skeletonMuscle);
        //基础代谢 千卡（整数）
        weightStatus.setBasalMetabolism(LSFormulaVendor.getBasalMetabolism(isMale, weight, pdf));
        //身体年龄 age +-5
        weightStatus.setBodyAge(WeightStatusUtil.getBodyAge(isMale, pdf, age));
        //内脏脂肪等级
        weightStatus.setVisceralFat(LSFormulaVendor.getVisceralFat(isMale, bmi, resistance50k,
                age));
        //水分率	%
        weightStatus.setWater(water);
        //骨量	kg
        weightStatus.setBone(bone);
        //蛋白质	%
        weightStatus.setProtein(LSFormulaVendor.getProtein(isMale, weight,
                pdf, water, bone));
        //体型
        weightStatus.setBodyType(bodyType);
        //身体得分
        weightStatus.setBodyScore(WeightStatusUtil.getBodyScore(isMale, pdf, bmi));
        //肌肉控制	  kg
        double muscleControl = WeightStatusUtil.getMuscleControl(height,weight,pdf, bmi, isMale);
        weightStatus.setMuscleControl(muscleControl);
        //脂肪控制     kg
        double fatControl = WeightStatusUtil.getFatControl(age, weight, pdf, bmi, isMale);
        weightStatus.setFatControl(fatControl);
        return weightStatus;
    }


}
