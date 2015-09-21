import java.util.ArrayList;

public class Algo2 extends Algo {

	public Algo2(ArrayList<ArrayList<Integer>> inputpairs, ArrayList<Device> input_cell) {
			time_slots = 5;
			pairs = inputpairs;
			cell = input_cell;
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
		
		return success;
	}
}
