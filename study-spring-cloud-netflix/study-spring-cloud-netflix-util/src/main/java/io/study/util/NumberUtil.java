package io.study.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字工具类
 * @author XieZhibing
 * @version 1.0.0
 */
public class NumberUtil implements Serializable {

    /***/
    private static final long serialVersionUID = 1L;

    /** 默认除法运算精度: 2位小数 */
    public static final int DEFAULT_SCALE = 2;

    /** 除法运算精度: 4位小数 */
    public static final int SCALE_FOUR = 4;

    /** 除法运算精度: 6位小数 */
    public static final int SCALE_SIX = 6;

    /**
     * 生成四位随机整数
     * @return
     */
    public static int random4(){
        return ((int)((Math.random()*9+1)*1000));
    }

    /**
     * 生成五位随机整数
     * @return
     */
    public static int random5(){
        return ((int)((Math.random()*9+1)*10000));
    }

    /**
     * 生成六位随机整数
     * @return
     */
    public static int random6(){
        return ((int)((Math.random()*9+1)*100000));
    }

    /**
     * 生成八位随机整数
     * @return
     */
    public static int random8(){
        return ((int)((Math.random()*9+1)*10000000));
    }

    /**
     * 加法: a + b + c ...
     * @param a
     * @param b
     * @param ds
     * @return
     */
    public static double add(double a, double b, double... ds) {
        double result = add(a, b);
        for (double item : ds) {
            result = add(result, item);
        }
        return result;
    }

    /**
     * 加法: a + b
     *
     * @param a
     * @param b
     * @return
     */
    public static double add(double a, double b) {
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return add(ba, bb).doubleValue();
    }


    /**
     * 加法: a + b
     *
     * @param a
     * @param b
     * @return
     */
    public static double add2(double a, double b) {
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return add2(ba, bb).doubleValue();
    }

    /**
     * 取相反数
     * @param a
     * @return
     */
    public static BigDecimal getOpposite(BigDecimal a) {
        return a.multiply(new BigDecimal(-1));
    }

    /**
     * 对象转为BigDecimal
     * @param obj
     * @return
     */
    public static BigDecimal getBigDecimal( Object obj ) {
        BigDecimal result = null;
        if( obj != null ) {
            if( obj instanceof BigDecimal ) {
                result = (BigDecimal) obj;
            } else if( obj instanceof String ) {
                result = new BigDecimal( (String) obj );
            } else if( obj instanceof BigInteger ) {
                result = new BigDecimal( (BigInteger) obj );
            } else if( obj instanceof Number ) {
                result = new BigDecimal( ((Number)obj).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+obj+"] from class "+obj.getClass()+" into a BigDecimal.");
            }
        }
        return result;
    }

    /**
     * 加法: a+b
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    /**
     * 加法: a+b, 保留2位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add2(BigDecimal a, BigDecimal b) {
        return add(a, b, DEFAULT_SCALE);
    }


    /**
     * 加法: a+b+c..., 保留2位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add2(BigDecimal a, BigDecimal b, BigDecimal ... ds) {
        BigDecimal result = add(a, b);
        for (BigDecimal item : ds) {
            result = add(result, item);
        }
        return result.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 加法: a+b, 保留scale位小数
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b, Integer scale) {
        return a.add(b).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法: a-b
     *@param a
     *@param b
     *@return
     */
    public static double sub(double a, double b) {
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return div(ba.subtract(bb).doubleValue(), 1);
    }

    /**
     * 减法: a-b-c ...
     * @param a
     * @param b
     * @param ds
     * @return
     */
    public static double sub(double a, double b, double... ds) {
        double result = sub(a, b);
        for (double item : ds) {
            result = sub(result, item);
        }
        return result;
    }

