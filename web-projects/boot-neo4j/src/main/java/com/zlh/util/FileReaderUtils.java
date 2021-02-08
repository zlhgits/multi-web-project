package com.zlh.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @package com.zlh.util
 * @company: dacheng
 * @author: zlh
 * @createDate: 2020/10/22
 */
public class FileReaderUtils {
    private static final Logger logger=LoggerFactory.getLogger(FileReaderUtils.class);
    /**
     * 读取csv数据
     * @param input
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> readCsv(InputStream input) {
        List<Map<String, String>> list = new ArrayList<>(16);
        //创建CSVFormat
        CSVFormat format = CSVFormat.DEFAULT.withHeader();
        CSVParser parser = null;
        try {
            //读取CSV数据
            parser = new CSVParser(new InputStreamReader(input),format);
            for (CSVRecord csvRecord:parser){
                Map<String, String> map = csvRecord.toMap();
                list.add(map);
            }
        } catch (IOException e) {
            logger.error("file读取异常",e);
        }finally {
            if (parser != null){
                try {
                    parser.close();
                }catch (IOException e){
                }
            }
            if (input != null){
                try {
                    input.close();
                }catch (IOException e){
                }
            }
        }
        return list;
    }

    public static void downLoadFile(InputStream inputStream, HttpServletResponse response) throws Exception {
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            response.reset();
            // 配置文件下载
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode("template.csv", "UTF-8"));
            // 实现文件下载
            os = response.getOutputStream();
            bis = new BufferedInputStream(inputStream);
            int count = 0;
            while ((count = bis.read(buff)) != -1){
                os.write(buff,0,count);
                os.flush();
            }
        }catch (Exception e){
            logger.error("文件下载失败",e);
            throw new Exception("文件下载失败",e);
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("os 关闭异常",e);
                }
            }
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("bis 关闭异常",e);
                }
            }
        }
    }
}
