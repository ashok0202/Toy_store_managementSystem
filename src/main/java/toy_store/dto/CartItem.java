package toy_store.dto;

public class CartItem {
    private Toys toy;
    private int quantity;

    public CartItem(Toys toy, int quantity) {
        this.toy = toy;
        this.quantity = quantity;
    }

    public Toys getToy() {
        return toy;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return toy.getCost() * quantity;
    }
}
