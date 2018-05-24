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
public class PM710 {

    private static final int[] read1 = {999, 20, 0, 0};
    private static final int[] read2 = {1019, 20, 0, 0};
    private static final int[] read3 = {3999, 102, 0, 0};
    private static final int[] read4 = {4101, 27, 0, 0};
    public static final int iter = 2;
    public static final int regNum = 40;
    public static final int buffSize = 80;
    public static final int[][] read = {read1, read2, read3, read4};
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    /**
     * Setup and Status
     */
    public static final int[] USAGE_HOURS = {1204, 2, 2, 408};
    public static final int[] USAGE_MINS = {1206, 2, 2, 412};
    public static final int[] SCALE_FACTOR_I = {4105, 1, 1, 626};
    public static final int[] SCALE_FACTOR_V = {4106, 1, 1, 628};
    public static final int[] SCALE_FACTOR_W = {4107, 1, 1, 630};
    public static final int[] SCALE_FACTOR_E = {4108, 1, 1, 632};
    public static final int[] USAGE_HOURS_INT = {4110, 1, 1, 636};
    public static final int[] USAGE_MINS_INT = {4111, 1, 1, 638};
    public static final int[] ERR_BMP = {4112, 1, 1, 640};
    public static final int[] THERMAL_DEMAND_INTERVAL = {4117, 1, 1, 650};
    public static final int[] POW_BLOCK_DEMAND_INTERVAL = {4118, 1, 1, 652};
    public static final int[] PBD_NO_OF_SUB_INTERVALS = {4119, 1, 1, 654};
    public static final int[] CTRATIO_PRIMARY = {4120, 1, 1, 656};
    public static final int[] CTRATIO_SECONDARY = {4121, 1, 1, 658};
    public static final int[] PTRATIO_PRIMARY = {4122, 1, 1, 660};
    public static final int[] PTRATIO_SCALE = {4123, 1, 1, 662};
    public static final int[] PTRATIO_SECONDARY = {4124, 1, 1, 664};
    public static final int[] SERVICE_FREQ = {4125, 1, 1, 666};
    public static final int[] RESET_COMM = {4126, 1, 1, 668};
    public static final int[] SYS_TYPE = {4127, 1, 1, 670};
    public static final int[] DISP_MODE = {4128, 1, 1, 672};

    /**
     * Metered Data
     */
    public static final int[] REAL_ENERGY_TOTAL = {1000, 2, 2, 0};
    public static final int[] APP_ENERGY_TOTAL = {1002, 2, 2, 4};
    public static final int[] REACT_ENERGY_TOTAL = {1004, 2, 2, 8};
    public static final int[] REAL_POW_TOTAL = {1006, 2, 2, 12};
    public static final int[] APP_POW_TOTAL = {1008, 2, 2, 16};
    public static final int[] REACT_POW_TOTAL = {1010, 2, 2, 20};
    public static final int[] POW_FACTOR_TOTAL = {1012, 2, 2, 24};
    public static final int[] VOLT_LL_3P_AVG = {1014, 2, 2, 28};
    public static final int[] VOLT_LN_3P_AVG = {1016, 2, 2, 32};
    public static final int[] CURRENT_3P_AVG = {1018, 2, 2, 36};
    public static final int[] FREQ = {1020, 2, 2, 40};
    public static final int[] CURRENT_A = {1034, 2, 2, 68};
    public static final int[] CURRENT_B = {1036, 2, 2, 72};
    public static final int[] CURRENT_C = {1038, 2, 2, 76};
    public static final int[] CURRENT_N = {1040, 2, 2, 80};
    public static final int[] VOLT_AB = {1054, 2, 2, 108};
    public static final int[] VOLT_BC = {1056, 2, 2, 112};
    public static final int[] VOLT_CA = {1058, 2, 2, 116};
    public static final int[] VOLT_AN = {1060, 2, 2, 120};
    public static final int[] VOLT_BN = {1062, 2, 2, 124};
    public static final int[] VOLT_CN = {1064, 2, 2, 128};
    public static final int[] REAL_POW_A = {1066, 2, 2, 132};
    public static final int[] REAL_POW_B = {1068, 2, 2, 136};
    public static final int[] REAL_POW_C = {1070, 2, 2, 140};
    public static final int[] APP_POW_A = {1072, 2, 2, 144};
    public static final int[] APP_POW_B = {1074, 2, 2, 148};
    public static final int[] APP_POW_C = {1076, 2, 2, 152};
    public static final int[] REACT_POW_A = {1078, 2, 2, 156};
    public static final int[] REACT_POW_B = {1080, 2, 2, 160};
    public static final int[] REACT_POW_C = {1082, 2, 2, 164};
    public static final int[] CURRENT_A_THD = {1084, 2, 2, 168};
    public static final int[] CURRENT_B_THD = {1086, 2, 2, 172};
    public static final int[] CURRENT_C_THD = {1088, 2, 2, 176};
    public static final int[] VOLT_AN_THD = {1092, 2, 2, 184};
    public static final int[] VOLT_BN_THD = {1094, 2, 2, 188};
    public static final int[] VOLT_CN_THD = {1096, 2, 2, 192};
    public static final int[] VOLT_AB_THD = {1098, 2, 2, 196};
    public static final int[] VOLT_BC_THD = {1100, 2, 2, 200};
    public static final int[] VOLT_CA_THD = {1102, 2, 2, 204};

