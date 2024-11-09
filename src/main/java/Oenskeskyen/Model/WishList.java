package Oenskeskyen.Model;

import java.util.ArrayList;
import java.util.List;

public class WishList {

    private String name;
    private String description;
    private List<Wish> wishList = new ArrayList<>();

    public WishList(){
    }

    public WishList(String name, String description){
        this.name = name;
        this.description = description;
    }

    //************************* Setter ang getters *******************************//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }




}
