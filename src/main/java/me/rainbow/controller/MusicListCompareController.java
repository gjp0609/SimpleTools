package me.rainbow.controller;

import com.alibaba.fastjson.JSON;
import me.rainbow.entity.Music;
import me.rainbow.service.MusicService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guojinpeng
 * @date 18.1.8 09:34
 */
@Controller
@RequestMapping("/music")
public class MusicListCompareController extends BaseController {
    private final MusicService service;

    @Autowired
    public MusicListCompareController(MusicService service) {
        this.service = service;
    }

    @RequestMapping(params = "index")
    public String index() {
        log.info(getUserIp() + "访问index");
        return "index";
    }

    @RequestMapping(params = "compare", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> compare(HttpServletRequest request) {
        String l1 = request.getParameter("l1");
        String l2 = request.getParameter("l2");
        log.info("{}访问，list1：{}，list2：{}", getUserIp(), l1, l2);
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        try {
            map1 = getList(getId(l1));
            map2 = getList(getId(l2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size1 = map1.size();
        int size2 = map2.size();
        if (size1 >= size2) {
            Map<String, String> temp = map2;
            map2 = map1;
            map1 = temp;
        }
        int count = 0;
        HashMap<String, String> lists = new HashMap<>();
        for (Map.Entry<String, String> song : map1.entrySet()) {
            if (map2.containsKey(song.getKey())) { // 相同
                lists.put(song.getKey(), song.getValue());
                count++;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", count);
        map.put("map", lists);
        return map;
    }


    private String getId(String url) {
        // PC https://music.163.com/playlist?id=749929549&userid=29295269
        String number = url;
        if (StringUtils.isNotBlank(url)) {
            String pc = "/playlist?id=";
            String android = "/playlist/";
            if (url.contains(pc)) {
                number = getNumber(url, pc);
            }
            if (url.contains(android)) {
                number = getNumber(url, android);
            }
        }
        return number;
    }

    private String getNumber(String url, String type) {
        char[] chars = url.toCharArray();
        StringBuilder s = new StringBuilder();
        int index = url.indexOf(type) + type.length();
        for (int i = index; i < chars.length; i++) {
            char c = chars[i];
            if (c >= '0' & c <= '9') s.append(c);
            else break;
        }
        return s.toString();
    }

    private Map<String, String> getList(String listId) throws IOException {
        Music music = service.getMusic(listId);
        if (music != null) {
            return music.getSongs();
        } else {
            String url = "http://music.163.com/playlist?id=" + listId;
            HashMap<String, String> map = new HashMap<>();
            Elements elements = Jsoup.connect(url)
                    .header("Referer", "http://music.163.com/")
                    .header("Host", "music.163.com")
                    .get().select("ul[class=f-hide] a");
            for (Element element : elements) {
                String href = element.attr("href");
                String id = href.substring(href.lastIndexOf("=") + 1);
                map.put(id, element.html());
            }
            music = new Music();
            music.setId(listId);
            music.setValue(JSON.toJSONString(map));
            service.saveMusic(music);
            return map;
        }
//        Jsoup.connect(url)
//                .header("Referer", "http://music.163.com/")
//                .header("Host", "music.163.com").get().select("ul[class=f-hide] a")
//                .stream().map(w -> w.text() + "-->" + w.attr("href"))
//                .forEach(System.out::println);
    }
}
