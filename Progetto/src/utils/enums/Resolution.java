package utils.enums;

public enum Resolution {

    LOWEST("1024x576", 1024, 576),
    LOW("1280x720", 1280, 720),
    MID("1600x900", 1600, 900),
    HIGH("1920x1080", 1920, 1080);
    
    private String res;
    private double width;
    private double height;
    
    public String getRes() {
        return res;
        }
    
    public double getWidth() {
        return width;
        }
    
    public double getHeight() {
        return height;
        }
    
    private Resolution (String r, double w, double h) {
        this.res = r;
        this.width = w;
        this.height = h;
    }
}