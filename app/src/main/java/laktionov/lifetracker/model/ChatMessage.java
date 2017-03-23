package laktionov.lifetracker.model;



public class ChatMessage {

    private String text;
    private String name;
    private String time;
    private String photoUri;

    public ChatMessage(){
    }

    public ChatMessage (String text, String name, String time, String photoUri){
        this.text = text;
        this.name = name;
        this.time = time;
        this.photoUri = photoUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
