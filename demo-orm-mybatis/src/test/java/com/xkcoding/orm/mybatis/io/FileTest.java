package com.xkcoding.orm.mybatis.io;

import com.xkcoding.orm.mybatis.entity.DataEntity;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static com.xkcoding.orm.mybatis.utils.getDataList;

public class FileTest {

    /**
     * 字节写
     */
    @Test
    public void FileOutputStreamTest() {
        // 表示不删除原文件，而是在该文件后继续写
        try (FileOutputStream fileOutputStream = new FileOutputStream("D://桌面//fileOutputTest1.txt", true)) {
            List<DataEntity> data = getDataList(10);
            for (DataEntity d: data) {
                fileOutputStream.write(d.toString().getBytes());
                fileOutputStream.write(System.lineSeparator().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FileInputStreamTest() {
        try (FileInputStream fileInputStream = new FileInputStream("D://桌面//fileOutputTest1.txt")) {
            int b;
            StringBuffer stringBuffer = new StringBuffer();
            while ((b = fileInputStream.read()) != -1) {
                // 将读取到的字节转换为对应的 ASCII 字符，并输出到控制台
                stringBuffer.append((char) b);
            }
            System.out.println(stringBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符写
     */
    @Test
    public void FileWriteTest() {
        try(FileWriter fileWriter = new FileWriter("D://桌面//fileOutputTest2.txt");) {
            List<DataEntity> dataList = getDataList(5);
            for (DataEntity data :
                dataList) {
                String s = data.toString();
                char[] chars = s.toCharArray();
                // 以下四种执行结果是一致的
                fileWriter.write(chars,0,chars.length);
//                fileWriter.write(chars);
//                fileWriter.write(s);
//                fileWriter.write(s,0,s.length());
                fileWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FileReadTest() {
        try(FileReader fileReader = new FileReader("D://桌面//fileOutputTest2.txt")) {
            int b;
            StringBuffer stringBuffer = new StringBuffer();
            while ((b = fileReader.read()) != -1) {
                stringBuffer.append((char) b);
            }
            System.out.println(stringBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FileTest() {
        try {
            // 创建文件
            File file = new File("D://桌面//fileTest.txt");
            if (file.createNewFile()) {
                System.out.println("文件创建成功");
            } else {
                System.out.println("文件已存在");
            }
            // 删除文件
            if (file.delete()) {
                System.out.println("文件删除成功");
            } else {
                System.out.println("文件删除失败");
            }

            // 重命名文件
            File oldFile = new File("D://桌面//fileTest.txt");
            File newFile = new File("D://桌面//fileTestNew.txt");
            if (oldFile.renameTo(newFile)) {
                System.out.println("文件重命名成功");
            } else {
                System.out.println("文件重命名失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