    public static final int[] REAL_ENERGY_TOTAL_LONG = {4000, 2, 3, 416};
    public static final int[] APP_ENERGY_TOTAL_LONG = {4002, 2, 3, 420};
    public static final int[] REACT_ENERGY_TOTAL_LONG = {4004, 2, 3, 424};
    public static final int[] REAL_POW_TOTAL_INT = {4006, 1, 1, 428};
    public static final int[] APP_POW_TOTAL_INT = {4007, 1, 1, 430};
    public static final int[] REACT_POW_TOTAL_INT = {4008, 1, 1, 432};
    public static final int[] POW_FACTOR_TOTAL_INT = {4009, 1, 1, 434};
    public static final int[] VOLT_LL_3P_AVG_INT = {4010, 1, 1, 436};
    public static final int[] VOLT_LN_3P_AVG_INT = {4011, 1, 1, 438};
    public static final int[] CURRENT_3P_AVG_INT = {4012, 1, 1, 440};
    public static final int[] FREQ_INT = {4013, 1, 1, 442};
    public static final int[] CURRENT_A_INT = {4020, 1, 1, 456};
    public static final int[] CURRENT_B_INT = {4021, 1, 1, 458};
    public static final int[] CURRENT_C_INT = {4022, 1, 1, 460};
    public static final int[] CURRENT_N_INT = {4023, 1, 1, 462};
    public static final int[] VOLT_AB_INT = {4030, 1, 1, 476};
    public static final int[] VOLT_BC_INT = {4031, 1, 1, 478};
    public static final int[] VOLT_CA_INT = {4032, 1, 1, 480};
    public static final int[] VOLT_AN_INT = {4033, 1, 1, 482};
    public static final int[] VOLT_BN_INT = {4034, 1, 1, 484};
    public static final int[] VOLT_CN_INT = {4035, 1, 1, 486};
    public static final int[] REAL_POW_A_INT = {4036, 1, 1, 488};
    public static final int[] REAL_POW_B_INT = {4037, 1, 1, 490};
    public static final int[] REAL_POW_C_INT = {4038, 1, 1, 492};
    public static final int[] APP_POW_A_INT = {4039, 1, 1, 494};
    public static final int[] APP_POW_B_INT = {4040, 1, 1, 496};
    public static final int[] ARRARENT_POW_C_INT = {4041, 1, 1, 498};
    public static final int[] REACT_POW_A_INT = {4042, 1, 1, 500};
    public static final int[] REACT_POW_B_INT = {4043, 1, 1, 502};
    public static final int[] REACT_POW_C_INT = {4044, 1, 1, 504};
    public static final int[] CURRENT_A_THD_INT = {4045, 1, 1, 506};
    public static final int[] CURRENT_B_THD_INT = {4046, 1, 1, 508};
    public static final int[] CURRENT_C_THD_INT = {4047, 1, 1, 510};
    public static final int[] VOLT_AN_THD_INT = {4049, 1, 1, 514};
    public static final int[] VOLT_BN_THD_INT = {4050, 1, 1, 516};
    public static final int[] VOLT_CN_THD_INT = {4051, 1, 1, 518};
    public static final int[] VOLT_AB_THD_INT = {4052, 1, 1, 520};
    public static final int[] VOLT_BC_THD_INT = {4053, 1, 1, 522};
    public static final int[] VOLT_CA_THD_INT = {4054, 1, 1, 524};

