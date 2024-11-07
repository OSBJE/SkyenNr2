package Oenskeskyen.Model;

public class Wish {

    private String name;
    private double price;
    private String urlLink;
    private int wishId;


    //************************* Konstructors *******************************//

    public Wish(String name){
        this.name = name;
    }

    public Wish(String name, double price){
        this.name = name;
        this.price = price;
    }

    public Wish(String name, double price, String urlLink, int wishId){
        this.name = name;
        this.price = price;
        this.urlLink = urlLink;
        this.wishId = wishId;
    }

    //************************* Setter ang getters *******************************//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }
}

