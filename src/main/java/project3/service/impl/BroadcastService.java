package project3.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    public void broadcastUpdate(String message) {
//        messagingTemplate.convertAndSend("/topic/broadcast", message); // 發送至 WebSocket 主題
//        System.out.println("Broadcasted via WebSocket: " + message);
//    }
//
//    public static String suffixGenerator(String type) {
//        if (type.equals("gameUpdate")) {
//            int r = (int) (Math.random() * 10);
//            switch (r) {
//                case 0:
//                    return " 趕緊來試試!";
//                case 1:
//                    return " 趕緊開個房間來體驗看看~";
//                case 2:
//                    return " 喔是喔真的假的55555";
//                case 3:
//                    return " 相關資訊已經更新 去遊戲列表看看吧";
//                default:
//                    return " 太棒了!☆*: .｡. o(≧▽≦)o .｡.:*☆";
//            }
//        } else {
//            int r2 = (int) (Math.random() * 20);
//            switch (r2) {
//                case 0:
//                    return " 我們希望你能帶點披薩來!";
//                case 1:
//                    return " 歡迎歡迎!!!!";
//                case 2:
//                    return " 喔是喔真的假的55555";
//                case 3:
//                    return " 我們懷念他!";
//                default:
//                    return "太棒了!☆*: .｡. o(≧▽≦)o .｡.:*☆";
//            }
//        }
//    }
}
