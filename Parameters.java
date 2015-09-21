
public class Parameters {
	static double p_success = 0.95;
	static int sinr_cell = 10;
	static int sinr_d2d = 5;
	static int noise_factor = -104;
	static int transmit_power_bs = 41;
	static int transmit_power_devices = 21;
	static int retransmissions = 5;  //TODO : change to that of equation 5 value
	static int cell_size = 0;
	static int cell_radius = 400;
	static int D2Dpairs =  2;
	static double transmission_probability = 1/D2Dpairs;
	static int frequency = 150; // TODO : what should it be?
}