    /**
     * Demand Values
     */
    public static final int[] REAL_POW_TOTAL_DEMAND_PRESENT = {1022, 2, 2, 44};
    public static final int[] APP_POW_TOTAL_DEMAND_PRESENT = {1024, 2, 2, 48};
    public static final int[] REACT_POW_TOTAL_DEMAND_PRESENT = {1026, 2, 2, 52};
    public static final int[] REAL_POW_TOTAL_DEMAND_PEAK = {1028, 2, 2, 56};
    public static final int[] APP_POW_TOTAL_DEMAND_PEAK = {1030, 2, 2, 60};
    public static final int[] REACT_POW_TOTAL_DEMAND_PEAK = {1032, 2, 2, 64};
    public static final int[] CURRENT_A_DEMAND_PRESENT = {1042, 2, 2, 84};
    public static final int[] CURRENT_B_DEMAND_PRESENT = {1044, 2, 2, 88};
    public static final int[] CURRENT_C_DEMAND_PRESENT = {1046, 2, 2, 92};
    public static final int[] CURRENT_A_DEMAND_PEAK = {1048, 2, 2, 96};
    public static final int[] CURRENT_B_DEMAND_PEAK = {1050, 2, 2, 100};
    public static final int[] CURRENT_C_DEMAND_PEAK = {1052, 2, 2, 104};

