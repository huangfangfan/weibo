package com.example.weibo.spirder;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-03 18:03
 */
@Component("hanLP")
public class HanlpHandle {
    /**
     * 功能描述:
     * 〈去除非法字符〉
     *
     * @param str 1
     * @return : java.lang.String
     * @author : huangfangfan
     * @date : 2020/4/3 18:11
     */
    public String textDenoising(String str) {
        return str.replaceAll("[^A-Za-z0-9 \\u4e00-\\u9fa5]", "")
                .replaceAll("网页链接","").replaceAll("O绿洲","")
                .replaceAll("O微博视频","")
                .replaceAll("微博","")
                .replaceAll("微博视频","")
                .replaceAll("超话","");
    }
    /**
     * 功能描述:
     * 〈文本分词〉
     *
     * @param str 1
     * @return : java.util.List<com.hankcs.hanlp.seg.common.Term>
     * @author : huangfangfan
     * @date : 2020/4/3 18:36
     */
    public List<Term> wordSegment(String str) {
        List<Term> termList = HanLP.segment(str);
        return termList;
    }
    /**
     * 功能描述:
     * 〈去停用词〉
     *
     * @param oldString 1
     * @return : java.lang.String
     * @author : huangfangfan
     * @date : 2020/4/5 0:00
     */
    public String removeAllOfStopWords(String oldString) {
        String newString = "";
        String filePath = "D:\\IntelliJ IDEA 2019\\stopwords-master\\baidu_stopwords.txt";
        File file = new File(filePath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<String> stopWords = new ArrayList<>();
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                stopWords.add(temp.trim());
            }
           List<String> termStringList = new ArrayList<>();
            for (Term term : wordSegment(textDenoising(oldString))) {
                termStringList.add(term.word);
            }
            termStringList.removeAll(stopWords);
            for (String str : termStringList) {
                newString += str;
            }
            return newString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newString;
    }
    /**
     * 功能描述:
     * 〈关键字提取〉
     *
     * @param str 1
     * @param num 2
     * @return : java.util.List<java.lang.String>
     * @author : huangfangfan
     * @date : 2020/4/5 0:16
     */
    public List<String> keywordExtract(String str, int num) {
        str = removeAllOfStopWords(str);
        List<String> listKeyword= new ArrayList<>();
        listKeyword = HanLP.extractKeyword(str,num);
        return listKeyword;
    }
}
