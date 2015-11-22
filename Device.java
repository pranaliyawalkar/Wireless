public class Device {
	int id;
	double x; //x coordinate in meter
	double y; //y coordinate in meter
	
	public Device(){
		
	}
	public Device(int device_id, double inputx, double inputy) {
		id = device_id;
		x = inputx;
		y = inputy;
	}
}
