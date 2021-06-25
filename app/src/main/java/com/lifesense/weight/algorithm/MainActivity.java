package com.lifesense.weight.algorithm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lifesense.formula.LSBodyCompositionData;
import com.lifesense.formula.LSFormulaType;
import com.lifesense.formula.LSFormulaUser;
import com.lifesense.formula.LSWeightCalcuate;
import com.lifesense.formula.LSWeightCalcuateData;

public class MainActivity extends AppCompatActivity {
    private TextView hw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hw = findViewById(R.id.hw);
        double weight =68.6D;
        double height = 1.7d;
        int age = 24;
        boolean isMale = true;
        double resistance50k = 506.0;

        LSFormulaUser userInfo=new LSFormulaUser();
        userInfo.setWeight(weight);
        userInfo.setHeight(height);
        userInfo.setAge(age);
        userInfo.setMale(isMale);

        Object obj = LSWeightCalcuate.getInstance().calculateBodyCompositionData(LSFormulaType.Vendor,userInfo,resistance50k);
        if(obj!=null && obj instanceof LSWeightCalcuateData){
            LSWeightCalcuateData weightStatus=(LSWeightCalcuateData)obj;
            hw.setText(weightStatus.toString());
            Log.d("LS-BLE", "onCreate: Vendor LSWeightCalcuateData:" + weightStatus);
        }
        else if(obj!=null && obj instanceof LSBodyCompositionData){
            LSBodyCompositionData weightStatus=(LSBodyCompositionData)obj;
            hw.setText(weightStatus.toString());
            Log.e("LS-BLE", "onCreate: FDA LSWeightCalcuateData:" + weightStatus);
        }

        /**
         * Vendor LSWeightCalcuateData:LSWeightCalcuateData{resistance50k=506.0, weight=68.6, ffm=54.80150457258217, bmi=23.73702422145329, pbf=20.114424821308784, bfm=13.798495427417825, rateOfMuscle=75.7017920465788, muscle=51.93142934395305, skeletonMuscle=28.2, basalMetabolism=1553.7124987677748, bodyAge=23, visceralFat=7.039718969751089, water=53.87987304029215, bone=2.842400040557535, protein=21.862261846041132, bodyType=5, bodyScore=85, muscleControl=-7.931429343953049, fatControl=0.0}
         *  FDA LSWeightCalcuateData:LSBodyCompositionData{weight=0.0, bmi=18.36547291092746, pbf=8.762170798898076, basalMetabolism=1376.2, water=68.0816704775023, bone=2.532556033057851, muscle=24.234175734618912, imp=45.0}
         */

        /**
         * FDA
         */
        LSFormulaUser fdaUser=new LSFormulaUser();
        fdaUser.setAge(30);
        fdaUser.setHeight(1.65);
        fdaUser.setWeight(50);
        fdaUser.setMale(true);
        fdaUser.setAthlete(false);
        Object obj2 = LSWeightCalcuate.getInstance().calculateBodyCompositionData(LSFormulaType.FDA,fdaUser,550);
        if(obj2!=null && obj2 instanceof LSWeightCalcuateData){
            LSWeightCalcuateData weightStatus=(LSWeightCalcuateData)obj2;
            hw.setText(weightStatus.toString());
            Log.d("LS-BLE", "onCreate: Vendor LSWeightCalcuateData:" + weightStatus);
        }
        else if(obj2!=null && obj2 instanceof LSBodyCompositionData){
            LSBodyCompositionData weightStatus=(LSBodyCompositionData)obj2;
            hw.setText(weightStatus.toString());
            /**
             * WeightAppendData    [basalMetabolism=1376.2, bodyFatRatio=8.762170798898076, bodyWaterRatio=68.0816704775023, muscleMassRatio=24.234175734618912, boneDensity=2.532556033057851, imp=45.0, bmi=18.36547291092746, proteinContent=18.09104665748393, visceralFat=-46.62157654466745]
             * LSBodyCompositionData [basalMetabolism=1376.2, bodyFatRatio=8.762170798898076, bodyWaterRatio=68.0816704775023, muscleMassRatio=24.234175734618912, boneDensity=2.532556033057851, imp=45.0, bmi=18.36547291092746, proteinContent=18.09104665748393, visceralFat=-46.62157654466745]
             */
            Log.e("LS-BLE", "onCreate: FDA LSWeightCalcuateData:" + weightStatus);
        }
    }
}
