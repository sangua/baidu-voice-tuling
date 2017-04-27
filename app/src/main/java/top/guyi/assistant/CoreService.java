package top.guyi.assistant;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import top.guyi.assistant.interfaces.InputListener;
import top.guyi.assistant.interfaces.OutputListener;
import top.guyi.assistant.interfaces.WakeUp;

public class CoreService extends Service implements top.guyi.assistant.interfaces.Service{

    private OutputListener output;
    private WakeUp wakeUp;
    private static Button btn;
    private String when_text;

    private int hideCount = 0;
    private boolean restart = true;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                InputListener.init(CoreService.this, CoreService.this);
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.obtainMessage(0).sendToTarget();
        }
    };

    private Runnable requestTuling = new Runnable() {
        @Override
        public void run() {
            try {
                JSONObject json = TulingRequest.api(when_text);
                String result = null;
                result = json.getString("text");
                output.speak(result);
                if(json.has("url")){
                    notification(json.getString("url"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


    @Override
    public void onCreate() {
        output = new OutputListener(this,CoreService.this);
        wakeUp = new WakeUp(this,CoreService.this);
        wakeUp.init();
        notificationTitle("请说\"你好小逸\"我将为您服务");
    }

    private void notification(String url){
        Notification notification = new Notification(R.drawable.ic_launcher,
                when_text, System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        notification.setLatestEventInfo(this, "小逸助手",when_text,
                pendingIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        startForeground(1, notification);
    }

    private void notificationTitle(String title){
        Notification notification = new Notification(R.drawable.ic_launcher,
                title, System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(this, "小逸助手", title,
                pendingIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        startForeground(1, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void textResult(String text) {
        if(text.contains("不说了") || text.contains("再见") || text.contains("拜拜")){
            this.hide();
            return;
        }

        if(text.contains("好的") && hideCount == 2){
            this.hide();
            return;
        }
        this.when_text = text;
        this.hideCount = 0;
        new Thread(requestTuling).start();
    }

    @Override
    public void playFinsh() {
        if(this.isRestart()){
            new Thread(runnable).start();
        }
    }

    @Override
    public void hide() {
        this.setRestart(false);
        output.speak("再见");
        this.hideCount = 0;
        wakeUp.init();
        notificationTitle("请说\"你好小逸\"我将为您服务");
    }

    @Override
    public void speakAgain() {
        if(this.hideCount >= 2){
            this.hide();
        }else{
            hideCount++;
            if(hideCount == 1){
                output.speak("你有在说话吗");
            }else if(hideCount == 2){
                output.speak("再不说话,我就走了");
            }
        }
    }

    @Override
    public void wakeUp() {
        this.setRestart(true);
        output.speak("干嘛");
        notificationTitle("小逸正在为您服务");
    }

    @Override
    public boolean isRestart() {
        return this.restart;
    }

    @Override
    public void setRestart(boolean boo) {
        this.restart = boo;
    }
}
