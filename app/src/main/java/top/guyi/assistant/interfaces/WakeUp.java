package top.guyi.assistant.interfaces;

import android.content.Context;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 古逸 on 2017-04-26.
 */
public class WakeUp {

    private EventManager wakeup;

    private Context context;
    private Service service;

    private Map<String,String> params;

    private static WakeUp _this;

    public WakeUp(Service service,Context context) {
        this.context = context;
        this.service = service;
        wakeup = EventManagerFactory.create(this.context, "wp");
        wakeup.registerListener(new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
                if ("wp.data".equals(name)) {
                    WakeUp._this.service.wakeUp();
                    wakeup.send("wp.stop", null, null, 0, 0);
                }
            }
        });

        this.params = new HashMap<String,String>();
        params.put("kws-file", "assets:///WakeUp.bin");

        WakeUp._this = this;
    }

    public void init(){
        wakeup.send("wp.start", new JSONObject(params).toString(), null, 0, 0);
    }
}
