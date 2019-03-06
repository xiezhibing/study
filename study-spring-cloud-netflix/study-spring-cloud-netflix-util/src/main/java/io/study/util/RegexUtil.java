package io.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验用户名是邮箱还是手机号码
 * @author XiaoFengzi
 * @version 1.0.0
 */
public class RegexUtil implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(RegexUtil.class);
    /** 国际手机号正则表达式 */
    private static Map<String, String> INTL_MOBILE_REGEX = new HashMap<String, String>();
    static {
        INTL_MOBILE_REGEX.put("ar-DZ", "^(\\+?213|0)(5|6|7)\\d{8}$");
        INTL_MOBILE_REGEX.put("ar-SY", "^(!?(\\+?963)|0)?9\\d{8}$");
        INTL_MOBILE_REGEX.put("ar-SA", "^(!?(\\+?966)|0)?5\\d{8}$");
        INTL_MOBILE_REGEX.put("en-US", "^(\\+?1)?[2-9]\\d{2}[2-9](?!11)\\d{6}$");
        INTL_MOBILE_REGEX.put("cs-CZ", "^(\\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$");
        INTL_MOBILE_REGEX.put("de-DE", "^(\\+?49[ \\.\\-])?([\\(]{1}[0-9]{1,6}[\\)])?([0-9 \\.\\-\\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$");
        INTL_MOBILE_REGEX.put("da-DK", "^(\\+?45)?(\\d{8})$");
        INTL_MOBILE_REGEX.put("el-GR", "^(\\+?30)?(69\\d{8})$");
        INTL_MOBILE_REGEX.put("en-AU", "^(\\+?61|0)4\\d{8}$");
        INTL_MOBILE_REGEX.put("en-GB", "^(\\+?44|0)7\\d{9}$");
        INTL_MOBILE_REGEX.put("en-HK", "^(\\+?852\\-?)?[569]\\d{3}\\-?\\d{4}$");
        INTL_MOBILE_REGEX.put("en-IN", "^(\\+?91|0)?[789]\\d{9}$");
        INTL_MOBILE_REGEX.put("en-NZ", "^(\\+?64|0)2\\d{7,9}$");
        INTL_MOBILE_REGEX.put("en-ZA", "^(\\+?27|0)\\d{9}$");
        INTL_MOBILE_REGEX.put("en-ZM", "^(\\+?26)?09[567]\\d{7}$");
        INTL_MOBILE_REGEX.put("es-ES", "^(\\+?34)?(6\\d{1}|7[1234])\\d{7}$");
        INTL_MOBILE_REGEX.put("fi-FI", "^(\\+?358|0)\\s?(4(0|1|2|4|5)?|50)\\s?(\\d\\s?){4,8}\\d$");
        INTL_MOBILE_REGEX.put("fr-FR", "^(\\+?33|0)[67]\\d{8}$");
        INTL_MOBILE_REGEX.put("he-IL", "^(\\+972|0)([23489]|5[0248]|77)[1-9]\\d{6}$");
        INTL_MOBILE_REGEX.put("hu-HU", "^(\\+?36)(20|30|70)\\d{7}$");
        INTL_MOBILE_REGEX.put("it-IT", "^(\\+?39)?\\s?3\\d{2} ?\\d{6,7}$");
        INTL_MOBILE_REGEX.put("ja-JP", "^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}$");
        INTL_MOBILE_REGEX.put("ms-MY", "^(\\+?6?01){1}(([145]{1}(\\-|\\s)?\\d{7,8})|([236789]{1}(\\s|\\-)?\\d{7}))$");
        INTL_MOBILE_REGEX.put("nb-NO", "^(\\+?47)?[49]\\d{7}$");
        INTL_MOBILE_REGEX.put("nl-BE", "^(\\+?32|0)4?\\d{8}$");
        INTL_MOBILE_REGEX.put("nn-NO", "^(\\+?47)?[49]\\d{7}$");
        INTL_MOBILE_REGEX.put("pl-PL", "^(\\+?48)? ?[5-8]\\d ?\\d{3} ?\\d{2} ?\\d{2}$");
        INTL_MOBILE_REGEX.put("pt-BR", "^(\\+?55|0)\\-?[1-9]{2}\\-?[2-9]{1}\\d{3,4}\\-?\\d{4}$");
        INTL_MOBILE_REGEX.put("pt-PT", "^(\\+?351)?9[1236]\\d{7}$");
        INTL_MOBILE_REGEX.put("ru-RU", "^(\\+?7|8)?9\\d{9}$");
        INTL_MOBILE_REGEX.put("sr-RS", "^(\\+3816|06)[- \\d]{5,9}$");
        INTL_MOBILE_REGEX.put("tr-TR", "^(\\+?90|0)?5\\d{9}$");
        INTL_MOBILE_REGEX.put("vi-VN", "^(\\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$");
        INTL_MOBILE_REGEX.put("zh-CN", "^(\\+?0?86\\-?)?1[345789]\\d{9}$");
        INTL_MOBILE_REGEX.put("zh-TW", "^(\\+?886\\-?|0)?9\\d{8}$");
    }
    /**
     * 验证Email
     * @param email email地址，格式：zhangsan@163.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-+.]\\w+)*\\.\\w+([-+.]\\w+)*?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex,idCard);
    }

    /**
     * 验证国际手机号码(支持国内手机号)：
     * <pre>
     * ar-DZ: /^(\+?213|0)(5|6|7)\d{8}$/,
     * ar-SY: /^(!?(\+?963)|0)?9\d{8}$/,
     * ar-SA: /^(!?(\+?966)|0)?5\d{8}$/,
     * en-US: /^(\+?1)?[2-9]\d{2}[2-9](?!11)\d{6}$/,
     * cs-CZ: /^(\+?420)? ?[1-9][0-9]{2} ?[0-9]{3} ?[0-9]{3}$/,
     * de-DE: /^(\+?49[ \.\-])?([\(]{1}[0-9]{1,6}[\)])?([0-9 \.\-\/]{3,20})((x|ext|extension)[ ]?[0-9]{1,4})?$/,
     * da-DK: /^(\+?45)?(\d{8})$/,
     * el-GR: /^(\+?30)?(69\d{8})$/,
     * en-AU: /^(\+?61|0)4\d{8}$/,
     * en-GB: /^(\+?44|0)7\d{9}$/,
     * en-HK: /^(\+?852\-?)?[569]\d{3}\-?\d{4}$/,
     * en-IN: /^(\+?91|0)?[789]\d{9}$/,
     * en-NZ: /^(\+?64|0)2\d{7,9}$/,
     * en-ZA: /^(\+?27|0)\d{9}$/,
     * en-ZM: /^(\+?26)?09[567]\d{7}$/,
     * es-ES: /^(\+?34)?(6\d{1}|7[1234])\d{7}$/,
     * fi-FI: /^(\+?358|0)\s?(4(0|1|2|4|5)?|50)\s?(\d\s?){4,8}\d$/,
     * fr-FR: /^(\+?33|0)[67]\d{8}$/,
     * he-IL: /^(\+972|0)([23489]|5[0248]|77)[1-9]\d{6}/,
     * hu-HU: /^(\+?36)(20|30|70)\d{7}$/,
     * it-IT: /^(\+?39)?\s?3\d{2} ?\d{6,7}$/,
     * ja-JP: /^(\+?81|0)\d{1,4}[ \-]?\d{1,4}[ \-]?\d{4}$/,
     * ms-MY: /^(\+?6?01){1}(([145]{1}(\-|\s)?\d{7,8})|([236789]{1}(\s|\-)?\d{7}))$/,
     * nb-NO: /^(\+?47)?[49]\d{7}$/,
     * nl-BE: /^(\+?32|0)4?\d{8}$/,
     * nn-NO: /^(\+?47)?[49]\d{7}$/,
     * pl-PL: /^(\+?48)? ?[5-8]\d ?\d{3} ?\d{2} ?\d{2}$/,
     * pt-BR: /^(\+?55|0)\-?[1-9]{2}\-?[2-9]{1}\d{3,4}\-?\d{4}$/,
     * pt-PT: /^(\+?351)?9[1236]\d{7}$/,
     * ru-RU: /^(\+?7|8)?9\d{9}$/,
     * sr-RS: /^(\+3816|06)[- \d]{5,9}$/,
     * tr-TR: /^(\+?90|0)?5\d{9}$/,
     * vi-VN: /^(\+?84|0)?((1(2([0-9])|6([2-9])|88|99))|(9((?!5)[0-9])))([0-9]{7})$/,
     * zh-CN: /^(\+?0?86\-?)?1[345789]\d{9}$/,
     * zh-TW: /^(\+?886\-?|0)?9\d{8}$/
     * </pre>
     * @param mobile 国内和国际手机号
     * @return 验证成功返回true; 验证失败返回false
     */
    public static boolean checkInternationalMobilePhone(String mobile) {
        for (String regex : INTL_MOBILE_REGEX.values()) {
            if(Pattern.matches(regex, mobile)){
                return true;
            }
        }
        return false;
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
     * 17+除9的任意数 147
     */
    public static boolean isChinaMobilePhoneLegal(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKMobilePhoneLegal(String str) {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。</p>
     * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkTelePhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkInteger(String digit) {
        String regex = "\\-?\\d+";
        return Pattern.matches(regex,digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?\\d+(\\.\\d+)?";
        return Pattern.matches(regex,decimals);
    }

    /**
     * 验证空白字符
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex,blankSpace);
    }

    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex,chinese);
    }

    /**
     * 验证日期（年月日）
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex,birthday);
    }

    /**
     * 判断日期格式和范围
     * @param date
     * @return
     */
    public static boolean isDate(String date){
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

    /**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * <pre>
     * 获取网址 URL 的一级域
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        // 获取完整的域名
        // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }
//
//    // 邮件正则表达式
//    private static final String emailExpr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//    // 手机号正则表达
//    private static final String phoneExpr = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
//
//    /**
//     * 校验字符串是否是邮箱
//     * @param str
//     * @return
//     */
//    public static Boolean checkMail(String str){
//        if(str.matches(emailExpr)){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 校验字符串是否是手机号码
//     * @param str
//     * @return
//     */
//    public static Boolean checkPhone(String str){
//        if(str.matches(phoneExpr)){
//            return true;
//        }
//        return false;
//    }


    public static void main(String[] args) {
        Boolean mailResult = checkEmail("4647899@qq.com");
        System.out.println(mailResult);
        Boolean phoneResult = checkInternationalMobilePhone("17606500217");
        System.out.println(phoneResult);
    }
}
