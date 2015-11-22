import java.util.ArrayList;

public class Algo1 extends Algo {
	
	public Algo1(ArrayList<ArrayList<Integer>> inputpairs, ArrayList<Device> input_cell) {
			time_slots = 9;
			discovery_message_time_slot = 3;
			pairs = inputpairs;
			cell = input_cell;
			time_slots_given = new ArrayList<Integer>();
			time_slots_needed = new ArrayList<Integer>();
			last_failed_transmissions = new ArrayList<Integer>();
			for (int i = 0;i < pairs.size(); i++)  {
				time_slots_needed.add(time_slots);  //initialize
				time_slots_given.add(0);
				last_failed_transmissions.add(0);
			}
		}
	
	public boolean make_transmission(
		int selected_pair, 
		ArrayList<Integer> selected_pairs //used to calculate interference
	) {
		int step = time_slots - time_slots_needed.get(selected_pair);  //current step
		
		int ue1 = pairs.get(selected_pair).get(0); //sender
		int ue2 = pairs.get(selected_pair).get(1); //receiver
		
		if ( step == 0) {
			//UE1 sends TM to BS requesting connection with UE2
			TransmissionMessage tm1 = new TransmissionMessage();
			tm1.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, true);
			if (tm1.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 1) {
			// BS tells UE2 to be ready to receive DM
			TransmissionMessage tm2 = new TransmissionMessage();
			tm2.feed_sinr(cell.get(ue2), selected_pair, selected_pairs, pairs, cell, false);
			if (tm2.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if ( step == 2) {
			//BS instructs UE1 to send Discovery Message to UE2
			TransmissionMessage tm3 = new TransmissionMessage();
			tm3.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, false);
			if (tm3.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 3) {
			//UE1 sends Discovery Message to UE2
			DiscoveryMessage dm1 = new DiscoveryMessage(ue1, ue2);
			dm1.feed_sinr(cell.get(ue1),  cell.get(ue2), selected_pair, selected_pairs, pairs, cell);
			if (dm1.SINR < Parameters.sinr_d2d)
				return false;  //failure
			return true;  //success
		}
		
		if ( step == 4) {
			//UE2 sends measurement results to BS
			TransmissionMessage tm4 = new TransmissionMessage();
			tm4.feed_sinr(cell.get(ue2), selected_pair, selected_pairs, pairs, cell, true);
			if (tm4.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 5) {
			//BS tells both UE1 and UE2 to listen for interference
			TransmissionMessage tm5 = new TransmissionMessage();
			tm5.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, false);
			
			TransmissionMessage tm6 = new TransmissionMessage();
			tm6.feed_sinr(cell.get(ue2), selected_pair, selected_pairs, pairs, cell, false);
			
			if (tm5.SINR < Parameters.sinr_cell)
				return false;  //failure
			if (tm6.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if ( step == 6) {
			//UE1 sends interference measurement results to BS
			TransmissionMessage tm7 = new TransmissionMessage();
			tm7.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, true);
			
			if (tm7.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		
		if (step == 7) {
			//UE2 sends interference measurement results to BS
			TransmissionMessage tm8 = new TransmissionMessage();
			tm8.feed_sinr(cell.get(ue2), selected_pair, selected_pairs, pairs, cell, true);
			
			if (tm8.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
			
		}
		
		if ( step == 8) {
			//BS sends message to UE1 and UE2 to start their communication
			TransmissionMessage tm9 = new TransmissionMessage();
			tm9.feed_sinr(cell.get(ue1), selected_pair, selected_pairs, pairs, cell, false);
			
			TransmissionMessage tm10 = new TransmissionMessage();
			tm10.feed_sinr(cell.get(ue2), selected_pair, selected_pairs, pairs, cell, false);
			
			if (tm9.SINR < Parameters.sinr_cell)
				return false;  //failure
			if (tm10.SINR < Parameters.sinr_cell)
				return false;  //failure
			return true;  //success
		}
		return false;  //failure
	}
	
}
