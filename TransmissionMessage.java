import java.util.ArrayList;

public class TransmissionMessage {
	//what should come here?
	int transmitter;
	int receiver;
	double received_interference; //values it carries from devices to BS
	double received_pathgain;
	double received_sinr;
	double SINR; //should meet γCELL

	public void feed_sinr(
			Device d1, 
			ArrayList<Integer> selected_pairs //used to calculate interference
		) {
			// TODO : this is wrong SINR calculation
			double interference = 0;
			double dist = Math.sqrt(Math.pow(d1.x, 2) + Math.pow(d1.y, 2)); //distance of base station from origin
			double mult = 1.0;
			if (dist/10 < mult)
				mult = dist/10;
			double alpha = mult * (1- Math.exp(-dist/36));
			alpha += Math.exp(-dist/36);
			double PL_LOS = (22* Math.log10(dist)) + 42 + (20*Math.log10(Parameters.frequency/5));
			double PL_NLOS = (36.7* Math.log10(dist)) + 40.9 + (26*Math.log10(Parameters.frequency/5));
			double X = 3.0; //TODO : what is sigma -_-
			this.SINR = (alpha * PL_LOS) + ((1-alpha)*PL_NLOS) + X;
		}
}


