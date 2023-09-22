package com.insert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhaixinwei
 * @date 2023/2/6
 */
public class Application {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/talimu";

    private static final String USER = "postgres";
    private static final String PASS = "root";

    public static void main(String[] args) throws IOException {
        List<String> csvField = Arrays.asList(
                "ORG_NAME","ORG_ID","PROJECT_NAME","PROJECT_ID","WELL_COMMON_NAME","WELL_ID","WELL_PURPOSE","WELL_TYPE","WELL_TYPE_ID","PROD_DATE"
        );
//        dsid,data_region,data_group,id,well_common_name,bsflag
        List<String> fixField = Arrays.asList("DSID","DATA_REGION","WELLBORE_LABEL","bsflag");

        String csvPath = "C:\\Users\\T470\\Desktop\\cd_well_source.csv";

        String tableName = "public.x_cd_well_source";

        List<String> parse = new CsvParse().parse(csvPath);

        DataSourceOpt dataSourceOpt = new DataSourceOpt(JDBC_DRIVER,DB_URL,USER,PASS);
        GenerateSql generateSql = new GenerateSql(tableName,csvField,fixField,parse,dataSourceOpt);

        generateSql.generateSql(()-> "'" +DSIDUtil.generateId("TEST_") + "',"  + "'TL'," +"'测试数据label',"+1+",");

    }
}

// - X_CD_WELL_SOURCE

//ORG_NAME,ORG_ID,PROJECT_NAME,PROJECT_ID,WELL_COMMON_NAME,WELL_ID,WELL_PURPOSE,WELL_TYPE,WELL_TYPE_ID,PROD_DATE
//DSID,DATA_REGION,WELLBORE_LABEL,bsflag

// - X_OP_WELL_VOL_DAILY
// "WELL_ID","PROD_DATE","PROD_TIME","LIQ_PROD_DAILY","OIL_PROD_DAILY","WATER_PROD_DAILY","GAS_PROD_DAILY","GAS_RELEASE_DAILY","SELF_USED_GAS_VOL","WATER_CUT","SAND_CONTENT_RATIO","GAS_OIL_RATIO","GAS_FLUID_CONTENT","GAS_CHLORIDE_CONTENT","STIM_KEY_ID","MAINTAIN_TYPE","DOWNTIME_TYPE","REMARKS","THERMAL_TAG","REMAIN_DEDU_TIME","WATER_GAS_RATIO","REMAIN_DEDU_VOL","SALT_CUT","WATER_DEDUCT","TEST_LIQ_PROD_DAILY","COMPOSITE_GAS_LIQUID_RATIO","COMPOSITE_GAS_PROD_DAILY","INJ_GAS_LIQUID_RATIO","WELL_PURPOSE","DRIVING_TYPE_CODE"

// "dsid","data_region","well_common_name","bsflag

// generateSql.generateSql(()-> "'" +DSIDUtil.generateId("TEST_") + "',"  + "'TL'," + "'测试井'," +1+",");

// - X_OP_WELL_STATUS_DAILY

// "WELL_ID","PROD_DATE","OIL_PROD_METHOD","OIL_NOZZLE","OIL_NOZZLE2","BACK_PRES","UP_CURRENT","DOWN_CURRENT",
//"ELEC_PUMP_CURRENT_A","ELEC_PUMP_CURRENT_B","ELEC_PUMP_CURRENT_C","ELEC_PUMP_VOLTAGE","WATER_MIX_PRES",
//"WATER_MIX_TEMP","RETU_OIL_TEMP","STATION_ENTRY_TEMP","WH_TEMP","MAIN_LINE_PRES","GAS_MEASURE_PRESSURE",
//"GAS_MEASURE_PRES_DIFF","GAS_MEASURE_TEMPERATURE","GAS_MEASURE_BAFFLE","NEEDLE_VALVE_OPEN_DEGREE",
//"MAX_OIL_PRES","MIN_OIL_PRES","AVG_OIL_PRES","MAX_CASING_PRES","MIN_CASING_PRES","AVG_CASING_PRES",
//"REVOLUTIONS","TUBING_PRES","CASING_PRES","SHUTDOWN_TUBING_PRES","SHUTDOWN_CASING_PRES","FLOW_FLAG_MACH",
//"WATER_MIX_VOL_DAILY","WATER_CUT_WATER_MIX","PCP_CURRENT","CABLE_SETTING_DEPTH","CABLE_SETTING_CURRENT",
//"COMPLETION_PERIOD","BAILING_TIMES","START_LIQ_DEPTH","END_LIQ_DEPTH",
//"BOUND_WATER","BEFORE_BAILING_WEIGHT","AFTER_BAILING_WEIGHT","TANK_VOL","BEFORE_BAILING_DEPTH",
//"AFTER_BAILING_DEPTH","CALIBRATE_VOL","CASING_INNER_DIAMETER","STATION_EXIT_PRES","STATION_ENTRY_PRES",
//"SEPERATOR_PRES","MEASURE_TEMP","INJ_PUMP_PRES","MIXED_LIQ_TYPE","OIL_HOUR_TIME","OIL_TIME",
//"START_PUMP_CASINGPRES","END_PUMP_CASINGPRES","START_PUMP_TUBPRES","END_PUMP_TUBPRES","START_PUMP_DEPTH",
//"START_PUMP_LIQ_LEVEL","PUMP_TIME","CHOKE_TRAN_TIME","END_PUMP_DEPTH","END_PUMP_LIQ_LEVEL","OIL_APPEAR_TIME",
//"SEPERATOR_TEMP","JOB_SYSTEM","MIX_LIQ_VOL","UNDERGROUND_MIX_WAT_PRES","UNDERGROUND_MIX_WAT_TEMP",
//"UNDERGROUND_MIX_WAT_CHOKE","UNDERGROUND_MIX_WAT_VOL","MIX_WAT_CHOKE","MIX_WAT_VOL","LIQU_MIX_METHOD",
//"ELEC_PUMP_CONT_VOLTAGE","UNDERGROUND_MIX_WAT_CUT","PUMP_EFFECT","COVER_NOZZLE_DIAMETER",
//"MIX_OIL_DENSITY","CAV_VALVE_OPEN_DEGREE","GAS_LIFT_DEPTH","GAS_NOZZLE","INJ_GAS_PRES",
//"INJ_GAS_TEMP","VALVE_NUMBER","TAIL_GAS_INSTANT_FLOW"

//"dsid","data_region","data_group","id","well_common_name","bsflag"

// generateSql.generateSql(()-> "'" +DSIDUtil.generateId("TEST_") + "',"  + "'TL'," +"'test_group','"+DSIDUtil.generateId(null)+ "','测试井'," +1+",");