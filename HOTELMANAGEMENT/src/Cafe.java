public class Cafe {
	public static void main(String[] args) {
		try {
			Guesthouse.dbConnect();

			CafeOrderSystem cafeSystem = new CafeOrderSystem();

			UIHotel.startApp(cafeSystem);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				Guesthouse.dbDisconnect();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}