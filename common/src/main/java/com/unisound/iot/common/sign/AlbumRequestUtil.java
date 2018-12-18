package com.unisound.iot.common.sign;/**
 * Created by Admin on 2017/12/19.
 */

import com.unisound.iot.common.constants.CommonConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @company: 北京云知声信息技术有限公司
 * @author: FANGNAN
 * @date: 2017/12/19
 **/
public class AlbumRequestUtil {

    public static Map<String, String> getCommonParams() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", CommonConstants.MANAGE_KEY );
        params.put("scenario", "child");
        return params;
    }

    /**
     * 签名算法
     *
     * @param params
     *            要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Map<String, String> params) throws Exception {

        params.remove("sign");
        // 格式化并排序请求参数
        String result = formatAndSortParams(params);
        // Base64编码
        result = encryptBASE64(result);
        // HMAC-SHA1哈希运算
        byte[] hmacBytes = HmacSHA1Encrypt(result, CommonConstants.MANAGE_SECRET);
        // MD5运算
        result = new String(DigestUtils.md5Hex(hmacBytes));
        return result;
    }

    /**
     * 格式化并排序请求参数
     *
     * @param
     * @throws Exception
     * @return String
     * @throws
     *
     */
    private static String formatAndSortParams(Map<String, String> map) throws Exception {
        if (null == map || map.size() == 0) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isBlank(entry.getKey()) || StringUtils.isBlank(entry.getValue())) {
                continue;
            }
            list.add(entry.getKey().trim() + "=" + encode(entry.getValue().trim()) + "&");
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String params = sb.toString();

        if (params.endsWith("&")) {
            params = params.substring(0, params.length() - 1);
        }
        return params;
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) throws Exception {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String ret = encryptBASE64(key.getBytes());
        return ret;
    }
    public static String encryptBASE64(byte[] key) throws Exception {
        if (null == key || key.length == 0) {
            return null;
        }
        String ret = (new BASE64Encoder()).encodeBuffer(key);
        ret = ret.replaceAll("\r\n", "");
        ret = ret.replaceAll("\n", "");
        return ret;
    }


    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText
     *            被签名的字符串
     * @param encryptKey
     *            密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        if (StringUtils.isBlank(encryptText) || StringUtils.isBlank(encryptKey)) {
            return null;
        }
        String MAC_NAME = "HmacSHA1";
        String ENCODING = "UTF-8";
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    public static String encode(String input) {
        if (input == null)
            return "";
        return encode(input, "utf8");
    }

    public static String encode(String input, String code) {
        if (input == null)
            return "";
        try {
            String encodedString = URLEncoder.encode(input, code);
            return encodedString;
        } catch (Exception e) {
            return input;
        }
    }
}
