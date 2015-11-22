import java.io.PrintWriter;
import java.util.ArrayList;


public class Algo {
	int time_slots; // time slots needed for the algorithm
	ArrayList<ArrayList<Integer>> pairs;  //all D2D pairs
	ArrayList<Integer> time_slots_needed;  //for each pair, how many time slots more are needed
	ArrayList<Integer> time_slots_given;  //for each pair, how many time slots has it been given till now
	ArrayList<Integer> last_failed_transmissions;  //for each pair, how many transmissions has it failed
	ArrayList<Device> cell; //all the devices in the region
	int discovery_message_time_slot;  //the time slot when discovery message is sent
	
	public int process_algo (PrintWriter out) {
		int time_slot = 0; // total time slots needed for all D2D discoveries
		
		//finding time slots needed to detect n D2D pairs
		while(true) {

			ArrayList<Integer> selected_pairs = select_pairs(); //select pairs
			
			if (selected_pairs.size() == 0) { //all pairs discovered or discarded or couldn't get a chance
				int check_count = discovered_discarded_pairs();
				if (check_count < Parameters.D2Dpairs)
					continue; 
				else //all pairs discovered or discarded
					break;
			}
			time_slot++;
			
			for (int i = 0; i < selected_pairs.size(); i++) {
				int selected_pair = selected_pairs.get(i);
				//now the current time slot is alloted to the selected pair. Make its next transmission
				boolean success = false;
				success = make_transmission(selected_pair, selected_pairs);
				if (success) {
					time_slots_needed.set(selected_pair, time_slots_needed.get(selected_pair) - 1);
					time_slots_given.set(selected_pair, time_slots_given.get(selected_pair) + 1);
					last_failed_transmissions.set(selected_pair, 0); //last chain of failed transmissions
				}
				else {
					last_failed_transmissions.set(selected_pair, last_failed_transmissions.get(selected_pair) + 1);
				}
			}
		}
		out.print(time_slot);
		return time_slot;
	}
	
	/*
	 * Counts the number of pairs which have either been discovered, or discarded.
	 * A pair is discarded if its last failed transmissions exceed the allowed number of 
	 * retransmissions
	 */
	public int discovered_discarded_pairs() {
			int count = 0;
			for (int i = 0 ;i < Parameters.D2Dpairs; i++) {
				int curr_time_slot = time_slots - time_slots_needed.get(i);
				if (time_slots_needed.get(i) == 0  //discovered
						|| (last_failed_transmissions.get(i) == Parameters.retransmissions 
							&& curr_time_slot == discovery_message_time_slot)  //discarded
						|| (curr_time_slot != discovery_message_time_slot
							&& last_failed_transmissions.get(i) == Parameters.retransmission_TM)  //discarded
					)
					count++;
			}
			return count;
		}
	
	public ArrayList<Integer> select_pairs() {
			ArrayList<Integer> selected_pairs = new ArrayList<>();
			for (int i = 0; i < Parameters.D2Dpairs; i++) {
				double probab = Math.random();
				int curr_time_slot = time_slots - time_slots_needed.get(i);
				/*
				 * Selecting a pair for transmission based on 3 conditions : 
				 * 1. Transmission probability 
				 * 2. The pair hasn't finished discovery yet
				 * 3. The pair hasn't exceeded its retransmission limit
				 */
				
				if (
					probab  <= Parameters.transmission_probability  //condition 1
					&& time_slots_needed.get(i) > 0  //condition 2
				)
					if ((curr_time_slot == discovery_message_time_slot && last_failed_transmissions.get(i) < Parameters.retransmissions)
							|| (curr_time_slot != discovery_message_time_slot && last_failed_transmissions.get(i) < Parameters.retransmission_TM)
					) //condition 3
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
		 *In case of unsuccessful transmission, return false
		 *This function should take care of sending the discovery message and its contents.
		***/
		return success;
	}
	
	
}
