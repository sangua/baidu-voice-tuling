package top.guyi.assistant.interfaces;

import android.content.Context;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * Created by 古逸 on 2017-04-26.
 */
public class OutputListener implements SpeechSynthesizerListener {

    private Service service;

    private SpeechSynthesizer speech;

    private boolean play_run;

    private static OutputListener _this;

    public OutputListener(Service service,Context context) {
        this.service = service;
        speech = SpeechSynthesizer.getInstance();
        speech.setContext(context);
        speech.setSpeechSynthesizerListener(this);
        speech.setApiKey("FmFKh3W7RGP7sfj1Gqp3Zs4G", "e56232da55efccd3daaaaa592a174e05");
        speech.setAppId("9564951");
        speech.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4");
        speech.setParam(SpeechSynthesizer.PARAM_SPEED,"4");
        AuthInfo authInfo = speech.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            speech.initTts(TtsMode.ONLINE);
        } else {
            Log.i("c", "授权失败");
        }
        OutputListener._this = this;
    }

    public void speak(String text){
        this.speech.speak(text);
    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {
        _this.service.playFinsh();
    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }
}
