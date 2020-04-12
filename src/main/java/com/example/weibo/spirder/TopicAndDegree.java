package com.example.weibo.spirder;

import com.example.weibo.pojo.TopicAbout;
import com.example.weibo.pojo.WeiboContentInfo;
import com.example.weibo.service.ContentService;
import com.example.weibo.service.ContentServiceImpl;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-04-08 00:37
 */
@Component("topicAndDegree")
public class TopicAndDegree {
    @Autowired
    private WeiboContentInfo weiboContentInfo;
    @Autowired
    private ContentServiceImpl contentServiceImpl;
    @Autowired
    private TopicAbout topicAbout;
    @Autowired
    private HanlpHandle hanlpHandle;
    /**
     * 功能描述:
     * 〈读取关键词并聚类〉
     *
     * @param
     * @return : java.util.List<java.util.Set<java.lang.String>>
     * @author : huangfangfan
     * @date : 2020/4/7 14:52
     */
    public List<Set<String>> readerTxt() {
        List<Set<String>> list = new ArrayList<>();
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<String>();
        try {
            File file = new File("D:\\IntelliJ IDEA 2019\\stopwords-master\\output0.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                analyzer.addDocument(str,str);
            }
            list = analyzer.repeatedBisection(1.0);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 功能描述:
     * 〈对关键字进行词频统计然后排序〉
     *
     * @param
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/7 16:27
     */
    public Map<String, Integer> sortKeyWords() {
        Map<String, Integer> sorted = new HashMap<>();
        try {
            File file = new File("D:\\IntelliJ IDEA 2019\\stopwords-master\\output5.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = null;
            Map<String, Integer> map = new HashMap<>();
            while ((str = bufferedReader.readLine()) != null) {
                str = str.trim();
                String [] words = str.split(" ");
                for (String word : words) {
                    if (!map.containsKey(word)){
                        map.put(word, 1);
                    } else {
                        int n = map.get(word);
                        map.put(word, ++n);
                    }
                }
            }
            // 按值排序降序
            sorted = map
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(comparingByValue()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sorted;
    }
    /**
     * 功能描述:
     * 〈存储关键词前n位〉
     *
     * @param num 1
     * @return : java.util.List<java.lang.String>
     * @author : huangfangfan
     * @date : 2020/4/8 0:39
     */
    public List<String> keywordsTop(int num) {
        List<String> topKeywords = new ArrayList<>();
        Map<String, Integer> sorted = sortKeyWords();
            for (String keyword : sorted.keySet()) {
                if (keyword.length() >= 2 && !"视频".equals(keyword)) {
                    topKeywords.add(keyword);
                    if (topKeywords.size() == num) {
                        break;
                    }
                }
            }
        return topKeywords;
    }
    /**
     * 功能描述:
     * 〈获取包含关键词的文本〉
     *
     * @param
     * @return :
     * @author : huangfangfan
     * @date : 2020/4/8 15:56
     */
    public Map<Integer, String> includeKeywordsContentText(List<String> list) {
        Map<Integer, String> map = new HashMap<>();
        try {
            for (String keyword: list) {
                weiboContentInfo.setKeywordsList(keyword);
                List<WeiboContentInfo> weiboContentInfoList = contentServiceImpl.queryByKeyword(weiboContentInfo);
                for (WeiboContentInfo weiboContentInfo : weiboContentInfoList) {
                    map.put(weiboContentInfo.getId(),weiboContentInfo.getContentText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 功能描述:
     * 〈将包含关键词的文本聚类〉
     *
     * @param
     * @return : void
     * @author : huangfangfan
     * @date : 2020/4/8 17:18
     */
    public void textCluster() {
        List<Set<String>> listCluster = new ArrayList<>();
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();
        //  关键字list
        List<String> list = keywordsTop(10);
        //  包含关键字的文本和id  Map
        Map<Integer, String> map = includeKeywordsContentText(list);
        for (Map.Entry<Integer,String> a : map.entrySet()) {
            analyzer.addDocument(String.valueOf(a.getKey()),a.getValue());
        }
        // 自动判断聚类数量k
        listCluster = analyzer.repeatedBisection(1.0);
        File file = new File("D:\\IntelliJ IDEA 2019\\stopwords-master\\textCluster"  + ".txt");
       try {
           BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
           if (file.exists()) {
               System.out.println("文件存在");
           } else {
               System.out.println("文件不存在");
               file.createNewFile();
           }
           for (Set<String> set : listCluster) {
               for (String id : set) {
                   bufferedWriter.write(id + ":" + map.get(Integer.parseInt(id)));
                   bufferedWriter.write("\r\n");
               }
               bufferedWriter.write("$$$$");
               bufferedWriter.write("\r\n");
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
   /**
    * 功能描述:
    * 〈计算热度的公式〉
    *
    * @param id 1
    * @return : double
    * @author : huangfangfan
    * @date : 2020/4/9 0:27
    */
    public double calculateDegree(int id) throws Exception {
        double num = 0;
        DecimalFormat df = new DecimalFormat("#.0000");

            topicAbout.setId(id);
            topicAbout = contentServiceImpl.queryTopicAbout(topicAbout);
            num = Math.log(topicAbout.getFansNumber()) + Math.sqrt(topicAbout.getForwardNumber()) + topicAbout.getCommentNumber();
        return Double.parseDouble(df.format(num));
    }

    public Map<String ,Double> hotDegree() throws Exception {
        //  存储聚类后的文本和id
        List<Set<String>> listCluster = new ArrayList<>();
        //  聚类中用到
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();
        //  关键字list
        List<String> list = keywordsTop(8);
        //  包含关键字的文本和id  Map
        Map<Integer, String> map = includeKeywordsContentText(list);

        for (Map.Entry<Integer,String> a : map.entrySet()) {
            analyzer.addDocument(String.valueOf(a.getKey()),a.getValue());
        }
        // 自动判断聚类数量k
        listCluster = analyzer.repeatedBisection(1.0);
        //  存储热点和话题的Map
        Map<String ,Double> hotDegreeMap = new HashMap<>();
        StringBuilder text;
        double hotReslut;
        for (Set<String> set : listCluster) {
            if (set.size() > 30){
                continue;
            }
            text = new StringBuilder();
            hotReslut = 0;
            for (String id : set) {
                System.out.println(id);
                text.append(hanlpHandle.textDenoising(map.get(Integer.parseInt(id))));
                hotReslut += calculateDegree(Integer.parseInt(id));
            }
            hotDegreeMap.put(keywordsListToString(text.toString()),hotReslut);
            System.out.println("map的大小" + hotDegreeMap.size());
            System.out.println("---------------------");
        }
        System.out.println(hotDegreeMap);
        System.out.println("---------------");

        return hotDegreeMap;
    }
    /**
     * 功能描述:
     * 〈将字符串提取关键词后在转为字符串〉
     *
     * @param text 1
     * @return : java.lang.String
     * @author : huangfangfan
     * @date : 2020/4/9 0:46
     */
    public String keywordsListToString(String text) {
        List<String> keywords = HanLP.extractKeyword(text, 5);
        StringBuilder reslut = new StringBuilder();
        for (String string : keywords) {
            reslut.append(string).append(" ");
        }
        return reslut.toString();
    }
    public Map<String, Double> sortedHotDegree(Map<String, Double> map) {
        Map<String, Double> sorted = new HashMap<>();
        try {
            sorted = map
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(comparingByValue()))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sorted;
    }
}