    public static final int[] REAL_POW_TOTAL_DEMAND_PRESENT_INT = {4014, 1, 1, 444};
    public static final int[] APP_POW_TOTAL_DEMAND_PRESENT_INT = {4015, 1, 1, 446};
    public static final int[] REACT_POW_TOTAL_DEMAND_PRESENT_INT = {4016, 1, 1, 448};
    public static final int[] REAL_POW_TOTAL_DEMAND_PEAK_INT = {4017, 1, 1, 450};
    public static final int[] APP_POW_TOTAL_DEMAND_PEAK_INT = {4018, 1, 1, 452};
    public static final int[] REACT_POW_TOTAL_DEMAND_PEAK_INT = {4019, 1, 1, 454};
    public static final int[] CURRENT_A_DEMAND_PRESENT_INT = {4024, 1, 1, 464};
    public static final int[] CURRENT_B_DEMAND_PRESENT_INT = {4025, 1, 1, 466};
    public static final int[] CURRENT_C_DEMAND_PRESENT_INT = {4026, 1, 1, 468};
    public static final int[] CURRENT_A_DEMAND_PEAK_INT = {4027, 1, 1, 470};
    public static final int[] CURRENT_B_DEMAND_PEAK_INT = {4028, 1, 1, 472};
    public static final int[] CURRENT_C_DEMAND_PEAK_INT = {4029, 1, 1, 474};
    /**
     * Min Max Values
     */
    public static final int[] REAL_POW_TOTAL_MIN = {1104, 2, 2, 208};
    public static final int[] APP_POW_TOTAL_MIN = {1106, 2, 2, 212};
    public static final int[] REACT_POW_TOTAL_MIN = {1108, 2, 2, 216};
    public static final int[] POW_FACTOR_TOTAL_MIN = {1110, 2, 2, 220};
    public static final int[] FREQ_MIN = {1112, 2, 2, 224};
    public static final int[] CURRENT_A_MIN = {1114, 2, 2, 228};
    public static final int[] CURRENT_B_MIN = {1116, 2, 2, 232};
    public static final int[] CURRENT_C_MIN = {1118, 2, 2, 236};
    public static final int[] VOLT_AN_MIN = {1122, 2, 2, 244};
    public static final int[] VOLT_BN_MIN = {1124, 2, 2, 248};
    public static final int[] VOLT_CN_MIN = {1126, 2, 2, 252};
    public static final int[] VOLT_AB_MIN = {1128, 2, 2, 256};
    public static final int[] VOLT_BC_MIN = {1130, 2, 2, 260};
    public static final int[] VOLT_CA_MIN = {1132, 2, 2, 264};
    public static final int[] CURRENT_A_THD_MIN = {1134, 2, 2, 268};
    public static final int[] CURRENT_B_THD_MIN = {1136, 2, 2, 272};
    public static final int[] CURRENT_C_THD_MIN = {1138, 2, 2, 276};
    public static final int[] VOLT_AN_THD_MIN = {1142, 2, 2, 284};
    public static final int[] VOLT_BN_THD_MIN = {1144, 2, 2, 288};
    public static final int[] VOLT_CN_THD_MIN = {1146, 2, 2, 292};
    public static final int[] VOLT_AB_THD_MIN = {1148, 2, 2, 296};
    public static final int[] VOLT_BC_THD_MIN = {1150, 2, 2, 300};
    public static final int[] VOLT_CA_THD_MIN = {1152, 2, 2, 304};
    public static final int[] REAL_POW_TOTAL_MAX = {1154, 2, 2, 308};
    public static final int[] APP_POW_TOTAL_MAX = {1156, 2, 2, 312};
    public static final int[] REACT_POW_TOTAL_MAX = {1158, 2, 2, 316};
    public static final int[] POW_FACTOR_TOTAL_MAX = {1160, 2, 2, 320};
    public static final int[] FREQ_MAX = {1162, 2, 2, 324};
    public static final int[] CURRENT_A_MAX = {1164, 2, 2, 328};
    public static final int[] CURRENT_B_MAX = {1166, 2, 2, 332};
    public static final int[] CURRENT_C_MAX = {1168, 2, 2, 336};
    public static final int[] VOLT_AN_MAX = {1172, 2, 2, 344};
    public static final int[] VOLT_BN_MAX = {1174, 2, 2, 348};
    public static final int[] VOLT_CN_MAX = {1176, 2, 2, 352};
    public static final int[] VOLT_AB_MAX = {1178, 2, 2, 356};
    public static final int[] VOLT_BC_MAX = {1180, 2, 2, 360};
    public static final int[] VOLT_CA_MAX = {1182, 2, 2, 364};
    public static final int[] CURRENT_A_THD_MAX = {1184, 2, 2, 368};
    public static final int[] CURRENT_B_THD_MAX = {1186, 2, 2, 372};
    public static final int[] CURRENT_C_THD_MAX = {1188, 2, 2, 376};
    public static final int[] VOLT_AN_THD_MAX = {1192, 2, 2, 384};
    public static final int[] VOLT_BN_THD_MAX = {1194, 2, 2, 388};
    public static final int[] VOLT_CN_THD_MAX = {1196, 2, 2, 392};
    public static final int[] VOLT_AB_THD_MAX = {1198, 2, 2, 396};
    public static final int[] VOLT_BC_THD_MAX = {1200, 2, 2, 400};
    public static final int[] VOLT_CA_THD_MAX = {1202, 2, 2, 404};

