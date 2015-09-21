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
		int step = time_slots - time_slots_needed.get(selected_pair);
		int ue1 = pairs.get(selected_pair).get(0);
		int ue2 = pairs.get(selected_pair).get(1);
		if (step == 0) {
			//UE1 sends discovery message to UE2
			DiscoveryMessage dm1 = new DiscoveryMessage();
			dm1.transmitter = ue1;
			dm1.receiver = ue2;
			dm1.feed_sinr(cell.get(ue1), cell.get(ue2), selected_pairs);
			if (dm1.SINR < Parameters.sinr_d2d)
				return false;
			return true;
		}
		if (step == 1) {
			//UE2 replies to UE1 with the received SINR value, the path gain from BS and interference
			TransmissionMessage tm1 = new TransmissionMessage();
			
		}
		if (step == 2) {
			//UE1 replies to BS with path gain, SINR and interference for both UE1 and UE2
			//how to store the values for these three steps before UE1 can send to BS??
		}
		if (step == 3) {
			//BS instructs both to initiate D2D
			TransmissionMessage tm4 = new TransmissionMessage();
			tm4.feed_sinr(cell.get(ue1), selected_pairs);
			
			TransmissionMessage tm5 = new TransmissionMessage();
			tm5.feed_sinr(cell.get(ue2), selected_pairs);
			
			if (tm4.SINR < Parameters.sinr_cell || tm5.SINR < Parameters.sinr_cell)
				return false;
			return true;
		}
		return false;
	}
}
