import java.util.ArrayList;

public class Algo1 extends Algo {
	
	public Algo1(ArrayList<ArrayList<Integer>> inputpairs) {
			time_slots = 9;
			pairs = inputpairs;
			for (int i = 0;i < pairs.size(); i++)  {
				time_slots_needed.add(time_slots);
				time_slots_given.add(0);
				last_failed_transmissions.add(0);
			}
		}
	
	public boolean make_transmission(
			int selected_pair, 
			ArrayList<Integer> selected_pairs //used to calculate interference
		) {
		boolean success = true;
		int step = time_slots - time_slots_needed.get(selected_pair);
		if ( step == 0) {
			//UE1 sends TM to BS requesting connection with UE2
			
		}
		if (step == 1) {
			
		}
		if ( step == 2) {
			
		}
		if (step == 3) {
			
		}
		if ( step == 4) {
			
		}
		if (step == 5) {
			
		}
		if ( step == 6) {
			
		}
		if (step == 7) {
			
		}
		if ( step == 8) {
			
		}
		return success;
	}
}