    public static final int[] REAL_POW_TOTAL_MIN_INT = {4055, 1, 1, 526};
    public static final int[] APP_POW_TOTAL_MIN_INT = {4056, 1, 1, 528};
    public static final int[] REACT_POW_TOTAL_MIN_INT = {4057, 1, 1, 530};
    public static final int[] POW_FACTOR_TOTAL_MIN_INT = {4058, 1, 1, 532};
    public static final int[] FREQ_MIN_INT = {4059, 1, 1, 534};
    public static final int[] CURRENT_A_MIN_INT = {4060, 1, 1, 536};
    public static final int[] CURRENT_B_MIN_INT = {4061, 1, 1, 538};
    public static final int[] CURRENT_C_MIN_INT = {4062, 1, 1, 540};
    public static final int[] VOLT_AN_MIN_INT = {4064, 1, 1, 544};
    public static final int[] VOLT_BN_MIN_INT = {4065, 1, 1, 546};
    public static final int[] VOLT_CN_MIN_INT = {4066, 1, 1, 548};
    public static final int[] VOLT_AB_MIN_INT = {4067, 1, 1, 550};
    public static final int[] VOLT_BC_MIN_INT = {4068, 1, 1, 552};
    public static final int[] VOLT_CA_MIN_INT = {4069, 1, 1, 554};
    public static final int[] CURRENT_A_THD_MIN_INT = {4070, 1, 1, 556};
    public static final int[] CURRENT_B_THD_MIN_INT = {4071, 1, 1, 558};
    public static final int[] CURRENT_C_THD_MIN_INT = {4072, 1, 1, 560};
    public static final int[] VOLT_AN_THD_MIN_INT = {4074, 1, 1, 564};
    public static final int[] VOLT_BN_THD_MIN_INT = {4075, 1, 1, 566};
    public static final int[] VOLT_CN_THD_MIN_INT = {4076, 1, 1, 568};
    public static final int[] VOLT_AB_THD_MIN_INT = {4077, 1, 1, 570};
    public static final int[] VOLT_BC_THD_MIN_INT = {4078, 1, 1, 572};
    public static final int[] VOLT_CA_THD_MIN_INT = {4079, 1, 1, 574};
    public static final int[] REAL_POW_TOTAL_MAX_INT = {4080, 1, 1, 576};
    public static final int[] APP_POW_TOTAL_MAX_INT = {4081, 1, 1, 578};
    public static final int[] REACT_POW_TOTAL_MAX_INT = {4082, 1, 1, 580};
    public static final int[] POW_FACTOR_TOTAL_MAX_INT = {4083, 1, 1, 582};
    public static final int[] FREQ_MAX_INT = {4084, 1, 1, 584};
    public static final int[] CURRENT_A_MAX_INT = {4085, 1, 1, 586};
    public static final int[] CURRENT_B_MAX_INT = {4086, 1, 1, 588};
    public static final int[] CURRENT_C_MAX_INT = {4087, 1, 1, 590};
    public static final int[] VOLT_AN_MAX_INT = {4089, 1, 1, 594};
    public static final int[] VOLT_BN_MAX_INT = {4090, 1, 1, 596};
    public static final int[] VOLT_CN_MAX_INT = {4091, 1, 1, 598};
    public static final int[] VOLT_AB_MAX_INT = {4092, 1, 1, 600};
    public static final int[] VOLT_BC_MAX_INT = {4093, 1, 1, 602};
    public static final int[] VOLT_CA_MAX_INT = {4094, 1, 1, 604};
    public static final int[] CURRENT_A_THD_MAX_INT = {4095, 1, 1, 606};
    public static final int[] CURRENT_B_THD_MAX_INT = {4096, 1, 1, 608};
    public static final int[] CURRENT_C_THD_MAX_INT = {4097, 1, 1, 610};
    public static final int[] VOLT_AN_THD_MAX_INT = {4099, 1, 1, 614};
    public static final int[] VOLT_BN_THD_MAX_INT = {4100, 1, 1, 616};
    public static final int[] VOLT_CN_THD_MAX_INT = {4101, 1, 1, 618};
    public static final int[] VOLT_AB_THD_MAX_INT = {4102, 1, 1, 620};
    public static final int[] VOLT_BC_THD_MAX_INT = {4103, 1, 1, 622};
    public static final int[] VOLT_CA_THD_MAX_INT = {4104, 1, 1, 624};
}
