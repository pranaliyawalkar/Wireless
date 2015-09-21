
public class Parameters {
	static int sinr_cell = 10;
	static int sinr_d2d = 5;
	static double noise_factor = Math.pow(10, -13.4);  //Watt
	static double transmit_power_bs = Math.pow(10, 1.1);  //Watt
	static double transmit_power_devices = Math.pow(10, -0.9);  //Watt
	static int cell_size = 50;  //50 devices
	static int cell_radius = 400;
	static int D2Dpairs =  4;
	static double eta = 0.95;
	static double transmission_probability = 1/(double)D2Dpairs;
	static double p_success = transmission_probability * Math.pow(1 - transmission_probability, D2Dpairs-1);
	static int retransmissions = (int) (Math.log10(1- eta)/ Math.log10(1 - p_success));  //TODO : change to that of equation 5 value
	static int frequency = 150; // TODO : what should it be?
}
