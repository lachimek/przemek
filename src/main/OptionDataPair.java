package main;

public class OptionDataPair {
    private String option;
    private String data;

    public void setPair(String option, String data){
        this.option = option;
        this.data = data;
    }

    public String getOption() {
        return option;
    }

    public String getData() {
        return data;
    }
}