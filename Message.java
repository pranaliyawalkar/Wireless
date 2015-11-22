import java.util.ArrayList;

public class Message {
	double SINR;
	
	//SINR between device and BS
	public void feed_sinr(
			Device d1, 
			int selected_pair,
			ArrayList<Integer> selected_pairs, //used to calculate interference
			ArrayList<ArrayList<Integer>> pairs,
			ArrayList<Device> cell,
			boolean is_transmitter_device // true if message is going from device to BS, false if from BS to device
		) {
		
		double path_loss = path_loss(d1); //path loss between d1 and BS, in dB
		double transmit_power = Parameters.transmit_power_bs; //Watt
		if (is_transmitter_device)
			transmit_power = Parameters.transmit_power_devices;
		double p_receiver = transmit_power / (Math.pow(10, path_loss/10));  //Watt
		double watt_interference = calculate_interference(d1, selected_pair, selected_pairs, pairs, cell); //Watt
		double sinr = p_receiver / (watt_interference + Parameters.noise_factor); //dB
		SINR = 10* Math.log10(sinr); //dB
	}
	
	//SINR between device and device
	public void feed_sinr(
			Device d1, //transmitter
			Device d2, //receiver
			int selected_pair,
			ArrayList<Integer> selected_pairs, //used to calculate interference
			ArrayList<ArrayList<Integer>> pairs,
			ArrayList<Device> cell
		) {
			
		double path_loss = path_loss(d1, d2); //in dB
		double transmit_power = Parameters.transmit_power_devices; //Watt
		double p_receiver = transmit_power / (Math.pow(10, path_loss/10));  //Watt
		double watt_interference = calculate_interference(d1, selected_pair, selected_pairs, pairs, cell); //Watt
		double sinr = p_receiver / (watt_interference + Parameters.noise_factor); //dB
		SINR = 10* Math.log10(sinr); //dB
	}
	
	private double calculate_interference (
			Device d1, 
			int selected_pair,
			ArrayList<Integer> selected_pairs, //used to calculate interference
			ArrayList<ArrayList<Integer>> pairs,
			ArrayList<Device> cell
	) {
		double intereference = 0;
		for (int i = 0; i < selected_pairs.size(); i++) {
			Device ue1 = cell.get(pairs.get(selected_pairs.get(i)).get(0)); //transmitter is always first element of the pair
			double path_loss_d2d = path_loss(ue1, d1); // interference caused in d1's transmission because of ue1's presence (in dB)
			if (path_loss_d2d > 0) {
				intereference += Parameters.transmit_power_devices / (Math.pow(10, path_loss_d2d/10));  //Watt
			}
		}
		return intereference; //Watt
	}

	//between device and BS
	private double path_loss(Device d1) {  
		double dist = Math.sqrt(Math.pow(d1.x, 2) + Math.pow(d1.y, 2)); //distance of base station from origin
		double mult = 1.0;
		if (dist/10 < mult)
			mult = dist/10;
		double alpha = mult * (1- Math.exp(-dist/36));
		alpha += Math.exp(-dist/36);
		double PL_LOS = (22* Math.log10(dist)) + 42;
		double PL_NLOS = (36.7* Math.log10(dist)) + 40.9;
		return (alpha * PL_LOS) + ((1-alpha)*PL_NLOS); //equation 6
	}
	
	//between 2 devices
	private double path_loss(Device d1, Device d2) { 
		double dist = Math.sqrt(Math.pow(d1.x-d2.x, 2) + Math.pow(d1.y-d2.y, 2)); //distance between d1 and d2
		double alpha = 0;
		if (dist <= 4)
			alpha = 1;
		else if (dist < 60 && dist > 4)
			alpha = Math.exp(-(dist-4)/3);
		else if (dist >= 60)
			alpha = 0;
		
		double PL_LOS = (16.9* Math.log10(dist)) + 46.8;
		double PL_NLOS = (40* Math.log10(dist)) + 49;
		return (alpha * PL_LOS) + ((1-alpha)*PL_NLOS); //equation 6
	}
}
