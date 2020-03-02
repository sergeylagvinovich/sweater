package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepos messageRepos;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model)
    {
        Iterable<Message> messages = messageRepos.findAll();
        model.put("messages", messages);
        model.put("error","Полученный список");
        return "main";
    }

    @PostMapping("/main")
    public String Add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){

        Message message = new Message(text, tag);
        messageRepos.save(message);
        Iterable<Message> messages = messageRepos.findAll();
        model.put("messages", messages);
        model.put("error","Полученный список");

        return "main";
    }

    @PostMapping("filter")
    public String Filter(@RequestParam String filter, Map<String, Object> model){

        List<Message> messages = messageRepos.findByTag(filter);
        if(filter!=null && !filter.isEmpty() && messages.size()>0) {
            model.put("messages", messages);
            model.put("error","Полученный список");
        }
        else
        {
            model.put("error","Не найдено");
        }
        return "main";
    }

    @PostMapping("all")
    public String All(Map<String, Object> model){

        Iterable<Message> messages = messageRepos.findAll();
        model.put("messages", messages);
        model.put("error","Полученный список");

        return "main";
    }


}
