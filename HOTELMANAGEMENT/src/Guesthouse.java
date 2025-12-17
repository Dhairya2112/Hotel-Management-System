import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class Guesthouse {

    static Connection con;

    public static void dbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            System.out.println("Database connected successfully!");
        }
        catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    public static void dbDisconnect() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database disconnected successfully!");
            }
        }
        catch (Exception e) {
            System.out.println("Error closing database: " + e.getMessage());
        }
    }

    public static boolean isGuestCountValid(int guestCount) {
        if (guestCount <= 0) {
            System.out.println("Error: Guest count must be at least 1.");
            return false;
        } else if (guestCount > 3) {
            System.out.println("Error: Maximum 3 guests allowed per room. Please book multiple rooms.");
            return false;
        }
        return true;
    }

    public static boolean isValidAadhar(long aadhar) {
        String aadharStr = String.valueOf(aadhar);
        return aadharStr.length() == 12;
    }

    public static long getValidAadharFromUser() {
        Scanner scn = new Scanner(System.in);
        long aadhar = 0;
        while (true) {
            try {
                System.out.print("Enter Your 12-digit Aadhar Number: ");
                aadhar = scn.nextLong();
                if (isValidAadhar(aadhar)) {
                    return aadhar;
                } else {
                    System.out.println("Invalid Aadhar! Must be 12 digits. Please try again.");
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input! Enter numeric digits only.");
                scn.nextLine();
            }
        }
    }

    public static boolean isValidName(String name) {
        if (name == null) return false;
        name = name.trim();
        return name.length() >= 2;
    }
    
    public static String getValidName(String prompt) {
        Scanner scn = new Scanner(System.in);
        String name = "";
        while (true) {
            System.out.print(prompt);
            name = scn.nextLine();
            if (isValidName(name)) {
                return name.trim();
            } else {
                System.out.println("Invalid name! Must be at least 2 characters. Try again.");
            }
        }
    }

    public static void getRoomDetails() {
        try {
            String query = "SELECT r_no, r_details, r_price FROM rooms";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("+-------+-----------------------+-------+");
            System.out.println("|Room No|Details                |Price  |");
            System.out.println("+-------+-----------------------+-------+");

            while (rs.next()) {
                System.out.printf("|%-7d|%-23s|%-7d|\n",
                        rs.getInt(1), rs.getString(2), rs.getInt(3));
            }

            System.out.println("+-------+-----------------------+-------+");
        }
        catch (Exception e) {
            System.out.println("Error fetching room details: " + e.getMessage());
        }
    }

    public static void getRoomDetailsWithAvailability() {
        try {
            String query = "SELECT r_no, r_details, r_price, r_availability FROM rooms";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("+-------+-----------------------+-------+---------------+");
            System.out.println("|Room No|Details                |Price  |Availability   |");
            System.out.println("+-------+-----------------------+-------+---------------+");

            while (rs.next()) {
                System.out.printf("|%-7d|%-23s|%-7d|%-15s|\n",
                        rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            }

            System.out.println("+-------+-----------------------+-------+---------------+");
        }
        catch (Exception e) {
            System.out.println("Error fetching room availability: " + e.getMessage());
        }
    }

    public static boolean ifRoomNoIsValid(int roomNo) {
        try {
            String query = "SELECT 1 FROM rooms WHERE r_no=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomNo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {
            System.out.println("Error checking room number: " + e.getMessage());
            return false;
        }
    }

    public static boolean isRoomAvailable(int roomNo) {
        try {
            String query = "SELECT isRoomAvailable(?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean(1);
            }
        }
        catch (Exception e) {
            System.out.println("Error checking room availability: " + e.getMessage());
        }
        return false;
    }

    public static void bookRoom(int roomNo, int days, String firstName, String lastName, long aadhar,int guestCount) {
        try {
            if (!isGuestCountValid(guestCount)) {
                System.out.println("Guest count exceeds 3. Please book multiple rooms.");
                return;
            }

            String sp = "{CALL BookRoom(?, ?, ?, ?, ?, ?,?)}";
            CallableStatement cs = con.prepareCall(sp);
            cs.setInt(1, roomNo);
            cs.setInt(2, days);
            cs.setString(3, firstName);
            cs.setString(4, lastName);
            cs.setLong(5, aadhar);
            cs.setInt(6, guestCount);
            cs.setInt(7,0);
            cs.execute();
            System.out.println("Room booked successfully for " + firstName + " " + lastName);

            // Write to CheckInCustomers file
            File file = new File("CheckInCustomers.txt");
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write("===============================================");
            fw.write("Name: " + firstName + " " + lastName + "\n");
            fw.write("Room: " + roomNo + ", Aadhar: " + aadhar +", Guests: " + guestCount + "\n");
            fw.write("===============================================");
            fw.close();

        }
        catch (Exception e) {
            System.out.println("Error booking room: " + e.getMessage());
        }
    }

    public static boolean authenticateCustomer(int roomNo, long aadhar) {
        try {
            String query = "SELECT 1 FROM rooms WHERE r_no=? AND c_aadhar_no=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomNo);
            ps.setLong(2, aadhar);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {
            System.out.println("Error authenticating customer: " + e.getMessage());
            return false;
        }
    }

    public static void forgotRN(long aadhar) {
        try {
            String query = "SELECT r_no FROM rooms WHERE c_aadhar_no=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, aadhar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Your Room No is " + rs.getInt("r_no"));
            } else {
                System.out.println("No room booked with this Aadhar.");
            }
        }
        catch (Exception e) {
            System.out.println("Error fetching room number: " + e.getMessage());
        }
    }

    public static void displayFoodMenu() {
        try {
            String query = "SELECT f_id, f_name, f_price FROM food_menu";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("+-------+-----------------------+-------+");
            System.out.println("|ID     |Food Item              |Price  |");
            System.out.println("+-------+-----------------------+-------+");

            while (rs.next()) {
                System.out.printf("|%-7d|%-23s|%-7d|\n",
                        rs.getInt(1), rs.getString(2), rs.getInt(3));
            }

            System.out.println("+-------+-----------------------+-------+");
        }
        catch (Exception e) {
            System.out.println("Error displaying food menu: " + e.getMessage());
        }
    }

    public static boolean isFoodItemIDValid(int id) {
        try {
            String query = "SELECT 1 FROM food_menu WHERE f_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {
            System.out.println("Error validating food ID: " + e.getMessage());
            return false;
        }
    }

    public static void addOrderToFoodOrderTable(int id, int qty, int roomNo) {
        try {
            String query = "SELECT f_name, f_price FROM food_menu WHERE f_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String fName = rs.getString("f_name");
                int fPrice = rs.getInt("f_price");
                int total = fPrice * qty;
                String insert = "INSERT INTO food_order(fid, room_no, fname, fprice, fqty, ftotal) VALUES(?,?,?,?,?,?)";
                PreparedStatement psInsert = con.prepareStatement(insert);
                psInsert.setInt(1, id);
                psInsert.setInt(2, roomNo);
                psInsert.setString(3, fName);
                psInsert.setInt(4, fPrice);
                psInsert.setInt(5, qty);
                psInsert.setInt(6, total);
                psInsert.executeUpdate();

                System.out.printf("| %s | %d | %d | %d |\n", fName, fPrice, qty, total);

                // Write to FoodOrderHistory file
                File file = new File("FoodOrderHistory.txt");
                if (!file.exists())
                {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file, true);
                fw.write("Room: " + roomNo + ", Food: " + fName + ", Qty: " + qty + ", Total: " + total + "\n");
                fw.close();

            } else {
                System.out.println("Food ID not found!");
            }
        }
        catch (Exception e) {
            System.out.println("Error adding food order: " + e.getMessage());
        }
    }

    public static int totalPrice(int id, int qty) {
        try {
            String query = "SELECT f_price FROM food_menu WHERE f_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("f_price") * qty;
            }
        }
        catch (Exception e) {
            System.out.println("Error calculating total price: " + e.getMessage());
        }
        return 0;
    }

    public static void addTotalAmountInCustomerTable(int roomNo, long aadhar, int totalAmount) {
        try {
            String query = "UPDATE customers SET c_balance_amt = c_balance_amt + ? WHERE c_room_no=? AND c_aadhar_no=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, totalAmount);
            ps.setInt(2, roomNo);
            ps.setLong(3, aadhar);
            ps.executeUpdate();
            System.out.println("Amount added to your account!");
        }
        catch (Exception e) {
            System.out.println("Error updating customer balance: " + e.getMessage());
        }
    }

    public static void removeAllCustomerDetails(int roomNo, long aadhar) {
        try {
            // Calculate final bill
            int totalBill = roomBill(roomNo, aadhar) + foodBill(roomNo);

            // Free room
            String updateRoom = "UPDATE rooms SET r_availability='available', c_aadhar_no=NULL, c_days=NULL WHERE r_no=?";
            PreparedStatement ps1 = con.prepareStatement(updateRoom);
            ps1.setInt(1, roomNo);
            ps1.executeUpdate();

            // Delete customer
            String deleteCustomer = "DELETE FROM customers WHERE c_aadhar_no=? AND c_room_no=?";
            PreparedStatement ps2 = con.prepareStatement(deleteCustomer);
            ps2.setLong(1, aadhar);
            ps2.setInt(2, roomNo);
            ps2.executeUpdate();

            // Delete food orders
            String deleteFood = "DELETE FROM food_order WHERE room_no=?";
            PreparedStatement ps3 = con.prepareStatement(deleteFood);
            ps3.setInt(1, roomNo);
            ps3.executeUpdate();

            System.out.println("Checkout successful! Room is now available.");

            // Write to CheckOutCustomers file
            File file = new File("CheckOutCustomers.txt");
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write("Room: " + roomNo + ", Bill: " + totalBill + ", Aadhar: " + aadhar + "\n");
            fw.close();

        }
        catch (Exception e) {
            System.out.println("Error during checkout: " + e.getMessage());
        }
    }

    public static int roomBill(int roomNo, long aadhar) {
        try {
            String query = "SELECT r_price, c_days FROM rooms WHERE r_no=? AND c_aadhar_no=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomNo);
            ps.setLong(2, aadhar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("r_price") * rs.getInt("c_days");
            }
        }
        catch (Exception e) {
            System.out.println("Error calculating room bill: " + e.getMessage());
        }
        return 0;
    }

    public static int foodBill(int roomNo) {
        try {
            String query = "SELECT COALESCE(SUM(ftotal),0) FROM food_order WHERE room_no=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("Error calculating food bill: " + e.getMessage());
        }
        return 0;
    }

    public static void getFinalBill(int roomNo, long aadhar) {
        System.out.println("\n==================== FINAL BILL ====================");
        System.out.println("Room Bill: Rs." + roomBill(roomNo, aadhar));
        System.out.println("Food Bill: Rs." + foodBill(roomNo));
        System.out.println("Grand Total: Rs." + (roomBill(roomNo, aadhar) + foodBill(roomNo)));
        System.out.println("====================================================\n");
    }

    public static void displayCheckInCustomers() {
        try {
            File file = new File("CheckInCustomers.txt");
            if (!file.exists()) {
                System.out.println("No check-in customers yet.");
                return;
            }
            Scanner sc = new Scanner(file);
            System.out.println("=== CHECK-IN CUSTOMER LIST ===");
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
        }
        catch (Exception e) {
            System.out.println("Error reading check-in file: " + e.getMessage());
        }
    }

    public static void displayCheckOutCustomers() {
        try {
            File file = new File("CheckOutCustomers.txt");
            if (!file.exists()) {
                System.out.println("No check-out customers yet.");
                return;
            }
            Scanner sc = new Scanner(file);
            System.out.println("=== CHECK-OUT CUSTOMER LIST ===");
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
        }
        catch (Exception e) {
            System.out.println("Error reading check-out file: " + e.getMessage());
        }
    }

    public static void displayFoodOrderHistory() {
        try {
            File file = new File("FoodOrderHistory.txt");
            if (!file.exists()) {
                System.out.println("No food orders yet.");
                return;
            }
            Scanner sc = new Scanner(file);
            System.out.println("=== FOOD ORDER HISTORY ===");
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
        }
        catch (Exception e) {
            System.out.println("Error reading food order file: " + e.getMessage());
        }
    }
}