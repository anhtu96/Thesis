/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.devices;

/**
 *
 * @author root
 */
public class PM5350 {

    /**
     * METER DATA (BASIC)
     */
    /* 1s Metering */
    public static final int[] CURRENT_A = {3000, 2, 2};
    public static final int[] CURRENT_B = {3002, 2, 2};
    public static final int[] CURRENT_C = {3004, 2, 2};
    public static final int[] CURRENT_N = {3006, 2, 2};
    public static final int[] CURRENT_G = {3008, 2, 2};
    public static final int[] CURRENT_AVG = {3010, 2, 2};
    public static final int[] CURRENT_UNBALANCE_A = {3012, 2, 2};
    public static final int[] CURRENT_UNBALANCE_B = {3014, 2, 2};
    public static final int[] CURRENT_UNBALANCE_C = {3016, 2, 2};
    public static final int[] CURRENT_UNBALANCE_WORST = {3018, 2, 2};
    public static final int[] VOLT_AB = {3020, 2, 2};
    public static final int[] VOLT_BC = {3022, 2, 2};
    public static final int[] VOLT_CA = {3024, 2, 2};
    public static final int[] VOLT_LL_AVG = {3026, 2, 2};
    public static final int[] VOLT_AN = {3028, 2, 2};
    public static final int[] VOLT_BN = {3030, 2, 2};
    public static final int[] VOLT_CN = {3032, 2, 2};
    public static final int[] VOLT_LN_AVG = {3034, 2, 2};
    public static final int[] VOLT_UNBALANCE_AB = {3038, 2, 2};
    public static final int[] VOLT_UNBALANCE_BC = {3040, 2, 2};
    public static final int[] VOLT_UNBALANCE_CA = {3042, 2, 2};
    public static final int[] VOLT_UNBALANCE_LL_WORST = {3044, 2, 2};
    public static final int[] VOLT_UNBALANCE_AN = {3046, 2, 2};
    public static final int[] VOLT_UNBALANCE_BN = {3048, 2, 2};
    public static final int[] VOLT_UNBALANCE_CN = {3050, 2, 2};
    public static final int[] VOLT_UNBALANCE_LN_WORST = {3052, 2, 2};
    public static final int[] ACTIVE_POW_A = {3054, 2, 2};
    public static final int[] ACTIVE_POW_B = {3056, 2, 2};
    public static final int[] ACTIVE_POW_C = {3058, 2, 2};
    public static final int[] ACTIVE_POW_TOTAL = {3060, 2, 2};
    public static final int[] REACTIVE_POW_A = {3062, 2, 2};
    public static final int[] REACTIVE_POW_B = {3064, 2, 2};
    public static final int[] REACTIVE_POW_C = {3066, 2, 2};
    public static final int[] REACTIVE_POW_TOTAL = {3068, 2, 2};
    public static final int[] APPARENT_POW_A = {3070, 2, 2};
    public static final int[] APPARENT_POW_B = {3072, 2, 2};
    public static final int[] APPARENT_POW_C = {3074, 2, 2};
    public static final int[] APPARENT_POW_TOTAL = {3076, 2, 2};

