import java.util.Scanner;

public class UIHotel {

    private static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        Guesthouse.dbConnect();
        CafeOrderSystem cafeSystem = new CafeOrderSystem();
        startApp(cafeSystem);
        Guesthouse.dbDisconnect();
    }

    static void startApp(CafeOrderSystem cafeSystem) {
        while (true) {
            try {
                System.out.println();
                System.out.println("===============================================================");
                System.out.println("                   HOTEL MANAGEMENT SYSTEM                    ");
                System.out.println("===============================================================");
                System.out.println("1. Book a Room");
                System.out.println("2. Enter Cafe");
                System.out.println("3. Login to Your Room");
                System.out.println("4. Admin menu");
                System.out.println("5. Exit ");
                System.out.print("\nEnter your choice: ");
                int choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        bookRoomMenu();
                        break;
                    case 2:
                        cafeSystem.orderFood(scn);
                        break;
                    case 3:
                        loginMenu();
                        break;
                    case 4:
                        adminLogin(cafeSystem);
                    case 5:
                        System.out.println("Exiting. Thank you for visiting!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scn.nextLine();
            }
        }
    }

    private static void bookRoomMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("**************** ROOM BOOKING ****************");
                System.out.println("1. Show All Rooms");
                System.out.println("2. Show Rooms with Availability");
                System.out.println("3. Book a Room");
                System.out.println("4. Go Back");
                System.out.print("\nEnter your choice: ");
                int choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        Guesthouse.getRoomDetails();
                        break;
                    case 2:
                        Guesthouse.getRoomDetailsWithAvailability();
                        break;
                    case 3:
                        bookRoomProcess();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error! Please enter numeric values.");
                scn.nextLine();
            }
        }
    }

    private static void adminLogin(CafeOrderSystem cafeSystem) {
        System.out.print("Enter Admin Password: ");
        int password = scn.nextInt();

        if (password == 1111) {
            System.out.println("Admin Login Successful!");
            adminMenu(cafeSystem);
        } else {
            System.out.println("Incorrect Password! Access Denied.");
        }
    }

    private static void adminMenu(CafeOrderSystem cafeSystem) {
        while (true) {

            try {
                System.out.println();
                System.out.println("**************** ADMIN PANEL ****************");
                System.out.println("1. View Checked-in Customers");
                System.out.println("2. View Checked-out Customers");
                System.out.println("3. View Food Order History");
                System.out.println("4. View cafe Food Order History");
                System.out.println("4. Go Back");
                System.out.print("\nEnter your choice: ");
                int choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        Guesthouse.displayCheckInCustomers();
                        break;
                    case 2:
                        Guesthouse.displayCheckOutCustomers();
                        break;
                    case 3:
                        Guesthouse.displayFoodOrderHistory();
                        break;
                    case 4:
                        cafeSystem.displayCafeOrderHistoryFile();
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error! Please enter numeric values.");
                scn.nextLine();
            }
        }
    }
    private static void bookRoomProcess() {
        try {
            System.out.print("Enter Room Number to Book: ");
            int roomNo = scn.nextInt();

            if (!Guesthouse.ifRoomNoIsValid(roomNo)) {
                System.out.println("Invalid Room Number!");
                return;
            }

            if (!Guesthouse.isRoomAvailable(roomNo)) {
                System.out.println("Room " + roomNo + " is not available!");
                return;
            }

            System.out.print("Enter Number of Days to Stay: ");
            int days = scn.nextInt();

            int guestCount = 0;
            while (true) {
                System.out.print("Enter Number of Guests (1–3): ");
                guestCount = scn.nextInt();
                if (Guesthouse.isGuestCountValid(guestCount)) {
                    break;
                }
            }

            scn.nextLine();
            String firstName = "";
            String lastName = "";

            while (true) {
                System.out.print("Enter Your First Name: ");
                firstName = scn.nextLine();
                if (isNameValid(firstName)) {
                    break;
                }
                System.out.println("Invalid name! Only letters allowed.");
            }

            // Validate last name
            while (true) {
                System.out.print("Enter Your Last Name: ");
                lastName = scn.nextLine();
                if (isNameValid(lastName)){
                    break;
                }
                System.out.println("Invalid name! Only letters allowed.");
            }

            long aadhar = 0;
            while (true) {
                System.out.print("Enter Your 12-digit Aadhar Number: ");
                aadhar = scn.nextLong();
                if (Guesthouse.isValidAadhar(aadhar)){
                    break;
                }
                System.out.println("Invalid Aadhar Number! Please try again.");
            }

            System.out.print("Confirm Booking? (Y/N): ");
            char confirm = scn.next().toLowerCase().charAt(0);

            if (confirm == 'y') {
                Guesthouse.bookRoom(roomNo, days, firstName, lastName, aadhar, guestCount);
            } else {
                System.out.println("Booking Cancelled!");
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Booking failed.");
            scn.nextLine();
        }
    }


    private static boolean isNameValid(String name) {
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (!((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))) {
                return false;
            }
        }
        return true;
    }

    private static void loginMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("**************** LOGIN ****************");
                System.out.println("1. Login with Room Number & Aadhar");
                System.out.println("2. Find Room number by Aadhar no");
                System.out.println("3. Go Back");
                System.out.print("\nEnter your choice: ");
                int choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        enterRoomAndAadhar();
                        break;
                    case 2:
                        forgotRoomNumber();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scn.nextLine();
            }
        }
    }

    private static void enterRoomAndAadhar() {
        try {
            System.out.print("Enter Room Number: ");
            int roomNo = scn.nextInt();

            long aadhar = 0;
            while (true) {
                System.out.print("Enter 12-digit Aadhar Number: ");
                aadhar = scn.nextLong();
                if (Guesthouse.isValidAadhar(aadhar)) break;
                System.out.println("Invalid Aadhar Number! Please try again.");
            }

            if (Guesthouse.authenticateCustomer(roomNo, aadhar)) {
                System.out.println("Login Successful!");
                customerMenu(roomNo, aadhar);
            } else {
                System.out.println("Login Failed! Check Room Number & Aadhar.");
            }
        }
        catch (Exception e) {
            System.out.println("Invalid input!");
            scn.nextLine();
        }
    }

    private static void forgotRoomNumber() {
        try {
            long aadhar = 0;
            while (true) {
                System.out.print("Enter Your 12-digit Aadhar Number: ");
                aadhar = scn.nextLong();
                if (Guesthouse.isValidAadhar(aadhar)) break;
                System.out.println("Invalid Aadhar Number! Please try again.");
            }
            Guesthouse.forgotRN(aadhar);
        }
        catch (Exception e) {
            System.out.println("Invalid input! Cannot fetch Room Number.");
            scn.nextLine();
        }
    }

    private static void customerMenu(int roomNo, long aadhar) {
        while (true) {
            try {
                System.out.println();
                System.out.println("********** CUSTOMER MENU **********");
                System.out.println("1. Order Food");
                System.out.println("2. View Final Bill");
                System.out.println("3. Checkout");
                System.out.println("4. Exit");
                System.out.print("\nEnter your choice: ");
                int choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        orderFood(roomNo, aadhar);
                        break;
                    case 2:
                        Guesthouse.getFinalBill(roomNo, aadhar);
                        break;
                    case 3:
                        checkout(roomNo, aadhar);
                        return;
                    case 4:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input!");
                scn.nextLine();
            }
        }
    }

    private static void orderFood(int roomNo, long aadhar) {
        int totalAmount = 0;

        while (true) {
            Guesthouse.displayFoodMenu();
            try {
                System.out.print("Enter Food Item ID: ");
                int itemID = scn.nextInt();

                if (!Guesthouse.isFoodItemIDValid(itemID)) {
                    System.out.println("Invalid Food ID!");
                    continue;
                }

                System.out.print("Enter Quantity: ");
                int qty = scn.nextInt();

                Guesthouse.addOrderToFoodOrderTable(itemID, qty, roomNo);
                totalAmount += Guesthouse.totalPrice(itemID, qty);

                System.out.print("Order more items? (Y/N): ");
                char ans = scn.next().toLowerCase().charAt(0);
                if (ans == 'n') {
                    Guesthouse.addTotalAmountInCustomerTable(roomNo, aadhar, totalAmount);
                    System.out.println("Order Completed. Total Amount: Rs." + totalAmount);
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input! Try again.");
                scn.nextLine();
            }
        }
    }

    private static void checkout(int roomNo, long aadhar) {
        Guesthouse.getFinalBill(roomNo, aadhar);
        int grandTotal = Guesthouse.roomBill(roomNo, aadhar) + Guesthouse.foodBill(roomNo);

        while (true) {
            try {
                System.out.print("Enter Amount to Pay: ");
                int payment = scn.nextInt();

                if (payment == grandTotal) {
                    Guesthouse.removeAllCustomerDetails(roomNo, aadhar);
                    System.out.println("Payment Successful! Thank you for staying with us.");
                    return;
                }
                else {
                    System.out.println("Please pay the exact amount: Rs." + grandTotal);
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scn.nextLine();
            }
        }
    }
}