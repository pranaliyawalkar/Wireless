
public class Parameters {
	static int sinr_cell = 10; //in dB 
	static int sinr_d2d = 5; //in dB 
	static double noise_factor = -134; //in dB 
	static double transmit_power_bs = Math.pow(10, 1.1);  //Watt
	static double transmit_power_devices = Math.pow(10, -0.9);  //Watt
	static double db_transmit_power_bs = 11; //in dB 
	static double db_transmit_power_devices = -9; //in dB 
	static int cell_size = 50;  //50 devices
	static int cell_radius = 400; //in meter
	static int D2Dpairs = 2;  //no. of D2D pairs
	static double eta = 0.95; //success probability
	static double transmission_probability = 1/(double)D2Dpairs; //equation 2
	static double p_success = transmission_probability * Math.pow(1 - transmission_probability, D2Dpairs-1); //equation 1
	static int retransmissions = (int) (Math.log(1- eta)/ Math.log(1 - p_success)) ; //equation 5, retransmissions allowed on a discovery message
	static int retransmission_TM = 12; //no. of retransmissions allowed on a transmission message
	static long frequency = 125;  //table 1
}
