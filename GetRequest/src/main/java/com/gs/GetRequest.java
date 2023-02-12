package com.gs;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaixinwei
 * @date 2023/1/9
 */
public class GetRequest {

    private static final String URL = "";


    private static int PAGE_SIZE = 200;
    private static int PAGE_COUNT = 0;

    private static final String QUERY = "";

    private static final String IDENTITY = "69AF02C982BD4015989CC290AFE89CC14E098D316434E323GI3DMNDCF4ZTMKZQG42DKKZSL4YTCKZWGFSDIKZUF4XGGMS7L5QWIYRUGEXT2ZTRL53W62LXF5RXE4LKMFTXQY3UMFUXOZ3UPBRDAMBTGEZC4MBQGUXTONZXFYXTAMBSF4YDAMQ";

    private static final String HEAD_AUTH = "Authorization";
    private static final String HEAD_Auth_VAL = "Bearer eyJhbGciOiJSUzI1NiJ9.eyJsb2dpbl9uYW1lIjoiVDA2MTg0MTAiLCJ1c2VyX2lkIjoiaHNheXFreTFldHNsY2l6ZXZja3lpdnpkIiwib3JnYW5pemF0aW9uIjoiT1JHQVRMMTEwMDA1MTg2IiwiaXNzIjoiYTM2YzMwNDliMzYyNDlhM2M5Zjg4OTFjYjEyNzI0M2MiLCJkaXNwbGF5X25hbWUiOiLltJTlvIDpmJQiLCJyZWdpb24iOiJUTCIsImlhdCI6MTY3MTQzNTkyOSwiZXhwIjoxNjc0MDI3OTI5fQ.QkvsF7MutUg3h85dYLqwig-k-PnVf6O_oy3trKOBXFRIpsEAeFKQ1aK5N1BEMKZ7pzO4D7g8EqvH3yfd8mbRnlAB_EeiJ6_Pv3QyNk0J9TF8dlRlWy8HJUnFfo0ccrrnXI2Q9cOwlK_CYktXHhJlOCyavtA6pE7ljzxxwlH5bfvS3oAAZnlrqE61djKgv9Uddv_wDcqCb-Vv7m1VCz3pkPLVKlMfQfVSh9UysRt8KpJUBu2wgylQCyWTXWlGW6mrrhr0HrgTB_F22pK2VlijXiXdxV0RfBC8gqEESDeOv9j5EZgRDVv-l6cNrDGJgUiRUaae0u5J2s-YAf3Qu3LNYQ";
    public static void main(String[] args) throws IOException {
        GetRequest getRequest = new GetRequest();
        for (int i = 0; i < PAGE_COUNT; i++) {
            getRequest.request(i);
        }
    }

    private void request(int pageNum) throws IOException {
        String url = URL + "?identity="+IDENTITY+"&page="+pageNum+"&size=" + PAGE_SIZE + "&query="+QUERY;
        OkHttpClient client = new OkHttpClient();
        Map paraMap = new HashMap();
        paraMap.put("yybh", "1231231");

        RequestBody requestBody = new MultipartBody.Builder()
//                .addFormDataPart("consumerAppId", "tst")
//                .addFormDataPart("serviceName", "queryCipher")
//                .addFormDataPart("params", JSON.toJSONString(paraMap))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader(HEAD_AUTH, HEAD_Auth_VAL)
                .build();
        Response response = client
                .newCall(request)
                .execute();
        if (response.isSuccessful()) {
            System.out.println("result:" + response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}
//
//
//没有数据权限：CD_STATION、 CD_PIPING、X_SF_PROD_WELL_INFO、X_SF_G_GAS_WELL_INFO
//X_SF_MACH_PUMP_INFO、X_SF_HEAT_FURN_INFO