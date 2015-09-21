import java.util.ArrayList;

public class Algo {
	int time_slots;
	ArrayList<ArrayList<Integer>> pairs;
	ArrayList<Integer> time_slots_needed;
	ArrayList<Integer> time_slots_given;
	ArrayList<Integer> last_failed_transmissions;
	int algo_number;
	Parameters params = new Parameters();
	
	public Algo() {
		
	}
	
	public Algo(ArrayList<ArrayList<Integer>> inputpairs) {
		pairs = inputpairs;
		for (int i = 0;i < pairs.size(); i++)  {
			time_slots_needed.add(time_slots);
			time_slots_given.add(0);
			last_failed_transmissions.add(0);
		}
	}
	
	public void process_algo () {
		int time_slot = 0; // total time slots needed for all D2D discoveries
		
		//finding time slots needed to detect n D2D pairs
		while(true) {
			
			//selecting pairs based on transmission probability in each time slot
			ArrayList<Integer> selected_pairs = select_pairs();
			if (selected_pairs.size() == 0) { //all pairs discovered or discarded or couldn't get a chance
				
				int check_count = discovered_discarded_pairs();
				if (check_count < params.D2Dpairs)
					continue; 
				else //all pairs discovered or discarded
					break;
			}
			
			for (int i = 0; i < selected_pairs.size(); i++) {
				int selected_pair = selected_pairs.get(i);
				
				//now the current time slot is alloted to the selected pair. Make its next transmission
				boolean success = false;
				success = make_transmission(selected_pair, selected_pairs);
				if (success) {
					time_slots_needed.set(selected_pair, time_slots_needed.get(selected_pair) - 1);
					time_slots_given.set(selected_pair, time_slots_given.get(selected_pair) + 1);
					last_failed_transmissions.set(selected_pair, 0);
				}
				else {
					last_failed_transmissions.set(selected_pair, last_failed_transmissions.get(selected_pair) + 1);
				}
			}
			time_slot++;
		}
	}
	
	public int discovered_discarded_pairs() {
			int count = 0;
			for (int i = 0 ;i < params.D2Dpairs; i++) {
				if (time_slots_needed.get(i) == 0 || last_failed_transmissions.get(i) == params.retransmissions)
					count++;
			}
			return count;
		}
	
	public ArrayList<Integer> select_pairs() {
			ArrayList<Integer> selected_pairs = new ArrayList<>();
			for (int i = 0;i < params.D2Dpairs; i++) {
				double probab = Math.random();
				if (
					probab  <= params.transmission_probability
					&& time_slots_needed.get(i) > 0 
					&& last_failed_transmissions.get(i) < params.retransmissions
				)
					selected_pairs.add(i);
			}
			return selected_pairs;
		}
	
	public boolean make_transmission(
			int selected_pair, 
			ArrayList<Integer> selected_pairs //used to calculate interference
		) {
		boolean success = true;
		/***
		 *Things to be done in each time slot depends on the actions given in the algo
		 *Something like switch cases, to perform different actions for different steps
		 *In case of unsuccessful transmission, return false
		 *This function should take care of sending the discovery message and its contents.
		***/
		return success;
	}
}
