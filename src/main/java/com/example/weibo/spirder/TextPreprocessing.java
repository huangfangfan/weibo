package com.example.weibo.spirder;

import com.example.weibo.pojo.WeiboContentInfo;
import com.example.weibo.service.ContentServiceImpl;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-05 16:20
 */
@Component("textPreprocessing")
public class TextPreprocessing {
    @Autowired
    private HanlpHandle hanlpHandle;
    @Autowired
    private ContentServiceImpl contentServiceImpl;
    @Autowired
    private WeiboContentInfo weiboContentInfo;
    /**
     * 功能描述:
     * 〈将关键词写入txt文本〉
     *
     * @param
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/5 21:54
     */
    public void wirteKeyWordsToText() {
        List<String> listKeyword= new ArrayList<>();
        int keywordsLength = 5;
        int pageNum = 1;
        int pageSize = 200;
        int circleTime = 0;
        int txtNum = 1;
        try {
            int countLineOfContentView = contentServiceImpl.queryCountIdFromView();
            if (countLineOfContentView%pageSize == 0) {
                circleTime = countLineOfContentView/pageSize;
            } else {
                circleTime = countLineOfContentView/pageSize + 1;
            }
            for (int i = 1;i <= circleTime; i++) {
                pageNum = i;
                txtNum = txtFileNum(i);
                File file = new File("D:\\IntelliJ IDEA 2019\\stopwords-master\\output" + txtNum + ".txt");
                if (file.exists()) {
                    System.out.println("文件存在");
                } else {
                    System.out.println("文件不存在");
                    file.createNewFile();
                }
                Page<WeiboContentInfo> contentInfos = contentServiceImpl.queryUserText(pageNum,pageSize);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
                for (WeiboContentInfo contentInfo : contentInfos) {
                    String str = contentInfo.getContentText();
                    keywordsLength = caculateKeywordsLength(str);
                    listKeyword = hanlpHandle.keywordExtract(str, keywordsLength);
                    String keywordsStr = "";
                    int id = contentInfo.getId();
                    for (String keyWord:listKeyword) {
                        bufferedWriter.write(keyWord + " ");
                        keywordsStr += keyWord + " ";
                    }
                    bufferedWriter.write("\r\n");
                    keywordsIntoContentView(id,keywordsStr);
                }
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能描述:
     * 〈根据文本长度不同，确定应该返回的关键词字数〉
     *
     * @param str 1
     * @return : int
     * @author : huangfangfan
     * @date : 2020/4/5 22:02
     */
    public int caculateKeywordsLength(String str) {
        int keywordsLength = 0;
        if (str.length() <= 150) {
            keywordsLength = 5;
        } else {
            keywordsLength = 8;
        }
        return keywordsLength;
    }
    /**
     * 功能描述:
     * 〈将关键字赋值，并调用insertKeywords方法更新视图〉
     *
     * @param id 1
     * @param keywords 2
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/5 22:52
     */
    public void keywordsIntoContentView(int id, String keywords) {
        weiboContentInfo.setKeywordsList(keywords);
        weiboContentInfo.setId(id);
        try {
            contentServiceImpl.insertKeywords(weiboContentInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能描述:
     * 〈文件编号产生〉
     *
     * @param pageNum 1
     * @return : int
     * @author : huangfangfan
     * @date : 2020/4/6 13:16
     */
    public int txtFileNum(int pageNum) {
        int txtNum = 0;
        txtNum = pageNum/50;
        return txtNum;
    }
}
