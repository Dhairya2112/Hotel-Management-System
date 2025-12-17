import java.io.*;
import java.util.Scanner;

class Node {
    OrderItem1 data;
    Node next;

    public Node(OrderItem1 data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void add(OrderItem1 data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data.toString());
            current = current.next;
        }
    }

    public void sort() {
        if (head == null || head.next == null) {
            return;
        }

        Node current = head;

        while (current != null) {
            Node minNode = current;
            Node nextNode = current.next;

            while (nextNode != null) {
                if (nextNode.data.getTotalPrice() < minNode.data.getTotalPrice()) {
                    minNode = nextNode;
                }
                nextNode = nextNode.next;
            }

            // Swap the data
            if (minNode != current) {
                OrderItem1 temp = current.data;
                current.data = minNode.data;
                minNode.data = temp;
            }

            current = current.next;
        }
    }

    public void sort1() {
        if (head == null || head.next == null) {
            return;
        }

        Node sorted = null;

        Node current = head;
        while (current != null) {
            Node next = current.next;

            if (sorted == null || sorted.data.getTotalPrice() >= current.data.getTotalPrice()) {
                current.next = sorted;
                sorted = current;
            } else {
                Node temp = sorted;
                while (temp.next != null && temp.next.data.getTotalPrice() < current.data.getTotalPrice()) {
                    temp = temp.next;
                }
                current.next = temp.next;
                temp.next = current;
            }

            current = next;
        }

        head = sorted;
    }

    public OrderItem1 findByID(int itemID) {
        Node current = head;
        while (current != null) {
            if (current.data.itemID == itemID) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public OrderItem1 findByName(String itemName) {
        Node current = head;
        while (current != null) {
            if (current.data.itemName.equalsIgnoreCase(itemName)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }
}

class OrderItem {
    int itemID;
    String itemName;
    private int itemPrice;
    int itemQuantity;

    public OrderItem(int itemID, String itemName, int itemPrice, int itemQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    // Public getter for itemPrice
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

class CafeOrderSystem {
    LinkedList menuList;

    public CafeOrderSystem() {
        menuList = new LinkedList();
        Menu();
    }

    public void orderFood(Scanner scn) {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Show Menu");
            System.out.println("2. Show Menu Sorted by Price");
            System.out.println("3. Search Item by Name");
            System.out.println("4. Order Food");
            System.out.println("5. Exit");

            int choice = scn.nextInt();
            scn.nextLine();

            switch (choice) {
                case 1:
                    displayMenu(false);
                    break;
                case 2:
                    displayMenu(true);
                    break;
                case 3:
                    System.out.print("Enter the item name: ");
                    String itemName = scn.nextLine();
                    searchItemByName(itemName);
                    break;
                case 4:
                    processOrder(scn);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public void Menu() {
        menuList.add(new OrderItem1(1, "Espresso", 120, 1));
        menuList.add(new OrderItem1(2, "Americano", 150, 1));
        menuList.add(new OrderItem1(3, "Latte", 180, 1));
        menuList.add(new OrderItem1(4, "Cappuccino", 200, 1));
        menuList.add(new OrderItem1(5, "Mocha", 220, 1));
        menuList.add(new OrderItem1(6, "Iced Coffee", 230, 1));
        menuList.add(new OrderItem1(7, "Cold Brew", 250, 1));
        menuList.add(new OrderItem1(8, "Frappuccino", 270, 1));
        menuList.add(new OrderItem1(9, "Macchiato", 240, 1));
        menuList.add(new OrderItem1(10, "Flat White", 260, 1));
        menuList.add(new OrderItem1(11, "Affogato", 300, 1));
        menuList.add(new OrderItem1(12, "Chai Latte", 170, 1));
        menuList.add(new OrderItem1(13, "Matcha Latte", 290, 1));
        menuList.add(new OrderItem1(14, "Hot Chocolate", 200, 1));
        menuList.add(new OrderItem1(15, "Croissant", 150, 1));
        menuList.add(new OrderItem1(16, "Bagel with Cream Cheese", 180, 1));
        menuList.add(new OrderItem1(17, "Blueberry Muffin", 120, 1));
        menuList.add(new OrderItem1(18, "Chocolate Chip Cookie", 100, 1));
        menuList.add(new OrderItem1(19, "Ham and Cheese Sandwich", 220, 1));
        menuList.add(new OrderItem1(20, "Veggie Wrap", 200, 1));
        menuList.add(new OrderItem1(21, "Caesar Salad", 270, 1));
        menuList.add(new OrderItem1(22, "Greek Salad", 250, 1));
        menuList.add(new OrderItem1(23, "Tomato Basil Soup", 180, 1));
        menuList.add(new OrderItem1(24, "Minestrone Soup", 190, 1));
        menuList.add(new OrderItem1(25, "Pasta Alfredo", 350, 1));
        menuList.add(new OrderItem1(26, "Spaghetti Bolognese", 370, 1));
        menuList.add(new OrderItem1(27, "Margherita Pizza", 450, 1));
        menuList.add(new OrderItem1(28, "Pepperoni Pizza", 480, 1));
        menuList.add(new OrderItem1(29, "BBQ Chicken Pizza", 520, 1));
        menuList.add(new OrderItem1(30, "Veggie Delight Pizza", 490, 1));
    }

    public void displayMenu(boolean sorted) {
        if (sorted) {
            menuList.sort();
        }
        System.out.println("+-------+--------------------------------------+-------+");
        System.out.println("| ItemID| Food Item                            | Price |");
        System.out.println("+-------+--------------------------------------+-------+");

        Node current = menuList.head;
        while (current != null) {
            System.out.printf("| %-5d | %-36s | %-5d |\n", current.data.itemID, current.data.itemName.trim(), current.data.getItemPrice());
            current = current.next;
        }

        System.out.println("+-------+--------------------------------------+-------+");
    }

    public void searchItemByName(String itemName) {
        Node current = menuList.head;
        boolean found = false;

        System.out.println("+---------------------------------+-------+-------+--------------+");
        System.out.println("| Food Item                       | Price | Qty   | Subtotal     |");
        System.out.println("+---------------------------------+-------+-------+--------------+");

        while (current != null) {
            if (current.data.itemName.toLowerCase().contains(itemName.toLowerCase())) {
                System.out.print(current.data.toString());
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("Item not found.");
        }

        System.out.println("+---------------------------------+-------+-------+--------------+");
    }


    public void processOrder(Scanner scn) {
        LinkedList orderList = new LinkedList();
        int totalAmount = 0;

        while (true) {
            displayMenu(false);

            System.out.print("Enter Item ID: ");
            int itemID = scn.nextInt();

            OrderItem1 menuItem = menuList.findByID(itemID);
            if (menuItem != null) {
                System.out.print("Enter Item Quantity: ");
                int itemQuantity = scn.nextInt();

                // Create order item and add to order
                OrderItem1 orderItem = new OrderItem1(itemID, menuItem.itemName, menuItem.getItemPrice(), itemQuantity);
                orderList.add(orderItem);
                totalAmount += orderItem.getTotalPrice();

                System.out.print("Order more items? [Y/N]: ");
                char ans = scn.next().toLowerCase().charAt(0);

                if (ans == 'n') {
                    displayOrderSummary(orderList, totalAmount);

                    if (processPayment(scn, totalAmount)) {
                        saveCafeOrderToFile(orderList, totalAmount);
                    } else {
                        System.out.println("Order not saved due to failed payment.");
                    }
                    break;
                }
            }
            else {
                System.out.println("Please enter a valid Food ID!");
            }
        }
    }

    public void saveCafeOrderToFile(LinkedList orderList, int totalAmount) {
        try {
            FileWriter fw = new FileWriter("cafe_order_history.txt", true); // append mode
            BufferedWriter bw = new BufferedWriter(fw);

            Node current = orderList.head;
            while (current != null) {
                bw.write("Food: " + current.data.itemName +
                        ", Price: " + current.data.getItemPrice() +
                        ", Qty: " + current.data.itemQuantity +
                        ", Subtotal: " + current.data.getTotalPrice() + "\n");
                current = current.next;
            }

            bw.write("Total Amount: Rs." + totalAmount + "\n");
            bw.write("-----\n"); // separator for next order

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving cafe order: " + e.getMessage());
        }
    }

    public void displayCafeOrderHistoryFile() {
        try {
            File file = new File("cafe_order_history.txt");
            if (!file.exists()) {
                System.out.println("No cafe order history found.");
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error reading cafe order history.");
        }
    }

    public static void displayOrderSummary(LinkedList orderList, int totalAmount) {
        System.out.println("+---------------------------------+-------+-------+--------------+");
        System.out.println("| Food Item                       | Price | Qty   | Subtotal     |");
        System.out.println("+---------------------------------+-------+-------+--------------+");

        orderList.display();

        System.out.println("+---------------------------------+-------+-------+--------------+");
        System.out.println("Total Amount To Pay Is:- Rs." + totalAmount);
    }

    public boolean processPayment(Scanner scn, int totalAmount) {
        System.out.println("Proceeding to payment...");

        // Generate 4-digit OTP
        int otp = (int) (Math.random() * 9000) + 1000;
        System.out.println("Your OTP is: " + otp);

        System.out.print("Enter the OTP to complete the payment: ");
        int enteredOtp = scn.nextInt();

        // Verify OTP
        if (enteredOtp == otp) {
            System.out.println("Payment successful! Rs." + totalAmount + " has been paid.");
            System.out.println("********** Thank You For the Visit **********");
            return true; // payment successful
        } else {
            System.out.println("Incorrect OTP! Payment failed.");
            System.out.println("Please Try Again!");
            return false; // payment failed
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        CafeOrderSystem system = new CafeOrderSystem();
        system.orderFood(scn);
    }
}