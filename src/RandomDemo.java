import java.util.Random;

public class RandomDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random r = new Random(System.currentTimeMillis());
		for(int i=0; i<6; i++) {
			System.out.println(i + " . " + r.nextInt(10));
		}
	
		for(int i=0; i<6; i++) {
			System.out.println(i + " . " + (int)(Math.random() * 1000) % 7);
		}
		
	}

}
