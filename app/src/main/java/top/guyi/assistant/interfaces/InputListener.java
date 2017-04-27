package top.guyi.assistant.interfaces;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.baidu.speech.VoiceRecognitionService;

import java.util.List;

public class InputListener implements RecognitionListener{

    public static InputListener inputListener;
    public static void init(Service service, Context context){
        inputListener = new InputListener(service,context);
        inputListener.start();
    }

    private Service service;
    private Context context;

    private SpeechRecognizer speech;
    private Intent intent;

    public InputListener(Service service, Context context) {
        this.service = service;
        this.context = context;
        intent = new Intent();
        intent.putExtra("asr-base-file-path", "/sdcard/easr/s_1");
    }

    public void start(){
        speech = SpeechRecognizer.createSpeechRecognizer(this.context, new ComponentName(this.context, VoiceRecognitionService.class));
        speech.setRecognitionListener(this);
        speech.startListening(this.intent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        if(error == 6 || error == 7){
            service.speakAgain();
        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.i("c",results.toString());
        speech.destroy();
        speech = null;
        service.textResult(((List)results.get("results_recognition")).get(0).toString());
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
