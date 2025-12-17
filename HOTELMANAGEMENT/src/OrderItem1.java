class OrderItem1 {
    int itemID;
    String itemName;
    private int itemPrice; // Made private to enforce encapsulation
    int itemQuantity;

    public OrderItem1(int itemID, String itemName, int itemPrice, int itemQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getTotalPrice() {
        return itemPrice * itemQuantity;
    }

    public String toString() {
        return String.format("| %-21s | %-5d | %-5d | Rs.%-10d |\n", itemName, itemPrice, itemQuantity, getTotalPrice());
    }
}