    /**
     * 减法
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    /**
     * 减法: a-b, 保留scale位小数
     * @param a
     * @param b
     * @param scale 保留小数位数
     * @return
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b, Integer scale) {
        return a.subtract(b).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法: a-b, 保留2位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal sub2(BigDecimal a, BigDecimal b) {
        return a.subtract(b).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法: a-b, 保留2位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal sub2(BigDecimal a, BigDecimal b, BigDecimal ... ds) {
        BigDecimal result = sub(a, b);
        for (BigDecimal item : ds) {
            result = sub(result, item);
        }
        return result.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * 描述:四舍五入 scale=0 则取整
     * @param d
     * @param scale 小数点后保留精度
     * @return
     * @return  Double
     */
    public static Double round(Double d, int scale) {
        if (d == null) {
            return 0.00d;
        }
        if (scale < 0) {
            return d;
        }
        BigDecimal ba = new BigDecimal(d);
        return ba.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 描述:四舍五入
     * @param d
     * @param scale 小数点后保留精度
     * @return
     */
    public static String roundFormat(Double d, int scale) {
        if (d == null) {
            return "0.00";
        }
        if (scale < 0) {
            return String.valueOf(d);
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(scale);
        return nf.format(d);
    }
    /**
     * 除法
     *@param a
     *@param b
     *@return
     */
    public static double div(double a, double b) {
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return div(ba, bb).doubleValue();
    }
    /**
     * 除法： a/b 保留两位小数
     *@param a
     *@param b
     *@return
     */
    public static double div2(double a, double b) {
        return div(a, b, DEFAULT_SCALE);
    }
    /**
     * 除法： a/b 保留4位小数
     *@param a
     *@param b
     *@return
     */
    public static double div4(double a, double b) {
        return div(a, b, SCALE_FOUR);
    }

    /**
     * 除法，保留scale位小数，当scale小于0的时候，保留两位
     *@param a
     *@param b
     *@param scale
     *@return
     */
    public static double div(double a, double b, int scale) {
        if (scale < 0) {
            scale = DEFAULT_SCALE;
        }
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return ba.divide(bb, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 除法: a/b
     *@param a
     *@param b
     *@return
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b, DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }
    /**
     * 除法: a/b 保留2位小数
     *@param a
     *@param b
     *@return
     */
    public static BigDecimal div2(BigDecimal a, BigDecimal b) {
        return div(a, b, DEFAULT_SCALE);
    }
    /**
     * 除法: a/b 保留4位小数
     *@param a
     *@param b
     *@return
     */
    public static BigDecimal div4(BigDecimal a, BigDecimal b) {
        return div(a, b, SCALE_FOUR);
    }

    /**
     * 除法，保留scale位小数，当scale小于0的时候，保留两位
     *@param a
     *@param b
     *@param scale
     *@return
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b, int scale) {
        if (scale < 0) {
            scale = DEFAULT_SCALE;
        }
        return a.divide(b, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 描述: 计算平仓价格所用
     * @param value
     * @return
     * @return  Double
     */
    public static Double format(Double value) {
        return ceil(value, DEFAULT_SCALE);
    }

    /**
     *
     * 描述:5位精度，向上取整
     * @param d
     * @return
     * @return  Double
     */
    public static Double ceilFive(Double d) {
        return ceil(d, 5);
    }

    /**
     * @param d
     * @param scale	小数点后保留精度
     * @return
     * @return  Double
     */
    public static Double ceil(Double d, int scale) {
        if (d == null) {
            return 0.0d;
        }
        if (scale <= 0) {
            return d;
        }
        int su = 1;
        for (int i = 0; i < scale; i++) {
            su = su * 10;
        }
        return div(Math.ceil(mul(d, su)), su, scale);
    }

    /**
     *
     * 描述:5位精度，向下取整
     * @param d
     * @return
     * @return  Double
     */
    public static Double floorFive(Double d) {
        return floor(d, 5);
    }

    /**
     * 指定精度向下取整
     * @param d
     * @param scale	小数点后保留精度
     * @return
     * @return  Double
     */
    public static Double floor(Double d, int scale) {
        if (d == null) {
            return 0.0d;
        }
        if (scale <= 0) {
            return d;
        }
        int su = 1;
        for (int i = 0; i < scale; i++) {
            su = su * 10;
        }
        Double result = div(Math.floor(mul(d, su)), su, scale);
        return result;
    }

    /**
     * 对double取整
     * @param d
     * @param upTrueDownFalse
     * <p>Example:
     * <table border>
     * <tr valign=top><th>Input</th><th>True</th><th>False</th></tr>
     * <tr align=right><td>5.5</td><td>6</td><td>5</td></tr>
     * <tr align=right><td>2.5</td><td>3</td><td>2</td></tr>
     * <tr align=right><td>1.6</td><td>2</td><td>1</td></tr>
     * <tr align=right><td>1.1</td><td>1</td><td>1</td></tr>
     * <tr align=right><td>1.0</td><td>1</td><td>1</td></tr>
     * <tr align=right><td>-1.0</td><td>-1</td><td>-1</td></tr>
     * <tr align=right><td>-1.1</td><td>-1</td><td>-2</td></tr>
     * <tr align=right><td>-1.6</td><td>-2</td><td>-2</td></tr>
     * <tr align=right><td>-2.5</td><td>-3</td><td>-3</td></tr>
     * <tr align=right><td>-5.5</td><td>-6</td><td>-6</td></tr>
     * </table>
     * @return
     */
    public static Integer doublefloor(Double d, boolean upTrueDownFalse){
        DecimalFormat df = new DecimalFormat("0");
        if(upTrueDownFalse){
            df.setRoundingMode(RoundingMode.HALF_UP);
        }else{
            df.setRoundingMode(RoundingMode.FLOOR);
        }
        return Integer.parseInt(df.format(d));
    }

    /**
     * 乘法: a * b
     *@param a
     *@param b
     *@return
     */
    public static double mul(double a, double b) {
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return ba.multiply(bb).doubleValue();
    }

    /**
     * 乘法: a * b, 保留2位小数
     *@param a
     *@param b
     *@return
     */
    public static double mul2(double a, double b) {
        return mul(a, b, DEFAULT_SCALE);
    }

    /**
     * 乘法: a * b, 保留4位小数
     *@param a
     *@param b
     *@return
     */
    public static double mul4(double a, double b) {
        return mul(a, b, SCALE_FOUR);
    }

    /**
     * 乘法: a * b, 保留scale位小数，当scale小于0的时候，保留两位
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static double mul(double a, double b, Integer scale) {
        BigDecimal ba = new BigDecimal(Double.toString(a));
        BigDecimal bb = new BigDecimal(Double.toString(b));
        return mul(ba, bb, scale).doubleValue();
    }

    /**
     * 乘法: a * b
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    /**
     * 乘法: a * b, 保留两位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul2(BigDecimal a, BigDecimal b) {
        return mul(a, b, DEFAULT_SCALE);
    }

    /**
     * 乘法: a * b, 保留4位小数
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal mul4(BigDecimal a, BigDecimal b) {
        return mul(a, b, SCALE_FOUR);
    }

    /**
     * 乘法: a*b*c..., 保留两位小数
     * @param a
     * @param b
     * @param ds
     * @return
     */
    public static BigDecimal mul2(BigDecimal a, BigDecimal b, BigDecimal ... ds) {
        BigDecimal result = mul(a, b);
        for (BigDecimal item : ds) {
            result = mul(result, item);
        }

        return result.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘法: a * b, 保留scale位小数，当scale小于0的时候，保留两位
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b, Integer scale) {
        if (scale < 0) {
            scale = DEFAULT_SCALE;
        }
        return a.multiply(b).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 比较大小
     * @param a
     * @param b
     * @return -1: a小于b; 0: a等于b; 1: a大于b
     */
    public static int compare(BigDecimal a, BigDecimal b){
        return a.compareTo(b);
    }

    public static void main(String[] args) {

        BigDecimal a = new BigDecimal(12.22);
        BigDecimal b = new BigDecimal(5.23);
        BigDecimal c = new BigDecimal(-1);
        BigDecimal result = mul2(a, b, c);
        System.out.println(result);

        System.out.println(add2(a, b, c));
    }
}
