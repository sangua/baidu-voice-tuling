package top.guyi.assistant.interfaces;

/**
 * Created by 古逸 on 2017-04-26.
 */
public interface Service {

    void textResult(String text);

    void playFinsh();

    void hide();

    void speakAgain();

    void wakeUp();

    boolean isRestart();

    void setRestart(boolean boo);

}
