import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main {

	ArrayList<Device> cell; //total devices
	ArrayList<ArrayList<Integer>> pairs;
	BaseStation bs;
	int algo_number = 1;
	Parameters params = new Parameters();
	
	public static void main(String[] args) {	
		Main m1 = new Main();
		m1.init();
	}
	
	public void init() {
		cell = new ArrayList<Device>();
		Random random_number_genrator = new Random();
		HashSet<String> taken = new HashSet<>();
		int i = 0;
		while(i < params.cell_size)  {
			int r = random_number_genrator.nextInt(params.cell_radius+1); // 0 to cell_radius
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
		bs = new BaseStation(params.D2Dpairs);
		pairs = generate_pairs();
		if (algo_number ==1 ) {
			Algo1 algo1 = new Algo1(pairs, cell);
			algo1.process_algo();
		}
		else if (algo_number ==2 ) {
			Algo2 algo2 = new Algo2(pairs, cell);
			algo2.process_algo();
		}

	}
	public ArrayList<ArrayList<Integer>> generate_pairs() {
		//randomly select one, choose the other closest to him
		Random random_number_generator = new Random();
		ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
		HashSet<Integer> taken = new HashSet<>();
		for (int i = 0;i < bs.n ; i++) {
			ArrayList<Integer> pair = new ArrayList<Integer>();
			int device1 = random_number_generator.nextInt(params.cell_size);
			taken.add(device1);
			int device2 = random_number_generator.nextInt(params.cell_size);
			while (taken.contains(device2))
				device2 = random_number_generator.nextInt(params.cell_size);
			taken.add(device2);
			pair.add(device1);
			pair.add(device2);
			ans.add(pair);
		}
		return ans;
	}
}
