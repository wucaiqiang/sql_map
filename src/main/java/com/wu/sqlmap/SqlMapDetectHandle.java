package com.wu.sqlmap;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SqlMapDetectHandle {

    /**
     * sqlmap安装目录
     */
    private String sqlmapHome = "/soft/sqlmap/sqlmap.py";

    private String userHome = System.getProperty("user.home");//用户目录

    private String logDirPath = userHome + File.separator + "sqlmap" + File.separator + "log";
    private String reqfilePath = userHome + File.separator + "sqlmap" + File.separator + "reqfile";
    private String logFilePath = logDirPath + File.separator + "report";//执行日记记录
    private String veryGoodPath = logDirPath + File.separator + "verygood";//存在漏洞的文件

    private FileWriter fileWriter = null;
    private FileWriter goodFileWriter = null;

    public void execute(String sazFilePath) throws Exception {
//        String filePath = "/Users/caiqiang.wu/Desktop/sqlmap/wucq.saz";


        File logDir = new File(logDirPath);
        if (!logDir.exists()) {
            logDir.mkdir();
        }

        File reqfileDir = new File(reqfilePath);
        if (!reqfileDir.exists()) {
            reqfileDir.mkdir();
        }


        File logFile = new File(logFilePath);
        if (logFile.exists()) {
            logFile.delete();
        }

        File veryGoodFile = new File(veryGoodPath);
        if (veryGoodFile.exists()) {
            veryGoodFile.delete();
        }

        try {
            fileWriter = new FileWriter(logFilePath, true);
            goodFileWriter = new FileWriter(veryGoodPath, true);
            goodFileWriter.write("===============检测到有系统漏洞的文件===============\n");

            unzip(sazFilePath);

        } finally {
            fileWriter.close();
            goodFileWriter.close();
        }

    }

    public void unzip(String zipFile) throws IOException {
        // 要解压文件路径
        File file = new File(zipFile);
        if (!file.exists() || !file.isFile()) {
            return;
        }

        String reqfile = reqfilePath + File.separator + file.getName().substring(0, file.getName().lastIndexOf("."));

        File folder = new File(reqfile);
        if (folder.exists()) {
            folder.delete();
        }
        folder.mkdir();


        String unzipCmd = String.format("unzip -o %s -d %s", zipFile, reqfile);

        Runtime.getRuntime().exec(unzipCmd);

        // 缓冲区大小\
        int buffersize = 1024;
        int count = 0;
        // 缓冲区
        byte[] buffer = new byte[buffersize];
        try {
            // Zip文件
            ZipFile zfile = new ZipFile(file);
            // 获取Zip包里的所有元素
            Enumeration<ZipEntry> zips = (Enumeration<ZipEntry>) zfile.entries();
            // 遍历Zip包里的所有元素
            while (zips.hasMoreElements()) {
                // 获取文件
                ZipEntry entry = zips.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                // 解压后文件的全路径
                // 文件名
                String name = entry.getName();
                if (!name.contains("_c.txt")) {
                    continue;
                }
                String filePath = reqfile + File.separator + name;
                buildDetect(filePath);
            }
            // 关闭文件
            zfile.close();
        } catch (Exception e) {

        }
    }

    private void buildDetect(String filePath) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("================开始检测文件：%s================", filePath)).append("\n");

        String cmd = String.format("%s -r  %s --batch ", sqlmapHome, filePath);

        Process process = Runtime.getRuntime().exec(cmd);

        InputStream inputStream = process.getInputStream();

        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";

        boolean isExistInject = false;

        while ((line = input.readLine()) != null) {
            if (StringUtils.isNotBlank(line) && line.contains("following injection point(s)")) {
                //存在漏洞
                isExistInject = true;
            }
            stringBuilder.append(line).append("\n");
        }
        input.close();

        stringBuilder.append(String.format("===================文件检测完成：%s===================", filePath)).append("\n");

        System.out.println(stringBuilder.toString());

        fileWriter.write(stringBuilder.toString());

        if (isExistInject) {
            goodFileWriter.write(filePath + "\n");
        }
    }

}

