package com.xkcoding.orm.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xkcoding.orm.mybatis.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

public class UnclassifiedTest {

    @Test
    public void decimalFormatTest() {
        BigDecimal num = new BigDecimal(100.114514);
        // 四舍五入法取小数点后三位
        DecimalFormat df1 = new DecimalFormat("#.###");
        String s = df1.format(num);
        System.out.println(s);
        /**
         * 你还可以指定其他格式,如:
         * - ###.## - 整数部分至少 3 位,小数 2 位
         * - 0.00 - 小数点前补 0,2 位小数
         * - #,###.## - 每三位添加分隔符(逗号)
         */
        DecimalFormat df2 = new DecimalFormat("0000.000");
        System.out.println(df2.format(num));
    }

    @Test
    public void someTest() {
        Map<String, List<Map<String, Integer>>> hashMap = new HashMap<>();
        /**
         * {
         *     "roadSectionId1": {
         *         "时间段1"：count2,
         *         "时间段2"：count2,
         *         ...
         *     },
         *     "roadSectionId2": {
         *          *         "时间段1"：count2,
         *          *         "时间段2"：count2,
         *          *         ...
         *          *     }
         * }
         */
    }


    /**
     *
     * @param count
     * @return
     */
    private List<DataEntity> getDataList(int count) {
        List<DataEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(i);
            dataEntity.setName("name" + i);
            dataEntity.setNow(new Date());
            list.add(dataEntity);
        }
        return list;
    }

    @Test
    public void getIndex() {
        // 生成个长度为10的对象List
        List<DataEntity> dataList = getDataList(10);
        for (DataEntity d:dataList) {
            System.out.println(dataList.indexOf(d));
        }
    }

    @Test
    public void removeSpaceTest() {
        String s = "     a b c   d   f         e   ";
        String s1 = StringUtils.deleteWhitespace(s);
        System.out.println(s1);
    }

    @Test
    public void serializableOutputTest() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\桌面\\test.txt");
            // 该类将实现了序列化的对象序列化后写入指定地方,这样可以将对象保存到文件、发送到网络或存储在其他数据源中
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            List<DataEntity> dataList = getDataList(10);
            objectOutputStream.writeObject(dataList);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void serializableInputTest() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\桌面\\test.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<DataEntity> dataEntity = (List<DataEntity>) objectInputStream.readObject();
            dataEntity.stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileOutStreamTest() {
        try {
            long time = new Date().getTime();

            FileOutputStream fileOutputStream = new FileOutputStream("D:\\桌面\\fileOutStreamTest.txt");
            // 使用 BufferedOutputStream 包装了 FileOutputStream，以创建一个带有缓冲区的输出流。
            //BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            List<DataEntity> dataList = getDataList(100);
            dataList.stream().forEach(dataEntity -> {
                try {
                    outputStreamWriter.write(JSON.toJSONString(dataEntity));
                    outputStreamWriter.write(System.lineSeparator());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            outputStreamWriter.flush();
            outputStreamWriter.close();
            System.out.println(new Date().getTime() - time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void fileInStreamTest() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\桌面\\fileOutStreamTest.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 如果想解析为为List<指定类> 则需要使用JSON.parseArray(String text, Class clazz)方法
                DataEntity parse = JSON.parseObject(line, DataEntity.class);
                System.out.println(parse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toJsonTest() {
        String str = "{\"buyerAddress\":\"\",\"buyerBank\":\"\",\"buyerBankAccount\":\"\",\"buyerEmail\":\"13735891668@139.com\",\"buyerName\":\"龙元建设集团股份有限公司\",\"buyerTaxNum\":\"91330000704203949A\",\"buyerTel\":\"\",\"buyerType\":1,\"goodsCode\":\"3040502020200000000\",\"goodsName\":\"车辆停放服务\",\"goodsType\":1,\"invoiceFee\":1200,\"invoiceMethod\":1,\"invoiceOrderRelPo\":[{\"orderNo\":\"P3512305193776218871\",\"parkOrderId\":\"a742429529d34a479f7aafc99fc3d04d\"}],\"invoiceQrCode\":\"https://inv.jss.com.cn/fp2/v0hz0HTyOZanDlGxib9iBXuwOuWJ7G_jOHGBimpPk59oBe6HQ8D51K2hjsq42nLi3P0Hze4VdQVt23Am9ZyJMg.pdf\",\"invoiceStatus\":2,\"mchNo\":\"1\",\"msgMchSerialNo\":\"I2307140189683130\",\"plateNo\":\"浙ADE322\",\"remarks\":\"浙ADE322\",\"serialNo\":\"23071400313601104206\",\"submitTime\":1689265896000}";
        JSONObject jsonObject = JSON.parseObject(str);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void truncateTest() {
        String s = "中文字符串一二三四五六";
        String truncate = StringUtils.truncate(s, 10);
        System.out.println(truncate);
    }
}
