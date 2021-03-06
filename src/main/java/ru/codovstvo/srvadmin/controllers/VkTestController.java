package ru.codovstvo.srvadmin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

import ru.codovstvo.srvadmin.dto.*;

@RestController
@RequestMapping(value = "back/testpay")
public class VkTestController {

    @PostMapping
    public Object VkNotification(@RequestParam String notification_type,
                                    @RequestParam String app_id,
                                    @RequestParam String user_id,
                                    @RequestParam(name = "order_id", required=false) String order_id,
                                    @RequestParam(name = "item", required=false) String item
                                    ) {
        if (notification_type.equals("get_item_test")) {
            System.out.println("Обработчик события: " + notification_type);
            if (item.equals("item1")) {
                ResponseTransfer responseTransfer = new ResponseTransfer();
                Map response = new HashMap<>();
                response.put("title", "item1");
                response.put("price", 10);
                responseTransfer.setResponse(response);
                return responseTransfer;
            } else {
                System.out.println("Товара не существует: " + item);
                ErrorTransfer errorTransfer = new ErrorTransfer();
                Map response = new HashMap<>();
                response.put("error_code", "20");
                response.put("error_msg", "Товар " + item + " не существует");
                response.put("critical", true);
                errorTransfer.setError(response);
                return errorTransfer;
            }

        } else if (notification_type.equals("order_status_change_test")) {
            System.out.println("Обработчик события: " + notification_type);
            ResponseTransfer responseTransfer = new ResponseTransfer();
            Map response = new HashMap<>();
            response.put("order_id", order_id);
            response.put("app_order_id", Math.random() * 1000);
            responseTransfer.setResponse(response);
            return responseTransfer;
        }

        System.out.println("Событие не существует: " + notification_type);
        ErrorTransfer errorTransfer = new ErrorTransfer();
        Map response = new HashMap<>();
        response.put("error_code", "1");
        response.put("error_msg", "Событие " + notification_type + " не обрабатывается сервером");
        response.put("critical", true);
        errorTransfer.setError(response);
        return errorTransfer;
    }

}