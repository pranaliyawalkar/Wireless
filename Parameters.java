
public class Parameters {
	static int sinr_cell = 10;
	static int sinr_d2d = 5;
	static double noise_factor = -134; //in dB /*Math.pow(10, -13.4);  //Watt*/
	static double transmit_power_bs = Math.pow(10, 1.1);  //Watt
	static double transmit_power_devices = Math.pow(10, -0.9);  //Watt
	static double db_transmit_power_bs = 11;
	static double db_transmit_power_devices = -9;
	static int cell_size = 50;  //50 devices
	static int cell_radius = 400;
	static int D2Dpairs =  2;
	static double eta = 0.95;
	static double transmission_probability = 1/(double)D2Dpairs;
	static double p_success = transmission_probability * Math.pow(1 - transmission_probability, D2Dpairs-1);
	static int retransmissions = (int) (Math.log(1- eta)/ Math.log(1 - p_success)) ;
	static int retransmission_TM = 12;
	static long frequency = 2000000000; // TODO : what should it be?
}
