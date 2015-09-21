public class BaseStation {
	int n; //number of D2D devices that can take part
	double transmission_probab;
	
	public BaseStation(int number_of_pairs) {
		n = number_of_pairs;
		transmission_probab = 1/n;
	}
	public BaseStation(double probab) {
		transmission_probab = probab;
	}
}
