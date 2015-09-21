import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main {

	ArrayList<Device> cell; //total devices
	BaseStation bs;
	static double p_success = 0.95;
	static int sinr_cell = 10;
	static int sinr_d2d = 5;
	static int noise_factor = -104;
	static int transmit_power_bs = 41;
	static int transmit_power_devices = 21;
	static int retransmissions = 5;
	
	int cell_size = 0;
	int cell_radius = 50;
	
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
		Random random_number_genrator = new Random();
		HashSet<String> taken = new HashSet<>();
		int i = 0;
		while(i < cell_size)  {
			int r = random_number_genrator.nextInt(cell_radius+1); // 0 to cell_radius
			int theta = random_number_genrator.nextInt(360); //0 to 259
			if (taken.contains(r + "_" + theta)) {
				continue;
			}
			taken.add(r + "_" + theta);
			double x = r* Math.cos(Math.toRadians((double)theta));
			double y = r* Math.sin(Math.toRadians((double)theta));
			Device d1 = new Device(i, x, y);
			cell.add(d1);
			i++;
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
			
			//selecting pairs based on transmission probability in each time slot
			ArrayList<Integer> selected_pairs = select_pairs(time_slots_needed, last_failed_transmissions);
			if (selected_pairs.size() == 0) { //all pairs discovered or discarded or couldn't get a chance
				
				int check_count = discovered_discarded_pairs(time_slots_needed, last_failed_transmissions);
				if (check_count < bs.n)
					continue; 
				else //all pairs discovered or discarded
					break;
			}
			
			for (int i = 0; i < selected_pairs.size(); i++) {
				int selected_pair = selected_pairs.get(i);
				
				//now the current time slot is alloted to the selected pair. Make its next transmission
				boolean success = false;
				success = make_transmission(algo_number, selected_pair, pairs, selected_pairs, time_slots_needed);
				if (success) {
					time_slots_needed.set(selected_pair, time_slots_needed.get(selected_pair) - 1);
					time_slots_given.set(selected_pair, time_slots_given.get(selected_pair) + 1);
					last_failed_transmissions.set(selected_pair, 0);
				}
				else {
					last_failed_transmissions.set(selected_pair, last_failed_transmissions.get(selected_pair) + 1);
				}
			}
			time_slot++;
		}
	}
	
	public int discovered_discarded_pairs(
		ArrayList<Integer> time_slots_needed,
		ArrayList<Integer> last_failed_transmissions
	) {
		int count = 0;
		for (int i = 0 ;i < bs.n; i++) {
			if (time_slots_needed.get(i) == 0 || last_failed_transmissions.get(i) == retransmissions)
				count++;
		}
		return count;
	}
	
	public ArrayList<Integer> select_pairs(
		ArrayList<Integer> time_slots_needed,
		ArrayList<Integer> last_failed_transmissions
	) {
		ArrayList<Integer> selected_pairs = new ArrayList<>();
		for (int i = 0;i < bs.n; i++) {
			double probab = Math.random();
			if (
				probab  <= bs.transmission_probab 
				&& time_slots_needed.get(i) > 0 
				&& last_failed_transmissions.get(i) < retransmissions
			)
				selected_pairs.add(i);
		}
		return selected_pairs;
	}
	
	public int get_algo_time_slots(int algo_number) {
		if (algo_number == 1)
			return 9;
		else
			return 4;
	}
	
	public boolean make_transmission(
		int algo_number, 
		int selected_pair, 
		ArrayList<ArrayList<Integer>> pairs,
		ArrayList<Integer> selected_pairs, //used to calculate interference
		ArrayList<Integer> time_slots_needed
	) {
		if (algo_number == 1)
			return make_transmission_algo1(selected_pair, pairs, selected_pairs, time_slots_needed);
		else
			return make_transmission_algo2(selected_pair, pairs, selected_pairs, time_slots_needed);
	}
	
	public boolean make_transmission_algo1(
		int selected_pair, 
		ArrayList<ArrayList<Integer>> pairs,
		ArrayList<Integer> selected_pairs,
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
		ArrayList<Integer> selected_pairs,
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
		//randomly select one, choose the other closest to him
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
