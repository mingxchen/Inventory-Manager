package InventoryManager.Model;


public class Stock {

    private int id;
    private String style;
    private String color;
    private String size;
    private int quantity;

    public Stock(int i, String S, String c, String s, int q){
        this.id = i;
        this.style = S;
        this.color = c;
        this.size = s;
        this.quantity = q;
    }


    public int getId(){ return this.id; }

    public String getStyle(){
        return this.style;
    }

    public String getColor(){
        return this.color;
    }

    public String getSize(){
        return this.size;
    }

    public int getQuantity(){
        return this.quantity;
    }

}
