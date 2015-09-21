import java.util.ArrayList;

public class DiscoveryMessage {
	//should have info about transmitter, receiver
	int transmitter;
	int receiver;
	// TODO : path gain estimates
	double SINR; //should meet γD2D
	
	public void feed_sinr(
			Device d1,
			Device d2,
			ArrayList<Integer> selected_pairs //used to calculate interference
		) {
			// TODO : this is wrong SINR calculation
			double interference = 0;
			double dist = Math.sqrt(Math.pow(d1.x, 2) + Math.pow(d1.y, 2)); //distance of base station from origin
			double alpha = 0;
			
			if (dist <= 4)
				alpha = 1;
			else if (dist < 60 && dist > 4)
				alpha = Math.exp(-(dist-4)/3);
			else if (dist >= 60)
				alpha = 0;
			
			double PL_LOS = (16.9* Math.log10(dist)) + 46.8 + (20*Math.log10(Parameters.frequency/5));
			double PL_NLOS = (40* Math.log10(dist)) + 49 + (30*Math.log10(Parameters.frequency*100));
			double X = 12.0; //TODO : what is sigma -_-
			this.SINR = (alpha * PL_LOS) + ((1-alpha)*PL_NLOS) + X;
		}
}
