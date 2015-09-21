import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class Device {
	int id;
	
	public Device(){
		
	}
	public Device(int device_id) {
		id = device_id;
	}
}

class BaseStation {
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

class DiscoveryMessage {
	//should have info about transmitter, receiver
	int transmitter;
	int receiver;
	// TODO : path gain estimates
}

class TransmissionMessage {
	//what should come here?
}

public class Main {

	ArrayList<Device> cell; //total number of devices
	BaseStation bs;
	static double p_success = 0.95;
	static int sinr_cell = 10;
	static int sinr_d2d = 5;
	static int noise_factor = -104;
	static int transmit_power_bs = 41;
	static int transmit_power_devices = 21;
	static int retransmissions = 5;
	
	int cell_size = 0;
	
	public static void main(String[] args) {	
		Main m1 = new Main();
		m1.init();
		m1.algos(1); //input algo number : 1 or 2
	}
	
	public void init() {
		//taking from system.in values of cell_size and number of D2D pairs
		System.out.println("Please enter the cell size and number of D2D pairs");
		
		Scanner sc  = new Scanner(System.in);
		cell_size = sc.nextInt();
		cell = new ArrayList<Device>();
		for (int i = 0; i < cell_size; i++)  {
			Device d1 = new Device(i);
			cell.add(d1);
		}
		bs = new BaseStation(sc.nextInt());
	}
	
	public void algos(int algo_number) {
		ArrayList<ArrayList<Integer>> pairs = new ArrayList<ArrayList<Integer>>();
		pairs = generate_pairs();
		ArrayList<Integer> time_slots_needed = new ArrayList<Integer>();
		ArrayList<Integer> time_slots_given = new ArrayList<Integer>(); //needed as final answer
		ArrayList<Integer> last_failed_transmissions = new ArrayList<Integer>();
		
		int algo_time_slots = get_algo_time_slots(algo_number);
		
		for (int i = 0;i < pairs.size(); i++)  {
			time_slots_needed.add(algo_time_slots);
			time_slots_given.add(0);
			last_failed_transmissions.add(0);
		}
		
		process_algo(
			algo_number, 
			pairs, 
			time_slots_needed, 
			time_slots_given, 
			last_failed_transmissions
		);
	}
	
	public void process_algo (
		int algo_number,
		ArrayList<ArrayList<Integer>> pairs,
		ArrayList<Integer> time_slots_needed,
		ArrayList<Integer> time_slots_given,
		ArrayList<Integer> last_failed_transmissions
	) {
		int time_slot = 0; // total time slots needed for all D2D discoveries
		
		//finding time slots needed to detect n D2D pairs
		while(true) {
			
			//selecting a pair based on transmission probability
			Random random_number_generator = new Random();
			int selected_pair = random_number_generator.nextInt(bs.n);
			
			int discoveries = 0;
			while (
					time_slots_needed.get(selected_pair) == 0 
					|| last_failed_transmissions.get(selected_pair) == retransmissions
			) {
				//just choosing the next pair if current one is already discovered
				//do we change the transmission probability? 
				if (discoveries == bs.n)
					break;
				discoveries++;
				selected_pair++;
				selected_pair = (selected_pair) % bs.n;
			}
			
			//now the current time slot is alloted to the selected pair. Make its next transmission
			if (discoveries < bs.n) {
				boolean success = false;
				success = make_transmission(algo_number, selected_pair, pairs, time_slots_needed);
				if (success) {
					time_slots_needed.set(selected_pair, time_slots_needed.get(selected_pair) - 1);
					time_slots_given.set(selected_pair, time_slots_given.get(selected_pair) + 1);
					last_failed_transmissions.set(selected_pair, 0);
				}
				else {
					last_failed_transmissions.set(selected_pair, last_failed_transmissions.get(selected_pair) + 1);
					//do we count failed transmission as a time slot alloted too? I think, yes.
				}
			}
			else // all D2D pairs discovered
				break;
			time_slot++;
		}
	}
	
	public int get_algo_time_slots(int algo_number) {
		if (algo_number == 1)
			return 5;
		else
			return 2;
	}
	
	public boolean make_transmission(
		int algo_number, 
		int selected_pair, 
		ArrayList<ArrayList<Integer>> pairs,
		ArrayList<Integer> time_slots_needed
	) {
		if (algo_number == 1)
			return make_transmission_algo1(selected_pair, pairs, time_slots_needed);
		else
			return make_transmission_algo2(selected_pair, pairs, time_slots_needed);
	}
	
	public boolean make_transmission_algo1(
		int selected_pair, 
		ArrayList<ArrayList<Integer>> pairs,
		ArrayList<Integer> time_slots_needed 
	) {
		
		boolean success = true;
		/***
		 *TODO things to be done in each time slot depends on the actions given in the algo
		 *Something like switch cases, to perform different actions for different steps
		 *In case of unsuccessful transmission, return false
		 *This function should take care of sending the discovery message and its contents.
		***/
		return success;
	}
	
	public boolean make_transmission_algo2(
		int selected_pair, 
		ArrayList<ArrayList<Integer>> pairs,
		ArrayList<Integer> time_slots_needed 
	) {
		
		boolean success = true;
		/***
		 *TODO things to be done in each time slot depends on the actions given in the algo
		 *Something like switch cases, to perform different actions for different steps
		 *In case of unsuccessful transmission, return false
		 *This function should take care of sending the discovery message and its contents.
		***/
		return success;
	}
	
	public ArrayList<ArrayList<Integer>> generate_pairs() {
		Random random_number_generator = new Random();
		ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
		HashSet<Integer> taken = new HashSet<>();
		for (int i = 0;i < bs.n ; i++) {
			ArrayList<Integer> pair = new ArrayList<Integer>();
			int device1 = random_number_generator.nextInt(cell_size);
			taken.add(device1);
			int device2 = random_number_generator.nextInt(cell_size);
			while (taken.contains(device2))
				device2 = random_number_generator.nextInt(cell_size);
			taken.add(device2);
			pair.add(device1);
			pair.add(device2);
			ans.add(pair);
		}
		return ans;
	}
}
