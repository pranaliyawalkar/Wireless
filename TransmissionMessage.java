import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.math.BigDecimal;

public class TransmissionMessage extends Message {
	int transmitter;
	int receiver;
	double received_interference; //values it carries from devices to BS
	double received_pathgain;
	double received_sinr;
	//SINR should meet γCELL

}


