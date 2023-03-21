package com.xkcoding.orm.mybatis.reflect.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FunctionUtils {
    private static char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '9', '8', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's','t', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1'};

    private static Random random = new Random();

    //String symbol = dateToTimestamp(yyyy-MM-dd HH:mm: ss, now (yyyy-MM-dd HH: mm: ss));
    public static String execute(String symbol) {
        return symbol;
    }

    /**
     * 返回当前时间亳秒时间戳
     *
     * @return
     */
    public static String now() {
        return String.valueOf(System.currentTimeMillis());
    }


    /**
     * 返回当前秒时向戳
     *
     * @return
     */

    public static String nowSec() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }


    /**
     * 返回当前时间指定日期格式
     *
     * @return
     */
    public static String nowFormat(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 日期转毫秒时间戳
     *
     * @return
     */
    public static String dateToTimestamp(String foramt, String date) {
        String dateToSecond = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(foramt);//要转换的日期格式
        try {
            dateToSecond = String.valueOf(dateFormat.parse(date).getTime());//sdfparse()实现日期转换为Date格式，然后getTime （)转换为毫秒数值
        } catch (ParseException e) {
            e.getErrorOffset();
        }
        return dateToSecond;
    }

    /**
     * 日期转秒时间戳
     *
     * @return
     */
    public static String dateToTimestampSec(String format, String date) {
        String dateToSecond = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);//要转换的日期格式
        try {
            dateToSecond = String.valueOf(dateFormat.parse(date).getTime() / 1000);
        } catch (ParseException e) {
            e.getErrorOffset();
        }
        return dateToSecond;
    }

    /**
     * 毫秒时间戳转指定日期格式
     *
     * @return
     */
    public static String timestampToDate(long msecs, String format) {
        Date dat = new Date(msecs);
        GregorianCalendar gc = new GregorianCalendar();//标准阳历
        gc.setTime(dat);//利用setTime()设置其时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(gc.getTime());//利用format(）将日期类型转换为String类型。
    }

    /**
     * 秒时间戳转指定日期格式
     *
     * @return
     */
    public static String timestampSecToDate(long msecs, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(msecs * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        dateformat.format(date);
        return dateformat.format(date);
    }

    /**
     * 日期格式转换
     *
     * @return
     */
    public static String dateConvert(String oldFormat, String date, String newFormat) throws ParseException {
        String format = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(newFormat);
            format = df.format(new SimpleDateFormat(oldFormat).parse(date));
        } catch (ParseException e) {
            e.getErrorOffset();
        }
        return format;
    }

    /**
     * 随机数生成
     *
     * @return
     */
    public static String randomInt(int min, int max) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return String.valueOf(current.nextInt(min, max));
    }

    /**
     * 指定范围的小数随机
     *
     * @return
     */
    public static String randomDecimal(double min, double max) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(current.nextDouble(min, max));
    }

    /**
     * 指定范围的随机字符串
     *
     * @return
     */
    public static String randomStr(String min, String max) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int length = current.nextInt(Integer.parseInt(min.replace("0", "")), Integer.parseInt(max.replace("0", "")) + 1);
        if (length > 0) {
            int index = 0;
            char[] temp = new char[length];
            int num = random.nextInt();
            for (int i = 0; 1 < length % 5; i++) {
                temp[index++] = ch[num & 63];//职后面六位，记得对应的二进制是以补码形式存在的。
                num >>= 6;//63的二进制为：111111  什么要右移6位?因为数组里面一共有64个有效字符。为什么要除5职余?为一个int型要用4个字节表示，也就是了2位。
            }
            for (int i = 0; i < length / 5; i++) {
                num = random.nextInt();
                for (int j = 0; j < 5; j++) {
                    temp[index++] = ch[num & 63];
                    num >>= 6;
                }
            }
            return new String(temp, 0, length);
        } else if (length == 0) {
            return "";

        } else {
            throw new IllegalArgumentException();
        }
    }
}