    public static final int[] POW_FACTOR_A = {3078, 2, 4};  //4Q PF FQ TYPE
    public static final int[] POW_FACTOR_B = {3080, 2, 4};
    public static final int[] POW_FACTOR_C = {3082, 2, 4};
    public static final int[] POW_FACTOR_TOTAL = {3084, 2, 4};
    public static final int[] DISPLACEMENT_POW_FACTOR_A = {3086, 2, 4};
    public static final int[] DISPLACEMENT_POW_FACTOR_B = {3088, 2, 4};
    public static final int[] DISPLACEMENT_POW_FACTOR_C = {3090, 2, 4};
    public static final int[] DISPLACEMENT_POW_FACTOR_TOTAL = {3092, 2, 4};
    public static final int[] FREQ = {3110, 2, 2};
    public static final int[] PHASE_ROTATION = {3134, 1, 1};
    /* Energy */
 /* Accumulated Energy */
    public static final int[] ACC_ENERGY_RESET_DATETIME = {3200, 4, 5};
    public static final int[] ACTIVE_ENERGY_DELIVERED = {3204, 4, 5};
    public static final int[] ACTIVE_ENERGY_RECEIVED = {3208, 4, 5};
    public static final int[] ACTIVE_ENERGY_DELIVERED_PLUS_RECEIVED = {3212, 4, 5};
    public static final int[] ACTIVE_ENERGY_DELIVERED_MINUS_RECEIVED = {3216, 4, 5};
    public static final int[] REACTIVE_ENERGY_DELIVERED = {3220, 4, 5};
    public static final int[] REACTIVE_ENERGY_RECEIVED = {3224, 4, 5};
    public static final int[] REACTIVE_ENERGY_DELIVERED_PLUS_RECEIVED = {3228, 4, 5};
    public static final int[] REACTIVE_ENERGY_DELIVERED_MINUS_RECEIVED = {3232, 4, 5};
    public static final int[] APPARENT_ENERGY_DELIVERED = {3236, 4, 5};
    public static final int[] APPARENT_ENERGY_RECEIVED = {3240, 4, 5};
    public static final int[] APPARENT_ENERGY_DELIVERED_PLUS_RECEIVED = {3244, 4, 5};
    public static final int[] APPARENT_ENERGY_DELIVERED_MINUS_RECEIVED = {3238, 4, 5};
    /* Demand */
 /* System 1 - Power */
    public static final int[] POW_DEMAND_METHOD = {3701, 1, 1};
    public static final int[] POW_DEMAND_INTERVAL_DURATION = {3702, 1, 1};
    public static final int[] POW_DEMAND_SUBINTERVAL_DURATION = {3703, 1, 1};
    public static final int[] POW_DEMAND_ELAPSED_TIME_INTERVAL = {3704, 1, 1};
    public static final int[] POW_DEMAND_ELAPSED_TIME_SUBINTERVAL = {3705, 1, 1};
    public static final int[] POW_DEMAND_PEAK_RESET_DT = {3706, 4, 6};//DATETIME TYPE
    /* System 2 - Current */
    public static final int[] CURRENT_DEMAND_METHOD = {3711, 1, 1};
    public static final int[] CURRENT_DEMAND_INTERVAL_DURATION = {3712, 1, 1};
    public static final int[] CURRENT_DEMAND_SUBINTERVAL_DURATION = {3713, 1, 1};
    public static final int[] CURRENT_DEMAND_ELAPSED_TIME_INTERVAL = {3714, 1, 1};
    public static final int[] CURRENT_DEMAND_ELAPSED_TIME_SUBINTERVAL = {3715, 1, 1};
    public static final int[] CURRENT_DEMAND_PEAK_RESET_DT = {3716, 4, 6};

    /* Channel 1 - Active Power */
    public static final int[] DEMAND_SYS_ASSIGNMENT_1 = {3761, 1, 1};
    public static final int[] REG_NUM_OF_METERED_QUANTITY_1 = {3762, 1, 1};
    public static final int[] UNITS_CODE_1 = {3763, 1, 1};
    public static final int[] LAST_DEMAND_1 = {3764, 2, 2};
    public static final int[] PRESENT_DEMAND_1 = {3766, 2, 2};
    public static final int[] PREDICTED_DEMAND_1 = {3768, 2, 2};
    public static final int[] PEAK_DEMAND_1 = {3770, 2, 2};
    public static final int[] PEAK_DEMAND_DT_1 = {3772, 4, 6};
    /* Channel 2 - Reactive Power */
    public static final int[] DEMAND_SYS_ASSIGNMENT_2 = {3777, 1, 1};
    public static final int[] REG_NUM_OF_METERED_QUANTITY_2 = {3778, 1, 1};
    public static final int[] UNITS_CODE_2 = {3779, 1, 1};
    public static final int[] LAST_DEMAND_2 = {3780, 2, 2};
    public static final int[] PRESENT_DEMAND_2 = {3782, 2, 2};
    public static final int[] PREDICTED_DEMAND_2 = {3784, 2, 2};
    public static final int[] PEAK_DEMAND_2 = {3786, 2, 2};
    public static final int[] PEAK_DEMAND_DT_2 = {3788, 4, 6};
    /* Channel 3 - Apparent Power */
    public static final int[] DEMAND_SYS_ASSIGNMENT_3 = {3793, 1, 1};
    public static final int[] REG_NUM_OF_METERED_QUANTITY_3 = {3794, 1, 1};
    public static final int[] UNITS_CODE_3 = {3795, 1, 1};
    public static final int[] LAST_DEMAND_3 = {3796, 2, 2};
    public static final int[] PRESENT_DEMAND_3 = {3798, 2, 2};
    public static final int[] PREDICTED_DEMAND_3 = {3800, 2, 2};
    public static final int[] PEAK_DEMAND_3 = {3802, 2, 2};
    public static final int[] PEAK_DEMAND_DT_3 = {3804, 4, 6};
    /* Channel 8 - Current Avg */
    public static final int[] DEMAND_SYS_ASSIGNMENT_8 = {3873, 1, 1};
    public static final int[] REG_NUM_OF_METERED_QUANTITY_8 = {3874, 1, 1};
    public static final int[] UNITS_CODE_8 = {3875, 1, 1};
    public static final int[] LAST_DEMAND_8 = {3876, 2, 2};
    public static final int[] PRESENT_DEMAND_8 = {3878, 2, 2};
    public static final int[] PREDICTED_DEMAND_8 = {3880, 2, 2};
    public static final int[] PEAK_DEMAND_8 = {3882, 2, 2};
    public static final int[] PEAK_DEMAND_DT_8 = {3884, 4, 6};

}
