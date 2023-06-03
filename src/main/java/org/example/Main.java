package org.example;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.*;
import java.util.Base64;

public class Main {
    public static String QQ = "10000001";//设置QQ号
    public static String QR_Script_URL = "qr_req.py";//qr_req.py所在位置
    public static long unix_time = 0;
    public static void main(String[] args) {
        System.out.println("正在登录");
        Bot bot = BotFactory.INSTANCE.newBot(Long.parseLong(QQ), BotAuthorization.byQRCode(), configuration -> {
            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
        });
        bot.login();
        System.out.println("登录成功");
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event->{
            if (event.getMessage().contentToString().startsWith("/getqr")){
                long time = System.currentTimeMillis()/1000 - Main.unix_time;
                if (time <= 60){
                    event.getGroup().sendMessage("请等待" + (60-time) + "s后再次获取二维码喵~");
                }else{
                    event.getGroup().sendMessage("正在获取喵，请稍后");
                    unix_time = System.currentTimeMillis()/1000;

                    var Result = GetQR();
                    if (!Result.contains("FAILED"))
                        try{
                            var Data = Base64.getDecoder().decode(Result);
                            event.getGroup().sendMessage(event.getSender().uploadImage(ExternalResource.create(Data)));
                        }catch (Exception e){
                            event.getGroup().sendMessage("二维码获取失败");
                        }

                    else
                        event.getGroup().sendMessage("二维码获取失败");
                }

            }
        });
    }
    public static String GetQR(){
        String result = "";
        try {
            Process proc = Runtime.getRuntime().exec("python " + QR_Script_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}