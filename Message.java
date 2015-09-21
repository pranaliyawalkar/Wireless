import java.util.ArrayList;

public class Message {
	double SINR;
	
	//SINR between device and BS
	public void feed_sinr(
			Device d1, 
			ArrayList<Integer> selected_pairs, //used to calculate interference
			ArrayList<ArrayList<Integer>> pairs,
			ArrayList<Device> cell,
			boolean is_transmitter_device // true if message is going from device to BS, false if from BS to device
		) {
		
		double path_loss = path_loss(d1); //path loss between d1 and BS, in DB
		double transmit_power = Parameters.transmit_power_bs; //Watt
		if (is_transmitter_device)
			transmit_power = Parameters.transmit_power_devices;
		double p_receiver = transmit_power / (Math.pow(10, path_loss/10));  //Watt
		double watt_interference = calculate_interference(d1, selected_pairs, pairs, cell); //Watt
		double sinr = p_receiver / (watt_interference + Parameters.noise_factor); //DB
		SINR = 10* Math.log10(sinr);
	}
	
	//sinr between device and device
	public void feed_sinr(
			Device d1, //transmitter
			Device d2, //receiver
			ArrayList<Integer> selected_pairs, //used to calculate interference
			ArrayList<ArrayList<Integer>> pairs,
			ArrayList<Device> cell
		) {
			
		double path_loss = path_loss(d1, d2);
		double transmit_power = Parameters.transmit_power_devices;
		double p_receiver = transmit_power / (Math.pow(10, path_loss/10));  //Watt
		double watt_interference = calculate_interference(d1, selected_pairs, pairs, cell);
		double sinr = p_receiver / (watt_interference + Parameters.noise_factor);
		SINR = 10* Math.log10(sinr);
	}
	
	private double calculate_interference (
			Device d1, 
			ArrayList<Integer> selected_pairs, //used to calculate interference
			ArrayList<ArrayList<Integer>> pairs,
			ArrayList<Device> cell
	) {
		double intereference = 0;
		for (int i = 0; i < selected_pairs.size(); i++) {
			Device ue1 = cell.get(pairs.get(selected_pairs.get(i)).get(0)); //assuming the transmitter is always first element of the pair
			double path_loss_d2d = path_loss(ue1, d1);
			if (path_loss_d2d > 0) {
				path_loss_d2d = Parameters.transmit_power_devices / (Math.pow(10, path_loss_d2d/10));  //Watt
				intereference += path_loss_d2d;
			}
		}
		return intereference; //Watt
	}

	private double path_loss(Device d1) {
		double dist = Math.sqrt(Math.pow(d1.x, 2) + Math.pow(d1.y, 2)); //distance of base station from origin
		double mult = 1.0;
		if (dist/10 < mult)
			mult = dist/10;
		double alpha = mult * (1- Math.exp(-dist/36));
		alpha += Math.exp(-dist/36);
		double PL_LOS = (22* Math.log10(dist)) + 42  + (20*Math.log10(Parameters.frequency/5));
		double PL_NLOS = (36.7* Math.log10(dist)) + 40.9 + (26*Math.log10(Parameters.frequency/5));
		//double X = 3.0; //TODO : what is sigma -_-
		return (alpha * PL_LOS) + ((1-alpha)*PL_NLOS) /*+ X*/;
	}
	
	private double path_loss(Device d1, Device d2) {
		double dist = Math.sqrt(Math.pow(d1.x-d2.x, 2) + Math.pow(d1.y-d2.y, 2)); //distance of base station from origin
		double alpha = 0;
		
		if (dist <= 4)
			alpha = 1;
		else if (dist < 60 && dist > 4)
			alpha = Math.exp(-(dist-4)/3);
		else if (dist >= 60)
			alpha = 0;
		
		double PL_LOS = (16.9* Math.log10(dist)) + 46.8 + (20*Math.log10(Parameters.frequency/5));
		double PL_NLOS = (40* Math.log10(dist)) + 49 + (30*Math.log10(Parameters.frequency*100));
		//double X = 12.0; //TODO : what is sigma -_-
		return (alpha * PL_LOS) + ((1-alpha)*PL_NLOS) /*+ X*/;
	}
}
