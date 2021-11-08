package utils;


public interface OnTrafficTouchListener {

    void onItemClickRed(String data);
    void onItemClickYellow(String data);
    void onItemClickGreen(String data);
    void onFinish();
}