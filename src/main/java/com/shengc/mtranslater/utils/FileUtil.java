package com.shengc.mtranslater.utils;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import java.io.*;
import java.util.ArrayList;

import static com.shengc.mtranslater.utils.AsciiUtil.dbc2sbc;
import static com.shengc.mtranslater.utils.ConsoleUtil.inputBack;
import static com.shengc.mtranslater.utils.ConsoleUtil.print;

/**
 * 文件I/O操作 util
 *
 * @author sc
 * @create 2018-08-14-11:26
 **/
public class FileUtil {
    /**
     * 读取制定位置的文件
     * @throws IOException
     * @throws OpenXML4JException
     * @throws XmlException
     */
    public static void readFile() throws IOException, OpenXML4JException, XmlException {
        /* 获取输入 word 文件路径 */
        print("请输入word文件完整路径：");
        // 获取console的返回
        String inFilePath = inputBack();
        File inFile = new File(inFilePath);
        // 判断文件有效性
        while (!inFile.exists() || inFile.isDirectory() || (!inFilePath.endsWith(".doc") && !inFilePath.endsWith(".docx") )) {
            print("请输入一个正确的word文件路径：");
            inFilePath = inputBack();
            inFile = new File(inFilePath);
        }

        /* 获取输出 markdown 文件路径 */
        print("请输入markdown文件完整路径：");
        // 获取console的返回
        String outFilePath = inputBack();
        // 判断文件有效性
        while (!outFilePath.endsWith(".md")) {
            print("请输入一个正确的markdown文件路径：");
            outFilePath = inputBack();
        }

        // 开始转换
        writeWordFileToMD(horizontalToVertical(readWordFile(inFilePath)),outFilePath);

    }

    /**
     * 获取 word文件中的数据
     * @param path
     * @return
     * @throws IOException
     * @throws OpenXML4JException
     * @throws XmlException
     */
    protected static String readWordFile(String path) throws IOException, OpenXML4JException, XmlException {
        String data = "";
        // 使用第三方插件获取 word 文件内容
        if (path.endsWith(".doc")) {
            InputStream is = new FileInputStream(new File(path));
            WordExtractor ex = new WordExtractor(is);
            data = ex.getText();
        } else if (path.endsWith("docx")) {
            OPCPackage opcPackage = POIXMLDocument.openPackage(path);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            data = extractor.getText();
        } else {
            throw new IOException("传入的word文件不正确:" + path);
        }
        return data;
    }

    /**
     * 将 word文档数据写入markdown中
     * @param finalData
     * @throws IOException
     */
    protected static void writeWordFileToMD(String finalData,String outFilePath) throws IOException {
        //使用字节流输出
        FileOutputStream fw=new FileOutputStream(outFilePath);
        //设置编码
        OutputStreamWriter osw=new OutputStreamWriter(fw,"utf-8");
        BufferedWriter bw=new BufferedWriter(osw);
        //写入
        bw.write(finalData);
        //关流
        bw.close();
        fw.close();
    }

    /**
     * 文字转换：横排显示转换为竖排显示
     * @param data
     * @return
     */
    protected static String horizontalToVertical(String data) {
        // 行划分
        String[] splitDatas = data.split("\n");
        // 计算转换后的列数
        int columns = splitDatas.length;
        // 计算转换后的行数
        int rows = 0;
        //将行字符串转成单字符后的存放列表
        ArrayList<char[]> charArr = new ArrayList();
        for (String splitData : splitDatas) {
            if (splitData.length() > rows) {
                rows = splitData.length();
            }
            //每行字符串都转为字符数组，放入列表
            charArr.add(splitData.toCharArray());
        }
        // 用于存放转换后数据的StringBuffer
        StringBuffer finalData = new StringBuffer();

        // 格式重排
        for (int i = 0; i < rows; i++) {
            for (int j = columns - 1; j >= 0; j--) {
                if (i < splitDatas[j].length()){
                    //其实sub是一个char型字符
                    char sub = charArr.get(j)[i];
                    //存放全角字符（只有全角字符才可以对其）
                    finalData.append(dbc2sbc(sub));
                }else{
                    //中文全角空格字符！用来对其列
                    finalData.append("　");
                }
                // 第一行不留白，要不右边多出一排空格
                if (j != 0){
                    // 留白贼好看,半角空格留白（每列之间制造间距，使之有段落间隔的效果）
                    finalData.append(" ");
                }
            }
            finalData.append("\n");
        }
        return finalData.toString();
    }


    public static void main(String[] args) {
        System.out.println("你".length());
        System.out.println(" ".length());
        System.out.println("\t".length());
        System.out.println("H".length());
    }
}
