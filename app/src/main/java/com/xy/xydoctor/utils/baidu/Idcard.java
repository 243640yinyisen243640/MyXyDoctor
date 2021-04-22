package com.xy.xydoctor.utils.baidu;

import com.blankj.utilcode.util.GsonUtils;
import com.xy.xydoctor.bean.IdCardBean;

import java.net.URLEncoder;

public class Idcard {

    private static final String TAG = "Idcard";

    public static String idcard() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            //本地文件路径
            String filePath = "F:\\time.png";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "id_card_side=" + "front" + "&image=" + imgParam;
            String accessToken = "24.8eda639e70f3aae786062fe40510dcc9.2592000.1581490737.282335-18266084";
            String result = HttpUtil.post(url, accessToken, param);
            IdCardBean idCardBean = GsonUtils.fromJson(result, IdCardBean.class);
            IdCardBean.WordsResultBean resultBean = idCardBean.getWords_result();
            String name = resultBean.get姓名().getWords();
            String sex = resultBean.get性别().getWords();
            String nation = resultBean.get民族().getWords();
            String birthday = resultBean.get出生().getWords();
            String address = resultBean.get住址().getWords();
            String idNumber = resultBean.get公民身份号码().getWords();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Idcard.idcard();
    }
}
