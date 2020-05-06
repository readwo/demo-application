package com.example.demo.ase;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public  class AESUtils {


    /**
     * 密钥算法
     */
    public final static  String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    public final static  String ALGORITHM_STR = "AES/ECB/PKCS5Padding";




    /**
     * 加密
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec keySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         * 此处使用AES-128-CBC加密模式，key需要为16位。
         */
        String cSrc = "{\n" +
                "  \"plateNum\":\"京A123456\",\n" +
                "  \"plateColor\":0,\n" +
                "  \"mediaType\":0,\n" +
                "  \"vehicleType\":1,\n" +
                "  \"vehicleClass\":0,\n" +
                "  \"enTollStationId\":\"G0001210030040\",\n" +
                "  \"enTime\":\"2020-02-15T13:13:52\",\n" +
                "  \"exTollStationId\":\"G0001110010010\",\n" +
                "  \"exTime\": \"2020-02-15T17:10:00\",\n" +
                "  \"axleCount\":2,\n" +
                "  \"units\":\n" +
                "[\n" +
                "    {   \n" +
                "\"unitId\": \"G000121003001110\",\n" +
                "              \"passTime\": \"2020-02-15T14:10:00\"\n" +
                "},\n" +
                "    {   \n" +
                "\"unitId\": \"G000111001001720\",\n" +
                "              \"pastime\": \"2020-02-15T16:22:00\"\n" +
                "}\n" +
                "]\n" +
                "}\n";

        //JSONObject object = JSONObject.parseObject(cSrc);
        //String scSrc = object.toJSONString();
        // 需要加密的字串
        String cKey = "chinaetc@1234567";
        System.out.println(cSrc);
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = AESUtils.Encrypt(cSrc,cKey);
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        String msg = "Vb4Gsa7WkliWuMlWHdoALinZgM5izrGg2XAZpE53NynhG0PN4iNtePzvxzzIp9nHh26/DRj/UbkNorM1WAk4Ouzyo3Ni8j0J0IwiLEn1Wrj2U0mff8b5M2xIS/IOwGuQrnGY/iytKf7HS9rfqgq0IbJ5BmnV+R2Ffze6SsJkWSzcyLwEFG/mRy5mmfLJXe8NUCD3AXDwDq5Rih8fkHkRLQbw6y1i50gkml3MqDq29cPWyAIwxJCsEjIxConMNz3xXpDVieOwNEe7gPDgMEU/vOXFuKfsSkwN70LQttKLEHg2whv+RfKiBwh5pO98YoPFd9DH4WEGPBNy7MsbddSYda5xmP4srSn+x0va36oKtCGgyOYiMcjt8GRbJq+5bXvtLNKeN4WaOqOCmx1qFxvuhk3fW1SaavT2UkESsRie9sgxkAKgchBoPIbhogjNWy3AH3TUJZ/oFiB3kOrqrN9bV5wmEfj0jEKs8ZpBRmjBRPvzq8AsGCHm1LL0mfTWvFxZeflXtfuIPGo5G08zp+se6JasK+a+2NY4vNvMmEijdlVE2fOmtvDSILO0b/uA4bC5gjspAdVHIKI/9lagiT2djjM+Yj2Drp7lSoxK8GIbNjcFqRT+/IGNFfHePfAxea67BfG0Vv0NBrkiCaN1MHk1sot7ZvvvV0OO9PqC2gFwK6pgecVZLUkDdxjYs7Ee501ejVBySIEfnEK8OubKaPcZ13I1h+AYspFrhwuzGlp5k60wJ8ih95QQQHrO8yelDiHShHh9DkZ7xVasLYHEC2dQh+6p+24jwzWkR/SGFcYEJdvN0Zw18ARfpFhp3+RgTQZqglPsqVzsrh35B4yKb8TBegZGcjRhTySgZJJSDANlfXCNUHJIgR+cQrw65spo9xnX0gMAVkI+OECFe9kXuKNW3bzGXhZr+9jJORTIIz8VJw+XmiE0Hix/d3Kppeu9ElFVtTlOhV/uOkQgtnQ0b3im+A==";
        lStart = System.currentTimeMillis();
        String DeString = AESUtils.Decrypt(msg, cKey);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }



}